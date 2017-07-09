package cn.hisdar.lib.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileAdapter {

	public static String pathCat(String pathHead, String pathTail) {
		String formatedHead = pathHead;//.replace('\\', '/');
		String formatedTail = pathTail;//.replace('\\', '/');
		
		if (formatedTail.startsWith("/") || formatedTail.startsWith("\\")) {
			formatedTail = formatedTail.substring(1);
		}
		
		if (!formatedHead.endsWith("/") && !formatedHead.endsWith("\\")) {
			formatedHead = formatedHead + "/";
		}
		
		return formatedHead + formatedTail;
	}

	public static boolean saveStringToFile(String string, String path) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(path);
			fileOutputStream.write(string.getBytes());
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public static boolean initFolder(String folderPath) {
		File folderPathFile = new File(folderPath);
		if (folderPathFile.exists()) {
			if (folderPathFile.isDirectory()) {
				return true;
			} else {
				return false;
			}
		}

		return folderPathFile.mkdirs();
	}
	
	public static ArrayList<File> getFileListAtCurrentFolder(String folderPath) {
		
		File currentFolder = new File(folderPath);
		if (!currentFolder.isDirectory()) {
			return null;
		}
		
		ArrayList<File> fileList = new ArrayList<File>();
		File[] childFiles = currentFolder.listFiles();
		for (int i = 0; i < childFiles.length; i++) {
			if (childFiles[i].isFile()) {
				fileList.add(childFiles[i]);
			}
		}
		
		return fileList;
	}
	
	public static ArrayList<File> getFileAndFolderListAtCurrentFolder(String folderPath) {
		
		File currentFolder = new File(folderPath);
		if (!currentFolder.isDirectory()) {
			return null;
		}
		
		ArrayList<File> fileList = new ArrayList<File>();
		File[] childFiles = currentFolder.listFiles();
		for (int i = 0; i < childFiles.length; i++) {
			if (!childFiles[i].getName().equals(".") && !childFiles[i].getName().equals("..")) {
				fileList.add(childFiles[i]);
			}
		}
		
		return fileList;
	}
	
	public static ArrayList<File> getFileList(String folderPath) {
		
		File currentFolder = new File(folderPath);
		if (!currentFolder.isDirectory()) {
			return null;
		}
		
		ArrayList<File> fileList = new ArrayList<File>();
		File[] childFiles = currentFolder.listFiles();
		for (int i = 0; i < childFiles.length; i++) {
			if (childFiles[i].getName().equals(".") || childFiles[i].getName().equals("..")) {
				continue;
			}
			
			if (childFiles[i].isDirectory()) {
				ArrayList<File> childFoldersFile = getFileList(childFiles[i].getPath());
				fileList.addAll(childFoldersFile);
			} else if (childFiles[i].isFile()) {
				fileList.add(childFiles[i]);
			}
		}
		
		return fileList;
	}
	
	public static String getFileExtendName(File file) {
		return getFileExtendName(file.getName());
	}
	
	public static String getFileExtendName(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index < 0) {
			return null;
		}
		
		return fileName.substring(index + 1);
	}
	
	public static String getFileNameNoExtendName(File file) {
		return getFileNameNoExtendName(file.getName());
	}
	
	public static String getFileNameNoExtendName(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index < 0) {
			return fileName;
		}
		
		return fileName.substring(0, index);
	}

	public static boolean copyFile(String srcFilePath, String toPath) {
		File srcFile = new File(srcFilePath);
		if (srcFile.isDirectory()) {
			return false;
		}
		
		String toFilePath = null;
		File toPathFile = new File(toPath);
		if (toPathFile.exists()) {
			if (toPathFile.isDirectory()) {
				toFilePath = pathCat(toPath, srcFile.getName());
			} else {
				toFilePath = toPath;
			}
		} else {
			if (toPath.trim().endsWith("/") || toPath.trim().endsWith("\\")) {
				toFilePath = pathCat(toPath, srcFile.getName());
			} else {
				toFilePath = toPath;
			}
		}
		
		if (!initFile(toFilePath)) {
			return false;
		}
		
		byte[] copyBuffer = new byte[1024];
		int readCount = 0;
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		try {
			fileInputStream = new FileInputStream(srcFile);
			fileOutputStream = new FileOutputStream(new File(toFilePath));
			readCount = fileInputStream.read(copyBuffer);
			while (readCount > 0) {
				fileOutputStream.write(copyBuffer, 0, readCount);
				readCount = fileInputStream.read(copyBuffer);				
			}
			
			fileOutputStream.close();
			fileInputStream.close();
		} catch (IOException e) {
			//System.err.printf("Fail to copy %s >>> %s\n", srcFilePath, toFilePath);
			return false;
		}
		
		return true;
	}

	public static boolean initFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			if (file.isDirectory()) {
				System.out.println("path is directory");
				return false;
			} else {
				return true;
			}
		}
		
		String parentPath = file.getParent();
		if (!initFolder(parentPath)) {
			System.out.println("path is directory");
			return false;
		}
		
		try {
			return file.createNewFile();
		} catch (IOException e) {
			System.err.println("Fail to create file:" + filePath);
			return false;
		}
	}

	public static boolean deleteFolder(String projectPath) {
		File folderFile = new File(projectPath);
		if (!folderFile.isDirectory()) {
			return folderFile.delete();
		} else {
			File[] childFiles = folderFile.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				return folderFile.delete();
			} else {
				for (int j = 0; j < childFiles.length; j++) {
					if (!deleteFolder(childFiles[j].getPath())) {
						return false;
					}
				}
			}
			
			folderFile.delete();
		}
		
		return true;
	}

	public static boolean copyFolder(String srcPath, String toPath, boolean withCurrentFolder) {
		
		File[] fileListToCopy = null;
		
		if (withCurrentFolder) {
			fileListToCopy = new File[1];
			fileListToCopy[0] = new File(srcPath);
		} else {
			fileListToCopy = new File(srcPath).listFiles();
		}
		
		for (int i = 0; i < fileListToCopy.length; i++) {
			if (fileListToCopy[i].isFile()) {
				if (copyFile(fileListToCopy[i].getPath(), pathCat(toPath, fileListToCopy[i].getName()))) {
					return false;
				}
			} else if (fileListToCopy[i].isDirectory()) {
				// create directory
				String targetFolderPath = pathCat(toPath, fileListToCopy[i].getName());
				if (!initFolder(targetFolderPath)) {
					return false;
				} else {
					if (!copyFolder(fileListToCopy[i].getPath(), targetFolderPath, false)) {
						return false;
					}
				}
			}
		}
		
		return true;
	}

	public static String readFile(String filePath) {
		return readFile(new File(filePath));
	}

	public static String readFile(File file) {
		if (!file.isFile()) {
			return null;
		}
		
		StringBuilder fileData = new StringBuilder();
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		byte[] readBuf = new byte[1024 * 4];
		try {
			int readCount = fileInputStream.read(readBuf);
			while (readCount > 0) {
				String currentData = new String(readBuf, 0, readCount);
				fileData.append(currentData);
				readCount = fileInputStream.read(readBuf);
			}
		} catch (IOException e) {
			e.printStackTrace();
			fileData = null;
		}
		
		try {
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (fileData != null) {
			return fileData.toString();
		} else {
			return null;
		}
	}
	
	public static boolean writeFile(String filePath, String data, boolean append) throws IOException {
		if (!initFile(filePath)) {
			return false;
		}
		
		FileOutputStream fileOutputStream = null;
		if (append) {
			fileOutputStream = new FileOutputStream(filePath, true);
		} else {
			fileOutputStream = new FileOutputStream(filePath);
		}
		
		fileOutputStream.write(data.getBytes());
		fileOutputStream.flush();
		fileOutputStream.close();
		
		return true;
	}
}
