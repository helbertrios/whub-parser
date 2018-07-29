package com.ef.file;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.ef.UtilTest;

public class FileReaderTest {
	
	
	
	
	@Test
	public void testOK() {
		
		Path path = Paths.get(UtilTest.getTestFileDirectory(),  "ok.log");
		FileReader fileReader = new FileReader(path);
		String expected = "LineLogFile { lineNumber: 1, accessDate: 2017-01-01T00:00:11.763, ip: 192.168.234.82, action: \"GET / HTTP/1.1\", code: 200, accessInformation: \"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\", source: 2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\" }";
		String actual =  fileReader.getLineReader().getLineLogFile(0).toString();		
		assertEquals(expected, actual);		
	}
	

	@Test
	public void testEmpty() {		
		Path path = Paths.get(UtilTest.getTestFileDirectory(),  "empty.log");
		FileReader fileReader = new FileReader(path);
		String expected = "LineLogFile { lineNumber: 1, accessDate: null, ip: null, action: null, code: null, accessInformation: null, source: null }";
		String actual =  fileReader.getLineReader().getLineLogFile(0).toString();		
		assertEquals(expected, actual);		
	}
	
	@Test
	public void testErrorLine1() {		
		Path path = Paths.get(UtilTest.getTestFileDirectory(),  "erro.log");
		FileReader fileReader = new FileReader(path);
		String expected = "LineLogFile { lineNumber: 1, accessDate: null, ip: null, action: null, code: null, accessInformation: null, source: |||| }";
		String actual =  fileReader.getLineReader().getLineLogFile(0).toString();
		assertEquals(expected, actual);		
	}
	
	@Test
	public void testErrorLine2() {		
		Path path = Paths.get(UtilTest.getTestFileDirectory(),  "erro.log");
		FileReader fileReader = new FileReader(path);
		String expected = "LineLogFile { lineNumber: 2, accessDate: null, ip: null, action: null, code: null, accessInformation: null, source: 192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\" }";
		String actual =  fileReader.getLineReader().getLineLogFile(1).toString();
		assertEquals(expected, actual);		
	}
	
	@Test
	public void testErrorLine3() {		
		Path path = Paths.get(UtilTest.getTestFileDirectory(),  "erro.log");
		FileReader fileReader = new FileReader(path);
		String expected = "LineLogFile { lineNumber: 3, accessDate: null, ip: null, action: null, code: null, accessInformation: null, source: 2017-01-01 00:00:11.763|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\" }";
		String actual =  fileReader.getLineReader().getLineLogFile(2).toString();		
		assertEquals(expected, actual);		
	}
	
	@Test
	public void testErrorLine4() {		
		Path path = Paths.get(UtilTest.getTestFileDirectory(),  "erro.log");
		FileReader fileReader = new FileReader(path);
		String expected = "LineLogFile { lineNumber: 4, accessDate: 2017-01-01T00:00:11.763, ip: 192.168.234.82, action: null, code: null, accessInformation: null, source: 2017-01-01 00:00:11.763|192.168.234.82|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\" }";
		String actual =  fileReader.getLineReader().getLineLogFile(3).toString();		
		assertEquals(expected, actual);		
	}
	
	@Test
	public void testErrorLine5() {		
		Path path = Paths.get(UtilTest.getTestFileDirectory(),  "erro.log");
		FileReader fileReader = new FileReader(path);
		String expected = "LineLogFile { lineNumber: 5, accessDate: 2017-01-01T00:00:11.763, ip: 192.168.234.82, action: null, code: null, accessInformation: null, source: 2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\" }";
		String actual =  fileReader.getLineReader().getLineLogFile(4).toString();		
		assertEquals(expected, actual);		
	}
	
	@Test
	public void testErrorLine6() {		
		Path path = Paths.get(UtilTest.getTestFileDirectory(),  "erro.log");
		FileReader fileReader = new FileReader(path);
		String expected = "LineLogFile { lineNumber: 6, accessDate: 2017-01-01T00:00:11.763, ip: 192.168.234.82, action: null, code: null, accessInformation: null, source: 2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200 }";
		String actual =  fileReader.getLineReader().getLineLogFile(5).toString();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testErrorLine7() {		
		Path path = Paths.get(UtilTest.getTestFileDirectory(),  "erro.log");
		FileReader fileReader = new FileReader(path);
		String expected = "LineLogFile { lineNumber: 7, accessDate: 2017-01-01T00:00:11.763, ip: 192.168.234.82, action: \"GET / HTTP/1.1\", code: 200, accessInformation:  , source: 2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|  }";
		String actual =  fileReader.getLineReader().getLineLogFile(6).toString();
		assertEquals(expected, actual);		
	}
	
	
	@Test
	/* this is not good*/
	public void testErrorLine8() {		
		Path path = Paths.get(UtilTest.getTestFileDirectory(),  "erro.log");
		FileReader fileReader = new FileReader(path);
		String expected = "LineLogFile { lineNumber: 8, accessDate: 2017-01-01T00:00:11.763, ip: 192.168.234.82, action: null, code: null, accessInformation: null, source: 2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200| }";
		String actual =  fileReader.getLineReader().getLineLogFile(7).toString();
		assertEquals(expected, actual);		
	}
	
	
	@Test
	public void testErrorLine9() {		
		Path path = Paths.get(UtilTest.getTestFileDirectory(),  "erro.log");
		FileReader fileReader = new FileReader(path);
		String expected = "LineLogFile { lineNumber: 9, accessDate: 2017-01-01T00:00:11.763, ip: 192.168.234.82, action: null, code: null, accessInformation: null, source: 2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"|xxxx|lsdflskd }";
		String actual =  fileReader.getLineReader().getLineLogFile(8).toString();
		assertEquals(expected, actual);		
	}
	
	@Test
	public void testErrorLine10() {		
		Path path = Paths.get(UtilTest.getTestFileDirectory(),  "erro.log");
		FileReader fileReader = new FileReader(path);
		String expected = "LineLogFile { lineNumber: 10, accessDate: null, ip: null, action: null, code: null, accessInformation: null, source: 2017-01-32 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\" }";
		String actual =  fileReader.getLineReader().getLineLogFile(9).toString();
		assertEquals(expected, actual);		
	}
	
	@Test
	public void testErrorLine11() {		
		Path path = Paths.get(UtilTest.getTestFileDirectory(),  "erro.log");
		FileReader fileReader = new FileReader(path);
		String expected = "LineLogFile { lineNumber: 11, accessDate: null, ip: null, action: null, code: null, accessInformation: null, source: 2017-01-01 00:00:11.763|192.168.234.500|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\" }";
		String actual =  fileReader.getLineReader().getLineLogFile(10).toString();
		assertEquals(expected, actual);		
	}
	
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testErrorLine12() {		
		Path path = Paths.get(UtilTest.getTestFileDirectory(),  "erro.log");
		FileReader fileReader = new FileReader(path);
		fileReader.getLineReader().getLineLogFile(11);
	}
	

		

}
