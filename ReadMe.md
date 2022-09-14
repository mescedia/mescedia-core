## mescedia :: Open Source EDI


### about

mescedia is an extensible, Java based EDI (https://en.wikipedia.org/wiki/Electronic_data_interchange) 
integration application for processing most common EDI message formats such as UN/EDIFACT, CSV, XML, Fixed-Length, ... 
using open standards (XSLT, XQuery, Java, ...). 
It is build upon existing powerful production proved Open-Source frameworks and libraries such as:
 
- Apache Camel :: https://camel.apache.org
- Apache Daffodil :: https://daffodil.apache.org/
- Smooks :: https://www.smooks.org, and many, many more ...

The aim of this project is to offer a 'free' and 'ready to use' EDI-System that contains all tools required in order  
to make EDI data-exchange highly customisable, secure, transparent, manageable and affordable using Open-Source and Open-Standards.
This project targets a wide range of interest from small business to large enterprises and service providers with high message load.


### features/roadmap

- **_Edifact-To-Xml_** and **_Xml-To-Edifact_** (vice versa) message transformation, versions **_(D93A-D19B)_**
- customized CSV, XML, fixed-length and other common message formats used in EDI are available through various frameworks
- {todo} content based dynamic message routing/processing
- {todo} customisable message validation
- {todo} extensible XML based data-transformation using open standards (XSLT, XQuery, Java, ...) 
- {todo} secure communication protocol integration: OpenAS2 (AS2), OpenSSH (SFTP), vsftpd (FTPS), nginx (HTTPS, WebDav) 
- {todo} REST interface to all affected services
- {todo} WebGUI for configuration, mapping-development, monitoring,  etc.
- {todo} automated installation scrips (ansible) and prebuild docker images
- {todo} documentation, howto's & examples 
- {planned} commercial support
    

### setup

###### database

mescedia requires a running rdbms instance (in this case mariadb/mysql, other rdbms are also possible)

{todo}

###### build mescedia:

	$ mvn clean install 

###### in case you want skip long duration tests:

	$ mvn clean install -DskipTests=true

###### run mescedia

	$ mvn camel:run

### examples/szenarios
{todo}

### License

mescedia is Open Source and licensed under the terms of the Apache License Version 2.0, or the GNU Lesser General Public License version 3.0 or later. You may use mescedia according to either of these licenses as is most appropriate for your project.

SPDX-License-Identifier: Apache-2.0 OR LGPL-3.0-or-later
