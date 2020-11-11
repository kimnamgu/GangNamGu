/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 바인딩리스너
 * 설명: 세션생성과 소멸시 호출(사용법:session.setAttribute("listener", new NewHttpSessionBindingListener());) 
 */
package nexti.ejms.common;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.research.model.ResearchManager;

public class NewHttpSessionBindingListener implements HttpSessionBindingListener {
	public void valueBound(HttpSessionBindingEvent event) {	
		clearDatabase(event);
	}

	public void valueUnbound(HttpSessionBindingEvent event) {
		clearDatabase(event);
	}
	
	/**
	 * 임시테이블 삭제 
	 *
	 */
	private void clearDatabase(HttpSessionBindingEvent event) {
		
		HttpSession session = event.getSession();
		String sessi = session.getId().split("[!]")[0];
		ServletContext context = event.getSession().getServletContext();
		
		//브라우저간 세션공유문제로 중복로그인 허용처리
		try { if ( !session.isNew() ) return; } catch ( Exception e ) {}
		
		try {
			//취합문서 임시정보 테이블 내용 삭제
			ColldocManager cdmgr = ColldocManager.instance();
			cdmgr.delColldocTempData_TGT_SANC(sessi);
			
			//신청서 임시테이블 삭제
			SinchungManager smgr = SinchungManager.instance();
			smgr.deleteTempAll(sessi, context);
			
			//설문조사 임시테이블 삭제
			ResearchManager rmgr = ResearchManager.instance();
			rmgr.delResearchTempData(sessi, context);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}