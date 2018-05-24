Byte Chameleon [![Build Status](https://travis-ci.org/remartins/byte-chameleon.svg?branch=master)](https://travis-ci.org/remartins/byte-chameleon) 
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=br.com.remartins%3Abyte-chameleon&metric=alert_status)](https://sonarcloud.io/api/project_badges/measure?project=br.com.remartins%3Abyte-chameleon&metric=alert_status)
[![GitHub release](https://img.shields.io/badge/release-1.0.0-blue.svg)](https://github.com/remartins/byte-chameleon/releases/tag/1.0.0)
[![License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/remartins/byte-chameleon/blob/master/LICENSE)
============

JavaAgent for replace, add before or after methods with Javassist


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
    <class>
      <name>com.github.remartins.bytechameleon.Process</name>
        <methods>
	  <method type="replace">
	    <name>process</name>
	    <params>java.lang.String</params>
	    <code>return "Wow !!! Message with Instrumentation: " + $1;</code>
	</method>
	<method type="replace">
	  <name>sum</name>
	  <code>return 3 + 3;</code>
	</method>
	<method type="before">
	  <name>sum</name>
	  <code>System.out.println("Call before !!!");</code>
	</method>
	<method type="after">
	  <name>process</name>
	  <code>System.out.println("Call after !!!");</code>
	</method>
      </methods>
    </class>
  </classes>
</byte-chameleon>   
```

where tag code is described like javaassist.
