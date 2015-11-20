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

package org.mescedia.edi.converter.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.milyn.edi.unedifact.d93a.D93AInterchangeFactory;
import org.milyn.edi.unedifact.d94a.D94AInterchangeFactory;
import org.milyn.edi.unedifact.d94b.D94BInterchangeFactory;
import org.milyn.edi.unedifact.d95a.D95AInterchangeFactory;
import org.milyn.edi.unedifact.d95b.D95BInterchangeFactory;
import org.milyn.edi.unedifact.d96a.D96AInterchangeFactory;
import org.milyn.edi.unedifact.d96b.D96BInterchangeFactory;
import org.milyn.edi.unedifact.d97a.D97AInterchangeFactory;
import org.milyn.edi.unedifact.d97b.D97BInterchangeFactory;
import org.milyn.edi.unedifact.d98a.D98AInterchangeFactory;
import org.milyn.edi.unedifact.d98b.D98BInterchangeFactory;
import org.milyn.edi.unedifact.d99a.D99AInterchangeFactory;
import org.milyn.edi.unedifact.d99b.D99BInterchangeFactory;
import org.milyn.edi.unedifact.d00a.D00AInterchangeFactory;
import org.milyn.edi.unedifact.d00b.D00BInterchangeFactory;
import org.milyn.edi.unedifact.d01a.D01AInterchangeFactory;
import org.milyn.edi.unedifact.d01b.D01BInterchangeFactory;
import org.milyn.edi.unedifact.d01c.D01CInterchangeFactory;
import org.milyn.edi.unedifact.d02a.D02AInterchangeFactory;
import org.milyn.edi.unedifact.d02b.D02BInterchangeFactory;
import org.milyn.edi.unedifact.d03a.D03AInterchangeFactory;
import org.milyn.edi.unedifact.d03b.D03BInterchangeFactory;
import org.milyn.edi.unedifact.d04a.D04AInterchangeFactory;
import org.milyn.edi.unedifact.d04b.D04BInterchangeFactory;
import org.milyn.edi.unedifact.d05a.D05AInterchangeFactory;
import org.milyn.edi.unedifact.d05b.D05BInterchangeFactory;
import org.milyn.edi.unedifact.d06a.D06AInterchangeFactory;
import org.milyn.edi.unedifact.d06b.D06BInterchangeFactory;
import org.milyn.edi.unedifact.d07a.D07AInterchangeFactory;
import org.milyn.edi.unedifact.d07b.D07BInterchangeFactory;
import org.milyn.edi.unedifact.d08a.D08AInterchangeFactory;
import org.milyn.edi.unedifact.d08b.D08BInterchangeFactory;
import org.milyn.edi.unedifact.d09a.D09AInterchangeFactory;
import org.milyn.edi.unedifact.d09b.D09BInterchangeFactory;
import org.milyn.edi.unedifact.d10a.D10AInterchangeFactory;
import org.milyn.edi.unedifact.d10b.D10BInterchangeFactory;
import org.milyn.edi.unedifact.d11a.D11AInterchangeFactory;
import org.milyn.edi.unedifact.d11b.D11BInterchangeFactory;
import org.milyn.edi.unedifact.d12a.D12AInterchangeFactory;
import org.milyn.edi.unedifact.d12b.D12BInterchangeFactory;
import org.milyn.edi.unedifact.d13a.D13AInterchangeFactory;
import org.milyn.smooks.edi.unedifact.model.UNEdifactInterchangeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;


public class FactoryCache {
	
	private List<FactoryCacheItem> cacheItemList = new ArrayList<FactoryCacheItem>(); ;
	private static FactoryCache instance = null;
	private static final Logger log = LoggerFactory.getLogger(FactoryCacheItem.class);
	
	private FactoryCache() throws IOException, SAXException {
		
		this.initialiseCache();		
	}
	
	public static FactoryCache getInstance() throws IOException, SAXException	{
		
		if (instance == null)	{
			instance = new FactoryCache();
			log.info("create FactoryCache instance ") ;
		}

		return instance ;
	}
	
	public UNEdifactInterchangeFactory getFactoryByVersion(String version) throws Exception	{
		
		for (FactoryCacheItem item : this.cacheItemList)	{
			
			if(version.toUpperCase().equals(item.getVersion()))	{
				return item.getFactory();
			}
		}
		throw new Exception("ItemFactory not found by version: " + version) ;
	}

	private void initialiseCache() throws IOException, SAXException 	{

		try {
		
			this.cacheItemList.add(new FactoryCacheItem("D93A", D93AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D94A", D94AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D94B", D94BInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D95A", D95AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D95B", D95BInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D96A", D96AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D96B", D96BInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D97A", D97AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D97B", D97BInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D98A", D98AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D98B", D98BInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D99A", D99AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D99B", D99BInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D00A", D00AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D00B", D00BInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D01A", D01AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D01B", D01BInterchangeFactory.getInstance())) ;
			
			this.cacheItemList.add(new FactoryCacheItem("D01C", D01CInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D02A", D02AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D02B", D02BInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D03A", D03AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D03B", D03BInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D04A", D04AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D04B", D04BInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D05A", D05AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D05B", D05BInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D06A", D06AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D06B", D06BInterchangeFactory.getInstance())) ;		
			this.cacheItemList.add(new FactoryCacheItem("D07A", D07AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D07B", D07BInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D08A", D08AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D08B", D08BInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D09A", D09AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D09B", D09BInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D10A", D10AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D10B", D10BInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D11A", D11AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D11B", D11BInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D12A", D12AInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D12B", D12BInterchangeFactory.getInstance())) ;
			this.cacheItemList.add(new FactoryCacheItem("D13A", D13AInterchangeFactory.getInstance())) ;
			
			log.info("CacheItemList initialised ...");
		}
		catch (SAXException ex) {
			log.error(this.getStackTrace(ex));
		     throw ex ;
		}
		catch (IOException ex) {
			log.error( this.getStackTrace(ex));
		     throw ex;
		}
	}
	
	private String getStackTrace(final Throwable throwable) {
		
	     final StringWriter sw = new StringWriter();
	     final PrintWriter pw = new PrintWriter(sw, true);
	     throwable.printStackTrace(pw);
	     
	     return sw.getBuffer().toString();
	}

}
