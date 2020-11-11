package manpower.manage.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import manpower.common.util.ExcelRead;
import manpower.common.util.ExcelReadOption;
import manpower.common.util.FileUtils;
import manpower.manage.dao.ManageDAO;


@Service("manageService")
public class ManageServiceImpl implements ManageService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Resource(name="manageDAO")
	private ManageDAO manageDAO;

	/*공지사항*/
	@Override
	public List<Map<String, Object>> selectNoticeList(Map<String, Object> map) throws Exception {
		return manageDAO.selectNoticeList(map);
	}
	
	@Override
	public void updateDelNoticeAllList(Map<String, Object> map) throws Exception {
		manageDAO.updateDelNoticeAllList(map);
	}
	
	/*은행코드관리*/
	@Override
	public List<Map<String, Object>> selectBankList(Map<String, Object> map) throws Exception {
		return manageDAO.selectBankList(map);
	}
	
	@Override
	public void updateDelBankAllList(Map<String, Object> map) throws Exception {
		manageDAO.updateDelBankAllList(map);
	}
	
	@Override
	public Map<String, Object> bankExcelUp(File destFile,String ins_id){
		System.out.println("bankExcelUp service 탐");
		System.out.println("ins_id 확인 : " + ins_id);
		
	    	ExcelReadOption excelReadOption = new ExcelReadOption();

	        excelReadOption.setFilePath(destFile.getAbsolutePath());
 
	        excelReadOption.setOutputColumns("A","B");


	        excelReadOption.setStartRow(2);

	        List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);

	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("excelContent", excelContent);

	        int rtn=1;
	        Map<String, Object> resultMap = new HashMap<String, Object>();
	        
	        String name="";
	        String err_column="";
	        
	        try {

	            for (Map<String, String> map : excelContent) {
	            	map.put("INS_ID", ins_id);
	            	System.out.println("map 확인 : " + map.toString());
	            	name = map.get("B");
	            	
	            	manageDAO.insertBankManageExcel(map);
	            }
	            
		    } catch (Exception e) {
		    	rtn = 0;
		    	resultMap.put("exception", e.getMessage());
		    	
		    	String[] array = e.getMessage().split("###");
		    	
		    	if(array[1].contains("CODE")) {err_column="코드";}
		    	if(array[1].contains("BANK_NAME")) {err_column="은행명";}
		    	
		    	resultMap.put("err_msg", "엑셀 업로드중 " + name + " 의 "+ err_column + " 자료에서 에러가 발생하였습니다.");
		    	resultMap.put("err_msg_dtl", array[1]);
		    	

		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
	        
	        resultMap.put("rtn", rtn);
	        
	        return resultMap;
	  }
	
}
