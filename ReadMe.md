
<p align="center">
<img src="https://www.mescedia.org/img/mescedia-logo.png" alt="mescedia::Open Source EDI" />
</p>

## mescedia :: Open Source EDI

### about

mescedia is an extensible, Java based EDI (https://en.wikipedia.org/wiki/Electronic_data_interchange) 
integration application for processing most common EDI message formats such as UN/EDIFACT, CSV, XML, Fixed-Length, ... 
using open standards (XSLT, XQuery, Java, ...). 
It is build upon existing powerful production proved Open-Source frameworks and libraries such as:
 
- Apache Camel :: https://camel.apache.org
- Apache Daffodil :: https://daffodil.apache.org/
- Smooks :: https://www.smooks.org, and many, many more ...

The aim of this project is to offer a 'free' and 'ready to use' EDI-System that comes up with all tools required in order to make EDI data-exchange highly customisable, secure, transparent, affordable and easy manageable using Open-Source and Open-Standards ...

... in contrary to conventional EDI systems witch traditionally are Closed-Source, Non-Standard and usually very expensive.

### features & roadmap

- [x] **_Edifact-To-Xml_** and **_Xml-To-Edifact_** (vice versa) message transformation, versions **_D.93A - D.19B, all message types_**
- [x] customized CSV, XML, fixed-length and other common message formats used in EDI are available through various frameworks
- [ ] content based dynamic message routing/processing
- [ ] customisable message validation
- [x] extensible XML based data-transformation using Open-Standards (XSLT, XQuery, Java, ...) 
- [x] MariaDB/MySQL, PostgreSQL & SQLite support for system-database and external RDBMS
- [ ] secure communication protocol integration: OpenAS2 (AS2), OpenSSH (SFTP), vsftpd (FTPS), nginx (HTTPS, WebDav), ... 
- [ ] REST interface for managing affected services
- [ ] WebGUI for configuration, development, monitoring,  etc.
- [ ] automated installation scrips (ansible) and docker images
- [ ] documentation, howtos & various examples
    

### setup

###### database

mescedia requires a running rdbms instance (MariaDB/MySQL, PostgreSQL or SQLite = default )

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
