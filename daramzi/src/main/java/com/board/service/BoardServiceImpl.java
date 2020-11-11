package com.board.service;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.board.dao.BoardDao;
import com.board.vo.BoardVO;
import com.board.vo.ReplyVO;
import com.common.vo.FileVO;

@Service("boardService")
public class BoardServiceImpl implements BoardService {
 
    @Resource(name="boardDao")
    private BoardDao boardDao;
    
    @Override
    public List<BoardVO> boardList(BoardVO boardVO) {
        return boardDao.boardList(boardVO);
    }
    
    public int boardListCnt(BoardVO boardVO) {
        return boardDao.boardListCnt(boardVO);
    }
 
    public BoardVO findByIdx(BoardVO boardVO) {
        return boardDao.findByIdx(boardVO);
    }
 
    @Override
    public BoardVO viewContent(BoardVO boardVO) {
        return boardDao.viewContent(boardVO);
    }
 
    @Override
    public void insertBoard(BoardVO boardVO) {
    	boardDao.insertBoard(boardVO);
    }
 
    @Override
    public int updateBoard(BoardVO boardVO) {
        return boardDao.updateBoard(boardVO);
    }
 
    @Override
    public String deleteView(int boardIdx) {
        return boardDao.deleteView(boardIdx);
    }
 
    @Override
    public int deleteBoard(BoardVO boardVO) {
        return boardDao.deleteBoard(boardVO);
    }
 
    @Override
    public void replyBoard(BoardVO boardVO) {
        boardDao.replyBoard(boardVO);
    }
 
    @Override
    public void replyUpPos(BoardVO boardVO) {
        boardDao.replyUpPos(boardVO);
    }
    
    @Override
    public List<FileVO> fileList(int boardIdx) {
        return boardDao.fileList(boardIdx);
    }
    
    @Override
    public void insertBoardRecommend(BoardVO boardVO) {
    	boardDao.insertBoardRecommend(boardVO);        
    }
    
    @Override
    public int deleteBoardRecommend(BoardVO boardVO) {
        int result = boardDao.deleteBoardRecommend(boardVO);
        return result;
    }
    
    public BoardVO selectBoardRecommendCnt(BoardVO boardVO) {
        return boardDao.selectBoardRecommendCnt(boardVO);
    }
    
    public void insertReply(ReplyVO replyVO) {
    	boardDao.insertReply(replyVO);
    }
    
    public List<ReplyVO> replyList(int boardIdx) {
    	return boardDao.replyList(boardIdx);
    }
 
}
