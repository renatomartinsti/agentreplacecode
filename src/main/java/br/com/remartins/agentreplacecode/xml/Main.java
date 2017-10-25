package br.com.remartins.agentreplacecode.xml;

import java.io.File;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import br.com.remartins.agentreplacecode.xml.deprecated.Config;

public class Main {

	public static void main(String[] args) {
		//gravarXml();
		lerXml();
		
	}
	
	private static void lerXml() {
		XStream xStream = new XStream();
		xStream.autodetectAnnotations(true);
		xStream.alias("classe", Classe.class);
		Classe classe = (Classe) xStream.fromXML(new File("C:\\dados\\desenvolvimento\\java\\agent.txt"));
		System.out.println(classe);
	}
	
	private static void lerListaXml() {
		XStream xStream = new XStream();
		List<Config> list = (List<Config>)xStream.fromXML(new File("C:\\dados\\desenvolvimento\\java\\agent.txt"));
		System.out.println(list.get(0));
	}

//	
//	private static void gravarXml() {
//		Classe classe = new Classe();
//		classe.setNome("Classe " + 1);
//		classe.setMetodo("Metodo " + 1);
//		
//		XStream xStream = new XStream();
//		xStream.autodetectAnnotations(true);
//		System.out.println(xStream.toXML(classe));
//	}
//	
//	private static void gravarListaXml() {
//		List<Config> list = new ArrayList<Config>();
//		
//		for (int i = 1; i < 11; i++) {
//			Config config = new Config();
//			
//			Classe classe = new Classe();
//			classe.setNome("Classe " + i);
//			classe.setMetodo("Metodo " + i);
//			
//			Classe classeFonte = new Classe();
//			classeFonte.setNome("Classe Fonte " + i);
//			classeFonte.setMetodo("Metodo Fonte " + i);
//			
//			config.setClasseAlvo(classe);
//			config.setClasseFonte(classeFonte);
//			
//			list.add(config);
//		}
//		
//		XStream xStream = new XStream();
//		System.out.println(xStream.toXML(list));
//	}
//	
}
