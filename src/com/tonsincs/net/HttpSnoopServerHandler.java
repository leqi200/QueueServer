/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.tonsincs.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.CookieDecoder;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.ServerCookieEncoder;
import io.netty.util.CharsetUtil;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.tonsincs.service.QueueServiceManage;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;

public class HttpSnoopServerHandler extends SimpleChannelInboundHandler<Object> {
	
	private Logger log = Logger.getLogger(HttpSnoopServerHandler.class);
	
	private HttpRequest request;
	/** Buffer that stores the response content */
	private final StringBuilder req_buf = new StringBuilder();//用于组装接收请求信息
	
	private final StringBuilder res_buf=new StringBuilder();//响应信息
	
    private boolean isRequestContent=false;	//标识http请求是否包含内容
    
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
		
		
		if (msg instanceof HttpRequest) {
			
			HttpRequest request = this.request = (HttpRequest) msg;
			
			if (HttpHeaders.is100ContinueExpected(request)) {
				send100Continue(ctx);
			}
			
			isRequestContent=false;//重置标志位
			res_buf.setLength(0);//重置响应消息缓冲区
			req_buf.setLength(0);//重置请求消息缓冲区
			req_buf.append("WELCOME TO THE QUEUESYSTEM WEB SERVER\r\n");
			req_buf.append("===================================\r\n");

			req_buf.append("VERSION: ").append(request.getProtocolVersion())
					.append("\r\n");
			req_buf.append("HOSTNAME: ")
					.append(HttpHeaders.getHost(request, "unknown"))
					.append("\r\n");
			req_buf.append("REQUEST_URI: ").append(request.getUri())
					.append("\r\n\r\n");
			
			//获得HTTP请求头信息
			HttpHeaders headers = request.headers();
			if (!headers.isEmpty()) {
				for (Map.Entry<String, String> h : headers) {
					String key = h.getKey();
					String value = h.getValue();
					req_buf.append("HEADER: ").append(key).append(" = ")
							.append(value).append("\r\n");
				}
				req_buf.append("\r\n");
			}
			
            //打印URL 请求地址参数
			QueryStringDecoder queryStringDecoder = new QueryStringDecoder(
					request.getUri());
			Map<String, List<String>> params = queryStringDecoder.parameters();
			if (!params.isEmpty()) {
				for (Entry<String, List<String>> p : params.entrySet()) {
					String key = p.getKey();
					List<String> vals = p.getValue();
					for (String val : vals) {
						req_buf.append("PARAM: ").append(key).append(" = ")
								.append(val).append("\r\n");
					}
				}
				req_buf.append("\r\n");
			}

			appendDecoderResult(req_buf, request);
			
		}

		//判断是否开始接收请求主体内容
		if (msg instanceof HttpContent) {
			HttpContent httpContent = (HttpContent) msg;

			ByteBuf content = httpContent.content();
			if (content.isReadable()) {
				req_buf.append("CONTENT: \r\n");
				String req_xml = content.toString(CharsetUtil.UTF_8);
				req_buf.append(req_xml+"\r\n");
				
				//响应报文消息
				String res_xml= QueueServiceManage.doServer(req_xml);
				//组装响应内容
				res_buf.append(res_xml);
			   //xml2 = BeanUtils.beanToXML(cp, "UTF-8");
				 
				
				//System.out.println("msg:" + xml2 );
				req_buf.append("RESPONSE_CONTENT:" + res_xml );
				req_buf.append("\r\n");
				appendDecoderResult(req_buf, request);
				
				log.info("\r\n"+req_buf.toString());
				isRequestContent=true;//设置标志位
			}
			
			//判断请求内容是否为结束
			if (msg instanceof LastHttpContent) {
				req_buf.append("END OF CONTENT\r\n");

				LastHttpContent trailer = (LastHttpContent) msg;
				if (!trailer.trailingHeaders().isEmpty()) {
					req_buf.append("\r\n");
					for (String name : trailer.trailingHeaders().names()) {
						for (String value : trailer.trailingHeaders().getAll(
								name)) {
							req_buf.append("TRAILING HEADER: ");
							req_buf.append(name).append(" = ").append(value)
									.append("\r\n");
						}
					}
					req_buf.append("\r\n");
				}

				//如果Http请求没有包含内容则返回请求内容否则返回报文响应内容
				if (!isRequestContent) {
					res_buf.setLength(0);
					res_buf.append(req_buf);
				} 
				
				if (!writeResponse(trailer, ctx,res_buf)) {
					// If keep-alive is off, close the connection once the
					// content is fully written.
					ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(
							ChannelFutureListener.CLOSE);
				}
			}
		}
	}

	private static void appendDecoderResult(StringBuilder buf, HttpObject o) {
		DecoderResult result = o.getDecoderResult();
		if (result.isSuccess()) {
			return;
		}

		buf.append(".. WITH DECODER FAILURE: ");
		buf.append(result.cause());
		buf.append("\r\n");
	}

	
	/**
	 * 包装响应消息并且响客户端响应内容
	 * @param currentObj
	 * @param ctx
	 * @param buf
	 * @return
	 */
	private boolean writeResponse(HttpObject currentObj,
			ChannelHandlerContext ctx,StringBuilder buf) {
		// Decide whether to close the connection or not.
		boolean keepAlive = HttpHeaders.isKeepAlive(request);
		// Build the response object.
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
				currentObj.getDecoderResult().isSuccess() ? OK : BAD_REQUEST,
				Unpooled.copiedBuffer(buf.toString(), CharsetUtil.UTF_8));

		response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");

		if (keepAlive) {
			// Add 'Content-Length' header only for a keep-alive connection.
			response.headers().set(CONTENT_LENGTH,
					response.content().readableBytes());
			// Add keep alive header as per:
			// -
			// http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
			response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
		}

		// Encode the cookie.
		String cookieString = request.headers().get(COOKIE);
		if (cookieString != null) {
			Set<Cookie> cookies = CookieDecoder.decode(cookieString);
			if (!cookies.isEmpty()) {
				// Reset the cookies if necessary.
				for (Cookie cookie : cookies) {
					response.headers().add(SET_COOKIE,
							ServerCookieEncoder.encode(cookie));
				}
			}
		} else {
			// Browser sent no cookie. Add some.
			response.headers().add(SET_COOKIE,
					ServerCookieEncoder.encode("key1", "value1"));
			response.headers().add(SET_COOKIE,
					ServerCookieEncoder.encode("key2", "value2"));
		}

		// Write the response.
		ctx.write(response);

		return keepAlive;
	}

	private static void send100Continue(ChannelHandlerContext ctx) {
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
				CONTINUE);
		ctx.write(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		//cause.printStackTrace();
		log.error("",cause);
		ctx.close();
	}
}
