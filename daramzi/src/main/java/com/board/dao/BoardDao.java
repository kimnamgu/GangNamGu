package com.board.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.board.vo.BoardVO;
import com.board.vo.ReplyVO;
import com.common.dao.AbstractDAO;
import com.common.vo.FileVO;

@Repository("boardDao")
public class BoardDao extends AbstractDAO  {

	@SuppressWarnings("unchecked")
	public List<BoardVO> boardList(BoardVO boardVO) {
        List<BoardVO> result = new ArrayList<BoardVO>();
        result = (List<BoardVO>)selectList("board.boardList",boardVO);
        return result;
    }
    
	public int boardListCnt(BoardVO boardVO) {
        int resultCnt = (Integer)selectOne("board.boardListCnt",boardVO);
        return resultCnt;
    }
 
    public String preView(int boardIdx) {
		/* String preContent = BoardManager.preView(seq); */
        String preContent = "";
        return preContent;
    }
    
    //글 수정시 내용 조회
    public BoardVO findByIdx(BoardVO boardVO) {
    	BoardVO result = (BoardVO) selectOne("board.findByIdx",boardVO);
        return result;
    }
 
    public BoardVO viewContent(BoardVO boardVO) {
    	update("board.readCount",boardVO.getBoardIdx());	// 글보면 카운트수 증가
    	BoardVO result = new BoardVO();
    	result = (BoardVO) selectOne("board.findByIdx",boardVO);
        return result;
    }
 
    // 글쓰기는  upPos와 insertBoard 두개임
    public void upPos(){
		/*
		 * BoardManager.upPos(); System.out.println(" 글을 쓰면 Pos 1업");
		 */
    }
        
    public void insertBoard(BoardVO board) {
        //upPos();
        insert("boardWrite.insertBoard", board);
    }
 
    
    // 게시글 수정,삭제는 바로 리턴 ...... 글쓰기는 void처리
    public int updateBoard(BoardVO board) {
    	update("boardWrite.updateBoard",board);
        return 1;
    }
 
    // 글삭제
    public int deleteBoard(BoardVO board) {
		 delete("boardWrite.deleteBoard",board);
		 return 1;
    }
 
    public String deleteView(int boardIdx) {
		/*
		 * String storPass = BoardManager.deleteView(seq); return storPass;
		 */
        return "";
    }
 
    public void replyBoard(BoardVO board) {
		/* BoardManager.replyboard(board); */
    }
 
    public void replyUpPos(BoardVO board) {
		/* BoardManager.replyUpPos(board); */
    }
    
	@SuppressWarnings("unchecked")
	public List<FileVO> fileList(int boardIdx) {
        List<FileVO> result = new ArrayList<FileVO>();
        result = (List<FileVO>)selectList("board.fileList",boardIdx);
        return result;
    }
	
    public void insertBoardRecommend(BoardVO boardVO) {
        insert("board.insertBoardRecommend", boardVO);
    }
    
    public int deleteBoardRecommend(BoardVO boardVO) {
    	delete("board.deleteBoardRecommend",boardVO);
        return 1;
    }
    
    public BoardVO selectBoardRecommendCnt(BoardVO boardVO) {
    	BoardVO result = new BoardVO();
    	result = (BoardVO) selectOne("board.selectBoardRecommendCnt",boardVO);
        return result;
    }
    
    public void insertReply(ReplyVO replyVO) {
        insert("board.insertReply", replyVO);
    }
    
	@SuppressWarnings("unchecked")
	public List<ReplyVO> replyList(int boardIdx) {
        List<ReplyVO> result = new ArrayList<ReplyVO>();
        result = (List<ReplyVO>)selectList("board.replyList",boardIdx);
        return result;
    }
}

