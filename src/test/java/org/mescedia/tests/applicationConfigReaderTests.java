package org.mescedia.tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mescedia.helper.ApplicationConfig;
import org.mescedia.helper.ApplicationConfigReader;
import org.mescedia.helper.ApplicationProperties;
import org.mescedia.helper.DbDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class applicationConfigReaderTests {

    private static final Logger log = LoggerFactory.getLogger(applicationConfigReaderTests.class);

    @BeforeAll
    static void setup() throws Exception {
    }

    @BeforeEach
    void init() {}

    @Test
    void applicationConfigReaderTest() throws Exception {

        DbDataProvider.getInstance().readMessageFormats();

        ApplicationConfigReader reader = new ApplicationConfigReader();
        reader.read();

        assertTrue(
                ApplicationConfig.getInstance().getMessageFormatList().get(0).getName().equals("Edifact")
                && ApplicationConfig.getInstance().getMessageFormatList().get(1).getName().equals("DfdlXmlEdifact")
                && ApplicationConfig.getInstance().getMessageFormatList().get(2).getName().equals("SapIdocXml")
        );
    }

     @Test
    void propertyTest() throws IOException {

         ApplicationProperties appp = ApplicationProperties.getInstance();

         assertEquals(appp.getDbUrl(), "jdbc:sqlite:data/database/sqlite/mescedia2.db");
         assertEquals(appp.getDbUsername(), "dummy");
         assertEquals(appp.getDbPassword(), "dummy");

     }
}
