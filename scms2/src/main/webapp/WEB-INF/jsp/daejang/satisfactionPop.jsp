<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>수의계약 관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		$(document).ready(function(){
						
		});
		
		function fn_downloadFile(FID){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
			comSubmit.addParam("ID", FID);
			comSubmit.submit();
		}
	</script>
	
	
<style>

input:read-only { 
	background-color:#ddd;
	}
.tbl_fieldT > .btn {color: #000 !important; padding: 5px 15px;background-color: #eee; vertical-align:text-bottom;}
    
</style>	
</head>

<body>
<form name="form1" id="form1">
<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/scms/images/popup_title.png" align="absmiddle">업체정보</td>
      </tr>
      <tr>
        <td class="margin_title"></td>
      </tr>
    </table>
    <!--페이지 타이틀 끝 -->
    </td>
  </tr>
  
  <tr>
    <td class="pupup_frame" style="padding-right:12px">

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="15">
                                              		
		                
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="margin_btn" colspan="2"></td>
          </tr>
        </table>
        
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	          <tr>
	            <td width="100%" colspan="6" class="tbl_fieldT">	            	            
		            	부서만족도 평가
	            </td>	           							
	           </tr>
	          <tr>
	            <td width="120" class="tbl_fieldc">과업명</td>
	            <td width="101" class="tbl_fieldc">과업년월</td>
	            <td width="111" class="tbl_fieldc">계약금액</td>
	            <td width="201" class="tbl_fieldc">소관부서</td>
	            <td width="171" class="tbl_fieldc">평가결과</td>
	            <td width="123" class="tbl_fieldc">체크리스트</td>					
	          </tr> 
	           <c:forEach var="row" items="${satisfactionList}" varStatus="status" end="10">
	          <tr>
	            <td width="120" class="tbl_list_center">${row.CNRT_NM}</td>
	            <td width="101" class="tbl_list_center"><c:out value="${fn:substring(row.CNRT_YEAR,0,4)}"/>.<c:out value="${fn:substring(row.CNRT_YEAR,4,6)}"/></td>
	            <td width="111" class="tbl_list_center"><fmt:formatNumber value="${row.CNRT_AMT}" pattern="###,###,###" /></td>
	            <td width="201" class="tbl_list_center">${row.CNRT_DEPT_NM}</td>
	            <td width="171" class="tbl_list_center">
	            <c:choose> 
				    <c:when test="${row.CNRT_EVAL eq 'S'}">
				         ★&nbsp;★&nbsp;★&nbsp;★&nbsp;(매우만족)
				    </c:when> 
				    <c:when test="${row.CNRT_EVAL eq 'A'}">
				       ★&nbsp;★&nbsp;★&nbsp;(만족)
				    </c:when>
				    <c:when test="${row.CNRT_EVAL eq 'B'}">
				         ★&nbsp;★&nbsp;(보통)
				    </c:when>
				    <c:when test="${row.CNRT_EVAL eq 'C'}">
				        ★&nbsp;(불만족)
				    </c:when>				 
				</c:choose>	            
	            </td>
	            <td width="123" class="tbl_list_left">	      
	            <c:choose> 
				    <c:when test="${row.ATTACH eq 'Y'}">
				         <a href='javascript:fn_downloadFile(${row.FID})' name='filenm' ><img src="/scms/images/ico_att.gif" align="absmiddle"></a>
					     <input type='hidden' name='FID' id='FID' value="${row.FID}">
				    </c:when>
				</c:choose>    
	            </td>	          
	          </tr>
	          </c:forEach>
	      </table>
        <!-- -------------- 버튼 시작 --------------  -->
        <!-- -------------- 버튼 끝 --------------  -->
        </td>
      </tr>
    </table>
    </td>    
  </tr>
</table>
</form>
<%@ include file="/WEB-INF/include/include-body.jspf" %>

</body>
</html>
