package cn.hisdar.lib.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.filechooser.FileNameExtensionFilter;

import cn.hisdar.lib.adapter.FileAdapter;
import cn.hisdar.lib.print.HPrint;

public class HFileLog implements HLogInterface {

	private static final int DEFAULT_LOG_FILE_COUNT = 10;
	private static final int DEFAULT_ZIP_FILE_COUNT = 10;
	private static final int DEFAULT_LOG_FILE_SIZE  = 1024 * 1024;
	private static final String DEFAULT_LOG_FILE_NAME = "programLog.log";
	private static final String DEFAULT_LOG_FOLDER_NAME = "./log/";
	
	private String logFileName = DEFAULT_LOG_FILE_NAME;
	private String logFileFolder = DEFAULT_LOG_FOLDER_NAME;
	
	private int maxLogFileCount = DEFAULT_LOG_FILE_COUNT;
	private int maxZipFileCount = DEFAULT_ZIP_FILE_COUNT;
	
	private long logFileSize = DEFAULT_LOG_FILE_SIZE; 
	
	private boolean isZipLogFile = true;
	
	private File logFile = null;
	
	private FileOutputStream logFileOutPutStream = null;
	
	public HFileLog() {
		initOutputStream();
	}
	
	public HFileLog(String fileFolder, String fileName) {
		this.logFileFolder = fileFolder;
		this.logFileName = fileName;
		
		initOutputStream();
	}
	
	public void logOut(String log) {
		if (logFileOutPutStream == null) {
			System.err.println("Log file is not inited");
			return;
		}
		
		if (logFile.length() + log.length() > logFileSize) {
			submitLog();
		}
		
		try {
			logFileOutPutStream.write(log.getBytes());
		} catch (IOException e) {
			System.err.println("Write data to file fail:" + e.getMessage());
			return;
		}
	}
	
	private boolean initOutputStream() {
		if (logFileFolder == null || logFileName == null) {
			return false;
		}
		
		FileAdapter.initFolder(logFileFolder);
		String logFilePath = FileAdapter.pathCat(logFileFolder, logFileName);
		logFile = new File(logFilePath);
		try {
			logFileOutPutStream = new FileOutputStream(new File(logFilePath), true);
		} catch (FileNotFoundException e) {
			System.err.println("Log file out put stream init fail:" + e.getMessage());
			return false;
		}
		
		return true;
	}
	
	private boolean submitLog() {
		
		// 1. close log file output stream
		try {
			logFileOutPutStream.flush();
		} catch (IOException e) {
			System.err.println("Log file out put stream flush fail:" + e.getMessage());
			return false;
		}
		
		try {
			logFileOutPutStream.close();
		} catch (IOException e) {
			System.err.println("Log file out put stream close fail:" + e.getMessage());
			return false;
		}
		
		// 2. check log files count and zip log files if necessary
		ArrayList<File> logFiles = getLogFiles();
		if (logFiles.size() >= maxLogFileCount) {
			if (isZipLogFile) {
				zipLogFiles(logFiles);
			} else {
				File logFile = new File(FileAdapter.pathCat(logFileFolder, logFileName));
				renameFileToUper(logFile, FileAdapter.getFileNameNoExtendName(logFileName), maxLogFileCount);
			}
		} else {
			// rename log files
			File logFile = new File(FileAdapter.pathCat(logFileFolder, logFileName));
			renameFileToUper(logFile, FileAdapter.getFileNameNoExtendName(logFileName), maxLogFileCount);
		}
		
		// 3. reinit log file output stream
		initOutputStream();
		
		return true;
	}
	
	private void sortLogFileList(ArrayList<File> logFiles) {
		int minLogFileIndex = 0;
		for (int i = 0; i < logFiles.size(); i++) {
			minLogFileIndex = i;
			for (int j = i + 1; j < logFiles.size(); j++) {
				if (compareFileName(logFiles.get(minLogFileIndex).getPath(), logFiles.get(j).getPath()) > 0) {
					minLogFileIndex = j;
				}
			}
			
			if (minLogFileIndex != i) {
				File exchangeFile = logFiles.get(i);
				logFiles.set(i, logFiles.get(minLogFileIndex));
				logFiles.set(minLogFileIndex, exchangeFile);
			}
		}
	}

	private boolean renameFileToUper(File srcFile, String basicFileName, int maxFileNumber) {
		String fileName = srcFile.getName();
		if (fileName.indexOf(basicFileName) != 0) {
			System.err.println("not target file");
			return false;
		}
		
		int fileNumber = getFileNumber(fileName, basicFileName);
		if (fileNumber < 0) {
			System.err.println("No file number found");
			return false;
		}
		
		fileNumber += 1;
		
		String uperFileName = basicFileName + "-" + fileNumber;
		String fileExtendName = FileAdapter.getFileExtendName(srcFile);
		if (fileExtendName != null) {
			uperFileName = uperFileName + "." + fileExtendName;
		}
		
		String uperFilePath = FileAdapter.pathCat(logFileFolder, uperFileName);
		System.out.println("uperFilePath=" + uperFilePath);
		File uperFile = new File(uperFilePath);
		if (uperFile.exists()) {
			if (fileNumber >= maxFileNumber) {
				uperFile.delete();
			} else {
				renameFileToUper(uperFile, basicFileName, maxFileNumber);
			}
		}
		
		srcFile.renameTo(uperFile);
		return true;
	}
	
