/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2010. All Rights Reserved. 
 * 
 * Note to U.S. Government Users Restricted Rights:  Use, 
 * duplication or disclosure restricted by GSA ADP Schedule 
 * Contract with IBM Corp.
 *******************************************************************************/

package com.jke.db.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The base implementation creates an instance of the simple JKE_DB_C connection
 * manager. Subclasses may override to create a different manager. One, for
 * example, that uses J2EE connection pooling. An application that uses a
 * subclass must make sure the factory is initialized before a connection is
 * requested. This implementation gets the connection parameters from the
 * JKEDB1.properties file found in the same package as this factory.
 */

public class JKE_DB_Factory {

	private static JKE_DB_Factory fgFactory;

    private JKE_DB_I fDB;

    @Autowired
    private Environment environment;

	/**
	 * A subclass static getFactory method can use this method to set the factory
	 * its custom factory instance in here in addition to holding its own
	 * reference for use in overridden methods.
	 * 
	 * @param factory
	 * @return
	 */
	public static synchronized JKE_DB_Factory initFactory(JKE_DB_Factory factory) {

		if (factory == null){
            fgFactory= new JKE_DB_Factory();
        }
		else {
            fgFactory= factory;
        }

        return fgFactory;
	}

	/**
	 * Returns the factory singleton. Creates an instance of this class if needed.
	 * 
	 * @return
	 */
	public static synchronized JKE_DB_Factory getFactory() {
		if (fgFactory == null)
			initFactory(null);
		return fgFactory;
	}

	/**
	 * Only the factory initialization can create a factory instance, but
	 * subclasses can use this constructor.
	 */
	protected JKE_DB_Factory() {
		super();
	}

	/**
	 * Returns the application's JKE DB singleton.
	 * 
	 * @return the JKE database manager
	 */
	public JKE_DB_I getDB() {
		if (fDB == null) {
			fDB= createJKE_DB();
		}
		return fDB;
	}

	/**
	 * Subclasses should override.
	 * 
	 * @return a new instance of the database manager
	 * @throws IOException if the properties file read fails
	 */
	protected JKE_DB_I createJKE_DB() {

        String dbType = null;

        Properties props= new Properties();
        InputStream is= Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
        try {
            props.load(is);

            dbType = readProperty(props, "spring.dbprob.type");
        } catch (Exception e) {
            e.printStackTrace();
        }

		if (dbType != null && dbType.trim().equalsIgnoreCase("mysql")) {
            return new JKE_DB_MySQL(getPropertiesForOthers(props));

        } else if (dbType != null && dbType.trim().equalsIgnoreCase("db2")) {
            return new JKE_DB_DB2(getPropertiesForOthers(props));
        } else {
            return new JKE_DB_Derby(getPropertiesForDerby());
        }
	}

	private Properties getPropertiesForDerby () {
        Properties props = new Properties();
        props.put("jdbc.driver", "org.apache.derby.jdbc.EmbeddedDriver");
        props.put("jdbc.protocol", "jdbc:derby:");
        props.put("jdbc.dbname", "JKEDB");
        return props;
    }

    private Properties getPropertiesForOthers (Properties propsIn) {
        Properties props = new Properties();

        props.put("jdbc.driver", readProperty(propsIn, "spring.datasource.driver-class-name"));
        props.put("jdbc.protocol", readProperty(propsIn, "spring.dbprob.protocol"));
        props.put("jdbc.hostname", readProperty(propsIn, "spring.dbprob.hostname"));
        props.put("jdbc.port",  readProperty(propsIn, "spring.dbprob.port"));
        props.put("jdbc.dbname", readProperty(propsIn, "spring.dbprob.dbname"));
        props.put("jdbc.user", readProperty(propsIn, "spring.datasource.username"));
        props.put("jdbc.password", readProperty(propsIn, "spring.datasource.password"));

        return props;
    }

    //Read from env property, if not read from application.properties. Becuase it might have been overridden by the env property from helm values.yaml
    private String readProperty (Properties propsIn, String propertyName) {
        String result = System.getenv(propertyName);
        if (result == null || result.isEmpty()) {
            result = propsIn.getProperty(propertyName);
        }
        return result;
    }

}