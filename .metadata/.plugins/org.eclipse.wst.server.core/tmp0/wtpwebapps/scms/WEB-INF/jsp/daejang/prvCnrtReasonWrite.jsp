<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>수의계약 관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var tmp2 = "";
		var i = 1;
		var in_chk1 = 0; //이름
		var in_chk2 = 0; //사업자번호
		var sel_val = 0;  //발주계획 입력안해도 넘어갈수 있게 수정  2019.1.10
		var gfv_count = 0;
		
		$(document).ready(function(){
			
			fn_yearList();
			
			
			$("a[name='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
			
			
			$("#addFile").on("click", function(e){ //파일 추가 버튼
				e.preventDefault();
				fn_addFile();
			});
			
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_prvCnrtReasonList();
			});
			
			$("#write").on("click", function(e){ //등록하기 버튼							
				e.preventDefault();
				fn_insertPrvCnrtContract();				
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
			
			$('#sel').click(function() {
		        sel_val = 1;
		        alert('설정완료!!');
		    });
						
			$('#open').click(function() {
		        
				$('#pop').show();
				fn_sauplist();
		    });
			
			
			$('#open2').click(function() {
		        
				if($("#USER_NAME").val().length < 1){					
					alert('찾으시려는 이름을 입력하세요');
					$("#USER_NAME").focus();
					return;
				}
				
				$('#pop2').show();
				fn_jikwonlist();
		    });
			
			
			$('#clear').click(function() {
				
				$('#CNRT_DEPT_CD').val("");
				$('#CNRT_DEPT_NM').val("");
				$('#CNRT_PERSON_ID').val("");
				$('#CNRT_PERSON_NM').val("");
		    });
			
			$("#CNRT_AMT").bind('keyup keydown',function(){
		        inputNumberFormat(this);
		    });
						
			$(document).keydown(function(e){		
				if(e.target.nodeName != "INPUT" && e.target.nodeName != "TEXTAREA"){
			        if(e.keyCode == 8){		        
			        	return false;
			        }
			    }
				
				if(e.target.readOnly){ // readonly일 경우 true
		            if(e.keyCode == 8){
		                return false;
		           }
				}
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
		
		
		
		function fn_prvCnrtReasonList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtReasonList.do' />");
			comSubmit.addParam("ID","${param.ID}");
			comSubmit.submit();
		}
				
		function fn_sauplist(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/daejang/selectPrvCnrtPlanList.do' />");			
			comAjax.setCallback("fn_popSaupListCallback");
			comAjax.addParam("SAUP_DEPT_CD",$('#INS_DEPT').val());
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());
			comAjax.ajax();
		}
		
		function fn_popSaupListCallback(data){
			data = $.parseJSON(data);
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
					eventName : "fn_sauplist"
			};
			gfn_renderPaging(params);
				
			$.each(data.list, function(key, value){										
					
					str += "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" +
					   "<td class=\"list_center\" nowrap>" + value.RNUM + "</td>" +
					   "<td class=\"list_center\" nowrap>" + dv_yrmonth(NvlStr(value.SAUP_YEAR), ".") + "</td>" +	
	                   "<td class=\"list_center\" nowrap>" +      	                 
	                   "<a href='#this' name='title' >" + NvlStr(value.SAUP_NM)  + "</a>" + 					
	                   "<input type='hidden' name='SID' id='SID' value=" + NvlStr(value.ID) + ">" +
					   "<input type='hidden' name='SNM' id='SNM' value='" + NvlStr(value.SAUP_NM) + "'>" +					
					   "</td>" + 
	                   "<td class=\"list_center\" nowrap>" + out_gubun1(NvlStr(value.SAUP_FIELD1_GB)) + "</td>" + 
	                   "<td class=\"list_center\" nowrap>" + comma(NvlStr(value.SAUP_BUDGET_AMT)) + "</td>" +
	                   "<td class=\"list\"></td>" +	                 
					   "</tr>" +
					   "<tr>" + 
				       "<td colspan=\"5\" class=\"list_line\"></td>" + 
					   "</tr>";
					i++;
			});
			
			body.append(str);
			
			$("a[name='title']").on("click", function(e){ //제목 
				
				e.preventDefault();			
				fn_set_user_info($(this));
			});
		}
		
		
		
		function fn_set_user_info(obj, i){
			var tmp1 = "";
		
			tmp1 = obj.parent().find("#SID").val();
			
			$("#SAUP_ID").val(tmp1);
			
			tmp1 = obj.parent().find("#SNM").val();
			
			$("#SAUP_NM").val(tmp1);			
			$('#pop').hide();
		}
			
		
		function fn_jikwonlist(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/common/popJikWonList.do' />");
			comAjax.setCallback("fn_popJikWonListCallback");
			comAjax.addParam("USER_NAME",$("#USER_NAME").val());
			comAjax.ajax();
		}
		
		function fn_popJikWonListCallback(data){
			data = $.parseJSON(data);
			var str = "";
			var i = 1;
			var body = $("#mytable2");
			
			body.empty();
					
			$.each(data.list, function(key, value){										
					
					str += "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" +
					   "<td class=\"list_center\" nowrap>" + value.RNUM + "</td>" +
	                   "<td class=\"list_center\" nowrap>" +      	                 
	                   "<a href='#this' name='title' >" + NvlStr(value.NDU_USER_NAME)  + "</a>" + 					
	                   "<input type='hidden' name='JDID' id='JDID' value=" + NvlStr(value.NDU_DEPT_ID) + ">" +
					   "<input type='hidden' name='JDEPT' id='JDEPT' value=" + NvlStr(value.NDU_DEPT_NAME) + ">" +
					   "<input type='hidden' name='JID' id='JID' value=" + NvlStr(value.NDU_USER_ID) + ">" +
					   "<input type='hidden' name='JNAME' id='JNAME' value=" + NvlStr(value.NDU_USER_NAME) + ">" +					   
					   "</td>" +	                   
	                   "<td class=\"list_center\" nowrap>" + NvlStr(value.NDU_DEPT_NAME) + "</td>" + 
	                   "<td class=\"list_center\" nowrap>" + NvlStr(value.NDU_CLSS_NM) + "</td>" +
	                   "<td class=\"list\"'>" + NvlStr(value.NDU_POSIT_NM) + "</td>" +
	                   "<td class=\"list\"'></td>" +
					   "</tr>" +
					   "<tr>" + 
				       "<td colspan=\"5\" class=\"list_line\"></td>" + 
					   "</tr>";
					i++;
			});
			
			body.append(str);
			
			$("a[name='title']").on("click", function(e){ //제목 
				
				e.preventDefault();			
				fn_set_user_info2($(this));
			});
		}
		
		
		function fn_set_user_info2(obj, i){
			var tmp1 = "";
		
			tmp1 = obj.parent().find("#JID").val();			
			$("#CNRT_PERSON_ID").val(tmp1);
			
			tmp1 = "";
			tmp1 = obj.parent().find("#JNAME").val();			
			$("#CNRT_PERSON_NM").val(tmp1);
			
			tmp1 = "";
			tmp1 = obj.parent().find("#JDID").val();			
			$("#CNRT_DEPT_CD").val(tmp1);
			
			tmp1 = "";
			tmp1 = obj.parent().find("#JDEPT").val();			
			$("#CNRT_DEPT_NM").val(tmp1);
			
			$('#pop2').hide();
		}
		
		function fn_insertPrvCnrtContract(){
			var comSubmit = new ComSubmit("form1");
			var tmp = "";

			if($('#SAUP_ID').val() == "" && sel_val == 0)
			{
				alert('사업을 선택해 주세요!!');
				$('#SAUP_NM').focus();
				return false();
			}
			
			
			if($('#CNRT_YEAR1').val() == "")
			{
				alert('년도를 선택해 주세요!!');
				$('#CNRT_YEAR1').focus();
				return false();
			}
			
			if($('#CNRT_MONTH').val() == "")
			{
				alert('월을 선택해 주세요!!');
				$('#CNRT_MONTH').focus();
				return false();
			}
			
			if($('#CNRT_NM').val() == "")
			{
				alert('계약명을 입력해 주세요!!');
				$('#CNRT_NM').focus();
				return false();
			}
			
			if($('#CNRT_AMT').val() == "")
			{
				alert('계약금액을 입력해 주세요!!');
				$('#CNRT_AMT').focus();
				return false();
			}
						
			if($('#CNRT_PERSON_NM').val() == "")
			{
				alert('담당자를  입력해 주세요!!\n\n 오른쪽 끝에 찾기 옆의 네모칸에 이름을 입력하시고 찾기를 클릭하면 됩니다.');
				$('#CNRT_PERSON_NM').focus();
				return false();
			}
			
			if($('#CNRT_CHOOSE_REASON').val() == "")
			{
				alert('선정사유를 입력하세요.');
				$('#CNRT_CHOOSE_REASON').focus();
				return false();
			}
						
			tmp = $('#CNRT_YEAR1').val() + $('#CNRT_MONTH').val(); 
			$('#CNRT_YEAR').val(tmp);
						
			fn_amtCheck();
						
			if(confirm('데이터를 등록하시겠습니까?'))
			{												
				comSubmit.setUrl("<c:url value='/daejang/insertPrvCnrtReason.do' />");									
				comSubmit.submit();							
			}
		}
		
		function fn_yearList(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("/scms/common/yearList.do");
			comAjax.setCallback("fn_yearListCallback");			
			comAjax.ajax();
		}
		
		function fn_yearListCallback(data){
			data = $.parseJSON(data);
			var str = "";
			var i = 1;
					
			$.each(data.list, function(key, value){										
					str += "<option value=\"" + value.YEAR + "\">" + value.YEAR + "년</option>";
					i++;
			});
			
			$("#CNRT_YEAR1").append(str);			
		}	
		
		
		function fn_amtCheck(){
			
			if(uncomma($('#CNRT_AMT').val()) <  3300000 || uncomma($('#CNRT_AMT').val()) > 22000000 )
			{				
				alert('수의계약 관리시스템 금액기준을 벗어났습니다.!!\n한번 확인후 진행하시길 바랍니다.[330만원 ~ 2,200만원(예외있음)]');
				$('#CNRT_AMT').focus();
				$('#CNRT_AMT').select();
				//return false();
			}
			
		}
		
		function fn_addFile(){
			var str = "<p><input type='file' name='file_"+(gfv_count++)+"' onchange='fileCheck(this)'> <a href='#this' class='btn' name='delete'>삭제</a></p>";
			$("#fileDiv").append(str);
			$("a[name='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
		}
		
		function fileCheck(input){
			var word;
            var version = "N/A";
            var agent = navigator.userAgent.toLowerCase();
            var name = navigator.appName;

		    if(input.value!=""){				
                // IE old version ( IE 10 or Lower )
                if ( name == "Microsoft Internet Explorer" ) word = "msie ";
                else {
                    // IE 11
                    if ( agent.search("trident") > -1 ) word = "trident/.*rv:";
                    // IE 12  ( Microsoft Edge )
                    else if ( agent.search("edge/") > -1 ) word = "edge/";
                }

                var reg = new RegExp( word + "([0-9]{1,})(\\.{0,}[0-9]{0,1})" );

                if (  reg.exec( agent ) != null  )
                    version = RegExp.$1 + RegExp.$2;

				if( version >= 10 ){
						
						fileSize = input.files[0].size;
					    var maxSize = 10 * 1024 * 1024;//10MB				 
					    if(fileSize > maxSize){
					    	alert("파일 사이즈가 10MB를 넘습니다!! [" + comma(fileSize) + " Byte]");
					    }
					    else{
					    	fchk = 0;
					    }
			   	}						
		   }
	    }	
		
		
		function fn_deleteFile(obj){
			obj.parent().remove();
		}		
	</script>
	
<style>

input:read-only { 
	background-color:blue;
	}
</style>	
</head>

<body>
<form name="form1" id="form1" enctype="multipart/form-data">
<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="INS_DEPT" name="INS_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${map.INS_ID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="CNRT_DEPT_CD" name="CNRT_DEPT_CD" value="">
<input type="hidden" id="CNRT_PERSON_ID" name="CNRT_PERSON_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="COMP_ID" name="COMP_ID" value="${param.ID}">
<input type="hidden" id="DATA_GB" name="DATA_GB" value="2">
<input type="hidden" id="CNRT_EVAL" name="CNRT_EVAL" value="">
<input type="hidden" id="STATE_GB" name="STATE_GB" value="1">
<input type="hidden" id="SAUP_ID" name="SAUP_ID" value="">
<input type="hidden" id="CNRT_AGENCY_NM" name="CNRT_AGENCY_NM" value="강남구">
<input type="hidden" id="CNRT_YEAR" name="CNRT_YEAR" value="">
<input type="hidden" id="CNRT_DETAIL" name="CNRT_DETAIL" value="">
<input type="hidden" id="BOARD_GB" name="BOARD_GB" value="9">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/scms/images/popup_title.png" align="absmiddle">선정사유 등록</td>
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
            <td class="bodyframe_n" colspan="2">* 수의계약 관리시스템 입력 기준금액 : 330만원 ~ 2,200만원(부가세 포함)</td>
          </tr>
        </table>
        
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">	      	  
	          <tr>
	            <td width="120" class="tbl_field">사업선택</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="SAUP_NM" name="SAUP_NM" class="input" style="width:300;" readonly> &nbsp;* 발주계획에서 입력한 사업명을 <a href="#this" class="btn" id="open">클릭</a>하세요.</td>									
	          </tr>	                      				 	         
	          <tr>
	            <td width="120" class="tbl_field">계약년월</td>
	            <td width="607" colspan="3" class="tbl_list_left">	           
	            <select id="CNRT_YEAR1" name="CNRT_YEAR1" class="input">
	            <option value="">--------</option>
	            </select> &nbsp;
	            <select id="CNRT_MONTH" name="CNRT_MONTH" class="input">
	            <option value="">--------</option>
	            <option value="01">&nbsp;1월</option>
	            <option value="02">&nbsp;2월</option>
	            <option value="03">&nbsp;3월</option>
	            <option value="04">&nbsp;4월</option>
	            <option value="05">&nbsp;5월</option>
	            <option value="06">&nbsp;6월</option>
	            <option value="07">&nbsp;7월</option>
	            <option value="08">&nbsp;8월</option>
	            <option value="09">&nbsp;9월</option>
	            <option value="10">10월</option>
	            <option value="11">11월</option>
	            <option value="12">12월</option>
	            </select>	            	           
	            </td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">계약명</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="CNRT_NM" name="CNRT_NM" class="input" style="width:300;"> * 발주계획 생략사업은 <a href="#this" class="btn" id="sel">클릭</a>후 입력하시기 바랍니다.</td>									
	          </tr>	          
	          <tr>
	            <td width="120" class="tbl_field">계약금액</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="CNRT_AMT" name="CNRT_AMT" class="input" style="width:300;"> <b>원</b></td>									
	          </tr>	         
	          <tr>
	            <td width="120" class="tbl_field">담당자</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="CNRT_PERSON_NM" name="CNRT_PERSON_NM" value="${sessionScope.userinfo.userName}" class="input" style="width:300;" readonly>
	            <input type="text" id="USER_NAME" name="USER_NAME" class="input" style="width:80;"> <a href="#this" id="open2" class="bk">찾기</a> <a href="#this" id="clear" class="bk">지우기</a>
	            </td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">담당부서</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="CNRT_DEPT_NM" name="CNRT_DEPT_NM" value="${sessionScope.userinfo.deptName}" class="input" style="width:300;" readonly></td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">전화번호</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="CNRT_PERSON_TEL" name="CNRT_PERSON_TEL" value="${sessionScope.userinfo.usertel}" class="input" style="width:300;"> ex) 02-3423-1234</td>									
	          </tr>	        
	          <tr>
	            <td width="120" class="tbl_field">선정사유</td>
	            <td width="607" colspan="3" class="tbl_list_left">	           
	            <textarea rows="10" cols="76" id="CNRT_CHOOSE_REASON" name="CNRT_CHOOSE_REASON" class="input" style="width:557;"></textarea>
	            </td>
	          </tr>	          	          	        
	          <tr>
	            <td width="120" class="tbl_field">비고</td>
	            <td width="607" colspan="3" class="tbl_list_left">	            	           
	            <textarea rows="2" cols="76" id="BIGO" name="BIGO" class="input" style="width:557;"></textarea>	            
	            </td>									
	          </tr>
	           <tr>
	            <td width="120" class="tbl_field">업체선정평가<br>기준표</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <div id="fileDiv">
						<p>
							<input type="file" id="file" name="file_0" onchange="fileCheck(this)">
							<a href="#this" class="btn" id="delete" name="delete">삭제</a> (<b>10M이하</b>)
						</p>
					</div>
					<a href="#this" class="btn" id="addFile">파일 추가</a> (<b>10M이하</b>)           
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
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/scms/images/btn_type0_head.gif"></td>
                    <td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write"><font color="white">등록</font></a></td>
                    <td><img src="/scms/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>                               	
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/scms/images/btn_type1_head.gif"></td>
                    <td background="/scms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
                    <td><img src="/scms/images/btn_type1_end.gif"></td>
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
<div id="pop" style="position:absolute;left:395px;top:190;z-index:200;display:none;"> 
<table width=530 height=250 cellpadding=2 cellspacing=0>
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
				<td class="list_tit_line_s" width="70"></td>
				<td class="list_tit_line_s" width="100%"></td>
				<td class="list_tit_line_s" width="40"></td>
				<td class="list_tit_line_s" width="80"></td>			
			</tr>
			<tr class="list_tit_tr">
				<td nowrap class="list_tit"></td>
				<td nowrap class="list_tit_bar">발주시기</td>
				<td nowrap class="list_tit_bar">사업명</td>
				<td nowrap class="list_tit_bar">유형</td>
				<td nowrap class="list_tit_bar">예산금액</td>						
			</tr>
			<tr>
				<td colspan="5" class="list_tit_line_e"></td>
			</tr>
			<tbody id='mytable'>			
			</tbody>
			<tr>
				<td colspan="5" height="20"></td>
			</tr>
			<tr>
				<td colspan="5">
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


<!-- POPUP --> 
<div id="pop2" style="position:absolute;left:395px;top:190;z-index:200;display:none;"> 
<table width=530 height=250 cellpadding=2 cellspacing=0>
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
				<td class="list_tit_line_s" width="160"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="100%"></td>
				<td class="list_tit_line_s" width="60"></td>			
			</tr>
			<tr class="list_tit_tr">
				<td nowrap class="list_tit"></td>
				<td nowrap class="list_tit_bar">이름</td>
				<td nowrap class="list_tit_bar">부서</td>
				<td nowrap class="list_tit_bar">직급</td>
				<td nowrap class="list_tit_bar">직위</td>						
			</tr>
			<tr>
				<td colspan="5" class="list_tit_line_e"></td>
			</tr>
			<tbody id='mytable2'>			
			</tbody>
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
