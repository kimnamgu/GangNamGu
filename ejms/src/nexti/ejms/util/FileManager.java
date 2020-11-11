package nexti.ejms.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

public class FileManager {
	/**
	 * struts 파일업로드 처리
	 * 
	 * @param FormFile, FileBean
	 * @return FileBean
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static Logger logger = Logger.getLogger(FileManager.class);

	private static FileBean doFileUpload(FormFile fileList, FileBean fileBean)
		throws FileNotFoundException, IOException {
		InputStream stream = null;
		String fileDir = null;
		String newFileName = null;
		OutputStream bos = null;
		
		try {
			stream = fileList.getInputStream();

			// 저장할 폴더 지정하지 않으면 오류
			if ((fileBean.getFileUrl() == null)
					|| (fileBean.getFileUrl().equalsIgnoreCase(""))) {
				throw new FileNotFoundException();
			} else {
				fileDir = fileBean.getFileDir();
			}
			// 저장할 파일명 지정하지 않으면 자동생성
			if ((fileBean.getFilenm() == null)
					|| (fileBean.getFilenm().equalsIgnoreCase(""))) {
				newFileName = String.valueOf(System.currentTimeMillis())
						+ String.valueOf((int) (Math.random() * 1000))
						+ Utils.getFileExtension(fileList.getFileName());
			} else {
				newFileName = fileBean.getFilenm();
			}
			fileBean.setFilenm(fileBean.getFileUrl() + newFileName);

			// 파일을 업로드할 절대 경로를 지정해야 한다.
			bos = new FileOutputStream(fileDir + File.separator + newFileName);
			int bytesRead = 0;
			byte[] buffer = new byte[4096];
			while ((bytesRead = stream.read(buffer, 0, buffer.length)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}

			String originfilenm = fileList.getFileName();

			fileBean.setOriginfilenm(originfilenm);
			fileBean.setFilesize(fileList.getFileSize());
			fileBean.setExt(originfilenm.substring(originfilenm.lastIndexOf(".") + 1));
		} finally {
			try { bos.close(); } catch (Exception e) {}
			try { stream.close(); } catch (Exception e) {}
		}
		
		return fileBean;
	}
	
	/**
	 * 파일업로드
	 */
	public static FileBean doFileUpload(FormFile formFile, ServletContext context, String upFolder) throws Exception {
		
		FileBean fileBean = null;
		
		if ( formFile != null ){
			String fileName = formFile.getFileName();

			if ((fileName != null) && (!fileName.equals("")) ) {
				fileBean = new FileBean();
				fileBean.setFileUrl(upFolder);
				fileBean.setFileDir(context.getRealPath(fileBean.getFileUrl()) );
				fileBean.setExt(fileName.substring(fileName.lastIndexOf(".")+1));
				fileBean.setFilesize(formFile.getFileSize());
				
				FileManager.doFileUpload(formFile, fileBean);	
			}
		}	
		return fileBean;
	}

//	전자결재 기안문 생성용 첨부파일 업로드
	public static FileBean doAttachFileMake(String filecontent, String fileExt, String uploadForder, ServletContext sContext, String type)
			throws FileNotFoundException, IOException {
		InputStream is = null;
		OutputStream os = null;
		FileBean fileBean = null;
		String newFileName = null;
	
		try {
			if (type.equals("FILE")) {
				is = new FileInputStream(sContext.getRealPath(filecontent));
			} else if (type.equals("CONTENT")) {
				is = new ByteArrayInputStream(filecontent.getBytes());
			} else {
				return null;
			}
			
			newFileName = String.valueOf(System.currentTimeMillis()) +
						  String.valueOf((int) (Math.random() * 1000));
			uploadForder = sContext.getRealPath(uploadForder);
			
			
			//업로드할 디렉토리 생성
			if(!new File(uploadForder).exists()) {
				new File(uploadForder).mkdirs();
			}
	
			// 파일을 업로드할 절대 경로를 지정해야 한다.
			os = new FileOutputStream(uploadForder + File.separator + newFileName + "." + fileExt);
			int bytesRead = 0;
			int size = 0;
			byte[] buffer = new byte[4096];
			while ((bytesRead = is.read(buffer, 0, buffer.length)) != -1) {
				os.write(buffer, 0, bytesRead);
				size += bytesRead;
			}
	
			fileBean = new FileBean();
			fileBean.setFilenm(newFileName);
			fileBean.setFilesize(size);
			fileBean.setExt(fileExt);
		} finally {
			try { os.close(); } catch (Exception e) {}
			try { is.close(); } catch (Exception e) {}
		}

		return fileBean;
	}

