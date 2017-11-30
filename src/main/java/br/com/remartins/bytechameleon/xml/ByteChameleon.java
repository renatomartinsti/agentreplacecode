package br.com.remartins.bytechameleon.xml;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("byte-chameleon")
public class ByteChameleon {

	private List<Classe> classes;

	public List<Classe> getClasses() {
		return classes;
	}

	public void setClasses(List<Classe> classes) {
		this.classes = classes;
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		for (Classe classe : this.classes) {
			sb.append("\n");
			sb.append(classe.getNome());
			sb.append("\n");
			for (Metodo metodo : classe.getMetodos()) {
				sb.append("  ").append(metodo).append("\n");	
			}
		}
		
		return sb.toString();
	}
	
	

}
