<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���տϷ� ���չ�������
 * ����:
--%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>
<%
String sysdocno = "";
String formseq = "";
String formkind = "";
String extchk = "";

if(request.getParameter("sysdocno") != null) {
	sysdocno = request.getParameter("sysdocno");
} else if(request.getAttribute("sysdocno") != null) {
	sysdocno = request.getAttribute("sysdocno").toString();
}

if(request.getParameter("formseq") != null) {
	formseq = request.getParameter("formseq");
} else if(request.getAttribute("formseq") != null) {
	formseq = request.getAttribute("formseq").toString();
}

if(request.getParameter("formkind") != null) {
	formkind = request.getParameter("formkind");
}

if(request.getParameter("extchk") != null) {
	extchk = request.getParameter("extchk");
} else if(request.getAttribute("extchk") != null) {
	extchk = request.getAttribute("extchk").toString();
}
%>

<jsp:include page="/include/header_user.jsp" flush="true"/>

<script src="/script/base64.js"></script>
<script src="/script/prototype.js"></script>
<script src="/script/collproc.js"></script>
<script src="/script/collcomp.js"></script>
<script>
menu1.onmouseover();
menu11.onmouseover();

function iframeScroll() {
	var frmDoc = formFrame.document;
	if(frmDoc.readyState == "complete") {
		frmDoc.body.scrollTop = parseInt("0<%=(String)request.getParameter("frmScrollTop")%>", 10);
		
		var sh = frmDoc.body.scrollHeight;
		if (sh < formFrameTD.height) {	//��Ĵټ���
			formFrameTD.height = sh;
		}
	}
}

function doSubmit() {
	var param = '?formkind=<%=formkind%>&sysdocno=<%=sysdocno%>&formseq=<%=formseq%>&frmScrollTop=' + formFrame.document.body.scrollTop;
	document.forms[0].action += param;
	document.forms[0].submit();
}

function linedataModify(frm){
	frm.action = "/collcomp/collcompLineModify.do?formkind=<%=formkind%>&frmScrollTop=" + document.frames[1].document.body.scrollTop;
	frm.submit();
}

function databookModify(frm){
	frm.action = "/collcomp/collcompDataBookModify.do?formkind=<%=formkind%>&frmScrollTop=" + document.frames[1].document.body.scrollTop;
	frm.submit();
}	

function f_row(gubun){ 
	var baseLocation=document.getElementById("mainTable");  
 	var lo_this = event.srcElement;
 	var lo_td   = lo_this.parentNode;
 	var lo_tr  	= lo_td.parentNode;
 	var lo_table = lo_tr.parentNode; 	
 	var li_row_index = lo_table.parentNode.parentNode.parentNode.getAttribute("id");
 	var startIdx = li_row_index.indexOf('r');
	var rownum = parseInt(li_row_index.substring(0,startIdx),10);
	
	fromID=parseInt(li_row_index,10);
	
	if(gubun == '1'){
		//set target row id : strip "r" from id               
  	toID=  parseInt((--rownum + "r"),10); 
  	if(toID<0){
  		alert("�ֻ����Դϴ� �����Ҽ� �����ϴ�.");
  		return;
  	}
  }else{
  	toID= parseInt((++rownum + "r"),10);
  	if(toID>=baseLocation.rows.length){
  		alert("�������Դϴ� �����Ҽ� �����ϴ�.");
  		return;
  	}
	}
  // move row : clear background : clear rowID from container
  baseLocation.moveRow(fromID,toID);
  initTableRows();
}

function initTableRows(){
   var baseLocation=document.getElementById("mainTable");
   document.getElementsByName("rowid").value="disabled";
   for (i=0; i < baseLocation.cells.length; i++)
   {
   	baseLocation.cells(i).unselectable = "On";
   }
   for (i=0; i < baseLocation.rows.length; i++)
   {
   	baseLocation.rows(i).setAttribute("id",i+"r");
   	
   	baseLocation.rows(i).cells(0).innerHTML = (i+1)+"<input type=hidden id=ord name=ord value='"+(i+1)+"'>";
   }
}

function ordupdate(ord_gubun, frm){
	var saveConfirm = false;
	 if(ord_gubun == "0"){
	   saveConfirm = confirm("���ļ����� �μ����������� �����˴ϴ�\n\n�ʱ�ȭ �Ͻðڽ��ϱ�?");
	 } else if(ord_gubun == "1"){
	   saveConfirm = confirm("���ļ����� 1�� �����˴ϴ�\n\n�ʱ�ȭ �Ͻðڽ��ϱ�?");
	 } else {
	   saveConfirm = confirm("���������Ͻðڽ��ϱ�?");
	 }
   
   if(saveConfirm)
   {
       var Sysdocno, Formseq, Fileseq, Ord;
       var approvalLine = new Array();
       
       if(isNaN(frm.sysdocno.length) != true)
       {              
           for(var k = 0; k < frm.sysdocno.length; k++)
           {                   
                   Sysdocno = frm.sysdocno[k].value;
				           Formseq = frm.formseq[k].value;
				           Fileseq = frm.fileseq[k].value;
				           Ord = frm.ord[k].value;
				           Tgtdeptcd = frm.tgtdeptcd[k].value;
				           Inputusrid = frm.inputusrid[k].value;       
                   approvalLine[k] = Sysdocno + ";" + Formseq + ";" + Fileseq + ";" + Ord + ";" + Tgtdeptcd + ";" + Inputusrid;
           }
       }
       else
       {
           Sysdocno = frm.sysdocno.value;
           Formseq = frm.formseq.value;
           Fileseq = frm.fileseq.value;
           Ord = frm.ord.value;
           Tgtdeptcd = frm.tgtdeptcd.value;
           Inputusrid = frm.inputusrid.value;
           approvalLine[0] = Sysdocno + ";" + Formseq + ";" + Fileseq + ";" + Ord + ";" + Tgtdeptcd + ";" + Inputusrid;
       }

       document.getElementById("app").value = approvalLine;
       frm.target = "actionFrame";
       frm.action += "?ord_gubun="+ord_gubun+"&formkind=<%=formkind%>&frmScrollTop=" + document.frames[1].document.body.scrollTop;
       frm.submit();
   }
   else
   {
       return;
   }
}

