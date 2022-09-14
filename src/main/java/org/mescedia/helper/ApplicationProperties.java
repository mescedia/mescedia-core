package org.mescedia.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApplicationProperties {

    private String dbUsername, dbPassword, dbUrl ;
    private final String appConfigPath = "src/main/resources/application.properties";
    private static ApplicationProperties instance = null;

    private ApplicationProperties() throws IOException {
        this.readDbProperties();
    }

    public static ApplicationProperties getInstance() throws IOException {

        if ( instance == null ) {
            instance = new ApplicationProperties();
        }
        return instance ;
    }

    private void readDbProperties() throws IOException {

        Properties appProps = new Properties();
        appProps.load(new FileInputStream(this.appConfigPath));

        this.dbUrl = appProps.getProperty("db.url");
        this.dbUsername = appProps.getProperty("db.username");
        this.dbPassword = appProps.getProperty("db.password");
    }

    public String getDbUsername()   {

        return this.dbUsername;
    }

    public String getDbPassword()   {
        return this.dbPassword;
    }

    public String getDbUrl()    {

        return this.dbUrl;
    }
}
