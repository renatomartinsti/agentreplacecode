package br.com.remartins.bytechameleon.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 
 * @author Renato Martins
 * @since 10/2017
 *
 */
@XStreamAlias("method")
public class Method {

	private String name;
	private String params;
	private String code;

	@XStreamAsAttribute
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getType());
		sb.append(" : ");
		sb.append(getName());
		return sb.toString();
	}

}
