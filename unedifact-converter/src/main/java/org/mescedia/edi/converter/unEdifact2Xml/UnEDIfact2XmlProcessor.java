/*
	mescedia - Copyright (C) 2014 - 2015

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public License (version 3) 
	as published by the Free Software Foundation.
	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
	See the  GNU Lesser General Public License for more details:
	https://www.gnu.org/licenses/lgpl-3.0.txt
*/

/*
  @author Michael Kassnel 
  @web    https://www.mescedia.com  
*/

package org.mescedia.edi.converter.unEdifact2Xml;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mescedia.edi.converter.helper.MessageVersionAnalyser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnEDIfact2XmlProcessor implements Processor {
	
	private String messageIn = null;
	private String messageOut = null;
	private String messageVersion = null;
	private long startL = 0;
	private static final Logger log = LoggerFactory.getLogger(UnEDIfact2XmlProcessor.class);
	
	public synchronized void process(Exchange exchange) throws Exception  {
		
		try {
			this.startL = System.currentTimeMillis();			
			this.messageIn = exchange.getIn().getBody(String.class) ;
			this.messageVersion = MessageVersionAnalyser.GetEancomVersion(messageIn).toString().toLowerCase() ;
			
			this.messageOut = UnEDIfact2XmlConverter.getInstance().convert2Xml(this.messageVersion, this.messageIn, this.startL);
		    
		    exchange.getIn().setBody(messageOut);
		}
		catch (Exception ex)	{
			log.error(ex.getMessage());			
			throw new Exception(ex.getMessage());
		}
	}
}
