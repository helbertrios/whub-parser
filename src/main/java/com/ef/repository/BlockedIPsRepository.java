package com.ef.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlockedIPsRepository {
	
	private static final String INSERT_BLOCKED_IPS =  
					" INSERT INTO `log_parser`.`tb_ip_blocked`(`ip`,`requests`,`comments`,`id_block_order`)                                                     "+
					" WITH cte AS (                                                                                                                             "+
					" 	SELECT                                                                                                                                  "+
					" 		bo.id  AS id_block_order,                                                                                                           "+
					" 		lfl.ip,                                                                                                                             "+
					" 		bo.threshold,                                                                                                                       "+
					" 		bo.start_date,                                                                                                                      "+
					" 		FROM_UNIXTIME(UNIX_TIMESTAMP(bo.start_date) + IF(bo.duration = 'd', 86399.999, 3599.999)) AS end_date,								"+
					" 		COUNT(*) AS requests                                                                                                                "+
					" 	FROM log_parser.tb_block_order bo	                                                                                                    "+
					" 		INNER JOIN log_parser.tb_log_file_line lfl ON lfl.id_log_file = bo.id_log_file                                                      "+
					" 	WHERE bo.id = ?                                                                                                                         "+
					"   AND lfl.ip is not null                                                                                                                  "+
					" 	AND lfl.access_date 						                                                                                            "+
					" 		BETWEEN bo.start_date AND FROM_UNIXTIME(UNIX_TIMESTAMP(bo.start_date) + IF(bo.duration = 'd', 86399.999, 3599.999))					"+					
					" 	GROUP BY bo.id, lfl.ip, bo.threshold, bo.start_date, bo.duration                                                                        "+
					" 	HAVING COUNT(*) >= bo.threshold                                                                                                         "+
					"     )                                                                                                                                     "+
					" SELECT                                                                                                                                    "+
					" 	 ip, requests, CONCAT( ip, ' has ', threshold, ' or more requests between ', start_date, ' and ', end_date) AS comments, id_block_order	"+
					" FROM cte                                                                                                                                  "+
					" ORDER BY ip;                                                                                                                              ";
	
	private static final String COMPLETE_ORDER_BLOCK = "UPDATE `log_parser`.`tb_block_order` SET `order_completed` = 1 WHERE `id` = ?"; 	
	private static final String IPS_BLOCKED =	"SELECT `tb_ip_blocked`.`comments` FROM `log_parser`.`tb_ip_blocked` WHERE `tb_ip_blocked`.`id_block_order` = ? ;";
	
	public void blockedIPs(final Long idOrderBlock) throws SQLException {		
		try (PreparedStatement stmt = createPreparedStatementBlockedIPs(idOrderBlock);) {			
			stmt.executeUpdate();											
		} 
	}
	
	private PreparedStatement createPreparedStatementBlockedIPs(final Long idOrderBlock) throws SQLException {
		final PreparedStatement stmt =   ConnectionFactory.getInstance().getConnection().prepareStatement(INSERT_BLOCKED_IPS);
		stmt.setLong(1, idOrderBlock);		
		return stmt;
	}
	
	public void completeOrderBlock(final Long idOrderBlock) throws SQLException {		
		try (PreparedStatement stmt = createPreparedStatementCompleteOrderBlock(idOrderBlock);) {			
			stmt.executeUpdate();											
		} 
	}
	
	private PreparedStatement createPreparedStatementCompleteOrderBlock(final Long idOrderBlock) throws SQLException {
		final PreparedStatement stmt =   ConnectionFactory.getInstance().getConnection().prepareStatement(COMPLETE_ORDER_BLOCK);
		stmt.setLong(1, idOrderBlock);		
		return stmt;
	}
	
	public List<String> listIPsBlocked(final Long idOrderBlock) throws SQLException {
		
		List<String> results = new ArrayList<String>();
		
		try (
				PreparedStatement stmt = createPreparedStatementListIPsBlocked(idOrderBlock);
				ResultSet rs = stmt.executeQuery();
			) {		
			
			while(rs.next()){
				results.add(rs.getString(1));
			}
		} 
		
		return results;
		
	}
	
	private PreparedStatement createPreparedStatementListIPsBlocked(final Long idOrderBlock) throws SQLException {
		final PreparedStatement stmt =   ConnectionFactory.getInstance().getConnection().prepareStatement(IPS_BLOCKED);
		stmt.setLong(1, idOrderBlock);		
		return stmt;
	}
}
