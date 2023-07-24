<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:D01B="http://www.ibm.com/dfdl/edi/un/edifact/D96A"
                xmlns:srv="http://www.ibm.com/dfdl/edi/un/service/4.1"
                xmlns:java="http://xsltExtensions.mescedia.org"
                exclude-result-prefixes="D01B java">
    <xsl:output omit-xml-declaration="yes" indent="no"/>
    <xsl:strip-space elements="*"/>

    <xsl:template name='xsl:initial-template' />

    <xsl:template match="/">

        <xsl:variable name="controlValue" select="9999999999" />

        <xsl:variable name="msgType" select="/ERP/@type" />
        <xsl:variable name="msgVersion" select="/ERP/@version" />
        <xsl:variable name="documentId" select="/ERP/message/@documentId" />
        <xsl:variable name="senderId" select="/ERP/@senderId" />
        <xsl:variable name="receiverId" select="/ERP/@receiverId" />

        <xsl:value-of select="java:log('INFO',concat('Processing mapping on message Type/Version/DocumentId/Sender/Receiver: ', $msgType, '/', $msgVersion, '/', $documentId, '/', $senderId, '/', $receiverId ))" />

        <D01B:Interchange xmlns:D01B="http://www.ibm.com/dfdl/edi/un/edifact/D01B" xmlns:srv="http://www.ibm.com/dfdl/edi/un/service/4.1" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:call-template name="UNA">
            </xsl:call-template>

            <xsl:call-template name="UNB">
                <xsl:with-param name="senderId" select="$senderId"/>
                <xsl:with-param name="receiverId" select="$receiverId"/>
                <xsl:with-param name="controlValue" select="$controlValue"/>
            </xsl:call-template>

            <xsl:variable name="msgRef" select="8888888888" />

            <D01B:Message>

                <xsl:call-template name="UNH">
                    <xsl:with-param name="msgRef" select="$msgRef"/>
                </xsl:call-template>

                <D01B:DESADV>
                    <xsl:call-template name="BGM">
                        <xsl:with-param name="documentId" select="$documentId"/>
                    </xsl:call-template>
                    <xsl:call-template name="DTM">
                        <xsl:with-param name="functionCode" select="137"/>
                        <xsl:with-param name="dateYYYYMMDD" select="format-date(current-date(),'[Y0001][M01][D01]')"/>
                        <xsl:with-param name="formatCode" select="102"/>
                    </xsl:call-template>
                    <xsl:call-template name="DTM">
                        <xsl:with-param name="functionCode" select="17"/>
                        <xsl:with-param name="dateYYYYMMDD" select="format-date(current-date(),'[Y0001][M01][D01]')"/>
                        <xsl:with-param name="formatCode" select="102"/>
                    </xsl:call-template>
                    <SegGrp-1>
                        <xsl:call-template name="RFF">
                            <xsl:with-param name="refCodeQualif">VN</xsl:with-param>
                            <xsl:with-param name="refIdent" select="/ERP/message/vendorNumber"/>
                        </xsl:call-template>
                    </SegGrp-1>
                    <SegGrp-1>
                        <xsl:call-template name="RFF">
                            <xsl:with-param name="refCodeQualif">ON</xsl:with-param>
                            <xsl:with-param name="refIdent" select="/ERP/message/orderNumber"/>
                        </xsl:call-template>
                    </SegGrp-1>
                    <SegGrp-1>
                        <xsl:call-template name="RFF">
                            <xsl:with-param name="refCodeQualif">DQ</xsl:with-param>
                            <xsl:with-param name="refIdent" select="/ERP/message/deliveryNoteId"/>
                        </xsl:call-template>
                    </SegGrp-1>
                    <SegGrp-2>
                        <xsl:call-template name="NAD">
                            <xsl:with-param name="e3035">SU</xsl:with-param>
                            <xsl:with-param name="e3039" select="/ERP/message/supplierId"/>
                            <xsl:with-param name="e3055">9</xsl:with-param>
                        </xsl:call-template>
                    </SegGrp-2>
                    <SegGrp-2>
                        <xsl:call-template name="NAD">
                            <xsl:with-param name="e3035">BY</xsl:with-param>
                            <xsl:with-param name="e3039" select="/ERP/message/buyerId"/>
                            <xsl:with-param name="e3055">9</xsl:with-param>
                        </xsl:call-template>
                    </SegGrp-2>
                    <SegGrp-2>
                        <xsl:call-template name="NAD">
                            <xsl:with-param name="e3035">DP</xsl:with-param>
                            <xsl:with-param name="e3039" select="/ERP/message/deliveryPartyId"/>
                            <xsl:with-param name="e3055">9</xsl:with-param>
                        </xsl:call-template>
                    </SegGrp-2>
                    <SegGrp-10>
                        <xsl:call-template name="CPS">
                            <xsl:with-param name="e7164">1</xsl:with-param>
                        </xsl:call-template>
                        <SegGrp-11>
                            <xsl:call-template name="PAC">
                                <xsl:with-param name="e7224" select="/ERP/message/delivery/packages/@quantity"/>
                                <xsl:with-param name="e7065" select="201"/>
                            </xsl:call-template>
                            <xsl:call-template name="MEA">
                                <xsl:with-param name="e6311">PD</xsl:with-param>
                                <xsl:with-param name="e6313">AAD</xsl:with-param>
                                <xsl:with-param name="e6411">KGM</xsl:with-param>
                                <xsl:with-param name="e6314" select="/ERP/message/delivery/packages/grpssweight/@value"/>
                            </xsl:call-template>
                        </SegGrp-11>
                    </SegGrp-10>

                    <xsl:for-each select="/ERP/message/delivery//package">
                        <SegGrp-10>
                            <xsl:call-template name="CPS">
                                <xsl:with-param name="e7164" select="position()+1"/>
                            </xsl:call-template>
                            <SegGrp-11>
                                <xsl:call-template name="PAC">
                                    <xsl:with-param name="e7224">1</xsl:with-param>
                                    <xsl:with-param name="e7065" select="@type"/>
                                </xsl:call-template>
                                <SegGrp-13>
                                    <xsl:call-template name="PCI">
                                        <xsl:with-param name="e4233">33E</xsl:with-param>
                                    </xsl:call-template>
                                    <SegGrp-15>
                                        <xsl:call-template name="GIN">
                                            <xsl:with-param name="e7405">BJ</xsl:with-param>
                                            <xsl:with-param name="e7402" select="SSCC/text()" />
                                        </xsl:call-template>
                                    </SegGrp-15>
                                </SegGrp-13>
                            </SegGrp-11>
                           <SegGrp-17>
                               <xsl:call-template name="LIN">
                                   <xsl:with-param name="e1082" select="@id" />
                                   <xsl:with-param name="e7140" select="article/id/text()" />
                                   <xsl:with-param name="e7143">SRV</xsl:with-param>
                               </xsl:call-template>
                               <xsl:call-template name="PIA">
                                   <xsl:with-param name="e4347">1</xsl:with-param>
                                   <xsl:with-param name="e7140" select="article/id/text()" />
                                   <xsl:with-param name="e7143">SA</xsl:with-param>
                                   <xsl:with-param name="e3055">91</xsl:with-param>
                               </xsl:call-template>
                               <xsl:call-template name="QTY">
                                   <xsl:with-param name="e6063">1</xsl:with-param>
                                   <xsl:with-param name="e6060" select="article/quantity/text()" />
                                   <xsl:with-param name="e6411">PCE</xsl:with-param>
                               </xsl:call-template>
                               <xsl:call-template name="FTX">
                                   <xsl:with-param name="e4451">AFM</xsl:with-param>
                                   <xsl:with-param name="e4453">1</xsl:with-param>
                                   <xsl:with-param name="e4441">AAA</xsl:with-param>
                                   <xsl:with-param name="e4440" select="article/description/text()" />
                               </xsl:call-template>
                           </SegGrp-17>
                        </SegGrp-10>
                    </xsl:for-each>
                </D01B:DESADV>

                <xsl:call-template name="UNT">
                    <xsl:with-param name="msgRef" select="$msgRef"/>
                    <xsl:with-param name="numSegmentMsg" select="7777"/>
                </xsl:call-template>

            </D01B:Message>

            <xsl:call-template name="UNZ">
                <xsl:with-param name="numMsg" select="count(/ERP//message)"/>
                <xsl:with-param name="controlValue" select="$controlValue"/>
            </xsl:call-template>

        </D01B:Interchange>

    </xsl:template>



    <!-- =============================================== -->
    <!-- TEMPLATES   =================================== -->
    <!-- =============================================== -->

    <xsl:template name="FTX">
        <xsl:param name="e4451"/>
        <xsl:param name="e4453"/>
        <xsl:param name="e4441"/>
        <xsl:param name="e4440"/>
        <FTX>
            <E4451><xsl:value-of select="$e4451"/></E4451>
            <E4453><xsl:value-of select="$e4453"/></E4453>
            <C107>
                <E4441><xsl:value-of select="$e4441"/></E4441>
            </C107>
            <C108>
                <E4440><xsl:value-of select="$e4440"/></E4440>
            </C108>
        </FTX>
    </xsl:template>

    <xsl:template name="QTY">
        <xsl:param name="e6063"/>
        <xsl:param name="e6060"/>
        <xsl:param name="e6411"/>
        <QTY>
            <C186>
                <E6063><xsl:value-of select="$e6063"/></E6063>
                <E6060><xsl:value-of select="$e6060"/></E6060>
                <E6411><xsl:value-of select="$e6411"/></E6411>
            </C186>
        </QTY>
    </xsl:template>

    <xsl:template name="PIA">
        <xsl:param name="e4347"/>
        <xsl:param name="e7140"/>
        <xsl:param name="e7143"/>
        <xsl:param name="e3055"/>
        <PIA>
            <E4347><xsl:value-of select="$e4347"/></E4347>
            <C212>
                <E7140><xsl:value-of select="$e7140"/></E7140>
                <E7143><xsl:value-of select="$e7143"/></E7143>
                <E3055><xsl:value-of select="$e3055"/></E3055>
            </C212>
        </PIA>
    </xsl:template>

    <xsl:template name="LIN">
        <xsl:param name="e1082"/>
        <xsl:param name="e7140"/>
        <xsl:param name="e7143"/>

        <LIN>
            <E1082><xsl:value-of select="$e1082"/></E1082>
            <C212>
                <E7140><xsl:value-of select="$e7140"/></E7140>
                <E7143><xsl:value-of select="$e7143"/></E7143>
            </C212>
        </LIN>
    </xsl:template>

    <xsl:template name="GIN">
        <xsl:param name="e7405"/>
        <xsl:param name="e7402"/>
        <GIN>
            <E7405><xsl:value-of select="$e7405"/></E7405>
            <C208>
                <E7402><xsl:value-of select="$e7402"/></E7402>
            </C208>
        </GIN>
    </xsl:template>


    <xsl:template name="PCI">
        <xsl:param name="e4233"/>
        <PCI>
            <E4233><xsl:value-of select="$e4233"/></E4233>
        </PCI>
    </xsl:template>

    <xsl:template name="MEA">
        <xsl:param name="e6311"/>
        <xsl:param name="e6313"/>
        <xsl:param name="e6411"/>
        <xsl:param name="e6314"/>
        <MEA>
            <E6311><xsl:value-of select="$e6311"/></E6311>
            <C502>
                <E6313><xsl:value-of select="$e6313"/></E6313>
            </C502>
            <C174>
                <E6411><xsl:value-of select="$e6411"/></E6411>
                <E6314><xsl:value-of select="$e6314"/></E6314>
            </C174>
        </MEA>
    </xsl:template>

    <xsl:template name="PAC">
        <xsl:param name="e7224"/>
        <xsl:param name="e7065"/>
        <PAC>
            <E7224><xsl:value-of select="$e7224"/></E7224>
            <C202>
                <E7065><xsl:value-of select="$e7065"/></E7065>
            </C202>
        </PAC>
    </xsl:template>

    <xsl:template name="CPS">
        <xsl:param name="e7164"/>
        <CPS>
            <E7164><xsl:value-of select="$e7164"/></E7164>
        </CPS>
    </xsl:template>

    <xsl:template name="NAD">
        <xsl:param name="e3035"/>
        <xsl:param name="e3039"/>
        <xsl:param name="e3055"/>
        <NAD>
            <E3035><xsl:value-of select="$e3035"/></E3035>
            <C082>
                <E3039><xsl:value-of select="$e3039"/></E3039>
                <E3055><xsl:value-of select="$e3055"/></E3055>
            </C082>
        </NAD>
    </xsl:template>

    <xsl:template name="RFF">
        <xsl:param name="refCodeQualif"/>
        <xsl:param name="refIdent"/>
        <RFF>
            <C506>
                <E1153><xsl:value-of select="$refCodeQualif"/></E1153>
                <E1154><xsl:value-of select="$refIdent"/></E1154>
            </C506>
        </RFF>
    </xsl:template>

    <xsl:template name="DTM">
        <xsl:param name="functionCode"/>
        <xsl:param name="dateYYYYMMDD"/>
        <xsl:param name="formatCode"/>
        <DTM>
            <C507>
                <E2005><xsl:value-of select="$functionCode"/></E2005>
                <E2380><xsl:value-of select="$dateYYYYMMDD"/></E2380>
                <E2379><xsl:value-of select="$formatCode"/></E2379>
            </C507>
        </DTM>
    </xsl:template>

    <xsl:template name="BGM">
        <xsl:param name="documentId"/>
        <BGM>
            <C002>
                <E1001>351</E1001>
                <E3055>9</E3055>
            </C002>
            <C106>
                <E1004><xsl:value-of select="$documentId"/></E1004>
            </C106>
            <E1225>9</E1225>
        </BGM>
    </xsl:template>

    <xsl:template name="UNB">
        <xsl:param name="senderId"/>
        <xsl:param name="receiverId"/>
        <xsl:param name="controlValue"/>

        <UNB>
            <S001>
                <E0001>UNOC</E0001>
                <E0002>3</E0002>
            </S001>
            <S002>
                <E0004><xsl:value-of select="$senderId"/></E0004>
                <E0007>ZZZ</E0007>
            </S002>
            <S003>
                <E0010><xsl:value-of select="$receiverId"/></E0010>
                <E0007>ZZZ</E0007>
            </S003>
            <S004>
                <E0017><xsl:value-of select="format-date(current-date(),'[Y0001][M01][D01]')"/></E0017>
                <E0019><xsl:value-of select="format-time(current-time(),'[H01][m01]')"/></E0019>
            </S004>
            <E0020><xsl:value-of select="$controlValue"/></E0020>
            <S005>
                <E0022></E0022>
            </S005>
            <E0032>EANCOM</E0032>
        </UNB>
    </xsl:template>

    <xsl:template name="UNA" >
        <UNA>
            <CompositeSeparator>:</CompositeSeparator>
            <FieldSeparator>+</FieldSeparator>
            <DecimalSeparator>.</DecimalSeparator>
            <EscapeCharacter>?</EscapeCharacter>
            <RepeatSeparator>&#160;</RepeatSeparator>
            <SegmentTerminator>'</SegmentTerminator>
        </UNA>
    </xsl:template>

    <xsl:template name="UNZ">
        <xsl:param name="numMsg"/>
        <xsl:param name="controlValue"/>
        <UNZ>
            <E0036><xsl:value-of select="$numMsg"/></E0036>
            <E0020><xsl:value-of select="$controlValue"/></E0020>
        </UNZ>
    </xsl:template>

    <xsl:template name="UNH">
        <xsl:param name="msgRef"/>
        <UNH>
            <E0062><xsl:value-of select="$msgRef"/></E0062>
            <S009>
                <E0065>DESADV</E0065>
                <E0052>D</E0052>
                <E0054>01B</E0054>
                <E0051>UN</E0051>
                <E0057>EAN007</E0057>
            </S009>
        </UNH>
    </xsl:template>

    <xsl:template name="UNT">
        <xsl:param name="msgRef"/>
        <xsl:param name="numSegmentMsg"/>
        <UNT>
            <E0074><xsl:value-of select="$numSegmentMsg"/></E0074>
            <E0062><xsl:value-of select="$msgRef"/></E0062>
        </UNT>
    </xsl:template>

</xsl:stylesheet>
