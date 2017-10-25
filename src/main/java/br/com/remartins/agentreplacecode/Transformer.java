package br.com.remartins.agentreplacecode;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.remartins.agentreplacecode.xml.deprecated.Config;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

public class Transformer implements ClassFileTransformer {

	private Map<String, Config> mapConfig;

	public Transformer(List<Config> listConfig) {

		this.mapConfig = new HashMap<String, Config>();

		for (Config c : listConfig) {
			mapConfig.put(c.getClasseAlvo().getNome(), c);
		}
	}

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

		String classNameFinal = className.replace('/', '.');
		byte[] retorno = classfileBuffer;

		if (this.mapConfig.containsKey(classNameFinal)) {
			Config config = this.mapConfig.get(classNameFinal);

			try {
				ClassPool cp = ClassPool.getDefault();

				//cp.appendClassPath(new LoaderClassPath(loader));

				CtClass cc = cp.get(classNameFinal);
				CtMethod m = cc.getDeclaredMethod(config.getClasseAlvo().getMetodo());
				//m.insertBefore("{ System.out.println(\"Hello.say():\"); }");
				
				m.setBody(config.getClasseFonte().getMetodo());
				
				retorno = cc.toBytecode();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// System.out.println(classNameFinal);

		return retorno;

	}

}
