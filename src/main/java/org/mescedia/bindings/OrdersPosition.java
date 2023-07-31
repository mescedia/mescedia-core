package org.mescedia.bindings;

import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.FixedLengthRecord;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name = "ORDERS.Positions")
//@XmlAccessorType(XmlAccessType.FIELD)
//@FixedLengthRecord

//@XmlRootElement(name = "ORDERS.Positions")
//@XmlAccessorType(XmlAccessType.FIELD)
//@FixedLengthRecord(length=90, paddingChar=' ')
@FixedLengthRecord
public class OrdersPosition {

    @XmlElement(name="ArticleId")
    @DataField(pos = 1, length = 20, align = "L", trim = true)
    public String articleId ;

    @XmlElement(name="Quantity")
    @DataField(pos = 2, length = 10, align = "L", trim = true)
    public int quantity;

    @XmlElement(name="Description")
    @DataField(pos = 3, length = 60, align = "L", trim = true)
    public String description;

    @Override
    public String toString() {
        return "OrdersPosition [articleId=" + articleId + ", quantity=" + quantity + ", description="
                + description + "]";
    }

}
