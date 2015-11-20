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

import org.milyn.Smooks;

public class SmooksConfigCacheItem {
	
	private String configFilePath = null;
	private Smooks smooks = null;
	
	public SmooksConfigCacheItem(Smooks _smooks, String _config)	{
		
		this.smooks = _smooks ;
		this.configFilePath = _config;
	}
	
	public String getConfig()	{
	
		return this.configFilePath;
	}
	
	public Smooks getSmooks()	{
		
		return this.smooks;
	}
	

}
