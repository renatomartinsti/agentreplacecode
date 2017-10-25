package br.com.remartins.agentreplacecode;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.thoughtworks.xstream.XStream;
import br.com.remartins.agentreplacecode.xml.Classe;

/**
 * 
 * @author Renato Martins
 * @since 10/2017
 *
 */
public class Agent {

   private static final Logger LOGGER = Logger.getLogger(Agent.class);

	public static void premain(String agentArguments,
			Instrumentation instrumentation) {

		List<Classe> listConfig = carregarArgumentos(agentArguments);
		
      LOGGER.debug("--- INICIANDO A INSTRUMENTAÇÃO ---");
		
		instrumentation.addTransformer(new Transformer(listConfig));

      LOGGER.debug("--- FINALIZADO A INSTRUMENTAÇÃO ---");
	}

	private static List<Classe> carregarArgumentos(String agentArguments) {
		XStream xStream = new XStream();
		xStream.alias("classe", Classe.class);
		
		String[] files = agentArguments.split(",");
		List<Classe> list = new ArrayList<Classe>();
		
		for (String file : files) {
			list.add((Classe) xStream.fromXML(new File(file.replace(" ", ""))));
		}
		
		return list;
	}
	
}
