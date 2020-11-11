package com.common.service;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.common.dao.FileMngDao;
import com.common.vo.FileVO;
 
@Service("fileMngService")
public class FileMngServiceImpl implements FileMngService {
    @Resource(name="fileMngDao")
    private FileMngDao fileUploadDao;
	
    @Override
    public void fileupload(FileVO fileVO) {
    	fileUploadDao.fileupload(fileVO);
    }
    
    @Override
    public FileVO selectFile(String fileIdx) {
    	return fileUploadDao.selectFile(fileIdx);
    }
    
    @Override
    public String deleteFile(String fileIdx) {
        return fileUploadDao.deleteFile(fileIdx);
    }
}