function popup_contents(sysdocno, formseq, sort, frm) {
	var filenm;
	var filenmList = new Array();
	var categorynm;
	var precategorynm="";
	var tgtdeptnm;
	var pretgtdeptnm="";
	var contents="";
	var h = 1;
	var i = 0; 
	var j = 0;
	var han=new Array("��",			"��",			"��",			"��",			"��",			"��",			"��",
										"��",			"��",			"��",			"ī",			"Ÿ",			"��",			"��",
										"����",		"����",		"�ٴ�",		"���",		"����",		"�ٹ�",		"���",
										"�ƾ�",		"����",		"����",		"īī",		"ŸŸ",		"����",		"����",
										"������",		"������",		"�ٴٴ�",		"����",		"������",		"�ٹٹ�",		"����",
										"�ƾƾ�",		"������",		"������",		"īīī",		"ŸŸŸ",		"������",		"������",
										"��������",	"��������",	"�ٴٴٴ�",	"�����",	"��������",	"�ٹٹٹ�",	"�����",
										"�ƾƾƾ�",	"��������",	"��������",	"īīīī",	"ŸŸŸŸ",	"��������",	"��������");
	
	if(sort == '2'){
		if(isNaN(frm.categorynm.length) != true){
			for(var k = 0; k < frm.categorynm.length; k++){
				categorynm = frm.categorynm[k].value;
				tgtdeptnm = frm.tgtdeptnm[k].value;
				formtitle = frm.formtitle[k].value;
				if(precategorynm == categorynm){
					if(pretgtdeptnm == tgtdeptnm){
						contents += '    ('+ (j++) +')'+ formtitle + '\\r' ;	
					}else{
						j = 1;
						contents +=   '  '+ han[(i++)] + '.' +tgtdeptnm +'\\r' + '    ('+ (j++) + ')'+ formtitle + '\\r' ;	
					}				
				}else{
					i = 0;
					j = 1;
					contents +=  (h++) + '.' + categorynm +'\\r' + '  '+ han[(i++)] + '.' + tgtdeptnm +'\\r' + '    ('+ (j++) + ')'+ formtitle + '\\r' ;	
				}
				precategorynm = categorynm;
				pretgtdeptnm = tgtdeptnm;
			}
		}else{
				categorynm = frm.categorynm.value;
				tgtdeptnm = frm.tgtdeptnm.value;
				formtitle = frm.formtitle.value;
				
				i = 0;
					j = 1;
					contents +=  (h++) + '.' + categorynm +'\\r' + '  '+ han[(i++)] + '.' + tgtdeptnm +'\\r' + '    ('+ (j++) + ')'+ formtitle + '\\r' ;	
		}
	}else{
		if(isNaN(frm.categorynm.length) != true){
			for(var k = 0; k < frm.categorynm.length; k++){
				categorynm = frm.categorynm[k].value;
				tgtdeptnm = frm.tgtdeptnm[k].value;
				formtitle = frm.formtitle[k].value;
				if(pretgtdeptnm == tgtdeptnm){
					if(precategorynm == categorynm){
						contents += '    ('+ (j++) +')'+ formtitle + '\\r' ;	
					}else{
						j = 1;
						contents +=   '  '+ han[(i++)] + '.' + categorynm +'\\r' + '    ('+ (j++) + ')'+ formtitle + '\\r' ;	
					}
				}else{
					i = 0;
					j = 1;
					contents +=  (h++) + '.' + tgtdeptnm +'\\r' + '  '+ han[(i++)] + '.' + categorynm +'\\r' + '    ('+ (j++) + ')'+ formtitle + '\\r' ;	
				}
				precategorynm = categorynm;
				pretgtdeptnm = tgtdeptnm;
			}
		}else{
				categorynm = frm.categorynm.value;
				tgtdeptnm = frm.tgtdeptnm.value;
				formtitle = frm.formtitle.value;
				
				i = 0;
				j = 1;
				contents +=  (h++) + '.' + tgtdeptnm +'\\r' + '  '+ han[(i++)] + '.' + categorynm +'\\r' + '    ('+ (j++) + ')'+ formtitle + '\\r' ;	
		}
	}

	if ( isNaN(frm.sysdocno.length) != true ) {
		var cnt = 0;
		for ( var k = 0; k < frm.filenm.length; k++ ) {
			var fileExt = frm.filenm[k].value.substring(frm.filenm[k].value.length-4).toLowerCase();
			if ( fileExt == ".hwp" || fileExt == ".txt" ) {
				filenm = frm.filenm[k].value;
		    filenmList[cnt++] = filenm;
			}
		}
	} else {
		var fileExt = frm.filenm.value.substring(frm.filenm.value.length-4).toLowerCase();
		if ( fileExt == ".hwp" || fileExt == ".txt" ) {
			filenm = frm.filenm.value;
			filenmList = filenm;
		}
	}
	
	if ( String(filenmList).trim() == "" ) {
		alert("�����ڷ�� ������ ������ �����ϴ�");
		return;
	}
	
	var mform = document.mergeForm;
	mform.sysdocno.value = sysdocno;
	mform.formseq.value = formseq;
	mform.filenmlist.value = filenmList;
	mform.contents.value = contents;
	
	window.open("", "file", "width=1015,height=740,resizable=no");
	mform.target = "file";
	mform.submit();
}	

