/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� manager
 * ����:
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
	 * �������� ���
	 * @throws Exception 
	 */
	public List noticeList(SearchBean search) throws Exception{
		List result = null;
		
		result = getNoticeDAO().noticeList(search);
		
		return result;		
	}
	
	/**
	 * �������� ��� ���� ��������	
	 * @throws Exception 
	 */
	public int noticeTotCnt(SearchBean search) throws Exception{
		int result = 0;
		
		result = getNoticeDAO().noticeTotCnt(search);
		
		return result;
	}
	
	/**
	 * �������� �󼼺���
	 * @throws Exception 
	 */
	public NoticeBean noticeInfo(int seq) throws Exception{
		NoticeBean notice = null;
		
		notice = getNoticeDAO().noticeInfo(seq);
		
		return notice;
	}
	
	/**
	 * �湮ȸ�� ����
	 * @throws Exception 
	 */
	public int addVisitNo (int seq) throws Exception {
		int result = 0;
		
		result = getNoticeDAO().addVisitNo(seq);
		
		return result;
	}
		
	/** 
	 * �������� ����	
	 * @throws Exception 
	 */
	public int deleteNotice (int seq) throws Exception {
		int result = 0;
		
		result = getNoticeDAO().deleteNotice(seq);
		
		return result;
	}
	
	/** 
	 * �������� ����
	 * @throws Exception 
	 */
	public int updateNotice (NoticeBean notice) throws Exception {
		int result = 0;
		
		result = getNoticeDAO().updateNotice(notice);
		
		return result;
	}	
	
	/** 
	 * �������� ����
	 * @throws Exception 
	 */
	public int insertNotice (NoticeBean notice) throws Exception {
		int result = 0;
		
		result = getNoticeDAO().insertNotice(notice);
		
		return result;
	}
	
	/** 
	 * ��������  ÷������ ����	
	 * @throws Exception 
	 */
	public int fileDelete (int seq, int fileseq) throws Exception {
		int result = 0;
		
		result = getNoticeDAO().fileDelete(seq, fileseq);
		
		return result;
	}
	
	/** 
	 * ��������  ÷������ �߰�
	 * @throws Exception 
	 */
	public int fileinsert (int seq, String userid, FileBean bean) throws Exception {
		int result = 0;
		
		result = getNoticeDAO().fileInsert(seq, userid, bean);
		
		return result;
	}
	
	/**
	 * �������� ÷������ ���
	 * @throws Exception 
	 */
	public List fileList(int seq) throws Exception{
		List result = null;
		
		result = getNoticeDAO().fileList(seq);
		
		return result;		
	}
	
	/**
	 * ���ϸ� ��������	
	 * @throws Exception 
	 */
	public String fileNm(int seq, int fileseq) throws Exception{
		String result = "";
		
		result = getNoticeDAO().fileNm(seq, fileseq);
		
		return result;
	}
}
