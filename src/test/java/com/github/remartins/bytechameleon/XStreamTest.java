package com.github.remartins.bytechameleon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import com.github.remartins.bytechameleon.parse.xml.ByteChameleon;
import com.github.remartins.bytechameleon.parse.xml.Clazz;
import com.github.remartins.bytechameleon.parse.xml.Method;
import com.thoughtworks.xstream.XStream;

public class XStreamTest {

	@Test
	public void readXml() {
		XStream xStream = new XStream();
		
		Class<?>[] classes = new Class[] { ByteChameleon.class, Clazz.class, Method.class };
		XStream.setupDefaultSecurity(xStream);
		xStream.allowTypes(classes);
		xStream.processAnnotations(ByteChameleon.class);
		
		ByteChameleon byteChameleon = (ByteChameleon) xStream
				.fromXML(new File(AgentTest.class.getResource("/byte-chameleon.xml").getPath()));
		
		String[] result = new String[6];
		result[1] = "com.github.remartins.bytechameleon.Process";
		result[2] = "- replace : process";
		result[3] = "- replace : sum";
		result[4] = "- before : sum";
		result[5] = "- after : process";
		
		
		String[] real = byteChameleon.toString().split("\n");
		
		for (int i = 1; i < real.length; i++) {
			assertTrue(real[i].equals(result[i]));
		}
	}
	
	
	@Test
	public void writeXml() {
		
		Method method = new Method();
		method.setName("message");
		method.setParams("String");
		method.setType("before");
		method.setCode("System.out.println(\"Hello before call !!!\");");
		
		Clazz clazz = new Clazz();
		clazz.setName("Messenger");
		clazz.setMethods(new ArrayList<Method>());
		clazz.getMethods().add(method);

		ByteChameleon bc = new ByteChameleon();
		bc.setClasses(new ArrayList<Clazz>());
		bc.getClasses().add(clazz);
		
		XStream xStream = new XStream();
		Class<?>[] classes = new Class[] { ByteChameleon.class, Clazz.class, Method.class };
		XStream.setupDefaultSecurity(xStream);
		xStream.allowTypes(classes);
		xStream.processAnnotations(ByteChameleon.class);
		
		String result = xStream.toXML(bc).replaceAll("&quot;", "\"");
		String[] resultArray = result.split("\n");
		
		
		String[] padrao = {
				"<byte-chameleon>",
				"  <classes>",
				"    <class>",
				"      <name>Messenger</name>",
				"      <methods>",
				"        <method type=\"before\">",
				"          <name>message</name>",
				"          <params>String</params>",
				"          <code>System.out.println(\"Hello before call !!!\");</code>",
				"        </method>",
				"      </methods>",
				"    </class>",
				"  </classes>",
				"</byte-chameleon>"
		};
		
		assertEquals(padrao.length, resultArray.length);
		
		for (int i = 0; i < padrao.length - 1; i++) {
			assertTrue("Linha " + (i + 1) + ": " + resultArray[i], resultArray[i].equals(padrao[i]));
		}
		
		
	}
}
