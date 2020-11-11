package com.common.service;

import com.common.vo.FileVO;

public interface FileMngService {
    public void fileupload(FileVO fileVO);
    
    public FileVO selectFile(String fileIdx);
    public String deleteFile(String fileIdx);
}
