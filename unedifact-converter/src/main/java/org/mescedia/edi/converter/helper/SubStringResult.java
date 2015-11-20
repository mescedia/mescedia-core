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

public class SubStringResult {
	
	private String messagePart ;
	private String message ;
	private int offset;
	
	public SubStringResult(String _msg, int _off)	{
		
		this.message =_msg;
		this.offset = _off;
	}
	
	public String getMessage()	{
		
		return this.message;
	}
	
	public int getOffset()	{
		
		return this.offset;
	}
	
	public void setOffset(int _off)	{
		
		this.offset = _off ;
	}

	public String getMessagePart() {
		return messagePart;
	}

	public void setMessagePart(String messagePart) {
		this.messagePart = messagePart;
	}

}
