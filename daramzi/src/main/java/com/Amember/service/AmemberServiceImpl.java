package com.Amember.service;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.Amember.dao.AmemberDao;
import com.Amember.vo.AmemberVO;

@Service("memberService")
public class AmemberServiceImpl implements AmemberService {
 
    @Resource(name="memberDao")
    private AmemberDao memberDao;
    
    @Override
    public List<AmemberVO> memberList(AmemberVO memberVO) {
        return memberDao.memberList(memberVO);
    }
    
    public int memberListCnt(AmemberVO memberVO) {
        return memberDao.memberListCnt(memberVO);
    }
 
    public AmemberVO findBySeq(int seq) {
        return memberDao.findBySeq(seq);
    }
 
    @Override
    public AmemberVO viewContent(int seq) {
        return memberDao.viewContent(seq);
    }
 
    @Override
    public int updateAmember(AmemberVO member) {
        return memberDao.updateAmember(member);
    }
 
    @Override
    public String deleteView(int seq) {
        return memberDao.deleteView(seq);
    }
 
    @Override
    public int deleteAmember(AmemberVO member) {
        return memberDao.deleteAmember(member);
    }
 
    @Override
    public void replyAmember(AmemberVO member) {
        memberDao.replyAmember(member);
    }
 
    @Override
    public void replyUpPos(AmemberVO member) {
        memberDao.replyUpPos(member);
    }
    
    @Override
    public void fileupload(AmemberVO member) {
    	memberDao.fileupload(member);
    }
 
}
