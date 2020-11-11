package sds.common.util;

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

import sds.common.util.CommonUtils;

@Component("fileUtils")
public class FileUtils {
	Logger log = Logger.getLogger(this.getClass());
	
	//private static final String filePath = "C:\\dev\\file\\";
	
	public List<Map<String,Object>> parseInsertFileInfo(Map<String,Object> map, HttpServletRequest request) throws Exception{
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
    	Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
    	String filePath = "C:\\dev\\file\\";
    	int k = 1;
    	
    	if(request.getServerName().equals("98.23.7.109") || request.getServerName().equals("98.23.7.110"))  //운영서버	
    			filePath = "/usr/local/server/file/sds/";
    	
    	log.debug("server1===> [ "+ request.getServerName() + "]");
    	
    	
    	MultipartFile multipartFile = null;
    	String originalFileName = null;
    	String originalFileExtension = null;
    	String storedFileName = null;
    	
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> listMap = null; 
        
        String boardIndId = (String)map.get("ID");
        String bdId = (String)map.get("BOARD_ID");
        log.debug("check1!!!!!!!!!!!!!!");
        
        File file = new File(filePath);
        if(file.exists() == false){
        	file.mkdirs();
        }
        
        log.debug("check2!!!!!!!!!!!!!!");
        
        while(iterator.hasNext()){
        	multipartFile = multipartHttpServletRequest.getFile(iterator.next());
        	if(multipartFile.isEmpty() == false){
        		originalFileName = multipartFile.getOriginalFilename();
        		originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        		storedFileName = CommonUtils.getRandomString() + originalFileExtension;
        		
        		log.debug("check3!!!!!!!!!!!!!!");
        		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@img file[" + k + "] =[" + request.getParameter("imgFile").isEmpty()  + "]");
        		log.debug("check3----1!!!!!!!!!!!!!!");
        		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@multipartFile[" + k + "] =[" + multipartFile.getContentType()  + "][" + multipartFile.getName() + "]");
        		//if(k == 3) //3번째 첨부파일 (하단 이미지) 
        		log.debug("check4!!!!!!!!!!!!!!");
        		
        		if( "imgFile".equals(multipartFile.getName()) )	
        		{	
        			if(request.getServerName().equals("98.23.7.109") || request.getServerName().equals("98.23.7.110"))
        			{	
        				filePath = "/usr/local/server/file/sds/img/";
        			}
        			else{
        				filePath = "C:\\dev\\file\\img\\";
        			}
        			        			
        		}
        		
        		file = new File(filePath + storedFileName);
        		
        		multipartFile.transferTo(file);
        		
        		listMap = new HashMap<String,Object>();
        		listMap.put("BOARD_ID", bdId);        		
        		listMap.put("BD_LST_ID", boardIndId);
        	
        		listMap.put("ORIGINAL_FILE_NAME", originalFileName);
        		listMap.put("STORED_FILE_NAME", storedFileName);
        		listMap.put("FILE_SIZE", multipartFile.getSize());
        		list.add(listMap);
        		k++;
        	}
        }
		return list;
	}

	public List<Map<String, Object>> parseUpdateFileInfo(Map<String, Object> map, HttpServletRequest request) throws Exception{
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
    	Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
    	
    	String filePath = "C:\\dev\\file\\";
    	
    	if(request.getServerName().equals("98.23.7.109") || request.getServerName().equals("98.23.7.110"))  //�����	
    			filePath = "/usr/local/server/file/sds/";
    	    	
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
