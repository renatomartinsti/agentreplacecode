
package com.github.remartins.bytechameleon.parse.xml;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 
 * @author Renato Martins
 * @since 10/2017
 *
 */
@XStreamAlias("class")
public class Clazz {

	@XStreamOmitField
	private boolean instrumentalized = false;

	private String name;
	
	@XStreamAlias("methods")
	private List<Method> method;

	public boolean isInstrumentalized() {
		return instrumentalized;
	}

	public void setInstrumentalized(boolean instrumentalized) {
		this.instrumentalized = instrumentalized;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Method> getMethods() {
		return method;
	}

	public void setMethods(List<Method> methods) {
		this.method = methods;
	}

}
