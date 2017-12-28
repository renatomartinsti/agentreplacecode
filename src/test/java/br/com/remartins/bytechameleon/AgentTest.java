package br.com.remartins.bytechameleon;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ea.agentloader.AgentLoader;

public class AgentTest {

	@Test
	public void agentReplaceMethod() {

		AgentLoader.loadAgentClass(Agent.class.getName(), AgentTest.class.getResource("/byte-chameleon.xml").getPath());

		Process p = new Process();
		String result = p.process("It's me, param!");
		assertTrue(result.equals("Wow !!! Message with Instrumentation: It's me, param!"));
	}

}
