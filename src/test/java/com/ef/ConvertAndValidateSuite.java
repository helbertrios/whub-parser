package com.ef;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ef.inputs.ConverterAndValidateDurationTest;
import com.ef.inputs.ConverterAndValidateFilePathTest;
import com.ef.inputs.ConverterAndValidateStartDateTest;
import com.ef.inputs.ConverterAndValidateThresholdTest;

@SuiteClasses({ConverterAndValidateStartDateTest.class,	ConverterAndValidateDurationTest.class, 	ConverterAndValidateThresholdTest.class, ConverterAndValidateFilePathTest.class})
@RunWith(Suite.class)
public class ConvertAndValidateSuite {
	
	@BeforeClass 
    public static void setUpClass() { 
		//LogConfig.setLevel(Level.FINE);}
	}
	
}
