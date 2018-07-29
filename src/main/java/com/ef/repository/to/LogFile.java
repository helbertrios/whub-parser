package com.ef.repository.to;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class LogFile {

	private Long id;
	private LocalDateTime importDate;
	private String filePath;
	private String fileHash;
	private Boolean importCompleted;
	private Integer lastLineImported;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getImportDate() {
		return importDate;
	}

	public void setImportDate(LocalDateTime importDate) {
		this.importDate = importDate;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileHash() {
		return fileHash;
	}

	public void setFileHash(String fileHash) {
		this.fileHash = fileHash;
	}

	public Boolean getImportCompleted() {
		return importCompleted;
	}

	public void setImportCompleted(Boolean importCompleted) {
		this.importCompleted = importCompleted;
	}
	
	public Path toPath() {
		return Paths.get(this.getFilePath());
	}
	
	public Integer getLastLineImported() {
		return lastLineImported;
	}

	public void setLastLineImported(Integer lastLineImported) {
		this.lastLineImported = lastLineImported;
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		result.append(this.getClass().getName());
		result.append(" Object {");
		result.append(newLine);

		// determine fields declared in this class only (no fields of superclass)
		Field[] fields = this.getClass().getDeclaredFields();

		// print field names paired with their values
		for (Field field : fields) {
			result.append("  ");
			try {
				result.append(field.getName());
				result.append(": ");
				// requires access to private field:
				result.append(field.get(this));
			} catch (IllegalAccessException ex) {
				System.out.println(ex);
			}
			result.append(newLine);
		}
		result.append("}");

		return result.toString();
	}

}