function DataBookCompSave(frm) {
	if(frm.inputFile.value == "") {
		alert("���ε��� ������ �����Ͽ� �ֽʽÿ�.");
		return;
	} else {
		if(window.confirm("����Ͻðڽ��ϱ�?") == false) {
			return;
		}
	
		frm.target = "actionFrame";
		frm.action += "?formkind=<%=formkind%>&frmScrollTop=" + document.frames[1].document.body.scrollTop;
		frm.submit();
	}
}

function DataBookCompDelete(frm) {
	var cnf = window.confirm("���������ڷ� ������ �����Ͻðڽ��ϱ�?\n�ѹ� ������ �����ʹ� ������ ���� �ʽ��ϴ�.");
	
	if(cnf) {
		frm.target = "actionFrame";
		frm.action = "/collcomp/DataBookCompDelete.do?formkind=<%=formkind%>&frmScrollTop=" + document.frames[1].document.body.scrollTop;		
		frm.submit();
	}
}

function bundleDownload(frm) {
	var oldAction = frm.action;
	var oldTarget = frm.target;
	frm.action = "/bundleDownload.do"
	frm.target = "actionFrame";
	frm.submit();
	frm.action = oldAction;
	frm.target = oldTarget;
}
</script>

<div id="comment" style="display:none; position:absolute;">
<table border="1" style="border-collapse:collapse" bgcolor="rgb(255, 255, 200)"><tr><td>
<font face="����" size="2">&nbsp;Ŭ���Ͻø� ���ĵ˴ϴ�.&nbsp;</font>
</td></tr></table>
</div>
<table width="865" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <!--Ÿ��Ʋ-->
        <tr> 
          <td background="/images/common/title_bg.gif" height="38"><img src="/images/collect/title_07.gif" alt=""></td>
          <td rowspan="2" colspan="2" valign="bottom"><img src="/images/common/right_round.gif" width="13" height="13" alt=""></td>
        </tr>
        <tr> 
          <td height="11"></td>
        </tr>
        <tr> 
          <td height="6" valign="top"> 
            <table width="820" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td> 
                  <!--��� ���ø޴� ���̺�-->
                  <table width="820" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="5"><img src="/images/common/select_left.gif" width="5" height="41" alt=""></td>
                      <td width="810" background="/images/common/select_bg.gif"> 
                        <table width="800" border="0" cellspacing="0" cellpadding="0" align="center">
                          <tr> 
                            <td width="94"><img src="/images/collect/s_m_01.gif" width="94" height="41" alt="���չ�������" style="cursor:hand" onclick="location.href='/collcompInfoView.do?formkind=<%=formkind%>&sysdocno=<%=sysdocno%>&formseq=<%=formseq%>&frmScrollTop=' + formFrame.document.body.scrollTop"></td>
                            <td width="94"><img src="/images/collect/s_m_02_over.gif" width="94" height="41" alt="���վ���ڷ�"></td>
                            <td align="right" valign="bottom" style="padding:0 0 1 0;" id="changeicon">
					                  	<img src="/images/common/btn_release.gif" alt="��������" style="cursor:hand" onclick="chkCancelProc('<%=sysdocno%>')">
					                  	<img src="/images/common/btn_end.gif" alt="���ո���" style="cursor:hand" onclick="popup_open(<%=sysdocno%>)">
                              <img src="/images/common/btn_list.gif" alt="��Ϻ���" style="cursor:hand" onclick="location.href='/collcompList.do'">
                            </td>
                          </tr>
                        </table>
                      </td>
                      <td width="5"><img src="/images/common/select_right.gif" width="5" height="41" alt=""></td>
                    </tr>
                  </table>
                  <!--��� ���ø޴� ���̺�-->
                </td>
              </tr>
              <tr> 
                <td height="15"></td>
              </tr>
              <tr> 
                <td> 
                  <table width="820" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="42">
                    <tr> 
                      <td bgcolor="#FFFFFF"> 
                        <!--��������/�˸��� ���̺� ����-->
                        <table width="810" border="0" cellspacing="0" cellpadding="0" height="32" align="center" bgcolor="#F7F7F7">
                          <tr> 
                            <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="250"><font color="#4F4F4F"><b>�������� : </b></font><b><font color="#FF5656"><bean:write name="collprocForm" property="enddt"/></font></b></td>
                            <td width="25" align="center"><img src="/images/common/dot.gif" width="7" height="7" alt=""></td>
                            <td width="510"><font color="#4F4F4F"><b>�����˸��� : </b></font><font color="#4F4F4F"><bean:write name="collprocForm" property="endcomment"/></font></td>
                          </tr>
                        </table>
                        <!--��������/�˸��� ���̺� ��-->
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
              </tr>
              <tr> 
                <td valign="top" height="172" id="formFrameTD"><iframe name="formFrame" src="/formatList.do?sysdocno=<%=sysdocno%>&formseq=<%=formseq%>" width="100%" height="100%" frameborder="0" scrolling="auto" title="��ĸ��" onload="iframeScroll()"></iframe></td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
              </tr>
            </table>
          </td>
          <td width="11" valign="bottom"></td>
          <td width="2" valign="top"> 
            <table width="2" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#ECF3F9">
              <tr> 
                <td width="2"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
    <td width="2" valign="top"><img src="/images/common/step_04.gif" style="position:absolute; margin:50 0 0 5; z-index:2;" alt="">
    </td>
  </tr>
