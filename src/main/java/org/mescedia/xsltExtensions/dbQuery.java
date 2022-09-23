package org.mescedia.xsltExtensions;

import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.lib.ExtensionFunctionCall;
import net.sf.saxon.lib.ExtensionFunctionDefinition;
import net.sf.saxon.om.Sequence;
import net.sf.saxon.om.StructuredQName;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.value.SequenceExtent;
import net.sf.saxon.value.SequenceType;
import net.sf.saxon.value.StringValue;
import org.mescedia.helper.DbDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class dbQuery extends ExtensionFunctionDefinition {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(dbQuery.class);

    @Override
    public StructuredQName getFunctionQName() {

        return new StructuredQName("", "http://xsltExtensions.mescedia.org", "dbQuery");
    }

    @Override
    public SequenceType[] getArgumentTypes() {

        return new SequenceType[]{
                SequenceType.SINGLE_STRING,  // sql
                SequenceType.SINGLE_STRING,  // columnSeparator
                SequenceType.SINGLE_STRING   // nameValueSaparator
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
            public Sequence<?> call(XPathContext context, Sequence[] arguments) throws XPathException {

                String sql = arguments[0].toString().trim().replace("\"", "");
                String fieldSeparator = arguments[1].toString().trim().replace("\"", "");
                String nameValueSeparator = arguments[2].toString().trim().replace("\"", "");

                Statement stm = null;
                String val = null;
                ResultSet rs = null;

                List<StringValue> list = new ArrayList<StringValue>();

                try {

                    DbDataProvider dataProvider = DbDataProvider.getInstance();

                    log.debug("execute dbQuery: " + sql);

                    stm = dataProvider.getConnection().createStatement();
                    rs = stm.executeQuery(sql);
                    ResultSetMetaData md = rs.getMetaData();

                    while (rs.next()) {

                        String row = "";
                        for (int c = 1; c <= md.getColumnCount(); c++) {

                            row += md.getColumnName(c) + nameValueSeparator + rs.getString(c) + fieldSeparator;
                        }

                        row = row.substring(0, row.length() - fieldSeparator.length());
                        list.add(new StringValue(row));

                        log.debug(" -> row: " + row);
                    }

                    if (!rs.isClosed())
                        rs.close();

                    if (!stm.isClosed())
                        stm.close();

                } catch (SQLException | IOException e) {
                    log.error(e.getMessage());
                    e.printStackTrace();
                }

                return new SequenceExtent(list);
            }
        };
    }
}
