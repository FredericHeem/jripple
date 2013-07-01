## Introduction
**jripple** is a java websocket client to connect to the [ripple] (https://www.ripple.com) server 

Both asynchronous and synchronous API are supported.

## Getting started
	
### Get source and build it

    git clone git@github.com:FredericHeem/jripple.git
	cd jripple
	mvn install	

## Operation

  account_info   
  account_lines

## Usage
	
	See src/test/java/org/opencoin/client/RippleWsClientAccountInfoTest.java for unitest related to the account_info command
	See src/test/java/org/opencoin/client/RippleWsClientAccountLinesTest.java for unitest related to the account_lines command
	           
## Development

### Generate the eclipse project

    mvn eclipse:eclipse

### Run the unitest

    mvn test
    
### Generate the code coverage report

   mvn coberture:cobertura 
	                                                         
## Contributors

[FredericHeem](https://github.com/FredericHeem)



	

	
	






