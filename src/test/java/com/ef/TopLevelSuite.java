package com.ef;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@SuiteClasses({ConvertAndValidateSuite.class, InputHandlerSuite.class, FileSuite.class})
@RunWith(Suite.class)
public class TopLevelSuite {}


