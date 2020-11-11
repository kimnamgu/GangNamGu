package com.common.dao;

import org.springframework.stereotype.Repository;

import com.common.vo.FileVO;

@Repository("fileMngDao")
public class FileMngDao extends AbstractDAO  {
    public void fileupload(FileVO fileVO) {
    	insert("file.fileupload", fileVO);
    }
    
    public FileVO selectFile(String fileIdx) {
    	FileVO result = new FileVO();
    	result = (FileVO) selectOne("file.selectFile",fileIdx);
        return result;
    }
    
    public String deleteFile(String fileIdx) {
    	delete("file.deleteFile",fileIdx);
        return "1";
    }
}

