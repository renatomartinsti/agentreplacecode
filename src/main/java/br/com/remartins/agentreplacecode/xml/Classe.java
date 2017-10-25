package br.com.remartins.agentreplacecode.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Classe")
public class Classe {

	private String nome;
	private Metodo metodo;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Metodo getMetodo() {
		return metodo;
	}

	public void setMetodo(Metodo metodo) {
		this.metodo = metodo;
	}

}
