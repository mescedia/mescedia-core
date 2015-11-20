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
			  <xsl:with-param name="senderGLN" select="normalize-space(/INVOIC/GlnSender/text())" />
			  <xsl:with-param name="recipientGLN" select="normalize-space(/INVOIC/GlnRecipient/text())" />
			  <xsl:with-param name="controlRef" select="normalize-space($controlRef)" />
			  <xsl:with-param name="unbDate" select="normalize-space($unbDate)" />
			  <xsl:with-param name="unbTime" select="normalize-space($unbTime)" />
			  <xsl:with-param name="appRef">INVOIC</xsl:with-param>
		  </xsl:call-template>
		  
		  <xsl:for-each select="//INVOIC">	

				<env:interchangeMessage xmlns:c="urn:org.milyn.edi.unedifact:un:d96a:common" xmlns:invoic="urn:org.milyn.edi.unedifact:un:d96a:invoic">			
					
					<xsl:variable name="msgRef_UNH_UNT">12345</xsl:variable>
					
					<xsl:call-template name="segmentUNH">
						<xsl:with-param name="msgRef"><xsl:value-of select="$msgRef_UNH_UNT" /></xsl:with-param>
					</xsl:call-template>
					
					<invoic:INVOIC>
					  
						<invoic:BGM>
							<c:C002><c:e1001>380</c:e1001></c:C002>
							<c:e1004><xsl:value-of select="normalize-space(./DocumentNo/text())" /></c:e1004>
							<c:e1225>9</c:e1225>
						</invoic:BGM>
						
						<invoic:DTM>
						  <c:C507>
							  <c:e2005>137</c:e2005>
							  <c:e2380><xsl:value-of select="normalize-space(./DocumentDate/text())" /></c:e2380>
							  <c:e2379>102</c:e2379>
						  </c:C507>
						</invoic:DTM>
						
						<invoic:DTM>
						  <c:C507>
							  <c:e2005>35</c:e2005>
							  <c:e2380><xsl:value-of select="normalize-space(./DeliveryDate/text())" /></c:e2380>
							  <c:e2379>102</c:e2379>
						  </c:C507>
						</invoic:DTM>
						
						<invoic:FTX>
							<c:e4451>AAB</c:e4451>
							<c:C108>
								<c:e4440_-_-1>payment condition text 1</c:e4440_-_-1>
								<c:e4440_-_-2>payment condition text 2</c:e4440_-_-2>
							</c:C108>
							<c:e3453>EN</c:e3453>
						</invoic:FTX>
						
				
						<invoic:Segment_group_1>
						
							<invoic:RFF>
								<c:C506>
									<c:e1153>DQ</c:e1153>
									<c:e1154><xsl:value-of select="normalize-space(./DeliveryReportNo/text())" /></c:e1154>
								</c:C506>
							</invoic:RFF>
						
							<invoic:DTM>
								<c:C507>
									<c:e2005>171</c:e2005>
									<c:e2380><xsl:value-of select="normalize-space(./DeliveryDate/text())" /></c:e2380>
									<c:e2379>102</c:e2379>
								</c:C507>
							</invoic:DTM>
						</invoic:Segment_group_1>
						
						<invoic:Segment_group_1>
							<invoic:RFF>
								<c:C506>
									<c:e1153>ON</c:e1153>
									<c:e1154><xsl:value-of select="normalize-space(./CustomerOrderNo/text())" /></c:e1154>
								</c:C506>
							</invoic:RFF>
							<invoic:DTM>
								<c:C507>
									<c:e2005>171</c:e2005>
									<c:e2380><xsl:value-of select="normalize-space(./OrderDate/text())" /></c:e2380>
									<c:e2379>102</c:e2379>
								</c:C507>
							</invoic:DTM>
						</invoic:Segment_group_1>
												
						<invoic:Segment_group_2>
							<invoic:NAD>
								<c:e3035>SU</c:e3035>
								<c:C082>
									<c:e3039><xsl:value-of select="normalize-space(./GlnSupplier/text())" /></c:e3039>
									<c:e3055>9</c:e3055>
								</c:C082>
							</invoic:NAD>
							<invoic:Segment_group_3>
								<invoic:RFF>
									<c:C506>
										<c:e1153>VA</c:e1153>
										<c:e1154><xsl:value-of select="normalize-space(./SupplierVatID/text())" /></c:e1154>
									</c:C506>
								</invoic:RFF>
							</invoic:Segment_group_3>
						</invoic:Segment_group_2>
						
						<invoic:Segment_group_2>
							<invoic:NAD>
								<c:e3035>BY</c:e3035>
								<c:C082>
									<c:e3039><xsl:value-of select="normalize-space(./GlnRecipient/text())" /></c:e3039>
									<c:e3055>9</c:e3055>
								</c:C082>
							</invoic:NAD>
						</invoic:Segment_group_2>
						
						<invoic:Segment_group_2>
							<invoic:NAD>
								<c:e3035>IV</c:e3035>
								<c:C082>
									<c:e3039><xsl:value-of select="normalize-space(./GlnRecipient/text())" /></c:e3039>
									<c:e3055>9</c:e3055>
								</c:C082>
							</invoic:NAD>
						</invoic:Segment_group_2>

						<invoic:Segment_group_2>
							<invoic:NAD>
								<c:e3035>DP</c:e3035>
								<c:C082>
									<c:e3039><xsl:value-of select="normalize-space(./GlnDeliveryParty/text())" /></c:e3039>
									<c:e3055>9</c:e3055>
								</c:C082>
							</invoic:NAD>
						</invoic:Segment_group_2>
						
						<invoic:Segment_group_6>
							<invoic:TAX>
								<c:e5283>7</c:e5283>
								<c:C241>
									<c:e5153>VAT</c:e5153>
								</c:C241>
								<c:C243>
									<c:e5278>7</c:e5278>
								</c:C243>
							</invoic:TAX>
						</invoic:Segment_group_6>
												
						<invoic:Segment_group_7>
							<invoic:CUX>
								<c:C504_-_-1>
									<c:e6347>2</c:e6347>
									<c:e6345><xsl:value-of select="normalize-space(./Currency/text())" /></c:e6345>
									<c:e6343>9</c:e6343>
								</c:C504_-_-1>
							</invoic:CUX>
						</invoic:Segment_group_7>
						
						
						<xsl:for-each select="./Positions//Position">
						
							<invoic:Segment_group_25>
							
								<invoic:LIN>
									  <c:e1082><xsl:value-of select="normalize-space(./PositionNo/text())" /></c:e1082>
									  <c:C212>
										  <c:e7140><xsl:value-of select="normalize-space(./EAN/text())" /></c:e7140>
										  <c:e7143>EN</c:e7143>
									  </c:C212>
								</invoic:LIN>
								
								<invoic:PIA>
									<c:e4347>1</c:e4347>
									<c:C212_-_-1>
										<c:e7140><xsl:value-of select="normalize-space(./SupplierArticleNo/text())" /></c:e7140>
										<c:e7143>SA</c:e7143>
										<c:e3055>91</c:e3055>
									</c:C212_-_-1>
								</invoic:PIA>
								
								<invoic:IMD>
									<c:e7077>A</c:e7077>
									<c:C273>
										<c:e7009></c:e7009>
										<c:e7008_-_-1><xsl:value-of select="normalize-space(./ArticleDescription/text())" /></c:e7008_-_-1>
									</c:C273>
								</invoic:IMD>
								
								<invoic:QTY>
									<c:C186>
										<c:e6063>47</c:e6063>
										<c:e6060><xsl:value-of select="normalize-space(./Quantity/text())" /></c:e6060>
										<c:e6411><xsl:value-of select="normalize-space(./MeasureUnit/text())" /></c:e6411>
									</c:C186>
								</invoic:QTY>
								
								<invoic:Segment_group_28>
								
										<invoic:PRI>
											<c:C509>
												<c:e5125>AAA</c:e5125>
												<c:e5118><xsl:value-of select="normalize-space(./PurchasePriceNet/text())" /></c:e5118>
												<c:e5386><xsl:value-of select="normalize-space(./MeasureUnit/text())" /></c:e5386>
											</c:C509>
										</invoic:PRI>
									  
								</invoic:Segment_group_28>
								
								<invoic:Segment_group_28>
								
										<invoic:PRI>
											<c:C509>
												<c:e5125>AAB</c:e5125>
												<c:e5118><xsl:value-of select="normalize-space(./PurchasePriceGros/text())" /></c:e5118>
												<c:e5386><xsl:value-of select="normalize-space(./MeasureUnit/text())" /></c:e5386>
											</c:C509>
										</invoic:PRI>
									  
								</invoic:Segment_group_28>								
						
							</invoic:Segment_group_25>
						
						</xsl:for-each> 	
					
						
						<invoic:UNS>
							<c:e0081>S</c:e0081>
						</invoic:UNS>
											
						<invoic:Segment_group_48>
							<invoic:MOA>
								<c:C516>
									<c:e5025>77</c:e5025>
									<c:e5004>6221.22</c:e5004>
								</c:C516>
							</invoic:MOA>
						</invoic:Segment_group_48>
						<invoic:Segment_group_48>
							<invoic:MOA>
								<c:C516>
									<c:e5025>79</c:e5025>
									<c:e5004>5822.77</c:e5004>
								</c:C516>
							</invoic:MOA>
						</invoic:Segment_group_48>
						<invoic:Segment_group_48>
							<invoic:MOA>
								<c:C516>
									<c:e5025>124</c:e5025>
									<c:e5004>403.45</c:e5004>
								</c:C516>
							</invoic:MOA>
						</invoic:Segment_group_48>
						<invoic:Segment_group_48>
							<invoic:MOA>
								<c:C516>
									<c:e5025>125</c:e5025>
									<c:e5004>5824.77</c:e5004>
								</c:C516>
							</invoic:MOA>
						</invoic:Segment_group_48>

					</invoic:INVOIC>
					
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
			
			<env:agreementId>EANCOM</env:agreementId>
			<env:testIndicator>1</env:testIndicator>
		</env:UNB>

	</xsl:template>

	<!-- ==================================================================== -->

	<xsl:template name="segmentUNH">
		<xsl:param name="msgRef" />
		
		<env:UNH>
			<env:messageRefNum><xsl:value-of select="$msgRef" /></env:messageRefNum>
			<env:messageIdentifier>
				<env:id>INVOIC</env:id>
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