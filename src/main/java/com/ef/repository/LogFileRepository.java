package com.ef.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ef.repository.to.LogFile;



public class LogFileRepository {
	

	
	private static final String SELECT_LOG_FILE_BY_HASH =
			" SELECT 																		                                               "+
			"    lf.id					AS lf_id,								                                                           "+
			"    lf.import_date  		AS lf_import_date,						                                                           "+
			"    lf.file_path			AS lf_file_path,						                                                           "+
			"    lf.file_hash			AS lf_file_hash,																			       "+
			"    lf.import_completed	AS lf_import_completed,	                                                                           "+
			"    (SELECT MAX(lfl.line_number) FROM log_parser.tb_log_file_line lfl WHERE lfl.id_log_file = lf.id) AS last_line_imported    "+
			" FROM log_parser.tb_log_file lf								                                                               "+
			" WHERE lf.file_hash = ? ;		                                                                                               ";
	
	private static final String INSERT_LOG_FILE = " INSERT INTO `log_parser`.`tb_log_file`(`file_path`,`file_hash`) VALUES (?,?); ";
	
	
	public Long simpleInsertLogFile(final String filePath, final String fileHash) throws SQLException {
		final Long id = executeInsertLogFile(filePath, fileHash);
		return id;
	}
	
	public List<LogFile> listByHash(final String fileHash) throws SQLException {
		final List<LogFile> results = executeSelectLogFileByHash(fileHash);
		return results;
	}
	
		
	
	private Long executeInsertLogFile(final String filePath, final String fileHash) throws SQLException {
		
		Long result = null;						
		
		try (
				PreparedStatement stmt = createPreparedStatementInsertLogFile(filePath, fileHash);
				ResultSet rs = stmt.getGeneratedKeys();
			) {
			
			if (rs != null && rs.next()) {
				result = rs.getLong(1);
			}
		
		}							
		return result;
	}
	
	private List<LogFile> executeSelectLogFileByHash(final String fileHash) throws SQLException {
		
		List<LogFile>  results = null;						
		
		try (
				PreparedStatement stmt = createPreparedStatementSelectLogFileByHash(fileHash);				
				ResultSet rs = stmt.executeQuery();
				
			){
			
			results = new ArrayList<LogFile>();
			
			while (rs.next()) {
				LogFile result = new LogFile();		
				result.setId(rs.getLong(1));
								
				result.setImportDate(rs.getTimestamp(2).toLocalDateTime());
				result.setFilePath(rs.getString(3));				
				result.setFileHash(rs.getString(4));
				result.setImportCompleted(rs.getBoolean(5));
				result.setLastLineImported(rs.getInt(6));
				results.add(result);
	
			}			
		}

		return results;
	}	
	
	private PreparedStatement createPreparedStatementSelectLogFileByHash(final String fileHash) throws SQLException {
		final PreparedStatement stmt = ConnectionFactory.getInstance().getConnection().prepareStatement(SELECT_LOG_FILE_BY_HASH);
		stmt.setString(1, fileHash);
		return stmt;
	}
	
	
	private PreparedStatement createPreparedStatementInsertLogFile(final String filePath, final String fileHash) throws SQLException {
		final PreparedStatement stmt = ConnectionFactory.getInstance().getConnection().prepareStatement(INSERT_LOG_FILE, PreparedStatement.RETURN_GENERATED_KEYS);
		stmt.setString(1, filePath);
		stmt.setString(2, fileHash);
		stmt.executeUpdate();
		return stmt;
	}
	

}
