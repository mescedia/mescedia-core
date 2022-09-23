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
            <xsl:value-of select="java:log('INFO', $messageFormat )" />

            <xsl:value-of select="java:log('DEBUG','this is a debug message')" />
            <xsl:value-of select="java:log('INFO','this is an info message')" />
            <xsl:value-of select="java:log('WARN','this is a warning message')" />
            <xsl:value-of select="java:log('ERROR','this is an error message')" />

            <xsl:variable name="msgType" select="/D96A:Interchange/D96A:Message/UNH/S009/E0065" />
            <xsl:variable name="msgVersion" select="concat(/D96A:Interchange/D96A:Message/UNH/S009/E0052, /D96A:Interchange/D96A:Message/UNH/S009/E0054)" />

            <message type="{$msgType}" version="{$msgVersion}" content-type="application/edifact" />

            <recordList>
                <xsl:for-each select="java:listDemo('dbUrl','dbUser','dbPass')">
                        <xsl:call-template name="displayRecord">
                            <xsl:with-param name="line" select="."/>
                        </xsl:call-template>
                </xsl:for-each>
            </recordList>


            <dbList>
                <xsl:variable name="sql">select id, formatName, extractStartIndex, extractEndIndex, extractEndIndexString from messageFormat;</xsl:variable>
                <xsl:variable name="columnSeparator">&#059;</xsl:variable>
                <xsl:variable name="nameValueSeparator">&#058;</xsl:variable>
                <xsl:value-of select="java:log('INFO',$sql)" />
                <xsl:for-each select="java:dbQuery($sql, ';', ':')">
                    <xsl:call-template name="displayDbRecord">
                        <xsl:with-param name="line" select="."/>
                        <xsl:with-param name="columnSeparator" select="$columnSeparator"/>
                        <xsl:with-param name="nameValueSeparator" select="$nameValueSeparator"/>
                    </xsl:call-template>
                </xsl:for-each>
            </dbList>

        </root>
    </xsl:template>

    <xsl:template name="displayRecord" >
        <xsl:param name="line"/>
        <record>
            <id><xsl:value-of select="tokenize($line,';')[1]" /></id>
            <name><xsl:value-of select="tokenize($line,';')[2]" /></name>
            <key><xsl:value-of select="tokenize($line,';')[3]" /></key>
        </record>
    </xsl:template>

    <xsl:template name="displayDbRecord" >
        <xsl:param name="line"/>
        <xsl:param name="columnSeparator"/>
        <xsl:param name="nameValueSeparator"/>
        <record>
            <xsl:for-each select="tokenize($line,$columnSeparator)">
                <xsl:variable name="nameValue" select="." />
                    <column name="{tokenize($nameValue,$nameValueSeparator)[1]}"><xsl:value-of select="tokenize($nameValue,$nameValueSeparator)[2]"/></column>
            </xsl:for-each>
        </record>

    </xsl:template>

</xsl:stylesheet>
