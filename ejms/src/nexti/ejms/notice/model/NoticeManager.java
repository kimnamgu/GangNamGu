/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공지사항 manager
 * 설명:
 */
package nexti.ejms.notice.model;

import java.util.List;

import nexti.ejms.util.FileBean;

public class NoticeManager {
	private static NoticeManager instance = null;
	private static NoticeDAO dao = null;
	
	private NoticeManager() {
	}
	
	public static NoticeManager instance() {
		if (instance==null) instance = new NoticeManager(); 
		return instance;
	}
	
	private NoticeDAO getNoticeDAO(){
		if (dao==null) dao = new NoticeDAO(); 
		return dao;
	}	
	
	/**
	 * 공지사항 목록
	 * @throws Exception 
	 */
	public List noticeList(SearchBean search) throws Exception{
		List result = null;
		
		result = getNoticeDAO().noticeList(search);
		
		return result;		
	}
	
	/**
	 * 공지사항 목록 갯수 가져오기	
	 * @throws Exception 
	 */
	public int noticeTotCnt(SearchBean search) throws Exception{
		int result = 0;
		
		result = getNoticeDAO().noticeTotCnt(search);
		
		return result;
	}
	
	/**
	 * 공지사항 상세보기
	 * @throws Exception 
	 */
	public NoticeBean noticeInfo(int seq) throws Exception{
		NoticeBean notice = null;
		
		notice = getNoticeDAO().noticeInfo(seq);
		
		return notice;
	}
	
	/**
	 * 방문회수 증가
	 * @throws Exception 
	 */
	public int addVisitNo (int seq) throws Exception {
		int result = 0;
		
		result = getNoticeDAO().addVisitNo(seq);
		
		return result;
	}
		
	/** 
	 * 공지사항 삭제	
	 * @throws Exception 
	 */
	public int deleteNotice (int seq) throws Exception {
		int result = 0;
		
		result = getNoticeDAO().deleteNotice(seq);
		
		return result;
	}
	
	/** 
	 * 공지사항 수정
	 * @throws Exception 
	 */
	public int updateNotice (NoticeBean notice) throws Exception {
		int result = 0;
		
		result = getNoticeDAO().updateNotice(notice);
		
		return result;
	}	
	
	/** 
	 * 공지사항 저장
	 * @throws Exception 
	 */
	public int insertNotice (NoticeBean notice) throws Exception {
		int result = 0;
		
		result = getNoticeDAO().insertNotice(notice);
		
		return result;
	}
	
	/** 
	 * 공지사항  첨부파일 삭제	
	 * @throws Exception 
	 */
	public int fileDelete (int seq, int fileseq) throws Exception {
		int result = 0;
		
		result = getNoticeDAO().fileDelete(seq, fileseq);
		
		return result;
	}
	
	/** 
	 * 공지사항  첨부파일 추가
	 * @throws Exception 
	 */
	public int fileinsert (int seq, String userid, FileBean bean) throws Exception {
		int result = 0;
		
		result = getNoticeDAO().fileInsert(seq, userid, bean);
		
		return result;
	}
	
	/**
	 * 공지사항 첨부파일 목록
	 * @throws Exception 
	 */
	public List fileList(int seq) throws Exception{
		List result = null;
		
		result = getNoticeDAO().fileList(seq);
		
		return result;		
	}
	
	/**
	 * 파일명 가져오기	
	 * @throws Exception 
	 */
	public String fileNm(int seq, int fileseq) throws Exception{
		String result = "";
		
		result = getNoticeDAO().fileNm(seq, fileseq);
		
		return result;
	}
}
