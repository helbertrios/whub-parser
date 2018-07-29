package com.ef;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ef.file.FileReader;
import com.ef.file.LogFiles;
import com.ef.inputs.InputData;
import com.ef.inputs.InputHandler;
import com.ef.inputs.InputHandlerIncompleteLogFile;
import com.ef.inputs.InputHandlerLogFile;
import com.ef.log.ParserLogger;
import com.ef.repository.BlockedIPsRepository;
import com.ef.repository.ConnectionFactory;
import com.ef.repository.LineLogFileBatchInsertRepository;
import com.ef.repository.LogFileRepository;
import com.ef.repository.OrderBlockRepository;
import com.ef.repository.to.LineLogFile;
import com.ef.repository.to.LogFile;
import com.ef.repository.to.OrderBlock;

public class Parser {
	
	private Parser() {
		super();		
	}
	
	
	public static void main(String[] args) {
		
		ParserLogger.getLogger().info("Let's go.");		
		
		Parser parser = new Parser();
		parser.mainFlow(args);

		ParserLogger.getLogger().info("Remember: "+Sentence.getSentence());
		ParserLogger.getLogger().info("See you soon.");	
		System.exit(0);
		
	}

	private void mainFlow(final String[] args) {
		
	
		ParserLogger.getLogger().info("I will extract the received parameters.");		
		final InputHandler inputHandler = new InputHandler();
		final InputData inputData = inputHandler.extractParameters(args);
		ParserLogger.getLogger().info("Parameters extracted.");
		
		ParserLogger.getLogger().info("I will hash your file.");		
		final String fileHash = LogFiles.hashFile(inputData.getFilePath());
		ParserLogger.getLogger().info("Parser hashed your file.");	

		ParserLogger.getLogger().info("I will read file: "+inputData.getFilePath().toAbsolutePath().toString());		
		final FileReader fileReader = new FileReader(inputData.getFilePath());
		ParserLogger.getLogger().info("I read the file and found "+ fileReader.getLineReader().getLinesCount()+ " lines. ");	
		
	
		try ( ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
			  Connection connection = connectionFactory.openConnection();) {
						
			
			Long idLogFile = importLogFileFow(inputData, fileReader, fileHash);
			
			 
			ParserLogger.getLogger().info("I will record order block in database. ");
			final OrderBlock orderBlock = persistOrderBlock(inputData, idLogFile);
			ParserLogger.getLogger().info("I recorded Order block in database. ");
						
			ParserLogger.getLogger().info("I will record IPs Blocked in database. ");
			persistBlockedIPs(orderBlock.getId());
			ParserLogger.getLogger().info("I recorded IPs Blocked in database. ");		
		
			ParserLogger.getLogger().info("I will display IPs Blocked. ");
			printIPsBlocked(orderBlock.getId());
			ParserLogger.getLogger().info("I displayed IPs Blocked. ");
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	private Long importLogFileFow(final InputData inputData, final FileReader fileReader, final String fileHash) throws SQLException {
		
		
		ParserLogger.getLogger().info("I will check if this log file has already been imported. ");
		List<LogFile> logFiles = getLogFilesAlreadyImported(fileHash);
		ParserLogger.getLogger().info("I checked if this log file has already been imported. ");
		
		Integer startLine = 1;
		
		if ( (logFiles == null) || (logFiles.isEmpty())) {
			ParserLogger.getLogger().info("This log file never been imported. ");
			final Long idLogFile = importLogFileSubFow(inputData, fileReader, fileHash, startLine);
			return idLogFile;
		}
		
		Integer indexLogFileSelected = InputHandlerLogFile.getIndexLogFileSelected(logFiles);
		
		
		if (InputHandlerLogFile.isNewimport(indexLogFileSelected)) {
			ParserLogger.getLogger().info("You choose for new import log file. ");
			final Long idLogFile = importLogFileSubFow(inputData, fileReader, fileHash, startLine);
			return idLogFile;
		}
		
		if (logFiles.get(indexLogFileSelected).getImportCompleted()) {
			final Long idLogFile = logFiles.get(indexLogFileSelected).getId();
			return idLogFile;
		}
		
		boolean option = InputHandlerIncompleteLogFile.isNewImportLogFile();
		if (option) {
			ParserLogger.getLogger().info("You choose for new import log file. ");
			final Long idLogFile = importLogFileSubFow(inputData, fileReader, fileHash, startLine);
			return idLogFile;
		}			
		
		final Long idLogFile = logFiles.get(indexLogFileSelected).getId();
		startLine = logFiles.get(indexLogFileSelected).getLastLineImported()+1;
		
		ParserLogger.getLogger().info("I will record file lines in database. ");
		persistLogFileLine(fileReader, idLogFile, startLine);
		ParserLogger.getLogger().info("I recorded file lines in database. ");
		
		return idLogFile;
		
	}
	
	private Long importLogFileSubFow(final InputData inputData, final FileReader fileReader, final String fileHash, final Integer startLine) throws SQLException {
		
		ParserLogger.getLogger().info("I will record log file information in database. ");
		Long idLogFile = persistLogFile(inputData.getFilePath().toAbsolutePath().toString(), fileHash);				
		ParserLogger.getLogger().info("I recorded log file information in database. ");
		
		ParserLogger.getLogger().info("I will record file lines in database. ");
		persistLogFileLine(fileReader, idLogFile, startLine);
		ParserLogger.getLogger().info("I recorded file lines in database. ");
		
		return idLogFile;
	}
	
	private void printIPsBlocked(final Long idOrderBlock) throws SQLException {
		
		ConnectionFactory.getInstance().startTransaction();
		
		final BlockedIPsRepository blockedIPsRepository = new BlockedIPsRepository();
		
		List<String> IPsBlocked = blockedIPsRepository.listIPsBlocked(idOrderBlock);
		
		System.out.println("");
		if (IPsBlocked.size() > 0) {
			
			System.out.println("The following IPs were blocked: ");
			System.out.println("");
			for (String blocked : IPsBlocked) {
				System.out.println(blocked);				
			}
			System.out.println("");
			System.out.println("Total IPs blocked (" + IPsBlocked.size() + ").");
		} else {
			System.out.println("No IPs were blocked. ");		
		}
		System.out.println("");
		ConnectionFactory.getInstance().flush();
	}
	
	private void persistBlockedIPs(final Long idOrderBlock) throws SQLException {
		
		ConnectionFactory.getInstance().startTransaction();
		
		final BlockedIPsRepository blockedIPsRepository = new BlockedIPsRepository();
		blockedIPsRepository.blockedIPs(idOrderBlock);
		blockedIPsRepository.completeOrderBlock(idOrderBlock);
		
		ConnectionFactory.getInstance().flush();
		
	}
	
	
	private Long persistLogFile(final String filePath, final String fileHash) throws SQLException {
		ConnectionFactory.getInstance().startTransaction();
		
		/* Persist log file information */
		final LogFileRepository  logFileRepository = new LogFileRepository();		
		Long result = logFileRepository.simpleInsertLogFile(filePath, fileHash);
		
		ConnectionFactory.getInstance().flush();
		
		return result;
	}
		
	private OrderBlock persistOrderBlock(final InputData inputData, final Long idLogFile) throws SQLException {
		
		ConnectionFactory.getInstance().startTransaction();		
		
		/* Persist order block information */
		final OrderBlockRepository orderBlockRepository = new OrderBlockRepository();
		OrderBlock orderBlock = orderBlockRepository.insertOrderBlock(inputData.getStartDate(), inputData.getDuration(), inputData.getThreshold(), idLogFile);
		
		ConnectionFactory.getInstance().flush();
		
		return orderBlock;
		
	}
	
	
	private void persistLogFileLine(final FileReader fileReader, final Long idLogFile, final int lineStart) throws SQLException {
		
		ConnectionFactory.getInstance().startTransaction();
				
		try (LineLogFileBatchInsertRepository lineLogFileBatchInsertRepository = new LineLogFileBatchInsertRepository();) {
			
			for (int i = (lineStart-1); i < fileReader.getLineReader().getLinesCount(); i++) {
				LineLogFile lineLogFile = fileReader.getLineReader().getLineLogFile(i);
				lineLogFileBatchInsertRepository.insertLogFileLineBatch(lineLogFile.getLineNumber(), lineLogFile.getAccessDate(), lineLogFile.getIp(), lineLogFile.getAction(), lineLogFile.getCode(), lineLogFile.getAccessInformation(), lineLogFile.getSource(), idLogFile);
			}

			
			lineLogFileBatchInsertRepository.flushBatch();
			lineLogFileBatchInsertRepository.completeImportLogFile(idLogFile);
		}
		
		ConnectionFactory.getInstance().flush();
		
	}
	
	
	private List<LogFile> getLogFilesAlreadyImported(final String fileHash) throws SQLException {
		
		ConnectionFactory.getInstance().startTransaction();
		
		final LogFileRepository  logFileRepository = new LogFileRepository();		
		List<LogFile> results = logFileRepository.listByHash(fileHash);
		
		ConnectionFactory.getInstance().flush();
		
		return results;
		
	}
	
	



}
