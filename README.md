## Introduction
**jripple** is a java websocket client that connect to the [ripple] (https://ripple.com) server 

Both asynchronous and synchronous API are supported.

## Getting started
	
### Get source and build it

    git clone git@github.com:FredericHeem/jripple.git
	cd jripple
	mvn install	

## Operations

[account_info] (https://ripple.com/wiki/RPC_API#account_info)
    
[account_lines] (https://ripple.com/wiki/RPC_API#account_lines)

## Usage
	
See [RippleWsClientAccountInfoTest.java] (https://github.com/FredericHeem/jripple/blob/master/src/test/java/org/opencoin/client/RippleWsClientAccountInfoTest.java) for unitest related to the account_info command
	
See [RippleWsClientAccountLinesTest.java] (https://github.com/FredericHeem/jripple/blob/master/src/test/java/org/opencoin/client/RippleWsClientAccountLinesTest.java) for unitest related to the account_lines command
	           
## Development

**jripple** is built with maven, developed with eclipse, tested with junit, statically analyzed, code covered, and continuously integrated: [![Build Status](https://travis-ci.org/FredericHeem/jripple.png)](https://travis-ci.org/FredericHeem/jripple)
    
### Generate the eclipse project

    mvn eclipse:eclipse

### Run the unitest

    mvn test

### Run static analysis with [pmd] (http://pmd.sourceforge.net/)

    mvn pmd:pmd   
     
View the report at target/site/pmd.html

### Run static analysis [findbugs] (http://findbugs.sourceforge.net/)

    mvn site:site
    
View the report at target/site/findbugs.html    

### Generate the code coverage report with [cobertura] (http://cobertura.sourceforge.net/)

    mvn cobertura:cobertura
    
View report at target/site/cobertura/index.html 
	                                                         
## Contributors

[FredericHeem](https://github.com/FredericHeem)



	

	
	






