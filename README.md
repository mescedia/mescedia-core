mescedia :: Open Source EDI  
===========================

<p align="center">    
    <img src="https://img.shields.io/badge/Version-1.1.6-blue.svg" alt="mescedia-1.1.6">
    <img src="https://img.shields.io/badge/฿-1MEscEdiAkacRStk57FFb7MAd5rYSAYF7n-orange.svg" alt="donate-BTC">    
</p>


### about 

mescedia is an extensible, Java based EDI (https://en.wikipedia.org/wiki/Electronic_data_interchange) integration application
for processing most common EDI message formats such as UN/EDIFACT, CSV, XML, Fixed-Length, ... using open standards (XSLT, XQuery, Java, ...). It is build upon existing powerful, production proved Open Source frameworks and libraries such 
as: 

* *Apache-Karaf* :: https://karaf.apache.org 
* *Apache-Camel* :: https://camel.apache.org
* *Smooks*       :: https://www.smooks.org


- - -
 
### prerequisites

1. Make sure that Java is properly installed and working.
2. Download and install the smooks/unedifact project -> https://github.com/smooks/unedifact.

### setup

To build the server run
	
	$ mvn clean install

on macOS run the command with the profile option `-P macOS`

	$ mvn -P macOS clean install

change to karaf-root 	

	$ cd karaf/ 

to start karaf run
  
	$ bin/karaf 	

install mescedia features in apache-karaf
	
	admin@edi-server $ feature:repo-add file:./mescedia-features.xml
	admin@edi-server $ feature:install mescedia  
  
  this might take a while ....  
  you may want to watch the data logs for detailed information during setup 
   
	$ tail -f data/log/karaf.log
	
  copy 'edi-routes.xml' to deploy folder before running the examples below

	$ cp ./edi-routes.xml ./deploy/  
  
- - -  
  
### examples

To start the server in background run  

	$ bin/start

##### UN/EDIFACT-TO-XML and vice versa conversions

This setup includes all resources to transform UN/EDIFACT messages to XML and vice versa.  
All message types from EDIFACT version D.93A to D.13A are currently supported. 

Place your messages in the corresponding input folder and see results in your logs and output folders. 
Refer to the camel-routes in $karaf.home/deploy/edi-routes.xml for details.  

 
##### Business Message Exchange Szenarios: XML *[in-out]* UN/EDIFACT *[in-out]* CSV 
 
All data transformation mappings which are used by the example szenarios below are attached to this project.

A retailers ERP imports INVOIC and exports ORDERS messages in CSV format. 
The suppliers ERP on this other hand XML messages. Both retailer and supplier 
exchange UN/EDIFACT (D96A) messages. 

	Supplier (XML) 		UN/EDIFACT		Retailer (CSV)
	-------------------------------------------------------
	ORDERS.xml   < 		ORDERS.edi  <    	ORDERS.csv
	INVOIC.xml   > 		INVOIC.edi  > 		INVOIC.csv
		
The testfiles (ORDERS.csv and INVOIC.xml) resist in $karaf.home/testfiles/ folder. 

For an initial test run the following commands from $karaf.home  

	$ cp testfiles/INVOIC.xml CAMEL_FS/INVOIC_XML_IN/
	$ cp testfiles/ORDERS.csv CAMEL_FS/ORDERS_CSV_IN/

and watch the logs and output folders for details on each interim and final message format used during processing.
 

#### message routing and processing 

mescedia comes with some basic message routing and message processing examples.

This requires some basic knowledge about Apache-Camel since this framework offers a lot of different techniques 
for message processing like XSLT, XQuery, custom Java-Processors, and more ...

For an initial impression you may refer to the corresponding camel-routes in $karaf.home/deploy/edi-routes.xml and 
the contents of the $karaf.home/mapping folder which contains the message mappings used in these examples.
  
 
Enjoy

---
