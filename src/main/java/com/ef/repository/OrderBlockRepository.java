package com.ef.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.ef.repository.to.Duration;
import com.ef.repository.to.LogFile;
import com.ef.repository.to.OrderBlock;

public class OrderBlockRepository {
	
		
	private static final String SELECT_ORDER_BLOCK =
													 " SELECT 															"+
													 " 	  bo.id 				AS bo_id,								"+
													 "    bo.order_date			AS bo_order_date,						"+
													 "    bo.start_date   		AS bo_start_date,						"+
													 "    bo.duration     		AS bo_duration,							"+
													 "    bo.threshold    		AS bo_threshold,						"+
													 "    bo.order_completed	AS bo_order_completed,		   			"+
													 "    lf.id					AS lf_id,								"+
													 "    lf.import_date  		AS lf_import_date,						"+
													 "    lf.file_path			AS lf_file_path,						"+
													 "    lf.file_hash			AS lf_file_hash,						"+													
													 "    lf.import_completed	AS lf_import_completed		   			"+
													 " FROM log_parser.tb_block_order bo								"+
													 "	JOIN log_parser.tb_log_file lf ON lf.id = bo.id_log_file		"+
													 " WHERE bo.id = ?;													";
	private static final String INSERT_ORDER_BLOCK = " INSERT INTO `log_parser`.`tb_block_order`(`start_date`,`duration`,`threshold`,`id_log_file`) VALUES (? , ?, ?, ?); ";
	
		
	public OrderBlock insertOrderBlock(final LocalDateTime startDate, final Duration duration, final Long treshold, final Long idFileLog) throws SQLException {		
		final Long id = executeInsertOrderBlock( startDate, duration, treshold, idFileLog);
		final OrderBlock orderBlock = execuSelectOrderBlock(id);
		return orderBlock;
	}
	
	private OrderBlock execuSelectOrderBlock(final Long id) throws SQLException {
		
		OrderBlock result = null;	
		
		try(
				PreparedStatement stmt = createPreparedStatementSelectOrderBlock(id);				
				ResultSet rs = stmt.executeQuery();
			){			

			if (rs != null && rs.next()) {
															
				result = new OrderBlock();
				result.setId(rs.getLong(1));
				result.setOrderDate(rs.getTimestamp(2).toLocalDateTime());
				result.setStartDate(rs.getTimestamp(3).toLocalDateTime());
				result.setDuration(Duration.getDurationByKey(rs.getString(4).charAt(0)));
				result.setThreshold(rs.getLong(5));
				result.setOrderCompleted(rs.getBoolean(6));
				
				result.setLogFile(new LogFile()); 
				result.getLogFile().setId(rs.getLong(7));
				result.getLogFile().setImportDate(rs.getTimestamp(8).toLocalDateTime());
				result.getLogFile().setFilePath(rs.getString(9));				
				result.getLogFile().setFileHash(rs.getString(10));
				result.getLogFile().setImportCompleted(rs.getBoolean(11));
				
			}
											
		} 
				
		return result;
		
	}
		
	private Long executeInsertOrderBlock(final LocalDateTime startDate, final Duration duration,  final Long treshold, final Long idFileLog) throws SQLException {
		
		Long result = null;		
		
		try(
				PreparedStatement stmt = createPreparedStatementInsertOrderBlock(startDate, duration,  treshold, idFileLog);				
				ResultSet rs = stmt.getGeneratedKeys();
			){			

			if (rs != null && rs.next()) {
				result = rs.getLong(1);
			}
			
											
		} 
				
		return result;
	}
	
	private PreparedStatement createPreparedStatementSelectOrderBlock(final Long id) throws SQLException {
		final PreparedStatement stmt =   ConnectionFactory.getInstance().getConnection().prepareStatement(SELECT_ORDER_BLOCK);
		stmt.setLong(1, id);		
		return stmt;
	}
	
	private PreparedStatement createPreparedStatementInsertOrderBlock(final LocalDateTime startDate, final Duration duration,  final Long treshold, final Long idFileLog) throws SQLException {
		final PreparedStatement stmt =   ConnectionFactory.getInstance().getConnection().prepareStatement(INSERT_ORDER_BLOCK, PreparedStatement.RETURN_GENERATED_KEYS);
		Timestamp ts = Timestamp.valueOf(startDate);
		stmt.setTimestamp(1, ts);
		stmt.setString(2, String.valueOf(duration.getKey()));
		stmt.setLong(3, treshold);
		stmt.setLong(4, idFileLog);
		stmt.executeUpdate();
		return stmt;
	}
	
	


	
	

}
