<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>공약·역점 사업관리시스템</title>
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
			fn_ffemsDataList();			
		});
		
		
		$("#logout").on("click", function(e){ //로그아웃				
			e.preventDefault();				
			fn_logout();
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
	    
	function fn_ffemsDataList(){
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/daejang/ffemsDataList.do' />");
		comSubmit.submit();
	}
	
	function fn_logout(){
		var comSubmit = new ComSubmit();			
		comSubmit.setUrl("<c:url value='/logout.do' />");
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
        <td class="pupup_title"><a href="#this" class="btn" id="logout"><img src="/ffems/images/popup_title.gif" align="absmiddle"></a>신속집행현황</td>
      </tr>
      <tr>
        <td class="margin_title"></td>
      </tr>
    </table>
    <!--페이지 타이틀 끝 -->
    </td>
  </tr>
  <tr>
    <td>  
      &nbsp;	
    </td>
  </tr>
  <tr>
    <td>  
      <div class="tit_detail">2020년도 신속집행 목표액 및 집행현황</div>	
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
	            <td colspan=2 class="tbl_field_cf12">기준일 : <fmt:parseDate value="${map.GIJUN_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/></td>	           
	            <td colspan=5 class="tbl_field_cf12"><fmt:parseDate value="${map.GIJUN_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="MM"/>월 집행목표 : 6월말 기준 ${map.OUT_COMMENT1}% 이상 /<fmt:parseDate value="${map.GIJUN_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="MM"/>월말 기준 100%  이상</td>
	            <td class="tbl_field_c">(단위:천원)</td>
	          </tr>
	          <tr>
	            <td rowspan=2 class="tbl_field_c">부서명</td>
	            <td rowspan=2 class="tbl_field_c">예산액(A)</td>
	            <td colspan=3 class="tbl_field_c">우리구 집행목표(누계) &nbsp;(6월말 기준)</td>
	            <td colspan=3 class="tbl_field_c">우리구 집행목표(누계) &nbsp;(<fmt:parseDate value="${map.GIJUN_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="MM"/>월말 기준)</td>
	          </tr>
	          <tr>	            
	            <td width="100" class="tbl_field_c">집행목표액 (B)</td>
	            <td width="100" class="tbl_field_c"><fmt:parseDate value="${map.GIJUN_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="MM/dd"/> 기준 <br>집행액(가)</td>
	            <td width="100" class="tbl_field_c">집행률<br>(C=가/B)</td>
	            <td width="100" class="tbl_field_c">집행목표액(D)<br>(6월말기준 목표액의 ${map.OUT_COMMENT1}%)</td>
	            <td width="100" class="tbl_field_c"><fmt:parseDate value="${map.GIJUN_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="MM/dd"/><br>집행액(가)</td>
	            <td width="100" class="tbl_field_c">집행률<br>(E=가/D)</td>
	          </tr>
	          <c:forEach var="row" items="${list}" varStatus="status" end="50">
	          <c:set var="dept_nm" value="${row.SAUP_DEPT_NM}" />	          
	          <tr>	            
	            <c:choose>				
					<c:when test="${dept_nm eq '행정국' || dept_nm eq '기획재정국' || dept_nm eq '복지생활국' || dept_nm eq '뉴디자인국' || dept_nm eq '도시환경국' || dept_nm eq '안전교통국' || dept_nm eq '보건소' || dept_nm eq '구의회사무국'}">
					<td width="140" class="tbl_listas"> ${row.SAUP_DEPT_NM}</td>					
					</c:when>
					<c:otherwise>
					<td width="140" class="tbl_list"> ${row.SAUP_DEPT_NM}</td>
					</c:otherwise>
				</c:choose>
				<c:choose>				
					<c:when test="${dept_nm eq '행정국' || dept_nm eq '기획재정국' || dept_nm eq '복지생활국' || dept_nm eq '뉴디자인국' || dept_nm eq '도시환경국' || dept_nm eq '안전교통국' || dept_nm eq '보건소' || dept_nm eq '구의회사무국'}">					          
	            <td width="100" class="tbl_list_rights">
	            	</c:when>
	            	<c:otherwise>
	            <td width="100" class="tbl_list_right">	
	            	</c:otherwise>
	            </c:choose>	            
	            	<c:set var="amt1" value="${row.IN_AMT1}" />
	            	<c:choose>				
					<c:when test="${amt1 eq '0'}">
                    -
		            </c:when>
		            <c:otherwise>		            	             
	                <fmt:formatNumber value="${row.IN_AMT1}" pattern="##,###,###,###" />
	                </c:otherwise>
	                </c:choose>	                
	            </td>
	            <c:choose>				
					<c:when test="${dept_nm eq '행정국' || dept_nm eq '기획재정국' || dept_nm eq '복지생활국' || dept_nm eq '뉴디자인국' || dept_nm eq '도시환경국' || dept_nm eq '안전교통국' || dept_nm eq '보건소' || dept_nm eq '구의회사무국'}">
	            <td width="100" class="tbl_list_rights">
	            	</c:when>
	            	<c:otherwise>
	            <td width="100" class="tbl_list_right">	
	            	</c:otherwise>
	            </c:choose>	        
	                <c:set var="amt2" value="${row.IN_AMT2}" />
	            	<c:choose>				
					<c:when test="${amt2 eq '0'}">
                    -
		            </c:when>
		            <c:otherwise>		            	             
	                <fmt:formatNumber value="${row.IN_AMT2}" pattern="##,###,###,###" />
	                </c:otherwise>
	                </c:choose>	                
	            </td>
 				<c:choose>				
					<c:when test="${dept_nm eq '행정국' || dept_nm eq '기획재정국' || dept_nm eq '복지생활국' || dept_nm eq '뉴디자인국' || dept_nm eq '도시환경국' || dept_nm eq '안전교통국' || dept_nm eq '보건소' || dept_nm eq '구의회사무국'}">
	            <td width="100" class="tbl_list_rights">
	            	</c:when>
	            	<c:otherwise>
	            <td width="100" class="tbl_list_right">	
	            	</c:otherwise>
	            </c:choose>	        	            	        
	                <c:set var="amt3" value="${row.IN_AMT3}" />
	            	<c:choose>				
					<c:when test="${amt3 eq '0'}">
                    -
		            </c:when>
		            <c:otherwise>		            	             
	                <fmt:formatNumber value="${row.IN_AMT3}" pattern="##,###,###,###" />
	                </c:otherwise>
	                </c:choose>	                
	            </td>
	            <c:choose>				
					<c:when test="${dept_nm eq '행정국' || dept_nm eq '기획재정국' || dept_nm eq '복지생활국' || dept_nm eq '뉴디자인국' || dept_nm eq '도시환경국' || dept_nm eq '안전교통국' || dept_nm eq '보건소' || dept_nm eq '구의회사무국'}">
	            <td width="100" class="tbl_list_rights">
	            	</c:when>
	            	<c:otherwise>
	            <td width="100" class="tbl_list_right">	
	            	</c:otherwise>
	            </c:choose>	        
	            
	                <c:set var="per1" value="${row.IN_PER1}" />
	            	<c:choose>				
					<c:when test="${per1 eq null}">
                    -
		            </c:when>
		            <c:otherwise>		            	             
	                <fmt:formatNumber value="${row.IN_PER1}" pattern="###.#" /> %
	                </c:otherwise>
	                </c:choose>	                
	            </td>
	    		<c:choose>				
					<c:when test="${dept_nm eq '행정국' || dept_nm eq '기획재정국' || dept_nm eq '복지생활국' || dept_nm eq '뉴디자인국' || dept_nm eq '도시환경국' || dept_nm eq '안전교통국' || dept_nm eq '보건소' || dept_nm eq '구의회사무국'}">
	            <td width="100" class="tbl_list_rights">
	            	</c:when>
	            	<c:otherwise>
	            <td width="100" class="tbl_list_right">	
	            	</c:otherwise>
	            </c:choose>		
	                <c:set var="amt4" value="${row.IN_AMT4}" />
	            	<c:choose>				
					<c:when test="${amt4 eq '0'}">
                    -
		            </c:when>
		            <c:otherwise>		            	             
	                <fmt:formatNumber value="${row.IN_AMT4}" pattern="##,###,###,###" />
	                </c:otherwise>
	                </c:choose>	                
	            </td>	           
 				<c:choose>				
					<c:when test="${dept_nm eq '행정국' || dept_nm eq '기획재정국' || dept_nm eq '복지생활국' || dept_nm eq '뉴디자인국' || dept_nm eq '도시환경국' || dept_nm eq '안전교통국' || dept_nm eq '보건소' || dept_nm eq '구의회사무국'}">
	            <td width="100" class="tbl_list_rights">
	            	</c:when>
	            	<c:otherwise>
	            <td width="100" class="tbl_list_right">	
	            	</c:otherwise>
	            </c:choose>	        
	            
	                <c:set var="amt5" value="${row.IN_AMT5}" />
	            	<c:choose>				
					<c:when test="${amt5 eq '0'}">
                    -
		            </c:when>
		            <c:otherwise>		            	             
	                <fmt:formatNumber value="${row.IN_AMT5}" pattern="##,###,###,###" />
	                </c:otherwise>
	                </c:choose>	                
	            </td>
	            <c:choose>				
					<c:when test="${dept_nm eq '행정국' || dept_nm eq '기획재정국' || dept_nm eq '복지생활국' || dept_nm eq '뉴디자인국' || dept_nm eq '도시환경국' || dept_nm eq '안전교통국' || dept_nm eq '보건소' || dept_nm eq '구의회사무국'}">
	            <td width="100" class="tbl_list_rights">
	            	</c:when>
	            	<c:otherwise>
	            <td width="100" class="tbl_list_right">	
	            	</c:otherwise>
	            </c:choose>
	            	        
	                <c:set var="per2" value="${row.IN_PER2}" />
	            	<c:choose>				
					<c:when test="${per2 eq null}">
                    -
		            </c:when>
		            <c:otherwise>		            	             
	                <fmt:formatNumber value="${row.IN_PER2}" pattern="###.#" /> %
	                </c:otherwise>
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
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/ffems/images/btn_type1_head.gif"></td>
                    <td background="/ffems/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
                    <td><img src="/ffems/images/btn_type1_end.gif"></td>
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
