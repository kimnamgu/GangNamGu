<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
pageContext.setAttribute("cr", "\r"); //Space
pageContext.setAttribute("cn", "\n"); //Enter
pageContext.setAttribute("crcn", "\r\n"); //Space, Enter
pageContext.setAttribute("br", "<br/>"); //br 태그
%>
<html>
<head>
<title>주요사업관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
	var gfv_count = 1;
	
	$(document).ready(function(){
		
		$("#print").on("click", function(e){ //프린트
			e.preventDefault();
			window.print();
			//fn_print();
		});
		
		$("#list").on("click", function(e){ //목록으로 버튼
			//alert('aa');
			e.preventDefault();
			fn_mainSaupList();			
		});
		
	});
	
	function fn_print() { 
      if (document.all && window.print) { 
        //window.onbeforeprint = bef; 
        //window.onafterprint = aft; 
        window.print(); 
      } 
    } 
    
    function bef() { 
      if (document.all) { 
        contents.style.display = 'none'; 
        printArea.innerHTML = document.all['printArea'].innerHTML;
      } 
    }
 
    function aft() { 
      if (document.all) { 
        contents.style.display = 'block'; 
        printArea.innerHTML = ""; 
      } 
    } 
	    
	function fn_mainSaupList(){
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/daejang/mainSaupList.do?year=${param.year}&sgb=${param.sgb}' />");
		comSubmit.submit();
	}
				
</script>
</head>

