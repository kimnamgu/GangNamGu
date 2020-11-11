package ffems.daejang.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ffems.common.util.FileUtils;
import ffems.daejang.dao.DaejangDAO;
import ffems.common.util.CommonLib;
import ffems.common.util.CommonUtils;
import ffems.common.util.ExcelRead;
import ffems.common.util.ExcelReadOption;

@Service("daejangService")
public class DaejangServiceImpl implements DaejangService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Resource(name="daejangDAO")
	private DaejangDAO daejangDAO;
	
	@Override
	public List<Map<String, Object>> selectFfemsDataList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectFfemsDataList(map);
	}

	
	@Override
	public Map<String, Object> ffemsDataDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = daejangDAO.ffemsDataDetail(map);
		resultMap.put("map", tempMap);
		
		List<Map<String,Object>> list = daejangDAO.selectFastFinanceList(map);
		resultMap.put("list", list);
		
		return resultMap;
	}
	
	
	
	@Transactional
	@Override
	public int excelUpload(Map<String, Object> map, HttpServletRequest request, File destFile ) throws Exception{
		int fid = 0;
		int len_arrdrjuso = 0;
		int rtn = 0;
		int j = 0;
	
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		System.out.println("file List[" + list.size() + "] === [" + list + "]");
		for(int i=0, size=list.size(); i<size; i++){
			fid = daejangDAO.insertFile(list.get(0));
		}			

		System.out.println("FID =[" + fid + "]");
		
		ExcelReadOption excelReadOption = new ExcelReadOption();
        excelReadOption.setFilePath(destFile.getAbsolutePath());
        excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H","I","J");
        excelReadOption.setStartRow(1);
        
        List<Map<String, String>>excelContent =ExcelRead.read(0, excelReadOption);      
        System.out.println("size=[" + excelContent.size() + "]");        
        
        for(Map<String, String> article: excelContent){                 
            System.out.println("A [" + article.get("A") + "]");
            System.out.println("B [" + article.get("B") + "]");
            System.out.println("C [" + article.get("C") + "]");            
            System.out.println("D [" + article.get("D") + "]");
            System.out.println("E [" + article.get("E") + "]");
            System.out.println("F [" + article.get("F") + "]");
            System.out.println("G [" + article.get("G") + "]");
            System.out.println("H [" + article.get("H") + "]");
            
			System.out.println("==============================");
            	          
           
			map.put("UPLOAD_FLAG", fid);           
            map.put("SAUP_DEPT_NM", CommonLib.defaultIfEmpty(article.get("A"), ""));
            map.put("IN_AMT1", CommonLib.defaultIfEmpty(article.get("B"), ""));
            map.put("IN_AMT2", CommonLib.defaultIfEmpty(article.get("C"), ""));
            map.put("IN_AMT3", CommonLib.defaultIfEmpty(article.get("D"), ""));
            map.put("IN_PER1", CommonLib.defaultIfEmpty(article.get("E"), ""));
            map.put("IN_AMT4", CommonLib.defaultIfEmpty(article.get("F"), ""));
            map.put("IN_AMT5", CommonLib.defaultIfEmpty(article.get("G"), ""));
            map.put("IN_PER2", CommonLib.defaultIfEmpty(article.get("H"), ""));
            map.put("BIGO", "");            
            j++;
            log.debug("j = " + "[" + j + "]");  
            if(j > 4 && j < 56){
            //log.debug("map impl = " + "[" + map.get("INS_ID") + "]");          
            	rtn = daejangDAO.insertFinanceData(map);
            }
        }
        return rtn;
	}


	@Override
	public void insertMainSaup(Map<String, Object> map, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
}
