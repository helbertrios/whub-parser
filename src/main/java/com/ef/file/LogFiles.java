package com.ef.file;

import java.io.BufferedInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

public class LogFiles {
	
	public static String hashFile(Path path)  {
		try {
			final byte[] buffer= new byte[8192];		
			final MessageDigest digest = MessageDigest.getInstance("SHA-512");
			final BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(path));
			
			int count;
		    while ((count = bis.read(buffer)) > 0) {
		        digest.update(buffer, 0, count);
		    }
		    bis.close();
	
		    final byte[] hash = digest.digest();
		    String hashBase64 = DatatypeConverter.printBase64Binary(hash);
		    return hashBase64;
		} catch (Exception e) {
			throw new RuntimeException("Cannot obtain file hash");
		}
	}
	


}
