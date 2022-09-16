package org.mescedia.xsltExtensions;

import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.lib.ExtensionFunctionCall;
import net.sf.saxon.lib.ExtensionFunctionDefinition;
import net.sf.saxon.om.Sequence;
import net.sf.saxon.om.SequenceTool;
import net.sf.saxon.om.StructuredQName;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.tree.iter.SingletonIterator;

import net.sf.saxon.value.SequenceType;
import net.sf.saxon.value.StringValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class logger extends ExtensionFunctionDefinition {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(logger.class);

    @Override
    public SequenceType[] getArgumentTypes() {
        return new SequenceType[]{SequenceType.SINGLE_STRING,SequenceType.SINGLE_STRING};
    }

    @Override
    public StructuredQName getFunctionQName() {
        return new StructuredQName("", "http://xsltExtensions.mescedia.org", "log");
    }

    @Override
    public SequenceType getResultType(SequenceType[] arg0) {
        return SequenceType.SINGLE_STRING;
    }

    @Override
    public ExtensionFunctionCall makeCallExpression() {
        return new ExtensionFunctionCall()
        {
            @Override
            public Sequence call(XPathContext context, Sequence[] arguments) throws XPathException {

                String type = arguments[0].toString().toUpperCase().replace("\"", "");
                String msg = arguments[1].toString().trim().replace("\"", "");

                if( type.equals("DEBUG"))
                    log.debug(msg);
                else if (type.equals("INFO"))
                    log.info(msg);
                else if (type.equals("WARN") || type.equals("WARNING"))
                    log.warn(msg);
                else if (type.equals("ERROR"))
                    log.error(msg);

                return SequenceTool.toLazySequence(SingletonIterator.makeIterator(new StringValue("")));
            }
        };
    }

}

