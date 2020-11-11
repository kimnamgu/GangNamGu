package com.imageboard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.common.dao.AbstractDAO;
import com.imageboard.vo.MyFileMngVO;

@Repository("myFileMngDao")
public class MyFileMngDao extends AbstractDAO  {

	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> myFileList(MyFileMngVO myFileMngVO) {
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        result = (List<Map<String,Object>>)selectList("myFileMng.myFileList",myFileMngVO);
        return result;
    }
    
	public int myFileListCnt(MyFileMngVO myFileMngVO) {
        int resultCnt = (Integer)selectOne("myFileMng.myFileListCnt",myFileMngVO);
        return resultCnt;
    }

	public int updateFileInfo(MyFileMngVO myFileMngVO) {
		update("myFileMng.updateFileInfo",myFileMngVO);
        return 1;
    }

    public MyFileMngVO selectFile(String fileIdx) {
    	MyFileMngVO result = new MyFileMngVO();
    	result = (MyFileMngVO) selectOne("myFileMng.selectFile",fileIdx);
        return result;
    }
	
    public String deleteFile(String fileIdx) {
    	delete("myFileMng.deleteFile",fileIdx);
        return "1";
    }
}

