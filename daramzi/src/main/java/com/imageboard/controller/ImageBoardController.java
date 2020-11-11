package com.imageboard.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.board.vo.PageVO;
import com.common.service.FileMngService;
import com.common.util.Const;
import com.common.util.GetSession;
import com.common.vo.FileVO;
import com.common.vo.FolderVO;
import com.imageboard.service.ImageBoardService;
import com.imageboard.vo.ImageBoardVO;
 
@Controller
public class ImageBoardController {
    @Resource(name="imageboardService")
    private ImageBoardService imageboardService;
    
    @Resource(name="fileMngService")
    private FileMngService fileMngService;
    
    //사진게시판 목록
    @RequestMapping(value="/imageboard/imageboardList")
    public ModelAndView imageboardList(@ModelAttribute("search") ImageBoardVO info,ModelMap model, HttpServletRequest request) throws Exception
    {
        ModelAndView view = new ModelAndView();

        String id = "";
        try {
        	id =  GetSession.GetSessionId(request); //세션아이디 가져오기
        }catch (Exception e) {
        	id = "";
        }
        
        info.setId(id);
        info.setCategoryCode("imageBoard");
        
        
        int totalCount = imageboardService.imageboardListCnt(info);
        PageVO page = new PageVO();
        page.setDisplayRow(info.getListNum());       
        page.setTotalCount(totalCount);
        page.setPage(info.getPage());
        
        List<ImageBoardVO> resultlist = imageboardService.imageboardList(info);
        
        view.addObject("resultlist", resultlist);
        view.addObject("page",page);
        
        //조건값 유지를 위한 model
        view.addObject("search",info);
        view.setViewName("imageBoard/imageBoard_List.view");
        return view;
    }
    
    //사진 업로드 화면
    @RequestMapping(value="/imageboard/fileUploadForm")
    public ModelAndView fileUploadForm(@ModelAttribute("search") ImageBoardVO info,ModelMap model)
    {
    	System.out.println("컨트롤러 : fileUploadForm");
        ModelAndView view = new ModelAndView();
        view.setViewName("imageBoard/fileUploadForm.view");
        return view;
    }
    
    
    //파일관리 화면
    @RequestMapping(value="/imageboard/myFileMng")
    public ModelAndView myImageMng(@ModelAttribute("search") ImageBoardVO info,ModelMap model)
    {
        ModelAndView view = new ModelAndView();
        view.setViewName("imageBoard/myFileMng.view");
        return view;
    }
    

