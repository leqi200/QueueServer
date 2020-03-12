package com.tonsincs.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.tonsincs.vo.CommPackage;

/**
 * JAVABean转换工具
 * 
 * @author mrxiao
 * 
 */
public class BeanUtils {

	/**
	 * 
	 * @param commPackage
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String beanToXML(CommPackage commPackage, String encoding)
			throws IOException {
		JAXBContext jaxbContext;
		String xml = null;
		StringWriter writer = null;
		try {

			jaxbContext = JAXBContext.newInstance(CommPackage.class);
			Marshaller jaxbmMarshaller = jaxbContext.createMarshaller();
			jaxbmMarshaller
					.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
			jaxbmMarshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			writer = new StringWriter();

			jaxbmMarshaller.marshal(commPackage, writer);

			xml = writer.toString();

			jaxbContext = null;
			jaxbmMarshaller = null;

		} catch (JAXBException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			writer.close();
			writer = null;
		}
		return xml;
	}

	@SuppressWarnings("unchecked")
	public static <T> T converyToJavaBean(String xml, Class<T> c)
			throws JAXBException {
		T t = null;

		JAXBContext context = JAXBContext.newInstance(c);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		t = (T) unmarshaller.unmarshal(new StringReader(xml));

		return t;
	}

	public static void main(String[] args) {
		try {
			String xml = BeanUtils.beanToXML(
					new CommPackage("", "", "", "", "",""), "UTF-8");
			System.out.println(xml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
