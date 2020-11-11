<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>주요사업관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
 #pop{
  width:300px; height:250px; background:#3d3d3d; color:#fff; 
  position:absolute; top:10px; left:100px; text-align:center; 
  border:1px solid #000;
   }
</style>
<script type="text/javascript">
		var gfv_count = 1;
	
		$(document).ready(function(){
			
			$("#list").on("click", function(e){ //목록으로 버튼
				//alert('aa');
				e.preventDefault();				
				fn_mainSaupList();
			});
			
			$("#update").on("click", function(e){ //수정하기 버튼
				if( $('#USER_RIGHT').val() == 'A' || $('#WRITE_DEPT').val() == $('#LOGIN_DEPT').val())
				{				
					e.preventDefault();
					fn_updateMainSaupData();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			
			$("#del").on("click", function(e){ //수정하기 버튼
				if( $('#USER_RIGHT').val() == 'A' || $('#WRITE_DEPT').val() == $('#LOGIN_DEPT').val())
				{				
					e.preventDefault();
					fn_deleteMainSaupData();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			
			$("#msaup").on("click", function(e){ //목록으로 버튼
				//alert('aa');
				e.preventDefault();
				$('#pop2').show();		        
				fn_mSaupList();
			});
			
			
			$('#close1').click(function() {
		        $('#pop').hide();
		    });
			
			$('#close2').click(function() {
		        $('#pop').hide();
		    });
			
			$('#close3').click(function() {
		        $('#pop2').hide();
		    });
			
			$('#close4').click(function() {
		        $('#pop2').hide();
		    });
			
			$('#open').click(function() {
		        
				if($("#USER_NAME").val().length < 1){					
					alert('찾으시려는 이름을 입력하세요');
					$("#USER_NAME").focus();
					return;
				}
				
				$('#pop').show();
		        fn_jikwonlist();
		    });
			
			
			$("#USER_NAME").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$('#pop').show();
					fn_jikwonlist();
				}

			});
			
			$("#SAUP_BUDGET_AMT").bind('keyup keydown',function(){
		        inputNumberFormat(this);
		    });
			
			
			$("#SAUP_OPEN_DATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			
			$('#clear').click(function() {
				
				$('#SAUP_DAMDANG_ID').val("");
				$('#SAUP_DAMDANG_NM').val("");
				$('#SAUP_DAMDANG_TEL').val("");
				$('#SAUP_DEPT_CD').val("");
				$('#SAUP_DEPT_NM').val("");							
		    });
			
		});
		
		
		//입력한 문자열 전달
	    function inputNumberFormat(obj) {
	        obj.value = comma(uncomma(obj.value));
	    }
	       
	    //콤마찍기
	    function comma(str) {
	        str = String(str);
	        return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
	    }
	 
	    //콤마풀기
	    function uncomma(str) {
	        str = String(str);
	        return str.replace(/[^\d]+/g, '');
	    }
		
		function fn_jikwonlist(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/common/popJikWonList.do' />");
			comAjax.setCallback("fn_popJikWonListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());
			comAjax.addParam("USER_NAME",$("#USER_NAME").val());
			comAjax.addParam("GUBUN",$("#GUBUN").val());
			comAjax.ajax();
		}
		
		function fn_popJikWonListCallback(data){
			
			//data = $.parseJSON(data);
			var str = "";
			var i = 1;			
			var body = $("#mytable");
			var total = data.TOTAL;
			var recordcnt = $("#RECORD_COUNT").val();
			
			body.empty();
			
			var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_jikwonlist"
			};
			gfn_renderPaging(params);
			
			$.each(data.list, function(key, value){										
					
					str += "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" +
					   "<td class=\"list_center\" nowrap>" + value.RNUM + "</td>" +
	                   "<td class=\"list_center\" nowrap>" +      	                 
	                   "<a href='#this' name='title' >" + NvlStr(value.NDU_USER_NAME)  + "</a>" + 					
	                   "<input type='hidden' name='JDID' id='JDID' value=" + NvlStr(value.NDU_DEPT_ID) + ">" +
					   "<input type='hidden' name='JDEPT' id='JDEPT' value=" + NvlStr(value.NDU_DEPT_NAME) + ">" +
					   "<input type='hidden' name='JID' id='JID' value=" + NvlStr(value.NDU_USER_ID) + ">" +
					   "<input type='hidden' name='JNAME' id='JNAME' value=" + NvlStr(value.NDU_USER_NAME) + ">" +
					   "<input type='hidden' name='JTEL' id='JTEL' value=" + NvlStr(value.NAESUN) + ">" +
					   "</td>" +	                   
	                   "<td class=\"list_center\" nowrap>" + NvlStr(value.NDU_DEPT_NAME) + "</td>" + 
	                   "<td class=\"list_center\" nowrap>" + NvlStr(value.NDU_CLSS_NM) + "</td>" +	            	                   
	                   "<td class=\"list_center\" nowrap>" + 
	                   "<a href='#this' name='title2' >" + NvlStr(value.NDU_POSIT_NM) + "</a>" + 
	                   "<input type='hidden' name='JNAME' id='JNAME' value=" + NvlStr(value.NDU_USER_NAME) + ">" +
					   "<input type='hidden' name='JTEL' id='JTEL' value=" + NvlStr(value.NAESUN) + ">" +
					   "<input type='hidden' name='JPOS' id='JPOS' value=" + NvlStr(value.NDU_POSIT_NM) + ">" +
	                   "</td>" +
	                   "<td class=\"list_center\">" + NvlStr(value.NAESUN) + "</td>" +
					   "</tr>" +
					   "<tr>" + 
				       "<td colspan=\"6\" class=\"list_line\"></td>" + 
					   "</tr>";
					i++;
			});
			
			body.append(str);
			//alert(str);
			
			$("a[name='title']").on("click", function(e){ //제목 
				
				e.preventDefault();			
				fn_set_user_info($(this));
			});
			
			$("a[name='title2']").on("click", function(e){ //직급 
				
				e.preventDefault();			
				fn_set_user_info2($(this));
			});
		}
		
		
		function fn_set_user_info(obj){
			var tmp1 = "";
		
			tmp1 = obj.parent().find("#JID").val();			
			$("#SAUP_DAMDANG_ID").val(tmp1);
			
			tmp1 = "";
			tmp1 = obj.parent().find("#JNAME").val();
			$("#SAUP_DAMDANG_NM").val(tmp1);
			
			tmp1 = "";
			tmp1 = obj.parent().find("#JTEL").val();			
			$("#SAUP_DAMDANG_TEL").val(tmp1);
						
			tmp1 = "";
			tmp1 = obj.parent().find("#JDID").val();			
			$("#SAUP_DEPT_CD").val(tmp1);
			
			tmp1 = "";
			tmp1 = obj.parent().find("#JDEPT").val();			
			$("#SAUP_DEPT_NM").val(tmp1);
						
			$('#pop').hide();
		}
		
		
		function fn_set_user_info2(obj){
			var tmp1 = "";
		
			tmp1 = obj.parent().find("#JPOS").val();			
			
			if(tmp1 == '팀장')
			{
				tmp1 = "";
				tmp1 = obj.parent().find("#JNAME").val();			
				$("#SAUP_DEPT_TEAMJANG").val(tmp1);
				
				tmp1 = "";
				tmp1 = obj.parent().find("#JTEL").val();
				$("#SAUP_TEAMJANG_TEL").val(tmp1);
			}
			else{
				tmp1 = "";
				tmp1 = obj.parent().find("#JNAME").val();			
				$("#SAUP_DEPT_GWAJANG").val(tmp1);
				
				tmp1 = "";
				tmp1 = obj.parent().find("#JTEL").val();
				$("#SAUP_GWAJANG_TEL").val(tmp1);
				
			}
			
			$('#pop').hide();
		}
		
		
		
		
		function fn_mSaupList(pageNo){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/daejang/mSaupList.do' />");
			comAjax.setCallback("fn_mSaupListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX2").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT2").val());
			comAjax.ajax();
		}
		
		function fn_mSaupListCallback(data){
			//data = $.parseJSON(data);
			var str = "";
			var i = 1;			
			var body = $("#mytable2");
			var total = data.TOTAL;
			var recordcnt = $("#RECORD_COUNT2").val();
			
			body.empty();
			
			var params = {
					divId : "PAGE_NAVI2",
					pageIndex : "PAGE_INDEX2",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_mSaupList"
			};
			gfn_renderPaging(params);
					
			$.each(data.list, function(key, value){										
					
					str += "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" +
					   "<td class=\"list_center\" nowrap>" + value.ID + "</td>" +
					   "<td class=\"list\"'>" + NvlStr(value.DEPT_NM) + "</td>" +
	                   "<td class=\"list_center\" nowrap>" +      	                 
	                   "<a href=\"#this\" name=\"title\" >" + NvlStr(value.SAUP_NM)  + "</a>" + 					
	                   "<input type=\"hidden\" name=\"JID\" id=\"JID\" value=" + NvlStr(value.ID) + ">" +	
	                   "<input type=\"hidden\" name=\"JSNM\" id=\"JSNM\" value=\"" + NvlStr(value.SAUP_NM) + "\">" +
	                   "<input type=\"hidden\" name=\"JDEPT\" id=\"JDEPT\" value=\"" + NvlStr(value.DEPT_NM) + "\">" +
					   "</td>" +	                   
					   "</tr>" +
					   "<tr>" + 
				       "<td colspan=\"3\" class=\"list_line\"></td>" + 
					   "</tr>";
					i++;
			});
			
			body.append(str);
			//alert(str);
			
			$("a[name='title']").on("click", function(e){ //제목 
				
				e.preventDefault();			
				fn_saup_info($(this));
			});
		}
		
		
		
		function fn_saup_info(obj, i){
			var tmp1 = "";
		
			tmp1 = obj.parent().find("#JID").val();			
			$("#SID").val(tmp1);
			
			tmp1 = "";
			tmp1 = obj.parent().find("#JSNM").val();
			$("#SAUP_NM").val(tmp1);
			
			tmp1 = "";
			tmp1 = obj.parent().find("#JDEPT").val();
			$("#USER_NAME").val(tmp1);
		
			$('#pop2').hide();
		}
		
		
		
		
		
		
		
		function fn_mainSaupList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/mainSaupList.do?year=${param.year}&sgb=${param.sgb}' />");
			comSubmit.submit();
		}
		
		function fn_updateMainSaupData(){
			var comSubmit = new ComSubmit("form1");
			
			
			if($('#SAUP_BUDGET_AMT').val() == "")
			{
				alert('예산금액을 입력하세요.');
				$('#SAUP_BUDGET_AMT').focus();
				return false();
			}
			
			if($('#SAUP_OPEN_DATE').val().replace(/-/gi, "").length < 8)
			{
				alert('목표시기를 8자리로 입력하세요.');
				$('#SAUP_OPEN_DATE').focus();
				return false();
			}
			
			if($('#SAUP_DEPT_NM').val() == "")
			{
				alert('담당부서를 입력하세요.');
				$('#SAUP_DEPT_NM').focus();
				return false();
			}
			
			if($('#SAUP_DAMDANG_NM').val() == "")
			{
				alert('담당자를 입력하세요.');
				$('#SAUP_DAMDANG_NM').focus();
				return false();
			}
			
			if($('#SAUP_DEPT_TEAMJANG').val() == "")
			{
				alert('팀장을 입력하세요.');
				$('#SAUP_DEPT_TEAMJANG').focus();
				return false();
			}
			
			if($('#SAUP_DEPT_GWAJANG').val() == "")
			{
				alert('과장을 입력하세요.');
				$('#SAUP_DEPT_GWAJANG').focus();
				return false();
			}
						
			
			if($('#YEAR').val() == 2019)
			{
				alert('2019년도는 더이상 입력/수정 되지 않습니다.\n 2020 년도로 입력/수정 하시길 바랍니다.');
				return false();
			}
			
			
			if(confirm('데이터를 수정하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/daejang/updateMainSaup.do' />");
				comSubmit.submit();
			}
		}
		
		
		
		function fn_deleteMainSaupData(){
			var comSubmit = new ComSubmit("form1");		
			
			if($('#YEAR').val() == 2019)
			{
				alert('2019년도는 더이상 입력/수정 되지 않습니다.\n 2020 년도로 입력/수정 하시길 바랍니다.');
				return false();
			}
			
			if(confirm('데이터를 삭제하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/daejang/deleteMainSaup.do' />");
				comSubmit.submit();
			}
		}

		
		
	</script>
</head>

<body>
<form id="form1" name="form1" enctype="multipart/form-data">
<input type="hidden" id="MOD_ID" name="MOD_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="MOD_DEPT" name="MOD_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="WRITE_DEPT" name="WRITE_DEPT" value="${map.INS_DEPT}">
<input type="hidden" id="LOGIN_DEPT" name="LOGIN_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="SAUP_FIELD1_GB" name="SAUP_FIELD1_GB" value="">
<input type="hidden" id="SAUP_START_DATE" name="SAUP_START_DATE" value="">
<input type="hidden" id="SAUP_END_DATE" name="SAUP_END_DATE" value="">
<input type="hidden" id="SAUP_NM" name="SAUP_NM" value="${param.SAUP_NM}">
<input type="hidden" id="SAUP_DEPT_CD" name="SAUP_DEPT_CD" value="${map.SAUP_DEPT_CD}">
<input type="hidden" id="SAUP_DAMDANG_ID" name="SAUP_DAMDANG_ID" value="${map.SAUP_DAMDANG_ID}">
<input type="hidden" id="SID" name="SID" value="${map.ID}">
<input type="hidden" id="YEAR" name="YEAR" value="${param.year}">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/bims/images/popup_title.gif" align="absmiddle">주요사업 데이터 등록</td>
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
				<td class="title"><img src="/bims/images/title_ico.gif" align="absmiddle">기본정보수정</td>
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
	            <td width="120" class="tbl_field">사업명</td>
	            <td width="612" colspan="3" class="tbl_list_left">
	            ${map.SAUP_NM}	                
	            </td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">사업설명(요약)</td>
	            <td width="612" colspan="3" class="tbl_list_left">
	                <textarea id="SAUP_DETAIL" name="SAUP_DETAIL" class="input" rows="10" cols="150" style="width:800px; height:50px;">${map.SAUP_DETAIL}</textarea>	                
	            </td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">예산금액</td>
	            <td width="612" colspan="3" class="tbl_list_left">
	                <input type="text" id="SAUP_BUDGET_AMT" name="SAUP_BUDGET_AMT" value="<fmt:formatNumber value="${map.SAUP_BUDGET_AMT}" pattern="###,###,###" />" class="input" style="width:400;">	                
	            </td>
	          </tr>	          
	          <tr>
	            <td width="120" class="tbl_field">목표시기</td>
	            <td width="612" colspan="3" class="tbl_list_left">	                
	                <input type="text" id="SAUP_OPEN_DATE" name="SAUP_OPEN_DATE" value="<fmt:parseDate value="${map.SAUP_OPEN_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy-MM-dd"/>" class="input" style="width:174;">	                
	            </td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">담당부서</td>
	            <td width="612" colspan="3" class="tbl_list_left"><input type="text" id="SAUP_DEPT_NM" name="SAUP_DEPT_NM" value="${map.SAUP_DEPT_NM}" class="input" style="width:174;" readonly></td>									
	          </tr>	   	          
	          <tr>
	            <td width="120" class="tbl_field">담당자</td>
	            <td width="246" class="tbl_list_left"><input type="text" id="SAUP_DAMDANG_NM" name="SAUP_DAMDANG_NM" value="${map.SAUP_DAMDANG_NM}" class="input" style="width:174;" readonly>	            
	            <select id="GUBUN" name="GUBUN" class="input">
	            <option value="2">부서명</option>
	            <option value="1">직원명</option>
	            </select> <input type="text" id="USER_NAME" name="USER_NAME" value="${map.SAUP_DEPT_NM}" class="input" style="width:80;"> <a href="#this" id="open" class="bk">찾기</a> <a href="#this" id="clear" class="bk">지우기</a>
	            </td>
	            <td width="120" class="tbl_field">전화번호</td>
	            <td width="246" class="tbl_list_left">
	                <input type="text" id="SAUP_DAMDANG_TEL" name="SAUP_DAMDANG_TEL" value="${map.SAUP_DAMDANG_TEL}" class="input" style="width:100;">	                
	            </td>									
	          </tr>	                	      	       
	          <tr>
	            <td width="120" class="tbl_field">담당팀장</td>
	            <td width="246" class="tbl_list_left">
	                <input type="text" id="SAUP_DEPT_TEAMJANG" name="SAUP_DEPT_TEAMJANG" value="${map.SAUP_DEPT_TEAMJANG}" class="input" style="width:174;">	                
	            </td>
	            <td width="120" class="tbl_field">전화번호</td>
	            <td width="246" class="tbl_list_left">
	                <input type="text" id="SAUP_TEAMJANG_TEL" name="SAUP_TEAMJANG_TEL" value="${map.SAUP_TEAMJANG_TEL}" class="input" style="width:100;">	                
	            </td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">담당과장</td>
	            <td width="246" class="tbl_list_left">
	                <input type="text" id="SAUP_DEPT_GWAJANG" name="SAUP_DEPT_GWAJANG" value="${map.SAUP_DEPT_GWAJANG}" class="input" style="width:174;">	                
	            </td>
	            <td width="120" class="tbl_field">전화번호</td>
	            <td width="246" class="tbl_list_left">
	                <input type="text" id="SAUP_GWAJANG_TEL" name="SAUP_GWAJANG_TEL" value="${map.SAUP_GWAJANG_TEL}" class="input" style="width:100;">	                
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
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
                  <tr>
                    <td><img src="/bims/images/btn_type0_head.gif"></td>
                    <td background="/bims/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="update"><font color="white">수정</font></a></td>
                    <td><img src="/bims/images/btn_type0_end.gif"></td>
                  </tr>
                </table>                
                </td>
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
                  <tr>
                    <td><img src="/bims/images/btn_type0_head.gif"></td>
                    <td background="/bims/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="del"><font color="white">삭제</font></a></td>
                    <td><img src="/bims/images/btn_type0_end.gif"></td>
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
<%@ include file="/WEB-INF/include/include-body.jspf" %>


<!-- POPUP --> 
<div id="pop" style="position:absolute;left:395px;top:90;z-index:200;display:none;"> 
<table width=560 height=250 cellpadding=2 cellspacing=0>
<tr>        
    <td align=right bgcolor=white>      
        <a href="#" id="close1"><B>[닫기]</B></a> 
    </td> 
</tr>
<tr> 
    <td style="border:1px #666666 solid" height=230 align=center bgcolor=white> 
     <table width="100%" border="0" cellspacing="0" cellpadding="0" style="TABLE-layout: fixed">
			<tr>
				<td class="list_tit_line_s" width="40"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="100%"></td>
				<td class="list_tit_line_s" width="60"></td>
				<td class="list_tit_line_s" width="100"></td>			
			</tr>
			<tr class="list_tit_tr">
				<td nowrap class="list_tit"></td>
				<td nowrap class="list_tit_bar">이름</td>
				<td nowrap class="list_tit_bar">부서</td>
				<td nowrap class="list_tit_bar">직급</td>
				<td nowrap class="list_tit_bar">직위</td>
				<td nowrap class="list_tit_bar">내선</td>							
			</tr>
			<tr>
				<td colspan="6" class="list_tit_line_e"></td>
			</tr>
			<tbody id='mytable'>			
			</tbody>
			<tr>
				<td colspan="6" height="20"></td>
			</tr>
			<tr>
				<td colspan="6">
				<div id="PAGE_NAVI" align='center'></div>
				<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>
				<input type="hidden" id="RECORD_COUNT" name="RECORD_COUNT" value="20"/>
				<%@ include file="/WEB-INF/include/include-body.jspf" %>
				</td>
			</tr>
			</table>
    </td> 
</tr> 
<tr>        
    <td align=right bgcolor=white>      
        <a href="#" id="close2"><B>[닫기]</B></a> 
    </td> 
</tr> 
      
</table> 
</div>  



<!-- POPUP2 --> 
<div id="pop2" style="position:absolute;left:395px;top:50;z-index:200;display:none;"> 
<table width=500 height=250 cellpadding=2 cellspacing=0>
<tr>        
    <td align=right bgcolor=white>      
        <a href="#" id="close3"><B>[닫기]</B></a> 
    </td> 
</tr>
<tr> 
    <td style="border:1px #666666 solid" height=230 align=center bgcolor=white> 
     <table width="100%" border="0" cellspacing="0" cellpadding="0" style="TABLE-layout: fixed">
			<tr>
				<td class="list_tit_line_s" width="40"></td>
				<td class="list_tit_line_s" width="80"></td>				
				<td class="list_tit_line_s" width="100%"></td>							
			</tr>
			<tr class="list_tit_tr">
				<td nowrap class="list_tit"></td>
				<td nowrap class="list_tit_bar">추진부서</td>
				<td nowrap class="list_tit_bar">사업명</td>											
			</tr>
			<tr>
				<td colspan="3" class="list_tit_line_e"></td>
			</tr>
			<tbody id='mytable2'>			
			</tbody>			
			<tr>
				<td colspan="3" height="20"></td>
			</tr>
			<tr>
				<td colspan="3">
				<div id="PAGE_NAVI2" align='center'></div>
				<input type="hidden" id="PAGE_INDEX2" name="PAGE_INDEX2"/>
				<input type="hidden" id="RECORD_COUNT2" name="RECORD_COUNT2" value="20"/>
				<%@ include file="/WEB-INF/include/include-body.jspf" %>
				</td>
			</tr>			
			</table>
    </td> 
</tr> 
<tr>        
    <td align=right bgcolor=white>      
        <a href="#" id="close4"><B>[닫기]</B></a> 
    </td> 
</tr> 
      
</table> 
</div>  
</body>
</html>
