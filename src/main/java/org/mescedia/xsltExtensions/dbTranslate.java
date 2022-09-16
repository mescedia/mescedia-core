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
import org.mescedia.helper.DbDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dbTranslate extends ExtensionFunctionDefinition  {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(dbTranslate.class);

    @Override
    public StructuredQName getFunctionQName() {

        return new StructuredQName("", "http://xsltExtensions.mescedia.org", "translate");
    }

    @Override
    public SequenceType[] getArgumentTypes() {
                                    //table					 //key						 //value					//retrun field-name
        return new SequenceType[]{SequenceType.SINGLE_STRING,SequenceType.SINGLE_STRING,SequenceType.SINGLE_STRING,SequenceType.SINGLE_STRING};
    }

    @Override
    public SequenceType getResultType(SequenceType[] sequenceTypes) {

        return SequenceType.SINGLE_STRING;
    }

    @Override
    public ExtensionFunctionCall makeCallExpression() {


        return new ExtensionFunctionCall() {

            @Override
            public Sequence call(XPathContext context, Sequence[] arguments) throws XPathException {

                String table = arguments[0].toString().trim().replace("\"", "");
                String key = arguments[1].toString().trim().replace("\"", "");
                String value = arguments[2].toString().replace("\"", "");
                String returnField = arguments[3].toString().trim().replace("\"", "");

                Statement stm = null;
                String q, val = null;
                ResultSet rs = null;

                try {

                    DbDataProvider dataProvider = DbDataProvider.getInstance();
                    q = "select " + returnField + " from " + table +
                            " where " + key + "='" + value + "' limit 1;";

                    log.info("execute dbTranslate-query: " + q);

                    stm = dataProvider.getConnection().createStatement();
                    rs = stm.executeQuery(q);

                    if (rs.next()) {
                        val = rs.getString(returnField);
                    }

                    if (rs != null) {
                        if (rs.isClosed())
                            rs.close();
                    }

                    if (stm != null) {
                        if (stm.isClosed())
                            stm.close();
                    }

                } catch (SQLException | IOException e) {
                    log.error(e.getMessage());
                    e.printStackTrace();
                }

                return SequenceTool.toLazySequence(SingletonIterator.makeIterator(new StringValue(val)));
            }
        };
    }
}
