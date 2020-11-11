<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>수의계약 관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var gfv_count = 1;
		var tmp2 = "";
		var i = 1;
		var fchk = 0;  //첨부파일 사이즈 체크
		
		$(document).ready(function(){
			
			fn_yearList();				
						
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_prvCnrtPlanList();
			});
			
			$("#write").on("click", function(e){ //등록하기 버튼							
				e.preventDefault();				
				fn_prvCnrtPlanWrite();				
			});
						
			$('#close1').click(function() {
		        $('#pop').hide();
		    });
			
			$('#close2').click(function() {
		        $('#pop').hide();
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
			
			$('#clear').click(function() {
				
				$('#SAUP_PERSON_ID').val("");
				$('#SAUP_PERSON_NM').val("");
				$('#SAUP_DEPT_CD').val("");
				$('#SAUP_DEPT_NM').val("");							
		    });
			
			$("#addFile").on("click", function(e){ //파일 추가 버튼
				e.preventDefault();
				fn_addFile();
			});
			
			$("a[name='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
			
			$("#SAUP_BUDGET_AMT").bind('keyup keydown',function(){
		        inputNumberFormat(this);
		    });
			
			$("#ACCEPT_ST_DATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			$("#ACCEPT_ED_DATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
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
	 
	    //숫자만 리턴(저장할때)
	    //alert(cf_getNumberOnly('1,2./3g')); -> 123 return
	    function cf_getNumberOnly (str) {
	        var len      = str.length;
	        var sReturn  = "";
	 
	        for (var i=0; i<len; i++){
	            if ( (str.charAt(i) >= "0") && (str.charAt(i) <= "9") ){
	                sReturn += str.charAt(i);
	            }
	        }
	        return sReturn;
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
				fn_set_user_info($(this));
			});
		}
		
		
		function fn_set_user_info(obj, i){
			var tmp1 = "";
		
			tmp1 = obj.parent().find("#JID").val();			
			$("#SAUP_PERSON_ID").val(tmp1);
			
			tmp1 = "";
			tmp1 = obj.parent().find("#JNAME").val();
			$("#SAUP_PERSON_NM").val(tmp1);
			
			tmp1 = "";
			tmp1 = obj.parent().find("#JDID").val();			
			$("#SAUP_DEPT_CD").val(tmp1);
			
			tmp1 = "";
			tmp1 = obj.parent().find("#JDEPT").val();			
			$("#SAUP_DEPT_NM").val(tmp1);
			
			$('#pop').hide();
		}
		
		
		function fn_prvCnrtPlanList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtPlanList.do' />");		
			comSubmit.submit();
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
			
			$("#SAUP_YEAR1").append(str);			
		}	
		
		
		function fn_prvCnrtPlanWrite(){
			var comSubmit = new ComSubmit("form1");
			var tmp = "";
		
			if($('#SAUP_YEAR1').val() == "")
			{
				alert('발주시기 년도를 선택하세요!!');
				$('#SAUP_YEAR1').focus();
				return false();
			}
			
			if($('#SAUP_MONTH').val() == "")
			{
				alert('발주시기 월을 선택하세요!!');
				$('#SAUP_MONTH').focus();
				return false();
			}
			
			if($("input:radio[name='SAUP_FIELD1_GB']").is(':checked') == false)					
			{
				alert('유형을  선택하세요!!');
				return false();
			}
			
			if($('#SAUP_NM').val() == "")
			{
				alert('사업명을  입력하세요!!');
				$('#SAUP_NM').focus();
				return false();
			}
			
			if($('#SAUP_BUDGET_AMT').val() == "")
			{
				alert('예산액을 입력하세요!!');
				$('#SAUP_BUDGET_AMT').focus();
				return false();
			}
								
			if($('#SAUP_DETAIL').val() == "")
			{
				alert('사업내용을  입력하세요!!');
				$('#SAUP_DETAIL').focus();
				return false();
			}
			
			
			if($('#SAUP_PERSON_NM').val() == "")
			{
				alert('담장자 이름을  입력하세요!!\n\n오른쪽 끝에 찾기 왼쪽에 이름을 입력하시고 찾기를 클릭하면 직원이름을 선택할 수 있습니다.');
				$('#SAUP_PERSON_NM').focus();
				return false();
			}
			
			if($('#SAUP_PERSON_TEL').val() == "")
			{
				alert('담당자 전화번호를  입력하세요!!');
				$('#SAUP_PERSON_TEL').focus();
				return false();
			}
			
			if($('#ACCEPT_ST_DATE').val() == "")
			{
				alert('접수시작일을  입력하세요!!');
				$('#ACCEPT_ST_DATE').focus();
				return false();
			}
			
			if($('#ACCEPT_ED_DATE').val() == "")
			{
				alert('접수마감일을  입력하세요!!');
				$('#ACCEPT_ED_DATE').focus();
				return false();
			}
			
			
			tmp = $('#SAUP_YEAR1').val() + $('#SAUP_MONTH').val(); 
			$('#SAUP_YEAR').val(tmp);			
			
			fn_amtCheck();
			
			if(fchk)
			{
				alert("첨부파일 사이즈는  한화면에 쵀대 20MB 이내로 등록 가능합니다.");
				return false;
			}
						
			if(confirm('데이터를 등록하시겠습니까?'))
			{							
				comSubmit.setUrl("<c:url value='/daejang/insertPrvCnrtPlan.do' />");
				comSubmit.submit();
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
		
		function fn_deleteFile(obj){
			obj.parent().remove();
		}		
		
		
		function fn_amtCheck(){
			
			if(uncomma($('#SAUP_BUDGET_AMT').val()) <  3300000 || uncomma($('#SAUP_BUDGET_AMT').val()) > 22000000 )
			{				
				alert('수의계약 관리시스템 금액기준을 벗어났습니다.!!\n한번 확인후 진행하시길 바랍니다.[330만원 ~ 2,200만원(예외있음)]');
				$('#SAUP_BUDGET_AMT').focus();
				$('#SAUP_BUDGET_AMT').select();
// 				return false();
			}			
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
	</script>
	
	
<style>

input:read-only { 
	background-color:blue;
	}
</style>	
</head>

<body>
<form id="form1" name="form1" enctype="multipart/form-data">
<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="INS_DEPT" name="INS_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${map.INS_ID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="SAUP_FIELD2_DETAIL" name="SAUP_FIELD2_DETAIL" value="">
<input type="hidden" id="SAUP_DEPT_CD" name="SAUP_DEPT_CD" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="SAUP_PERSON_ID" name="SAUP_PERSON_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="SAUP_FIELD2_GB" name="SAUP_FIELD2_GB" value="">
<input type="hidden" id="SAUP_YEAR" name="SAUP_YEAR" value="">
<input type="hidden" id="BOARD_GB" name="BOARD_GB" value="3"> 

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/scms/images/popup_title.png" align="absmiddle">발주계획 등록</td>
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
	            <td width="120" class="tbl_field">발주시기</td>
	            <td width="607" colspan="3" class="tbl_list_left">
	            <select id="SAUP_YEAR1" name="SAUP_YEAR1" class="input">
	            <option value="">--------</option>
	            </select> &nbsp;
	            <select id="SAUP_MONTH" name="SAUP_MONTH" class="input">
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
	            <td width="120" class="tbl_field">유형</td>
	            <td width="607" colspan="3" class="tbl_list_left">
	            <input type="radio" id="SAUP_FIELD1_GB1" name="SAUP_FIELD1_GB" value="1" class="input"> 공사 &nbsp; <input type="radio" id="SAUP_FIELD1_GB2" name="SAUP_FIELD1_GB" value="2" class="input"> 용역 &nbsp; <input type="radio" id="SAUP_FIELD1_GB3" name="SAUP_FIELD1_GB" value="3" class="input"> 물품	            
	            </td>									
	          </tr>	          
	          <tr>
	            <td width="120" class="tbl_field">사업명</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="SAUP_NM" name="SAUP_NM" class="input" style="width:300;"></td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">예산액</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="SAUP_BUDGET_AMT" name="SAUP_BUDGET_AMT" class="input" maxlength="13" style="width:300;"> 원</td>									
	          </tr>	         	         	          
	          <tr>
	            <td width="120" class="tbl_field">세부사업내용</td>
	            <td width="607" colspan="3" class="tbl_list_left">
	             <textarea rows="10" cols="76" id="SAUP_DETAIL" name="SAUP_DETAIL" class="input" style="width:557;"></textarea>
	            </td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">첨부파일</td>
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
	          <tr>
	            <td width="120" class="tbl_field">담당자</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="SAUP_PERSON_NM" name="SAUP_PERSON_NM" value="${sessionScope.userinfo.userName}" class="input" style="width:300;" readonly>
	            <input type="text" id="USER_NAME" name="USER_NAME" class="input" style="width:80;"> <a href="#this" id="open" class="bk">찾기</a> <a href="#this" id="clear" class="bk">지우기</a>
	            </td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">소관부서</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="SAUP_DEPT_NM" name="SAUP_DEPT_NM" value="${sessionScope.userinfo.deptName}" class="input" style="width:300;" readonly></td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">전화번호</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="SAUP_PERSON_TEL" name="SAUP_PERSON_TEL" value="${sessionScope.userinfo.usertel}" class="input" style="width:300;"> ex) 02-3423-1234</td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">접수기간</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="ACCEPT_ST_DATE" name="ACCEPT_ST_DATE" value="" class="input" style="width:100;"> ~ <input type="text" id="ACCEPT_ED_DATE" name="ACCEPT_ED_DATE" value="" class="input" style="width:100;"><p><b>* 접수마감일은  주말, 공휴일 제외하고 공개일 다음날부터 3일입니다.</b></p></td>									
	          </tr>	              	        
	          <tr>
	            <td width="120" class="tbl_field">비고</td>
	            <td width="607" colspan="3" class="tbl_list_left"><textarea rows="2" cols="76" id="BIGO" name="BIGO" class="input" style="width:557;"></textarea></td>									
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
                    <td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write"><font color="white">사전승인요청</font></a></td>
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
        <a href="#" id="close2"><B>[닫기]</B></a> 
    </td> 
</tr>      
</table> 
</div>

</body>
</html>