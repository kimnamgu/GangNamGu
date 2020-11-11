/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� �������� �μ�/��������� ����
 * ����:
 */
package nexti.ejms.user.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nexti.ejms.common.rootAction;
import nexti.ejms.dept.model.ChrgUnitBean;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.user.form.UsrMgrForm;
import nexti.ejms.user.model.UsrMgrBean;
import nexti.ejms.user.model.UsrMgrManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeptManageDBAction extends rootAction {
	
	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {	

		UsrMgrForm usrMgrForm = (UsrMgrForm)form;		
		String mode = request.getParameter("mode");
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		
		DeptManager deptManager = DeptManager.instance();
		UsrMgrManager usrMgrManager = UsrMgrManager.instance();
		
		//����� ����
		if("usr_d".equals(mode)){
			String id = request.getParameter("id");
			usrMgrManager.deleteUser(id);
			//usrMgrManager.deleteExt(id);

			PrintWriter out = response.getWriter();
			out.write("<script language='javascript'>parent.ifrm2_refresh('D');</script>");			
			return null;
			
		//����� �߰�
		}else if("usr_i".equals(mode)){
			UsrMgrBean uBean = new UsrMgrBean();
			uBean.setUser_id(usrMgrForm.getUser_id());
			uBean.setUser_name(usrMgrForm.getUser_name());
			uBean.setUser_sn(usrMgrForm.getUser_sn());
			
			/*
			//�ֹι�ȣ üũ�� ���ɴ�� ���� �ֱ�
			boolean user_sn_yn = Utils.validateJumin(usrMgrForm.getUser_sn().substring(0,6), usrMgrForm.getUser_sn().substring(6,13));
			if(user_sn_yn){
				int age = Utils.getAge1(usrMgrForm.getUser_sn());
				
				//���ɴ�
				if(age < 10){
					uBean.setAge("0");
				
				}else if(age < 100){
					uBean.setAge(String.valueOf(age).substring(0,1)+"0");
				
				}else{
					uBean.setAge(String.valueOf(age).substring(0,2)+"0");
				}
				
				//����
				if(usrMgrForm.getUser_sn().substring(6,7).equals("1") || usrMgrForm.getUser_sn().substring(6,7).equals("3")){
					uBean.setSex("M");
				}else{
					uBean.setSex("F");
				}
					
			}
			*/
			uBean.setDept_id(usrMgrForm.getDept_id());
			uBean.setDept_name(usrMgrForm.getDept_name());
			uBean.setDept_fullname(deptManager.getDeptInfo(usrMgrForm.getDept_id()).getDept_fullname());
			
			uBean.setPosition_id(usrMgrForm.getPosition_id());
			uBean.setClass_id(usrMgrForm.getClass_id());
			uBean.setClass_name(usrMgrForm.getClass_name());
			uBean.setPosition_name(usrMgrForm.getPosition_name());
			uBean.setEmail(usrMgrForm.getEmail());
			uBean.setTel(usrMgrForm.getTel());
			uBean.setMobile(usrMgrForm.getMobile());
			uBean.setPasswd(usrMgrForm.getPasswd());
			
			if(usrMgrForm.getUse_yn_one() == null || usrMgrForm.getUse_yn_one().equals(""))  usrMgrForm.setUse_yn_one("0");
			if(usrMgrForm.getCon_yn_one() == null || usrMgrForm.getCon_yn_one().equals(""))  usrMgrForm.setCon_yn_one("0");
			
			uBean.setUse_yn_one(usrMgrForm.getUse_yn_one());
			uBean.setCon_yn_one(usrMgrForm.getCon_yn_one());
			uBean.setEa_id(usrMgrForm.getEa_id());
			uBean.setGpki_id(usrMgrForm.getGpki_id());
			uBean.setUsr_rank(usrMgrForm.getUsr_rank());
			uBean.setUptusr(user_id);
			
			int result = 0;
			result = usrMgrManager.insertUser(uBean);						//���� ����� �߰�(USR)
			if ( result > 0 ) {
				usrMgrManager.modifyUsrAgeSex(uBean.getUser_id());		//�����̶� ���ɴ� ������Ʈ(USR_EXT)
			}		

			PrintWriter out = response.getWriter();
			out.write("<script language='javascript'>parent.ifrm2_refresh('I');</script>");			
			return null;

		//����� ����
		}else if("usr_u".equals(mode)){
			UsrMgrBean uBean = new UsrMgrBean();
			uBean.setUser_id(usrMgrForm.getUser_id());
			uBean.setUser_name(usrMgrForm.getUser_name());
			uBean.setUser_sn(usrMgrForm.getUser_sn());
			uBean.setDept_id(usrMgrForm.getDept_id());
			uBean.setDept_name(usrMgrForm.getDept_name());

			uBean.setPosition_name(usrMgrForm.getPosition_name());
			uBean.setClass_id(usrMgrForm.getClass_id());
			uBean.setClass_name(usrMgrForm.getClass_name());
			uBean.setUsr_rank(usrMgrForm.getUsr_rank());
			uBean.setEmail(usrMgrForm.getEmail());
			uBean.setTel(usrMgrForm.getTel());
			
			uBean.setMobile(usrMgrForm.getMobile());
			uBean.setPasswd(usrMgrForm.getPasswd());

			if(usrMgrForm.getUse_yn_one() == null || usrMgrForm.getUse_yn_one().equals(""))  usrMgrForm.setUse_yn_one("N");
			if(usrMgrForm.getCon_yn_one() == null || usrMgrForm.getCon_yn_one().equals(""))  usrMgrForm.setCon_yn_one("N");
			
			uBean.setUse_yn_one(usrMgrForm.getUse_yn_one());
			uBean.setCon_yn_one(usrMgrForm.getCon_yn_one());
			uBean.setEa_id(usrMgrForm.getEa_id());
			uBean.setGpki_id(usrMgrForm.getGpki_id());
			uBean.setUptusr(user_id);
			
			int result = 0;
			result = usrMgrManager.modifyUser(uBean);						//���� ����� ����(USR)
			if ( result > 0 ) {
				usrMgrManager.modifyUsrAgeSex(uBean.getUser_id());		//�����̶� ���ɴ� ������Ʈ(USR_EXT)
			}		

			PrintWriter out = response.getWriter();
			
			if ( request.getParameter("userinfo") == null ) {
				out.write("<script language='javascript'>parent.ifrm2_refresh('U');</script>");
			} else {
				out.write("<script language='javascript'>opener.location.href=opener.location.href;window.close();</script>");
			}
			
			return null;
			
		//�μ� ����
		} else if("dept_d".equals(mode)){
			String id = request.getParameter("id");
			
			usrMgrManager.deleteSub(id);      //�����μ� ����			
			
			PrintWriter out = response.getWriter();
			out.write("<script language='javascript'>parent.ifrm2_refresh();parent.ifrm1_refresh('D');</script>");
			return null;
			
		
		//�μ� �߰�
		}else if("dept_i".equals(mode)){
			//�ߺ��˻�
			boolean isExist = usrMgrManager.existedDept(usrMgrForm.getDept_id());
			
			if(isExist){
				PrintWriter out = response.getWriter();
				out.write("<script language='javascript'>parent.goBack('dept_I');</script>");			
				return null;
			}
			UsrMgrBean uBean = new UsrMgrBean();
			uBean.setDept_id(usrMgrForm.getDept_id());
			uBean.setDept_name(usrMgrForm.getDept_name());
			uBean.setDept_fullname(deptManager.getDeptInfo(usrMgrForm.getUpper_dept_id()).getDept_fullname() + " " + usrMgrForm.getDept_name());
			uBean.setUpper_dept_id(usrMgrForm.getUpper_dept_id());
			uBean.setDept_rank(usrMgrForm.getDept_rank());

			if(usrMgrForm.getUse_yn_one() == null || usrMgrForm.getUse_yn_one().equals(""))  usrMgrForm.setUse_yn_one("0");
			if(usrMgrForm.getCon_yn_one() == null || usrMgrForm.getCon_yn_one().equals(""))  usrMgrForm.setCon_yn_one("0");
			
			uBean.setUse_yn_one(usrMgrForm.getUse_yn_one());
			uBean.setCon_yn_one(usrMgrForm.getCon_yn_one());
			uBean.setDept_tel(usrMgrForm.getDept_tel());
			uBean.setOrggbn(usrMgrForm.getOrggbn());
			uBean.setUptusr(user_id);

			int result = 0;
			result = usrMgrManager.insertDept(uBean);			//���� �μ� �߰�(DEPT)
			if ( result > 0 ) {
				DeptManager dmgr = DeptManager.instance();
				if ( dmgr.existedChrg(uBean.getDept_id()) == false ) {	//���������� �߰�(CHRGUNIT)
					ChrgUnitBean cuBean = new ChrgUnitBean();
					cuBean.setDept_id(uBean.getDept_id());
					cuBean.setChrgunitcd(1);
					cuBean.setChrgunitnm("���");
					cuBean.setOrd(1);
					cuBean.setCrtusrid(uBean.getUptusr());	
					dmgr.insertChrgUnit(cuBean);	
				}		
			}	
			
			PrintWriter out = response.getWriter();
			out.write("<script language='javascript'>parent.ifrm2_refresh();parent.ifrm1_refresh('I');</script>");
			return null;
			
		//�μ� ����
		}else if("dept_u".equals(mode)){
			UsrMgrBean uBean = new UsrMgrBean();
			uBean.setDept_id(usrMgrForm.getDept_id());
			uBean.setDept_name(usrMgrForm.getDept_name());
			uBean.setUpper_dept_id(usrMgrForm.getUpper_dept_id());
			uBean.setUpper_dept_id(usrMgrForm.getUpper_dept_id());
			uBean.setDept_rank(usrMgrForm.getDept_rank());
			uBean.setDept_tel(usrMgrForm.getDept_tel());

			if(usrMgrForm.getUse_yn_one() == null || usrMgrForm.getUse_yn_one().equals(""))  usrMgrForm.setUse_yn_one("N");
			if(usrMgrForm.getCon_yn_one() == null || usrMgrForm.getCon_yn_one().equals(""))  usrMgrForm.setCon_yn_one("N");
			
			uBean.setUse_yn_one(usrMgrForm.getUse_yn_one());
			uBean.setCon_yn_one(usrMgrForm.getCon_yn_one());
			uBean.setBigo(usrMgrForm.getBigo());
			uBean.setOrggbn(usrMgrForm.getOrggbn());
			uBean.setUptusr(user_id);
			
			usrMgrManager.modifyDept(uBean);		//���� �μ� ����(DEPT)
			
			PrintWriter out = response.getWriter();
			out.write("<script language='javascript'>parent.ifrm2_refresh();parent.ifrm1_refresh('U');</script>");
			return null;
			
		//����� �н����� ����Ʈ �н������ ����
		} else if("defaultPasswd".equals(mode)){
			UsrMgrBean uBean = new UsrMgrBean();
			uBean.setUser_id(usrMgrForm.getUser_id());
			uBean.setUptusr(user_id);
			
			usrMgrManager.modifyUsrPass(uBean);
			
			PrintWriter out = response.getWriter();
			out.write("<script language='javascript'>parent.ifrm2_refresh();parent.ifrm1_refresh('P');</script>");
			return null;
		}
		
		return null;
	}
}

