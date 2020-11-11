/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���ڰ��� manager
 * ����:
 */
package nexti.ejms.elecAppr.model;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.List;

import javax.servlet.ServletContext;

//import org.apache.log4j.Logger;
import org.apache.commons.beanutils.BeanUtils;

import nexti.ejms.ccd.model.CcdBean;
import nexti.ejms.ccd.model.CcdManager;
import nexti.ejms.colldoc.model.ColldocBean;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.formatLine.model.FormatLineBean;
import nexti.ejms.formatLine.model.FormatLineManager;
import nexti.ejms.formatFixed.model.FormatFixedBean;
import nexti.ejms.formatFixed.model.FormatFixedManager;
import nexti.ejms.format.model.InputDeptSearchBoxBean;
import nexti.ejms.formatBook.model.FormatBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;
import nexti.ejms.formatBook.model.DataBookBean;
import nexti.ejms.sinchung.form.DataForm;
import nexti.ejms.sinchung.model.ArticleBean;
import nexti.ejms.sinchung.model.ReqMstBean;
import nexti.ejms.sinchung.model.ReqSubBean;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.util.Utils;
import nexti.ejms.util.commfunction;

public class ElecApprManager {
	
	//private static Logger logger = Logger.getLogger(ElecApprManager.class);
	
	private static ElecApprManager instance = null;
	private static ElecApprDAO dao = null;
	
	private ElecApprManager() {
		
	}
	
	public static ElecApprManager instance() {
		
		if(instance == null)
			instance = new ElecApprManager();
		return instance;
	}

	private ElecApprDAO getElecApprDAO() {
		
		if(dao == null)
			dao = new ElecApprDAO(); 
		return dao;
	}
	
	/**
	 * �������� ��������
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public ElecApprBean selectColldocSancInfo(int sysdocno, String tgtdeptcd, String inputusrid) throws Exception {
		return getElecApprDAO().selectColldocSancInfo(sysdocno, tgtdeptcd, inputusrid);
	}
	
	/**
	 * �������� ��������
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public ElecApprBean selectColldocSancInfo(int seq) throws Exception {
		return getElecApprDAO().selectColldocSancInfo(seq);
	}
	
	/**
	 * �������� �Է�
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public int insertColldocSancInfo(ElecApprBean eaBean) throws Exception {
		return getElecApprDAO().insertColldocSancInfo(eaBean);
	}
	
	/**
	 * �������� ����
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public int updateColldocSancInfo(ElecApprBean eaBean) throws Exception {
		return getElecApprDAO().updateColldocSancInfo(eaBean);
	}
	
	/**
	 * �������� ��������
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public ElecApprBean selectRequestSancInfo(int reqformno, int reqseq) throws Exception {
		return getElecApprDAO().selectRequestSancInfo(reqformno, reqseq);
	}
	
	/**
	 * �������� ��������
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public ElecApprBean selectRequestSancInfo(int seq) throws Exception {
		return getElecApprDAO().selectRequestSancInfo(seq);
	}
	
	/**
	 * �������� �Է�
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public int insertRequestSancInfo(ElecApprBean eaBean) throws Exception {
		return getElecApprDAO().insertRequestSancInfo(eaBean);
	}
	
	/**
	 * �������� ����
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public int updateRequestSancInfo(ElecApprBean eaBean) throws Exception {
		return getElecApprDAO().updateRequestSancInfo(eaBean);
	}
	
	/**
	 * ������� �ִ��� üũ
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public boolean isSancneed() throws Exception {
		return getElecApprDAO().isSancneed();
	}
	
	/**
	 * ���չ��� ������� �ִ��� üũ
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public boolean isColldocSancneed(int seq) throws Exception {
		return getElecApprDAO().isColldocSancneed(seq);
	}
	
	/**
	 * ��û�� ������� �ִ��� üũ
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public boolean isRequestSancneed(int seq) throws Exception {
		return getElecApprDAO().isRequestSancneed(seq);
	}
	
	/*
	 * ���ڰ����ȹ��� ���� ����� : ��������
	 */
	public String makeElecApprContent(String formtitle) throws Exception {
		
		StringBuffer result = new StringBuffer();
		
		result.append("\t1. \n");
		result.append("\t2. [" + formtitle + "] ������ �����Ͽ� ���Ӱ� ���� �����մϴ�.\n");
		result.append("\n");
		result.append("\t����. �����ڷ� �� 1��. ��.\n");
		
		if ( "����3190000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
			if ( formtitle.indexOf("��������") != -1 || formtitle.indexOf("���� ����") != -1 ) {
				result.delete(0, result.capacity());
				result.append("\t�ۡۡۡۡۡۡۡۡۡ� ����� ���Ͽ� �������ڼ��� �߱��� ��û�ϴ�, ó���Ͽ� �ֽñ� �ٶ��ϴ�\n");
				result.append("\n");
				result.append("\t���� : �������ڼ��� ��û��. ��.\n");
			}
		}
		
		return result.toString(); 
	}
	
