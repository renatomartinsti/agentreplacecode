package com.github.remartins.bytechameleon;

import static org.junit.Assert.assertTrue;

import java.io.File;

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
}
