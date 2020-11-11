package vbms.daejang.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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


import vbms.common.common.CommandMap;
import vbms.common.util.CommonLib;
import vbms.common.util.CommonUtils;
import vbms.common.util.Nvl;
import vbms.daejang.service.DaejangService;

@Controller
public class DaejangController {
	Logger log = Logger.getLogger(this.getClass());
	
	
	@Resource(name="daejangService")
	private DaejangService daejangService;
	
	@RequestMapping(value="/daejang/violBuildingList.do")
    public ModelAndView violBuildingList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/violBuildingList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/daejang/selectViolBuildingList.do")
    public ModelAndView selectViolBuildingList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectViolBuildingList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	
	@RequestMapping(value="/daejang/violBuildingUpList.do")
    public ModelAndView violBuildingUpList(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/violBuildingUpList");
    	
    	return mv;
    }
	
	@RequestMapping(value="/daejang/selectViolBuildingUpList.do")
    public ModelAndView selectViolBuildingUpList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	List<Map<String,Object>> list = daejangService.selectViolBuildingUpList(commandMap.getMap());
    	mv.addObject("list", list);
    	if(list.size() > 0){
    		mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
    	}
    	else{
    		mv.addObject("TOTAL", 0);
    	}
    	
    	return mv;
    }
	
	
	
	
	
	
	@RequestMapping(value="/daejang/violBuildingWrite.do")
	public ModelAndView selfDgnsMastWrite(CommandMap commandMap) throws Exception{
	    	
		ModelAndView mv = new ModelAndView("/daejang/violBuildingWrite");
		
	   	return mv;
	}
	
	
	@RequestMapping(value="/daejang/violBuildingUpload.do")
	public ModelAndView violBuildingUpload(CommandMap commandMap) throws Exception{
	    	
		ModelAndView mv = new ModelAndView("/daejang/violBuildingUpload");
		
	   	return mv;
	}
	
	
	@RequestMapping(value="/daejang/uploadViolBuilding.do")
	public ModelAndView uploadViolBuilding(CommandMap commandMap, MultipartHttpServletRequest request) throws Exception{
	    	
		ModelAndView mv = new ModelAndView("redirect:/daejang/violBuildingList.do");
		String spath = null;
		
		MultipartFile excelFile =request.getFile("excelFile");
		System.out.println("엑셀 파일 업로드 컨트롤러");
		
        if(excelFile==null || excelFile.isEmpty()){
            throw new RuntimeException("엑셀파일을 선택 해 주세요.");        	
        }
               
        String profiles = currentProfile();
       
        if("prod".equals(profiles))
        {        	
        	spath = "/usr/local/server/file/vbms/excel/";
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
        	daejangService.excelUpload(commandMap.getMap(), request, destFile);        
        } catch ( Exception e) {        	
            //e.printStackTrace();
        	log.debug(e.getMessage());
        	
        }       
		
		
	   	return mv;
	}
	
	@RequestMapping(value="/daejang/violBuildingContent.do")
	public ModelAndView violBuildingContent(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/daejang/violBuildingContent");
		
		Map<String,Object> map = daejangService.violBuildingContent(commandMap.getMap());
		mv.addObject("map", map.get("map"));
				
		return mv;
	}
	

	@RequestMapping(value="/daejang/insBldMngDaejang.do")
	public ModelAndView insBldMngDaejang(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/violBuildingList.do");
		String spath = null;
		String flag = null;
		
		daejangService.insBldMngDaejang(commandMap.getMap(), request);
					
		return mv;
	}
	
	
	
	
	@RequestMapping(value="/daejang/updateBldMngDaejang.do")
	public ModelAndView updateBldMngDaejang(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/violBuildingList.do");
		
		daejangService.updateBldMngDaejang(commandMap.getMap(), request);

		return mv;
	}
	
	
	@RequestMapping(value="/daejang/deleteBldMngDaejang.do")
	public ModelAndView deleteBldMngDaejang(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/violBuildingList.do");
		
		daejangService.deleteBldMngDaejang(commandMap.getMap(), request);

		return mv;
	}
	
	
	@RequestMapping(value="/daejang/deleteViolBuildingUpload.do")
	public ModelAndView deleteViolBuildingUpload(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/daejang/violBuildingUpList.do");
		
		daejangService.deleteViolBuildingUpload(commandMap.getMap(), request);

		return mv;
	}
	
	
	
	@RequestMapping(value="/daejang/ExcelDataUpload.do")
    public ModelAndView ExcelDataUpload(CommandMap commandMap) throws Exception{
    	
		ModelAndView mv = new ModelAndView("/daejang/ExcelDataUpload");
    	
    	return mv;
    }
	
    @RequestMapping(value = "/daejang/excel.do", method = RequestMethod.GET)
	public ModelAndView exceldown() {
    	ModelAndView mv = new ModelAndView("/daejang/excel");
    	
    	return mv;
	}
    
    
    @RequestMapping(value = "/daejang/exceldown.do")
    public void excelDown(CommandMap commandMap, HttpServletResponse response) throws Exception {
    	String tmp = null;
    	List<Map<String,Object>> list = daejangService.selectViolBuildingList(commandMap.getMap());

        // 워크북 생성
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("위반건축물");

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
        cell.setCellValue("행정동");

        cell = row.createCell(2);
        cell.setCellStyle(headStyle);
        cell.setCellValue("주소");
        
        cell = row.createCell(3);
        cell.setCellStyle(headStyle);
        cell.setCellValue("도로명");
        
        cell = row.createCell(4);
        cell.setCellStyle(headStyle);
        cell.setCellValue("건축주");
        
        cell = row.createCell(5);
        cell.setCellStyle(headStyle);
        cell.setCellValue("건축주주소");
        
        cell = row.createCell(6);
        cell.setCellStyle(headStyle);
        cell.setCellValue("구조");
        
        cell = row.createCell(7);
        cell.setCellStyle(headStyle);
        cell.setCellValue("면적");
                
        cell = row.createCell(8);
        cell.setCellStyle(headStyle);
        cell.setCellValue("용도");
        
        cell = row.createCell(9);
        cell.setCellStyle(headStyle);
        cell.setCellValue("위치");
        
        cell = row.createCell(10);
        cell.setCellStyle(headStyle);
        cell.setCellValue("적발방법");
        
        cell = row.createCell(11);
        cell.setCellStyle(headStyle);
        cell.setCellValue("사전예고");
        
        cell = row.createCell(12);
        cell.setCellStyle(headStyle);
        cell.setCellValue("시정명령");
        
        cell = row.createCell(13);
        cell.setCellStyle(headStyle);
        cell.setCellValue("위반건축물등재일");
        
        cell = row.createCell(14);
        cell.setCellStyle(headStyle);
        cell.setCellValue("의견진술기한");
        
        cell = row.createCell(15);
        cell.setCellStyle(headStyle);
        cell.setCellValue("이행강제금부과일");
        
        cell = row.createCell(16);
        cell.setCellStyle(headStyle);
        cell.setCellValue("이행강제금부과액");

        cell = row.createCell(17);
        cell.setCellStyle(headStyle);
        cell.setCellValue("새무과통보");
        
        cell = row.createCell(18);
        cell.setCellStyle(headStyle);
        cell.setCellValue("처리결과");
        
        cell = row.createCell(19);
        cell.setCellStyle(headStyle);
        cell.setCellValue("비고");

        // 데이터 부분 생성
        for( int i=0; i < list.size(); i++) {

            row = sheet.createRow(rowNo++);
            cell = row.createCell(0);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("RNUM").toString());
            
            cell = row.createCell(1);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("BLD_DONG"));

            cell = row.createCell(2);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("BLD_ADDR1"));
                        
            cell = row.createCell(3);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("BLD_ADDR_ROAD"));
            
            cell = row.createCell(4);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("BLD_OWNER"));
            
            cell = row.createCell(5);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("MBLD_ADDR_ROAD"));
            
            cell = row.createCell(6);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("S_BLD_STRUCTURE"));
                        
            cell = row.createCell(7);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("VIOL_AREA").toString());
                        
            cell = row.createCell(8);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("S_PURPOSE"));
            
            cell = row.createCell(9);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("LOCATION"));
            
            cell = row.createCell(10);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("EXPOSURE_DETAILS"));
            
            cell = row.createCell(11);
            cell.setCellStyle(bodyStyle);
            tmp = CommonUtils.changeDateFormat((String) list.get(i).get("PRE_CORRTN_ORDER"), ".");
            cell.setCellValue(tmp);

            cell = row.createCell(12);
            cell.setCellStyle(bodyStyle);
            tmp = CommonUtils.changeDateFormat((String) list.get(i).get("CORRTN_ORDER"), ".");
            cell.setCellValue(tmp);
            
            cell = row.createCell(13);
            cell.setCellStyle(bodyStyle);
            tmp = CommonUtils.changeDateFormat((String) list.get(i).get("VIOL_BUILDING_REGDATE"), ".");
            cell.setCellValue(tmp);
            
            cell = row.createCell(14);
            cell.setCellStyle(bodyStyle);
            tmp = CommonUtils.changeDateFormat((String) list.get(i).get("OPINION_STATE_LIMIT"), ".");
            cell.setCellValue(tmp);
            
            cell = row.createCell(15);
            cell.setCellStyle(bodyStyle);
            tmp = CommonUtils.changeDateFormat((String) list.get(i).get("EXEC_IMPOSE_DATE"), ".");
            cell.setCellValue(tmp);
            
            
            cell = row.createCell(16);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(NumberFormat.getInstance().format(list.get(i).get("EXEC_IMPOSE_AMT")));
            
            cell = row.createCell(17);
            cell.setCellStyle(bodyStyle);
            tmp = CommonUtils.changeDateFormat((String) list.get(i).get("TAX_DEP_NOTEDATE"), ".");
            cell.setCellValue(tmp);
            
            cell = row.createCell(18);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("S_STATE"));
            
            cell = row.createCell(19);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue((String) list.get(i).get("BIGO"));
            
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
    
    
    @RequestMapping(value="/daejang/sample.do")
	public ModelAndView SampleTest(CommandMap commandMap) throws Exception{
	    	
		ModelAndView mv = new ModelAndView("/daejang/Sample");
		
	   	return mv;
	}
    
    
    @RequestMapping(value="/daejang/samplepop.do")
	public ModelAndView SamplePop(CommandMap commandMap) throws Exception{
	    	
		ModelAndView mv = new ModelAndView("/daejang/jusoPopup");
		
	   	return mv;
	}
  
    @RequestMapping(value="/daejang/msamplepop.do")
	public ModelAndView mSamplePop(CommandMap commandMap) throws Exception{
	    	
		ModelAndView mv = new ModelAndView("/daejang/mjusoPopup");
		
	   	return mv;
	}
    
}