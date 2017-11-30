
package br.com.remartins.bytechameleon;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import br.com.remartins.bytechameleon.xml.ByteChameleon;
import br.com.remartins.bytechameleon.xml.Classe;
import br.com.remartins.bytechameleon.xml.Metodo;
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

	private Map<String, Classe> mapClasse;

	public Transformer(List<ByteChameleon> list) {

		this.mapClasse = new HashMap<String, Classe>();

		for (ByteChameleon bc : list) {
			for (Classe c : bc.getClasses()) {
				mapClasse.put(c.getNome(), c);
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
				Classe classe = this.mapClasse.get(classNameFinal);
				this.mapClasse.remove(classNameParam);
				return instrumentaliza(cp, classNameFinal, retorno, classe);
			}
		}

		return retorno;
	}

	private byte[] instrumentaliza(ClassPool cp, String classNameFinal, byte[] retorno, Classe classe) {
		try {
			CtClass cc = cp.get(classNameFinal);
			CtMethod ctMethod = null;

			for (Metodo metodo : classe.getMetodos()) {

				if (metodo.getParametros() != null) {

					ctMethod = getMethodWithParams(cp, cc, metodo);
				} else {
					ctMethod = getMethod(cc, metodo);
				}

				if (metodo.getTipo() == null || metodo.getTipo().trim().equalsIgnoreCase("replace")) {
					ctMethod.setBody(metodo.getCodigo());
				} else if (metodo.getTipo().trim().equalsIgnoreCase("before")) {
					ctMethod.insertBefore(metodo.getCodigo());
				} else if (metodo.getTipo().trim().equalsIgnoreCase("after")) {
					ctMethod.insertAfter(metodo.getCodigo());
				}
			}

			retorno = cc.toBytecode();

		} catch (Exception e) {
			LOGGER.error(e);
		}
		return retorno;
	}

	private CtMethod getMethod(CtClass cc, Metodo metodo) throws NotFoundException {
		return cc.getDeclaredMethod(metodo.getNome());
	}

	private CtMethod getMethodWithParams(ClassPool cp, CtClass cc, Metodo metodo) throws NotFoundException {
		CtMethod m;
		List<CtClass> listCtClass = new ArrayList<CtClass>();

		/** create a array with params like get method form reflection **/
		for (String parametro : metodo.getParametros().split(",")) {
			listCtClass.add(cp.get(parametro.replace(" ", "")));
		}

		CtClass[] arrays = new CtClass[listCtClass.size()];
		listCtClass.toArray(arrays);

		m = cc.getDeclaredMethod(metodo.getNome(), arrays);
		return m;
	}

}
