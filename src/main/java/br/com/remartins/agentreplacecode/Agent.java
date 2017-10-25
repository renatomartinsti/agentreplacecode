package br.com.remartins.agentreplacecode;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import br.com.remartins.agentreplacecode.xml.deprecated.Config;

public class Agent {

	public static void premain(String agentArguments,
			Instrumentation instrumentation) {
		
		List<Config> listConfig = carregarArgumentos(agentArguments);
		
		System.out.println(agentArguments);
		
		instrumentation.addTransformer(new Transformer(listConfig));
	}

	@SuppressWarnings("unchecked")
	private static List<Config> carregarArgumentos(String agentArguments) {
		XStream xStream = new XStream();
		return ((List<Config>) xStream.fromXML(new File(agentArguments)));
	}
	
}
