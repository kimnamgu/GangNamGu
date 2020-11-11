package com.imageboard.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.AbstractDAO;
import com.common.vo.FolderVO;
import com.imageboard.vo.ImageBoardVO;

@Repository("imageboardDao")
public class ImageBoardDao extends AbstractDAO  {

	@SuppressWarnings("unchecked")
	public List<ImageBoardVO> imageboardList(ImageBoardVO imageboardVO) {
        List<ImageBoardVO> result = new ArrayList<ImageBoardVO>();
        result = (List<ImageBoardVO>)selectList("imageboard.imageboardList",imageboardVO);
        return result;
    }
    
	public int imageboardListCnt(ImageBoardVO imageboardVO) {
        int resultCnt = (Integer)selectOne("imageboard.imageboardListCnt",imageboardVO);
        return resultCnt;
    }
 
    public String preView(int seq) {
		/* String preContent = BoardManager.preView(seq); */
        String preContent = "";
        return preContent;
    }
    
    //글 수정시 내용 조회
    public ImageBoardVO findByIdx(int seq) {
    	ImageBoardVO result = (ImageBoardVO) selectOne("imageboard.findByIdx",seq);
        return result;
    }
 
    public ImageBoardVO imageviewContent(int seq) {
    	update("imageboard.readCount",seq);	// 글보면 카운트수 증가
    	ImageBoardVO result = new ImageBoardVO();
    	result = (ImageBoardVO) selectOne("imageboard.findByIdx",seq);
        return result;
    }
 
    // 글쓰기는  upPos와 insertBoard 두개임
    public void upPos(){
		/*
		 * BoardManager.upPos(); System.out.println(" 글을 쓰면 Pos 1업");
		 */
    }
        
    public void insertimageBoard(ImageBoardVO imageboard) {
        //upPos();
        insert("imageboardWrite.insertimageBoard", imageboard);
    }
 
    
    // 게시글 수정,삭제는 바로 리턴 ...... 글쓰기는 void처리
    public int updateimageBoard(ImageBoardVO imageboard) {
    	update("imageboardWrite.updateimageBoard",imageboard);
        return 1;
    }
 
    // 글삭제
    public int deleteimageBoard(ImageBoardVO imageboard) {
		 delete("imageboardWrite.deleteimageBoard",imageboard);
		 return 1;
    }
 
    public String deleteimageView(int seq) {
		/*
		 * String storPass = BoardManager.deleteView(seq); return storPass;
		 */
        return "";
    }
 
    public void replyimageBoard(ImageBoardVO imageboard) {
		/* BoardManager.replyboard(imageboard); */
    }
 
    public void replyUpPos(ImageBoardVO imageboard) {
		/* BoardManager.replyUpPos(imageboard); */
    }
    
    
    public void mkDir(FolderVO folderVo) {
    	insert("fileupload.mkDir", folderVo);
    }
    public void delDir(FolderVO folderVo) {
    	delete("fileupload.delDir", folderVo);
    }
    
	@SuppressWarnings("unchecked")
	public List<ImageBoardVO> selectFolder(String id) {
        List<ImageBoardVO> result = new ArrayList<ImageBoardVO>();
        result = (List<ImageBoardVO>)selectList("imageboard.selectFolder",id);
        return result;
    }
	
	
    public int delFileInPath(ImageBoardVO imageboard) {
    	delete("imageboard.delFileInPath",imageboard);
        return 1;
    }
    
    public void insertFileRecommend(ImageBoardVO imageboard) {
        insert("imageboard.insertFileRecommend", imageboard);
    }
    
    public int deleteFileRecommend(ImageBoardVO imageboard) {
    	delete("imageboard.deleteFileRecommend",imageboard);
        return 1;
    }
    
    public ImageBoardVO selectRecommendCnt(ImageBoardVO imageboard) {
    	ImageBoardVO result = new ImageBoardVO();
    	result = (ImageBoardVO) selectOne("imageboard.selectRecommendCnt",imageboard);
        return result;
    }
}

