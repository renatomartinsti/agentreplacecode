package com.github.remartins.bytechameleon;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ea.agentloader.AgentLoader;
import com.github.remartins.bytechameleon.Agent;

/**
 * 
 * @author Renato Martins
 * @since 12/2017
 *
 */
public class AgentTest {

	@Test
	public void agentReplaceMethodWithParams() {

		AgentLoader.loadAgentClass(Agent.class.getName(), AgentTest.class.getResource("/byte-chameleon.xml").getPath());

		Process p = new Process();
		String result = p.process("It's me, param!");
		assertTrue(result.equals("Wow !!! Message with Instrumentation: It's me, param!"));
	}
	
	@Test
	public void agentReplaceMethodWithoutParams() {

		AgentLoader.loadAgentClass(Agent.class.getName(), AgentTest.class.getResource("/byte-chameleon.xml").getPath());

		Process p = new Process();
		int result = p.sum();
		assertTrue(result == 6);
	}

}