<body>
<form id="form1" name="form1" enctype="multipart/form-data">
<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="INS_DEPT" name="INS_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="SAUP_FIELD1_GB" name="SAUP_FIELD1_GB" value="">
<input type="hidden" id="SAUP_FIELD2_GB" name="SAUP_FIELD2_GB" value="">
<input type="hidden" id="SAUP_FIELD2_DETAIL" name="SAUP_FIELD2_DETAIL" value="">
<input type="hidden" id="SAUP_DETAIL" name="SAUP_DETAIL" value="">
<input type="hidden" id="SAUP_START_DATE" name="SAUP_START_DATE" value="">
<input type="hidden" id="SAUP_END_DATE" name="SAUP_END_DATE" value="">
<input type="hidden" id="SAUP_DEPT_CD" name="SAUP_DEPT_CD" value="">
<input type="hidden" id="SAUP_DEPT_NM" name="SAUP_DEPT_NM" value="">
<input type="hidden" id="SAUP_DEPT_GWAJANG" name="SAUP_DEPT_GWAJANG" value="">
<input type="hidden" id="SAUP_DEPT_TEAMJANG" name="SAUP_DEPT_TEAMJANG" value="">
<input type="hidden" id="SAUP_PERSON_ID" name="SAUP_PERSON_ID" value="">
<input type="hidden" id="SAUP_PERSON_NM" name="SAUP_PERSON_NM" value="">
<input type="hidden" id="SAUP_PERSON_TEL" name="SAUP_PERSON_TEL" value="">
<div id="contents">
<div id="printArea" >
<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/bims/images/popup_title.gif" align="absmiddle">공약·역점 사업</td>
      </tr>
      <tr>
        <td class="margin_title"></td>
      </tr>
    </table>
    <!--페이지 타이틀 끝 -->
    
     <table width="100%" border="0" cellspacing="0" cellpadding="0">
	 <tr>
		<td width="100%">
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="title"><img src="/bims/images/title_ico.gif" align="absmiddle">세부내용</td>
			</tr>
		</table>
		</td>				
	</tr>
	</table>
	
    </td>
  </tr>
  
  <tr>
    <td class="pupup_frame" style="padding-right:12px">

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="15">
                
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	          <tr>
	            <td width="140" class="tbl_field">사업명</td>
	            <td width="710" class="tbl_list_left">
	                ${map.SAUP_NM}	                
	            </td>
	          </tr>
	          <tr>
	            <td width="140" class="tbl_field">사업설명(요약)</td>
	            <td width="710" class="tbl_list_left">	               
	                ${fn:replace(map.SAUP_DETAIL, crcn, br)}	                
	            </td>
	          </tr>
	          <tr>
	            <td width="140" class="tbl_field">예산금액</td>
	            <td width="710" class="tbl_list_left">
	               <fmt:formatNumber value="${map.SAUP_BUDGET_AMT}" pattern="###,###,###" /> 천원              
	            </td>
	          </tr>	          
	          <tr>
	            <td width="140" class="tbl_field">목표시기</td>
	            <td width="710" class="tbl_list_left">	                
	                <fmt:parseDate value="${map.SAUP_OPEN_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM"/>	                
	            </td>
	          </tr>
	          <tr>
	            <td width="140" class="tbl_field">2020년 진척률</td>
	            <td width="710" class="tbl_list_left">	                
	                ${map.SAUP_PROCESS_PERCENT} % 달성               
	            </td>
	          </tr>
	          <tr>
	            <td width="140" class="tbl_field">최종목표 대비 진척률</td>
	            <td width="710" class="tbl_list_left">	                
	                ${map.SAUP_PROCESS_TOT_PERCENT} % 달성               
	            </td>
	          </tr>
	          <tr>
	            <td width="140" class="tbl_field">지난 추진내용<br><br>(<fmt:parseDate value="${map.THWK_ST_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/> ~ <fmt:parseDate value="${map.THWK_ED_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/>)</td>
	            <td width="710" class="tbl_list_left">					
					${fn:replace(fn:replace(fn:replace(map.SAUP_PUSH_CONTENT, "<!--[if !supportEmptyParas]-->", ""),"<!--[endif]-->", ""), "<!--[if !vml]-->", "")}					                	               
	            </td>
	          </tr>
	          <tr>
	            <td width="140" class="tbl_field">이번 추진계획<br><br>(<fmt:parseDate value="${map.NTWK_ST_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/> ~ <fmt:parseDate value="${map.NTWK_ED_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/>)</td>
	            <td width="710" class="tbl_list_left">	                
	                ${fn:replace(fn:replace(fn:replace(map.SAUP_NEXT_CONTENT, "<!--[if !supportEmptyParas]-->", ""),"<!--[endif]-->", ""), "<!--[if !vml]-->", "")}	                                
	            </td>
	          </tr>
	          <tr>
	            <td width="140" class="tbl_field">담당부서</td>
	            <td width="710" class="tbl_list_left">	      
	                ${map.SAUP_DEPT_NM} &nbsp; 과장 : ${map.SAUP_DEPT_GWAJANG} ☎ ${map.SAUP_GWAJANG_TEL} &nbsp;&nbsp;&nbsp; 팀장 : ${map.SAUP_DEPT_TEAMJANG} ☎ ${map.SAUP_TEAMJANG_TEL} &nbsp;&nbsp;&nbsp; 담당 : ${map.SAUP_DAMDANG_NM} ☎ ${map.SAUP_DAMDANG_TEL}  	                
	            </td>
	          </tr>
	      </table>
        <!-- -------------- 버튼 시작 --------------  -->
        <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td></td>
            <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>                				               
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/bims/images/btn_type1_head.gif"></td>
                    <td background="/bims/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="print">프린트</a></td>
                    <td><img src="/bims/images/btn_type1_end.gif"></td>
                  </tr>
                </table>   
                </td>
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/bims/images/btn_type1_head.gif"></td>
                    <td background="/bims/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
                    <td><img src="/bims/images/btn_type1_end.gif"></td>
                  </tr>
                </table>   
                </td>                               
              </tr>
            </table>
            </td>
          </tr>
          <tr>
            <td class="margin_btn" colspan="2"></td>
          </tr>
        </table>
        <!-- -------------- 버튼 끝 --------------  -->
        </td>
      </tr>
    </table>    
    </td>    
  </tr>
</table>
</form>
</div>
</div>
<%@ include file="/WEB-INF/include/include-body.jspf" %>
</body>
</html>
