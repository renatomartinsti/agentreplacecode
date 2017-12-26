package br.com.remartins.bytechameleon;

import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.remartins.bytechameleon.xml.ByteChameleon;
import br.com.remartins.bytechameleon.xml.Classe;
import br.com.remartins.bytechameleon.xml.Metodo;
import javassist.ByteArrayClassPath;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

public class AgentTest {
	

	@Test
	public void testAgent() {

		try {
			Metodo metodo = new Metodo();
			metodo.setNome("sum");
			metodo.setParametros("int,int");
			metodo.setTipo("replace");
			metodo.setCodigo("{return $1 - $2; System.out.print(\"funfou\");}  ");
			

			Classe classe = new Classe();
			classe.setNome("br.com.remartins.bytechameleon.Calc");
			classe.setMetodos(new ArrayList<Metodo>());
			classe.getMetodos().add(metodo);

			ByteChameleon bc = new ByteChameleon();
			bc.setClasses(new ArrayList<Classe>());
			bc.getClasses().add(classe);

			List<ByteChameleon> list = new ArrayList<ByteChameleon>();
			list.add(bc);

			Transformer transformer = new Transformer(list);
			byte[] b = transformer.transform(this.getClass().getClassLoader(), classe.getNome(), null, null,
					null);
			
			ClassPool pool = ClassPool.getDefault();
			CtClass cc = pool.makeClass(new ByteArrayInputStream(b));
			cc.toClass();
			
			Calc calc = new Calc();
			int valor = calc.sum(2, 2);
			System.out.println(valor);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

//	private byte[] getClassfileBuffer() throws NotFoundException, IOException, CannotCompileException {
//		ClassPool pool = ClassPool.getDefault();
//		CtClass cc = pool.get(Calc.class.getName());
//		
//		return cc.toBytecode();
//	}

}
