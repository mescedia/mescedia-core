
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

- [x] **_Edifact-To-Xml_** and **_Xml-To-Edifact_** (vice versa) message transformation, versions **_D.93A - D.19B, all messages_**
- [x] customized CSV, XML, fixed-length and other common message formats used in EDI are available through various frameworks
- [x] content based, dynamic message routing and processing
- [x] EDIFact message validation -> https://www.mescedia.org/tools/edifact::editor
- [x] extensible XML based data-transformation using Open-Standards (XSLT, XQuery, Java, ...) 
- [x] MariaDB/MySQL, PostgreSQL & SQLite support for system-database and external RDBMS
- [x] secure communication protocol integration:
  - [x] AS2 (see mescedia-openAS2 fork -> https://github.com/mescedia/mescedia-openAS2-Server) 
  - [x] FTPS (see vsftpd scripts/configs -> https://github.com/mescedia/mescedia-FTPS-Server)
  - [x] HTTPS/WebDAV (see nginx scripts/configs -> https://github.com/mescedia/mescedia-HTTP-Server)  
- [ ] REST interface for managing affected services
- [ ] WebGUI for configuration, development, monitoring,  etc.
- [x] automated installation scrips (ansible) and docker images (https://github.com/mescedia/mescedia-DevOps)
- [ ] documentation, howtos & various examples
- [ ] commercial support
    

### setup

###### database

mescedia requires a running rdbms instance (MariaDB/MySQL, PostgreSQL or SQLite )

{todo}

###### build mescedia:

	$ mvn clean install 

###### in case you want skip long duration tests:

	$ mvn clean install -DskipTests=true

###### run mescedia

	$ mvn camel:run

### EDIFact/Xml Conversion Examples 

mescedia uses a **dynamic data-model loading mechanism** (per type/version) which are loaded when needed and unloaded when others (types/versions) are used more often.

As a result the first conversion of an EDIFact or Xml message of  a certain type/version may take some time since its corresponding data-model needs to be loaded into memory (cache) first.

Once this has happend any other conversion of the same type/version will be superfast.

#### EDIFact-To-Xml

    $ curl -X POST -H "Content-Type: application/edifact" -d @"./src/test/resources/files/desadv-d11b.edifact" http://127.0.0.1:8585/edifact2xml

#### Xml-To-EDIFact

    $ curl -X POST -H "Content-Type: application/xml" -d @"./src/test/resources/files/desadv-d11b.xml" http://127.0.0.1:8585/xml2edifact    

### donate
 
In case you like to support development on this project you may send some $bitcoin (BTC) or $ada (ADA) (Cardano's principal currency) to the corresponding address below:
 - BTC: {todo}
 - ADA: {todo}

### License

mescedia is Open Source and licensed under the terms of the Apache License Version 2.0, or the GNU Lesser General Public License version 3.0 or later. You may use mescedia according to either of these licenses as is most appropriate for your project.

SPDX-License-Identifier: Apache-2.0 OR LGPL-3.0-or-later
