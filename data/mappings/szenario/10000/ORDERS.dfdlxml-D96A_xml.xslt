<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:D96A="http://www.ibm.com/dfdl/edi/un/edifact/D96A"
                xmlns:srv="http://www.ibm.com/dfdl/edi/un/service/4.1"
                xmlns:java="http://xsltExtensions.mescedia.org"
                exclude-result-prefixes="java srv D96A">
    <xsl:output omit-xml-declaration="yes" indent="no"/>
    <xsl:strip-space elements="*"/>

    <xsl:template name='xsl:initial-template' />

    <xsl:template match="/">
        <xsl:variable name="msgType" select="/D96A:Interchange/D96A:Message/UNH/S009/E0065" />
        <xsl:variable name="msgVersion" select="concat(/D96A:Interchange/D96A:Message/UNH/S009/E0052, /D96A:Interchange/D96A:Message/UNH/S009/E0054)" />
        <xsl:variable name="documentId" select="/D96A:Interchange/D96A:Message/D96A:ORDERS/BGM/E1004" />
        <xsl:variable name="senderId" select="/D96A:Interchange/UNB/S002/E0004" />
        <xsl:variable name="receiverId" select="/D96A:Interchange/UNB/S003/E0010" />

        <xsl:value-of select="java:log('INFO',concat('Processing mapping on message Type/Version/DocumentId/Sender/Receiver: ', $msgType, '/', $msgVersion, '/', $documentId, '/', $senderId, '/', $receiverId ))" />

        <ERP type="{$msgType}" version="{$msgVersion}" senderId="{$senderId}" receiverId="{$receiverId}">
            <message documentId="{$documentId}">
                <xsl:for-each select="/D96A:Interchange/D96A:Message/D96A:ORDERS//SegGrp-25">
                    <article>
                        <id><xsl:value-of select="LIN/C212/E7140/text()"/></id>
                        <quantity><xsl:value-of select="QTY/C186/E6060/text()"/></quantity>
                        <description><xsl:value-of select="FTX/C108/E4440/text()"/></description>
                    </article>
                </xsl:for-each>
            </message>
        </ERP>
    </xsl:template>

</xsl:stylesheet>
