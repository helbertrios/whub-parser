CREATE USER 'wallethub'@'localhost' IDENTIFIED WITH mysql_native_password BY '$Whub123';

CREATE DATABASE log_parser;

GRANT ALL PRIVILEGES ON log_parser.* TO 'wallethub'@'localhost'; 

DROP TABLE IF EXISTS log_parser.tb_ip_blocked;
DROP TABLE IF EXISTS log_parser.tb_block_order;
DROP TABLE IF EXISTS log_parser.tb_log_file_line;
DROP TABLE IF EXISTS log_parser.tb_log_file;

CREATE TABLE log_parser.tb_log_file( 
	id 						BIGINT 									NOT NULL 	AUTO_INCREMENT, 
	import_date 			TIMESTAMP(3)							NOT NULL 	DEFAULT CURRENT_TIMESTAMP(3), 
	file_path 				VARCHAR(1024) CHARACTER SET UTF8MB4 	NOT NULL, 
	file_hash 				VARCHAR(100) CHARACTER SET UTF8MB4 		NOT NULL, 
	import_completed 		BIT 									NOT NULL	DEFAULT 0, 
	PRIMARY KEY (id)
);

CREATE TABLE log_parser.tb_block_order(
	id 						BIGINT 									NOT NULL 	AUTO_INCREMENT, 
	order_date 				TIMESTAMP(3)							NOT NULL	DEFAULT CURRENT_TIMESTAMP(3),  
	start_date 				TIMESTAMP(3)							NOT NULL, 
	duration 				CHAR(1) 								NOT NULL, 
	threshold 				INT 									NOT NULL,
	order_completed 		BIT 									NOT NULL	DEFAULT 0, 
	id_log_file 			BIGINT 									NOT NULL,	
	PRIMARY KEY (id), 
	FOREIGN KEY (id_log_file) REFERENCES log_parser.tb_log_file(id)
);

CREATE TABLE log_parser.tb_log_file_line(
	id 						BIGINT 									NOT NULL 	AUTO_INCREMENT,
	import_date 			TIMESTAMP(3)							NOT NULL 	DEFAULT CURRENT_TIMESTAMP(3), 
	line_number				INT										NOT NULL,
	access_date 			TIMESTAMP(3),
	ip 						VARCHAR(15),
	action	 				VARCHAR(512) CHARACTER SET UTF8MB4,
	code					INT,
	access_information 		VARCHAR(4096) CHARACTER SET UTF8MB4,
	source			 		VARCHAR(8192) CHARACTER SET UTF8MB4,
	id_log_file 			BIGINT 									NOT NULL,	
	PRIMARY KEY (id), 
	FOREIGN KEY (id_log_file) REFERENCES log_parser.tb_log_file(id)
);

CREATE TABLE log_parser.tb_ip_blocked (
	id 						BIGINT 									NOT NULL 	AUTO_INCREMENT, 
	blocked_date			TIMESTAMP(3)							NOT NULL	DEFAULT CURRENT_TIMESTAMP(3),  
	ip 						VARCHAR(15)								NOT NULL,	
	requests 				INT 									NOT NULL,
	comments		 		VARCHAR(100) CHARACTER SET UTF8MB4		NOT NULL,
	id_block_order 			BIGINT 									NOT NULL,
	active		 			BIT 									NOT NULL	DEFAULT 1,
	PRIMARY KEY (id), 
	FOREIGN KEY (id_block_order) REFERENCES log_parser.tb_block_order(id)
);

