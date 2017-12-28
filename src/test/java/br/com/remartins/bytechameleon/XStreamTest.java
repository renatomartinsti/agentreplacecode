package br.com.remartins.bytechameleon;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.remartins.bytechameleon.xml.ByteChameleon;
import br.com.remartins.bytechameleon.xml.Clazz;
import br.com.remartins.bytechameleon.xml.Method;

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
		
		String[] result = new String[3];
		result[1] = "br.com.remartins.bytechameleon.Process";
		result[2] = "- replace : process";
		
		
		String[] real = byteChameleon.toString().split("\n");
		
		for (int i = 1; i < real.length; i++) {
			assertTrue(real[i].equals(result[i]));
		}

	}
}