</table>

<logic:notEmpty name="formatLineForm">
<table width="865" border="0" cellspacing="0" cellpadding="0">
<html:form action="/collcomp/formatLineView.do" method="POST">
<html:hidden property="sysdocno" value="<%=sysdocno %>"/>
<html:hidden property="formseq" value="<%=formseq %>"/>
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="6" valign="top"> 
					  <table width="820" border="0" cellspacing="0" cellpadding="0">
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">��İ���</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
						      <logic:notEmpty name="formatLineForm" property="formcomment">
										<bean:define id="formcomment" name="formatLineForm" property="formcomment"/>
										<b><%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formcomment.toString())%></b>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">�Է�����</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
									<logic:empty name="formatLineForm" property="sch_deptcd1_collection">
						      	<html:hidden property="sch_deptcd1"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatLineForm" property="sch_deptcd1_collection">
					      		<html:select property="sch_deptcd1" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd1_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatLineForm" property="sch_deptcd2_collection">
						      	<html:hidden property="sch_deptcd2"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatLineForm" property="sch_deptcd2_collection">
					      		<html:select property="sch_deptcd2" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd2_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatLineForm" property="sch_deptcd3_collection">
						      	<html:hidden property="sch_deptcd3"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatLineForm" property="sch_deptcd3_collection">
					      		<html:select property="sch_deptcd3" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd3_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatLineForm" property="sch_deptcd4_collection">
						      	<html:hidden property="sch_deptcd4"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatLineForm" property="sch_deptcd4_collection">
					      		<html:select property="sch_deptcd4" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd4_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty><br>
									<logic:empty name="formatLineForm" property="sch_deptcd5_collection">
						      	<html:hidden property="sch_deptcd5"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatLineForm" property="sch_deptcd5_collection">
					      		<html:select property="sch_deptcd5" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd5_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatLineForm" property="sch_deptcd6_collection">
						      	<html:hidden property="sch_deptcd6"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatLineForm" property="sch_deptcd6_collection">
					      		<html:select property="sch_deptcd6" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd6_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatLineForm" property="sch_chrgunitcd_collection">
						      	<html:hidden property="sch_chrgunitcd"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatLineForm" property="sch_chrgunitcd_collection">
					      		<html:select property="sch_chrgunitcd" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_chrgunitcd_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatLineForm" property="sch_inputusrid_collection">
						      	<html:hidden property="sch_inputusrid"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatLineForm" property="sch_inputusrid_collection">
					      		<html:select property="sch_inputusrid" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_inputusrid_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
							<tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">�հ�����</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
					      	<html:checkbox property="totalState" style="border:0;background-color:transparent;" onclick="doSubmit()" styleId="ts"/><label for="ts"><b>��ü�ڷ� �Ѱ� ǥ��</b></label>
					      </td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">��������</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
					      	<html:checkbox property="includeNotSubmitData" style="border:0;background-color:transparent;" onclick="doSubmit()" styleId="ns"/><label for="ns"><b>�Է������ڷ� ����(������)</b></label>
					      </td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					  </table>
          </td>
          <td width="11" valign="bottom"></td>
          <td width="2" valign="top"> 
            <table width="2" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#ECF3F9">
              <tr> 
                <td width="2"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
    <td width="2" valign="top"></td>
  </tr>
</html:form>
</table><br>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left:20">
  <tr> 
    <td>
    	<b style="position:absolute;left:290;padding-top:10;color:red;">�� �ѱ۴ٿ�ε�� ���常 �����մϴ�.</b>
    	<form name="formatDataForm" method="post" target="actionFrame">
				<input type="hidden" name="sysdocno" value="<%=sysdocno%>">
				<input type="hidden" name="formseq" value="<%=formseq%>">
				<input type="hidden" name="formatData">
				<input type="image" src="/images/common/btn_excel.gif" style="border:0;" onclick="this.form.action='/xlsDownload.do';formatData.value=tbl.outerHTML;" alt="�����ٿ�ε�">
				<input type="image" src="/images/common/btn_hwp.gif" style="border:0;" onclick="this.form.action='/hwpDownload.do';formatData.value=tbl.outerHTML;" alt="�ѱ۴ٿ�ε�">
			</form>
		</td>
  </tr>
  <tr>
  	<td height="5"></td>
  </tr>
  <tr>
  	<td><!-- ��ĵ����� ��Ÿ�� �κ� ����-->
	  	<bean:write name="formatLineForm" property="formheaderhtml" filter="false"/>
			<logic:iterate id="list" name="formatLineForm" property="listform">
				<bean:write name="list" property="formbodyhtml" filter="false" />
			</logic:iterate>
			<bean:write name="formatLineForm" property="formtailhtml" filter="false"/>
  	</td><!-- ��ĵ����� ��Ÿ�� �κ� ��-->
  </tr>