    //파일 업로드 수행
    @ResponseBody //리턴되는 값은 View 를 통해서 출력되지 않고 HTTP Response Body 에 직접 쓰여지게 됨.
    @RequestMapping(value="/imageboard/fileupload", method= RequestMethod.POST)
	public int fileupload(@RequestParam("files") List<MultipartFile> fileList,
			@RequestParam("folderName") String folderName
			, HttpServletRequest request
			, ImageBoardVO info) throws Exception {
    	
    	System.out.println("info인자값 확인 : " + info.toStringMultiline());

        String uploadDir =this.getClass().getResource("").getPath();
        uploadDir = uploadDir.substring(1,uploadDir.indexOf(".metadata"))+"daramzi/src/main/webapp/resources/uploadImage";
        
    	String folderPath = "";
    	
    	String id = GetSession.GetSessionId(request); //세션아이디 가져오기
    	//폴더별 사진 저장
    	if(folderName.equals("..")) {//제일 상위폴더를 선택했을경우
    		folderPath = id + "/imageBoard";
    	}else {//폴더를 선택했을 경우 폴더 명에 파일을 업로드 시킴
    		folderPath = id + "/imageBoard/" + folderName;
    	}
    	
    	uploadDir = uploadDir + "/" + folderPath;
        
        long sizeSum = 0;
        for(MultipartFile f : fileList){
        	
        	String uuid = UUID.randomUUID().toString();
        	uuid = uuid.replace("-", "");
        	
        	// check for Unix-style path
    		int pos = f.getOriginalFilename().lastIndexOf("/");
    		String getOrgFileName = "";
    		if (pos == -1) {
    			// check for Windows-style path
    			pos = f.getOriginalFilename().lastIndexOf("\\");
    		}
    		if (pos != -1)  {
    			// any sort of path separator found
    			getOrgFileName= f.getOriginalFilename().substring(pos + 1);
    		}
    		
            //확장자 검사
            if(!isValidExtension(getOrgFileName)){
                return Const.RESULT_UNACCEPTED_EXTENSION;
            }

            
            //용량 검사
            sizeSum += f.getSize();
            if(sizeSum >= Const.LIMIT_SIZE) {
                return Const.RESULT_EXCEED_SIZE;
            }


        	
        	// 파일이 실제 저장될때 고유의 값으로 저장 되도록 random 값으로 파일명 설정
        	String savedName = uuid + "_" + getOrgFileName;
        	
        	File target = new File(uploadDir,savedName);
        	FileCopyUtils.copy(f.getBytes(), target);
        	
        	
            String fileExtension = FilenameUtils.getExtension(f.getOriginalFilename()); // 확장자
            String fileName = FilenameUtils.getBaseName(f.getOriginalFilename());	// 파일명
        	
            FileVO sendVO = new FileVO();
            
        	sendVO.setFileName(fileName);
        	sendVO.setFileExtension(fileExtension);
        	sendVO.setFileRealName(savedName);
        	sendVO.setFileSize(f.getSize());
        	sendVO.setCategoryCode("imageBoard");
        	sendVO.setFolderPath(folderPath);
        	sendVO.setFileSeq(0);
        	//sendVO.setBoardIdx(vo.getBoardIdx()); // auto increment 값을 쿼리에서 설정으로 받아올수 있음
        	sendVO.setId(id);
        	
        	fileMngService.fileupload(sendVO);
        }

        return Const.RESULT_SUCCESS;
    }
    
    
    //required above jdk 1.7 - switch(String)
	private boolean isValidExtension(String originalName) {
		String originalNameExtension = originalName.substring(originalName.lastIndexOf(".") + 1);
		switch (originalNameExtension) {
		case "jpg":
		case "png":
		case "gif":
			return true;
		}
		return false;
	}

    
    //파일 추천 상태 변경
    @RequestMapping(value="/imageboard/fileRecommend", method= RequestMethod.POST)
    public @ResponseBody ImageBoardVO fileRecommend(ImageBoardVO info,HttpServletRequest request) throws Exception {
    	
    	System.out.println("info인자값 확인 : " + info.toStringMultiline());
    	
    	String id = GetSession.GetSessionId(request); //세션아이디 가져오기
    	info.setId(id);
    	
    	//추천 상태 바꾸기전 기존 정보 삭제
    	int delresult = imageboardService.deleteFileRecommend(info);
    	
    	if(delresult == 1) {
    		imageboardService.insertFileRecommend(info);
    	}
    	
    	ImageBoardVO getCnt = new ImageBoardVO();
    	getCnt = imageboardService.selectRecommendCnt(info);
    	
    	info.setRecommendYcnt(getCnt.getRecommendYcnt());
    	info.setRecommendNcnt(getCnt.getRecommendNcnt());
    	
    	System.out.println("나중 info인자값 확인 : " + info.toStringMultiline());
    	
    	
	   return info;
    }
	
    //선택 파일 삭제 액션 ajax 로 변경 -- 기능을 옮겨야할듯함
	@RequestMapping(value = "/imageboard/fileChk", method = RequestMethod.POST)
	public @ResponseBody List<String> fileChk(@RequestParam("sendStyle") String sendStyle,
			@RequestParam("idxArr[]") List<String> idxArr) {

		for (String value : idxArr) {

			if (sendStyle.equals("del")) { // 체크파일 삭제일경우
				fileDel(value);
			}
		}

		return idxArr;
	}
    
    
    //파일 삭제 수행
    @RequestMapping(value="/imageboard/fileDel", method= RequestMethod.POST)
    public ResponseEntity<String> fileDel(String fileIdx){
		FileVO fileSelct= fileMngService.selectFile(fileIdx);
		
        String uploadDir =this.getClass().getResource("").getPath();

        uploadDir = uploadDir.substring(1,uploadDir.indexOf(".metadata"))+"daramzi/src/main/webapp/resources/uploadImage";
    		
		File f = new File(uploadDir +"/" + fileSelct.getFolderPath() + "/" + fileSelct.getFileRealName()); // 파일 객체생성
    	if( f.exists()) {// 파일이 존재하면 파일을 삭제한다. 
    		fileMngService.deleteFile(fileIdx);
    		f.delete(); 
    	}
    	
	   return new ResponseEntity<String>("deleted",HttpStatus.OK);
    }
    
    
    // 브라우저 정보 get
    public String getBrowser(HttpServletRequest request) {

        String header = request.getHeader("User-Agent");

		   if (header.indexOf("MSIE") > -1) {
		
		       return "MSIE";
		
		   } else if (header.indexOf("Trident") > -1) {   // IE11 문자열 깨짐 방지
		
		       return "Trident";
		
		   } else if (header.indexOf("Chrome") > -1) {
		
		       return "Chrome";
		
		   } else if (header.indexOf("Opera") > -1) {
		
		       return "Opera";
		
		   } else if (header.indexOf("Safari") > -1) {
		
		       return "Safari";
		
		   }
		
		   return "Firefox";

    }