	private String getBaseZipFileName() {
		String baseLogFileName = FileAdapter.getFileNameNoExtendName(logFileName);
		return baseLogFileName + "-pkg";
	}
	
	private boolean zipLogFiles(ArrayList<File> logFiles) {

		// 1、get zip file list
		ArrayList<File> zipFileList = FileAdapter.getFileList(logFileFolder);
		int fileCount = zipFileList.size();
		for (int i = fileCount - 1; i >= 0; i--) {
			if (!ZipFileUtil.isEndsWithZip(zipFileList.get(i).getName())) {
				zipFileList.remove(i);
			}
		}
		
		// 2、check zip file and remove unlegal zip file
		int fileNumber = -1;
		fileCount = zipFileList.size();
		for (int i = fileCount - 1; i >= 0; i--) {
			fileNumber = getFileNumber(zipFileList.get(i).getName(), getBaseZipFileName());
			if (fileNumber < 0 || fileNumber >= maxZipFileCount) {
				zipFileList.get(i).delete();
				zipFileList.remove(i);
			}
		}
		
		// 3、zip log files
		sortLogFileList(zipFileList);
		if (zipFileList.size() == maxLogFileCount) {
			zipFileList.get(zipFileList.size() - 1).delete();
			zipFileList.remove(zipFileList.size() - 1);
		}
		
		if (zipFileList.size() > 0) {
			renameFileToUper(zipFileList.get(0), getBaseZipFileName(), maxZipFileCount);
		}
		
		String zipFilePath = FileAdapter.getFileNameNoExtendName(logFileName);
		zipFilePath = zipFilePath + "-pkg.zip";
		zipFilePath = FileAdapter.pathCat(logFileFolder, zipFilePath);
		
		File[] logFileList = new File[logFiles.size()];
		for (int i = 0; i < logFileList.length; i++) {
			logFileList[i] = logFiles.get(i);
		}
		
		ZipFileUtil.compressFiles2Zip(logFileList, zipFilePath);
		
		for (int i = 0; i < logFiles.size(); i++) {
			logFiles.get(i).delete();
			logFiles.remove(i);
		}
		
		return true;
	}

	private ArrayList<File> getLogFiles() {
		ArrayList<File> logFileList = new ArrayList<File>();
		
		File logFolder = new File(logFileFolder);
		File[] logFolderFiles = logFolder.listFiles();
		for (int i = 0; i < logFolderFiles.length; i++) {
			if (logFolderFiles[i].isFile()) {
				String fileName = logFolderFiles[i].getName();
				if (getFileNumber(fileName, FileAdapter.getFileNameNoExtendName(logFileName)) >= 0) {
					logFileList.add(logFolderFiles[i]);
				}
			}
		}
		
		return logFileList;
	}
	
	private int getFileNumber(String fileName, String fileBaseName) {
		
		System.out.println("fileName=" + fileName);
		System.out.println("fileBaseName=" + fileBaseName);
		if (fileName.indexOf(fileBaseName) != 0) {
			System.out.println("No basic name found");
			return -1;
		}
		
		if (FileAdapter.getFileNameNoExtendName(fileName).equals(fileBaseName)) {
			System.out.println("Basic file");
			return 0;
		}
		
		String fileNumberString = fileName.substring(fileBaseName.length());
		if (fileNumberString.charAt(0) != '-') {
			System.out.println("No - found");
			return -1;
		}
		
		fileNumberString = FileAdapter.getFileNameNoExtendName(fileNumberString);
		fileNumberString = fileNumberString.substring(1);
		int fileNumber = -1;
		try {
			fileNumber = Integer.parseInt(fileNumberString);
		} catch (NumberFormatException e) {
			System.err.println(e.getMessage());
		}
		
		return fileNumber;
	}
	
	/**
	 * @description 1、文件名长的，比较大；2、文件名一样长的，直接比较字符
	 * @return 
	 */
	private int compareFileName(String fileName1, String fileName2) {
		if (fileName1.length() == fileName2.length()) {
			return fileName1.compareToIgnoreCase(fileName2);
		} else {
			return fileName1.length() - fileName2.length();
		}
	}
	
	public String getFileName() {
		return logFileName;
	}

	public void setFileName(String fileName) {
		this.logFileName = fileName;
	}

	public String getFileFolder() {
		return logFileFolder;
	}

	public void setFileFolder(String fileFolder) {
		this.logFileFolder = fileFolder;
	}

	public int getMaxLogFileCount() {
		return maxLogFileCount;
	}

	public void setMaxLogFileCount(int maxLogFileCount) {
		this.maxLogFileCount = maxLogFileCount;
	}

	public int getMaxZipFileCount() {
		return maxZipFileCount;
	}

	public void setMaxZipFileCount(int maxZipFileCount) {
		this.maxZipFileCount = maxZipFileCount;
	}

	public long getLogFileSize() {
		return logFileSize;
	}

	public void setLogFileSize(long logFileSize) {
		this.logFileSize = logFileSize;
	}

	public boolean isZipLogFile() {
		return isZipLogFile;
	}

	public void setZipLogFile(boolean isZipLogFile) {
		this.isZipLogFile = isZipLogFile;
	}

	@Override
	public void info(String log) {
		logOut(log);
	}

	@Override
	public void error(String log) {
		logOut(log);
	}

	@Override
	public void debug(String log) {
		logOut(log);
	}
	
}