</table><br>
</logic:notEmpty>

<logic:notEmpty name="formatFixedForm">
<table width="865" border="0" cellspacing="0" cellpadding="0">
<html:form action="/collcomp/formatFixedView.do" method="POST">
<html:hidden property="sysdocno" value="<%=sysdocno %>"/>
<html:hidden property="formseq" value="<%=formseq %>"/>
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="6" valign="top"> 
					  <table width="820" border="0" cellspacing="0" cellpadding="0">
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">��İ���</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
						      <logic:notEmpty name="formatFixedForm" property="formcomment">
										<bean:define id="formcomment" name="formatFixedForm" property="formcomment"/>
										<b><%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formcomment.toString())%></b>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">�Է�����</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
									<logic:empty name="formatFixedForm" property="sch_deptcd1_collection">
						      	<html:hidden property="sch_deptcd1"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatFixedForm" property="sch_deptcd1_collection">
					      		<html:select property="sch_deptcd1" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd1_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatFixedForm" property="sch_deptcd2_collection">
						      	<html:hidden property="sch_deptcd2"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatFixedForm" property="sch_deptcd2_collection">
					      		<html:select property="sch_deptcd2" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd2_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatFixedForm" property="sch_deptcd3_collection">
						      	<html:hidden property="sch_deptcd3"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatFixedForm" property="sch_deptcd3_collection">
					      		<html:select property="sch_deptcd3" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd3_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatFixedForm" property="sch_deptcd4_collection">
						      	<html:hidden property="sch_deptcd4"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatFixedForm" property="sch_deptcd4_collection">
					      		<html:select property="sch_deptcd4" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd4_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty><br>
									<logic:empty name="formatFixedForm" property="sch_deptcd5_collection">
						      	<html:hidden property="sch_deptcd5"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatFixedForm" property="sch_deptcd5_collection">
					      		<html:select property="sch_deptcd5" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd5_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatFixedForm" property="sch_deptcd6_collection">
						      	<html:hidden property="sch_deptcd6"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatFixedForm" property="sch_deptcd6_collection">
					      		<html:select property="sch_deptcd6" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd6_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatFixedForm" property="sch_chrgunitcd_collection">
						      	<html:hidden property="sch_chrgunitcd"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatFixedForm" property="sch_chrgunitcd_collection">
					      		<html:select property="sch_chrgunitcd" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_chrgunitcd_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatFixedForm" property="sch_inputusrid_collection">
						      	<html:hidden property="sch_inputusrid"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatFixedForm" property="sch_inputusrid_collection">
					      		<html:select property="sch_inputusrid" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_inputusrid_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
								</td>
					    </tr>					    
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">�հ�����</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
					      	<html:checkbox property="totalState" style="border:0;background-color:transparent;" onclick="doSubmit()" styleId="ts"/><label for="ts"><b>��ü�ڷ� �Ѱ� ǥ��</b></label>
					      	<b>(</b><html:checkbox property="totalShowStringState" style="border:0;background-color:transparent;" onclick="doSubmit()" styleId="tsss"/><label for="tsss"><b>���� �Ѱ� ǥ��</b></label><b>)</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					      	<html:checkbox property="subtotalState" style="border:0;background-color:transparent;" onclick="doSubmit()" styleId="sts"/><label for="sts"><b>�μ��� �Ұ� ǥ��</b></label>
									<b>(</b><html:checkbox property="subtotalShowStringState" style="border:0;background-color:transparent;" onclick="doSubmit()" styleId="stsss"/><label for="stsss"><b>���� �Ұ� ǥ��</b></label><b>)</b>
					      </td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">��������</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
					      	<html:checkbox property="includeNotSubmitData" style="border:0;background-color:transparent;" onclick="doSubmit()" styleId="ns"/><label for="ns"><b>�Է������ڷ� ����(������)</b></label>
					      </td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					  </table>
          </td>
          <td width="11" valign="bottom"></td>
          <td width="2" valign="top"> 
            <table width="2" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#ECF3F9">
              <tr> 
                <td width="2"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
    <td width="2" valign="top"></td>
  </tr>
