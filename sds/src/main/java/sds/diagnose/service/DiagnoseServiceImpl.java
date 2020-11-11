package sds.diagnose.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import sds.common.util.CommonUtils;
import sds.common.util.CommonLib;
import sds.common.util.ExcelRead;
import sds.common.util.ExcelReadOption;
import sds.common.util.FileUtils;
import sds.common.util.Nvl;
import sds.diagnose.dao.DiagnoseDAO;


@Service("diagnoseService")
public class DiagnoseServiceImpl implements DiagnoseService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Resource(name="diagnoseDAO")
	private DiagnoseDAO diagnoseDAO;

	
	@Override
	public List<Map<String, Object>> selectSelfDgnsMastList(Map<String, Object> map) throws Exception {
		return diagnoseDAO.selectSelfDgnsMastList(map);
	}

	
	@Override
	public Map<String, Object> selfDgnsContentView(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = diagnoseDAO.selfDgnsContentView(map);
		resultMap.put("map", tempMap);
		
		
		List<Map<String,Object>> lists = diagnoseDAO.selfDgnsSubjList(map);
		resultMap.put("lists", lists);
		
		List<Map<String,Object>> listq = diagnoseDAO.selfDgnsQstList(map);
		resultMap.put("listq", listq);
		
		List<Map<String,Object>> liste = diagnoseDAO.selfDgnsExamList(map);
		resultMap.put("liste", liste);
		
		List<Map<String,Object>> listq_cnt = diagnoseDAO.selfDgnsQstListCnt(map);
		resultMap.put("listq_cnt", listq_cnt);
		
		return resultMap;
	}
	
	
	
	@Override
	public void insertDgnsMast(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		//System.out.println("map List=[" + list + "]");
		
		if(list.size() == 3){
			map.put("ORIGINAL_FILE_NAME", list.get(0).get("ORIGINAL_FILE_NAME"));
			map.put("STORED_FILE_NAME", list.get(0).get("STORED_FILE_NAME"));
			map.put("ORIGINAL_IMG_NAME", list.get(2).get("ORIGINAL_FILE_NAME"));
			map.put("IMG_FILE_NAME", list.get(2).get("STORED_FILE_NAME"));
		}
		else if(list.size() == 2){
			map.put("ORIGINAL_FILE_NAME", list.get(0).get("ORIGINAL_FILE_NAME"));
			map.put("STORED_FILE_NAME", list.get(0).get("STORED_FILE_NAME"));
			map.put("ORIGINAL_IMG_NAME", "");
			map.put("IMG_FILE_NAME", "");
		}
		
		diagnoseDAO.insertDgnsMast(map);	
		//System.out.println("map id =[" + map.get("DGMS_MID") + "]");		
	}
	
	
	@Override
	public void updateDgnsMast(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		
		if(list.size() > 0){
			if(list.size() == 3){
				map.put("ORIGINAL_FILE_NAME", list.get(0).get("ORIGINAL_FILE_NAME"));
				map.put("STORED_FILE_NAME", list.get(0).get("STORED_FILE_NAME"));
				map.put("ORIGINAL_IMG_NAME", list.get(2).get("ORIGINAL_FILE_NAME"));
				map.put("IMG_FILE_NAME", list.get(2).get("STORED_FILE_NAME"));
				
				diagnoseDAO.updateDgnsSubj(map);
				diagnoseDAO.updateDgnsQuest(map);
				diagnoseDAO.updateDgnsExamp(map);							
				
			}
			else if(list.size() == 2){
				
				if( request.getParameter("SFILE").isEmpty() == true)
				{
					map.put("ORIGINAL_IMG_NAME", list.get(1).get("ORIGINAL_FILE_NAME"));
					map.put("IMG_FILE_NAME", list.get(1).get("STORED_FILE_NAME"));
					
					diagnoseDAO.updateDgnsSubj(map);
					diagnoseDAO.updateDgnsQuest(map);
					diagnoseDAO.updateDgnsExamp(map);
				}
				else if( request.getParameter("excelFile").isEmpty() == true){
					map.put("ORIGINAL_FILE_NAME", list.get(0).get("ORIGINAL_FILE_NAME"));
					map.put("STORED_FILE_NAME", list.get(0).get("STORED_FILE_NAME"));
					map.put("ORIGINAL_IMG_NAME", list.get(1).get("ORIGINAL_FILE_NAME"));
					map.put("IMG_FILE_NAME", list.get(1).get("STORED_FILE_NAME"));
					
				}
				else { //3번째칸 빈칸일때
					map.put("ORIGINAL_FILE_NAME", list.get(0).get("ORIGINAL_FILE_NAME"));
					map.put("STORED_FILE_NAME", list.get(0).get("STORED_FILE_NAME"));
					
					diagnoseDAO.updateDgnsSubj(map);
					diagnoseDAO.updateDgnsQuest(map);
					diagnoseDAO.updateDgnsExamp(map);
				}
								
			}
			else//사이즈 1
			{
				if( request.getParameter("SFILE").isEmpty() == false){
					map.put("ORIGINAL_FILE_NAME", list.get(0).get("ORIGINAL_FILE_NAME"));
					map.put("STORED_FILE_NAME", list.get(0).get("STORED_FILE_NAME"));					
				}
				else if( request.getParameter("imgFile").isEmpty() == false){
					map.put("ORIGINAL_IMG_NAME", list.get(0).get("ORIGINAL_FILE_NAME"));
					map.put("IMG_FILE_NAME", list.get(0).get("STORED_FILE_NAME"));	
				}
				else
				{
					diagnoseDAO.updateDgnsSubj(map);
					diagnoseDAO.updateDgnsQuest(map);
					diagnoseDAO.updateDgnsExamp(map);
				}
			}
			
		}
		
		diagnoseDAO.updateDgnsMast(map);		
	}
	
	
	@Override
	public void deleteDgnsMast(Map<String, Object> map,  HttpServletRequest request) throws Exception {
					
		diagnoseDAO.deleteDgnsMast(map);
		diagnoseDAO.updateDgnsSubj(map);
		diagnoseDAO.updateDgnsQuest(map);
		diagnoseDAO.updateDgnsExamp(map);			
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void insertDgnsSubj(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		diagnoseDAO.insertDgnsSubj(map);
		
	}
	
	
	
	@Override
	public Map<String, Object> selfDgnsBasicInfo(Map<String, Object> map) throws Exception {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		
		Map<String, Object> tempMap = diagnoseDAO.selfDgnsContentView(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	
	
	
	
		
	@Override
    public void excelUpload(Map<String, Object> map, HttpServletRequest request, File destFile, String flag) throws Exception{
		
		String word1 = null;
		
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		System.out.println("file List[" + list.size() + "] === [" + list + "]");
		
		if(list.size() > 0)
		{
		
			if("I".equals(flag))
			{		//최초 입력시	
				
				if(list.size() == 3){
					map.put("ORIGINAL_FILE_NAME", list.get(0).get("ORIGINAL_FILE_NAME"));
					map.put("STORED_FILE_NAME", list.get(0).get("STORED_FILE_NAME"));
					map.put("ORIGINAL_IMG_NAME", list.get(2).get("ORIGINAL_FILE_NAME"));
					map.put("IMG_FILE_NAME", list.get(2).get("STORED_FILE_NAME"));
				}
				else if(list.size() == 2){
					map.put("ORIGINAL_FILE_NAME", list.get(0).get("ORIGINAL_FILE_NAME"));
					map.put("STORED_FILE_NAME", list.get(0).get("STORED_FILE_NAME"));
					map.put("ORIGINAL_IMG_NAME", "");
					map.put("IMG_FILE_NAME", "");
				}
				
				diagnoseDAO.insertDgnsMast(map);
			}		
			else if("U".equals(flag)){  //수정시
			
				if(list.size() == 3){
					map.put("ORIGINAL_FILE_NAME", list.get(0).get("ORIGINAL_FILE_NAME"));
					map.put("STORED_FILE_NAME", list.get(0).get("STORED_FILE_NAME"));
					map.put("ORIGINAL_IMG_NAME", list.get(2).get("ORIGINAL_FILE_NAME"));
					map.put("IMG_FILE_NAME", list.get(2).get("STORED_FILE_NAME"));
					
					diagnoseDAO.updateDgnsSubj(map);
					diagnoseDAO.updateDgnsQuest(map);
					diagnoseDAO.updateDgnsExamp(map);												
				}
				else if(list.size() == 2){
					
					if( request.getParameter("SFILE").isEmpty() == true)
					{
						map.put("ORIGINAL_IMG_NAME", list.get(1).get("ORIGINAL_FILE_NAME"));
						map.put("IMG_FILE_NAME", list.get(1).get("STORED_FILE_NAME"));
						
						diagnoseDAO.updateDgnsSubj(map);
						diagnoseDAO.updateDgnsQuest(map);
						diagnoseDAO.updateDgnsExamp(map);
					}
					else if( request.getParameter("excelFile").isEmpty() == true){
						map.put("ORIGINAL_FILE_NAME", list.get(0).get("ORIGINAL_FILE_NAME"));
						map.put("STORED_FILE_NAME", list.get(0).get("STORED_FILE_NAME"));
						map.put("ORIGINAL_IMG_NAME", list.get(1).get("ORIGINAL_FILE_NAME"));
						map.put("IMG_FILE_NAME", list.get(1).get("STORED_FILE_NAME"));
						
					}
					else { //3번째칸 빈칸일때
						map.put("ORIGINAL_FILE_NAME", list.get(0).get("ORIGINAL_FILE_NAME"));
						map.put("STORED_FILE_NAME", list.get(0).get("STORED_FILE_NAME"));
						
						diagnoseDAO.updateDgnsSubj(map);
						diagnoseDAO.updateDgnsQuest(map);
						diagnoseDAO.updateDgnsExamp(map);
					}
									
				}
				else//사이즈 1
				{
					if( request.getParameter("SFILE").isEmpty() == false){
						map.put("ORIGINAL_FILE_NAME", list.get(0).get("ORIGINAL_FILE_NAME"));
						map.put("STORED_FILE_NAME", list.get(0).get("STORED_FILE_NAME"));					
					}
					else if( request.getParameter("imgFile").isEmpty() == false){
						map.put("ORIGINAL_IMG_NAME", list.get(0).get("ORIGINAL_FILE_NAME"));
						map.put("IMG_FILE_NAME", list.get(0).get("STORED_FILE_NAME"));	
					}
					else
					{
						diagnoseDAO.updateDgnsSubj(map);
						diagnoseDAO.updateDgnsQuest(map);
						diagnoseDAO.updateDgnsExamp(map);
					}
				}
					
				diagnoseDAO.updateDgnsMast(map);			
			}
			
		}
		
		
		
		ExcelReadOption excelReadOption = new ExcelReadOption();
        excelReadOption.setFilePath(destFile.getAbsolutePath());
        excelReadOption.setOutputColumns("A","B","C");
        excelReadOption.setStartRow(2);
        
        List<Map<String, String>>excelContent =ExcelRead.read(0, excelReadOption);      
       //System.out.println("size=[" + excelContent.size() + "]");        
        
        for(Map<String, String> article: excelContent){                 
            /*System.out.println("A [" + article.get("A") + "]");
            System.out.println("B [" + article.get("B") + "]");
            System.out.println("C [" + article.get("C") + "]");           
            System.out.println("==============================");*/
            
            map.put("SBJ_SEQ", article.get("A"));
            map.put("SUBJECT", CommonLib.defaultIfEmpty(article.get("B"), ""));
            map.put("BIGOS", CommonLib.defaultIfEmpty(article.get("C"), ""));            
            //log.debug("map impl = " + "[" + map.get("INS_ID") + "]");
          
            if( !CommonUtils.empty(article.get("A")))
            {            	
            	diagnoseDAO.insertDgnsSubj(map);
            }
            else{
            	
            	break;
            }
        }
        
               
        excelReadOption.setOutputColumns("A","B","C","D","E");        
        List<Map<String, String>>excelContent1 =ExcelRead.read(1, excelReadOption);        
        //System.out.println("size1=[" + excelContent1.size() + "]");
                
        for(Map<String, String> article: excelContent1){                 
          
        	map.put("SBJ_SEQ", article.get("A"));
            map.put("QST_SEQ", CommonLib.defaultIfEmpty(article.get("B"), ""));
            map.put("CONT_HEAD", CommonLib.defaultIfEmpty(article.get("C"), ""));
            map.put("CONT", CommonLib.defaultIfEmpty(article.get("D"), ""));
            map.put("BIGOQ", CommonLib.defaultIfEmpty(article.get("E"), ""));            
                    
            if( !CommonUtils.empty(article.get("A")))
            {            	
            	diagnoseDAO.insertDgnsQuest(map);
            }
            else{
            	
            	break;
            }
           
        }

        
        excelReadOption.setOutputColumns("A","B","C","D","E","F");        
        List<Map<String, String>>excelContent2 =ExcelRead.read(2, excelReadOption);        
        //System.out.println("size2=[" + excelContent2.size() + "]");
                
        for(Map<String, String> article: excelContent2){                 
        	
            map.put("SBJ_SEQ", article.get("A"));
            map.put("QST_SEQ", CommonLib.defaultIfEmpty(article.get("B"), ""));
            map.put("EXP_SEQ", CommonLib.defaultIfEmpty(article.get("C"), ""));
            map.put("POINT", CommonLib.defaultIfEmpty(article.get("D"), ""));
            map.put("CONT", CommonLib.defaultIfEmpty(article.get("E"), ""));
            map.put("BIGOE", CommonLib.defaultIfEmpty(article.get("F"), ""));
            
            if( !CommonUtils.empty(article.get("A")))
            {            	
            	diagnoseDAO.insertDgnsExamp(map);
            }
            else{
            	
            	break;
            }
           
        }
        
	
	}
	
}
