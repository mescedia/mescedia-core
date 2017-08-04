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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.mescedia.edi.converter.helper.FactoryCache;
import org.mescedia.edi.converter.helper.SegmentCountSetter;
import org.mescedia.edi.converter.helper.SmooksConfigCache;
import org.mescedia.edi.converter.helper.StackTrace;
import org.milyn.Smooks;
import org.milyn.edisax.model.internal.Delimiters;
import org.milyn.payload.JavaResult;
import org.milyn.smooks.edi.unedifact.model.UNEdifactInterchange;
import org.milyn.smooks.edi.unedifact.model.r41.UNB41;
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactInterchange41;
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactMessage41;
import org.milyn.smooks.edi.unedifact.model.r41.UNH41;
import org.milyn.smooks.edi.unedifact.model.r41.UNT41;
import org.milyn.smooks.edi.unedifact.model.r41.UNZ41;
import org.milyn.smooks.edi.unedifact.model.r41.types.DateTime;
import org.milyn.smooks.edi.unedifact.model.r41.types.MessageIdentifier;
import org.milyn.smooks.edi.unedifact.model.r41.types.Party;
import org.milyn.smooks.edi.unedifact.model.r41.types.Ref;
import org.milyn.smooks.edi.unedifact.model.r41.types.SourceIdentifier;
import org.milyn.smooks.edi.unedifact.model.r41.types.SyntaxIdentifier;
import org.milyn.smooks.edi.unedifact.model.r41.types.TransferStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class Xml2UnEDIfactConverter {
	
	private Object msgObj = null;
	private Smooks smooks = null;
	private JavaResult result = null;
	private static String smooksConfigPath = "/org/mescedia/edi/bindingconfigs/unedifact"; 
	private InputStream inputStream = null;
	private String messageVersion = null;
	private String xmlMiddlware = null;
	private List<UNEdifactMessage41> interchangeList =  new ArrayList<UNEdifactMessage41>();
	private List<UNEdifactMessage41> msgList = null;
	private String ediMessageString ;
	private SegmentCountSetter segmentCountSetter = new SegmentCountSetter();
	
	private Delimiters delimiters = null;
	private UNEdifactInterchange41 interchange =null;
	private Party sender = null;
	private Party recipient = null;
	
	private Ref recipientRef = null;
	
	private SyntaxIdentifier synID = null;
	private MessageIdentifier messageIdentifier = null;
	private long startL; 
	private long startLS;
	private String unhXml=null; 
	private String msgRefNum = null; 
	private String smooksConfig = null;
	private String message = null;
	
	private UNB41 unb = null;
	private UNZ41 unz = null; 
	private UNH41 unh = null;
	private UNT41 unt = null;
	private UNEdifactMessage41 ediMessage = null;
	private StringWriter ediOutStream = null;
	
	private  TransferStatus transferStatus = null;
	
	private SourceIdentifier subset = null;
	private SourceIdentifier scenario = null;
	private SourceIdentifier implementationGuideline = null;
	
	private String controlRef = null;
	private String interchangeMsg = null;
	private String messageTag = null;
	private String messageNamespace = null;
	private String docIdXpath = null;
	private String docID = null;
	private String version = null;
	
	private String appRef = null ;
	
	private static org.milyn.smooks.edi.unedifact.model.r41.types.DateTime dt ;
	
	private SecureRandom random = null;
	private DocumentBuilderFactory documentFactory = null;
	private DocumentBuilder builder =  null;
	private Document document =  null;
	
	private Pattern patternL = null;
	private Matcher matcherL = null;
	private Pattern patternUNH = null;
	private Matcher matcherUNH = null;
	private Pattern patternUNB = null;
	private Matcher matcherUNB = null;
	private XPath xpathUNH = null;
	private XPath xpathUNH1 = null;
	private XPath xpathUNB = null; 
	private XPathExpression xpathExpressionUNH = null;
	private XPathExpression xpathExpressionUNH1 = null;
	private XPathExpression xpathExpressionUNB = null ;
	
	private static NamespaceContext namespaceContext = null;
	private static String currentMessageVersion = null;
	private static String currentMessageType = null;	
	private static final Logger log = LoggerFactory.getLogger(Xml2UnEDIfactConverter.class);
	private static Xml2UnEDIfactConverter instance = null;
	private final static int defaultSegmentCounter = 99999;
	
	private Xml2UnEDIfactConverter() throws IOException, SAXException, ParserConfigurationException	{
		
		this.setNamespaceContext();
		this.documentFactory = DocumentBuilderFactory.newInstance();
		this.documentFactory.setNamespaceAware(true);
		this.builder = this.documentFactory.newDocumentBuilder();
	}
	
	public static Xml2UnEDIfactConverter getInstance() throws IOException, SAXException, ParserConfigurationException	{
		
		if(instance == null)
			instance = new Xml2UnEDIfactConverter();
		
		return instance;
	}

	public String convertToUNEdifact(String _xmlMiddleware,  String _interchgControlRef ) throws Exception {
		
		try {
			startL = System.currentTimeMillis();
			
			this.xmlMiddlware = _xmlMiddleware;
			this.messageVersion = null;
			
			// message interchange
			interchange = new UNEdifactInterchange41();
		    delimiters = new Delimiters();
		    delimiters.setSegment("'"); 
		    delimiters.setDecimalSeparator("."); 	    
		    delimiters.setEscape("?");
		    delimiters.setComponent(":"); 
		    delimiters.setField("+"); 	    
		    interchange.setInterchangeDelimiters(delimiters) ;
		    
		    this.controlRef = _interchgControlRef;
		    if (controlRef == null )	{	    
		    	random = new SecureRandom();
		    	controlRef= String.valueOf(new BigInteger(14, random));
		    }
		    
			// UNB
			this.buildUNBSection(this.xmlMiddlware,controlRef) ;
			interchange.setInterchangeHeader(this.unb);
			
			// interchanges
			this.msgList = this.buildInterchanges(this.xmlMiddlware);
			
			this.unz = new UNZ41();
			log.debug("ControlCount");
			this.unz.setControlCount(msgList.size()) ;
			
			log.debug("ControlRef:" +controlRef);
			this.unz.setControlRef(controlRef);
			
		    interchange.setInterchangeTrailer(this.unz) ;
		    interchange.setMessages(this.msgList) ;
		    
		    this.ediMessageString = this.getUnEdifactMessage(this.messageVersion, interchange) ;
		    this.segmentCountSetter.process(this.ediMessageString,delimiters.getSegment(),delimiters.getField(),defaultSegmentCounter);
		    this.ediMessageString = this.segmentCountSetter.getMessage(); 
		    
		    log.info("processed message [in:"+this.xmlMiddlware.length()+"; out:"+ this.ediMessageString.length()+" bytes] converted in " +String.valueOf(((System.currentTimeMillis()-startL) ) )  +" ms - done ");
		    
		    this.msgList.clear();
		    
		    return this.ediMessageString;
		}
		catch (Exception ex)	{
			
			log.error(ex.getMessage());
			log.error( StackTrace.getStackTrace(ex));
			
			throw new Exception(ex.getMessage()) ;
		}
	} 
	
	private void buildUNBSection(String xml, String controlRef) throws Exception	{
		
		this.patternUNB = Pattern.compile("(<env:UNB>.+?</env:UNB>)",Pattern.DOTALL);
		this.matcherUNB = this.patternUNB.matcher(xml);
		
		if (this.matcherUNB.find())	{
			xml = matcherUNB.group(1).replaceFirst("<env:UNB>", "<env:UNB xmlns:env=\"urn:org.milyn.edi.unedifact.v41\">");	
			log.debug("UNB section:" + xml);
		}
		else {
			throw new Exception("no xml-UNB sectiont found !!!") ;
		} 
		
		this.document =  this.builder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));
		
		this.unb = new UNB41() ;
		this.xpathUNB = XPathFactory.newInstance().newXPath(); 			
		this.xpathUNB.setNamespaceContext(namespaceContext);
		this.synID = new SyntaxIdentifier();
		this.xpathExpressionUNB = this.xpathUNB.compile("/env:UNB/env:syntaxIdentifier/env:id/text()");
		this.synID.setId((String) this.xpathExpressionUNB.evaluate(document, XPathConstants.STRING)) ; 
		this.xpathExpressionUNB = this.xpathUNB.compile("/env:UNB/env:syntaxIdentifier/env:versionNum/text()");
		this.synID.setVersionNum((String) this.xpathExpressionUNB.evaluate(document, XPathConstants.STRING));
		this.unb.setSyntaxIdentifier(this.synID);
	    this.sender = new Party();
	    this.xpathExpressionUNB = this.xpathUNB.compile("/env:UNB/env:sender/env:id/text()");
	    this.sender.setId( (String) this.xpathExpressionUNB.evaluate(document, XPathConstants.STRING)) ;
		this.xpathExpressionUNB = this.xpathUNB.compile("/env:UNB/env:sender/env:codeQualifier/text()");
		this.sender.setCodeQualifier((String) this.xpathExpressionUNB.evaluate(document, XPathConstants.STRING));	
		this.xpathExpressionUNB = this.xpathUNB.compile("/env:UNB/env:sender/env:internalId/text()");		
		this.sender.setInternalId((String) this.xpathExpressionUNB.evaluate(document, XPathConstants.STRING));
		this.unb.setSender(this.sender);
		this.recipient = new Party();
		this.xpathExpressionUNB = this.xpathUNB.compile("/env:UNB/env:recipient/env:id/text()");
		this.recipient.setId((String) this.xpathExpressionUNB.evaluate(document, XPathConstants.STRING)) ;
		this.xpathExpressionUNB = this.xpathUNB.compile("/env:UNB/env:recipient/env:codeQualifier/text()");
		this.recipient.setCodeQualifier((String) this.xpathExpressionUNB.evaluate(document, XPathConstants.STRING));
		this.xpathExpressionUNB = this.xpathUNB.compile("/env:UNB/env:recipient/env:internalId/text()");
		this.recipient.setInternalId((String) this.xpathExpressionUNB.evaluate(document, XPathConstants.STRING));
	    this.unb.setRecipient(recipient);
	    dt = new DateTime();
	    this.xpathExpressionUNB = this.xpathUNB.compile("/env:UNB/env:dateTime/env:date/text()");
		dt.setDate((String) this.xpathExpressionUNB.evaluate(document, XPathConstants.STRING));
		this.xpathExpressionUNB = this.xpathUNB.compile("/env:UNB/env:dateTime/env:time/text()");
	    dt.setTime((String) this.xpathExpressionUNB.evaluate(document, XPathConstants.STRING));
	    this.unb.setDate(dt);
	    this.unb.setControlRef(controlRef);
	    this.recipientRef = new Ref();
	    this.xpathExpressionUNB = this.xpathUNB.compile("/env:UNB/env:recipientRef/env:ref/text()");	   
	    this.recipientRef.setRef((String) this.xpathExpressionUNB.evaluate(document, XPathConstants.STRING));
	    this.xpathExpressionUNB = this.xpathUNB.compile("/env:UNB/env:recipientRef/env:refQualifier/text()");
	    this.recipientRef.setRefQualifier((String) this.xpathExpressionUNB.evaluate(document, XPathConstants.STRING));
	    this.unb.setRecipientRef(this.recipientRef);
	    this.xpathExpressionUNB = this.xpathUNB.compile("/env:UNB/env:applicationRef/text()");
	    this.appRef = (String) this.xpathExpressionUNB.evaluate(document, XPathConstants.STRING);
	    if (this.appRef.equals(""))
	    	this.appRef = "MESCEDIA" ;
		this.xpathExpressionUNB = this.xpathUNB.compile("/env:UNB/env:processingPriorityCode/text()");
		this.unb.setProcessingPriorityCode((String) this.xpathExpressionUNB.evaluate(document, XPathConstants.STRING));
		this.xpathExpressionUNB = this.xpathUNB.compile("/env:UNB/env:ackRequest/text()");
		this.unb.setAckRequest((String) this.xpathExpressionUNB.evaluate(document, XPathConstants.STRING));
		xpathExpressionUNB = this.xpathUNB.compile("/env:UNB/env:agreementId/text()");
		this.unb.setAgreementId((String) this.xpathExpressionUNB.evaluate(document, XPathConstants.STRING));
		xpathExpressionUNB = this.xpathUNB.compile("/env:UNB/env:testIndicator/text()");
		this.unb.setTestIndicator((String) this.xpathExpressionUNB.evaluate(document, XPathConstants.STRING)) ;	  

	}
	
	private List<UNEdifactMessage41> buildInterchanges(String xml) throws Exception	{
		
		interchangeMsg = null;		
		this.interchangeList.clear();
		
		patternL = Pattern.compile("(<env:interchangeMessage.+?</env:interchangeMessage>)",Pattern.DOTALL);
		matcherL = patternL.matcher(xml);
				
		while (matcherL.find()) {
			interchangeMsg = matcherL.group(1).replaceFirst("<env:interchangeMessage", "<env:interchangeMessage xmlns:env=\"urn:org.milyn.edi.unedifact.v41\" ");
			this.buildEdiMessage(interchangeMsg);
			log.debug("interchange message:" + ediMessage);
			interchangeList.add(ediMessage);
 		}
		
		return interchangeList;
	}
	
	private void buildEdiMessage(String xml) throws Exception	{
		
		unhXml = null;
		
	    random = new SecureRandom();
	    msgRefNum = String.valueOf(new BigInteger(10, random)); 
		
		// UNH
		this.patternUNH = Pattern.compile("(<env:UNH>.+?</env:UNH>)",Pattern.DOTALL);
		this.matcherUNH = patternUNH.matcher(xml);
				
		if (this.matcherUNH.find()) {
			unhXml = this.matcherUNH.group(1).replaceFirst("<env:UNH>", "<env:UNH xmlns:env=\"urn:org.milyn.edi.unedifact.v41\">");
			log.debug("UNH section:" + unhXml);
			this.buildUNHSegment(unhXml,msgRefNum);			
 		}	    	    
		else {			
			
			throw new Exception("UNH section not found !!!") ;
		}
		
		// MESSAGE
		message = null;
		messageTag = unh.getMessageIdentifier().getId().toLowerCase() + ":" + unh.getMessageIdentifier().getId().toUpperCase() ;
		this.patternUNH = Pattern.compile("(<"+ messageTag +">.+?</"+messageTag+">)",Pattern.DOTALL);
		this.matcherUNH = patternUNH.matcher(xml);
		
		messageNamespace  = "";
				
		if (this.matcherUNH.find()) {
			
			messageNamespace  = "xmlns:c=\"urn:org.milyn.edi.unedifact:un:"+unh.getMessageIdentifier().getVersionNum().toLowerCase()+
					unh.getMessageIdentifier().getReleaseNum().toLowerCase() +":common\" " +
					"xmlns:"+unh.getMessageIdentifier().getId().toLowerCase()+"=\"urn:org.milyn.edi.unedifact:un:"+
					unh.getMessageIdentifier().getVersionNum().toLowerCase()+
					unh.getMessageIdentifier().getReleaseNum().toLowerCase()+ ":"+
					unh.getMessageIdentifier().getId().toLowerCase()+"\""; 
		
			
			message = this.matcherUNH.group(1).replaceFirst("<"+messageTag +">","<"+messageTag + " " + messageNamespace +">" );
						
			log.debug("MESSAGE section:" + message);
			
 		}
		else {
			
			throw new Exception("message section not found !!!") ;
		}

		smooksConfig = this.smooksConfigPath + "/" +
					unh.getMessageIdentifier().getVersionNum().toUpperCase()  +					// D 
					unh.getMessageIdentifier().getReleaseNum().toUpperCase() + "/" +			// 96A
					unh.getMessageIdentifier().getVersionNum().toUpperCase()  +					// D 
					unh.getMessageIdentifier().getReleaseNum().toUpperCase() + "-" +			// 96A
					unh.getMessageIdentifier().getId().toUpperCase() + "-bindingconfig.xml" ;   // DESADV-bindingconfig.xml"
		
		
		currentMessageVersion = unh.getMessageIdentifier().getVersionNum().toLowerCase() +  
				unh.getMessageIdentifier().getReleaseNum().toLowerCase();
		
		currentMessageType = unh.getMessageIdentifier().getId().toLowerCase() ;
		
		log.debug("smooks message binding:" + smooksConfig);
		
		startLS = System.currentTimeMillis();
		
		this.smooks = SmooksConfigCache.getInstance().getSmooksInstanceByConfig(smooksConfig) ;
		
		this.result = new JavaResult();
		this.inputStream = new ByteArrayInputStream(message.getBytes());
		this.smooks.filterSource(new StreamSource(this.inputStream), result) ; 

		this.smooks.close();
		
		this.msgObj = result.getBean(unh.getMessageIdentifier().getId().toUpperCase()) ;
		this.document =  this.builder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));
		
		this.xpathUNH = XPathFactory.newInstance().newXPath(); 			
		this.xpathUNH.setNamespaceContext(namespaceContext);
		
		docIdXpath = "/env:interchangeMessage//"+messageTag+"/"+unh.getMessageIdentifier().getId().toLowerCase()+":BGM/c:e1004/text()";
		
		this.xpathExpressionUNH = this.xpathUNH.compile(docIdXpath);
		docID = (String) this.xpathExpressionUNH.evaluate(document, XPathConstants.STRING) ;
		
		if (docID.equals(""))	{
			docID = "null" ;
		} 
		
		log.info("processed interchange " + unh.getMessageIdentifier().getId().toUpperCase() + "-" + 
				unh.getMessageIdentifier().getVersionNum() + unh.getMessageIdentifier().getReleaseNum() + " [" + docID + "] " +
				String.valueOf( (System.currentTimeMillis()-startLS)) + " ms") ;
		
		// UNT
		unt = new UNT41();
		unt.setMessageRefNum(msgRefNum); 						// same as UNH
		unt.setSegmentCount(defaultSegmentCounter); 			
		
		ediMessage = new UNEdifactMessage41();
		ediMessage.setMessage(this.msgObj) ;
		ediMessage.setMessageHeader(unh);
		ediMessage.setMessageTrailer(unt) ;	
	}
	
	private void buildUNHSegment(String xml,String msgRefNum) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException	{
		
		// MESSAGE_TYPE
		
		this.document =  this.builder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));
		
		this.xpathUNH1 = XPathFactory.newInstance().newXPath(); 			
		this.xpathUNH1.setNamespaceContext(namespaceContext);
		
		this.messageIdentifier = new MessageIdentifier();
		
		this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:messageIdentifier/env:id/text()");
		this.messageIdentifier.setId((String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING)) ;									// DESADV 

		this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:messageIdentifier/env:versionNum/text()");			
		version =  (String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING) ;												// D
		this.messageIdentifier.setVersionNum(version) ; 													
		
		this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:messageIdentifier/env:releaseNum/text()");									// 96A
		this.messageVersion = (String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING);
		this.messageIdentifier.setReleaseNum(this.messageVersion) ;
		 
		this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:messageIdentifier/env:controllingAgencyCode/text()");
		this.messageIdentifier.setControllingAgencyCode((String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING)) ;				// UN
		
		this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:messageIdentifier/env:associationAssignedCode/text()");
		this.messageIdentifier.setAssociationAssignedCode((String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING)) ;			// EAN005					
		
	    unh = new UNH41();
	    unh.setMessageIdentifier(this.messageIdentifier) ;
	    unh.setMessageRefNum(msgRefNum) ;
	    
	    this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:commonAccessRef/text()");
		unh.setCommonAccessRef((String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING)) ;
		
	    this.transferStatus = new TransferStatus();
	    
	    this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:transferStatus/env:sequence/text()");
	    this.transferStatus.setSequence((String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING)) ;
	    
	    this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:transferStatus/env:firstAndLast/text()");
	    this.transferStatus.setFirstAndLast((String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING)) ;
	    
	    unh.setTransferStatus(transferStatus);

	    this.subset = new SourceIdentifier();
	    
	    this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:subset/env:id/text()");
	    this.subset.setId((String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING)) ;
	    
	    this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:subset/env:versionNum/text()");
	    this.subset.setVersionNum((String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING)) ;
	    
	    this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:subset/env:releaseNum/text()");
	    this.subset.setReleaseNum((String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING)) ;
	    
	    this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:subset/env:controllingAgencyCode/text()");
	    this.subset.setControllingAgencyCode((String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING)) ;
	    	    
	    unh.setSubset(this.subset);
	    
	    this.implementationGuideline = new SourceIdentifier();
	    
	    this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:implementationGuideline/env:id/text()");
	    this.implementationGuideline.setId((String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING)) ;
	    
	    this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:implementationGuideline/env:versionNum/text()");
	    this.implementationGuideline.setVersionNum((String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING)) ;
	    
	    this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:implementationGuideline/env:releaseNum/text()");
	    this.implementationGuideline.setReleaseNum((String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING)) ;
	    
	    this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:implementationGuideline/env:controllingAgencyCode/text()");
	    this.implementationGuideline.setControllingAgencyCode((String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING)) ;
	    
	    unh.setImplementationGuideline(this.implementationGuideline);
	    
	    this.scenario = new SourceIdentifier();
	    
	    this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:scenario/env:id/text()");
	    this.scenario.setId((String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING)) ;
	    
	    this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:scenario/env:versionNum/text()");
	    this.scenario.setVersionNum((String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING)) ;
	    
	    this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:scenario/env:releaseNum/text()");
	    this.scenario.setReleaseNum((String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING)) ;
	    
	    this.xpathExpressionUNH1 = this.xpathUNH1.compile("/env:UNH/env:scenario/env:controllingAgencyCode/text()");
	    this.scenario.setControllingAgencyCode((String) this.xpathExpressionUNH1.evaluate(document, XPathConstants.STRING)) ;
	    
	    unh.setScenario(this.scenario);
	}	
	
	private void setNamespaceContext()	{
		
		if(namespaceContext == null)	{
			namespaceContext =  new NamespaceContext() {
				public String getNamespaceURI(String prefix) {
										
					if (prefix.equals(currentMessageType) && !prefix.equals(null)) {
						return "urn:org.milyn.edi.unedifact:un:"+currentMessageVersion+":" + currentMessageType; 
					}
					else if (prefix.equals("c")) {
						return "urn:org.milyn.edi.unedifact:un:"+currentMessageVersion+":common" ;
					}

					return "urn:org.milyn.edi.unedifact.v41";	//env default					
				}
				@SuppressWarnings("rawtypes")
				public Iterator getPrefixes(String val) {
					return null;
				}
				public String getPrefix(String uri) {
					return null;
				}
			};
		}		
	}
		
	// 
	private String getUnEdifactMessage(String version, UNEdifactInterchange interchange) throws Exception	{
		
		ediOutStream = new StringWriter();
		FactoryCache.getInstance().getFactoryByVersion("D" + version).toUNEdifact(interchange, ediOutStream);
		return ediOutStream.toString();
		
	}
}