</html:form>
</table><br>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left:20">
  <tr> 
    <td>
    	<b style="position:absolute;left:290;padding-top:10;color:red;">�� �ѱ۴ٿ�ε�� ���常 �����մϴ�.</b>
    	<form name="formatDataForm" method="post" target="actionFrame">
				<input type="hidden" name="sysdocno" value="<%=sysdocno%>">
				<input type="hidden" name="formseq" value="<%=formseq%>">
				<input type="hidden" name="formatData">
				<input type="image" src="/images/common/btn_excel.gif" style="border:0;" onclick="this.form.action='/xlsDownload.do';formatData.value=tbl.outerHTML;" alt="�����ٿ�ε�">
				<input type="image" src="/images/common/btn_hwp.gif" style="border:0;" onclick="this.form.action='/hwpDownload.do';formatData.value=tbl.outerHTML;" alt="�ѱ۴ٿ�ε�">
			</form>
		</td>
  </tr>
  <tr>
  	<td height="5"></td>
  </tr>
  <tr>
  	<td><!-- ��ĵ����� ��Ÿ�� �κ� ����-->
	  	<bean:write name="formatFixedForm" property="formheaderhtml" filter="false"/>
			<logic:iterate id="list" name="formatFixedForm" property="listform">
				<bean:write name="list" property="formbodyhtml" filter="false" />
			</logic:iterate>
			<bean:write name="formatFixedForm" property="formtailhtml" filter="false"/>
  	</td><!-- ��ĵ����� ��Ÿ�� �κ� ��-->
  </tr>
</table><br>
</logic:notEmpty>

<logic:notEmpty name="formatBookForm">
<table width="865" border="0" cellspacing="0" cellpadding="0">
<html:form action="/collcomp/formatBookView.do" method="POST">
<html:hidden property="sysdocno" value="<%=sysdocno %>"/>
<html:hidden property="formseq" value="<%=formseq %>"/>
  <tr> 
    <td width="20">&nbsp;</td>
    <td width="843" valign="top"> 
      <table width="843" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="6" valign="top"> 
					  <table width="820" border="0" cellspacing="0" cellpadding="0">
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">��İ���</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
						      <logic:notEmpty name="formatBookForm" property="formcomment">
										<bean:define id="formcomment" name="formatBookForm" property="formcomment"/>
										<b><%=nexti.ejms.util.Utils.convertHtmlBrNbsp(formcomment.toString())%></b>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">������</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
									<logic:notEmpty name="fileBookForm" property="listfilebook">
										<logic:iterate id="list" name="fileBookForm" property="listfilebook">
											<a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm"/>&fileName=<bean:write name="list" property="originfilenm"/>"><bean:write name="list" property="originfilenm"/></a><br>
										</logic:iterate>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">�Է�����</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
									<logic:empty name="formatBookForm" property="sch_deptcd1_collection">
						      	<html:hidden property="sch_deptcd1"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatBookForm" property="sch_deptcd1_collection">
					      		<html:select property="sch_deptcd1" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd1_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatBookForm" property="sch_deptcd2_collection">
						      	<html:hidden property="sch_deptcd2"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatBookForm" property="sch_deptcd2_collection">
					      		<html:select property="sch_deptcd2" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd2_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatBookForm" property="sch_deptcd3_collection">
						      	<html:hidden property="sch_deptcd3"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatBookForm" property="sch_deptcd3_collection">
					      		<html:select property="sch_deptcd3" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd3_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatBookForm" property="sch_deptcd4_collection">
						      	<html:hidden property="sch_deptcd4"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatBookForm" property="sch_deptcd4_collection">
					      		<html:select property="sch_deptcd4" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd4_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty><br>
									<logic:empty name="formatBookForm" property="sch_deptcd5_collection">
						      	<html:hidden property="sch_deptcd5"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatBookForm" property="sch_deptcd5_collection">
					      		<html:select property="sch_deptcd5" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd5_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatBookForm" property="sch_deptcd6_collection">
						      	<html:hidden property="sch_deptcd6"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatBookForm" property="sch_deptcd6_collection">
					      		<html:select property="sch_deptcd6" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_deptcd6_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatBookForm" property="sch_chrgunitcd_collection">
						      	<html:hidden property="sch_chrgunitcd"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatBookForm" property="sch_chrgunitcd_collection">
					      		<html:select property="sch_chrgunitcd" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_chrgunitcd_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
									<logic:empty name="formatBookForm" property="sch_inputusrid_collection">
						      	<html:hidden property="sch_inputusrid"/>
						      </logic:empty>
					      	<logic:notEmpty name="formatBookForm" property="sch_inputusrid_collection">
					      		<html:select property="sch_inputusrid" style="width:165;;" onchange="sch_Reset();doSubmit()">
											<html:optionsCollection property="sch_inputusrid_collection" filter="false"/>
	                 	</html:select>
									</logic:notEmpty>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">��������</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
					      	<html:checkbox property="includeNotSubmitData" style="border:0;background-color:transparent;" onclick="doSubmit()" styleId="ns"/><label for="ns"><b>�Է������ڷ� ����(������)</b></label>
					      </td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr>
					    	<td>&nbsp;</td>
					    </tr>
					   	<tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">���ռ���</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
									<html:radio style="border:0;background-color:transparent;" property="rdb_sort" name="formatBookForm" value="1" onclick="doSubmit()">���ļ�����μ���ī�װ���</html:radio>&nbsp;&nbsp;&nbsp;
									<html:radio style="border:0;background-color:transparent;" property="rdb_sort" name="formatBookForm" value="2" onclick="doSubmit()">ī�װ���μ��������ļ���</html:radio>&nbsp;&nbsp;&nbsp;
									<html:radio style="border:0;background-color:transparent;" property="rdb_sort" name="formatBookForm" value="3" onclick="doSubmit()">�μ���ī�װ��������ļ���</html:radio>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">�����ʱ�ȭ</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
									<b onclick="ordupdate('0', document.forms[1])" style="cursor:hand"><u>�μ�������</u></a></b>&nbsp;&nbsp;&nbsp;
									<b onclick="ordupdate('1', document.forms[1])" style="cursor:hand"><u>1�� �ʱ�ȭ</u></a></b>
								</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					  </table>
          </td>
          <td width="11" valign="bottom"></td>
          <td width="2" valign="top"> 
            <table width="2" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#ECF3F9">
              <tr> 
                <td width="2"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
    <td width="2" valign="top"></td>
  </tr>
