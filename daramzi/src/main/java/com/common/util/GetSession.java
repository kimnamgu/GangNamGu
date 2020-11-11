package com.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetSession {

	
    //폴더삭제시 폴더에 파일이 있을경우 안지워지기 때문에 재귀적으로 처리
	public static String GetSessionId(HttpServletRequest request) {

    	// 세션을 가져온다. (가져올 세션이 없다면 생성한다.)
    	HttpSession session = request.getSession(true);
    	AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
    	
    	return authInfo.getId();
	}
}
