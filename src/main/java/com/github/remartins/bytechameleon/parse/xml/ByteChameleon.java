package com.github.remartins.bytechameleon.parse.xml;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * 
 * @author Renato Martins
 * @since 10/2017
 *
 */
@XStreamAlias("byte-chameleon")
public class ByteChameleon {

	private List<Clazz> classes;

	public List<Clazz> getClasses() {
		return classes;
	}

	public void setClasses(List<Clazz> classes) {
		this.classes = classes;
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		for (Clazz classe : this.classes) {
			sb.append("\n");
			sb.append(classe.getName());
			sb.append("\n");
			for (Method metodo : classe.getMethods()) {
				sb.append("- ").append(metodo).append("\n");	
			}
		}
		
		return sb.toString();
	}
	
	

}
