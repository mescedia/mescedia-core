<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:D96A="http://www.ibm.com/dfdl/edi/un/edifact/D96A"
                xmlns:srv="http://www.ibm.com/dfdl/edi/un/service/4.1"
                xmlns:java="http://xsltExtensions.mescedia.org" >
    <xsl:output omit-xml-declaration="yes" indent="no"/>
    <xsl:strip-space elements="*"/>

    <xsl:template name='xsl:initial-template' />

    <xsl:template match="/">
        <root>

            <xsl:variable name="messageFormat" select="java:translate('messageFormat','id', '2', 'formatName')" />

            <messageFormat value="{$messageFormat}" />

            <xsl:variable name="vQ">"</xsl:variable>


            <xsl:value-of select="java:log('INFO', $messageFormat )" />

            <xsl:comment>=======================</xsl:comment>
            <xsl:comment>input analyser result file</xsl:comment>

            <xsl:comment>
                <xsl:value-of select="java:log('DEBUG','this is a debug message')" />
                <xsl:value-of select="java:log('INFO','this is an info message')" />
                <xsl:value-of select="java:log('WARN','this is a warning message')" />
                <xsl:value-of select="java:log('ERROR','this is an error message')" />
            </xsl:comment>




            <xsl:comment>=======================</xsl:comment>
            <interface type="inbound" user="test">AS2-TEST</interface>
            <xsl:variable name="msgType" select="/D96A:Interchange/D96A:Message/UNH/S009/E0065" />
            <xsl:variable name="msgVersion" select="concat(/D96A:Interchange/D96A:Message/UNH/S009/E0052, /D96A:Interchange/D96A:Message/UNH/S009/E0054)" />
            <message type="{$msgType}" version="{$msgVersion}" content-type="application/edifact" />
            <xsl:variable name="senderType" select="/D96A:Interchange/UNB/S002/E0007"/>
            <xsl:variable name="receiverType" select="/D96A:Interchange/UNB/S003/E0007"/>
            <sender type="{$senderType}"><xsl:value-of select="/D96A:Interchange/UNB/S002/E0004"/></sender>
            <receiver type="{$receiverType}"><xsl:value-of select="/D96A:Interchange/UNB/S003/E0010"/></receiver>
            <xsl:for-each select="/D96A:Interchange/D96A:Message/D96A:DESADV//SegGrp-2">
                <xsl:variable name="nadType" select="NAD/E3035"/>
                <nad type="{$nadType}"><xsl:value-of select="NAD/C082/E3039"/></nad>
            </xsl:for-each>
        </root>
    </xsl:template>

    <!-- formatting output to single line -->
    <xsl:template match="*/text()[normalize-space()]">
        <xsl:value-of select="normalize-space()"/>
    </xsl:template>
    <xsl:template match="*/text()[not(normalize-space())]" />

</xsl:stylesheet>
