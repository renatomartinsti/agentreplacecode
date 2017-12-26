package br.com.remartins.bytechameleon;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.remartins.bytechameleon.xml.ByteChameleon;
import br.com.remartins.bytechameleon.xml.Clazz;
import br.com.remartins.bytechameleon.xml.Method;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;

public class AgentTest {
	

	private static final String CLASSNAME = "br.com.remartins.bytechameleon.Calc";


	
	@Test
	public void testAgent() {

		try {


			Transformer transformer = new Transformer(getListDescription());
			byte[] bytecodeReplaced = transformer.transform(this.getClass().getClassLoader(), CLASSNAME, null, null,
					null);
			
			simulateLoadedClassReplaced(bytecodeReplaced);
			
			Calc calc = new Calc();
			int result = calc.sum(2, 2);

			assertTrue(result == 0);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	private List<ByteChameleon> getListDescription() {
		Method metodo = new Method();
		metodo.setName("sum");
		metodo.setParams("int,int");
		metodo.setType("replace");
		metodo.setCode("{return $1 - $2; System.out.print(\"Wow, subtracted ?!!!\");}");
		

		Clazz classe = new Clazz();
		classe.setName(CLASSNAME);
		classe.setMethods(new ArrayList<Method>());
		classe.getMethods().add(metodo);

		ByteChameleon bc = new ByteChameleon();
		bc.setClasses(new ArrayList<Clazz>());
		bc.getClasses().add(classe);

		List<ByteChameleon> list = new ArrayList<ByteChameleon>();
		list.add(bc);
		
		return list;
	}

	private void simulateLoadedClassReplaced(byte[] b) throws IOException, CannotCompileException {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.makeClass(new ByteArrayInputStream(b));
		cc.toClass();
	}


}
