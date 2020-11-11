/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트 등록 action
 * 설명:
 */
package nexti.ejms.agent.action;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.common.rootAction;
import nexti.ejms.agent.form.SystemAgentForm;
import nexti.ejms.agent.model.SystemAgentManager;
import nexti.ejms.agent.model.SystemAgentRuntimeBean;

public class SystemAgentRuntimeWriteAction extends rootAction {
	private static Logger logger = Logger.getLogger(SystemAgentRuntimeWriteAction.class);

	public ActionForward doService(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) 
		throws Exception {
		
		SystemAgentForm saForm = (SystemAgentForm)form;

		SystemAgentRuntimeBean arBean = new SystemAgentRuntimeBean();
		
		try {
			PropertyUtils.copyProperties(arBean, saForm);
			if ("001".equals(saForm.getP_type())) {
				arBean.setP_t1(saForm.getP_t1_1()+saForm.getP_t1_2());
			} else if ("002".equals(saForm.getP_type())) {
				arBean.setP_t2(saForm.getP_t2_1()+saForm.getP_t2_2()+saForm.getP_t2_3());
			} else if ("003".equals(saForm.getP_type())) {
				arBean.setP_t3(saForm.getP_t3_1()+saForm.getP_t3_2()+saForm.getP_t3_3());
			} else if ("004".equals(saForm.getP_type())) {
				arBean.setP_t4(saForm.getP_t4_1()+saForm.getP_t4_2()+saForm.getP_t4_3()+saForm.getP_t4_4());
			}
			
			SystemAgentManager saManager =  SystemAgentManager.instance();
			if ("m".equals(saForm.getMode())) {	//수정이면
				saManager.modifySystemAgentRuntime(arBean);
			} else {	//등록이면
				saManager.insertSystemAgentRuntime(arBean);
			}
			
		} catch (IllegalAccessException e) {
			logger.error("error", e);
			throw e;
		} catch (InvocationTargetException e) {
			logger.error("error", e);
			throw e;
		} catch (NoSuchMethodException e) {
			logger.error("error", e);
			throw e;
		}
		
		return mapping.findForward("write_ok");
	}

}
