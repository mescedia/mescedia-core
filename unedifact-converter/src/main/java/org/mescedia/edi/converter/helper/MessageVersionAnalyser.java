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

public class MessageVersionAnalyser {
	
	private static boolean IsEancomMessage(String msg)	{
				
		return (msg.indexOf("UNB") > -1) && (msg.indexOf("BGM") > -1) ;
	}
	
	public static UnEDIfactVersion GetEancomVersion(String msg) throws Exception	{
		
		if(msg.length() > 400)
			msg = msg.substring(0,399) ;
		
		if (!IsEancomMessage(msg))	{
			throw new Exception("Content not allowed !") ;
		}
		
		if(msg.indexOf("D:00A:UN") > 0)
			return UnEDIfactVersion.D00A;
		else if(msg.indexOf("D:00B:UN") > 0)
			return UnEDIfactVersion.D00B;
		else if(msg.indexOf("D:01A:UN") > 0)
			return UnEDIfactVersion.D01A;
		else if(msg.indexOf("D:01B:UN") > 0)
			return UnEDIfactVersion.D01B;
		else if(msg.indexOf("D:01C:UN") > 0)
			return UnEDIfactVersion.D01C;
		else if(msg.indexOf("D:02A:UN") > 0)
			return UnEDIfactVersion.D02A;		
		else if(msg.indexOf("D:02B:UN") > 0)
			return UnEDIfactVersion.D02B;
		else if(msg.indexOf("D:03A:UN") > 0)
			return UnEDIfactVersion.D03A;
		else if(msg.indexOf("D:03B:UN") > 0)
			return UnEDIfactVersion.D03B;
		else if(msg.indexOf("D:04A:UN") > 0)
			return UnEDIfactVersion.D04A;
		else if(msg.indexOf("D:04B:UN") > 0)
			return UnEDIfactVersion.D04B;
		else if(msg.indexOf("D:05A:UN") > 0)
			return UnEDIfactVersion.D05A;
		else if(msg.indexOf("D:05B:UN") > 0)
			return UnEDIfactVersion.D05B;
		else if(msg.indexOf("D:06A:UN") > 0)
			return UnEDIfactVersion.D06A;
		else if(msg.indexOf("D:06B:UN") > 0)
			return UnEDIfactVersion.D06B;
		else if(msg.indexOf("D:07A:UN") > 0)
			return UnEDIfactVersion.D07A;
		else if(msg.indexOf("D:07B:UN") > 0)
			return UnEDIfactVersion.D07B;
		else if(msg.indexOf("D:08A:UN") > 0)
			return UnEDIfactVersion.D08A;
		else if(msg.indexOf("D:08B:UN") > 0)
			return UnEDIfactVersion.D08B;
		else if(msg.indexOf("D:09A:UN") > 0)
			return UnEDIfactVersion.D09A;
		else if(msg.indexOf("D:09B:UN") > 0)
			return UnEDIfactVersion.D09B;
		else if(msg.indexOf("D:10A:UN") > 0)
			return UnEDIfactVersion.D10A;		
		else if(msg.indexOf("D:10B:UN") > 0)
			return UnEDIfactVersion.D10B;
		else if(msg.indexOf("D:11A:UN") > 0)
			return UnEDIfactVersion.D11A;		
		else if(msg.indexOf("D:11B:UN") > 0)
			return UnEDIfactVersion.D11B;
		else if(msg.indexOf("D:12A:UN") > 0)
			return UnEDIfactVersion.D12A;		
		else if(msg.indexOf("D:12B:UN") > 0)
			return UnEDIfactVersion.D12B;
		else if(msg.indexOf("D:13A:UN") > 0)
			return UnEDIfactVersion.D13A;		
		else if(msg.indexOf("D:93A:UN") > 0)
			return UnEDIfactVersion.D93A ;
		else if(msg.indexOf("D:94A:UN") > 0)
			return UnEDIfactVersion.D94A ;		
		else if(msg.indexOf("D:94B:UN") > 0)
			return UnEDIfactVersion.D94B ;
		else if(msg.indexOf("D:95A:UN") > 0)
			return UnEDIfactVersion.D95A ;		
		else if(msg.indexOf("D:95B:UN") > 0)
			return UnEDIfactVersion.D95B ;
		else if(msg.indexOf("D:96A:UN") > 0)
			return UnEDIfactVersion.D96A ;		
		else if(msg.indexOf("D:96B:UN") > 0)
			return UnEDIfactVersion.D96B ;
		else if(msg.indexOf("D:97A:UN") > 0)
			return UnEDIfactVersion.D97A ;		
		else if(msg.indexOf("D:97B:UN") > 0)
			return UnEDIfactVersion.D97B ;
		else if(msg.indexOf("D:98A:UN") > 0)
			return UnEDIfactVersion.D98A ;		
		else if(msg.indexOf("D:98B:UN") > 0)
			return UnEDIfactVersion.D98B ;
		else if(msg.indexOf("D:99A:UN") > 0)
			return UnEDIfactVersion.D99A ;		
		else if(msg.indexOf("D:99B:UN") > 0)
			return UnEDIfactVersion.D99B ;
		
		throw new Exception("This version is currently not supported !") ;
			
	}

}
