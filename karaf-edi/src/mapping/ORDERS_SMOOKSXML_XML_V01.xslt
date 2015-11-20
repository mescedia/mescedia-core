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
	xmlns:env="urn:org.milyn.edi.unedifact.v41"
	xmlns:c="urn:org.milyn.edi.unedifact:un:d96a:common"
	xmlns:orders="urn:org.milyn.edi.unedifact:un:d96a:orders"
	exclude-result-prefixes="orders c env">

	<xsl:output omit-xml-declaration="no" method="xml" indent="yes"
		encoding="UTF-8" cdata-section-elements="field"  />

	<!-- ==================================================================== -->
	<!-- ==================================================================== -->
	<!-- ==================================================================== -->

	<xsl:template match="/">
	
		<MESSAGE>
						
			<xsl:variable name="glnSender"><xsl:value-of select="normalize-space(/env:unEdifact/env:UNB/env:sender/env:id/text())" /></xsl:variable>
			<xsl:variable name="glnRecipient"><xsl:value-of select="normalize-space(/env:unEdifact/env:UNB/env:recipient/env:id/text())" /></xsl:variable>
	
			<xsl:for-each select="//env:interchangeMessage">
								
					<ORDERS>
					
						<DocumentNo>
							<xsl:value-of select="normalize-space(orders:ORDERS/orders:BGM/c:e1004/text())" />
						</DocumentNo>
						
						<xsl:for-each select="//orders:ORDERS/orders:DTM">
						
							<xsl:if test="c:C507/c:e2005/text()='137'">
								<DocumentDate>
									<xsl:value-of select="normalize-space(c:C507/c:e2380/text())" />
								</DocumentDate>
							</xsl:if>
							
							<xsl:if test="c:C507/c:e2005/text()='2'">
								<DeliveryDate>
									<xsl:value-of select="normalize-space(c:C507/c:e2380/text())" />
								</DeliveryDate>
							</xsl:if>
							
							<xsl:if test="c:C507/c:e2005/text()='64'">
								<DeliveryDateEarliest>
									<xsl:value-of select="normalize-space(c:C507/c:e2380/text())" />
								</DeliveryDateEarliest>
							</xsl:if>
							
							<xsl:if test="c:C507/c:e2005/text()='63'">
								<DeliveryDateLast>
									<xsl:value-of select="normalize-space(c:C507/c:e2380/text())" />
								</DeliveryDateLast>
							</xsl:if>
							
						</xsl:for-each>
						
						<!-- ============================================ -->
						
						<xsl:for-each select="//orders:ORDERS/orders:Segment_group_2">
							
							<xsl:if test="orders:NAD/c:e3035/text()='BY'">
								  <GlnBuyer>
									  <xsl:value-of select="normalize-space(orders:NAD/c:C082/c:e3039/text())" />  
								  </GlnBuyer>								  
							</xsl:if>
							
							<xsl:if test="orders:NAD/c:e3035/text()='SU'">
								  <GlnSupplier>
									  <xsl:value-of select="normalize-space(orders:NAD/c:C082/c:e3039/text())" />  
								  </GlnSupplier>								  
							</xsl:if>

							<xsl:if test="orders:NAD/c:e3035/text()='IV'">
								  <GlnInvoicRecipient>
									  <xsl:value-of select="normalize-space(orders:NAD/c:C082/c:e3039/text())" />  
								  </GlnInvoicRecipient>								  
							</xsl:if>							
						
						</xsl:for-each>
						
						<!-- ============================================ -->
						
						<GlnSender><xsl:value-of select="$glnSender" /></GlnSender>
						<GlnRecipient><xsl:value-of select="$glnRecipient" /></GlnRecipient>
						
						<!-- ============================================ -->
						
						<Currency>EUR</Currency>
						<VatRate>19</VatRate>
						
						<!-- ============================================ -->
					
						<Positions>
						
							<xsl:for-each select=".//orders:Segment_group_25">
							
								<Position>
								
									<PositionNumber>
										<xsl:value-of select="orders:LIN/c:e1082/text()" />
									</PositionNumber>
									
									<Ean>
										<xsl:value-of select="orders:LIN/c:C212/c:e7140/text()" />
									</Ean>
									
									<ArticleDescription>
										<xsl:value-of select="orders:IMD/c:C273/c:e7008_-_-1/text()" />
									</ArticleDescription>
									
									<OrderedQuantity>
										<xsl:value-of select="orders:QTY/c:C186/c:e6060/text()" />
									</OrderedQuantity>
									
									<PriceNet>
										<xsl:value-of select="orders:Segment_group_28/orders:PRI/c:C509/c:e5118/text()" />
									</PriceNet>
							
								</Position>
							
							</xsl:for-each>
						
						</Positions>
			
				  </ORDERS>
					  
				  
			  </xsl:for-each>	<!-- end interchangeMessage -->
		  
		  </MESSAGE>
		  
	</xsl:template>

	<!-- ==================================================================== -->
	<!-- ==================================================================== -->
	<!-- ==================================================================== -->


</xsl:stylesheet>