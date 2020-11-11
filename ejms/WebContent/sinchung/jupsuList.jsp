<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 접수내역 목록
 * 설명:
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
<html>
<head>
<title>접수내역</title>
<base target="_self">
<link href="/css/style.css" rel="stylesheet" type="text/css">	
<script src="/script/common.js"></script>	
<script src="/script/Calendar.js"></script>
<script language="javascript">
	function endProc(){
		alert('정상적으로 처리가 완료되었습니다.');
		return;
	}
	
	//EXCEL 변환
  function xlsDownload(){
		var oldTarget = document.forms[0].target;
		var oldAction = document.forms[0].action;
		document.forms[0].target = 'actionFrame';
		document.forms[0].action = '/jupsuXls.do';
		document.forms[0].submit();
		document.forms[0].target = oldTarget;
		document.forms[0].action = oldAction;
  }
  
  function jupsuProc(gbn) {
	  var reqseq = "0";
  	if ( gbn == 'all' ) {
  		if ( !confirm("검색결과와 관계없이 전체 신청서가\n[접수완료] 또는 [접수보류]로 일괄접수처리됩니다.\n\n처리하시겠습니까?") ) {
  			return;
  		}
  	} else if ( gbn == 'select' ) {
 			var isChecked = false;
	  	var chklst = document.getElementsByName('listjupsuproc[]');
	  	for(var i = 0; i < chklst.length; i++) {
				if ( chklst[i].checked == true ) {
					reqseq += "," + chklst[i].value;
					isChecked = true;
				}
			}
	  	if ( !isChecked ) {
	  		alert("처리할 신청서를 선택하세요");
	  		return;
	  	} else if ( !confirm("처리상태가 [접수완료]인 것은 [접수보류]로\n처리상태가 [접수보류]인 것은 [접수완료]로 처리됩니다.\n\n처리하시겠습니까?") ) {
		  	return;
		  }  		
  	}
  	
		var oldTarget = document.forms[0].target;
		var oldAction = document.forms[0].action;
		document.forms[0].target = 'actionFrame';
		document.forms[0].action = '/procJupsu.do?reqseq=' + reqseq + '&gbn=' + gbn;
		document.forms[0].submit();
		document.forms[0].target = oldTarget;
		document.forms[0].action = oldAction; 
  }
  
  function check_all(chk_parent, chk_child)	{
		var chkall = chk_parent;
		var chklst = document.getElementsByName(chk_child);
		
		for(var i = 0; i < chklst.length; i++)
			chklst[i].checked = chkall.checked;
	}
	
	function setSearchDate() {
		var frm = document.forms[0];
		var a = frm.strdt;			//문서작성일시작
		var b = frm.enddt;			//문서작성일끝
		
		if (a.onpropertychange == null) {
			a.onpropertychange = setSearchDate;
			b.onpropertychange = setSearchDate;
		} else if (event.propertyName.toLowerCase() == "value") {
			var src = event.srcElement;
			if (src == a) {
				if (a.value > b.value) {
					b.onpropertychange = null;
					b.value = a.value;
					b.onpropertychange = setSearchDate;
				}
			} else if (src == b) {
				if (a.value > b.value || a.value == "") {
					a.onpropertychange = null;
					a.value = b.value;
					a.onpropertychange = setSearchDate;
				}
			}
		}
	}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px" onload="return;fullSizeWindow()">
