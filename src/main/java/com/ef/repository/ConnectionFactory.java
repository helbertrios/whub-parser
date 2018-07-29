package com.ef.repository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.ef.log.ParserLogger;

public class ConnectionFactory implements AutoCloseable {

	private static final ThreadLocal<ConnectionFactory> connectionFactory = new ThreadLocal<ConnectionFactory>();

	private Connection connection;

	private ConnectionFactory() {
		super();
	}

	public static ConnectionFactory getInstance() {
		if (connectionFactory.get() == null) {
			connectionFactory.set(new ConnectionFactory());
		}
		return connectionFactory.get();
	}

	public Connection openConnection() throws SQLException {
		if (this.connection == null) {
			openNewConnection();
		}
		return connection;
	}

	public void startTransaction() throws SQLException {
		if (this.connection != null) {
			checkConnection();
			rollback();
		}
	}

	public void flush() throws SQLException {
		if (this.connection != null) {			
			checkConnection();
			this.connection.commit();
		}
	}

	public void rollback() throws SQLException {
		if (this.connection != null) {
			checkConnection();
			this.connection.rollback();			
		}
	}

	@Override
	public void close() throws SQLException {		
		closeConnection();
	}

	protected boolean isConnected() throws SQLException {

		Boolean check = null;

		try (
				Statement stmt = this.connection.createStatement();
				ResultSet rs = stmt.executeQuery("select 1 as 'check'");
			) {

			if (!this.connection.isClosed()) {
				rs.next();
				check = rs.getBoolean("check");
			}
			ParserLogger.getLogger().fine("Connection has been checked, active is " + check.toString());
		}

		return check == null ? Boolean.FALSE : check;
	}

	protected Connection getConnection() {
		return connection;
	}

	protected void checkConnection() throws SQLException {
		Boolean check = isConnected();
		if (Boolean.FALSE.equals(check)) {
			throw new RuntimeException("Database connection was lost.");
		}
	}

	protected void closeConnection() throws SQLException {
		if (this.connection != null) {
			if (!this.connection.isClosed()) {
				this.connection.close();
			}
		}
	}

	protected void openNewConnection() throws SQLException  {
		
		final InputStream stream = ParserLogger.class.getClassLoader().getResourceAsStream("db.properties");
		final Properties props = new Properties();
		
		try {
			props.load(stream);
			final Connection conn = DriverManager.getConnection(props.getProperty("DB_URL"), props.getProperty("DB_USERNAME"), props.getProperty("DB_PASSWORD"));
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			this.connection = conn;
			checkConnection();
		} catch (IOException  e) {
			throw new RuntimeException("Cannot read database configuration in db.properties file. ");
		}
		
	}

}
