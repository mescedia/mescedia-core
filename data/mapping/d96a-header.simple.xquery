xquery version "3.0";
declare namespace D96A="http://www.ibm.com/dfdl/edi/un/edifact/D96A";

(: not an expert on xquery here - lol :)

declare variable $contentType as xs:string := "application/edifact";


<root xmlns:D96A="http://www.ibm.com/dfdl/edi/un/edifact/D96A" xmlns:srv="http://www.ibm.com/dfdl/edi/un/service/4.1">
    <!--=======================-->
    <!--input analyser result file-->
    <!--=======================-->
    <interface type="inbound" user="test">AS2-TEST</interface>
    <message content-type="{$contentType}" type="{/D96A:Interchange/D96A:Message/UNH/S009/E0065}" version="{concat(/D96A:Interchange/D96A:Message/UNH/S009/E0052, /D96A:Interchange/D96A:Message/UNH/S009/E0054)}" />
    <sender type="{/D96A:Interchange/UNB/S002/E0007}">{/D96A:Interchange/UNB/S002/E0004/text()}</sender>
    <receiver type="{/D96A:Interchange/UNB/S003/E0007}">{/D96A:Interchange/UNB/S003/E0010/text()}</receiver>
</root>