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

package org.mescedia.edi.converter.xml2unEdifact;

import java.io.File;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mescedia.edi.converter.helper.StackTrace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Xml2UnEDIfactProcessor implements Processor {

	private String interchangeControlReference = null ;
	private String ediMessage = null;
	private String inMessage = null;
	private String bindingconfigPath = null;
	private File file = null;
	
	private static final Logger log = LoggerFactory.getLogger(Xml2UnEDIfactProcessor.class);

	public synchronized void process(Exchange exchange) throws Exception {
		
		try {
						
			if (exchange.getIn().getHeaders().get("X_MESCEDIA_interchangeControlReference") == null) 	
				this.interchangeControlReference = null;
			else
				this.interchangeControlReference = exchange.getIn().getHeaders().get("X_MESCEDIA_interchangeControlReference").toString();
			
			if (exchange.getIn().getHeaders().get("X_MESCEDIA_bindingconfigPath") == null) 	
				throw new Exception("bindingconfigPath not set - expected it in camel-header X_MESCEDIA_bindingconfigPath ") ;
			else
				this.bindingconfigPath = exchange.getIn().getHeaders().get("X_MESCEDIA_bindingconfigPath").toString();
			
			this.inMessage = exchange.getIn().getBody(String.class) ;		
							
			this.file = new File(this.bindingconfigPath);
			if(this.file.exists() && !this.file.isDirectory()) {
				log.error("bindingconfigPath not found:" + this.bindingconfigPath);
				throw new Exception("bindingconfigPath not found:" + this.bindingconfigPath) ;
			}
					
			this.ediMessage = Xml2UnEDIfactConverter.getInstance().convertToUNEdifact( this.inMessage, this.bindingconfigPath, this.interchangeControlReference);
			
			exchange.getIn().setBody(this.ediMessage);
		
		}
		catch (Exception ex)	{
			
			log.error(ex.getMessage());
			log.error( StackTrace.getStackTrace(ex));
			
			throw new Exception(ex.getMessage()) ;
		}
	} 
}