package com.flipkart.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.flipkart.DAO.Impl.CredentialDAOImpl;

/**
 * @author ciphereck
 * @category Util
 *
 */
public class DBUtil {
	private static final Logger logger = Logger.getLogger(DBUtil.class);
	private static Connection connection = null;
	
	/**
	 * 
	 * @return Connection of MySql
	 */
	public static Connection getConnection() {
		
        if (connection != null)
            return connection;
        else {
            try {
            	Properties prop = new Properties();
                InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("config.properties");
                prop.load(inputStream);
                String driver = prop.getProperty("driver");
                String url = prop.getProperty("url");
                String user = prop.getProperty("user");
                String password = prop.getProperty("password");
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException e) {
            	logger.fatal(e.getMessage());
            } catch (SQLException e) {
            	logger.fatal(e.getMessage());
            } catch (FileNotFoundException e) {
            	logger.fatal(e.getMessage());
            } catch (IOException e) {
            	logger.fatal(e.getMessage());
            }
            return connection;
        }

    }
}


