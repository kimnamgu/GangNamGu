package manpower.common.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import manpower.common.dao.CommonDAO;
import manpower.common.util.ExcelRead;
import manpower.common.util.ExcelReadOption;
import manpower.common.util.Nvl;

@Service("commonService")
public class CommonServiceImpl implements CommonService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="commonDAO")
	private CommonDAO commonDAO;

	@Override
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception {
		return commonDAO.selectFileInfo(map);
	}
	
	
	@Override
	public void updateDownCount(Map<String, Object> map) throws Exception {
		commonDAO.updateDownCount(map);
	}
	
	
	@Override
	public void updateApplyCount(Map<String, Object> map) throws Exception {
		commonDAO.updateApplyCount(map);
	}
	
	
	@Override
	public Map<String, Object> loginProcessId(Map<String, Object> map, HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = commonDAO.loginProcessId(map);
		resultMap.put("map_i", tempMap);
		
		return resultMap;
	}
	
	
	@Override
	public Map<String, Object> loginProcessPw(Map<String, Object> map, HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = commonDAO.loginProcessPw(map);
		resultMap.put("map_p", tempMap);
		
		return resultMap;
	}
	
	@Override
	public Map<String, Object> ssoLogin(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = commonDAO.ssoLogin(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public void insertUserinfo(Map<String, Object> map, HttpServletRequest request) throws Exception{
		commonDAO.insertUserinfo(map);
		
	}
	
	@Override
	public void updateUserinfo(Map<String, Object> map) throws Exception{
		commonDAO.updateUserinfo(map);
		
	}
	
	@Override
	public void updateEmployeeTransData(Map<String, Object> map) throws Exception{
		commonDAO.updateEmployeeTransData(map);
		
	}
	
	@Override
	public void insertDeptData(Map<String, Object> map) throws Exception{
		commonDAO.insertDeptData(map);
		
	}
	
	@Override
	public void deleteDeptData(Map<String, Object> map) throws Exception{
		commonDAO.deleteDeptData(map);
		
	}
	
	
	
	@Override
	public List<Map<String, Object>> selectEmployeeTransIdList() throws Exception {
		return commonDAO.selectEmployeeTransIdList();
	}
	
	
	@Override
	public List<Map<String, Object>> selectDeptList(Map<String, Object> map) throws Exception {
		return commonDAO.selectDeptList(map);
	}
	
	@Override
	public List<Map<String, Object>> structList(Map<String, Object> map) throws Exception {
		return commonDAO.structList(map);
	}
	
	@Override
	public List<Map<String, Object>> purposeList(Map<String, Object> map) throws Exception {
		return commonDAO.purposeList(map);
	}
	
	
	@Override
	public List<Map<String, Object>> stateList(Map<String, Object> map) throws Exception {
		return commonDAO.stateList(map);
	}
	
		
	@Override
	public List<Map<String, Object>> dongList(Map<String, Object> map) throws Exception {
		return commonDAO.dongList(map);
	}
	
	
	@Override
	public List<Map<String, Object>> popJikWonList(Map<String, Object> map) throws Exception {
		return commonDAO.popJikWonList(map);
	}
	
	
	
	@Override
	public List<Map<String, Object>> selectIdApproveList(Map<String, Object> map) throws Exception {
		return commonDAO.selectIdApproveList(map);
	}
	
	@Override
	public void updateIdApprove(Map<String, Object> map) throws Exception{
		commonDAO.updateIdApprove(map);
		
	}
	
	@Override
	public Map<String, Object> idApproveListDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = commonDAO.idApproveListDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public void updateIdApproveList(Map<String, Object> map) throws Exception{
		commonDAO.updateIdApproveList(map);
		
	}
	
	
	@Override
	public void deleteIdApproveList(Map<String, Object> map) throws Exception{
		commonDAO.deleteIdApproveList(map);
	}
	
	
	
	
	@Override
	public void deleteScmsUserData(Map<String, Object> map) throws Exception{
		commonDAO.deleteScmsUserData(map);
	}
	
	
	@Override
	public List<Map<String, Object>> selectSsoUserList(Map<String, Object> map) throws Exception {
		return commonDAO.selectSsoUserList(map);
	}
	
	
	
	@Override
	public List<Map<String, Object>> yearList(Map<String, Object> map) throws Exception {
		return commonDAO.yearList(map);
	}
	
	@Override
	public List<Map<String, Object>> dutyMonthList(Map<String, Object> map) throws Exception {
		return commonDAO.dutyMonthList(map);
	}
	
	
	@Override
	public List<Map<String, Object>> deptList(Map<String, Object> map) throws Exception {
		return commonDAO.deptList(map);
	}
	
	@Override
	public void excelUpload(File destFile,String ins_id,String ins_dept){
	    ExcelReadOption excelReadOption = new ExcelReadOption();

	    	//파일경로 추가
	        excelReadOption.setFilePath(destFile.getAbsolutePath());
	        //추출할 컬럼 명 추가, A는 맨앞의 순번이라서 쿼리에서는 뺌 
	        excelReadOption.setOutputColumns("A","B","C","D","E","F");

	        //시작 행
	        excelReadOption.setStartRow(2);

	        List<Map<String, String>>excelContent = ExcelRead.read(excelReadOption);

	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("excelContent", excelContent);

	        try {

	            for (Map<String, String> map : excelContent) {
	            	map.put("INS_ID", ins_id);
	            	map.put("INS_DEPT", ins_dept);
	            	
	            	commonDAO.insertExcel(map);
	            }

		    } catch (Exception e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
	  }
	
	
	 /**
     * 과일 리스트를 간단한 엑셀 워크북 객체로 생성
     * @param list
     * @return 생성된 워크북
     */
    public SXSSFWorkbook makeSimpleExcelWorkbook(List<Map<String, Object>> resultlist) {
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        
        // 시트 생성
        SXSSFSheet sheet = workbook.createSheet("공사발주현황");
        //시트 열 너비 설정
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(0, 3000);
        // 헤더 행 생
        Row headerRow = sheet.createRow(0);
        // 해당 행의 첫번째 열 셀 생성
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("연번");
        // 해당 행의 두번째 열 셀 생성
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("연도");
        // 해당 행의 세번째 열 셀 생성
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("공사명");
        // 해당 행의 네번째 열 셀 생성
        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("공사위치");
        
        headerCell = headerRow.createCell(5);
        headerCell.setCellValue("주요 공사");
        
        headerCell = headerRow.createCell(6);
        headerCell.setCellValue("공사비(천원)");
        
        headerCell = headerRow.createCell(7);
        headerCell.setCellValue("견적서 파일");
        
        // 과일표 내용 행 및 셀 생성
        Row bodyRow = null;
        Cell bodyCell = null;
        
        
        for(int i=0; i<resultlist.size(); i++) {
        	Map<String, Object> result = resultlist.get(i);
        	
            // 행 생성
            bodyRow = sheet.createRow(i+1);
            
            // 데이터 번호 표시
            bodyCell = bodyRow.createCell(0);
            bodyCell.setCellValue(Nvl.nvlStr(result.get("SEQ")));
            // 데이터 이름 표시
            bodyCell = bodyRow.createCell(1);
            bodyCell.setCellValue(Nvl.nvlStr(result.get("YEAR")));
            // 데이터 가격 표시
            bodyCell = bodyRow.createCell(2);
            bodyCell.setCellValue(Nvl.nvlStr(result.get("CONSTRUCT_NAME")));
            // 데이터 수량 표시
            bodyCell = bodyRow.createCell(4);
            bodyCell.setCellValue(Nvl.nvlStr(result.get("CONSTRUCT_LOCATION")));
            // 데이터 수량 표시
            bodyCell = bodyRow.createCell(5);
            bodyCell.setCellValue(Nvl.nvlStr(result.get("IMPORTANT_CONSTRUCT")));
            // 데이터 수량 표시
            bodyCell = bodyRow.createCell(6);
            bodyCell.setCellValue(Nvl.nvlStr(result.get("CONSTRUCT_COST")));
            
            bodyCell = bodyRow.createCell(7);
            bodyCell.setCellValue(Nvl.nvlStr(result.get("ORIGINAL_FILE_NAME")));
        }
        return workbook;
    }
    
    /**
     * 생성한 엑셀 워크북을 컨트롤레에서 받게해줄 메소
     * @param list
     * @return
     */
    public SXSSFWorkbook excelFileDownloadProcess(List<Map<String, Object>> resultlist) {
        return this.makeSimpleExcelWorkbook(resultlist);
    }

}

