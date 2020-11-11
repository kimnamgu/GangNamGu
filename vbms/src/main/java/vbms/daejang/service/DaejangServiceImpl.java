package vbms.daejang.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import vbms.common.util.CommonUtils;
import vbms.common.util.CommonLib;
import vbms.common.util.ExcelRead;
import vbms.common.util.ExcelReadOption;
import vbms.common.util.FileUtils;
import vbms.common.util.Nvl;
import vbms.daejang.dao.DaejangDAO;


@Service("daejangService")
public class DaejangServiceImpl implements DaejangService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Resource(name="daejangDAO")
	private DaejangDAO daejangDAO;

	
	@Override
	public List<Map<String, Object>> selectViolBuildingList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectViolBuildingList(map);
	}

	
	@Override
	public List<Map<String, Object>> selectViolBuildingUpList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectViolBuildingUpList(map);
	}

	
	@Override
	public Map<String, Object> violBuildingContent(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = daejangDAO.violBuildingContent(map);
		resultMap.put("map", tempMap);
				
		return resultMap;
	}
	
	
	
	@Override
	public void insBldMngDaejang(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		//List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		System.out.println("map=[" + map + "]");
		
		daejangDAO.insBldMngDaejang(map);	
			
	}
	
	
	@Override
	public void updateBldMngDaejang(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		
		daejangDAO.updateBldMngDaejang(map);		
	}
	
	
	@Override
	public void deleteBldMngDaejang(Map<String, Object> map,  HttpServletRequest request) throws Exception {
					
		daejangDAO.deleteBldMngDaejang(map);			
	}
	
	
	@Override
	public void deleteViolBuildingUpload(Map<String, Object> map,  HttpServletRequest request) throws Exception {
					
		daejangDAO.deleteViolBuildingUploadData(map);
		daejangDAO.deleteViolBuildingUploadFile(map);
	}
	
	
	
	@Override
	public Map<String, Object> selfDgnsBasicInfo(Map<String, Object> map) throws Exception {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		
		Map<String, Object> tempMap = daejangDAO.violBuildingContent(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	
	
	
	
		
	@Override
    public void excelUpload(Map<String, Object> map, HttpServletRequest request, File destFile ) throws Exception{
		int fid = 0;
		int len_arrdrjuso = 0;
		
		
		
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		System.out.println("file List[" + list.size() + "] === [" + list + "]");
		//for(int i=0, size=list.size(); i<size; i++){
			fid = daejangDAO.insertFile(list.get(0));
		//}			

		System.out.println("FID =[" + fid + "]");
		
		ExcelReadOption excelReadOption = new ExcelReadOption();
        excelReadOption.setFilePath(destFile.getAbsolutePath());
        excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","AA","AB","AC","AD","AE");
        excelReadOption.setStartRow(2);
        
        List<Map<String, String>>excelContent =ExcelRead.read(0, excelReadOption);      
       //System.out.println("size=[" + excelContent.size() + "]");        
        
        for(Map<String, String> article: excelContent){                 
            System.out.println("A [" + article.get("A") + "]");
            System.out.println("B [" + article.get("B") + "]");
            System.out.println("C [" + article.get("C") + "]");
            //System.out.println("F [" + article.get("F") + "]");
            
            String[] arrjuso = article.get("C").split(" ");
            String[] arrdrjuso = article.get("D").split(" ");
            String[] marrjuso = article.get("F").split(" ");            
            String[] marrdrjuso = article.get("G").split(" ");
            String[] tmpbuf= null;
            
            if(arrdrjuso.length > 4 )
            	len_arrdrjuso = 4;
    
            
            /*
            System.out.println("juso12 [" + arrjuso[2] + "]");
            System.out.println("juso13 [" + arrjuso[3] + "]");
            System.out.println("mjuso12 [" + marrjuso[2] + "]");
            System.out.println("mjuso13 [" + marrjuso[3] + "]");*/
                        
            /*System.out.println("D [" + article.get("D") + "]");
            System.out.println("E [" + article.get("E") + "]");
            System.out.println("F [" + article.get("F") + "]");
            System.out.println("G [" + article.get("G") + "]");*/
			System.out.println("==============================");
            	          
            map.put("BLD_DONG", CommonLib.defaultIfEmpty(arrjuso[2], ""));
            map.put("BLD_ZIPNO", CommonLib.defaultIfEmpty(article.get("B"), ""));
            map.put("BLD_ADDR1", CommonLib.defaultIfEmpty(arrjuso[3], ""));
            map.put("BLD_ADDR2", "");            
            map.put("BLD_ADDR_ROAD", CommonLib.defaultIfEmpty(arrdrjuso[2] + " " + arrdrjuso[3], ""));            
            map.put("FULL_BLD_ADDR", CommonLib.defaultIfEmpty(article.get("C"), ""));
            map.put("FULL_BLD_ADDR_ROAD", CommonLib.defaultIfEmpty(article.get("D"), ""));            
            map.put("MBLD_DONG", CommonLib.defaultIfEmpty(marrjuso[2], ""));
            map.put("MBLD_ZIPNO", CommonLib.defaultIfEmpty(article.get("E"), ""));
            map.put("MBLD_ADDR1", CommonLib.defaultIfEmpty(marrjuso[3], ""));
            map.put("MBLD_ADDR2", "");
            map.put("MBLD_ADDR_ROAD", CommonLib.defaultIfEmpty(marrdrjuso[2], ""));            
            map.put("MFULL_BLD_ADDR", CommonLib.defaultIfEmpty(article.get("F"), ""));
            map.put("MFULL_BLD_ADDR_ROAD", CommonLib.defaultIfEmpty(article.get("G"), ""));            
            map.put("BLD_OWNER", CommonLib.defaultIfEmpty(article.get("H"), ""));
            tmpbuf = null;
            tmpbuf = article.get("I").split(":");
            map.put("BLD_STRUCTURE", CommonLib.defaultIfEmpty(tmpbuf[0], ""));
            map.put("STRUCTURE_DETAIL", CommonLib.defaultIfEmpty(article.get("J"), ""));
            map.put("VIOL_AREA", CommonLib.defaultIfEmpty(article.get("K"), ""));
            tmpbuf = null;
            tmpbuf = article.get("L").split(":");
            map.put("PURPOSE", CommonLib.defaultIfEmpty(tmpbuf[0], ""));
            map.put("PURPOSE_DETAIL", CommonLib.defaultIfEmpty(article.get("M"), ""));
            map.put("LOCATION", CommonLib.defaultIfEmpty(article.get("N"), ""));
            map.put("EXPOSURE_DETAILS", CommonLib.defaultIfEmpty(article.get("O"), ""));
            map.put("PRE_CORRTN_ORDER", CommonLib.defaultIfEmpty(article.get("P"), ""));
            map.put("CORRTN_ORDER", CommonLib.defaultIfEmpty(article.get("Q"), ""));
            map.put("VIOL_BUILDING_REGDATE", CommonLib.defaultIfEmpty(article.get("R"), ""));            
            map.put("OPINION_STATE_LIMIT", CommonLib.defaultIfEmpty(article.get("S"), ""));
            map.put("EXEC_IMPOSE_DATE", CommonLib.defaultIfEmpty(article.get("T"), ""));
            map.put("EXEC_IMPOSE_AMT", CommonLib.defaultIfEmpty(article.get("U"), ""));
            map.put("TAX_DEP_NOTEDATE", CommonLib.defaultIfEmpty(article.get("V"), ""));            
            tmpbuf = null;
            tmpbuf = article.get("W").split(":");
            map.put("STATE", CommonLib.defaultIfEmpty(tmpbuf[0], ""));
            map.put("STATE_DETAIL", CommonLib.defaultIfEmpty(article.get("X"), ""));
            map.put("PERFORM_USERID", CommonLib.defaultIfEmpty(article.get("Y"), ""));
            map.put("PERFORM_PERSON", CommonLib.defaultIfEmpty(article.get("Z"), ""));
            map.put("PERFORM_USERHNO", CommonLib.defaultIfEmpty(article.get("AA"), ""));           
            map.put("BIGO", CommonLib.defaultIfEmpty(article.get("AB"), ""));
            map.put("DISPLAY_YN", "0");
            map.put("UPLOAD_FLAG", fid);
                       
            //log.debug("map impl = " + "[" + map.get("INS_ID") + "]");          
           	daejangDAO.insBldMngDaejang(map);
        }
	}


	@Override
	public void insertDgnsSubj(Map<String, Object> map, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
