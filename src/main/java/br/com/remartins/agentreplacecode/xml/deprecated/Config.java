package br.com.remartins.agentreplacecode.xml.deprecated;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Config")
public class Config {

	private Classe classeAlvo;
	private Classe classeFonte;

	public Classe getClasseAlvo() {
		return classeAlvo;
	}

	public void setClasseAlvo(Classe classeAlvo) {
		this.classeAlvo = classeAlvo;
	}

	public Classe getClasseFonte() {
		return classeFonte;
	}

	public void setClasseFonte(Classe classeFonte) {
		this.classeFonte = classeFonte;
	}

}
