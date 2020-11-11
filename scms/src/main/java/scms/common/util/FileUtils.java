package scms.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Component("fileUtils")
public class FileUtils {
	Logger log = Logger.getLogger(this.getClass());
	
	
	public List<Map<String,Object>> parseInsertFileInfo(Map<String,Object> map, HttpServletRequest request) throws Exception{
		log.debug("아아11");
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
    	Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
    	String filePath = null;    	
    	String file_dir_path = null;
		
		file_dir_path = CommonLib.getTimeStampStringShort();
		file_dir_path = file_dir_path.substring(0, 6);
		//System.out.println("file_dir_path=[" + file_dir_path + "]\t");
		
		
    	if(request.getServerName().equals("98.23.7.109"))  //운영서버	
    		filePath = "/usr/local/server/file/scms/" + file_dir_path + "/";
    	else 
    		filePath = "C:\\dev\\file\\scms\\" + file_dir_path + "\\";
    	
    	log.debug("server1===> [ "+ request.getServerName() + "]");
    	
    	
    	MultipartFile multipartFile = null;
    	String originalFileName = null;
    	String originalFileExtension = null;
    	String storedFileName = null;
    	
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> listMap = null; 
        
        String boardIndId = (String)map.get("SID");//sub  개별 id
        String bdId = (String)map.get("BOARD_GB"); //sub duty_gb
        String bdId2 = (String)map.get("BOARD_GB2"); //sub duty_gb  6: 출장점검 복명서
        String ftname = null;  //file input 이름
        
        File file = new File(filePath);
        if(file.exists() == false){
        	file.mkdirs();
        }
        
        while(iterator.hasNext()){
        	multipartFile = multipartHttpServletRequest.getFile(iterator.next());
        	//System.out.println("size=[" + multipartFile.getSize() + "]");
        	System.out.println("name=[" + multipartFile.getName() + "]");
        	ftname = multipartFile.getName();
        	if(multipartFile.isEmpty() == false){
        		originalFileName = multipartFile.getOriginalFilename();
        		originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        		storedFileName = CommonUtils.getRandomString() + originalFileExtension;
        		file = new File(filePath + storedFileName);
        		multipartFile.transferTo(file);
        		listMap = new HashMap<String,Object>();
        		if("file2_0".equals(ftname)) //현장점검 복명서이면 6, 아니면 5
        		{	
        			listMap.put("BOARD_GB", bdId2);
        		}
        		else{
        			listMap.put("BOARD_GB", bdId);
        		}
        		listMap.put("PARENT_ID", boardIndId);        	
        		listMap.put("ORIGINAL_FILE_NAME", originalFileName);
        		listMap.put("STORED_FILE_NAME", storedFileName);
        		listMap.put("FILE_SIZE", multipartFile.getSize());
        		list.add(listMap);
        	}
        }
		return list;
	}

	
	
	public List<Map<String, Object>> parseUpdateFileInfo(Map<String, Object> map, HttpServletRequest request) throws Exception{
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
    	Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
    	
    	String filePath = null;    	
    	String file_dir_path = null;
		
		file_dir_path = CommonLib.getTimeStampStringShort();
		file_dir_path = file_dir_path.substring(0, 6);
    	
    	if(request.getServerName().equals("98.23.7.109"))  //운영서버	
    		filePath = "/usr/local/server/file/scms/" + file_dir_path + "/";
    	else 
    		filePath = "C:\\dev\\file\\scms\\" + file_dir_path + "\\";
    	    	
    	log.debug("server2===> [ "+ request.getServerName() + "]");
    	    	
    	File file = new File(filePath);
        if(file.exists() == false){
        	file.mkdirs();
        }
    	    	
    	MultipartFile multipartFile = null;
    	String originalFileName = null;
    	String originalFileExtension = null;
    	String storedFileName = null;
    	
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> listMap = null; 
        
       
        String boardIndId = (String)map.get("PARENT_ID");
        String bdId = (String)map.get("BOARD_GB");
        String bdId2 = (String)map.get("BOARD_GB2");
        String requestName = null;
        String ftname = null;  //5,6 번 구별위한 값
        String idx = null;
        
        
        while(iterator.hasNext()){
        	multipartFile = multipartHttpServletRequest.getFile(iterator.next());
        	requestName = multipartFile.getName();
        	ftname = requestName.substring(0, requestName.indexOf("_"));
        	System.out.println("update file name=[" + multipartFile.getName() + "] ftname=[" + ftname + "] bdid=[" +bdId + "] bdid2=[" + bdId2 + "]" );
        	
        	if(multipartFile.isEmpty() == false){
        		originalFileName = multipartFile.getOriginalFilename();
        		originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        		storedFileName = CommonUtils.getRandomString() + originalFileExtension;
        		
        		
        		multipartFile.transferTo(new File(filePath + storedFileName));
        		
        		listMap = new HashMap<String,Object>();
        		listMap.put("IS_NEW", "Y");
        		System.out.println("if ftname=[" + ftname + "]");
        		
        		if("file2".equals(ftname))  //현장점검 복명서이면 6, 아니면 5
        		{	
        			listMap.put("BOARD_GB", bdId2);
        			System.out.println("if board_gb=[" +bdId2 + "]");
        		}
        		else{
        			listMap.put("BOARD_GB", bdId);
        			System.out.println("else board_gb=[" +bdId + "]");
        		}
        		        		
        		listMap.put("PARENT_ID", boardIndId);
        		listMap.put("ORIGINAL_FILE_NAME", originalFileName);
        		listMap.put("STORED_FILE_NAME", storedFileName);
        		listMap.put("FILE_SIZE", multipartFile.getSize());
        		list.add(listMap);
        	}
        	else{
        		
        		if("file2".equals(ftname))  //현장점검 복명서이면 6, 아니면 5
        		{
        			idx = "FID2_"+requestName.substring(requestName.indexOf("_")+1);
        		}
        		else{
        			idx = "FID_"+requestName.substring(requestName.indexOf("_")+1);
        		}
        		
        		System.out.println("else idx=[" + idx + "]");
        		
            	if(map.containsKey(idx) == true && map.get(idx) != null){
            		listMap = new HashMap<String,Object>();
            		listMap.put("IS_NEW", "N");
            		listMap.put("FILE_ID", map.get(idx));
            		list.add(listMap);
            	}
        	}
        }
		return list;
	}
		
}
