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

import org.milyn.smooks.edi.unedifact.model.UNEdifactInterchangeFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class FactoryCacheItem {
	
	private String version ;
	private UNEdifactInterchangeFactory factory ;
//	private static final Logger log = LoggerFactory.getLogger(FactoryCacheItem.class);
	
	public FactoryCacheItem(String _version , UNEdifactInterchangeFactory _factory )	{
		
		this.version = _version ;
		this.factory = _factory ;
	}
	
	public String getVersion()	{

		return this.version;
	}
	
	public UNEdifactInterchangeFactory getFactory()	{
		
		return this.factory ;
	}

}