	/**
	 * 하드 디스크에 있는 파일을 삭제한다.
	 *
	 * @param fileList
	 * @throws Exception
	 */
	public synchronized static void doFileDelete(String filepath) throws Exception {
	    File file = new File(filepath);
	    
	    if(file.exists()) {
	     	boolean flag = false;

	     	if (file.isDirectory()) {
	     		File[] files = file.listFiles();
	     		for (int i = 0; i < files.length; i++) {
	     			if (files[i].isDirectory()) {
	     				doFileDelete(files[i].getParent() + "/" + files[i].getName());
	     			} else {
	     				files[i].delete();
	     			}
	     		}
	     		flag = file.delete();
	     	} else {
	     		flag = file.delete();
	     	}
	     	
	     	if(!flag) {
	     		logger.info("파일삭제 도중 오류가 발생했습니다.");
	     	}
	    } else {
	    	logger.info("파일이 존재하지 않습니다.");
	    }
	}
	
	/**
	 * 하드 디스크에 있는 파일의 용량을 체크한다
	 *
	 * @param fileList
	 * @throws Exception
	 */
	public synchronized static int doCheckFileSize(String path) throws Exception {
		int size = 0;
	    File file = new File(path);
	    
	    if(file.exists()) {
	     	boolean flag = false;

	     	if (file.isDirectory()) {
	     		File[] files = file.listFiles();
	     		for (int i = 0; i < files.length; i++) {
	     			if (files[i].isDirectory()) {
	     				size += doCheckFileSize(files[i].getParent() + "/" + files[i].getName());
	     			} else {
	     				size += files[i].length();
	     			}
	     		}
	     		size += file.length();
	     	} else {
	     		size += file.length();
	     	}
	     	
	     	if(!flag) {
	     		logger.info("파일삭제 도중 오류가 발생했습니다.");
	     	}
	    } else {
	    	logger.info("파일이 존재하지 않습니다.");
	    }
	    return size;
	}
	
	/**
	 * 디스크에 있는 파일을 복제한다
	 * @param String filepath
	 * @return String 복제파일명
	 * @throws Exception
	 */
    public static String doFileCopy(String filepath) throws Exception {
    	filepath = filepath.replaceAll("\\\\", "/");
    	String descfilepath = filepath.substring(0, filepath.lastIndexOf("/") + 1);
        return doFileCopy(filepath, descfilepath);
    }
    
    /**
	 * 디스크에 있는 파일을 원하는 경로에 복제한다
	 * @param String filepath
	 * @param String descfiledir
	 * @return String 복제파일명
	 * @throws Exception
	 */
    public synchronized static String doFileCopy(String filepath, String descfilepath) throws Exception {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        String filenm = "";

        try {
			filenm = String.valueOf(System.currentTimeMillis())
					+ String.valueOf((int) (Math.random() * 1000))
					+ Utils.getFileExtension(filepath);

			String descFile = descfilepath + filenm;

			fis = new FileInputStream(filepath);
			fos = new FileOutputStream(descFile);

			int bytesRead = 0;
			byte[] buffer = new byte[4096];
			while ((bytesRead = fis.read(buffer, 0, buffer.length)) != -1) {
				fos.write(buffer, 0, bytesRead);
			}
		} finally {
			try {fos.close();} catch(Exception e) {}
			try {fis.close();} catch(Exception e) {}
		}
        
        return filenm;
    }
}
