Byte Chameleon 
[![Build Status](https://travis-ci.org/remartins/byte-chameleon.svg?branch=master)](https://travis-ci.org/remartins/byte-chameleon) 
[![GitHub release](https://img.shields.io/badge/release-1.0.0-blue.svg)](https://github.com/remartins/byte-chameleon/releases/tag/1.0.0)
[![License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/remartins/byte-chameleon/blob/master/LICENSE)
============

JavaAgent Replace Methods with Javassist


How To
------

Add to params

```
-javaagent:byte-chameleon.jar=agent.xml
```
where <b>agent.xml</b> is

```
<byte-chameleon>
  <classes>
    <classe>
      <nome>br.com.agent.Processamento</nome>
      <metodos>
        <metodo tipo="replace">
          <nome>processar</nome>
          <parametros>java.lang.String</parametros>
          <codigo>System.out.println("Sou o novissimo processador " + $1);</codigo>
        </metodo>
      </metodos>
    </classe>
    <classe>
      <nome>br.com.agent.Processamento</nome>
      <metodos>
          <metodo tipo="before">
          <nome>processar</nome>
          <parametros>java.lang.String</parametros>
          <codigo>System.out.println("Printer before !!!");</codigo>
        </metodo>
      </metodos>
    </classe>     
  </classes>	
</byte-chameleon>  
```
