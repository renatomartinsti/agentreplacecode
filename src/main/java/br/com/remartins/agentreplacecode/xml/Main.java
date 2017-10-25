package br.com.remartins.agentreplacecode.xml;

import java.io.File;

import com.thoughtworks.xstream.XStream;


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
	

}
