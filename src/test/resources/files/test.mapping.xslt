<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:D11B="http://www.ibm.com/dfdl/edi/un/edifact/D11B"
                xmlns:srv="http://www.ibm.com/dfdl/edi/un/service/4.1">
    <xsl:output omit-xml-declaration="yes" indent="no"/>
    <xsl:strip-space elements="*"/>

    <xsl:template name='xsl:initial-template' />

    <xsl:template match="/">
        <root>

            <xsl:variable name="msgType" select="/D11B:Interchange/D11B:Message/UNH/S009/E0065" />
            <xsl:variable name="msgVersion" select="concat(/D11B:Interchange/D11B:Message/UNH/S009/E0052, /D11B:Interchange/D11B:Message/UNH/S009/E0054)" />
            <message type="{$msgType}" version="{$msgVersion}" content-type="application/edifact" />

        </root>
    </xsl:template>
</xsl:stylesheet>