<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>소송업무관리</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var tmp2 = "";
		$(document).ready(function(){
			fn_deptList();
			fn_judgmentList();
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_allIncidentList();
			});
			
			$("#write").on("click", function(e){ //수정하기 버튼							
				e.preventDefault();
				if( $('#USER_RIGHT').val() == 'A')
				{
					fn_incidentBasicWrite();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			
			$("#basic").on("click", function(e){ //기본정보
				e.preventDefault();
				fn_incidentBasicWrite();
			});
			
			$("#indsim").on("click", function(e){ //1,2,3심
				e.preventDefault();								
				fn_indTrialWrite();				
			});
			
			$("#1sim-2").on("click", function(e){ //신청사건
				e.preventDefault();				
				fn_firstTrial2Write();
			});
			
			$("#1sim-3").on("click", function(e){ //신청사건(즉시항고)
				e.preventDefault();				
				fn_firstTrial3Write();
			});
			
			$("#3sim-2").on("click", function(e){ //환송후 사건
				e.preventDefault();								
				fn_thirdTrial2Write();				
			});
			
			$("#3sim-3").on("click", function(e){ //소송비용 확정 결정
				e.preventDefault();								
				fn_thirdTrial3Write();				
			});
			
			
			$("#I_DEPT_CD").change(function() {
			   //alert($(this).children(":selected").val());
			   //alert($(this).children("option:selected").text());
			   //alert($('#I_DEPT_CD option').size());
				
			   
			});
			
			$("input:radio[name=INCDNT_GB]").click(function(){			
				   
				if($("input:checkbox[id='C_GN_GB']").is(":checked") == true)
				{
					if($(":input:radio[name=INCDNT_GB]:checked").val() == "1")		       
		        		$("#CMPLN_NAME").val("강남구청장");
		        	else if($("input:radio[name=INCDNT_GB]:checked").val() == "2")
		        		$("#CMPLN_NAME").val("강남구");
		        	else
		        		$("#CMPLN_NAME").val("강남구청장");					
				}				
			});
			
			$("#C_GN_GB").on("click", function(e){ //소송비용 확정 결정
				var chk = $(this).is(":checked");
		        if(chk){ 
		        	$("input:checkbox[name='C_GN_GB']").prop('checked', true);
		        			 
		        	if($(":input:radio[name=INCDNT_GB]:checked").val() == "1")		       
		        		$("#CMPLN_NAME").val("강남구청장");
		        	else if($("input:radio[name=INCDNT_GB]:checked").val() == "2")
		        		$("#CMPLN_NAME").val("강남구");
		        	else
		        		$("#CMPLN_NAME").val("강남구청장");
		        }
		        else{
		        	$("input:checkbox[name='C_GN_GB']").prop('checked', false);
		        	$("#CMPLN_NAME").val("");
		        }
	
			});
			
			
		
		});
		
		function fn_allIncidentList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/allIncidentList.do' />");
			comSubmit.submit();
		}
		
		
		function fn_incidentBasicWrite(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/incidentBasicWrite.do' />");
			comSubmit.submit();
		}
		
		function fn_indTrialWrite(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/indTrialWrite.do' />");
			comSubmit.submit();
		}
		
		function fn_firstTrial2Write(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/firstTrial2Write.do' />");
			comSubmit.submit();
		}
		
		function fn_firstTrial3Write(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/firstTrial3Write.do' />");
			comSubmit.submit();
		}
		
		function fn_thirdTrial2Write(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/thirdTrial2Write.do' />");
			comSubmit.submit();
		}
		
		function fn_thirdTrial3Write(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/thirdTrial3Write.do' />");
			comSubmit.submit();
		}
		
		
		
		
		function fn_deptList(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/common/deptList.do' />");
			comAjax.setCallback("fn_deptListCallback");			
			comAjax.ajax();
		}
		
		function fn_deptListCallback(data){
			var str = "";
			var i = 1;
					
			$.each(data.list, function(key, value){										
					str += "<option value=\"" + value.DEPT_ID + "\">" + value.DEPT_NAME + ",</option>";
					i++;
			});
			
			$("#I_DEPT_CD").append(str);			
		}	
		
		
		
		function fn_incidentBasicWrite(){
			var comSubmit = new ComSubmit("form1");
			var tmp = "";
			var c_gn_gb = "";
			
			if($(":input:radio[name=INCDNT_GB]:checked").val() == "" || $(":input:radio[name=INCDNT_GB]:checked").val() == null)
			{
				alert('구분을 선택하세요!!');
				$('#INCDNT_GB').focus();
				return false();
			}
			
			if($('#INCDNT_NAME').val() == "")
			{
				alert('사건명을 입력하세요!!');
				$('#INCDNT_NAME').focus();
				return false();
			}
			
			
			if($('#CMPLN_NAME').val() == "")
			{
				alert('원고를 입력하세요!!');
				$('#CMPLN_NAME').focus();
				return false();
			}
			
			if($('#DFNDNT_NAME').val() == "")
			{
				alert('피고를 입력하세요!!');
				$('#DFNDNT_NAME').focus();
				return false();
			}
			
			tmp = $("#I_DEPT_CD > option:selected").text();
			tmp = tmp.substring(0, tmp.length-1);
			//alert(tmp);
			//for( var i=0; i<5; i++){
				//alert($("#I_DEPT_CD option:eq(2)").attr("selected"));
				//if( $("#I_DEPT_CD option:eq("+i+")").attr("selected") == 1) //선택된 값찾기
						//alert($("#I_DEPT_CD option:eq("+i+")").text()); //선택된 값 출력
						//alert('11');
			//}
			if(tmp == "" || tmp == null)
			{
				alert('소관부서를 선택하세요!!');
				$('#I_DEPT_CD').focus();
				return false();
			}
			
			
			if($("input:checkbox[id='C_GN_GB']").is(":checked")) 
				c_gn_gb = "1";
			
			
			if(confirm('데이터를 등록하시겠습니까?'))
			{							
				comSubmit.setUrl("<c:url value='/incident/insertIncidentBasic.do' />");				
				comSubmit.addParam("GN_GB", c_gn_gb);
				comSubmit.addParam("DEPT_CD", $('#I_DEPT_CD').val().toString());
				comSubmit.addParam("DEPT_NAME", tmp);
								
				if($("#LAST_RSLT_GB > option:selected").text() == "----------")
					comSubmit.addParam("LAST_RSLT_CONT", "");
				else
					comSubmit.addParam("LAST_RSLT_CONT", $("#LAST_RSLT_GB > option:selected").text());
							
				comSubmit.submit();
			}
		}
		
		function fn_judgmentList(){
			
			var comAjax = new ComAjax();
			
			comAjax.setUrl("<c:url value='/common/selectJudgmentList.do' />");
			comAjax.setCallback("fn_selectJudgmentListCallback");			
			comAjax.addParam("GR_CD", "15");
			comAjax.ajax();
		}
		
		function fn_selectJudgmentListCallback(data){			
			var str = "";
			var i = 1;
			
			str = "<option value=\"\">----------</option>";
			
			$.each(data.list, function(key, value){										
				str += "<option value=\"" + value.CODE + "\">" + value.NAME + "</option>";
				i++;
			});
			
			$("#LAST_RSLT_GB").append(str);			
		}
	</script>
