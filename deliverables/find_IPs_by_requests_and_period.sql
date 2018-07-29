SELECT	
	lfl.ip,
	COUNT(*) AS requests
FROM log_parser.tb_log_file_line lfl
WHERE lfl.ip is not null
AND	lfl.id_log_file in (SELECT max(id) FROM log_parser.tb_log_file lf WHERE lf.import_completed = 1 group by lf.file_hash )
AND lfl.access_date BETWEEN '2017-01-01.13:00:00' AND '2017-01-01.14:00:00'	
GROUP BY lfl.ip                                                                       
HAVING COUNT(*) >= 100  
