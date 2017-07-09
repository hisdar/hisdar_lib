package cn.hisdar.lib.log;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;
public class HZip {
//	    public static void main(String[] args) throws FileNotFoundException, Exception {
//	        Main main = new Main("c:/test1.zip");
//	        main.createZipOut();
//	        main.packToolFiles("C:/test", "");
//	        main.closeZipOut();
//	 
//	        main = new Main("c:/test2.zip");
//	        main.createZipOut();
//	        main.packToolFiles("C:/test", "test");
//	        main.closeZipOut();
//	 
//	        Main.unZipToFolder("c:/test1.zip", "c:/test1");
//	        
//	        Main.unZipToFolder("c:/test2.zip", "c:/test2");
//	    }
	    private OutputStream out = null;
	    private BufferedOutputStream bos = null;
	    private ZipArchiveOutputStream zaos = null;
	    private String zipFileName = null;
	 
	    public HZip(String zipname) {
	        this.zipFileName = zipname;
	    }
	 
	    public void createZipOut() throws FileNotFoundException, IOException {
	        File f = new File(zipFileName);
	        out = new FileOutputStream(f);
	        bos = new BufferedOutputStream(out);
	        zaos = new ZipArchiveOutputStream(bos);
	        zaos.setEncoding("GBK");
	 
	    }
	 
	    public void closeZipOut() throws Exception {
	        zaos.flush();
	        zaos.close();
	 
	        bos.flush();
	        bos.close();
	 
	        out.flush();
	        out.close();
	    }
	 
	    /**
	     * ��һ��Ŀ¼�����zip�ļ��е�ĳĿ¼
	     * @param dirpath   Ŀ¼���Ե�ַ
	     * @param pathName  zip��Ŀ¼
	     */
	    public void packToolFiles(String dirpath, String pathName) throws FileNotFoundException, IOException {
//	        TODO 
	    	//if (StringUtils.isNotEmpty(pathName)) {
//	            pathName = pathName + File.separator;
//	        }
	        packToolFiles(zaos, dirpath, pathName);
	    }
	 
	    /**
	     * ��һ��Ŀ¼�����һ��ָ����zip�ļ���
	     * @param dirpath           Ŀ¼���Ե�ַ
	     * @param pathName       zip�ļ������ַ
	     */
	    public void packToolFiles(ZipArchiveOutputStream zaos, String dirpath, String pathName) throws FileNotFoundException, IOException {
	 
	        ByteArrayOutputStream tempbaos = new ByteArrayOutputStream();
	        BufferedOutputStream tempbos = new BufferedOutputStream(tempbaos);
	 
	        File dir = new File(dirpath);
	        //���ش˾���·���µ��ļ�
	        File[] files = dir.listFiles();
	        if (files == null || files.length < 1) {
	            return;
	        }
	        for (int i = 0; i < files.length; i++) {
	            //�жϴ��ļ��Ƿ���һ���ļ���
	            if (files[i].isDirectory()) {
	                packToolFiles(zaos, files[i].getAbsolutePath(), pathName + files[i].getName() + File.separator);
	            } else {
	                zaos.putArchiveEntry(new ZipArchiveEntry(pathName + files[i].getName()));
	                IOUtils.copy(new FileInputStream(files[i].getAbsolutePath()), zaos);
	                zaos.closeArchiveEntry();
	 
	            }
	 
	        }
	 
	        tempbaos.flush();
	        tempbaos.close();
	 
	        tempbos.flush();
	        tempbos.close();
	    }
	 
	    /**
	     * ��һ��zip�ļ���ѹ��һ��ָ����Ŀ¼��
	     * @param zipfilename   zip�ļ������ַ
	     * @param outputdir     Ŀ¼���Ե�ַ
	     */
	    public static void unZipToFolder(String zipfilename, String outputdir) throws IOException {
	        File zipfile = new File(zipfilename);
	        if (zipfile.exists()) {
	            outputdir = outputdir + File.separator;
	            //FileUtils.forceMkdir(new File(outputdir));
	 
	            ZipFile zf = new ZipFile(zipfile, "GBK");
	            Enumeration zipArchiveEntrys = zf.getEntries();
	            while (zipArchiveEntrys.hasMoreElements()) {
	                ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) zipArchiveEntrys.nextElement();
	                if (zipArchiveEntry.isDirectory()) {
	                    //TODO FileUtils.forceMkdir(new File(outputdir + zipArchiveEntry.getName() + File.separator));
	                } else {
	                    //TODO IOUtils.copy(zf.getInputStream(zipArchiveEntry), FileUtils.openOutputStream(new File(outputdir + zipArchiveEntry.getName())));
	                }
	            }
	        } else {
	            throw new IOException("ָ���Ľ�ѹ�ļ������ڣ�\t" + zipfilename);
	        }
	    }
}
