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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.mescedia.edi.converter.helper.SmooksReaderCache;
import org.milyn.Smooks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;


public class UnEDIfact2XmlConverter {
	
	private int start = 0;
	private Smooks smooks = null;
	private StringWriter writer = null;
	
	private String messageOut = null;
	private String messageType = null;
	
	private static UnEDIfact2XmlConverter instance = null;
	private static final Logger log = LoggerFactory.getLogger(UnEDIfact2XmlConverter.class);

	
	public static UnEDIfact2XmlConverter getInstance() throws IOException, SAXException, ParserConfigurationException	{
		
		if(instance == null)
			instance = new UnEDIfact2XmlConverter();
		
		return instance;
	}

	public String convert2Xml(String messageVersion, String messageIn, long startL ) throws IOException, SAXException	{
		
		this.smooks = SmooksReaderCache.getInstance().getSmooksInstanceByConfig("urn:org.milyn.edi.unedifact:"+messageVersion+"-mapping:*");
		
	    this.writer = new StringWriter();
	    this.smooks.filterSource(new StreamSource(new ByteArrayInputStream( messageIn.getBytes() )), new StreamResult(writer));
	    		
	    this.smooks.close();
	    	    
	    this.messageOut =  writer.toString();
	    
	    this.messageType = this.readSubString(this.readSubString(messageOut,
	    		"<env:messageIdentifier>","</env:messageIdentifier>",0),
	    		"<env:id>","</env:id>",0);
	    
	    log.info("processed message [in:"+messageIn.length()+"; out:"+messageOut.length()+" bytes] ["+ this.messageType  +
	    		"-" + messageVersion.toUpperCase() +"]  converted in " +String.valueOf( ((System.currentTimeMillis()- startL)) ) + " ms");
	    
	    return this.messageOut;
	}
	
	private String readSubString(String xml, String strStart, String strEnd, int offset)	{
		
		start = xml.indexOf(strStart,offset) ;
		if(start==-1)
			return null;
		
		return  xml.substring(start+ strStart.length(), xml.indexOf(strEnd,offset) ) ;
	}
}
