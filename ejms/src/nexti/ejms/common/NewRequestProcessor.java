/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: Request 발생시 호출
 * 설명:
 */
package nexti.ejms.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.RequestProcessor;

public class NewRequestProcessor extends RequestProcessor {
	protected boolean processRoles(
			HttpServletRequest request,
			HttpServletResponse response,
			ActionMapping mapping) {
		//Role Checking 없이 무조건 true를 반환 한다. 
		//Checking은 rootAction 및 rootPopupAction에서 한다.
		if ( appInfo.isOutside() == true && !appInfo.getOutsideurl().equals(appInfo.getOutsideweburl()) ) {
			String[] availableURI = {
					"outsideRchResultList.do", "outsideRchList.do", "outsideReqList.do",
					"outsideRchResultView.do", "outsideRchGrpView.do", "outsideRchView.do", "outsideReqView.do",
					"outsideRchAns.do", "outsideReqAns.do", "addrFindList.do", "download.do"
					};
			
			boolean isAvailable = false;
			if ( appInfo.getAp_address().indexOf(request.getServerName()) == -1 ) {
				for ( int i = 0; i < availableURI.length; i++ ) {
					if ( request.getRequestURI().indexOf(availableURI[i]) != -1 ) {
						isAvailable = true;
						break;
					}
				}
			} else {
				isAvailable = true;
			}
			return isAvailable;
		}
		
		return true;
	}
}