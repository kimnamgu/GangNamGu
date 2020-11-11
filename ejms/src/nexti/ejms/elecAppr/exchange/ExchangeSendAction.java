/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 전자결재 기안대기발송 action
 * 설명:
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
		 * 1. 송신자 정보 세팅 
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
		 * 2. 사용자 임의 업로드 파일 설정 
		 ******************************/
		//기안내용 파일 첨부로 설정
		String logicalFilename = null;
		String fileExt = null;
		String uploadForder = null;
		String fileContent = null;
		FileBean fileBean = null;
		ArrayList attachFileList=null;
		
		if ("true".equals(attach)) {	//붙임내용이 있을 때
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
			
			if (type != 2) {	//자료취합
			
				List formatList = cmgr.getListFormat(sysdocno);
				for (int i = 0; i < formatList.size(); i++) {
					int formseq = ((ColldocBean)formatList.get(i)).getFormseq();
					String formName = ((ColldocBean)formatList.get(i)).getFormtitle();
				
					//제본형 샘플파일 리스트 가져오기
					List listFileBook = fbmgr.getFileBookFrm(sysdocno, formseq);
					if (listFileBook != null && listFileBook.size() != 0) {
						for (int j = 0; j < listFileBook.size(); j++) {
							FileBookBean bean = (FileBookBean)listFileBook.get(j);
							String fExt = bean.getExt();
							String fPhyName = bean.getFilenm();
							String fileNo = new DecimalFormat("00").format(j + 1);
							String fLogName = "";
							if( "성남3780000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1) {
								fLogName = bean.getOriginfilenm();
							}
							fLogName = "[" + formName + "_제출양식]" + fileNo + "_" + bean.getOriginfilenm();
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
								//제출양식은 기안전송시 첨부안함(12.01.09)
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
					
					//DataBookFrm 데이터 가져오기
					List dataList = fbmgr.getFormDataList(sysdocno, formseq, idsbbean, "1", true);
					if (dataList != null && dataList.size() != 0) {
						for (int j = 0; j < dataList.size(); j++) {
							DataBookBean bean = (DataBookBean)dataList.get(j);
							String fExt = bean.getExt();
							String fPhyName = bean.getFilenm();
							String fileNo = new DecimalFormat("00").format(j + 1);
							//String fLogName = "[" + formName + "_제출파일]" + fileNo + "_" + bean.getFormtitle() + "_" + bean.getOriginfilenm();
							String fLogName = ""; 
							if( "성남5100000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1) {
								fLogName = bean.getOriginfilenm();
							}
							fLogName = "[" + formName + "_제출파일]" + fileNo + "_" + bean.getOriginfilenm();
							
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
					
					//DataBookFrm 최종생성데이터 가져오기
					String compFilenm = fbmgr.getDataBookCompView(sysdocno, formseq).getFilenm();
					String compOrgFilenm = fbmgr.getDataBookCompView(sysdocno, formseq).getOriginfilenm();
					String compExt = fbmgr.getDataBookCompView(sysdocno, formseq).getExt();
					if (compFilenm != null && !compFilenm.trim().equals("")) {
						String fExt = compExt;
						String fPhyName = compFilenm;
						String fLogName = "";
						if( "성남5100000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1) {
							fLogName = compOrgFilenm;
						}
						fLogName = "[" + formName + "_최종자료]" + compOrgFilenm;
						
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
				
			} else {	//신청서
				
				ReqMstBean mstBean = scmgr.reqDataInfo(reqformno, reqseq);
				
				if ( mstBean != null ) {
					//첨부파일 가져오기
					String compFilenm = mstBean.getFilenm();
					String compOrgFilenm = mstBean.getOriginfilenm();
					String compExt = mstBean.getExt();
					String sinchungName = scmgr.reqFormInfo(reqformno).getTitle();
					if (compFilenm != null && !compFilenm.trim().equals("")) {
						String fExt = compExt;
						String fPhyName = compFilenm;
						String fLogName = "";
						if( "성남5100000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1) {
							fLogName = compOrgFilenm;
						}
						fLogName = "[" + sinchungName + "_첨부파일]" + compOrgFilenm;
						
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
			 * 3. 전자결재 기안문 전송 
			 ******************************/
			int exchangeSeq = new Exchange(exchangeVO).send();
			exchangeSeq++;	//Warnning 없애기 용
			
			/******************************
			 * 4. 전송 정보 저장 
			 ******************************/
			
			//전자결재아이디 저장
			UsrMgrManager.instance().setEaId(user_id, ea_id);
			ElecApprBean eaBean = new ElecApprBean();
			int result = 0;
			
			if (type != 2) {	//자료취합
				eaBean.setSysdocno(sysdocno);
				eaBean.setTgtdeptcd(dept_code);
				eaBean.setInputusrid(user_id);
				eaBean.setSeq(exchangeVO.getAdminNo());
				eaBean.setSancyn("0");
				eaBean.setCrtusrid(user_id);
				eaBean.setUptusrid(user_id);
				eamgr.insertColldocSancInfo(eaBean);
			} else {			//신청서
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
			script.append("alert('기안대기로 전송되었습니다.\\n전자결재의 행정정보연계(기안대기)에서\\n결재상신하시기 바랍니다.'); \n");
			script.append("try { window.dialogArguments.opener.location.href = window.dialogArguments.opener.location.href; } \n");
			script.append("catch (e) { window.dialogArguments.location.href = window.dialogArguments.location.href; } \n");
			script.append("window.close(); window.dialogArguments.close(); \n");
			script.append("</script> \n");
		} else {
			script.append("<script> \n");
			script.append("alert('첨부파일 용량이 " + (limitSancAttachFileSize - 1) + "MB를 초과하여\\n기안대기 전송이 중지되었습니다\\n관리자에게 용량 제한을 문의하세요'); \n");
			script.append("try { window.dialogArguments.opener.location.href = window.dialogArguments.opener.location.href; } \n");
			script.append("catch (e) { window.dialogArguments.location.href = window.dialogArguments.location.href; } \n");
			script.append("window.close(); window.dialogArguments.close(); \n");
			script.append("</script> \n");
		}
		
		/******************************
		 * 5. 생성파일삭제 및 완료 
		 ******************************/		
		if ("true".equals(attach)) {	//붙임내용이 있을 때
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
