SELECT	
	*
FROM log_parser.tb_log_file_line lfl
WHERE lfl.id_log_file in (SELECT max(id) FROM log_parser.tb_log_file lf WHERE lf.import_completed = 1 group by lf.file_hash )
AND lfl.ip = '192.168.129.191'