package com.imageboard.service;


import java.util.List;

import com.common.vo.FolderVO;
import com.imageboard.vo.ImageBoardVO;

public interface ImageBoardService {
 
    public List<ImageBoardVO> imageboardList(ImageBoardVO imageboardVO); 
    public int imageboardListCnt(ImageBoardVO imageboardVO);
    
    public ImageBoardVO findByIdx(int seq);
    public ImageBoardVO imageviewContent(int seq);
    public void insertimageBoard(ImageBoardVO imageboardVO);
    public int updateimageBoard(ImageBoardVO imageboardVO);
    public String deleteimageView(int seq);
    public int deleteimageBoard(ImageBoardVO imageboardVO);
    public void replyimageBoard(ImageBoardVO imageboardVO);
    public void replyUpPos(ImageBoardVO imageboardVO);
    
    public void mkDir(FolderVO folderVo);
    public void delDir(FolderVO folderVo);
    
    public List<ImageBoardVO> selectFolder(String id);
    public int delFileInPath(ImageBoardVO imageboardVO);
    
    public void insertFileRecommend(ImageBoardVO imageboardVO);
    public int deleteFileRecommend(ImageBoardVO imageboardVO);
    
    public ImageBoardVO selectRecommendCnt(ImageBoardVO imageboardVO);
}
