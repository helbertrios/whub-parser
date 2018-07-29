(1) Java program that can be run from command line
	
    java -cp "parser.jar" com.ef.Parser --accesslog=/path/to/file --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100 

    Database configuration in file "db.properties" inside the parser.jar
     
    If the file is already imported the program will display options for you choose:

        This log file already been imported, following times: 

            id: [1] importDate: 2018-07-28T15:00:00.000 path: /path/to/file

        Please enter [n - new import] or [id - use this import]: 

    If something goes wrong you can continue importing where you left off. The program will display the following message:

        This log file do not finish importation, do you want do it? [c - continue import] or [n - new import]: 

(2) Source Code for the Java program

    It is in file whub-parser-master.zip or git clone https://github.com/helbertrios/whub-parser.git

(3) MySQL schema used for the log data
    
   It is in file "data.sql"

(4) SQL queries for SQL test
    
    It is in files find_IPs_by_requests_and_period.sql and find_requests_by_IP.sql