	public String makeElecApprAttachFile(int sysdocno, int reqseq, InputDeptSearchBoxBean idsbbean, int type, ServletContext sContext) throws Exception {
		
		StringBuffer result = new StringBuffer();
		
		if (type != 2) {	//���չ���
		
			ColldocManager cmgr = ColldocManager.instance();
			List formatList = cmgr.getListFormat(sysdocno);
			
			for (int i = 0; i < formatList.size(); i++) {
				String formtitle = ((ColldocBean)formatList.get(i)).getFormtitle();
				String formkind = ((ColldocBean)formatList.get(i)).getFormkind();
				int formseq = ((ColldocBean)formatList.get(i)).getFormseq();
				
				if ("01".equals(formkind)) {
				
					FormatLineManager flmgr = FormatLineManager.instance();
					//���߰��� ��� ���� ��������
					FormatLineBean flbean = flmgr.getFormatLineDataView(sysdocno, formseq, idsbbean, false, false);		
					//���߰��� ��Ŀ� ���� ������ ��������
					flbean.setListform(flmgr.getFormDataList(sysdocno, formseq, idsbbean, false, false, true));
					//�Է¾�Ŀ��� �߰� ��ư(�÷�)����
					StringBuffer formbodyhtml = new StringBuffer(flmgr.getFormatFormView(sysdocno, formseq).getFormbodyhtml());
					int findIndex1 = formbodyhtml.length();
					int findIndex2 = 0;
					while((findIndex2 = formbodyhtml.lastIndexOf("</tr>", findIndex1)) != -1) {
						findIndex1 = formbodyhtml.lastIndexOf("<td", findIndex2);
						int isEmpty = formbodyhtml.lastIndexOf("border:1 solid", findIndex2);
						if(isEmpty < findIndex1) {
							formbodyhtml.delete(findIndex1, findIndex2);
						}
					}
					flbean.setFormbodyhtml_ext(formbodyhtml.toString());
					
					//��ĸ����
					result.append((i + 1) + ". " + formtitle + "<br>\r\n");
					result.append(flbean.getFormheaderhtml());
					List listform = flbean.getListform();
					if (listform == null) {
						result.append(flbean.getFormbodyhtml_ext());
					} else {
						for (int j = 0; j < listform.size(); j++) {
							result.append(((FormatLineBean)listform.get(j)).getFormbodyhtml());
						}
					}
					result.append(flbean.getFormtailhtml() + "<p>\r\n");
				
				} else if ("02".equals(formkind)) {
					
					FormatFixedManager ffmgr = FormatFixedManager.instance();
					//��������� ��� ���� ��������
					FormatFixedBean ffbean = ffmgr.getFormatFormView(sysdocno, formseq, "");
					//��������� ��Ŀ� ���� ������ ��������
					ffbean.setSysdocno(sysdocno);
					ffbean.setFormseq(formseq);
					ffbean.setSch_deptcd1(idsbbean.getSch_deptcd1());
					ffbean.setSch_deptcd2(idsbbean.getSch_deptcd2());
					ffbean.setSch_deptcd3(idsbbean.getSch_deptcd3());
					ffbean.setSch_deptcd4(idsbbean.getSch_deptcd4());
					ffbean.setSch_deptcd5(idsbbean.getSch_deptcd5());
					ffbean.setSch_deptcd6(idsbbean.getSch_deptcd6());
					ffbean.setSch_chrgunitcd(idsbbean.getSch_chrgunitcd());
					ffbean.setSch_inputusrid(idsbbean.getSch_inputusrid());
					ffbean.setTotalState(false);
					ffbean.setTotalShowStringState(false);
					ffbean.setSubtotalState(false);
					ffbean.setSubtotalShowStringState(false);
					ffbean.setIncludeNotSubmitData(true);
					ffbean.setListform(ffmgr.getFormFixedDataList(ffbean));
					
					//��ĸ����
					result.append((i + 1) + ". " + formtitle + "<br>\r\n");
					result.append(ffbean.getFormheaderhtml());
					List listform = ffbean.getListform();
					if (listform == null) {
						result.append(ffbean.getFormbodyhtml());
					} else {
						for (int j = 0; j < listform.size(); j++) {
							result.append(((FormatFixedBean)listform.get(j)).getFormbodyhtml());
						}
					}
					result.append(ffbean.getFormtailhtml() + "<p>\r\n");
					
				} else if ("03".equals(formkind)) {
					
					FormatBookManager fbmgr = FormatBookManager.instance();
					//������ ��� ���� ��������
					FormatBookBean fbbean = fbmgr.getFormatFormView(sysdocno, formseq);
					//������ �������� ����Ʈ ��������
					fbbean.setListfilebook(fbmgr.getFileBookFrm(sysdocno, formseq));
					//DataBookFrm ������ ��������
					fbbean.setFormdatalist(fbmgr.getFormDataList(sysdocno, formseq, idsbbean, "1", true));
					//DataBookFrm �������������� ��������
					fbbean.setFilenm(fbmgr.getDataBookCompView(sysdocno, formseq).getFilenm());
					fbbean.setOriginfilenm(fbmgr.getDataBookCompView(sysdocno, formseq).getOriginfilenm());
					fbbean.setFilesize(fbmgr.getDataBookCompView(sysdocno, formseq).getFilesize());
					
					//��ĸ����
					result.append((i + 1) + ". " + formtitle + "<br>\r\n");
					result.append("<table width='650' border='1' cellspacing='0' cellpadding='0' style='border-collapse:collapse'> \r\n");
					//�������� ������۽� ÷�ξ���(12.01.09)
					//result.append("  <tr>                                                         \r\n");
					//result.append("    <td width='100' class='s3'>��&nbsp;&nbsp;������</td>        \r\n");
					//result.append("    <td class='t1'>                                            \r\n");
					
					//List listFileBook = fbbean.getListfilebook();
					//if (listFileBook != null || listFileBook.size() != 0) {
					//	for (int j = 0; j < listFileBook.size(); j++) {
					//		FileBookBean bean = (FileBookBean)listFileBook.get(j);
					//		result.append("      "+bean.getOriginfilenm());
					//		if ( j + 1 < listFileBook.size() ) result.append("<br> \r\n");
					//	}
					//}
					//
					//result.append("    </td>                                                      \r\n");
					//result.append("  </tr>                                                        \r\n");
					
					if (fbbean.getFilenm() != null && !fbbean.getFilenm().trim().equals("")) {
						result.append("  <tr>                                                         \r\n");
						result.append("    <td width='100' class='s3'>��&nbsp;&nbsp;�����ڷ�</td>       \r\n");
						result.append("    <td class='t1'>"+fbbean.getOriginfilenm()+"</td>            \r\n");
						result.append("  </tr>                                                        \r\n");
					}
					
					result.append("</table><br>                                                   \r\n");
					result.append("<table width='650' border='1' cellspacing='0' cellpadding='0' style='border-collapse:collapse'> \r\n");
					result.append("  <tr>                                                         \r\n");
					result.append("    <td width='80' class='s3'>ī�װ�</td>                      \r\n");
					result.append("    <td width='80' class='s3'>�μ�</td>                         \r\n");
					result.append("    <td width='100' class='s3'>����</td>                        \r\n");
					result.append("    <td width='50' class='s3'>�����</td>                       \r\n");
					result.append("    <td class='s3'>÷������</td>                                 \r\n");
					result.append("    <td width='80' class='s3'>����ũ��</td>                      \r\n");
					result.append("  </tr>                                                        \r\n");
					List formDataList = fbbean.getFormdatalist();
					if (formDataList == null || formDataList.size() == 0) {
						result.append("  <tr>                                                         \r\n");
						result.append("    <td class='list_l' colspan='6'>�Էµ� �ڷᰡ �����ϴ�</td>      \r\n");
						result.append("  </tr>                                                        \r\n");
					} else {
						for (int j = 0; j < formDataList.size(); j++) {
							DataBookBean bean = (DataBookBean)formDataList.get(j);
							result.append("  <tr>                                                                                                          \r\n");
							result.append("    <td class='list_l'>"+bean.getCategorynm()+"</td>                                                            \r\n");
							result.append("    <td class='list_l'>"+bean.getTgtdeptnm()+"</td>                                                           \r\n");
							result.append("    <td class='list_l'>"+bean.getFormtitle()+"</td>                                                             \r\n");
							result.append("    <td class='list_l'>"+bean.getInputusrnm()+"</td>                                                            \r\n");
							result.append("    <td class='list_l'>"+bean.getOriginfilenm()+"</td>                                                          \r\n");
							result.append("    <td class='list_l' style='padding-right:10;text-align:right'>"+(int)(bean.getFilesize()/1000f+0.5)+"KB</td> \r\n");
							result.append("  </tr>                                                                                                         \r\n");
						}
					}
					result.append("</table><p> \r\n");
				}
			}
			
		}  else {	//��û��
			
			int reqformno = sysdocno;
			
			//���� ����
			SinchungManager smgr = SinchungManager.instance();
			ReqMstBean mstBean = smgr.reqDataInfo(reqformno, reqseq);
			DataForm dform = new DataForm();
			BeanUtils.copyProperties(dform, mstBean);
			
			//������ ������������
			String title = smgr.reqFormInfo(reqformno).getTitle();
			String basictype = smgr.reqFormInfo(reqformno).getBasictype();
			//�׸� ��� ������ ��������
			List articleList = smgr.reqFormSubList(reqformno);
			
			//CCD���̺��� CCD:991�� ��ȹ������� Ư�� ������·� ��Ⱥ����� ����� ���ؼ� ����� ���HTML�� �������� ���
			//		   ���ڵ� : ��ļ���, ���ڵ�� : ���HTML ���� ���ڵ�(CCDCD)
			//CCD���̺��� CCD:998�� ��ȹ������� Ư�� ������·� ��Ⱥ����� ����� ���ؼ� ����� ���HTML ������ ���
			//   ���ڵ� : �������� ���ڵ�(CCDCD), ���ڵ�� : ���HTML ���, ���ڵ弳�� : ������ ��û�� �ڵ�
			//CCD���̺��� CCD:999�� CCD:998�� ����� ���HTML�� ������ $VALUE ������ ġȯ�ϱ� ���� ���� ������ ����
			//   ���ڵ� : ���HTML�� $VALUE ����, ���ڵ�� : ��û���� �� ����, ���ڵ弳�� : ������ ��û�� �� ��Ī(�ʼ��ƴ�)
			
			String INFO_CCDCD = "991";
			String mapCcd = "";
			String mapFile = "";
			int mapRequestNo = 0;
			
			CcdManager ccdmgr = CcdManager.instance();
			
			List subCodeList = ccdmgr.subCodeList(INFO_CCDCD);
			for ( int i = 0; subCodeList != null && i < subCodeList.size(); i++ ) {
				CcdBean ccdBean = (CcdBean)subCodeList.get(i);
				String ccdname = Utils.nullToEmptyString(ccdBean.getCcdname());
				
				List list = ccdmgr.subCodeList(ccdname);
				if ( list != null && list.size() > 0 ) {
					CcdBean bean = (CcdBean)list.get(0);
					mapCcd = Utils.nullToEmptyString(bean.getCcdsubcd());
					mapFile = Utils.nullToEmptyString(bean.getCcdname());
					mapRequestNo = Integer.parseInt("0" + Utils.nullToEmptyString(bean.getCcddesc()));
					
					if ( reqformno == mapRequestNo ) break;
				}
			}
			
			if ( reqformno == mapRequestNo ) {
				FileInputStream  fis = null;
				ByteArrayOutputStream baos = null;
				StringBuffer html = null;
				
				try {
					fis = new FileInputStream(sContext.getRealPath(mapFile));
					baos = new ByteArrayOutputStream();
					
					int bytesRead = 0;
					byte[] buffer = new byte[4096];
					while ((bytesRead = fis.read(buffer, 0, buffer.length)) != -1) {
						baos.write(buffer, 0, bytesRead);
					}
					
					html = new StringBuffer(baos.toString());
					
					StringBuffer Presentsn = new StringBuffer(Utils.nullToEmptyString(dform.getPresentsn()));
					if ( Presentsn.length() > 5 ) {
						Presentsn.insert(6, "-");
					}
					
					String zip = ""; String addr1 = ""; String addr2 = "";
					if ( dform.getZip() != null ) { zip = "["+dform.getZip()+"]"; }
					if ( dform.getAddr1() != null ) { addr1 = dform.getAddr1(); }
					if ( dform.getAddr2() != null ) { addr2 = dform.getAddr2(); }
					
					//�⺻�������� : ��(0), ����(1), �ֹι�ȣ(2), �Ҽ�(3), ����(4), �ּ�(5), �̸���(6), ��ȭ��ȣ(7), �޴���ȭ��ȣ(8), �ѽ���ȣ(9)
					//�⺻������ 0~9��, �߰��Է¹����� 1���� 10���� ����
					
					String[] basicData = {"", dform.getPresentnm(), Presentsn.toString(), dform.getPosition(), dform.getDuty(),
										  zip+" "+addr1+" "+addr2, dform.getEmail(), dform.getTel(), dform.getCel(), dform.getFax()};
					
					String[] temp = basictype.split(",");
					String[] dataValue = null;
					
					if ( articleList != null ) {
						dataValue = new String[temp.length + 1 + articleList.size()];
						for ( int i = 0; i < articleList.size(); i++ ) {
							ArticleBean abean = (ArticleBean)articleList.get(i);                     //��û��� ������
							ReqSubBean subbean = (ReqSubBean)dform.getAnscontList().get(i);    //��û���� ������

							String dvalue = "";
							switch(Integer.parseInt(abean.getFormtype())){
								case 1:    //���� ������
									dvalue = commfunction.setRadioValue(abean.getSampleList(), abean.getExamtype(), subbean);
									break;
								case 2:    //���� ������
									dvalue = commfunction.setChkValue(abean.getSampleList(), abean.getExamtype(), subbean);
									break;
								case 3:    //�ܴ���
									dvalue = subbean.getAnscont();
									break;
								case 4:    //�幮��
									dvalue = Utils.convertHtmlBrNbsp(subbean.getAnscont());
									break;
							}
							
							dataValue[temp.length + 1 + i] = Utils.nullToEmptyString(dvalue);
						}
					} else {
						dataValue = new String[temp.length + 1];
					}
					
					dataValue[0] = basicData[0];
					for ( int i = 0; i < temp.length; i++ ) {
						dataValue[1 + i] = Utils.nullToEmptyString(basicData[Integer.parseInt(temp[i])]);
					}
					
					int idx = 0;
					int dataNo = 1;
					String MARK = "$VALUE";
					int MARK_LENGTH = MARK.length();
					while ( (idx = html.indexOf(MARK, idx)) != -1 ) {
						int mapDataIdx = 0;
						try {
							mapDataIdx = Integer.parseInt(ccdmgr.getCcd_SubName(mapCcd, Integer.toString(dataNo++)));
						} catch ( Exception e ) {
							e.printStackTrace();
						}
						try {
							html.replace(idx, idx + MARK_LENGTH, dataValue[mapDataIdx]);
						} catch ( Exception e ) {}
					}
					
					result = html;
				} catch ( Exception e ) {
					e.printStackTrace();
					mapRequestNo = 0;
				} finally {
					baos.close();
					fis.close();
				}
			}
			
			if ( reqformno != mapRequestNo ) {				
				result.append("<table width='650' border='1' cellspacing='0' cellpadding='0' style='border-collapse:collapse'>      \r\n");
				result.append("	<tr>                                                                                                \r\n");
				result.append("		<td width='110' height='23' align='center' class='s2'>�� ��</td>                                 \r\n");
				result.append("		<td width='540' style='padding-left:5px'>" + title + "</td>                          \r\n");
				result.append("	</tr>                                                                                               \r\n");
				result.append("	<tr>                                                                                                \r\n");
				result.append("		<td width='110' height='23' align='center' class='s2'>��û�Ͻ�</td>                              \r\n");
				result.append("		<td width='540' style='padding-left:5px'>" + mstBean.getCrtdt() + "</td>             \r\n");
				result.append("	</tr>                                                                                               \r\n");
				if ( mstBean.getOriginfilenm() != null && mstBean.getOriginfilenm().equals("") == false ) {
					result.append("	<tr>                                                                                            \r\n");
					result.append("		<td width='110' height='23' align='center' class='s2'>÷������</td>                           \r\n");
					result.append("		<td width='540' style='padding-left:5px'>" + mstBean.getOriginfilenm() + "</td>  \r\n");
					result.append("	</tr>                                                                                           \r\n");
				}
				result.append("	<tr>                                                                                                \r\n");
				result.append("		<td height='20' colspan='2' style='padding-top:15'>                                             \r\n");
				result.append("			<table width='650' border='0' cellspacing='0' cellpadding='0' style='word-break:break-all'> \r\n");
				result.append("				" + commfunction.dataView(basictype, dform, articleList, "mgr", null) + "               \r\n");
				result.append("			</table>                                                                                    \r\n");
				result.append("		</td>                                                                                           \r\n");
				result.append("	</tr>                                                                                               \r\n");			
				result.append("</table>                                                                                             \r\n");
			}
		}
		
		return result.toString();
	}
	
