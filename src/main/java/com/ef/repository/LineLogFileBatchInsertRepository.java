package com.ef.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class LineLogFileBatchInsertRepository implements AutoCloseable {
	
	private static final String INSERT_LOG_FILE_LINE =  "INSERT INTO `log_parser`.`tb_log_file_line`(`line_number`,`access_date`,`ip`,`action`,`code`,`access_information`,`source`,`id_log_file`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String COMPLETE_IMPORT_LOG_FILE_LINE = " UPDATE `log_parser`.`tb_log_file` SET `import_completed` = 1 WHERE `id` = ? ";
	
	private int maxToBatchUpdate;
	private int currentBatchUpdate = 0;
	private PreparedStatement insertStmt;

	public LineLogFileBatchInsertRepository(final int maxToBatchUpdate) {
		super();
		this.maxToBatchUpdate = maxToBatchUpdate;		
		this.insertStmt = getPreparedStatement();		
	}
	
	public LineLogFileBatchInsertRepository() {
		super();
		this.maxToBatchUpdate = 20000;	
		this.insertStmt = getPreparedStatement();	
	
	}
	
	
	private static PreparedStatement getPreparedStatement () {
		try {
			return ConnectionFactory.getInstance().getConnection().prepareStatement(INSERT_LOG_FILE_LINE);
		} catch (SQLException e) {
			throw new RuntimeException("Cannot get connection to line of log file.", e);
		}	
	}
	
	public void insertLogFileLineBatch(final Integer lineNumber, final LocalDateTime accessDate, final String ip, final String action, final Integer code, final String accessInformation, final String source, final Long idLogFile) {
		currentBatchUpdate++;
		
		try {	
			
			executePreparedStatementInsertLogFile(lineNumber, accessDate, ip, action, code, accessInformation, source, idLogFile);
			
			if (currentBatchUpdate >= maxToBatchUpdate) {				
				flushBatch();
			}
										
		} catch (SQLException e) {
			try { if (insertStmt != null) { insertStmt.close(); } } catch (Exception ex) {}
			throw new RuntimeException("Cannot save in database line of log file. ", e);
			
		} 			
	}
	
	public void completeImportLogFile(final Long idLogFile) throws SQLException {		
		try (PreparedStatement stmt = createPreparedStatementCompleteImportLogFile(idLogFile);) {			
			stmt.executeUpdate();											
		} 
	}
	
	private PreparedStatement createPreparedStatementCompleteImportLogFile(final Long idLogFile) throws SQLException {
		final PreparedStatement stmt =   ConnectionFactory.getInstance().getConnection().prepareStatement(COMPLETE_IMPORT_LOG_FILE_LINE);
		stmt.setLong(1, idLogFile);		
		return stmt;
	}
	
	public int[] flushBatch() throws SQLException {
		
		
		int[] results = null;
		
		try  {
			insertStmt.clearParameters();
			results = insertStmt.executeBatch();
			ConnectionFactory.getInstance().flush();
			ConnectionFactory.getInstance().startTransaction();
		} finally {			
			currentBatchUpdate = 0;
		}
		
		return results;
	}
	
	
	private void executePreparedStatementInsertLogFile(final Integer lineNumber, final LocalDateTime accessDate, final String ip, final String action, final Integer code, final String accessInformation, final String source, final Long idLogFile) throws SQLException {
			
		insertStmt.setInt(1, lineNumber);
		insertStmt.setTimestamp(2, Utils.getTimestamp(accessDate));
		insertStmt.setString(3, ip);
		insertStmt.setString(4, action);
		insertStmt.setInt(5, lineNumber);
		insertStmt.setString(6, accessInformation);
		insertStmt.setString(7, source);
		insertStmt.setLong(8, idLogFile); 
		insertStmt.addBatch();
	
	}

	@Override
	public void close() throws SQLException {
		
		if (insertStmt != null) { 
			insertStmt.close(); 
		}
		
	}
	

}
