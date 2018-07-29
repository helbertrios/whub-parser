package com.ef.file;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ef.repository.to.LineLogFile;
import com.google.common.primitives.Longs;

public class FileReader implements Iterable<LineLogFile>{
	
	
	private int currentSize;
	private FileReader.LineReader lineReader;	
	
	public FileReader(Path path) {
		super();
		
		try {
		  this.lineReader = new FileReader.LineReader(path);
		} catch (Exception e) {
			throw new RuntimeException("Cannot read file: "+path.toAbsolutePath().toString(), e);
		}
		this.currentSize = lineReader.getLinesCount();
	}
	
	
	
	public FileReader.LineReader getLineReader() {
		return lineReader;
	}

	@Override
    public Iterator<LineLogFile> iterator() {
        Iterator<LineLogFile> it = new Iterator<LineLogFile>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < currentSize &&   lineReader.getLineLogFile(currentIndex)  != null;
            }

            @Override
            public LineLogFile next() {
                return lineReader.getLineLogFile(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    } 

	public static class LineReader {
		
		private final Path path;
		private final long offsets[];
		private final static int BUFFER_SIZE = 1024;
		private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.SSS").withResolverStyle(ResolverStyle.STRICT);
		
		private LineReader(Path path) {
			
			this.path = path;		
			
			List<Long> offsetsList = new ArrayList<>();
			try (SeekableByteChannel sbc = Files.newByteChannel(this.path)) {
				byte[] buf = new byte[BUFFER_SIZE];
				long offset = 0;
				offsetsList.add(0L);
				while (sbc.position() < sbc.size()) {
					ByteBuffer dst = ByteBuffer.wrap(buf);
					sbc.read(dst);
					InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(buf));
					int processedOffset = 0;
					while (reader.ready()) {
						Character ch = (char) reader.read();
						int length = ch.toString().getBytes().length;
						offset += length;
						processedOffset += length;
						if (ch == '\n') {
							offsetsList.add(offset);
						}
						if (processedOffset > BUFFER_SIZE - 10) {
							sbc.position(offset);
							break;
						}
					}
				}
				
				int lineIndex = offsetsList.size()-1;				
				long expectedSize = (lineIndex < offsetsList.size() - 1 ? offsetsList.get((lineIndex + 1)) : sbc.size()) - offsetsList.get((lineIndex));
				if (expectedSize < 0) { offsetsList.remove(lineIndex); };
				
			} catch (IOException e) {
				throw new RuntimeException("Cannot read file: "+this.path.toAbsolutePath().toString(), e);
			}
			offsets = Longs.toArray(offsetsList);
		}
		
		private String getLine(int lineIndex)  {
			try (SeekableByteChannel sbc = Files.newByteChannel(this.path)) {
				long expectedSize = (lineIndex < offsets.length - 1 ? offsets[lineIndex + 1] : sbc.size()) - offsets[lineIndex];
				byte[] buf = new byte[(int) expectedSize];
				ByteBuffer dst = ByteBuffer.wrap(buf);
				sbc.position(offsets[lineIndex]);
				sbc.read(dst);
				BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buf)));
				return br.readLine();
			} catch (IOException e) {
				throw new RuntimeException("Cannot read line "+(lineIndex+1)+" of file: "+this.path.toAbsolutePath().toString(), e);
			}
		}

		public int getLinesCount() {
			return offsets.length;
		}
		
		public LineLogFile getLineLogFile(Integer lineIndex) {
			
			if (lineIndex >= getLinesCount()) {
				throw new ArrayIndexOutOfBoundsException("Index: "+lineIndex+ " length: "+getLinesCount());
			}
			
			LineLogFile result = new LineLogFile();
			 
			String line = this.getLine(lineIndex);
			result.setLineNumber(lineIndex+1);
			result.setSource(line);
			
			
			if (line == null) {	return result;	}						
			if ("".equals(line)) {	return result;	}
					
						
			String[] linePieces = line.split("\\|");
			
	
			LocalDateTime accessDate = convertAccessDate(getIfExists(linePieces,0));
			String ip = convertIP(getIfExists(linePieces,1));
			
			if ( (accessDate == null) || (ip == null) ) {
				return result;
			}
			
			result.setAccessDate(accessDate);
			result.setIp(ip);
			
			if (linePieces.length == 5) { 			
				result.setAction(getIfExists(linePieces,2));
				result.setCode(convertCode(getIfExists(linePieces,3)));
				result.setAccessInformation(getIfExists(linePieces,4));
			}
			
			return result;			
			
		}
		
		private String getIfExists(String[] linePieces, int index) {
			String result = null;
			
			if (linePieces.length > index) {
				result = linePieces[index];
			}			
			
			return result;
		}
		
		private String convertIP(final String pieceLine) {
			
			String result =  null;
			
			if (pieceLine != null) {
				final String pieceLineEvaluate = pieceLine.trim();	
				if (validIP(pieceLineEvaluate)) {				
					int lineEnd = pieceLineEvaluate.length() > 15 ? 15 : pieceLineEvaluate.length();
					result = pieceLineEvaluate.substring(0,lineEnd);
				}				
			} 
			return result;	
			
		}
		
		public static boolean validIP (String ip) {
		    try {
		        if ( ip == null || ip.isEmpty() ) {
		            return false;
		        }

		        String[] parts = ip.split( "\\." );
		        if ( parts.length != 4 ) {
		            return false;
		        }

		        for ( String s : parts ) {
		            int i = Integer.parseInt( s );
		            if ( (i < 0) || (i > 255) ) {
		                return false;
		            }
		        }
		        if ( ip.endsWith(".") ) {
		            return false;
		        }

		        return true;
		    } catch (NumberFormatException nfe) {
		        return false;
		    }
		}
		
		private LocalDateTime convertAccessDate(final String pieceLine) {
			LocalDateTime result;
			try {
				result = LocalDateTime.parse(pieceLine, FORMATTER);
			} catch (Exception e) {			
				result = null;
			}
			
			return result;
		}
		
		private Integer convertCode(final String pieceLine) {
			
			Integer result =  null;
			
			if (pieceLine != null) {
				final String pieceLineEvalute = pieceLine.trim();
				if (pieceLineEvalute.matches("\\d+")) {
					result = Integer.parseInt(pieceLineEvalute);
				}			
			} 
			return result;	
			
		}


	}
	



}
