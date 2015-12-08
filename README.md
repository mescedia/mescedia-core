# about 

mescedia is an extensible edi server (https://en.wikipedia.org/wiki/Electronic_data_interchange)
for processing most common edi message formats such as CSV, XML, UN/EDIFACT, ... using open standards.
It is build upon existing powerful, production proved open source frameworks and libraries.
 
# setup

Make sure you've downloaded and installed the smooks/unedifact project correctly ( -> https://github.com/smooks/unedifact).

To build the server run
	
	$ mvn clean install 

change to karaf-root	

	$ cd mescedia/ 

to start karaf
  
	in console: 		$ bin/karaf 
	or  in background: 	$ bin/start  

  watch the data log for detailed information
   
	$ tail -f data/log/karaf.log  
  
  
# examples
  
This setup includes the data transformation mappings defining the following example szenarios:

A retailers ERP imports INVOIC and exports ORDERS messages in CSV format. 
His suppliers ERP on this other hand XML messages. Both retailer and supplier 
exchange UN/EDIFACT (D96A) messages. 

	Supplier (XML) 		UN/EDIFACT			Retailer (CSV)
	-------------------------------------------------------
	ORDERS.xml   < 		ORDERS.edi  <    	ORDERS.csv
	INVOIC.xml   > 		INVOIC.edi  > 		INVOIC.csv

You may refer to the corresponding camel-routes ($karaf.home/deploy/edi-routes.xml) and the contents of the 
$karaf.home/mapping folder in order to understand how the message transformation works. 
		
The testfiles (ORDERS.csv and INVOIC.xml) resist in $karaf.home/testfiles/ folder. 
Copy them to their corresponding inbound destinations (CAMEL_FS/ORDERS_CSV_IN and CAMEL_FS/INVOIC_XML_IN) and watch 
the data logs and output folders for results. 
  
In order to get the full picture you may refer to the subprojects websites: 
- Apache-Karaf https://karaf.apache.org
- Apache-Camel https://camel.apache.org
- Smooks http://www.smooks.org
  
or visit project website at https://www.mescedia.com
and/or have a look at some recommended resources: https://www.mescedia.com/recommendations

enjoy
