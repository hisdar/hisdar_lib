package cn.hisdar.lib.log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HLog {

	private static ArrayList<HLogInterface> hLogInterfaces = new ArrayList<HLogInterface>();
	private static HCmdLog cmdHLog = null;
	
	private static final String ERROR_LOG_TAG = "[ERROR]";
	private static final String INFOR_LOG_TAG = "[INFOR]";
	private static final String DEBUG_LOG_TAG = "[DEBUG]";
	
	private static String getTimeInfo() {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
		String dateInfo = dateFormat.format(new Date());
		return dateInfo;
	}

	private static String getExceptionString(Exception e) {
		StringBuffer logString = new StringBuffer();
		logString.append(e.getMessage() + "\n");
		
		StackTraceElement[] stackTraceElements = e.getStackTrace();
		for (int i = 0; i < stackTraceElements.length; i++) {
			logString.append(stackTraceElements[i].toString() + "\n");
		}
		
		return logString.toString();
	}
	
	public static void el(String string) {
		String logInfo = getTimeInfo() + ERROR_LOG_TAG + string + "\n";
		printErrorLogToInterfaces(logInfo);
	}
	
	public static void el(Exception e) {
		el(getExceptionString(e));
	}
	
	public static void il(Exception e) {
		il(getExceptionString(e));
	}

	public static void il(String string) {
		String logInfo = getTimeInfo() + INFOR_LOG_TAG + string + "\n";
		printInfoLogToInterfaces(logInfo);
	}
	
	public static void il(Object obj) {
		il(obj.toString());
	}
	
	public static void il(char ch) {
		il("" + ch);
	}
	
	public static void dl(char ch) {
		dl("" + ch);
	}
	
	public static void el(char ch) {
		el("" + ch);
	}

	public static void dl(String string) {
		String logInfo = getTimeInfo() + DEBUG_LOG_TAG + string + "\n";
		printDebugLogToInterfaces(logInfo);
	}
	
	public static void dl(Exception e) {
		el(getExceptionString(e));
	}

	public static void e(String string) {
		String logInfo = getTimeInfo() + ERROR_LOG_TAG + string;
		printErrorLogToInterfaces(logInfo);
	}
	
	public static void i(String string) {
		String logInfo = getTimeInfo() + INFOR_LOG_TAG + string;
		printInfoLogToInterfaces(logInfo);
	}

	public static void d(String string) {
		String logInfo = getTimeInfo() + DEBUG_LOG_TAG + string;
		printDebugLogToInterfaces(logInfo);
	}
	
	private static void printInfoLogToInterfaces(String log) {
		for (int i = 0; i < hLogInterfaces.size(); i++) {
			hLogInterfaces.get(i).info(log);
		}
	}

	private static void printErrorLogToInterfaces(String log) {
		for (int i = 0; i < hLogInterfaces.size(); i++) {
			hLogInterfaces.get(i).error(log);
		}
	}
	
	private static void printDebugLogToInterfaces(String log) {
		for (int i = 0; i < hLogInterfaces.size(); i++) {
			hLogInterfaces.get(i).debug(log);
		}
	}
	
	public static void addHLogInterface(HLogInterface logLnterface) {
		hLogInterfaces.add(logLnterface);
	}
	
	public static void enableCmdLog() {
		if (cmdHLog == null) {
			synchronized (HLog.class) {
				if (cmdHLog == null) {
					cmdHLog = new HCmdLog();
					hLogInterfaces.add(cmdHLog);
				}
			}
		}
	}

}
