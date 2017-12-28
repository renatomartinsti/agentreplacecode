package br.com.remartins.bytechameleon.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;

public class XStreamSample {

	private static final Logger LOGGER = Logger.getLogger(XStreamSample.class);

	public static void main(String[] args) {

		XStreamSample sample = new XStreamSample();

		//sample.readXml();

		sample.writeXml();

	}

	private void readXml() {
		XStream xStream = new XStream();
		xStream.processAnnotations(ByteChameleon.class);
		ByteChameleon byteChameleon = (ByteChameleon) xStream
				.fromXML(new File(XStreamSample.class.getResource("/byte-chameleon.txt").getPath()));
		LOGGER.info(byteChameleon);

	}

	private void writeXml() {
		XStream xStream = new XStream();
		xStream.processAnnotations(ByteChameleon.class);
		LOGGER.info(xStream.toXML(getByteChameleon()));
	}

	private ByteChameleon getByteChameleon() {
		ByteChameleon byteChameleon = new ByteChameleon();
		byteChameleon.setClasses(getClasses());
		return byteChameleon;
	}

	private List<Clazz> getClasses() {
		List<Clazz> classes = new ArrayList<Clazz>();

		for (int i = 0; i < 5; i++) {
			Clazz clazz = new Clazz();
			clazz.setName("Class" + i);
			clazz.setMethods(getMetodos());
			classes.add(clazz);
		}

		return classes;
	}

	private List<Method> getMetodos() {
		List<Method> metodos = new ArrayList<Method>();

		for (int i = 0; i < 3; i++) {
			Method method = new Method();
			method.setCode("System.out.println(\"Sou o novissimo Sem Parametros\");");
			method.setName("methodProcess" + i);
			method.setParams("java.lang.String, java.lang.Integer");
			method.setType("replace");
			metodos.add(method);
		}

		return metodos;
	}

}
