package org.mescedia.xsltExtensions;

import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.lib.ExtensionFunctionCall;
import net.sf.saxon.lib.ExtensionFunctionDefinition;
import net.sf.saxon.ma.map.MapFunctionSet;
import net.sf.saxon.om.Sequence;
import net.sf.saxon.om.StructuredQName;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.value.SequenceExtent;
import net.sf.saxon.value.SequenceType;
import net.sf.saxon.value.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class listDemo extends ExtensionFunctionDefinition {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(listDemo.class);

    @Override
    public StructuredQName getFunctionQName() {

        return new StructuredQName("", "http://xsltExtensions.mescedia.org", "listDemo");
    }

    @Override
    public SequenceType[] getArgumentTypes() {  // dummy args
        return new SequenceType[] {
                SequenceType.OPTIONAL_STRING,
                SequenceType.OPTIONAL_STRING,
                SequenceType.OPTIONAL_STRING
        };
    }

    @Override
    public SequenceType getResultType(SequenceType[] sequenceTypes) {

        return SequenceType.STRING_SEQUENCE;
    }

    @Override
    public ExtensionFunctionCall makeCallExpression() {

        return new ExtensionFunctionCall()
        {
            @Override
            public Sequence<?> call(XPathContext xPathContext, Sequence[] sequences) throws XPathException {

                List<StringValue> list = new ArrayList<StringValue>();

                list.add(new StringValue("1111111" + ";" + "aaaaaaaa" + ";" + "1a1a1a"  ));
                list.add(new StringValue("2222222" + ";" + "bbbbbbbb" + ";" + "2b2b2b"  ));
                list.add(new StringValue("3333333" + ";" + "cccccccc" + ";" + "3c3c3c"  ));


                return new SequenceExtent(list);
            }
        };
    }

}
