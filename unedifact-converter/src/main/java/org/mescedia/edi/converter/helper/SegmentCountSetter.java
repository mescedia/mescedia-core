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

public class SegmentCountSetter {
	
	private String message = "";
	private String newPart = "";
	private String delimSegment = "";
	private String delimField = "";
	private int defaultCounter;
	private SubStringResult result;
	
	public SegmentCountSetter()	{
	}

	public String getMessage() {
		
		return message;
	}
		
	public void process(String _message,String _delimSegment, String _delimField, int _defaultCounter) throws Exception {
		
		this.message = _message;
		this.delimField = _delimField;
		this.delimSegment = _delimSegment;
		this.defaultCounter = _defaultCounter;
		this.result = new SubStringResult(this.message,0) ;
    	
    	while ((result=this.getEdiPartUnh2Unt(result)) != null)	{
    		
    		this.newPart = result.getMessagePart().replace("UNT"+this.delimField+String.valueOf(this.defaultCounter), 
    				"UNT" + this.delimField + String.valueOf(this.getSegmentCounter(result.getMessagePart())));
    		
    		this.message = this.message.replace(result.getMessagePart(), newPart);    		    		
    	}
	}
	
	 private SubStringResult getEdiPartUnh2Unt(SubStringResult result)	{
			
		 int start = result.getMessage().indexOf("UNH"+this.delimField,result.getOffset()) ;
		 if(start==-1)
			return null;
		
		 int endSUNT =  result.getMessage().indexOf("UNT" + this.delimField,result.getOffset());
		 int endEUNT = result.getMessage().indexOf(this.delimSegment,endSUNT)+1;
		
		 result.setOffset(endEUNT);
		 result.setMessagePart(result.getMessage().substring(start,endEUNT));
		
		 return  result ;
	 }
	 
	 private int getSegmentCounter(String unh2unb){
		
		 int cnt = 0, pos = 0;
		 while ((pos = unh2unb.indexOf('\'', pos+1)) > -1)	{
			cnt++;
		 }	
		 return cnt;
	 }
}
