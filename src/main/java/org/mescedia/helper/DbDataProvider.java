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

    private List<DbDataSource> dbDataSourceList = new ArrayList<DbDataSource>() ;

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
            log.info("Initialize new SystemDatabase connection ...");
            this.connection = this.dataSource.getConnection() ;
        }
        else if (!this.connection.isValid(300))	{
            log.info("Create new SystemDatabase connection ...");
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

    public Connection getExternalDbConnection(String connName) throws Exception {

        for (DbDataSource ds : this.dbDataSourceList)  {

            if (ds.getName().toUpperCase().equals(connName.toUpperCase())) {

                log.info("["+connName+"] using cached dbConnection ...");
                return ds.getConnection();
            }
        }

        return this.createNewExternalConnection(connName);
    }

    private Connection createNewExternalConnection(String connName) throws Exception {

        this.checkDbConnection();
        String query = "SELECT name, url, username, passwd from dbConnections where name = '"+connName+"';";

        this.sql = this.connection.createStatement();
        this.resultSet = this.sql.executeQuery(query);

        if (resultSet.next())   {
            DbDataSource db = new DbDataSource();
            db.setName( resultSet.getString("name")  );
            db.setUri( resultSet.getString("url")  );
            db.setUserName( resultSet.getString("username")  );
            db.setPassword( resultSet.getString("passwd")  );

            this.dbDataSourceList.add(db) ;

            log.info("["+connName+"] initialized new dbConnection ...");
            return db.getConnection();
        }

        throw new Exception("["+connName+"] dataSource not defined ...");
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

    public String[] getDestinationProcessSetId(MessageMetaInfo mi, String interfaceIn) throws SQLException {

        this.checkDbConnection();

        String query = "select iOut.destination as destination, pr.processSetId as processSetId from processRules pr left join interfaceOut iOut " +
            " ON iOut.id = pr.interfaceOutId " +
            " where " +
                "     interfaceInId  = (select id from interfaceIn  where source = '"+interfaceIn+"') " +
                " and senderUserId   = (select id from user  where value = '"+mi.getSenderId()+"') " +
                " and receiverUserId = (select id from user  where value = '"+mi.getReceiverId()+"') " +
                " and msgFormatId    = (select id from messageFormat where formatName = '"+mi.getMessageFormat()+"') " +
                " and msgTypeId      = (select id from messageTypes  where typeName = '"+mi.getMessageType()+"') " +
                " and msgVersionId   = (select id from messageVersions  where versionName = '"+mi.getMessageVersion()+"' ) ; " ;

        Statement sqlStat = this.connection.createStatement();
        ResultSet resultSet = sqlStat.executeQuery(query);

        String destination = null ;
        String processSetId =  null; // actually int

        if (resultSet.next()) {
            destination = String.valueOf(resultSet.getString("destination")) ;
            processSetId = String.valueOf(resultSet.getInt("processSetId")) ;
        }

        if (resultSet != null) 	{
            if (resultSet.isClosed())	{
                resultSet.close();
            }
        }

        if (sqlStat != null )	{
            if (sqlStat.isClosed())
                sqlStat.close();
        }

        return new String[]{destination,processSetId} ;
    }

    public String getRoutingSlip(String processSetId) throws SQLException {

        this.checkDbConnection();
        String sql = "select ps.command cmd from processSteps pss left join processStep ps " +
            " on pss.processStepId = ps.id " +
            " where " +
            " pss.processSetId=" + processSetId +
            " order by " +
            " pss.processOrderId asc ;" ;

        Statement sqlStat = this.connection.createStatement();
        ResultSet resultSet = sqlStat.executeQuery(sql);

        String routingSlip = "" ;
        String separator= "§" ;

        while (resultSet.next()) {
            routingSlip += resultSet.getString("cmd") + separator ;
        }

        if(!routingSlip.equals(""))
            routingSlip = routingSlip.substring(0,routingSlip.length()-1) ;

        if (resultSet != null) 	{
            if (resultSet.isClosed())	{
                resultSet.close();
            }
        }

        if (sqlStat != null )	{
            if (sqlStat.isClosed())
                sqlStat.close();
        }

        return routingSlip;
    }
}
