package com.Amember.service;


import java.util.List;

import com.Amember.vo.AmemberVO;


public interface AmemberService {
 
    public List<AmemberVO> memberList(AmemberVO memberVO); 
    public int memberListCnt(AmemberVO memberVO);
    
    public AmemberVO findBySeq(int seq);
    public AmemberVO viewContent(int seq);
    public int updateAmember(AmemberVO member);
    public String deleteView(int seq);
    public int deleteAmember(AmemberVO member);
    public void replyAmember(AmemberVO member);
    public void replyUpPos(AmemberVO member);
    
    public void fileupload(AmemberVO member);
}
