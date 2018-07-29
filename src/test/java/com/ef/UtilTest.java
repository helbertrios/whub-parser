package com.ef;

import java.io.File;

public class UtilTest {

	public static String getTestFileDirectory() {
		return System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"  + File.separator + "resources"+ File.separator + "fileLogsTest";
	}
	
	
	public static String getFilePath() {
		return getTestFileDirectory() + File.separator + "ok.log";	
	}
	
}
