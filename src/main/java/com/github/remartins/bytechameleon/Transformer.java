
package com.github.remartins.bytechameleon;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.github.remartins.bytechameleon.parse.xml.ByteChameleon;
import com.github.remartins.bytechameleon.parse.xml.Clazz;
import com.github.remartins.bytechameleon.parse.xml.Method;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

/**
 * 
 * @author Renato Martins
 * @since 10/2017
 *
 */
public class Transformer implements ClassFileTransformer {

	private static final Logger LOGGER = Logger.getLogger(Transformer.class);

	private Map<String, Clazz> mapClasse;

	public Transformer(List<ByteChameleon> list) {

		this.mapClasse = new HashMap<String, Clazz>();

		for (ByteChameleon bc : list) {
			for (Clazz c : bc.getClasses()) {
				mapClasse.put(c.getName(), c);
			}
		}
	}

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

		String classNameFinal = className.replace('/', '.');
		byte[] retorno = classfileBuffer;
		

		ClassPool cp = ClassPool.getDefault();
		cp.appendClassPath(new LoaderClassPath(loader));

		Iterator<String> iterator = this.mapClasse.keySet().iterator();
		while (iterator.hasNext()) {
			String classNameParam = iterator.next();
			if (classNameFinal.equals(classNameParam)) {
				Clazz classe = this.mapClasse.get(classNameFinal);
				this.mapClasse.remove(classNameParam);
				return instrumentalize(cp, classNameFinal, retorno, classe);
			}
		}

		return retorno;
	}

	private byte[] instrumentalize(ClassPool cp, String classNameFinal, byte[] retorno, Clazz classe) {
		try {
			CtClass cc = cp.get(classNameFinal);
			CtMethod ctMethod = null;

			for (Method metodo : classe.getMethods()) {

				if (metodo.getParams() != null) {

					ctMethod = getMethodWithParams(cp, cc, metodo);
				} else {
					ctMethod = getMethod(cc, metodo);
				}

				if (metodo.getType() == null || metodo.getType().trim().equalsIgnoreCase("replace")) {
					ctMethod.setBody(metodo.getCode());
				} else if (metodo.getType().trim().equalsIgnoreCase("before")) {
					ctMethod.insertBefore(metodo.getCode());
				} else if (metodo.getType().trim().equalsIgnoreCase("after")) {
					ctMethod.insertAfter(metodo.getCode());
				}
			}

			cc.detach();

			retorno = cc.toBytecode();

		} catch (Exception e) {
			LOGGER.error(e);
		}
		return retorno;
	}

	private CtMethod getMethod(CtClass cc, Method metodo) throws NotFoundException {
		return cc.getDeclaredMethod(metodo.getName());
	}

	private CtMethod getMethodWithParams(ClassPool cp, CtClass cc, Method metodo) throws NotFoundException {
		List<CtClass> listCtClass = new ArrayList<CtClass>();

		/** create a array with params like get method form reflection **/
		for (String parametro : metodo.getParams().split(",")) {
			listCtClass.add(cp.get(parametro.replace(" ", "")));
		}

		CtClass[] arrays = new CtClass[listCtClass.size()];
		listCtClass.toArray(arrays);

		return cc.getDeclaredMethod(metodo.getName(), arrays);
	}

}
