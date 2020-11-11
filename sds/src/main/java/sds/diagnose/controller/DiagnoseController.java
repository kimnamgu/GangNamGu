package sds.diagnose.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


import sds.common.common.CommandMap;
import sds.common.util.Nvl;
import sds.diagnose.service.DiagnoseService;

@Controller
public class DiagnoseController {
	Logger log = Logger.getLogger(this.getClass());
	
	
	@Resource(name="diagnoseService")
	private DiagnoseService diagnoseService;
	
	@RequestMapping(value="/diagnose/selfDgnsMastList.do")
    public ModelAndView selfDgnsMastList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/diagnose/selfDgnsMastList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/diagnose/selectSelfDgnsMastList.do")
    public ModelAndView selectSelfDgnsMastList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = diagnoseService.selectSelfDgnsMastList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	@RequestMapping(value="/diagnose/selfDgnsContent.do")
	public ModelAndView selfDgnsContent(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/diagnose/selfDgnsContent");
		
		Map<String,Object> map = diagnoseService.selfDgnsContentView(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("lists", map.get("lists"));
		mv.addObject("listq", map.get("listq"));
		mv.addObject("liste", map.get("liste"));
		mv.addObject("listq_cnt", map.get("listq_cnt"));
		
		return mv;
	}
	
	
	@RequestMapping(value="/diagnose/selfDgnsMastWrite.do")
	public ModelAndView selfDgnsMastWrite(CommandMap commandMap) throws Exception{
	    	
		ModelAndView mv = new ModelAndView("/diagnose/selfDgnsMastWrite");
		
	   	return mv;
	}
		
	
	@RequestMapping(value="/diagnose/selfDgnsBasicInfo.do")
	public ModelAndView selfDgnsBasicInfo(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/diagnose/selfDgnsBasicInfo");
		
		Map<String,Object> map = diagnoseService.selfDgnsBasicInfo(commandMap.getMap());
		mv.addObject("map", map.get("map"));
				
		return mv;
	}
	

	@RequestMapping(value="/diagnose/insAndUpDgnsMast.do")
	public ModelAndView insAndUpDgnsMast(CommandMap commandMap, MultipartHttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/diagnose/selfDgnsMastList.do");
		String spath = null;
		String flag = null;
		
		//diagnoseService.insertDgnsMast(commandMap.getMap(), request);
					
		MultipartFile excelFile =request.getFile("excelFile");
		flag = commandMap.getMap().get("GB").toString();
		
		System.out.println("flag111=================>>>>> [" + flag + "]");
		
		if("U".equals(flag) && excelFile.isEmpty()){
			diagnoseService.updateDgnsMast(commandMap.getMap(), request);
		}
		else
		{
		
	        System.out.println("엑셀 파일 업로드 컨트롤러");
	        if(excelFile==null || excelFile.isEmpty()){
	            throw new RuntimeException("엑셀파일을 선택 해 주세요.");        	
	        }
	               
	        String profiles = currentProfile();
	       
	        if("prod".equals(profiles))
	        {        	
	        	spath = "/usr/local/server/file/sds/excel/";
	        }
	        else{        	        
	        	spath = "D:\\imsi\\";        	
	        }
	        
	        File destFile = new File(spath+excelFile.getOriginalFilename());
	        
	        System.out.println("===============================>>>>> ");
	        try{
	            excelFile.transferTo(destFile);
	        }catch(IllegalStateException | IOException e){        	
	            throw new RuntimeException(e.getMessage(),e);        	
	        }        
	        
	        try {
	        	 diagnoseService.excelUpload(commandMap.getMap(), request, destFile, flag);  //입력  : I , 수정 : U        
	        } catch ( Exception e) {        	
	            //e.printStackTrace();
	        	log.debug(e.getMessage());
	        	
	        }       
		}
		
		return mv;
	}
	
	
	

	
	@RequestMapping(value="/diagnose/deleteDgnsMast.do")
	public ModelAndView deleteDgnsMast(CommandMap commandMap, MultipartHttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/diagnose/selfDgnsMastList.do");
		
		diagnoseService.deleteDgnsMast(commandMap.getMap(), request);

		return mv;
	}
	
	
	@RequestMapping(value="/diagnose/ExcelDataUpload.do")
    public ModelAndView ExcelDataUpload(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/diagnose/ExcelDataUpload");
    	
    	return mv;
    }
	
    @RequestMapping(value = "/diagnose/excel.do", method = RequestMethod.GET)
	public ModelAndView exceldown() {
    	ModelAndView mv = new ModelAndView("/diagnose/excel");
    	
    	return mv;
	}
    
    
    @RequestMapping(value = "/diagnose/exceldown.do")
    public void excelDown(CommandMap commandMap, HttpServletResponse response) throws Exception {
    	
    	List<Map<String,Object>> list = diagnoseService.selectSelfDgnsMastList(commandMap.getMap());

        // 워크북 생성
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("게시판");

        Row row = null;
        Cell cell = null;
        int rowNo = 0;     
        
        // 테이블 헤더용 스타일
        CellStyle headStyle = wb.createCellStyle();

        // 가는 경계선을 가집니다.
        headStyle.setBorderTop(BorderStyle.THIN);
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBorderLeft(BorderStyle.THIN);
        headStyle.setBorderRight(BorderStyle.THIN);

        // 배경색은 노란색입니다.
        headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 데이터는 가운데 정렬합니다.
        headStyle.setAlignment(HorizontalAlignment.CENTER);

        // 데이터용 경계 스타일 테두리만 지정
        CellStyle bodyStyle = wb.createCellStyle();

        bodyStyle.setBorderTop(BorderStyle.THIN);
        bodyStyle.setBorderBottom(BorderStyle.THIN);
        bodyStyle.setBorderLeft(BorderStyle.THIN);
        bodyStyle.setBorderRight(BorderStyle.THIN);



        // 헤더 생성
        row = sheet.createRow(rowNo++);
        cell = row.createCell(0);
        cell.setCellStyle(headStyle);
        cell.setCellValue("번호");

        cell = row.createCell(1);
        cell.setCellStyle(headStyle);
        cell.setCellValue("이름");

        cell = row.createCell(2);
        cell.setCellStyle(headStyle);
        cell.setCellValue("실시횟수");
        
        cell = row.createCell(3);
        cell.setCellStyle(headStyle);
        cell.setCellValue("다운로드횟수");
        

        // 데이터 부분 생성
        for( int i=0; i < list.size(); i++) {

            row = sheet.createRow(rowNo++);
            cell = row.createCell(0);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("NO").toString());
            

            cell = row.createCell(1);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("TITLE"));

            cell = row.createCell(2);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("ANSCOUNT").toString());
            
            
            cell = row.createCell(3);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("DOWNCOUNT").toString());
            
        }
        
        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=DGNS_LIST.xls");

        // 엑셀 출력
        wb.write(response.getOutputStream());
        wb.close();
    }

   
    
    @Autowired ConfigurableWebApplicationContext subContext;
    private String currentProfile(){ 
    	String[] profiles = subContext.getEnvironment().getActiveProfiles(); 
    	
    	if( profiles.length==0 ){ 
    		profiles = subContext.getEnvironment().getDefaultProfiles(); 
    	} 
    	return profiles[0];    	
    }
  
}