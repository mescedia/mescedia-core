<?xml version="1.0" encoding="UTF-8"?>
<!--  
	mescedia - Copyright (C) 2014 - 2015

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public License (version 3) 
	as published by the Free Software Foundation.
	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
	See the  GNU Lesser General Public License for more details:
	https://www.gnu.org/licenses/lgpl-3.0.txt
-->

<!-- 
  	@author Michael Kassnel 
  	@web    https://www.mescedia.com  
-->

<xsl:stylesheet version="2.0" 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:env="urn:org.milyn.edi.unedifact.v41">

	<xsl:output omit-xml-declaration="yes" method="xml" indent="yes"
		encoding="UTF-8" cdata-section-elements="field" />

	<xsl:template match="/">

		<!-- global variables -->
		<xsl:variable name="controlRef">12345</xsl:variable>
		<xsl:variable name="unbDate">140923</xsl:variable>
		<xsl:variable name="unbTime">0000</xsl:variable>
		<xsl:variable name="segCnt">99999</xsl:variable> 	<!-- value will be set by converter if value=99999  -->
		<xsl:variable name="ns"></xsl:variable> 

		<env:unEdifact > <!-- root element -->
		

		  <!-- UNB -->
		  <xsl:call-template name="segmentUNB">
			  <xsl:with-param name="senderGLN" select="normalize-space(/Order/Header/GlnSender/text())" />
			  <xsl:with-param name="recipientGLN" select="normalize-space(/Order/Header/GlnRecipient/text())" />
			  <xsl:with-param name="controlRef" select="normalize-space($controlRef)" />
			  <xsl:with-param name="unbDate" select="normalize-space($unbDate)" />
			  <xsl:with-param name="unbTime" select="normalize-space($unbTime)" />
			  <xsl:with-param name="appRef">ORDERS</xsl:with-param>
		  </xsl:call-template>
		  
		  <xsl:for-each select="//Order">	

				<env:interchangeMessage xmlns:c="urn:org.milyn.edi.unedifact:un:d96a:common" xmlns:orders="urn:org.milyn.edi.unedifact:un:d96a:orders">			
					
					<xsl:variable name="msgRef_UNH_UNT">12345</xsl:variable>
					
					<xsl:call-template name="segmentUNH">
						<xsl:with-param name="msgRef"><xsl:value-of select="$msgRef_UNH_UNT" /></xsl:with-param>
					</xsl:call-template>
					
					<orders:ORDERS>
					  
						<orders:BGM>
							<c:C002><c:e1001>220</c:e1001></c:C002>
							<c:e1004><xsl:value-of select="normalize-space(./Header/DocumentNo/text())" /></c:e1004>
							<c:e1225>9</c:e1225>
						</orders:BGM>
						
						<orders:DTM>
						  <c:C507>
							  <c:e2005>137</c:e2005>
							  <c:e2380><xsl:value-of select="normalize-space(./Header/DocumentDate/text())" /></c:e2380>
							  <c:e2379>102</c:e2379>
						  </c:C507>
						</orders:DTM>
						
						<orders:DTM>
						  <c:C507>
							  <c:e2005>2</c:e2005>
							  <c:e2380><xsl:value-of select="normalize-space(./Header/DeliveryDate/text())" /></c:e2380>
							  <c:e2379>102</c:e2379>
						  </c:C507>
						</orders:DTM>
						
						<orders:DTM>
						  <c:C507>
							  <c:e2005>64</c:e2005>
							  <c:e2380><xsl:value-of select="normalize-space(./Header/DeliveryDateFrom/text())" /></c:e2380>
							  <c:e2379>102</c:e2379>
						  </c:C507>
						</orders:DTM>

						<orders:DTM>
						  <c:C507>
							  <c:e2005>63</c:e2005>
							  <c:e2380><xsl:value-of select="normalize-space(./Header/DeliveryDateTo/text())" /></c:e2380>
							  <c:e2379>102</c:e2379>
						  </c:C507>
						</orders:DTM>
						
						<orders:Segment_group_2>
							<orders:NAD>
								<c:e3035>BY</c:e3035>
								<c:C082>
									<c:e3039><xsl:value-of select="normalize-space(./Header/GlnCustomer/text())" /></c:e3039>
									<c:e3055>9</c:e3055>
								</c:C082>
							</orders:NAD>
						</orders:Segment_group_2>
						
						<orders:Segment_group_2>
							<orders:NAD>
								<c:e3035>IV</c:e3035>
								<c:C082>
									<c:e3039><xsl:value-of select="normalize-space(./Header/GlnInvoicRecipient/text())" /></c:e3039>
									<c:e3055>9</c:e3055>
								</c:C082>
							</orders:NAD>
						</orders:Segment_group_2>
						
						<orders:Segment_group_2>
							<orders:NAD>
								<c:e3035>SU</c:e3035>
								<c:C082>
									<c:e3039><xsl:value-of select="normalize-space(./Header/GlnSupplier/text())" /></c:e3039>
									<c:e3055>9</c:e3055>
								</c:C082>
							</orders:NAD>
						</orders:Segment_group_2>
						
						<orders:Segment_group_7>
							<orders:CUX>
								<c:C504_-_-1>
									<c:e6347>2</c:e6347>
									<c:e6345><xsl:value-of select="normalize-space(./Currency/text())" /></c:e6345>
									<c:e6343>9</c:e6343>
								</c:C504_-_-1>
							</orders:CUX>
						</orders:Segment_group_7>
						
						<xsl:for-each select="./Position">
						
							<orders:Segment_group_25>
							
								<orders:LIN>
									  <c:e1082><xsl:value-of select="normalize-space(./PositionNo/text())" /></c:e1082>
									  <c:C212>
										  <c:e7140><xsl:value-of select="normalize-space(./EAN/text())" /></c:e7140>
										  <c:e7143>EN</c:e7143>
									  </c:C212>
								</orders:LIN>
								
								<orders:IMD>
									<c:e7077>F</c:e7077>
									<c:C273>
										<c:e7009>CU</c:e7009>
										<c:e7008_-_-1><xsl:value-of select="normalize-space(./LabelText/text())" /></c:e7008_-_-1>
									</c:C273>
								</orders:IMD>
								
								<orders:QTY>
									<c:C186>
										<c:e6063>21</c:e6063>
										<c:e6060><xsl:value-of select="normalize-space(./OrderedAmount/text())" /></c:e6060>
										<c:e6411><xsl:value-of select="normalize-space(./MeasureUnit/text())" /></c:e6411>
									</c:C186>
								</orders:QTY>
								
								<orders:Segment_group_28>
								
										<orders:PRI>
											<c:C509>
												<c:e5125>AAA</c:e5125>
												<c:e5118><xsl:value-of select="normalize-space(./PurchasePrice/text())" /></c:e5118>
												<c:e5386>NTP</c:e5386>
											</c:C509>
										</orders:PRI>
										
										<orders:CUX>
											<c:C504_-_-1>
												<c:e6347>2</c:e6347>
												<c:e6345><xsl:value-of select="normalize-space(./Currency/text())" /></c:e6345>
												<c:e6343>9</c:e6343>
											</c:C504_-_-1>
										</orders:CUX>
							  
								</orders:Segment_group_28>
						
							</orders:Segment_group_25>
						
						</xsl:for-each>	<!-- END POSITIONS -->
					
					</orders:ORDERS>
					
					<orders:UNS>
						<c:e0081>S</c:e0081>
					</orders:UNS>
					  
					<xsl:call-template name="segmentUNT">
						<xsl:with-param name="segCnt" select="normalize-space($segCnt)" />
						<xsl:with-param name="msgRef" select="normalize-space($msgRef_UNH_UNT)" />
					</xsl:call-template>
					
				</env:interchangeMessage>
				
			</xsl:for-each>
			
			<xsl:call-template name="segmentUNZ">
				<xsl:with-param name="cnt" select="normalize-space($segCnt)" />
				<xsl:with-param name="ref" select="normalize-space($controlRef)" />
			</xsl:call-template>
			
		</env:unEdifact>

	</xsl:template>
	
	<!-- ==================================================================== -->

	<xsl:template name="segmentUNB">
		<xsl:param name="senderGLN" />
		<xsl:param name="recipientGLN" />
		<xsl:param name="controlRef" />
		<xsl:param name="unbDate" />
		<xsl:param name="unbTime" />
		<xsl:param name="appRef" />

		<env:UNB>
			<env:syntaxIdentifier>
				<env:id>UNOC</env:id>
				<env:versionNum>3</env:versionNum>
			</env:syntaxIdentifier>
			<env:sender>
				<env:id><xsl:value-of select="$senderGLN" /></env:id>
				<env:codeQualifier>14</env:codeQualifier>
			</env:sender>
			<env:recipient>
				<env:id><xsl:value-of select="$recipientGLN" /></env:id>
				<env:codeQualifier>14</env:codeQualifier>
			</env:recipient>
			<env:dateTime>
				<env:date><xsl:value-of select="$unbDate" /></env:date>
				<env:time><xsl:value-of select="$unbTime" /></env:time>
			</env:dateTime>
			<env:controlRef><xsl:value-of select="$controlRef" /></env:controlRef>
			<env:applicationRef><xsl:value-of select="$appRef" /></env:applicationRef>
			
			<env:agreementId></env:agreementId>
			<env:testIndicator>1</env:testIndicator>
		</env:UNB>

	</xsl:template>

	<!-- ==================================================================== -->

	<xsl:template name="segmentUNH">
		<xsl:param name="msgRef" />
		
		<env:UNH>
			<env:messageRefNum><xsl:value-of select="$msgRef" /></env:messageRefNum>
			<env:messageIdentifier>
				<env:id>ORDERS</env:id>
				<env:versionNum>D</env:versionNum>
				<env:releaseNum>96A</env:releaseNum>
				<env:controllingAgencyCode>UN</env:controllingAgencyCode>
				<env:associationAssignedCode>EAN008</env:associationAssignedCode>
			</env:messageIdentifier>
		</env:UNH>

	</xsl:template>

	<!-- ==================================================================== -->

	<xsl:template name="segmentUNT">
		<xsl:param name="segCnt" />
		<xsl:param name="msgRef" />

		<env:UNT>
			<env:segmentCount><xsl:value-of select="$segCnt" /></env:segmentCount>
			<env:messageRefNum><xsl:value-of select="$msgRef" /></env:messageRefNum>
		</env:UNT>

	</xsl:template>
		
	
	<xsl:template name="segmentUNZ">
		<xsl:param name="cnt" />
		<xsl:param name="ref" />
	
		<env:UNZ>
			<env:controlCount><xsl:value-of select="$cnt" /></env:controlCount>
			<env:controlRef><xsl:value-of select="$ref" /></env:controlRef>
		</env:UNZ>
	
	</xsl:template>		
	
	<!-- ==================================================================== -->	


</xsl:stylesheet>