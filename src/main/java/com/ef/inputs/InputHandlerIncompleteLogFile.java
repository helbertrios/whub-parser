package com.ef.inputs;

import java.util.Scanner;

public class InputHandlerIncompleteLogFile {

	public static boolean isNewImportLogFile() {

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		Boolean answer = null;
		do {
			displayQuestion();
			String option = scanner.nextLine();
			answer = validationOption(option);
		} while (answer == null);

		System.out.println("");
		System.out.println("");
		return answer.booleanValue();

	}

	private static Boolean validationOption(final String option) {

		if ((option == null) || ("".equals(option))) {
			return null;
		} else if ("c".equalsIgnoreCase(option)) {
			return false;
		}
		if ("n".equalsIgnoreCase(option)) {
			return true;
		}

		return null;

	}

	private static void displayQuestion() {
		System.out.println("");
		System.out.print("This log file do not finish importation, do you want do it? [c - continue import] or [n - new import]: ");
	}
}
