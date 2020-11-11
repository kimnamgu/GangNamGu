package com.Amember.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.Amember.vo.AmemberVO;
import com.common.dao.AbstractDAO;

@Repository("memberDao")
public class AmemberDao extends AbstractDAO  {

	@SuppressWarnings("unchecked")
	public List<AmemberVO> memberList(AmemberVO memberVO) {
        List<AmemberVO> result = new ArrayList<AmemberVO>();
        result = (List<AmemberVO>)selectList("member.memberList",memberVO);
        return result;
    }
    
	public int memberListCnt(AmemberVO memberVO) {
        int resultCnt = (Integer)selectOne("member.memberListCnt",memberVO);
        return resultCnt;
    }
 
    public String preView(int seq) {
		/* String preContent = AmemberManager.preView(seq); */
        String preContent = "";
        return preContent;
    }
    
    //글 수정시 내용 조회
    public AmemberVO findBySeq(int seq) {
    	AmemberVO result = (AmemberVO) selectOne("member.findBySeq",seq);
        return result;
    }
 
    public AmemberVO viewContent(int seq) {
    	update("member.readCount",seq);	// 글보면 카운트수 증가
    	AmemberVO result = new AmemberVO();
    	result = (AmemberVO) selectOne("member.findBySeq",seq);
        return result;
    }
 
    // 글쓰기는  upPos와 insertAmember 두개임
    public void upPos(){
		/*
		 * AmemberManager.upPos(); System.out.println(" 글을 쓰면 Pos 1업");
		 */
    }
        
    // 게시글 수정,삭제는 바로 리턴 ...... 글쓰기는 void처리
    public int updateAmember(AmemberVO member) {
    	update("memberWrite.updateAmember",member);
        return 1;
    }
 
    // 글삭제
    public int deleteAmember(AmemberVO member) {
		 delete("memberWrite.deleteAmember",member);
		 return 1;
    }
 
    public String deleteView(int seq) {
		/*
		 * String storPass = AmemberManager.deleteView(seq); return storPass;
		 */
        return "";
    }
 
    public void replyAmember(AmemberVO member) {
		/* AmemberManager.replymember(member); */
    }
 
    public void replyUpPos(AmemberVO member) {
		/* AmemberManager.replyUpPos(member); */
    }
    
    public void fileupload(AmemberVO member) {
    	insert("imagemember.fileupload", member);
    }
}

