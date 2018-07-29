package com.ef;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ef.file.FileReaderTest;

@SuiteClasses({FileReaderTest.class})
@RunWith(Suite.class)
public class FileSuite {
	@BeforeClass 
    public static void setUpClass() { 
		//LogConfig.setLevel(Level.FINE);}
	}
}
