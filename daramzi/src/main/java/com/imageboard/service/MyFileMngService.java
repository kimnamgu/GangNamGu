package com.imageboard.service;


import java.util.List;
import java.util.Map;

import com.imageboard.vo.MyFileMngVO;

public interface MyFileMngService {
 
    public List<Map<String,Object>> myFileList(MyFileMngVO myFileMngVO); 
    public int myFileListCnt(MyFileMngVO myFileMngVO);
    
    public int updateFileInfo(MyFileMngVO myFileMngVO);
    
    public MyFileMngVO selectFile(String fileIdx);
    public String deleteFile(String fileIdx);
    
}
