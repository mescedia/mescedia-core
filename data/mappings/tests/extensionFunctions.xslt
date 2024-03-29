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

            <xsl:value-of select="java:log('DEBUG','this is a debug message')" />
            <xsl:value-of select="java:log('INFO','this is an info message')" />
            <xsl:value-of select="java:log('WARN','this is a warning message')" />
            <xsl:value-of select="java:log('ERROR','this is an error message')" />

            <xsl:variable name="msgType" select="/D96A:Interchange/D96A:Message/UNH/S009/E0065" />
            <xsl:variable name="msgVersion" select="concat(/D96A:Interchange/D96A:Message/UNH/S009/E0052, /D96A:Interchange/D96A:Message/UNH/S009/E0054)" />

            <message type="{$msgType}" version="{$msgVersion}" content-type="application/edifact" />


            <xsl:value-of select="java:log('INFO', concat('processing message: ',  $msgVersion, '.', $msgType) )" />

            <dbList>
                <!--
                    <xsl:variable name="dbConName">dbMariaDbDemoERP</xsl:variable>
                    <xsl:variable name="dbConName">dbPostgresDemoERP</xsl:variable>
                    <xsl:variable name="dbConName">dbSqliteDemoERP</xsl:variable>
                -->

                <xsl:variable name="dbConName">dbSqliteDemoERP</xsl:variable>

                <xsl:variable name="insertSql">INSERT INTO articles ( name, size, color, measureunit, stockavailable) values ('CCCCC','L','112123','PCE',222);</xsl:variable>
                <xsl:value-of select="java:dbQuery($dbConName, $insertSql, '', '')"/>

                <xsl:variable name="updateSql">update articles set name='DDDDD' where name='CCCCC';</xsl:variable>
                <xsl:value-of select="java:dbQuery($dbConName, $updateSql, '', '')"/>


                <xsl:variable name="deleteSql">delete from articles where id > 5;</xsl:variable>
                <xsl:value-of select="java:dbQuery($dbConName, $deleteSql, '', '')"/>



                <xsl:variable name="selectSql">select * from articles order by id;</xsl:variable>
                <xsl:for-each select="java:dbQuery($dbConName, $selectSql, ';', ':')">
                    <xsl:call-template name="displayDbRecord">
                        <xsl:with-param name="line" select="."/>
                    </xsl:call-template>
                </xsl:for-each>
            </dbList>

        </root>
    </xsl:template>

    <xsl:template name="displayDbRecord" >
        <xsl:param name="line"/>
        <xsl:variable name="columnSeparator">&#059;</xsl:variable> <!-- must match specialChar in dbQuery() arg -->
        <xsl:variable name="nameValueSeparator">&#058;</xsl:variable> <!-- must match specialChar in dbQuery() arg -->
        <record>
            <xsl:for-each select="tokenize($line,$columnSeparator)">
                <xsl:variable name="nameValue" select="." />
                    <column name="{tokenize($nameValue,$nameValueSeparator)[1]}"><xsl:value-of select="tokenize($nameValue,$nameValueSeparator)[2]"/></column>
            </xsl:for-each>
        </record>

    </xsl:template>

</xsl:stylesheet>
