package br.com.remartins.agentreplacecode;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import br.com.remartins.agentreplacecode.xml.Classe;

public class Agent {

	public static void premain(String agentArguments,
			Instrumentation instrumentation) {
		
		List<Classe> listConfig = carregarArgumentos(agentArguments);
		
		System.out.println(agentArguments);
		
		instrumentation.addTransformer(new Transformer(listConfig));
	}

	private static List<Classe> carregarArgumentos(String agentArguments) {
		XStream xStream = new XStream();
		xStream.alias("classe", Classe.class);
		
		String[] files = agentArguments.split(",");
		List<Classe> list = new ArrayList<Classe>();
		
		for (String file : files) {
			list.add((Classe) xStream.fromXML(new File(file.replace(" ", ""))));
		}
		
		return list;
	}
	
}
