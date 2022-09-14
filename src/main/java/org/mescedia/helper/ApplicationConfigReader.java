package org.mescedia.helper;

import org.mescedia.analyser.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;


public class ApplicationConfigReader {

    private static final Logger log = LoggerFactory.getLogger(ApplicationConfigReader.class);

    private ApplicationConfig appConfig = ApplicationConfig.getInstance();


    public ApplicationConfigReader() {}

    public void read() throws XPathExpressionException, ParserConfigurationException, IOException, URISyntaxException, SAXException, SQLException {

        DbDataProvider dbData = DbDataProvider.getInstance();
        List<MessageFormat> mfTable = dbData.getMessageFormatTable();

        for (MessageFormat mFormat : mfTable)	{

            log.debug("loading MessageFormat rules -> id: " + mFormat.getId()
                    + " formatName: "  + mFormat.getName()
                    + " extractStartIndex: "  + mFormat.getExtractStartIndex()
                    + " extractEndIndex: "  + mFormat.getExtractEndIndex()
                    + " extractEndIndexString: "  + mFormat.getExtractEndIndexString() );

            mFormat.setFormatAnalyser(dbData.getMessageFormatAnalyser(mFormat.getId()));
            mFormat.setContentAnalyser(dbData.getMessageContentAnalyser(mFormat.getId()));

            appConfig.addMessageFormat(mFormat);
        }
    }
}
