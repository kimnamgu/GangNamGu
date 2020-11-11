/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 외부망 설문조사 응답 저장 action
 * 설명:
 */
package nexti.ejms.outside.action;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import nexti.ejms.outside.model.OutsideManager;
import nexti.ejms.research.model.ResearchAnsBean;
import nexti.ejms.util.Utils;

public class OutsideRchAnsAction extends Action{
	
	
	/*SQL INJECTION 방지를 위한 SECURE CODING */
    //select, delete update, insert 등 기존 명령어와 알파벳, 숫자를 제외한 다른 문자들을 검출하는 정규식 설정
    private final static String UNSECURED_CHAR_REGULAR_EXPRESSION = "[@#=&*()?\'\"+/-]|select|delete|update|insert|create|alter|drop";
 
    private Pattern unsecuredCharPattern;
 
    //정규식을 초기화한다.
    public void initlalize()
    {
        unsecuredCharPattern = Pattern.compile(UNSECURED_CHAR_REGULAR_EXPRESSION,Pattern.CASE_INSENSITIVE);
    }
 
    //입력값을 정규식을 이용해 필터링한 후 의심되는 부분을 없앤다.
    private String makeSecureString(final String str)
    {
    	//String return_str = SpecialChars.matcher(str).replaceAll("");
    	initlalize();
        Matcher matcher = unsecuredCharPattern.matcher(str);
        return matcher.replaceAll("");
    }


	
	private static Logger logger = Logger.getLogger(OutsideRchAnsAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			//logger.info("OutsideRchAnsAction.execute START");
			String uid = Utils.nullToEmptyString((String)request.getParameter("uid"));
			//logger.info("uid["+uid+"]");
			int result = 0;
			List ansList = null;
			String msg = "";
			
			OutsideManager manager = OutsideManager.instance();
			String formtype = "";
			int rchno =  0;
			//logger.info("rchno["+request.getParameter("rchno")+"]");
			if(!Utils.isNull(request.getParameter("rchno"))){
				rchno = Integer.parseInt(request.getParameter("rchno").toString());
			}
			int listcnt = 0;
			//logger.info("listcnt["+request.getParameter("listcnt")+"]");
			if(!Utils.isNull(request.getParameter("listcnt"))){
				listcnt = Integer.parseInt(request.getParameter("listcnt").toString());
			}
			ansList = new ArrayList();
			for(int i=0; i<listcnt; i++){
				ResearchAnsBean bean = new ResearchAnsBean();
				int elistcnt = 0;
				String anscont = "";
				String other = "";
				bean.setFormseq(Integer.parseInt(request.getParameter("formseq"+i)));
				formtype = request.getParameter("formtype"+i).toString();
				
				if(formtype.equals("01")||formtype.equals("02")){
					elistcnt = Integer.parseInt(request.getParameter("elistcnt"+i));
					for(int j=0; j<elistcnt ; j++ ){
						if(formtype.equals("01")){
							if(request.getParameter("anscont"+i)!= null){
								anscont = request.getParameter("anscont"+i);
								other = request.getParameter("other"+i);
							}
						}else{
							if(request.getParameter(("anscont"+i)+"_"+j)!=null){
								if("".equals(anscont)){
									anscont = request.getParameter(("anscont"+i)+"_"+j);
								}else{
									anscont = anscont + "," + request.getParameter(("anscont"+i)+"_"+j);
								}
							}
							other = request.getParameter("other"+i);
						}
						bean.setAnscont(anscont);
						bean.setOther(other);
					}
				}else{
					System.out.println("특수문자가 제거된 데이터 확인 : " + makeSecureString(request.getParameter("anscont"+i+"0304")));
					bean.setAnscont(makeSecureString(request.getParameter("anscont"+i+"0304")));
				}

				ansList.add(bean);
			}
			//logger.info("ansList["+ansList.size()+"]");
			//사용자 취합부서결재, 제출부서, 제출그룹 임시 테이블 데이터 정상문서 데이터로 적용 
			result = manager.rchAnsSave(ansList, rchno, uid, "");
			//logger.info("result["+result+"]");
			if(result>0){
                //강남구청 설문조사 상세메시지 출력(JHkim, 14.01.06)
                if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
                    msg = "<br>답변이 등록 되었습니다.";
                }else{
                    //msg = "<img src=\"/images/research_comp.gif\" alt=\"답변이 등록 되었습니다\">";
                	 msg = "<br>답변이 등록 되었습니다.";
                }
				
				Cookie cookie = new Cookie("cookie_ejms_outside_rch", URLEncoder.encode("outside_rch"+rchno,"EUC-KR"));
				cookie.setMaxAge(60*60*24*30);    //30일동안 쿠키가 살아 있도록 셋팅
				response.addCookie(cookie);
				
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("info.msg",msg));
				saveMessages(request,messages);
			}
			
			request.setAttribute("result", new Integer(result));
		}catch(Exception e){
			//logger.info("OutsideRchAnsAction.execute Exception");
			e.printStackTrace();
		}
		
		//logger.info("OutsideRchAnsAction.execute END");
		return mapping.findForward("hidden");
	}
}
