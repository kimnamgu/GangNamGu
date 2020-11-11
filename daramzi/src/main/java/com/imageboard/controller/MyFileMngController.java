package com.imageboard.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.vo.JsonObj;
import com.imageboard.service.MyFileMngService;
import com.imageboard.vo.MyFileMngVO;

@Controller
public class MyFileMngController {
    @Resource(name="myFileMngService")
    private MyFileMngService myFileMngService;
	
    //내파일 목록
    @ResponseBody
    @RequestMapping(value="/myFileMng/myFileList")
    public JsonObj myFileList(
			@RequestParam(value = "page", required=false) String page,//page : 몇번째 페이지를 요청했는지
			@RequestParam(value = "rows", required=false) String rows,//rows : 페이지 당 몇개의 행이 보여질건지
			@RequestParam(value = "sidx", required=false) String sidx,//sidx : 소팅하는 기준이 되는 인덱스
			@RequestParam(value = "sord", required=false) String sord) {//sord : 내림차순 또는 오름차순
		

		int int_page = Integer.parseInt(page);// 1 2 3
		int perPageNum = (int)Double.parseDouble(rows);
		
		MyFileMngVO sendParam = new MyFileMngVO();
		
		sendParam.setPage(int_page);
		sendParam.setRows(perPageNum);
		sendParam.setSidx(sidx);
		sendParam.setSord(sord);
		
    	int totalCount = myFileMngService.myFileListCnt(sendParam);
    	
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	list = myFileMngService.myFileList(sendParam);
    	
    	JsonObj obj = new JsonObj();
    	
	    obj.setRows(list);  // list<map>형태의 받아온 데이터를 가공해서 셋( 그리드에 뿌려줄 행 데이터들 )
	    	    
	    //page : 현재 페이지
	    obj.setPage(int_page);// 현재 페이지를 매개변수로 넘어온 page로 지정해준다. 
		
	    //records : 보여지는 데이터 개수
	    obj.setRecords(list.size());//?
		
	    //total : rows에 의한 총 페이지수
		// 총 페이지 갯수는 데이터 갯수 / 한페이지에 보여줄 갯수 이런 식
		int totalPage = (int)Math.ceil(totalCount/Double.parseDouble(rows));
		obj.setTotal( totalPage ); // 총 페이지 수 (마지막 페이지 번호)

	    return obj;
	}
	
    //파일 정보 update
    @RequestMapping(value = "/myFileMng/myFileUpdate", method = RequestMethod.GET)
    public @ResponseBody String myFileUpdate(MyFileMngVO sendVO) {

    	int result = myFileMngService.updateFileInfo(sendVO);

        // Edit 구현하기
        return "SUCCESS";
    }

    //파일 삭제 수행
    @RequestMapping(value="/myFileMng/myFileDelete", method= RequestMethod.GET)
    public @ResponseBody String myFileDelete(@RequestParam("id") String id){

    	MyFileMngVO fileSelct= myFileMngService.selectFile(id);
    	
    	String uploadDir =this.getClass().getResource("").getPath();
        uploadDir = uploadDir.substring(1,uploadDir.indexOf(".metadata"))+"daramzi/src/main/webapp/resources/uploadImage";
    		
		File f = new File(uploadDir +"/" + fileSelct.getFolderPath() + "/" + fileSelct.getFileRealName()); // 파일 객체생성
    	if( f.exists()) {// 파일이 존재하면 파일을 삭제한다. 
    		myFileMngService.deleteFile(id);
    		f.delete(); 
    	}
    	
    	return "SUCCESS";
    }
    
}
