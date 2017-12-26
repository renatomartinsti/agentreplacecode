package br.com.remartins.bytechameleon;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class Main {

	public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException, InstantiationException, IllegalAccessException {
//		Calc c1 = new Calc();
//		c1.test();
		
		ClassPool cp = ClassPool.getDefault();
		CtClass cc = cp.get("br.com.remartins.bytechameleon.Calc");
		
		CtMethod cm = cc.getDeclaredMethod("test");
		cm.setBody("System.out.println(\"esse é novo.\");");
		
		cc.toClass();
		
		
		Calc c2 = new Calc(); //(Calc) cc.toClass().newInstance();
		c2.test();
		
	}
	
}
