package cn.hisdar.lib.local;

import java.util.Properties;

import cn.hisdar.HFolderDialog.HFolderDialog;
import cn.hisdar.lib.adapter.FileAdapter;

public class Windows {

	private static boolean isLoadLib = false;
	private static String x64LibFileName = "JNI-Windows_x64";
	private static String x86LibFileName = "JNI-Windows_x86";
	
	public Windows() {
		loadLib("./lib", getNativeLibFileName());
	}

	public Windows(String libFolder) {
		
		loadLib(libFolder, getNativeLibFileName());
	}
	
	public Windows(String libFolder, String libFileName) {
		loadLib(libFolder, libFileName);
	}
	
	private String getNativeLibFileName() {
		String libFileName = "";
		
		Properties props = System.getProperties();
		String bits = String.valueOf(props.get("sun.arch.data.model"));  
		if (bits.equals("64")) {
			libFileName = x64LibFileName;
		} else if (bits.equals("32")) {
			libFileName = x86LibFileName;
		}
		
		return libFileName;
	}
	
	private void loadLib(String libFolder, String libFileName) {
		if (isLoadLib) {
			return;
		}
		
		synchronized (HFolderDialog.class) {
			if (isLoadLib) {
				return;
			}

			System.loadLibrary(FileAdapter.pathCat(libFolder, libFileName));
			
			isLoadLib = true;
		}
	}
	
	public native boolean createShortCut(String srcPath, String tagFolder, String param);
	
}
