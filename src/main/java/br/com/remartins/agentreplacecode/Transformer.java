
package br.com.remartins.agentreplacecode;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import br.com.remartins.agentreplacecode.xml.Classe;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

/**
 * 
 * @author Renato Martins
 * @since 10/2017
 *
 */
public class Transformer implements ClassFileTransformer {

	private static final Logger LOGGER = Logger.getLogger(Transformer.class);

	private Map<String, Classe> mapClasse;

	public Transformer(List<Classe> list) {

		this.mapClasse = new HashMap<String, Classe>();

		for (Classe c : list) {
			mapClasse.put(c.getNome(), c);
		}
	}

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

		String classNameFinal = className.replace('/', '.');
		byte[] retorno = classfileBuffer;

		ClassPool cp = ClassPool.getDefault();
		cp.appendClassPath(new LoaderClassPath(loader));

		if (this.mapClasse.containsKey(classNameFinal)) {
			Classe classe = this.mapClasse.get(classNameFinal);
			classe.setInstrumentalizado(true);
			retorno = instrumentaliza(cp, classNameFinal, retorno, classe);
		}

		for (Classe cls : this.mapClasse.values()) {
			if (!cls.isInstrumentalizado()) {
				cls.setInstrumentalizado(true);
				instrumentaliza(cp, cls.getNome(), retorno, cls);
			}
		}

		return retorno;
	}

	private byte[] instrumentaliza(ClassPool cp, String classNameFinal, byte[] retorno, Classe classe) {
		try {
			CtClass cc = cp.get(classNameFinal);
			CtMethod m = null;

			if (classe.getMetodo().getParametros() != null) {

				List<CtClass> listCtClass = new ArrayList<CtClass>();

				for (String parametro : classe.getMetodo().getParametros().split(",")) {
					listCtClass.add(cp.get(parametro.replace(" ", "")));
				}
				CtClass[] arrays = new CtClass[listCtClass.size()];
				listCtClass.toArray(arrays);

				m = cc.getDeclaredMethod(classe.getMetodo().getNome(), arrays);
			} else {
				m = cc.getDeclaredMethod(classe.getMetodo().getNome());
			}

			m.setBody(classe.getMetodo().getCodigo());

			retorno = cc.toBytecode();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return retorno;
	}

}
