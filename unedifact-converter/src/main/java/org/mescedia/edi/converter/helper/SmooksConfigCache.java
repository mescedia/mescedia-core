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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class SmooksConfigCache {
	
	private static SmooksConfigCache instance = null;
	
	private List<SmooksConfigCacheItem> cacheItemList = new ArrayList<SmooksConfigCacheItem>(); ;
	private static final Logger log = LoggerFactory.getLogger(SmooksConfigCache.class);
	private Smooks smooks = null;
	
	private SmooksConfigCache()	{
		
	}
	
	public static  SmooksConfigCache getInstance()	{
		
		if(instance == null)
			instance = new SmooksConfigCache();
		
		return instance ;
	}
	
	public Smooks getSmooksInstanceByConfig(String configPath) throws IOException, SAXException	{
		
		for (SmooksConfigCacheItem item : this.cacheItemList)	{
			if(configPath.equals(item.getConfig()))	{
				log.debug("retrieve smooks instance from cache");
				return item.getSmooks();
			}
		}
		
		log.debug("smooks instance not found in cache - need to create a new one");
		
		this.smooks = new Smooks(configPath);		
		this.cacheItemList.add( new SmooksConfigCacheItem(this.smooks, configPath)); 
		
		return this.smooks;
	}

}
