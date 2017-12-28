package com.github.remartins.bytechameleon;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.github.remartins.bytechameleon.parse.xml.ByteChameleon;
import com.github.remartins.bytechameleon.parse.xml.Clazz;
import com.github.remartins.bytechameleon.parse.xml.Method;
import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author Renato Martins
 * @since 10/2017
 *
 */
public class Agent {

	private static final Logger LOGGER = Logger.getLogger(Agent.class);

	private Agent() {
		super();
	}

	public static void premain(String agentArguments, Instrumentation instrumentation) {
		instrumentation(agentArguments, instrumentation);
	}

	public static void agentmain(String agentArguments, Instrumentation instrumentation) {
		instrumentation(agentArguments, instrumentation);
	}

	private static void instrumentation(String agentArguments, Instrumentation instrumentation) {
		List<ByteChameleon> descriptors = loadArgs(agentArguments);

		LOGGER.debug("--- START INSTRUMENTATION ---");

		instrumentation.addTransformer(new Transformer(descriptors));

		LOGGER.debug("--- STOP INSTRUMENTATION ---");
	}

	private static List<ByteChameleon> loadArgs(String agentArguments) {
		XStream xStream = new XStream();

		Class<?>[] classes = new Class[] { ByteChameleon.class, Clazz.class, Method.class };
		XStream.setupDefaultSecurity(xStream);
		xStream.allowTypes(classes);

		xStream.processAnnotations(ByteChameleon.class);

		String[] files = agentArguments.split(",");
		List<ByteChameleon> list = new ArrayList<ByteChameleon>();

		for (String file : files) {
			list.add((ByteChameleon) xStream.fromXML(new File(file.replace(" ", ""))));
		}

		return list;
	}

}
