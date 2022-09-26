package org.mescedia.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DbDataSource {

    private String name;
    private String uri;
    private String username;
    private String password ;
    private BasicDataSource dataSource = null;
    private Connection connection = null;

    private static final Logger log = LoggerFactory.getLogger(DbDataSource.class);

    public Connection getConnection() throws SQLException {

        if(this.connection == null ) {
            this.dataSource = this.getDataSource();
            this.connection = this.dataSource.getConnection();
        }
        else if (!this.connection.isValid(300))	{
            log.info("["+this.name+"] initialize new database connection ...");
            this.connection.close();
            this.connection = this.dataSource.getConnection() ;
        }

        return this.connection;
    }

    private BasicDataSource getDataSource()  {

        if(this.dataSource == null)    {
            dataSource = new BasicDataSource();
            dataSource.setUrl(this.getUri());
            dataSource.setUsername(this.getUserName());
            dataSource.setPassword(this.getPassword());
        }
        return dataSource;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