    //파일 다운로드 수행
    @RequestMapping(value="/imageboard/fileDown")
    public ResponseEntity<String> fileDown(@RequestParam String fileIdx, HttpServletRequest request,HttpServletResponse response) throws Exception{
    	FileVO fileSelct= fileMngService.selectFile(fileIdx);
        String uploadDir =this.getClass().getResource("").getPath();

        uploadDir = uploadDir.substring(1,uploadDir.indexOf(".metadata"))+"daramzi/src/main/webapp/resources/uploadImage/" + fileSelct.getFolderPath() ;
        
	    request.setCharacterEncoding("UTF-8");

	    // 파일 업로드된 경로
	    String savePath = uploadDir;

	    // 서버에 실제 저장된 파일명
	    String filename = fileSelct.getFileRealName() ;

	    // 실제 내보낼 파일명
	    String orgfilename = fileSelct.getFileName() + "." + fileSelct.getFileExtension();
	 
	    InputStream in = null;
	    OutputStream os = null;
	    File file = null;
	    boolean skip = false;
	    String client = "";
	    
	    String browser = getBrowser(request);
        String dispositionPrefix = "attachment; filename=";
        String encodedFilename = null;

	    try{
	        // 파일을 읽어 스트림에 담기
	        try{
	            file = new File(savePath, filename);
	            in = new FileInputStream(file);
	        }catch(FileNotFoundException fe){
	            skip = true;
	        }

	        client = request.getHeader("User-Agent");

	        // 파일 다운로드 헤더 지정
	        response.reset() ;
	        response.setContentType("application/octet-stream");
	        response.setHeader("Content-Description", "JSP Generated Data");

	        if(!skip){
	        	
		        //브라우저별 한글깨짐현상 방지	
	        	if (browser.equals("MSIE")) {
	
	                    encodedFilename = URLEncoder.encode(orgfilename, "UTF-8").replaceAll("\\+", "%20");
	
	             } else if (browser.equals("Trident")) {       // IE11 문자열 깨짐 방지
	
	                    encodedFilename = URLEncoder.encode(orgfilename, "UTF-8").replaceAll("\\+", "%20");
	
	             } else if (browser.equals("Firefox")) {
	
	                    encodedFilename = "\"" + new String(orgfilename.getBytes("UTF-8"), "8859_1") + "\"";
	
	                    encodedFilename = URLDecoder.decode(encodedFilename);
	
	             } else if (browser.equals("Opera")) {
	
	                    encodedFilename = "\"" + new String(orgfilename.getBytes("UTF-8"), "8859_1") + "\"";
	
	             } else if (browser.equals("Chrome")) {
	
	                    StringBuffer sb = new StringBuffer();
	
	                    for (int i = 0; i < orgfilename.length(); i++) {
	
	                           char c = orgfilename.charAt(i);
	
	                           if (c > '~') {
	
	                                 sb.append(URLEncoder.encode("" + c, "UTF-8"));
	
	                           } else {
	
	                                 sb.append(c);
	
	                           }
	
	                    }
	
	                    encodedFilename = sb.toString();
	
	             } else if (browser.equals("Safari")){
	
	                    encodedFilename = "\"" + new String(orgfilename.getBytes("UTF-8"), "8859_1")+ "\"";
	
	                    encodedFilename = URLDecoder.decode(encodedFilename);
	
	             }
	
	             else {
	
	                    encodedFilename = "\"" + new String(orgfilename.getBytes("UTF-8"), "8859_1")+ "\"";
	
	             }
	        	

	            // IE
	            if(client.indexOf("MSIE") != -1){
	                response.setHeader ("Content-Disposition", dispositionPrefix + encodedFilename);
	            }else{
	                // 한글 파일명 처리
	                response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);
	                response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
	            }  

	            response.setHeader ("Content-Length", ""+file.length() );

	            os = response.getOutputStream();
	            byte b[] = new byte[(int)file.length()];
	            int leng = 0;

	            while( (leng = in.read(b)) > 0 ){
	                os.write(b,0,leng);
	            }
	        }else{
	            response.setContentType("text/html;charset=UTF-8");
	            System.out.println("파일을 찾을 수 없습니다");
	        }

	        in.close();
	        os.close();

	    }catch(Exception e){
	      e.printStackTrace();
	    }
		
    	
	   return new ResponseEntity<String>("deleted",HttpStatus.OK);
    }
    
    
    
    
   
    
    //폴더생성
    @RequestMapping(value = "/imageboard/mkDir", method = RequestMethod.GET)
    public @ResponseBody String mkDir(@RequestParam("folderName") String folderName,HttpServletRequest request) {
    	
    	String id = GetSession.GetSessionId(request); //세션아이디 가져오기
    	if(id != null) {

			// 사용자 정보를 가져올 수 있다.
			String uploadDir = this.getClass().getResource("").getPath();

			uploadDir = uploadDir.substring(1, uploadDir.indexOf(".metadata"))
					+ "daramzi/src/main/webapp/resources/uploadImage";

			String filePath = uploadDir + "/" + id + "/imageBoard/" + folderName + "/";

			File fPath = new File(filePath); // 경로생성

			if (!fPath.exists()) {
				fPath.mkdirs(); // 상위 디렉토리가 존재하지 않으면 상위디렉토리부터 생성.
				
				FolderVO info = new FolderVO();
				
				info.setId(id);
				info.setFolderName(folderName);
				info.setUpperFolder("imageBoard");
				info.setFolderDepth("3");
				imageboardService.mkDir(info);
			}else {
				return "aleadyFolder"; // 같은 이름의 폴더가 이미 있는경우
			}
    	}

        return "SUCCESS";
    }
   
    
    //폴더 삭제
    @RequestMapping(value = "/imageboard/delDir", method = RequestMethod.GET)
    public @ResponseBody String delDir(@RequestParam("folderName") String folderName,HttpServletRequest request) {

    	String id = GetSession.GetSessionId(request); //세션아이디 가져오기
    	if(id != null) {

			// 사용자 정보를 가져올 수 있다.
			String uploadDir = this.getClass().getResource("").getPath();

			uploadDir = uploadDir.substring(1, uploadDir.indexOf(".metadata"))
					+ "daramzi/src/main/webapp/resources/uploadImage";

			String filePath = uploadDir + "/" + id + "/imageBoard/" + folderName + "/";

			File fPath = new File(filePath); // 경로생성

			if (fPath.exists()) {
				System.out.println("폴더가 존재한다.");
				
				deleteFolder(filePath);
				
				
				FolderVO info = new FolderVO();
				
				info.setId(id); 
				info.setFolderName(folderName);
				info.setUpperFolder("imageBoard");
				info.setFolderDepth("3");
				imageboardService.delDir(info);// 폴더삭제
				
				
				ImageBoardVO fileDelInfo = new ImageBoardVO();
				
				fileDelInfo.setId(id); 
				fileDelInfo.setFolderName(folderName);
				fileDelInfo.setFolderPath(id + "/imageBoard/" + folderName); 
				imageboardService.delFileInPath(fileDelInfo);// 폴더 삭제시 하위 파일들 같이 삭제
			}
    	}
        return "SUCCESS";
    }
    
    //폴더삭제시 폴더에 파일이 있을경우 안지워지기 때문에 재귀적으로 처리
	public static void deleteFolder(String path) {

		File folder = new File(path);
		try {
			if (folder.exists()) {
				File[] folder_list = folder.listFiles(); // 파일리스트 얻어오기

				for (int i = 0; i < folder_list.length; i++) {
					if (folder_list[i].isFile()) {
						folder_list[i].delete();
						System.out.println("파일이 삭제되었습니다.");
					} else {
						deleteFolder(folder_list[i].getPath()); // 재귀함수호출
						System.out.println("폴더가 삭제되었습니다.");
					}
					folder_list[i].delete();
				}
				folder.delete(); // 폴더 삭제
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

    
    //폴더보기
    @RequestMapping(value = "/imageboard/folderView", method = RequestMethod.GET)
    public @ResponseBody List<ImageBoardVO> folderView(HttpServletRequest request) {
    	
    	String id = GetSession.GetSessionId(request); //세션아이디 가져오기
    	List<ImageBoardVO> resultVO = imageboardService.selectFolder(id);

        return resultVO;
    }
    
}
