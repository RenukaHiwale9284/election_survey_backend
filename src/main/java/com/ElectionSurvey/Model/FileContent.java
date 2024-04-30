package com.ElectionSurvey.Model;

public class FileContent {

	private String fileName;

	private String fileType;

	private byte[] fileData;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public FileContent(String fileName, String fileType, byte[] fileData) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileData = fileData;
	}

	public FileContent() {
		super();
	}

}
