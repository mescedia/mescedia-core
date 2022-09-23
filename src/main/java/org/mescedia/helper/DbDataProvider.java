package org.mescedia.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mescedia.analyser.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbDataProvider  {

    protected Connection connection = null;
    protected Statement sql = null;
    protected ResultSet resultSet = null;

    public static DbDataProvider instance = null;

    private BasicDataSource dataSource = new BasicDataSource();
    private List<MessageFormat> messageFormatList = new ArrayList<MessageFormat>() ;

    private static final Logger log = LoggerFactory.getLogger(DbDataProvider.class);

    private DbDataProvider() throws SQLException, IOException {

        initDb();
        checkDbConnection();
    }

    private void initDb() throws IOException {

        ApplicationProperties appProps = ApplicationProperties.getInstance();
        this.dataSource = new BasicDataSource();
        this.dataSource.setUrl(appProps.getDbUrl());
        this.dataSource.setUsername(appProps.getDbUsername());
        this.dataSource.setPassword(appProps.getDbPassword());
    }

    private void checkDbConnection() throws SQLException	{

        if (this.connection == null)	{
            log.info("Initialise database connection ...");
            this.connection = this.dataSource.getConnection() ;
        }
        else if (!this.connection.isValid(300))	{
            log.info("Create new database connection ...");
            this.connection.close();
            this.connection = this.dataSource.getConnection() ;
        }
    }

    public Connection getConnection() throws SQLException {

        this.checkDbConnection();
        return this.connection;
    }

    public static DbDataProvider getInstance() throws IOException, SQLException {

        if (instance == null)   {
            instance = new DbDataProvider();
        }
        return instance ;
    }

    // todo: test if connectionCache required ...
    public static Connection createConnection(String connName) throws SQLException, IOException {

        DbConnectionData dbConnData = DbDataProvider.getInstance().getDbConnectionData(connName);

        BasicDataSource dSource = new BasicDataSource();
        dSource.setUrl(dbConnData.getUri());
        dSource.setUsername(dbConnData.getUser());
        dSource.setPassword(dbConnData.getPassword());

        return dSource.getConnection() ;
    }

    public DbConnectionData getDbConnectionData(String connName) throws SQLException {

        this.checkDbConnection();

        String query = "SELECT name, url, user, passwd from dbConnections where name = '"+connName+"';";

        log.debug(query);

        this.sql = this.connection.createStatement();
        this.resultSet = this.sql.executeQuery(query);

        DbConnectionData dbConn = null;
        if (resultSet.next())   {

            dbConn = new DbConnectionData();
            dbConn.setName( resultSet.getString("name")  );
            dbConn.setUri( resultSet.getString("url")  );
            dbConn.setUser( resultSet.getString("user")  );
            dbConn.setPassword( resultSet.getString("passwd")  );

        }
        return dbConn;
    }

    public List<MessageFormat> getMessageFormatTable() {

        return this.messageFormatList;
    }

    public void readMessageFormats() throws Exception {

        this.checkDbConnection();

        String query = "SELECT id, formatName, extractStartIndex, extractEndIndex, extractEndIndexString, active " +
                " FROM messageFormat order by id; ";

        this.sql = this.connection.createStatement();
        this.resultSet = this.sql.executeQuery(query);

        MessageFormat mf = null;

        while (resultSet.next())	{
            mf = new MessageFormat();
            mf.setId(resultSet.getInt("id"));
            mf.setName(resultSet.getString("formatName"));
            mf.setExtractStartIndex(resultSet.getInt("extractStartIndex"));
            mf.setExtractEndIndex(resultSet.getInt("extractEndIndex"));
            mf.setExtractEndIndexString(resultSet.getString("extractEndIndexString"));

            log.debug("Format -> id: " + String.valueOf(resultSet.getInt("id"))
                    + " formatName: "  + String.valueOf(resultSet.getString("formatName"))
                    + " extractStartIndex: "  + String.valueOf(resultSet.getInt("extractStartIndex"))
                    + " extractEndIndex: "  + String.valueOf(resultSet.getInt("extractEndIndex"))
                    + " extractEndIndexString: "  + String.valueOf(resultSet.getString("extractEndIndexString"))
                    + " active: "  + String.valueOf(resultSet.getInt("active"))
            );

            mf.setFormatAnalyser( this.getMessageFormatAnalyser(mf.getId() ) );
            mf.setContentAnalyser( this.getMessageContentAnalyser(mf.getId() ) );

            this.messageFormatList.add(mf) ;
        }

        if (this.resultSet != null) 	{
            if (!this.resultSet.isClosed())	{
                this.resultSet.close();
            }
        }

        if (this.sql != null )	{
            if (!this.sql.isClosed())
                this.sql.close();
        }
    }

    public MessageFormatAnalyser getMessageFormatAnalyser(int formatId) throws SQLException {

        this.checkDbConnection();

        String query = "select id, formatId, rType, rValue, active FROM formatAnalyserRule where formatId = "
                +formatId+" and active=1 order by id;";

        Statement sqlMfa = this.connection.createStatement();
        ResultSet rsMfa = sqlMfa.executeQuery(query);

        MessageFormatAnalyser mfa = new MessageFormatAnalyser();

        while (rsMfa.next()) {
            log.debug("MessageFormatAnalyser -> id: " + String.valueOf(rsMfa.getInt("id"))
                    + " formatID: "  + String.valueOf(rsMfa.getInt("formatId"))
                    + " rType: "  + String.valueOf(rsMfa.getString("rType"))
                    + " rValue: "  + String.valueOf(rsMfa.getString("rValue"))
                    + " active: "  + String.valueOf(rsMfa.getInt("active")) );

            MessageFormatAnalyserRule r = new MessageFormatAnalyserRule();
            r.setType(rsMfa.getString("rType"));
            r.setValue(rsMfa.getString("rValue"));

            mfa.addRule( r );
        }

        if (rsMfa != null) 	{
            if (rsMfa.isClosed())	{
                rsMfa.close();
            }
        }

        if (sqlMfa != null )	{
            if (sqlMfa.isClosed())
                sqlMfa.close();
        }

        return mfa;
    }

    public MessageContentAnalyser getMessageContentAnalyser(int formatId) throws SQLException {

        this.checkDbConnection();

        String query = "select id, formatId, rItem, rType, rValue, active FROM contentAnalyserRule where formatId = "
                +formatId+" and active=1 order by id;";

        Statement sqlMca = this.connection.createStatement();
        ResultSet rsMca = sqlMca.executeQuery(query);

        MessageContentAnalyser mca = new MessageContentAnalyser();

        while (rsMca.next()) {
            log.debug("MessageContentAnalyser -> id: " + String.valueOf(rsMca.getInt("id"))
                    + " formatID: "  + String.valueOf(rsMca.getInt("formatId"))
                    + " rItem: "  + String.valueOf(rsMca.getString("rItem"))
                    + " rType: "  + String.valueOf(rsMca.getString("rType"))
                    + " rValue: "  + String.valueOf(rsMca.getString("rValue"))
                    + " active: "  + String.valueOf(rsMca.getInt("active")) );

            MessageContentAnalyserRule r = new MessageContentAnalyserRule();
            r.setItem(rsMca.getString("rItem"));
            r.setType(rsMca.getString("rType"));
            r.setValue(rsMca.getString("rValue"));

            mca.addRule( r );
        }

        if (rsMca != null) 	{
            if (rsMca.isClosed())	{
                rsMca.close();
            }
        }

        if (sqlMca != null )	{
            if (sqlMca.isClosed())
                sqlMca.close();
        }

        return mca;
    }
}