</head>

<body>
<form id="form1" name="form1">
<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="INS_DEPT" name="INS_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${map.INS_ID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/sosong/images/popup_title.gif" align="absmiddle">소송사건</td>
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
        
        <div class="LblockTab" style="position: relative;">
		<ul class="Lclear">
			<li class="Lcurrent" id='tab1'><span><a href="#this" class="btn" id="basic">기본정보</a></span></li>			
			<!-- <li id='tab2'><span><a href="#this" class="btn" id="indsim">1,2,3심</a></span></li>
			<li id='tab3'><span><a href="#this" class="btn" id="1sim-2">신청사건</a></span></li>	 		
	 		<li id='tab4'><span><a href="#this" class="btn" id="1sim-3">신청사건(즉시항고)</a></span></li>	 		
	 		<li id='tab5'><span><a href="#this" class="btn" id="3sim-2">환송후사건</a></span></li>	 		
	 		<li id='tab6'><span><a href="#this" class="btn" id="3sim-3">소송비용확정결정</a></span></li> -->	 		
		</ul>
		</div>
                
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="margin_btn" colspan="2"></td>
          </tr>
        </table>
        
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	          <tr>
	            <td width="120" class="tbl_field">구분</td>
	            <td width="243" class="tbl_list_left"><input type="radio" id="INCDNT_GB1" name="INCDNT_GB" value="1" class="input" onfocus="this.blur()"> 행정소송 &nbsp;<input type="radio" id="INCDNT_GB2" name="INCDNT_GB" value="2" class="input" onfocus="this.blur()"> 민사소송 &nbsp;<input type="radio" id="INCDNT_GB3" name="INCDNT_GB" value="3" class="input" onfocus="this.blur()"> 국가소송 </td>
				<td width="120" class="tbl_field">사건명</td>
	            <td width="244" class="tbl_list_left"><input type="text" id="INCDNT_NAME" name="INCDNT_NAME" class="input" style="width:200;"></td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">원고</td>
	            <td width="243" class="tbl_list_left">구소제기  <input type="checkbox" id="C_GN_GB" name="C_GN_GB" class="input"> <input type="text" id="CMPLN_NAME" name="CMPLN_NAME" class="input" style="width:200;"></td>
				<td width="120" class="tbl_field">특이사항</td>
	            <td width="244" class="tbl_list_left"><input type="text" id="NOTE" name="NOTE" class="input" style="width:200;"></td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">피고</td>
	            <td width="243" class="tbl_list_left"><input type="text" id="DFNDNT_NAME" name="DFNDNT_NAME" class="input" style="width:200;"></td>
				<td width="120" class="tbl_field">소관부서</td>
	            <td width="244" class="tbl_list_left">
	                <select id="I_DEPT_CD" name="I_DEPT_CD" class="input" size="10" multiple>	                		                    		               
	                </select>
				</td>							
	          </tr>			 	
			  <tr>
	            <td width="120" class="tbl_field">소가</td>
	            <td width="243" class="tbl_list_left"><input type="text" id="LAWSUIT_VALUE" name="LAWSUIT_VALUE" class="input" style="width:200;"></td>
				<td width="120" class="tbl_field">종결심</td>
	            <td width="244" class="tbl_list_left">
	            	<select id="LAST_SIM_GB" name="LAST_SIM_GB" class="input">
	                	<option value="">----------</option>
	                	<option value="1">1심</option>
	                	<option value="2">2심</option>
	                	<option value="3">3심</option> 					                    		                
	                </select>
	            </td>	            						
	          </tr>	          
	          <tr>
	            <td width="120" class="tbl_field">승패여부</td>
	            <td width="243" class="tbl_list_left">
	            	<select id="WIN_LOSE_GB" name="WIN_LOSE_GB" class="input">
	                	<option value="">----------</option>
	                	<option value="1">승소</option>
	                	<option value="2">패소</option> 					                    		                
	                </select>
	            </td>
				<td width="120" class="tbl_field">최종결과</td>
	            <td width="244" class="tbl_list_left">	            	
	                <select id="LAST_RSLT_GB" name="LAST_RSLT_GB" class="input">	                	        	 					                    		               
	                </select>
				</td>	            						
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">비고</td>
	            <td width="243" class="tbl_list_left"><input type="text" id="BIGO" name="BIGO" class="input" style="width:200;"></td>
				<td width="120" class="tbl_field"></td>
	            <td width="244" class="tbl_list_left"></td>							
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
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/sosong/images/btn_type0_head.gif"></td>
                    <td background="/sosong/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write"><font color="white">등록</font></a></td>
                    <td><img src="/sosong/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>                               	
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/sosong/images/btn_type1_head.gif"></td>
                    <td background="/sosong/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
                    <td><img src="/sosong/images/btn_type1_end.gif"></td>
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
<%@ include file="/WEB-INF/include/include-body.jspf" %>
</body>
</html>
