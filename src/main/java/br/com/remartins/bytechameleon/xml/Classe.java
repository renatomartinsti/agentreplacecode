
package br.com.remartins.bytechameleon.xml;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("classe")
public class Classe {

	@XStreamOmitField
	private boolean instrumentalizado = false;

	private String nome;
	private List<Metodo> metodos;

	public boolean isInstrumentalizado() {
		return instrumentalizado;
	}

	public void setInstrumentalizado(boolean instrumentalizado) {
		this.instrumentalizado = instrumentalizado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Metodo> getMetodos() {
		return metodos;
	}

	public void setMetodos(List<Metodo> metodos) {
		this.metodos = metodos;
	}

}
