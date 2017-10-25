package br.com.remartins.agentreplacecode;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.remartins.agentreplacecode.xml.Classe;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class Transformer implements ClassFileTransformer {

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

		if (this.mapClasse.containsKey(classNameFinal)) {
			Classe classe = this.mapClasse.get(classNameFinal);

			try {
				ClassPool cp = ClassPool.getDefault();

				//cp.appendClassPath(new LoaderClassPath(loader));

				CtClass cc = cp.get(classNameFinal);
				CtMethod m = null;
				
				if (classe.getMetodo().getParametros() != null) {
					String[] parametros = classe.getMetodo().getParametros().split(",");
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
				
				//m.insertBefore("{ System.out.println(\"Hello.say():\"); }");
				
				m.setBody(classe.getMetodo().getCodigo());
				
				retorno = cc.toBytecode();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// System.out.println(classNameFinal);

		return retorno;

	}

}
