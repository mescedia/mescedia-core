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
import java.util.ArrayList;
import java.util.List;

import org.milyn.Smooks;
import org.milyn.smooks.edi.unedifact.UNEdifactReaderConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class SmooksReaderCache {
	
	private static SmooksReaderCache instance = null;
	
	private List<SmooksReaderCacheItem> cacheItemList = new ArrayList<SmooksReaderCacheItem>(); ;
	private static final Logger log = LoggerFactory.getLogger(SmooksReaderCache.class);
	private Smooks smooks = null;
	
	private SmooksReaderCache()	{
		
	}
	
	public static  SmooksReaderCache getInstance()	{
		
		if(instance == null)
			instance = new SmooksReaderCache();
		
		return instance ;
	}
	
	public Smooks getSmooksInstanceByConfig(String readerConfig) throws IOException, SAXException	{
		
		for (SmooksReaderCacheItem item : this.cacheItemList)	{
			if(readerConfig.equals(item.getReaderConfig()))	{
				log.debug("retrieve smooks reader instance from cache");
				return item.getSmooks();
			}
		}
		
		log.debug("smooks reader instance not found in cache - need to create a new one");
		
		this.smooks = new Smooks();
		this.smooks.setReaderConfig(new UNEdifactReaderConfigurator(readerConfig));		
		this.cacheItemList.add( new SmooksReaderCacheItem(this.smooks, readerConfig)); 
		
		return this.smooks;
	}

}
