package com.imageboard.service;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.common.vo.FolderVO;
import com.imageboard.dao.ImageBoardDao;
import com.imageboard.vo.ImageBoardVO;
 
@Service("imageboardService")
public class ImageBoardServiceImpl implements ImageBoardService {
 
    @Resource(name="imageboardDao")
    private ImageBoardDao imageboardDao;
    
    @Override
    public List<ImageBoardVO> imageboardList(ImageBoardVO imageboardVO) {
        return imageboardDao.imageboardList(imageboardVO);
    }
    
    public int imageboardListCnt(ImageBoardVO imageboardVO) {
        return imageboardDao.imageboardListCnt(imageboardVO);
    }
 
    public ImageBoardVO findByIdx(int seq) {
        return imageboardDao.findByIdx(seq);
    }
 
    @Override
    public ImageBoardVO imageviewContent(int seq) {
        return imageboardDao.imageviewContent(seq);
    }
 
    @Override
    public void insertimageBoard(ImageBoardVO imageboard) {
        imageboardDao.insertimageBoard(imageboard);        
    }
 
    @Override
    public int updateimageBoard(ImageBoardVO imageboard) {
        return imageboardDao.updateimageBoard(imageboard);
    }
 
    @Override
    public String deleteimageView(int seq) {
        return imageboardDao.deleteimageView(seq);
    }
 
    @Override
    public int deleteimageBoard(ImageBoardVO imageboard) {
        return imageboardDao.deleteimageBoard(imageboard);
    }
 
    @Override
    public void replyimageBoard(ImageBoardVO imageboard) {
        imageboardDao.replyimageBoard(imageboard);
    }
 
    @Override
    public void replyUpPos(ImageBoardVO imageboard) {
        imageboardDao.replyUpPos(imageboard);
    }
    
    @Override
    public void mkDir(FolderVO folderVo) {
        imageboardDao.mkDir(folderVo);
    }
    
    @Override
    public void delDir(FolderVO folderVo) {
        imageboardDao.delDir(folderVo);
    }
    
    @Override
    public List<ImageBoardVO> selectFolder(String id) {
        return imageboardDao.selectFolder(id);
    }
 
    @Override
    public int delFileInPath(ImageBoardVO imageboard) {
        return imageboardDao.delFileInPath(imageboard);
    }
    
    @Override
    public void insertFileRecommend(ImageBoardVO imageboard) {
        imageboardDao.insertFileRecommend(imageboard);        
    }
    
    @Override
    public int deleteFileRecommend(ImageBoardVO imageboard) {
        int result = imageboardDao.deleteFileRecommend(imageboard);
        return result;
    }
    
    public ImageBoardVO selectRecommendCnt(ImageBoardVO imageboard) {
        return imageboardDao.selectRecommendCnt(imageboard);
    }
}
