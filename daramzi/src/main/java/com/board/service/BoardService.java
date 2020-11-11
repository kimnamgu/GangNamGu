package com.board.service;


import java.util.List;

import com.board.vo.BoardVO;
import com.board.vo.ReplyVO;
import com.common.vo.FileVO;

public interface BoardService {
 
    public List<BoardVO> boardList(BoardVO boardVO); 
    public int boardListCnt(BoardVO boardVO);
    
    public BoardVO findByIdx(BoardVO boardVO);
    public BoardVO viewContent(BoardVO boardVO);
    public void insertBoard(BoardVO boardVO);
    public int updateBoard(BoardVO boardVO);
    public String deleteView(int boardIdx);
    public int deleteBoard(BoardVO boardVO);
    public void replyBoard(BoardVO boardVO);
    public void replyUpPos(BoardVO boardVO);
    
    public List<FileVO> fileList(int boardIdx);
    
    public void insertBoardRecommend(BoardVO boardVO);
    public int deleteBoardRecommend(BoardVO boardVO);
    
    public BoardVO selectBoardRecommendCnt(BoardVO boardVO);
    
    public void insertReply(ReplyVO replyVO);
    public List<ReplyVO> replyList(int boardIdx);
}
