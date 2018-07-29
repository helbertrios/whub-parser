package com.ef;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ef.inputs.InputHandlerTest;

@SuiteClasses({InputHandlerTest.class})
@RunWith(Suite.class)
public class InputHandlerSuite {
	@BeforeClass 
    public static void setUpClass() { 
		//LogConfig.setLevel(Level.FINE);}
	}
}
