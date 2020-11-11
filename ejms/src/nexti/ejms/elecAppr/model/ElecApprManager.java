/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 전자결재 manager
 * 설명:
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
	 * 결재정보 가져오기
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public ElecApprBean selectColldocSancInfo(int sysdocno, String tgtdeptcd, String inputusrid) throws Exception {
		return getElecApprDAO().selectColldocSancInfo(sysdocno, tgtdeptcd, inputusrid);
	}
	
	/**
	 * 결재정보 가져오기
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public ElecApprBean selectColldocSancInfo(int seq) throws Exception {
		return getElecApprDAO().selectColldocSancInfo(seq);
	}
	
	/**
	 * 결재정보 입력
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public int insertColldocSancInfo(ElecApprBean eaBean) throws Exception {
		return getElecApprDAO().insertColldocSancInfo(eaBean);
	}
	
	/**
	 * 결재정보 수정
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public int updateColldocSancInfo(ElecApprBean eaBean) throws Exception {
		return getElecApprDAO().updateColldocSancInfo(eaBean);
	}
	
	/**
	 * 결재정보 가져오기
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public ElecApprBean selectRequestSancInfo(int reqformno, int reqseq) throws Exception {
		return getElecApprDAO().selectRequestSancInfo(reqformno, reqseq);
	}
	
	/**
	 * 결재정보 가져오기
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public ElecApprBean selectRequestSancInfo(int seq) throws Exception {
		return getElecApprDAO().selectRequestSancInfo(seq);
	}
	
	/**
	 * 결재정보 입력
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public int insertRequestSancInfo(ElecApprBean eaBean) throws Exception {
		return getElecApprDAO().insertRequestSancInfo(eaBean);
	}
	
	/**
	 * 결재정보 수정
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public int updateRequestSancInfo(ElecApprBean eaBean) throws Exception {
		return getElecApprDAO().updateRequestSancInfo(eaBean);
	}
	
	/**
	 * 결재건이 있는지 체크
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public boolean isSancneed() throws Exception {
		return getElecApprDAO().isSancneed();
	}
	
	/**
	 * 취합문서 결재건이 있는지 체크
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public boolean isColldocSancneed(int seq) throws Exception {
		return getElecApprDAO().isColldocSancneed(seq);
	}
	
	/**
	 * 신청서 결재건이 있는지 체크
	 * @param eaBean
	 * @return
	 * @throws Exception
	 */
	public boolean isRequestSancneed(int seq) throws Exception {
		return getElecApprDAO().isRequestSancneed(seq);
	}
	
	/*
	 * 전자결재기안문서 본문 만들기 : 구버전용
	 */
	public String makeElecApprContent(String formtitle) throws Exception {
		
		StringBuffer result = new StringBuffer();
		
		result.append("\t1. \n");
		result.append("\t2. [" + formtitle + "] 업무와 관련하여 붙임과 같이 제출합니다.\n");
		result.append("\n");
		result.append("\t붙임. 제출자료 각 1부. 끝.\n");
		
		if ( "동작3190000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ) {
			if ( formtitle.indexOf("행정전자") != -1 || formtitle.indexOf("행정 전자") != -1 ) {
				result.delete(0, result.capacity());
				result.append("\t○○○○○○○○○○ 사용을 위하여 행정전자서명 발급을 신청하니, 처리하여 주시기 바랍니다\n");
				result.append("\n");
				result.append("\t붙임 : 행정전자서명 신청서. 끝.\n");
			}
		}
		
		return result.toString(); 
	}
	
	public String makeElecApprAttachFile(int sysdocno, int reqseq, InputDeptSearchBoxBean idsbbean, int type, ServletContext sContext) throws Exception {
		
		StringBuffer result = new StringBuffer();
		
		if (type != 2) {	//취합문서
		
			ColldocManager cmgr = ColldocManager.instance();
			List formatList = cmgr.getListFormat(sysdocno);
			
			for (int i = 0; i < formatList.size(); i++) {
				String formtitle = ((ColldocBean)formatList.get(i)).getFormtitle();
				String formkind = ((ColldocBean)formatList.get(i)).getFormkind();
				int formseq = ((ColldocBean)formatList.get(i)).getFormseq();
				
				if ("01".equals(formkind)) {
				
					FormatLineManager flmgr = FormatLineManager.instance();
					//행추가형 양식 구조 가져오기
					FormatLineBean flbean = flmgr.getFormatLineDataView(sysdocno, formseq, idsbbean, false, false);		
					//행추가형 양식에 따른 데이터 가져오기
					flbean.setListform(flmgr.getFormDataList(sysdocno, formseq, idsbbean, false, false, true));
					//입력양식에서 추가 버튼(컬럼)삭제
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
					
					//양식만들기
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
					//고정양식형 양식 구조 가져오기
					FormatFixedBean ffbean = ffmgr.getFormatFormView(sysdocno, formseq, "");
					//고정양식형 양식에 따른 데이터 가져오기
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
					
					//양식만들기
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
					//제본형 양식 구조 가져오기
					FormatBookBean fbbean = fbmgr.getFormatFormView(sysdocno, formseq);
					//제본형 샘플파일 리스트 가져오기
					fbbean.setListfilebook(fbmgr.getFileBookFrm(sysdocno, formseq));
					//DataBookFrm 데이터 가져오기
					fbbean.setFormdatalist(fbmgr.getFormDataList(sysdocno, formseq, idsbbean, "1", true));
					//DataBookFrm 최종생성데이터 가져오기
					fbbean.setFilenm(fbmgr.getDataBookCompView(sysdocno, formseq).getFilenm());
					fbbean.setOriginfilenm(fbmgr.getDataBookCompView(sysdocno, formseq).getOriginfilenm());
					fbbean.setFilesize(fbmgr.getDataBookCompView(sysdocno, formseq).getFilesize());
					
					//양식만들기
					result.append((i + 1) + ". " + formtitle + "<br>\r\n");
					result.append("<table width='650' border='1' cellspacing='0' cellpadding='0' style='border-collapse:collapse'> \r\n");
					//제출양식은 기안전송시 첨부안함(12.01.09)
					//result.append("  <tr>                                                         \r\n");
					//result.append("    <td width='100' class='s3'>●&nbsp;&nbsp;제출양식</td>        \r\n");
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
						result.append("    <td width='100' class='s3'>●&nbsp;&nbsp;최종자료</td>       \r\n");
						result.append("    <td class='t1'>"+fbbean.getOriginfilenm()+"</td>            \r\n");
						result.append("  </tr>                                                        \r\n");
					}
					
					result.append("</table><br>                                                   \r\n");
					result.append("<table width='650' border='1' cellspacing='0' cellpadding='0' style='border-collapse:collapse'> \r\n");
					result.append("  <tr>                                                         \r\n");
					result.append("    <td width='80' class='s3'>카테고리</td>                      \r\n");
					result.append("    <td width='80' class='s3'>부서</td>                         \r\n");
					result.append("    <td width='100' class='s3'>제목</td>                        \r\n");
					result.append("    <td width='50' class='s3'>담당자</td>                       \r\n");
					result.append("    <td class='s3'>첨부파일</td>                                 \r\n");
					result.append("    <td width='80' class='s3'>파일크기</td>                      \r\n");
					result.append("  </tr>                                                        \r\n");
					List formDataList = fbbean.getFormdatalist();
					if (formDataList == null || formDataList.size() == 0) {
						result.append("  <tr>                                                         \r\n");
						result.append("    <td class='list_l' colspan='6'>입력된 자료가 없습니다</td>      \r\n");
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
			
		}  else {	//신청서
			
			int reqformno = sysdocno;
			
			//내용 복사
			SinchungManager smgr = SinchungManager.instance();
			ReqMstBean mstBean = smgr.reqDataInfo(reqformno, reqseq);
			DataForm dform = new DataForm();
			BeanUtils.copyProperties(dform, mstBean);
			
			//마스터 정보가져오기
			String title = smgr.reqFormInfo(reqformno).getTitle();
			String basictype = smgr.reqFormInfo(reqformno).getBasictype();
			//항목 양식 데이터 가져오기
			List articleList = smgr.reqFormSubList(reqformno);
			
			//CCD테이블의 CCD:991은 기안문생성시 특정 양식형태로 기안본문을 만들기 위해서 사용할 양식HTML과 매핑정보 등록
			//		   부코드 : 양식순번, 부코드명 : 양식HTML 정보 주코드(CCDCD)
			//CCD테이블의 CCD:998은 기안문생성시 특정 양식형태로 기안본문을 만들기 위해서 사용할 양식HTML 파일을 등록
			//   부코드 : 매핑정보 주코드(CCDCD), 부코드명 : 양식HTML 경로, 부코드설명 : 매핑할 신청서 코드
			//CCD테이블의 CCD:999는 CCD:998에 등록한 양식HTML의 내용중 $VALUE 값으로 치환하기 위한 값의 순서를 매핑
			//   부코드 : 양식HTML의 $VALUE 순번, 부코드명 : 신청서의 값 순번, 부코드설명 : 매핑할 신청서 값 명칭(필수아님)
			
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
					
					//기본정보순서 : 빈값(0), 성명(1), 주민번호(2), 소속(3), 직급(4), 주소(5), 이메일(6), 전화번호(7), 휴대전화번호(8), 팩스번호(9)
					//기본정보는 0~9번, 추가입력문항은 1번은 10번과 같음
					
					String[] basicData = {"", dform.getPresentnm(), Presentsn.toString(), dform.getPosition(), dform.getDuty(),
										  zip+" "+addr1+" "+addr2, dform.getEmail(), dform.getTel(), dform.getCel(), dform.getFax()};
					
					String[] temp = basictype.split(",");
					String[] dataValue = null;
					
					if ( articleList != null ) {
						dataValue = new String[temp.length + 1 + articleList.size()];
						for ( int i = 0; i < articleList.size(); i++ ) {
							ArticleBean abean = (ArticleBean)articleList.get(i);                     //신청양식 데이터
							ReqSubBean subbean = (ReqSubBean)dform.getAnscontList().get(i);    //신청내역 데이터

							String dvalue = "";
							switch(Integer.parseInt(abean.getFormtype())){
								case 1:    //단일 선택형
									dvalue = commfunction.setRadioValue(abean.getSampleList(), abean.getExamtype(), subbean);
									break;
								case 2:    //복수 선택형
									dvalue = commfunction.setChkValue(abean.getSampleList(), abean.getExamtype(), subbean);
									break;
								case 3:    //단답형
									dvalue = subbean.getAnscont();
									break;
								case 4:    //장문형
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
				result.append("		<td width='110' height='23' align='center' class='s2'>제 목</td>                                 \r\n");
				result.append("		<td width='540' style='padding-left:5px'>" + title + "</td>                          \r\n");
				result.append("	</tr>                                                                                               \r\n");
				result.append("	<tr>                                                                                                \r\n");
				result.append("		<td width='110' height='23' align='center' class='s2'>신청일시</td>                              \r\n");
				result.append("		<td width='540' style='padding-left:5px'>" + mstBean.getCrtdt() + "</td>             \r\n");
				result.append("	</tr>                                                                                               \r\n");
				if ( mstBean.getOriginfilenm() != null && mstBean.getOriginfilenm().equals("") == false ) {
					result.append("	<tr>                                                                                            \r\n");
					result.append("		<td width='110' height='23' align='center' class='s2'>첨부파일</td>                           \r\n");
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
		result.append("\t font-family:돋움, Arial, Helvetica; \r\n");
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
	 * 양식결재번호가져오기
	 */
	public int getNextSancSeq() throws Exception {
		return  getElecApprDAO().getNextSancSeq();
	}
}