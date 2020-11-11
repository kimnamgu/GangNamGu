/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���ڰ��� ��ȴ��߼� action
 * ����:
 */
package nexti.ejms.elecAppr.exchange;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import nexti.ejms.ccd.model.CcdManager;
import nexti.ejms.common.rootAction;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.colldoc.model.ColldocBean;
import nexti.ejms.colldoc.model.ColldocManager;

import nexti.ejms.elecAppr.model.ElecApprBean;
import nexti.ejms.elecAppr.model.ElecApprManager;

import nexti.ejms.format.form.FormatForm;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.format.model.InputDeptSearchBoxBean;
import nexti.ejms.formatBook.model.DataBookBean;
import nexti.ejms.formatBook.model.FileBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;
import nexti.ejms.sinchung.model.ReqMstBean;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.user.model.UsrMgrManager;
import nexti.ejms.util.FileBean;
import nexti.ejms.util.FileManager;
import nexti.ejms.util.Utils;

public class ExchangeSendAction extends rootAction {

	public ActionForward doService(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) 
	throws Exception {
		
		HttpSession session = request.getSession();
		String dept_code = Utils.nullToEmptyString((String)session.getAttribute("dept_code"));
		String dept_name = Utils.nullToEmptyString((String)session.getAttribute("dept_name"));
		String user_id = Utils.nullToEmptyString((String)session.getAttribute("user_id"));
		String user_name = Utils.nullToEmptyString((String)session.getAttribute("user_name"));
		
		if( "001".equals(Utils.nullToEmptyString((String)session.getAttribute("isSysMgr"))) ) {
			String originuserid = Utils.nullToEmptyString((String)request.getParameter("originuserid"));
			if ( !"".equals(originuserid) ) session.setAttribute("originuserid", originuserid);
			originuserid = Utils.nullToEmptyString((String)session.getAttribute("originuserid"));
			UserBean originUserBean = UserManager.instance().getUserInfo(originuserid);
			if ( originUserBean != null ) {
				user_id = originUserBean.getUser_id();
				user_name = originUserBean.getUser_name();
				dept_code = originUserBean.getDept_id();
				dept_name = UserManager.instance().getDeptName(user_id);
			}
		}
		
		ElecApprManager eamgr = ElecApprManager.instance();
		
		FormatForm fform = (FormatForm)form;
		
		String formtitle = Utils.nullToEmptyString(fform.getFormtitle());
		String formcomment = Utils.nullToEmptyString(fform.getFormcomment());
		String formhtml = Utils.nullToEmptyString(fform.getFormhtml());
		String attach = Utils.nullToEmptyString(fform.getAttach());
		String ea_id = fform.getEa_id();
		int type = fform.getType();
		int sysdocno = fform.getSysdocno();
		int reqformno = fform.getReqformno();
		int reqseq = fform.getReqseq();
		
		ExchangeVO exchangeVO = new ExchangeVO();

		/******************************
		 * 1. �۽��� ���� ���� 
		 ******************************/
		exchangeVO.setSenderId(ea_id);
		exchangeVO.setSenderName(user_name);
		exchangeVO.setSenderDeptName(dept_name);
		exchangeVO.setReceiverId(exchangeVO.getSenderId());
		exchangeVO.setReceiverName(exchangeVO.getSenderName());
		exchangeVO.setReceiverDeptName(exchangeVO.getSenderDeptName());
		
		exchangeVO.setTitle(formtitle);
		exchangeVO.setBody(formcomment);
		exchangeVO.setAdminNo(eamgr.getNextSancSeq());
			
		/******************************
		 * 2. ����� ���� ���ε� ���� ���� 
		 ******************************/
		//��ȳ��� ���� ÷�η� ����
		String logicalFilename = null;
		String fileExt = null;
		String uploadForder = null;
		String fileContent = null;
		FileBean fileBean = null;
		ArrayList attachFileList=null;
		
		if ("true".equals(attach)) {	//���ӳ����� ���� ��
			ColldocManager cmgr = ColldocManager.instance();
			FormatBookManager fbmgr = FormatBookManager.instance();
			SinchungManager scmgr = SinchungManager.instance();
			
			logicalFilename = formtitle;
			fileExt = "html";
			uploadForder =
				ResourceBundle.getBundle(ExchangeCmd.RESOURCE_FILE_NAME).getString("exchange_send_attachfile_dir")+File.separator;
			fileContent = eamgr.makeElecApprAttachFileAdjustTag(formhtml);
			fileBean = FileManager.doAttachFileMake(fileContent, fileExt, uploadForder, getServlet().getServletContext(), "CONTENT");
			
			if (fileBean != null) {
				ExchangeFileVO file=new ExchangeFileVO();
				file.setSaveDir(request.getSession().getServletContext().getRealPath(uploadForder));
				file.setFileStatus("I");
				file.setLogclFileName(logicalFilename + "." + fileExt);
				file.setPhyclFileName(fileBean.getFilenm());
				file.setFileSize(fileBean.getFilesize());
				file.setFileExt(fileBean.getExt());
				
				if (attachFileList == null) {
					attachFileList=new ArrayList();
				}
				attachFileList.add(file);
			}
			
			if (type != 2) {	//�ڷ�����
			
				List formatList = cmgr.getListFormat(sysdocno);
				for (int i = 0; i < formatList.size(); i++) {
					int formseq = ((ColldocBean)formatList.get(i)).getFormseq();
					String formName = ((ColldocBean)formatList.get(i)).getFormtitle();
				
					//������ �������� ����Ʈ ��������
					List listFileBook = fbmgr.getFileBookFrm(sysdocno, formseq);
					if (listFileBook != null && listFileBook.size() != 0) {
						for (int j = 0; j < listFileBook.size(); j++) {
							FileBookBean bean = (FileBookBean)listFileBook.get(j);
							String fExt = bean.getExt();
							String fPhyName = bean.getFilenm();
							String fileNo = new DecimalFormat("00").format(j + 1);
							String fLogName = "";
							if( "����3780000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1) {
								fLogName = bean.getOriginfilenm();
							}
							fLogName = "[" + formName + "_������]" + fileNo + "_" + bean.getOriginfilenm();
							fileBean = FileManager.doAttachFileMake(fPhyName, fExt, uploadForder, getServlet().getServletContext(), "FILE");
							
							if (fileBean != null) {
								ExchangeFileVO file=new ExchangeFileVO();
								file.setSaveDir(request.getSession().getServletContext().getRealPath(uploadForder));
								file.setFileStatus("I");
								file.setLogclFileName(fLogName);
								file.setPhyclFileName(fileBean.getFilenm());
								file.setFileSize(fileBean.getFilesize());
								file.setFileExt(fileBean.getExt());
								
								if (attachFileList == null) {
									attachFileList=new ArrayList();
								}
								//�������� ������۽� ÷�ξ���(12.01.09)
								//attachFileList.add(file);
							}
						}
					}
					
					InputDeptSearchBoxBean idsbbean = new InputDeptSearchBoxBean();
					String[][] deptInfo = FormatManager.instance().getInputDeptInfo(sysdocno, dept_code);
					int depth = CommTreatManager.instance().getTargetDeptLevel(sysdocno, dept_code);
					int idx = 0;
					if ( depth < 1 ) {
						ColldocBean cdbean = ColldocManager.instance().getColldoc(sysdocno);
						user_id = cdbean.getChrgusrcd();
						dept_code = cdbean.getColdeptcd();
						deptInfo = FormatManager.instance().getInputDeptInfo(sysdocno, dept_code);
						depth = CommTreatManager.instance().getTargetDeptLevel(sysdocno, dept_code);
					}
					if ( depth > idx ) idsbbean.setSch_deptcd1(deptInfo[idx++][0]);
					if ( depth > idx ) idsbbean.setSch_deptcd2(deptInfo[idx++][0]);
					if ( depth > idx ) idsbbean.setSch_deptcd3(deptInfo[idx++][0]);
					if ( depth > idx ) idsbbean.setSch_deptcd4(deptInfo[idx++][0]);
					if ( depth > idx ) idsbbean.setSch_deptcd5(deptInfo[idx++][0]);
					if ( depth > idx ) idsbbean.setSch_deptcd6(deptInfo[idx++][0]);
					idsbbean.setSch_inputusrid(user_id);
					
					//DataBookFrm ������ ��������
					List dataList = fbmgr.getFormDataList(sysdocno, formseq, idsbbean, "1", true);
					if (dataList != null && dataList.size() != 0) {
						for (int j = 0; j < dataList.size(); j++) {
							DataBookBean bean = (DataBookBean)dataList.get(j);
							String fExt = bean.getExt();
							String fPhyName = bean.getFilenm();
							String fileNo = new DecimalFormat("00").format(j + 1);
							//String fLogName = "[" + formName + "_��������]" + fileNo + "_" + bean.getFormtitle() + "_" + bean.getOriginfilenm();
							String fLogName = ""; 
							if( "����5100000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1) {
								fLogName = bean.getOriginfilenm();
							}
							fLogName = "[" + formName + "_��������]" + fileNo + "_" + bean.getOriginfilenm();
							
							fileBean = FileManager.doAttachFileMake(fPhyName, fExt, uploadForder, request.getSession().getServletContext(), "FILE");
							
							if (fileBean != null) {
								ExchangeFileVO file=new ExchangeFileVO();
								file.setSaveDir(request.getSession().getServletContext().getRealPath(uploadForder));
								file.setFileStatus("I");
								file.setLogclFileName(fLogName);
								file.setPhyclFileName(fileBean.getFilenm());
								file.setFileSize(fileBean.getFilesize());
								file.setFileExt(fileBean.getExt());
								
								if (attachFileList == null) {
									attachFileList=new ArrayList();
								}
								attachFileList.add(file);
							}
						}
					}
					
					//DataBookFrm �������������� ��������
					String compFilenm = fbmgr.getDataBookCompView(sysdocno, formseq).getFilenm();
					String compOrgFilenm = fbmgr.getDataBookCompView(sysdocno, formseq).getOriginfilenm();
					String compExt = fbmgr.getDataBookCompView(sysdocno, formseq).getExt();
					if (compFilenm != null && !compFilenm.trim().equals("")) {
						String fExt = compExt;
						String fPhyName = compFilenm;
						String fLogName = "";
						if( "����5100000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1) {
							fLogName = compOrgFilenm;
						}
						fLogName = "[" + formName + "_�����ڷ�]" + compOrgFilenm;
						
						fileBean = FileManager.doAttachFileMake(fPhyName, fExt, uploadForder, request.getSession().getServletContext(), "FILE");
						
						if (fileBean != null) {
							ExchangeFileVO file=new ExchangeFileVO();
							file.setSaveDir(request.getSession().getServletContext().getRealPath(uploadForder));
							file.setFileStatus("I");
							file.setLogclFileName(fLogName);
							file.setPhyclFileName(fileBean.getFilenm());
							file.setFileSize(fileBean.getFilesize());
							file.setFileExt(fileBean.getExt());
							
							if (attachFileList == null) {
								attachFileList=new ArrayList();
							}
							attachFileList.add(file);
						}
					}
				}
				
			} else {	//��û��
				
				ReqMstBean mstBean = scmgr.reqDataInfo(reqformno, reqseq);
				
				if ( mstBean != null ) {
					//÷������ ��������
					String compFilenm = mstBean.getFilenm();
					String compOrgFilenm = mstBean.getOriginfilenm();
					String compExt = mstBean.getExt();
					String sinchungName = scmgr.reqFormInfo(reqformno).getTitle();
					if (compFilenm != null && !compFilenm.trim().equals("")) {
						String fExt = compExt;
						String fPhyName = compFilenm;
						String fLogName = "";
						if( "����5100000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1) {
							fLogName = compOrgFilenm;
						}
						fLogName = "[" + sinchungName + "_÷������]" + compOrgFilenm;
						
						fileBean = FileManager.doAttachFileMake(fPhyName, fExt, uploadForder, request.getSession().getServletContext(), "FILE");
						
						if (fileBean != null) {
							ExchangeFileVO file=new ExchangeFileVO();
							file.setSaveDir(request.getSession().getServletContext().getRealPath(uploadForder));
							file.setFileStatus("I");
							file.setLogclFileName(fLogName);
							file.setPhyclFileName(fileBean.getFilenm());
							file.setFileSize(fileBean.getFilesize());
							file.setFileExt(fileBean.getExt());
							
							if (attachFileList == null) {
								attachFileList=new ArrayList();
							}
							attachFileList.add(file);
						}
					}
				}				
			}
		}
		exchangeVO.setAtchFileList(attachFileList);
		
		StringBuffer script = new StringBuffer();
		int limitSancAttachFileSize = Integer.parseInt("0" + Utils.nullToEmptyString(CcdManager.instance().getCcd_SubName("024", "001")));
		double sancAttachFileSize = FileManager.doCheckFileSize(request.getSession().getServletContext().getRealPath(uploadForder));
		sancAttachFileSize = sancAttachFileSize / 1024 / 1024;
		
		if ( limitSancAttachFileSize >= sancAttachFileSize ) {
			/******************************
			 * 3. ���ڰ��� ��ȹ� ���� 
			 ******************************/
			int exchangeSeq = new Exchange(exchangeVO).send();
			exchangeSeq++;	//Warnning ���ֱ� ��
			
			/******************************
			 * 4. ���� ���� ���� 
			 ******************************/
			
			//���ڰ�����̵� ����
			UsrMgrManager.instance().setEaId(user_id, ea_id);
			ElecApprBean eaBean = new ElecApprBean();
			int result = 0;
			
			if (type != 2) {	//�ڷ�����
				eaBean.setSysdocno(sysdocno);
				eaBean.setTgtdeptcd(dept_code);
				eaBean.setInputusrid(user_id);
				eaBean.setSeq(exchangeVO.getAdminNo());
				eaBean.setSancyn("0");
				eaBean.setCrtusrid(user_id);
				eaBean.setUptusrid(user_id);
				eamgr.insertColldocSancInfo(eaBean);
			} else {			//��û��
				eaBean.setReqformno(reqformno);
				eaBean.setReqseq(reqseq);
				eaBean.setSeq(exchangeVO.getAdminNo());
				eaBean.setSancyn("0");
				eaBean.setCrtusrid(user_id);
				eaBean.setUptusrid(user_id);
				result = eamgr.insertRequestSancInfo(eaBean);
				
				if ( result > 0 ) {
					SinchungManager smgr = SinchungManager.instance();
					smgr.procJupsu("3", reqformno, reqseq);
				}
			}
			
			script.append("<script> \n");
			script.append("alert('��ȴ��� ���۵Ǿ����ϴ�.\\n���ڰ����� ������������(��ȴ��)����\\n�������Ͻñ� �ٶ��ϴ�.'); \n");
			script.append("try { window.dialogArguments.opener.location.href = window.dialogArguments.opener.location.href; } \n");
			script.append("catch (e) { window.dialogArguments.location.href = window.dialogArguments.location.href; } \n");
			script.append("window.close(); window.dialogArguments.close(); \n");
			script.append("</script> \n");
		} else {
			script.append("<script> \n");
			script.append("alert('÷������ �뷮�� " + (limitSancAttachFileSize - 1) + "MB�� �ʰ��Ͽ�\\n��ȴ�� ������ �����Ǿ����ϴ�\\n�����ڿ��� �뷮 ������ �����ϼ���'); \n");
			script.append("try { window.dialogArguments.opener.location.href = window.dialogArguments.opener.location.href; } \n");
			script.append("catch (e) { window.dialogArguments.location.href = window.dialogArguments.location.href; } \n");
			script.append("window.close(); window.dialogArguments.close(); \n");
			script.append("</script> \n");
		}
		
		/******************************
		 * 5. �������ϻ��� �� �Ϸ� 
		 ******************************/		
		if ("true".equals(attach)) {	//���ӳ����� ���� ��
			String delFile = uploadForder;
			if ( delFile != null && delFile.trim().equals("") == false) {
				FileManager.doFileDelete(request.getSession().getServletContext().getRealPath(delFile));
			}
		}
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(script.toString());
		response.flushBuffer();

		return null; 
	} 
}