	public String makeElecApprAttachFileAdjustTag(String formhtml) {
		
		StringBuffer result = new StringBuffer();
		
		result.append("<html> \r\n");
		result.append("<head> \r\n");
		result.append("\t <meta http-equiv='Content-Type' content='text/html;charset=euc-kr'> \r\n");
		result.append("\t <style type='text/css'> \r\n");
		
		result.append("\t body, table, tr, td, select, input, textarea { \r\n");
		result.append("\t font-family:����, Arial, Helvetica; \r\n");
		result.append("\t font-size:9pt; \r\n");
		result.append("\t color:#000000; \r\n");
		result.append("\t word-break:break-all; \r\n");
		result.append("\t line-height: 130%; \r\n");
		result.append("\t } \r\n");		
		result.append("\t .list_l {color: #4F4F4F;	height:27; text-align:center; padding-top:3;}                       \r\n");
		result.append("\t .s2{color: #632FB1; height:28; padding-top:3; padding-left:20; background-color:#F4EFFB;}     \r\n");
		result.append("\t .s3{color: #632FB1; height:28; padding-top:3; background-color:#F4EFFB; text-align:center}    \r\n");
		result.append("\t .t1{color: #4F4F4F; padding-left:10}                                                          \r\n");

		result.append("\t </style> \r\n");
		result.append("</head> \r\n");
		result.append("<body> \r\n");
		result.append(formhtml);
		result.append("\r\n </body> \r\n");
		result.append("</html> \r\n");
		
		return result.toString();
	}
	
	/**
	 * ��İ����ȣ��������
	 */
	public int getNextSancSeq() throws Exception {
		return  getElecApprDAO().getNextSancSeq();
	}
}