</html:form>
</table><br>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-left:20">
  <tr> 
  	<td><!-- ��ĵ����� ��Ÿ�� �κ� ����-->
		  <table width="820" border="0" cellspacing="0" cellpadding="0">
		    <tr> 
		      <td colspan="7" class="list_bg4"></td>
		    </tr>
		    <tr> 
		    	<td width="50" class="s3">����</td>
				<bean:define id="sort" property="rdb_sort" name="formatBookForm"></bean:define>
    		<logic:equal  name="formatBookForm" property="rdb_sort" value="1">
	       	<td width="100" class="s3">ī�װ�</td>
	       	<td width="100" class="s3">�μ�</td>
      	</logic:equal>
      	<logic:equal  name="formatBookForm" property="rdb_sort" value="2">
	       	<td width="100" class="s3">ī�װ�</td>
	       	<td width="100" class="s3">�μ�</td>
      	</logic:equal>		      
      	<logic:equal  name="formatBookForm" property="rdb_sort" value="3">
	       	<td width="100" class="s3">�μ�</td>
	       	<td width="100" class="s3">ī�װ�</td>
      	</logic:equal>		      
		      <td width="150" class="s3">����</td>
		      <td class="s3" width="280">÷������</td>
		      <td width="80" class="s3">����ũ��</td>
		      <td width="60" class="s3">���ļ���</td>
		    </tr>
		    <tr> 
		      <td colspan="7" class="list_bg4"></td>
		    </tr>
		    <tr>
		    	<td colspan="7">
		    	<html:form action="/collcomp/DataBookOrdUpdate.do" method="POST">
					<logic:notEmpty name="dataForm" property="dataList">
						<table id="mainTable" width="100%" border="0" cellspacing="0" cellpadding="0">
			     	<logic:iterate id="list" name="dataForm" property="dataList" indexId="i">
							<tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'">		        
					    	<td class="list_l" width="50">
									<bean:define id="sysdocnolist" name="list" property="sysdocno"></bean:define>
			      			<bean:define id="formseqlist" name="list" property="formseq"></bean:define>
			      			<bean:define id="tgtdeptcdlist" name="list" property="tgtdeptcd"></bean:define>
			      			<bean:define id="inputusridlist" name="list" property="inputusrid"></bean:define> 
			      			<bean:define id="fileseqlist" name="list" property="fileseq"></bean:define>
			      			<bean:define id="filenmlist" name="list" property="filenm"></bean:define>
			      			<bean:define id="categorynmlist" name="list" property="categorynm"></bean:define>
			      			<bean:define id="tgtdeptnmlist" name="list" property="tgtdeptnm"></bean:define>
			      			<bean:define id="formtitlelist" name="list" property="formtitle"></bean:define>
					    	</td>
				    	<logic:equal name="formatBookForm" property="rdb_sort" value="1">
								<td class="list_l" width="100"><bean:write name="list" property="categorynm"/></td>
					      <td class="list_l" width="100"><bean:write name="list" property="tgtdeptnm"/></td>
							</logic:equal>
      				<logic:equal  name="formatBookForm" property="rdb_sort" value="2">
								<td class="list_l" width="100"><bean:write name="list" property="categorynm"/></td>
								<td class="list_l" width="100"><bean:write name="list" property="tgtdeptnm"/></td>
							</logic:equal>					 
      				<logic:equal  name="formatBookForm" property="rdb_sort" value="3">
								<td class="list_l" width="100"><bean:write name="list" property="tgtdeptnm"/></td>
								<td class="list_l" width="100"><bean:write name="list" property="categorynm"/></td>
							</logic:equal>				     
					      <td class="list_l" width="150"><bean:write name="list" property="formtitle"/></td>
					      <td class="list_l" width="280"><a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="list" property="filenm"/>&fileName=<bean:write name="list" property="originfilenm"/>"><bean:write name="list" property="originfilenm"/></a></td>
					      <td class="list_l" width="80" style="padding-right:10;text-align:right">11,<script>convertSize(<bean:write name="list" property="filesize"/>)</script></td>
					      <td class="list_l" width="60">
					      	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					      		<tr>
					      			<td align="center" class="list_l">
					      				<bean:write name="list" property="ord"/>
								      	<img src="/images/common/btn_ord_up.gif" alt="����" style="CURSOR: hand" align="absmiddle" onclick="f_row('1')">
												<img src="/images/common/btn_ord_down.gif" alt="�Ʒ���" style="CURSOR: hand" align="absmiddle" onclick="f_row('2')"/>
								      </td>
								    </tr>
								  </table>
								  <html:hidden property="sysdocno" value="<%=sysdocnolist.toString() %>"/>
			      			<html:hidden property="formseq" value="<%=formseqlist.toString() %>"/>
			      			<html:hidden property="fileseq" value="<%=fileseqlist.toString() %>"/>
								  <html:hidden property="categorynm" value="<%=categorynmlist.toString() %>"/>
					        <html:hidden property="tgtdeptnm" value="<%=tgtdeptnmlist.toString() %>"/>
					        <html:hidden property="formtitle" value="<%=formtitlelist.toString() %>"/>
					        <html:hidden property="filenm" value="<%=filenmlist.toString() %>"/>
					        <html:hidden property="tgtdeptcd" value="<%=tgtdeptcdlist.toString() %>"/>
					        <html:hidden property="inputusrid" value="<%=inputusridlist.toString() %>"/>														        
					        <input type="hidden" id="app" name="app">
					      </td>
					    </tr>
						</logic:iterate>
						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr> 
						  	<td class="list_bg4"></td>
							</tr>
							<tr>
								<td height="40" align="right" valign="middle">
									<img src="/images/common/btn_bundle.gif" alt="��ü�ٿ�ε�" style="CURSOR: hand" onclick="bundleDownload(document.forms[0])">
									<img src="/images/common/btn_list3.gif" alt="��������" style="CURSOR: hand" onclick="javascript:ordupdate('2', document.forms[1])">
								<% if (extchk.equals("0") == true){ %>
									<a href="javascript:popup_contents('<%=sysdocno %>','<%=formseq %>','<%=sort %>',document.forms[1])"><img src="/images/common/btn_make2.gif" border="0" alt="�����ڷ����"></a>
								<% } %>
								<% if (extchk.equals("0") == false){ %>
									<a href="javascript:alert('HWP, TXT �̿��� ������ �����ڷ�������� ���ܵ˴ϴ�.');popup_contents('<%=sysdocno %>','<%=formseq %>','<%=sort %>',document.forms[1])"><img src="/images/common/btn_make2.gif" border="0" alt="�����ڷ����"></a> 
								<% } %>
								</td>
							</tr>
							<html:hidden name="dataForm" property="rowid"/>
						</table>
					</logic:notEmpty>
					<logic:empty name="dataForm" property="dataList">
						<table id="mainTable" width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td style="display:none"></td>
								<td class="list_l">�˻��� ����� �����ϴ�</td>
							</tr>
						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr> 
					      <td class="list_bg4"></td>
					    </tr>
						</table><br>
					</logic:empty>
					</html:form>
					</td>
				</tr>
		    <tr>
		    	<td colspan="7">
		    		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		    		<html:form action="/collcomp/DataBookCompSave.do" method="POST" enctype="multipart/form-data">
			    		<input type="hidden" name="sysdocno" value="<%=sysdocno%>">
							<input type="hidden" name="formseq" value="<%=formseq%>">
		    			<tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					    <tr> 
					      <td width="100" class="s2"><img src="/images/common/dot_03.gif" width="15" height="11" align="absmiddle" alt="">�����ڷ�</td>
					      <td class="bg3"></td>
					      <td colspan="9" class="t1">
					      	<table border="0" cellspacing="0" cellpadding="0">
				      	<logic:notEmpty name="dataForm" property="filenm">
				      		<tr>
				      			<td>&nbsp;<a class="list_s2" target="actionFrame" href="/download.do?tempFileName=<bean:write name="dataForm" property="filenm"/>&fileName=<bean:write name="dataForm" property="originfilenm"/>"><bean:write name="dataForm" property="originfilenm"/></a></td>
										<td align="right">&nbsp;&nbsp;<img src="/images/common/btn_del.gif" alt="����" style="CURSOR: hand" onclick="javascript:DataBookCompDelete(document.forms[2])"></td>
									</tr>
								</logic:notEmpty>
								<logic:empty name="dataForm" property="filenm">
									<tr>
			            	<td height="25"><input type="file" name="inputFile" style="width:590" contentEditable="false" onkeydown="if(event.keyCode == 13)return false;"></td>
				            <td align="right">&nbsp;&nbsp;<img src="/images/common/btn_record.gif" alt="���" style="CURSOR: hand" onclick="javascript:DataBookCompSave(document.forms[2])"></td>
				        	</tr>
				        	<tr>
				        		<td colspan="2" height="25">&nbsp;&nbsp;��&nbsp;<font color="red">10Mbyte</font>&nbsp;������ ������ �����ϼ���</td>
				        	</tr>
		            </logic:empty>
		            	</table>
			        	</td>
					    </tr>
					    <tr> 
					      <td colspan="11" class="list_bg4"></td>
					    </tr>
					   </html:form>
		    		</table>
		    	</td>
		    </tr>
			</table>
  	</td><!-- ��ĵ����� ��Ÿ�� �κ� ��-->
  </tr>
</table><br>
<script>
initTableRows();
</script>
<form name="mergeForm" method="post" action="/collcomp/DataBookMerge.do">
	<input type="hidden" name="sysdocno">
	<input type="hidden" name="formseq">
	<input type="hidden" name="filenmlist">
	<input type="hidden" name="contents">
</form>
</logic:notEmpty>

<html:messages id="msg" message="true">
	<script>alert('<bean:write name="msg"/>');</script>
</html:messages>
<jsp:include page="/include/tail.jsp" flush="true"/>