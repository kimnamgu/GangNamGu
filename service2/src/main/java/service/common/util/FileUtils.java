package service.common.util;

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
	
	//private static final String filePath = "C:\\dev\\file\\";
	
	public List<Map<String,Object>> parseInsertFileInfo(Map<String,Object> map, HttpServletRequest request) throws Exception{
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
    	Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
    	
    	System.out.println("folderName 확인 : " + map.get("folderName"));
    	
    	String filePath = "C:\\dev\\file\\" + map.get("folderName") + "\\";
    	
    	System.out.println("filePath 확인 : " + filePath);
    	
    	if(request.getServerName().equals("98.23.7.109") || request.getServerName().equals("98.23.7.110"))  //�����	
    			filePath = "/usr/local/server/file/service/" + map.get("folderName") + "/";
    	
    	log.debug("server1===> [ "+ request.getServerName() + "]");
    	
    	
    	MultipartFile multipartFile = null;
    	String originalFileName = null;
    	String originalFileNm = null;
    	String originalFileExtension = null;
    	String storedFileName = null;
    	
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> listMap = null; 
        
        String boardIndId = (String)map.get("ID");
        //String conId = (String)map.get("CON_ID");
        String bdId = (String)map.get("BOARD_ID");
        
        File file = new File(filePath);
        if(file.exists() == false){
        	file.mkdirs();
        }
        
        while(iterator.hasNext()){
        	multipartFile = multipartHttpServletRequest.getFile(iterator.next());
        	if(multipartFile.isEmpty() == false){
        		originalFileName = multipartFile.getOriginalFilename();
        		
        		
        		
        		originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        		originalFileNm = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        		//log.debug("file originalFileNm===> [ "+ originalFileNm + "]");
        		//storedFileName = CommonUtils.getRandomString() + originalFileExtension;
        		storedFileName = originalFileName;
        		
        		file = new File(filePath + storedFileName);
        		multipartFile.transferTo(file);
        		
        		listMap = new HashMap<String,Object>();
        		listMap.put("BOARD_ID", bdId);
        		/*if("3".equals(bdId))
        			listMap.put("BD_LST_ID", conId);	
        		else*/
        		listMap.put("BD_LST_ID", boardIndId);
        	
        		listMap.put("ORIGINAL_FILE_NAME", originalFileName);
        		listMap.put("ORIGINAL_FILE_NM", originalFileNm);
        		listMap.put("STORED_FILE_NAME", originalFileName);
        		listMap.put("FILE_SIZE", multipartFile.getSize());
        		list.add(listMap);
        	}
        }
		return list;
	}

	public List<Map<String, Object>> parseUpdateFileInfo(Map<String, Object> map, HttpServletRequest request) throws Exception{
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
    	Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
    	
    	String filePath = "C:\\dev\\file\\";
    	
    	if(request.getServerName().equals("98.23.7.109") || request.getServerName().equals("98.23.7.110"))  //�����	
    			filePath = "/usr/local/server/file/service/";
    	    	
    	log.debug("server2===> [ "+ request.getServerName() + "]");
    	
    	MultipartFile multipartFile = null;
    	String originalFileName = null;
    	String originalFileExtension = null;
    	String storedFileName = null;
    	
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> listMap = null; 
        
       
        String boardIndId = (String)map.get("BD_LST_ID");
        String bdId = (String)map.get("BOARD_ID");
        String requestName = null;
        String idx = null;
        
        
        while(iterator.hasNext()){
        	multipartFile = multipartHttpServletRequest.getFile(iterator.next());
        	if(multipartFile.isEmpty() == false){
        		originalFileName = multipartFile.getOriginalFilename();
        		originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        		storedFileName = CommonUtils.getRandomString() + originalFileExtension;
        		
        		multipartFile.transferTo(new File(filePath + storedFileName));
        		
        		listMap = new HashMap<String,Object>();
        		listMap.put("IS_NEW", "Y");
        		listMap.put("BOARD_ID", bdId);
        		listMap.put("BD_LST_ID", boardIndId);
        		listMap.put("ORIGINAL_FILE_NAME", originalFileName);
        		listMap.put("STORED_FILE_NAME", storedFileName);
        		listMap.put("FILE_SIZE", multipartFile.getSize());
        		list.add(listMap);
        	}
        	else{
        		requestName = multipartFile.getName();
            	idx = "ID_"+requestName.substring(requestName.indexOf("_")+1);
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
