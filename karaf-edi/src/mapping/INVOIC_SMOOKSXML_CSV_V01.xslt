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
	xmlns:invoic="urn:org.milyn.edi.unedifact:un:d96a:invoic"
	exclude-result-prefixes="invoic c env">

	<xsl:output  method="text" encoding="UTF-8"/>
	
	<xsl:template match="/">

		<xsl:variable name="glnSender">
			<xsl:value-of select="normalize-space(/env:unEdifact/env:UNB/env:sender/env:id/text())" />
		</xsl:variable>
		<xsl:variable name="glnRecipient">
			<xsl:value-of select="normalize-space(/env:unEdifact/env:UNB/env:recipient/env:id/text())" />
		</xsl:variable>
		
		<xsl:for-each select="//env:interchangeMessage">
		
			<xsl:variable name="docNumber">
				<xsl:value-of select="normalize-space(invoic:INVOIC/invoic:BGM/c:e1004/text())" />
			</xsl:variable>
			
			<!-- HEADER ===================    -->
			
			<xsl:value-of  select="concat('HDR;', 
						'INVOIC',';',
						$glnSender,';',
						$glnRecipient,';',
						$docNumber, ';')" />
			
			<xsl:for-each select="//invoic:INVOIC/invoic:DTM">
			
				  <xsl:if test="c:C507/c:e2005/text()='137'">
					  <xsl:variable name="docDate">
						  <xsl:value-of select="normalize-space(c:C507/c:e2380/text())" />
					  </xsl:variable>
					  <xsl:value-of  select="concat($docDate,';')" />
				  </xsl:if>
				  
				   <xsl:if test="c:C507/c:e2005/text()='35'">
					  <xsl:variable name="deliveryDate">
						  <xsl:value-of select="normalize-space(c:C507/c:e2380/text())" />
					  </xsl:variable>
					  <xsl:value-of  select="concat($deliveryDate,';')" />
				  </xsl:if>	
				  
			</xsl:for-each>
		
			<xsl:for-each select="//invoic:INVOIC/invoic:Segment_group_1">
			
				<xsl:if test="invoic:RFF/c:C506/c:e1153/text()='DQ'">
				
					<xsl:variable name="deliveryNoteNumber">
						<xsl:value-of select="normalize-space(invoic:RFF/c:C506/c:e1154/text())" />
					</xsl:variable>
					<xsl:value-of  select="concat($deliveryNoteNumber,';')" />
					
				</xsl:if>
				
				<xsl:if test="invoic:RFF/c:C506/c:e1153/text()='ON'">
				
					<xsl:variable name="docOrderNumber">
						<xsl:value-of select="normalize-space(invoic:RFF/c:C506/c:e1154/text())" />
					</xsl:variable>
					<xsl:value-of  select="concat($docOrderNumber,';')" />
				
					<xsl:variable name="orderDate">
						<xsl:value-of select="normalize-space(invoic:DTM/c:C507/c:e2380/text())" />
					</xsl:variable>
					<xsl:value-of  select="concat($orderDate,';')" />
					
				</xsl:if>
			
			</xsl:for-each>
			
			
			<xsl:for-each select="//invoic:INVOIC/invoic:Segment_group_2">
			
				<xsl:if test="invoic:NAD/c:e3035/text()='BY'">
					<xsl:variable name="glnBuyer">
						<xsl:value-of select="normalize-space(invoic:NAD/c:C082/c:e3039/text())" />
					</xsl:variable>				
					<xsl:value-of  select="concat($glnBuyer,';')" />
				</xsl:if>
				
				<xsl:if test="invoic:NAD/c:e3035/text()='SU'">
					<xsl:variable name="glnSupplier">
						<xsl:value-of select="normalize-space(invoic:NAD/c:C082/c:e3039/text())" />
					</xsl:variable>
					<xsl:value-of  select="concat($glnSupplier,';')" />
				</xsl:if>
				
				<xsl:if test="invoic:NAD/c:e3035/text()='IV'">
					<xsl:variable name="glnInvoicRecipient">
						<xsl:value-of select="normalize-space(invoic:NAD/c:C082/c:e3039/text())" />
					</xsl:variable>
					<xsl:value-of  select="concat($glnInvoicRecipient,';')" />
				</xsl:if>
				
				<xsl:if test="invoic:NAD/c:e3035/text()='DP'">
					<xsl:variable name="glnDeliveryParty">
						<xsl:value-of select="normalize-space(invoic:NAD/c:C082/c:e3039/text())" />
					</xsl:variable>
					<xsl:value-of  select="concat($glnDeliveryParty,';')" />
				</xsl:if>
				
			</xsl:for-each>
			
			<xsl:variable name="taxRate">
				<xsl:value-of select="normalize-space(invoic:INVOIC/invoic:Segment_group_6/invoic:TAX/c:C243/c:e5278/text())" />
			</xsl:variable>
			<xsl:value-of  select="concat($taxRate,';')" />
			
			<xsl:variable name="currency">
				<xsl:value-of select="normalize-space(invoic:INVOIC/invoic:Segment_group_7/invoic:CUX/c:C504_-_-1/c:e6345/text())" />
			</xsl:variable>
			<xsl:value-of  select="concat($currency,';')" />
			
			<xsl:value-of  select="string('&#xA;')" />			<!-- header end -->
			
			<!-- POSITIONS ===================    -->
			
			<xsl:for-each select="//invoic:INVOIC/invoic:Segment_group_25">
				  
				<xsl:value-of  select="concat('POS',';')" />

				<xsl:variable name="posNo">
					<xsl:value-of select="normalize-space(invoic:LIN/c:e1082/text())" />
				</xsl:variable>		  
				<xsl:value-of  select="concat($posNo,';')" />

				<xsl:variable name="ean">
					<xsl:value-of select="normalize-space(invoic:LIN/c:C212/c:e7140/text())" />
				</xsl:variable>		  
				<xsl:value-of  select="concat($ean,';')" />

				<xsl:variable name="qty">
					<xsl:value-of select="normalize-space(invoic:QTY/c:C186/c:e6060/text())" />
				</xsl:variable>		  
				<xsl:value-of  select="concat($qty,';')" />

				<xsl:variable name="measureUnit">
					<xsl:value-of select="normalize-space(invoic:QTY/c:C186/c:e6411/text())" />
				</xsl:variable>		  
				<xsl:value-of  select="concat($measureUnit,';')" />
				 
				 
				<xsl:for-each select="//invoic:Segment_group_28">
				
					<xsl:if test="invoic:PRI/c:C509/c:e5125/text()='AAA'">
						<xsl:variable name="priceNet">
							<xsl:value-of select="normalize-space(invoic:PRI/c:C509/c:e5118/text())" />
						</xsl:variable>
						<xsl:value-of  select="concat($priceNet,';')" />
					</xsl:if>
					
					<xsl:if test="invoic:PRI/c:C509/c:e5125/text()='AAB'">
						<xsl:variable name="priceGros">
							<xsl:value-of select="normalize-space(invoic:PRI/c:C509/c:e5118/text())" />
						</xsl:variable>
						<xsl:value-of  select="concat($priceGros,';')" />
					</xsl:if>
				
				</xsl:for-each>	
				<xsl:value-of  select="string('&#xA;')" />	<!-- position end -->
				
			</xsl:for-each>
			
			<!-- SUM ===================    -->
			
			<xsl:for-each select="//invoic:Segment_group_48">
			
				<xsl:if test="invoic:MOA/c:C516/c:e5025/text()='77'">
					<xsl:variable name="amountInvoic">
						<xsl:value-of select="normalize-space(invoic:MOA/c:C516/c:e5004/text())" />
					</xsl:variable>
					<xsl:value-of  select="concat($amountInvoic,';')" />
				</xsl:if>
				
				<xsl:if test="invoic:MOA/c:C516/c:e5025/text()='79'">
					<xsl:variable name="amountSumPositions">
						<xsl:value-of select="normalize-space(invoic:MOA/c:C516/c:e5004/text())" />
					</xsl:variable>
					<xsl:value-of  select="concat($amountSumPositions,';')" />
				</xsl:if>
				
				<xsl:if test="invoic:MOA/c:C516/c:e5025/text()='124'">
					<xsl:variable name="amoutTaxes">
						<xsl:value-of select="normalize-space(invoic:MOA/c:C516/c:e5004/text())" />
					</xsl:variable>
					<xsl:value-of  select="concat($amoutTaxes,';')" />
				</xsl:if>
				
				<xsl:if test="invoic:MOA/c:C516/c:e5025/text()='125'">
					<xsl:variable name="amountTaxBase">
						<xsl:value-of select="normalize-space(invoic:MOA/c:C516/c:e5004/text())" />
					</xsl:variable>
					<xsl:value-of  select="concat($amountTaxBase,';')" />
				</xsl:if>
			
			</xsl:for-each>
			
		
		</xsl:for-each>  <!-- end interchange -->
		
		<xsl:value-of  select="string('&#xA;')" />	<!-- end message -->

	</xsl:template>
</xsl:stylesheet>
