/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2010. All Rights Reserved. 
 * 
 * Note to U.S. Government Users Restricted Rights:  Use, 
 * duplication or disclosure restricted by GSA ADP Schedule 
 * Contract with IBM Corp.
 *******************************************************************************/

package com.jke.db.connection;

import com.jke.db.data.DB2GenerateData;
import com.jke.db.data.GenerateData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.sql.ResultSet;

/**
 * A singleton that serves as the connection to and manager of the JKE database.
 * The database to create or connect to is specified in the JKEDB1.properties
 * file. This implementation provides a simple single connection interface to
 * the JKE database.
 */
public class JKE_DB_DB2 implements JKE_DB_I {
	private String fDriver;
	private String fURL;
	private Connection fConnection;
	private boolean fDriverLoaded= false;
	private boolean fHaveMassGeneratedTables= false;
	private Set<String> fCreatedTables= new HashSet<String>();

	private String userName;
	private String password;


	/**
	 * Saves the properties but does not yet connect to or create the database.
	 *
	 * @param props a set of properties defining the jdbc URL: jdbc.driver (the
	 * full class name of the driver to load), jdbc.protocol ("jdbc:derby:", for
	 * example) and jdbc.dbname (protocol specific database identifier)
	 */
	protected JKE_DB_DB2(Properties props) {
		super();
		fDriver= props.getProperty("jdbc.driver");
		
		String jdbcHostname = System.getenv("JKE_DB_SERVICE_HOST");
		fURL= String.format("%s://%s:%s/%s",
				props.getProperty("jdbc.protocol"), 				
				jdbcHostname != null ? jdbcHostname : props.getProperty("jdbc.hostname"),
				props.getProperty("jdbc.port"),
				props.getProperty("jdbc.dbname"));

        userName = props.getProperty("jdbc.user");
        password = props.getProperty("jdbc.password");

		System.out.println("URL ---> " + fURL);
		System.out.println("fDriver ---> " + fDriver);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jke.db.connection.JKE_DB_I#getConnection()
	 */
	public synchronized Connection getConnection() throws SQLException {
		primGetConnection();
		if (!fHaveMassGeneratedTables) {
			fHaveMassGeneratedTables= true; // avoid recursion
			GenerateData gen= new DB2GenerateData();
			fHaveMassGeneratedTables= gen.generateTablesAndInitialData();
		}
		return fConnection;
	}

	/*
	 * Return a connection with no attempt to generate tables.
	 */
	private Connection primGetConnection() throws SQLException {
		if (fConnection == null) {
			jdbcConnect();
		}
		return fConnection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jke.db.connection.JKE_DB_I#shutdown()
	 */
	public void shutdown() {
		jdbcDisconnect();
		try {
			DriverManager.getConnection(fURL);
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jke.db.connection.JKE_DB_I#createTable(java.lang.String,
	 * java.lang.String)
	 */
	public boolean createTable(String tableName, String columnsClause) throws SQLException {
		boolean created= false;
		Statement sta= null;
		if (!fCreatedTables.contains(tableName)) {
            if (!isTableExists(tableName)) {
                try {
                    String createStmt = "create table  " + tableName + columnsClause;
                    System.out.println("createStmt ---> " + createStmt);
                    sta = primGetConnection().createStatement();
                    sta.executeUpdate(createStmt);
                    System.out.println("Table created. " + tableName);
                    created = true;
                    fCreatedTables.add(tableName);
                } catch (SQLException e) {

                    /*
                     * If the exception indicates that the table already exists,
                     * record it as created. Otherwise, rethrow the exception.
                     */
                    if (e.getSQLState().equals("42S01")) {
                        fCreatedTables.add(tableName);
                    } else {
                        e.printStackTrace();
                        throw e;
                    }
                } finally {
                    if (sta != null)
                        sta.close();
                }
            }
		}
		return created;
	}


    /**
     * Returns whether the given table exists or not
     *
     * @param tableName
     * @return
     * @throws SQLException
     */
    public boolean isTableExists(String tableName) throws SQLException {

        System.out.println("Table exists check : " + tableName);

        String sql = "SELECT name FROM sysibm.systables "
                + " WHERE UPPER(name) = '" + tableName.toUpperCase() + "'";

        boolean result = false;

        ResultSet rs = null;
        Statement s = null;
        try {
            s = primGetConnection().createStatement();
            rs = s.executeQuery(sql);
            if (rs.next()) {
                result = true;
            }
        } finally {
            if (rs != null)
                rs.close();
            if (s != null)
                s.close();
        }

        System.out.println("Table exists : " + tableName + " : " + result);
        return result;
    }

    /**
	 * Creates and sets a new connection to the database. The database is created
	 * if it did not already exist.
	 * 
	 * @throws SQLException
	 */
	private void jdbcConnect() throws SQLException {
		loadDriver();
		try {
			fConnection= DriverManager.getConnection(fURL, userName, password);
		} catch (SQLException e) {
			System.out.println(e);
			throw e;
		}
	}

	/**
	 * Disconnects and forgets the connection to the database.
	 */
	private void jdbcDisconnect() {
		if (fConnection != null) {
			try {
				fConnection.close();
			} catch (SQLException se) {
			} finally {
				fConnection= null;
			}
		}
	}

	/**
	 * Loads the JDBC driver specified in the properties file.
	 */
	private void loadDriver() {
	    System.out.println("loadDriver Test 1 --->");
		if (!fDriverLoaded) {
            System.out.println("loadDriver Test 2 --->");
			try {
                System.out.println("loadDriver Test 3 ---> :" + fDriver);
				Class.forName(fDriver);
				fDriverLoaded= true;
                System.out.println("loadDriver Test 4 --->");
			} catch (ClassNotFoundException cnfe) {
				System.err.println("\nUnable to load the JDBC driver " + fDriver);
				System.err.println("Please check your CLASSPATH.");
				cnfe.printStackTrace(System.err);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jke.db.connection.JKE_DB_I#dropTable(java.lang.String)
	 */
	public boolean dropTable(String tableName) {
		boolean dropped= false;
		try {
			primGetConnection().createStatement().executeUpdate("DROP TABLE " + tableName);
			dropped= true;
		} catch (SQLException e) {
			// The table did not exist, ignore error.
		}
		fCreatedTables.remove(tableName);
		fHaveMassGeneratedTables= false;
		return dropped;
	}


}