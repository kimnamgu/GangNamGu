<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
pageContext.setAttribute("cr", "\r"); //Space
pageContext.setAttribute("cn", "\n"); //Enter
pageContext.setAttribute("crcn", "\r\n"); //Space, Enter
pageContext.setAttribute("br", "<br/>"); //br 태그
%>
<html>
<head>
<title>수의계약 관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var tmp2 = "";
		var i = 1;
		$(document).ready(function(){
			
			
						
			$("#record").on("click", function(e){ //
				e.preventDefault();								
				fn_recordList();				
			});
			
			$("#reason").on("click", function(e){ //
				e.preventDefault();								
				fn_reasonList();				
			});
			
			$("#eval").on("click", function(e){ //
				e.preventDefault();								
				fn_evalList();				
			});
			
			
			$("#reg").on("click", function(e){ //
				e.preventDefault();								
				fn_BasicInfo();				
			});
			
			
			$("a[name='filenm']").on("click", function(e){ //파일명 
				e.preventDefault();
				fn_downloadFile($(this));
			});
			
			$("a[name='filenm2']").on("click", function(e){ //파일명 
				e.preventDefault();
				fn_downloadFile($(this));
			});
			
			$("a[name='file']").on("click", function(e){ //파일 이름
				e.preventDefault();
				fn_downloadFile($(this));
			});
						
		});
		
		
		
		function fn_recordList(){			
			var comSubmit = new ComSubmit();
			comSubmit.addParam("ID", "${map.ID}");			
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtCompRecordList.do' />");
			comSubmit.submit();
			
		}
		
		
		function fn_reasonList(){			
			var comSubmit = new ComSubmit();			
			comSubmit.addParam("ID", "${map.ID}");			
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtReasonList.do' />");
			comSubmit.submit();
		}
		
		
		function fn_evalList(){			
			var comSubmit = new ComSubmit();			
			comSubmit.addParam("ID", "${map.ID}");			
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtEvalList.do' />");
			comSubmit.submit();
		}
		
		function fn_BasicInfo(){
			var comSubmit = new ComSubmit();
			
			comSubmit.addParam("ID", "${map.ID}");
			comSubmit.addParam("PARENT_ID", "${map.ID}");
			comSubmit.addParam("BOARD_GB", "5");  //실적 1, 평가 2 발주계획 3 기술자보유 4 업체등록 5
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtCompInfo.do' />");			
			comSubmit.submit();
		}
		
		
		function fn_downloadFile(obj){
			var idx = obj.parent().find("#FID").val();		
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
			comSubmit.addParam("ID", idx);
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
<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="INS_DEPT" name="INS_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${map.INS_ID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="COMP_ADDR" name="COMP_ADDR" value="">
<input type="hidden" id="COMP_ADDR_ROAD" name="COMP_ADDR_ROAD" value="">
<input type="hidden" id="COMP_CAPITAL_DETAIL" name="COMP_CAPITAL_DETAIL" value="">


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
	            <td width="827" colspan="6" class="tbl_fieldT">업체 기본정보</td>	           							
	          </tr>
	      	  <tr>
	            <td width="120" class="tbl_field">업체명(대표자)</td>
	            <td width="707" colspan="5" class="tbl_list_left">${map.COMP_NM} (${map.COMP_HEAD_NM})</td>									
	          </tr>	         
	          <tr>
	            <td width="120" class="tbl_field">사업자번호</td>
	            <td width="707" colspan="5" class="tbl_list_left"><c:out value="${fn:substring(map.COMP_SAUP_NO,0,3)}"/>-<c:out value="${fn:substring(map.COMP_SAUP_NO,3,5)}"/>-<c:out value="${fn:substring(map.COMP_SAUP_NO,5,10)}"/> (${map.COMP_BUBIN_NO})</td>									
	          </tr>                 				 	          
	          <tr>
	            <td width="120" class="tbl_field">주소</td>
	            <td width="707" colspan="5" class="tbl_list_left">${map.COMP_ADDR}<br>( ${map.COMP_ADDR_ROAD} )</td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">전화번호</td>
	            <td width="707" colspan="5" class="tbl_list_left">${map.COMP_TEL}</td>									
	          </tr>	          	         
	          <tr>
	            <td width="120" class="tbl_field">업체규모</td>
	            <td width="707" colspan="5" class="tbl_list_left">${map.COMP_SIZE}</td>									
	          </tr>	          
	          <tr>
	            <td width="120" class="tbl_field">재무상태</td>
	            <td width="707" colspan="5" class="tbl_list_left"><fmt:formatNumber value="${map.COMP_CAPITAL}" pattern="###,###,###" /> 원</td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">상근직원수</td>
	            <td width="707" colspan="5" class="tbl_list_left">${map.COMP_EMPLOYEE_CNT} 명</td>									
	          </tr>	        
	          <tr>
	            <td width="120" class="tbl_field">업무유형</td>
	            <td width="707" colspan="5" class="tbl_list_left">
	            <c:choose> 
				    <c:when test="${map.COMP_FIELD1_GB eq '100'}">
				         공사
				    </c:when> 
				    <c:when test="${map.COMP_FIELD1_GB eq '010'}">
				         용역 
				    </c:when>
				    <c:when test="${map.COMP_FIELD1_GB eq '001'}">
				         물품
				    </c:when>
				    <c:when test="${map.COMP_FIELD1_GB eq '110'}">
				         공사,용역 
				    </c:when>
				    <c:when test="${map.COMP_FIELD1_GB eq '101'}">
				         공사,물품
				    </c:when>
				    <c:when test="${map.COMP_FIELD1_GB eq '011'}">
				         용역,물품
				    </c:when>
				    <c:when test="${map.COMP_FIELD1_GB eq '111'}">
				         공사,용역,물품
				    </c:when>			 
				</c:choose>	            
	            </td>									
	          </tr>	         	          	          
	          <tr>
	            <td width="120" class="tbl_field">주요과업사항</td>
	            <td width="707" colspan="5" class="tbl_list_left">${fn:replace(map.COMP_MAIN_WORK, crcn, br)}</td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">회사소개서</td>
	            <td colspan="5" width="707" class="tbl_list_left">
					<c:forEach var="row" items="${list }">
						<p>
							<input type="hidden" id="FID" value="${row.ID }">
							<a href="#this" name="file">${row.ORIGINAL_FILE_NAME }</a> 
							(${row.FILE_SIZE }kb)
						</p>
					</c:forEach>
				</td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">현장점검 출장복명서</td>
	            <td colspan="5" width="707" class="tbl_list_left">
					<c:forEach var="row" items="${list6 }">
						<p>
							<input type="hidden" id="FID" value="${row.ID }">
							<a href="#this" name="file">${row.ORIGINAL_FILE_NAME }</a> 
							(${row.FILE_SIZE }kb)
						</p>
					</c:forEach>
				</td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">비고</td>
	            <td width="707" colspan="5" class="tbl_list_left">${fn:replace(map.BIGO, crcn, br)}</td>									
	          </tr>
	          <tr>
	            <td width="100%" colspan="6" class="tbl_fieldT">
	            	<table width="100%" border="0">
		            <tr>
		            <td width="85%" class="tbl_fieldT">과업실적 및 기술능력 정보</td>
		            </tr>
		            </table>	            		           
	            </td>	           							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_fieldc">항목</td>
	            <td width="101" class="tbl_fieldc">계약년월</td>
	            <td width="111" class="tbl_fieldc">발주처</td>
	            <td width="201" class="tbl_fieldc">사업명</td>
	            <td width="171" class="tbl_fieldc">과업내용</td>
	            <td width="123" class="tbl_fieldc">계약금액(원)</td>									
	          </tr>
	          <c:forEach var="row" items="${list1}" varStatus="status" end="10">
	          <tr>
	          	<c:if test="${status.count == 1}">
	            <td rowspan="${fn:length(list1)}" width="120" class="tbl_fieldc">과업실적</td>
	            </c:if>
	            <td width="101" class="tbl_list_center"><c:out value="${fn:substring(row.CNRT_YEAR,0,4)}"/>.<c:out value="${fn:substring(row.CNRT_YEAR,4,6)}"/></td>
	            <td width="111" class="tbl_list_center">${row.CNRT_AGENCY_NM}</td>
	            <td width="201" class="tbl_list_center">${row.CNRT_NM} 
	            <c:choose> 
				    <c:when test="${row.ATTACH eq 'Y'}">
				         <a href='#this' name='filenm2' ><img src="/scms/images/ico_att.gif" align="absmiddle"></a>
					     <input type='hidden' name='FID' id='FID' value="${row.FID}">
				    </c:when>
				</c:choose>
	            </td>
	            <td width="171" class="tbl_list_center">${fn:replace(row.CNRT_DETAIL, crcn, br)}</td>
	            <td width="123" class="tbl_list_center"><fmt:formatNumber value="${row.CNRT_AMT}" pattern="###,###,###" /></td>		
	          </tr>
	          </c:forEach>
	          <tr>
	            <td width="120" class="tbl_fieldc">항목</td>
	            <td width="101" class="tbl_fieldc">성명</td>
	            <td width="111" class="tbl_fieldc">자격(면허)</td>
	            <td width="372" colspan=2 class="tbl_fieldc">주요경력</td>
	            <td width="123" class="tbl_fieldc">비고</td>	            									
	          </tr>
	          <c:forEach var="row" items="${list2}" varStatus="status" end="10">
	          <tr>
	          	<c:if test="${status.count == 1}">
	            <td rowspan="${fn:length(list2)}" width="120" class="tbl_fieldc">기술자 보유현황</td>
	            </c:if>
	            <td width="101" class="tbl_list_center">${row.EXPERT_NM}</td>
	            <td width="111" class="tbl_list_center">${row.RIGHT_NM}</td>
	            <td width="372" colspan=2 class="tbl_list_center">${fn:replace(row.MAIN_CAREER, crcn, br)}</td>
	            <td width="123" class="tbl_list_center">${fn:replace(row.BIGO, crcn, br)}</td>	          
	          </tr>
	          </c:forEach>
	           <tr>
	            <td width="100%" colspan="6" class="tbl_fieldT">
	            <table width="100%" border="0">
	            <tr>
	            <td width="85%" class="tbl_fieldT">업체선정 사유</td>
	            </tr>
	            </table>
	            </td>	                  						
	           </tr>
	          <tr>
	            <td width="120" class="tbl_fieldc">계약명</td>
	            <td width="101" class="tbl_fieldc">계약일</td>
	            <td width="312" colspan=2 class="tbl_fieldc">선정사유</td>
	            <td width="171" class="tbl_fieldc">소관부서</td>
	            <td width="123" class="tbl_fieldc">담당자</td>					
	          </tr> 
	          <c:forEach var="row" items="${list3}" varStatus="status" end="10">
	          <tr>
	            <td width="120" class="tbl_list_center">${row.CNRT_NM}</td>
	            <td width="101" class="tbl_list_center"><c:out value="${fn:substring(row.CNRT_YEAR,0,4)}"/>.<c:out value="${fn:substring(row.CNRT_YEAR,4,6)}"/></td>
	            <td width="312" colspan=2 class="tbl_list_center">${fn:replace(row.CNRT_CHOOSE_REASON, crcn, br)}</td>
	            <td width="171" class="tbl_list_center">${row.CNRT_DEPT_NM}</td>
	            <td width="123" class="tbl_list_center">${row.CNRT_PERSON_NM}</td>	          
	          </tr>
	          </c:forEach>
	          <tr>
	            <td width="100%" colspan="6" class="tbl_fieldT">	            	            
		            <table width="100%" border="0">
		            <tr>
		            <td width="85%" class="tbl_fieldT">부서만족도 평가</td>
		            </tr>
		            </table>
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
	           <c:forEach var="row" items="${list4}" varStatus="status" end="10">
	          <tr>
	            <td width="120" class="tbl_list_center">${row.CNRT_NM}</td>
	            <td width="101" class="tbl_list_center"><c:out value="${fn:substring(row.CNRT_YEAR,0,4)}"/>.<c:out value="${fn:substring(row.CNRT_YEAR,4,6)}"/></td>
	            <td width="111" class="tbl_list_center"><fmt:formatNumber value="${row.CNRT_AMT}" pattern="###,###,###" /></td>
	            <td width="201" class="tbl_list_center">${row.CNRT_DEPT_NM}</td>
	            <td width="171" class="tbl_list_center">
	            <c:choose> 
				    <c:when test="${row.CNRT_EVAL eq 'S'}">
				         매우만족
				    </c:when> 
				    <c:when test="${row.CNRT_EVAL eq 'A'}">
				         만족 
				    </c:when>
				    <c:when test="${row.CNRT_EVAL eq 'B'}">
				         보통
				    </c:when>
				    <c:when test="${row.CNRT_EVAL eq 'C'}">
				         불만족 
				    </c:when>				 
				</c:choose>	            
	            </td>
	            <td width="123" class="tbl_list_left">	      
	            <c:choose> 
				    <c:when test="${row.ATTACH eq 'Y'}">
				         <a href='#this' name='filenm' ><img src="/scms/images/ico_att.gif" align="absmiddle"></a>
					     <input type='hidden' name='FID' id='FID' value="${row.FID}">
				    </c:when>
				</c:choose>    
	            </td>	          
	          </tr>
	          </c:forEach>
	      </table>
	      
	     
	      
        <!-- -------------- 버튼 시작 --------------  -->
        <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td></td>
            <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <td><img src="/scms/images/btn_type1_head.gif"></td>
                   <td background="/scms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="javascript:history.back();" class="btn" id="list">목록</a></td>
                   <td><img src="/scms/images/btn_type1_end.gif"></td>
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
<%@ include file="/WEB-INF/include/include-body.jspf" %>

</body>
</html>
