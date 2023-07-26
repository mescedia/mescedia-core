package org.mescedia.bindings;

import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.FixedLengthRecord;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/*
* UNA:+.? '
UNB+UNOC:3+20000:ZZZ+10000:ZZZ+20230721:1029+60001'
UNH+1222222+ORDERS:D:96A:UN:EAN008'
BGM+220+ORDERS_20000_10000_001+9'
DTM+137:20230721:102'
NAD+BY+20000000000000::9'
NAD+SU+10000000000000::9'
CTA+AL+123456790:Reed O. Lee'
COM+799(11)777-222/555444:AE'
LIN+1+1+10007456104:IN'
QTY+1:25'
FTX+AFM+1++All Sort of Things'
LIN+2++20074569099:IN'
QTY+1:25'
FTX+AFM+1++The Mobbit'
LIN+3++3007004656:IN'
QTY+1:16'
FTX+AFM+1++The Gilmarillion'
LIN+4++40076006777:IN'
QTY+1:10'
FTX+AFM+1++The Sons of the Desert'
UNS+S'
CNT+2:4'
UNT+22+1222222'
UNZ+1+60001'
*
* <ERP type="DESADV" version="D01B" senderId="10000" receiverId="20000">

    <message documentId="DESADV_10000_20000_001">

        <vendorNumber>757502</vendorNumber>
        <orderNumber>31259762</orderNumber>
        <deliveryNoteId>75722502</deliveryNoteId>

        <supplierId>10000000000000</supplierId>
        <buyerId>20000000000000</buyerId>
        <deliveryPartyId>30000000000000</deliveryPartyId>

        <delivery>

            <packages quantity="4">
                <grpssweight value="30.30" />
                <SSCC>110000000000000078</SSCC>
            </packages>

            <package id="1" type="CT">
                <SSCC>110000000000000011</SSCC>
                <article>
                    <id>10007456104</id>
                    <quantity>25</quantity>
                    <description>All Sort of Things</description>
                </article>
            </package>

            <package id="2" type="CT">
                <SSCC>110000000000000022</SSCC>
                <article>
                    <id>20074569099</id>
                    <quantity>25</quantity>
                    <description>The Mobbit</description>
                </article>
            </package>

            <package id="3" type="CT">
                <SSCC>110000000000000033</SSCC>
                <article>
                    <id>3007004656</id>
                    <quantity>16</quantity>
                    <description>The Gilmarillion</description>
                </article>
            </package>

            <package id="4" type="CT">
                <SSCC>110000000000000044</SSCC>
                <article>
                    <id>40076006777</id>
                    <quantity>10</quantity>
                    <description>The Sons of the Desert</description>
                </article>
            </package>

        </delivery>

    </message>
</ERP>
* */

@XmlRootElement(name = "ORDERS.Xml")
@XmlAccessorType(XmlAccessType.FIELD)
@FixedLengthRecord(length=80, paddingChar=' ')
public class ORDERS_FixedLength2Xml {

    @XmlElement(name="Sender")
    @DataField(pos = 1, length = 10)
    public String sender;

    @XmlElement(name="Receiver")
    @DataField(pos = 10, length = 10)
    public String receiver;

    @XmlElement(name="MessageFormat")
    @DataField(pos = 20, length = 20)
    public String messageFormat;

    @XmlElement(name="MessageType")
    @DataField(pos = 40, length = 20, align = "L")
    public String messageType;

    @XmlElement(name="MessageVersion")
    @DataField(pos = 60, length = 20)
    public String messageVersion;

    @Override
    public String toString() {
        return "ORDERS_FixedLength [sender=" + sender + ", receiver=" + receiver + ", messageFormat="
                + messageFormat + ", messageType=" + messageType
                + ", messageVersion=" + messageVersion + "]";
    }
}