<html:form method="POST" action="/jupsuList">
<html:hidden property="reqformno"/>
<table width="660" border="0" cellspacing="0" cellpadding="0" style="padding:0 10 10 10">
	<tr>
		<td background="/images/common/title_bg.gif" height="38"><img src="/images/collect/title_31.gif" alt=""></td>
	</tr>
	<tr> 
	  <td> 
	    <table width="660" border="0" cellspacing="0" cellpadding="0">
	      <tr> 
	        <td colspan="3" class="list_bg2"></td>
	      </tr>
	      <tr> 
	        <td width="100" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">신청서명</td>
	        <td class="bg2"></td>
	        <td class="t1"><bean:write name="dataListForm" property="title"/></td>
	      </tr>
	      <tr> 
	        <td colspan="3" class="bg1"></td>
	      </tr>
	      <tr> 
	        <td width="100" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">접수담당자</td>
	        <td class="bg2"></td>
	        <td class="t1"><b><font color="#008CD4">[<bean:write name="dataListForm" property="coldeptnm"/>]<bean:write name="dataListForm" property="chrgusrnm"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;문의전화 : <bean:write name="dataListForm" property="coltel"/></font><b></td>
	      </tr>
	      <tr> 
	        <td colspan="3" class="bg1"></td>
	      </tr>
	      <tr> 
	        <td width="100" class="s1"><img src="/images/common/dot_02.gif" width="15" height="11" align="absmiddle" alt="">접수기간</td>
	        <td class="bg2"></td>
	        <td class="t1">
	        	<logic:present name="dataListForm" property="enddt">
	          	<bean:write name="dataListForm" property="strdt"/>&nbsp;부터&nbsp;&nbsp;&nbsp;<bean:write name="dataListForm" property="enddt"/>&nbsp;까지
	      		</logic:present>
	      		<logic:notPresent name="dataListForm" property="enddt">
	      			<bean:write name="dataListForm" property="strdt"/>&nbsp;부터&nbsp;&nbsp;&nbsp;제한없음
	      		</logic:notPresent>
					</td>
	      </tr>
	      <tr> 
	        <td colspan="3" class="list_bg2"></td>
	      </tr>
	    </table>
	  </td>
	</tr>
	<tr>
		<td height="10"></td>
	</tr>
	<tr> 
	  <td> 
	    <table width="660" border="0" cellspacing="2" cellpadding="0" bgcolor="#ECF3F9" height="60">
	      <tr> 
	        <td bgcolor="#FFFFFF"> 
	          <!-- 접수내역 리스트 테이블 시작-->
	          <table width="650" border="0" cellspacing="0" cellpadding="0" height="50" align="center" bgcolor="#F7F7F7">
	          	<tr> 
	              <td width="15" align="right"><img src="/images/common/dot.gif" alt=""></td>
	              <td width="55" align="center"><font color="#4F4F4F">정렬순서</font></td>
	              <td width="75">
	              	<html:select property="gbn" onchange="submit()" style="width:100%;background-color:white">
	              		<html:option value="1">신청일시</html:option>
	              		<html:option value="2">부서</html:option>
	              		<html:option value="3">성명</html:option>
	              	</html:select>
	              </td>
	              <td colspan="6"></td>
	            </tr>
	            <tr> 
	              <td width="15" align="right"><img src="/images/common/dot.gif" alt=""></td>
	              <td width="55" align="center"><font color="#4F4F4F">신 청 자</font></td>
	              <td width="75"><html:text property="presentnm" style="width:95%;background-color:white"/></td>
	              <td width="50"><a href="javascript:document.forms[0].submit()"><img src="/images/common/btn_search.gif" alt=""></a></td>
	              <td width="15" align="right"><img src="/images/common/dot.gif" alt=""></td>
	              <td width="55" align="center"><font color="#4F4F4F">접수기간</font></td>
	              <td width="225">
	              	<html:text property="strdt" style="text-align:center;width:70;background-color:white" maxlength="10" readonly="true"/>&nbsp;<img src="/images/common/icon_date.gif" width="16" height="15" align="absmiddle" alt="달력" style="cursor:hand" onclick="Calendar_D(forms[0].strdt);">&nbsp;~
									<html:text property="enddt" style="text-align:center;width:70;background-color:white" maxlength="10" readonly="true"/>&nbsp;<img src="/images/common/icon_date.gif" width="16" height="15" align="absmiddle" alt="달력" style="cursor:hand" onclick="Calendar_D(forms[0].enddt);">&nbsp;<img src="/images/common/btn_icon_del.gif" style="cursor:hand" align="absmiddle" onclick="strdt.value=enddt.value='';submit();" alt="접수기간 초기화">
								</td>
								<td width="70"><html:checkbox property="procGbn" value="0" style="border:0;background-color:transparent;" onclick="submit()"/>미처리건</td>
	              <td width="80" align="right"><font class="result_no">[총 <%=request.getAttribute("totalCount")%>건]</font></td>
	            </tr>
	          </table>
	          <!-- 접수내역 리스트 테이블 끝-->
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
  <tr> 
    <td> 
      <table width="660" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="7" class="list_bg"></td>
        </tr>
        <tr> 
          <td colspan="7" height="1"></td>
        </tr>
        <tr> 
          <td class="list_t" width="20"><input type="checkbox" name="checkall" style="border:0;background-color:transparent;" onclick="check_all(this, 'listjupsuproc[]')"></td>
          <td class="list_t" width="58">접수번호</td>
          <td class="list_t" width="232">부서</td>
          <td class="list_t" width="55">성명</td>
          <td class="list_t" width="68">신청일시</td>
          <td class="list_t" width="169">최종승인자(결재시)</td>
          <td class="list_t" width="58">처리상태</td>
        </tr>
        <tr> 
          <td colspan="7" height="1"></td>
        </tr>
        <tr> 
          <td colspan="7" class="list_bg"></td>
        </tr>
        <tr> 
          <td colspan="7" height="1"></td>
        </tr> 
 		<logic:notEmpty name="dataListForm" property="dataList">
      <logic:iterate id="list" name="dataListForm" property="dataList" indexId="i">
	      <tr <% if (i.intValue() % 2 != 0) out.print("class=\"list_bg3\" onMouseOut=\"this.style.background='#F5FAFF'\"");%> onMouseOut="this.style.background='#ffffff'" onMouseOver="this.style.background='#FFFAD1'" onclick="if(event.srcElement.name!='listjupsuproc[]'){window.open('/jupsuView.do?reqformno=<bean:write name="list" property="reqformno"/>&reqseq=<bean:write name="list" property="reqseq"/>','jupsuview','width=705,height=500,status=no,scrollbars=yes')}" style="cursor:hand">
		      <td align="center"><input type="checkbox" name="listjupsuproc[]" value="<bean:write name="list" property="reqseq"/>" style="border:0;background-color:transparent;"></td>
          <td class="list_no" align="center"><bean:write name="list" property="reqseq"/></td>
          <td class="list_s" align="center"><bean:write name="list" property="position"/></td>
          <td class="list_s" align="center"><bean:write name="list" property="presentnm"/></td>
          <td class="list_s" align="center"><bean:write name="list" property="crtdt"/></td>
          <td class="list_s" align="center"><bean:write name="list" property="lastsanc"/></td>
          <td class="list_s" align="center"><bean:write name="list" property="statenm"/></td>
        </tr>
        <tr> 
          <td colspan="7" class="list_bg2"></td>
        </tr>
      </logic:iterate>
		</logic:notEmpty>
			<logic:empty name="dataListForm" property="dataList">
				<tr> 
          <td colspan="7" class="list_l">검색된 목록이 없습니다</td>
        </tr>
        <tr> 
          <td colspan="7" class="list_bg2"></td>
        </tr>
			</logic:empty>
      </table>
    </td>
  </tr>
  <tr> 
    <td height="20" align="center"><%=nexti.ejms.util.commfunction.procPage("/jupsuList.do",(java.util.HashMap)request.getAttribute("search"),request.getAttribute("totalPage").toString(),request.getAttribute("currpage").toString())%></td>
  </tr>
	<tr> 
    <td>
    	<table width="660" border="0" cellspacing="0" cellpadding="0">
	      <tr> 
	        <td><a href="javascript:xlsDownload()"><img src="/images/common/btn_excel.gif" alt="엑셀다운로드"></a></td>
	        <td align="right">
		        <a href="javascript:jupsuProc('select')"><img src="/images/common/btn_jupsuselect.gif" alt="선택접수처리"></a>
	        	<a href="javascript:jupsuProc('all')"><img src="/images/common/btn_jupsuall.gif" alt="일괄접수처리"></a>
           	<a href="javascript:window.close()"><img src="/images/common/btn_close2.gif" alt="닫기"></a>
	        </td>
	      </tr>
	    </table>
		</td>
  </tr>
</table>
</html:form>
<script>
setSearchDate();	//문서작성일,자료기준일 유효성체크 이벤트 세팅
</script>
<iframe name="actionFrame" width="0" height="0" title=""></iframe>
</body>
</html>