package br.com.remartins.bytechameleon;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;

import br.com.remartins.bytechameleon.xml.ByteChameleon;

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

		List<ByteChameleon> descriptors = carregarArgumentos(agentArguments);

		LOGGER.debug("--- START INSTRUMENTATION ---");

		instrumentation.addTransformer(new Transformer(descriptors));

		LOGGER.debug("--- STOP INSTRUMENTATION ---");
	}

	private static List<ByteChameleon> carregarArgumentos(String agentArguments) {
		XStream xStream = new XStream();
		xStream.processAnnotations(ByteChameleon.class);

		String[] files = agentArguments.split(",");
		List<ByteChameleon> list = new ArrayList<ByteChameleon>();

		for (String file : files) {
			list.add((ByteChameleon) xStream.fromXML(new File(file.replace(" ", ""))));
		}

		return list;
	}

}
