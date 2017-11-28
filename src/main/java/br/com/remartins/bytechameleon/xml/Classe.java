
package br.com.remartins.bytechameleon.xml;

import javax.xml.bind.annotation.XmlTransient;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Classe")
public class Classe
{

   @XmlTransient
   private boolean instrumentalizado = false;

   private String nome;
   private Metodo metodo;

   public boolean isInstrumentalizado()
   {
      return instrumentalizado;
   }

   public void setInstrumentalizado(boolean instrumentalizado)
   {
      this.instrumentalizado = instrumentalizado;
   }

   public String getNome()
   {
      return nome;
   }

   public void setNome(String nome)
   {
      this.nome = nome;
   }

   public Metodo getMetodo()
   {
      return metodo;
   }

   public void setMetodo(Metodo metodo)
   {
      this.metodo = metodo;
   }

}
