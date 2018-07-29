package com.ef.inputs;

import java.util.List;
import java.util.Scanner;

import com.ef.repository.to.LogFile;

public class InputHandlerLogFile {

	private static final Integer INDEX_NEW_IMPORT_LOG_FILE = -1;
	private static final String OPTION_NEW_IMPORT_LOG_FILE = "n";

	public static boolean isNewimport(final Integer indexLogFileSelected) {
		return INDEX_NEW_IMPORT_LOG_FILE.equals(indexLogFileSelected);
	}

	public static Integer getIndexLogFileSelected(final List<LogFile> logFilesAlreadyImported) {

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		Integer index = null;
		do {
			displayLogFilesAlreadyImported(logFilesAlreadyImported);
			String option = scanner.nextLine();
			index = validationOption(logFilesAlreadyImported, option);
		} while (index == null);

		System.out.println("");
		System.out.println("");
		return index;

	}

	private static Integer validationOption(final List<LogFile> logFilesAlreadyImported, final String option) {

		if ((option == null) || ("".equals(option))) {
			return null;
		}
		if (OPTION_NEW_IMPORT_LOG_FILE.equalsIgnoreCase(option)) {
			return INDEX_NEW_IMPORT_LOG_FILE;
		} else if (!option.matches("\\d+")) {
			return null;
		}

		Long optionValue = Long.parseLong(option);

		for (int i = 0; i < logFilesAlreadyImported.size(); i++) {
			LogFile logFile = logFilesAlreadyImported.get(i);
			if (optionValue.equals(logFile.getId())) {
				return i;
			}
		}

		return null;
	}

	private static void displayLogFilesAlreadyImported(final List<LogFile> logFilesAlreadyImported) {

		System.out.println("");
		System.out.println("This log file already been imported, following times: ");
		System.out.println("");
		for (LogFile logFile : logFilesAlreadyImported) {
			String message = "	" + "id: [" + logFile.getId() + "] importDate: " + logFile.getImportDate() + " path: "
					+ logFile.getFilePath();
			System.out.println(message);
		}
		System.out.println("");
		System.out.print("Please enter [" + OPTION_NEW_IMPORT_LOG_FILE + " - new import] or [id - use this import]: ");

	}
}
