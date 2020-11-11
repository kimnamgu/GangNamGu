<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>위반건축물 관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var tmp2 = "";
		var i = 1;
		var input_chk = 0;
		var input_chk2 = 0;
		
		$(document).ready(function(){
			
			fn_structList(); //구조콤보			
			fn_purposeList(); //용도
			fn_stateList();  //상태
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_violBuildingList();
			});
			
			$("#write").on("click", function(e){ //등록하기 버튼							
				e.preventDefault();
				if( $('#USER_RIGHT').val() == 'A')
				{
					fn_violBuildingWrite();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			
			$("#SNUM").change(function(){
				var num=0;		
				
				num = $("#SNUM option:selected").val();
				
				$("#kkk").val("직접수행");

				
			});
			
			
			$("#juso").on("click", function(e){ //주소찾기					
				e.preventDefault();
				var pop = window.open("/vbms/daejang/samplepop.do","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
			});
			
			$("#mjuso").on("click", function(e){ //주소찾기					
				e.preventDefault();
				var mpop = window.open("/vbms/daejang/msamplepop.do","mpop","width=570,height=420, scrollbars=yes, resizable=yes"); 
			});
			
			$("#basic").on("click", function(e){ //기본정보
				e.preventDefault();
				fn_incidentBasicWrite();
			});
			
			$("#indsim").on("click", function(e){ //1,2,3심
				e.preventDefault();								
				fn_indTrialWrite();				
			});
			
			
			$("#PRE_CORRTN_ORDER").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			$("#CORRTN_ORDER").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			$("#VIOL_BUILDING_REGDATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			$("#OPINION_STATE_LIMIT").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			$("#EXEC_IMPOSE_DATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			
			$("#TAX_DEP_NOTEDATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			
			$("#EXEC_IMPOSE_AMT").bind('keyup keydown',function(){
		        inputNumberFormat(this);
		    });
			
			$('#clear').click(function() {
				
				$('#PERFORM_PERSON').val("");
				$('#PERFORM_USERID').val("");
				$('#PERFORM_USERHNO').val("");
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
			
			
			$("#USER_NAME").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$('#pop').show();
					fn_jikwonlist();
				}
			});
			
			
			$("#manual").on("click", function(e){ //수동입력					
				var chk = $("input:checkbox[id='m_chk']").is(":checked");			    
		    	if(chk) {		    	
		    		$("input:checkbox[id='m_chk']").prop("checked", false);
		    		input_chk = 0;		    		
		    	}
			    else{
			    	$("input:checkbox[id='m_chk']").prop("checked", true);
			    	input_chk = 1;		    		
			    }
			});
			
			$("#m_chk").click(function(){
				
				var chk = $(this).is(":checked");//.attr('checked');
		    	if(chk) {		    	
		    		$("input:checkbox[id='m_chk']").prop("checked", true);
		    		input_chk = 1;		    		
		    	}
			    else{
			    	$("input:checkbox[id='m_chk']").prop("checked", false);
			    	input_chk = 0;		    		
			    }

			});
			
			
			
			$("#manual2").on("click", function(e){ //수동입력					
				var chk2 = $("input:checkbox[id='m_chk2']").is(":checked");			    
		    	if(chk2) {		    	
		    		$("input:checkbox[id='m_chk2']").prop("checked", false);
		    		input_chk2 = 0;		    		
		    	}
			    else{
			    	$("input:checkbox[id='m_chk2']").prop("checked", true);
			    	input_chk2 = 1;		    		
			    }
			});
			
			$("#m_chk2").click(function(){
				
				var chk2 = $(this).is(":checked");//.attr('checked');
		    	if(chk2) {		    	
		    		$("input:checkbox[id='m_chk2']").prop("checked", true);
		    		input_chk2 = 1;		    		
		    	}
			    else{
			    	$("input:checkbox[id='m_chk2']").prop("checked", false);
			    	input_chk2= 0;		    		
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
		
		
		
		function fn_violBuildingList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/violBuildingList.do' />");
			comSubmit.submit();
		}
		
		
		
		function fn_jikwonlist(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/common/popJikWonList.do' />");
			comAjax.setCallback("fn_popJikWonListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());
			comAjax.addParam("USER_NAME",$("#USER_NAME").val());
			comAjax.ajax();
		}
		
		function fn_popJikWonListCallback(data){
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
					   "<input type='hidden' name='JCID' id='JCID' value=" + NvlStr(value.NDU_CLSS_NO) + ">" +
					   "<input type='hidden' name='JCLASS' id='JCLASS' value=" + NvlStr(value.NDU_CLSS_NM) + ">" +
					   "<input type='hidden' name='JHNO' id='JHNO' value=" + NvlStr(value.NDU_HP_NO) + ">" +
					   " &nbsp; <input type='text' name='I_JHNO' id='I_JHNO' value='" + NvlStr(value.NDU_HP_NO) +"' style='width:90;'>" +
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
			var tmp2 = "";
			
			tmp1 = $("#PERFORM_PERSON").val();
			if(tmp1.replace(/ /g, '') != "")
				tmp1 = tmp1.replace(/ /g, '') + ",";
			tmp2 = obj.parent().find("#JNAME").val();
			
			//tmp1 = tmp1.replace(/ /g, '');
			
			
			$("#PERFORM_PERSON").val(tmp1 + tmp2);
			
			
			tmp1 = $("#PERFORM_USERID").val();
			if(tmp1.replace(/ /g, '') != "")
				tmp1 = tmp1.replace(/ /g, '') + ",";
			tmp2 = obj.parent().find("#JID").val();
			
			//tmp1 = tmp1.replace(/ /g, '');
			
			$("#PERFORM_USERID").val(tmp1 + tmp2);
			
			
			tmp1 = $("#PERFORM_USERHNO").val();
			if(tmp1.replace(/ /g, '') != ""){
				tmp1 = tmp1.replace(/ /g, '');
				tmp1 = tmp1.replace(/-/g, '') + ",";
			}
			tmp2 = obj.parent().find("#JHNO").val();
			//alert(tmp2);
			//alert("val=[" + obj.parent().find("#JHNO").val() + "]");
			if(tmp2 == '' || tmp2.length == 0)
			{				
				tmp2 = 	obj.parent().find("#I_JHNO").val();
				//alert(tmp2);
			}
			
			tmp2 = tmp2.replace(/-/g, '');
			
			$("#PERFORM_USERHNO").val(tmp1 + tmp2);			
			
			//alert($("#PERFORM_USERHNO").val());
			
			//$('#pop').hide();
		}
		
		
		
		function fn_structList(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/common/structList.do' />");
			comAjax.setCallback("fn_structListCallback");			
			comAjax.ajax();
			
		}
		
		function fn_structListCallback(data){
			data = $.parseJSON(data);
			
			var str = "";
			var i = 1;
					
			$.each(data.list, function(key, value){										
					str += "<option value=\"" + value.CODE_ID + "\">" + value.CODE_NAME + "</option>";
					i++;
			});
			//alert(str);
			$("#BLD_STRUCTURE").append(str);			
		}	
		
		
		
		
		function fn_purposeList(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/common/purposeList.do' />");
			comAjax.setCallback("fn_purposeListCallback");			
			comAjax.ajax();
			
		}
		
		function fn_purposeListCallback(data){
			data = $.parseJSON(data);
			
			var str = "";
			var i = 1;
					
			$.each(data.list, function(key, value){										
					str += "<option value=\"" + value.CODE_ID + "\">" + value.CODE_NAME + "</option>";
					i++;
			});
			//alert(str);
			$("#PURPOSE").append(str);			
		}	
		
		
		
		
		function fn_stateList(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/common/stateList.do' />");
			comAjax.setCallback("fn_stateListCallback");			
			comAjax.ajax();
			
		}
		
		function fn_stateListCallback(data){
			data = $.parseJSON(data);
			
			var str = "";
			var i = 1;
					
			$.each(data.list, function(key, value){										
					str += "<option value=\"" + value.CODE_ID + "\">" + value.CODE_NAME + "</option>";
					i++;
			});
			//alert(str);
			$("#STATE").append(str);			
		}	
		
		
		function fn_violBuildingWrite(){
			var comSubmit = new ComSubmit("form1");
			var tmp = "";
			
			
			/*if($('#BLD_ZIPNO').val() == "")
			{
				alert('오른쪽 끝에 "주소찾기"를 클릭하여 주소검색후 선택해 주세요!!');
				$('#BLD_ZIPNO').focus();
				return false();
			}*/
			
			/*if($('#BLD_OWNER').val() == "")
			{
				alert('건축주를 입력해 주세요!!');
				$('#BLD_OWNER').focus();
				return false();
			}
						
			if($('#MBLD_ZIPNO').val() == "")
			{
				alert('오른쪽 끝에 "주소찾기"를 클릭하여 주소검색후 선택해 주세요!!');
				$('#MBLD_ZIPNO').focus();
				return false();
			}
			
			if($('#BLD_STRUCTURE').val() == "00")
			{
				alert('구조를 선택해 주세요!!');
				$('#BLD_STRUCTURE').focus();
				return false();
			}
			
			if($('#VIOL_AREA').val() == "")
			{
				alert('면적을 입력하세요!!');
				$('#VIOL_AREA').focus();
				return false();
			}
									
			if($('#PURPOSE').val() == "00")
			{
				alert('용도를 선택하세요!!');
				$('#PURPOSE').focus();
				return false();
			}
			
			if($('#LOCATION').val() == "")
			{
				alert('위치를 입력해주세요!!');
				$('#LOCATION').focus();
				return false();
			}
			
			if($('#PRE_CORRTN_ORDER').val() == "")
			{
				alert('시전명령 사전예고일을 입력하세요!!');
				$('#PRE_CORRTN_ORDER').focus();
				return false();
			}
			
			if($('#CORRTN_ORDER').val() == "")
			{
				alert('시전명령일을 입력하세요!!');
				$('#CORRTN_ORDER').focus();
				return false();
			}
			
			if($('#VIOL_BUILDING_REGDATE').val() == "")
			{
				alert('위반건축물 등재일을 입력하세요!!');
				$('#VIOL_BUILDING_REGDATE').focus();
				return false();
			}
			
			if($('#OPINION_STATE_LIMIT').val() == "")
			{
				alert('이행강제금 부과 사전예고를 입력하세요!!');
				$('#OPINION_STATE_LIMIT').focus();
				return false();
			}
			
			
			if($('#EXEC_IMPOSE_DATE').val() == "")
			{
				alert('이행금 강제부과일을 입력하세요!!');
				$('#EXEC_IMPOSE_DATE').focus();
				return false();
			}
			
			
			if($('#EXEC_IMPOSE_AMT').val() == "")
			{
				alert('이행강제금을 입력하세요!!');
				$('#EXEC_IMPOSE_AMT').focus();
				return false();
			}
			
			if($('#TAX_DEP_NOTEDATE').val() == "")
			{
				alert('세무과통보일을 입력하세요!!');
				$('#TAX_DEP_NOTEDATE').focus();
				return false();
			}
			
			
			if($('#PERFORM_PERSON').val() == "")
			{
				alert('담당직원을 입력하세요!!');
				$('#PERFORM_PERSON').focus();
				return false();
			}
			*/
			
			if( input_chk == 1 )
			{			
				var jibunAddr = $('#jibunAddr').val();								
				var StrArray = jibunAddr.split(' ');				
				var roadAddrPart1 = $('#roadAddrPart1').val();
				var findStr = "구 ";
								
				$('#jibunAddr').val(jibunAddr);				
				$('#BLD_DONG').val( StrArray[2]);
				$('#BLD_ADDR1').val( StrArray[3]);
				$('#BLD_ADDR_ROAD').val(roadAddrPart1.substring(roadAddrPart1.indexOf(findStr)+1));
				$('#FULL_BLD_ADDR').val(jibunAddr);
				$('#FULL_BLD_ADDR_ROAD').val(roadAddrPart1);
				
			}
			
			
			
			if( input_chk2 == 1 )
			{			
				var mjibunAddr = $('#mjibunAddr').val();
				var mStrArray = mjibunAddr.split(' ');
				var mroadAddrPart1 = $('#mroadAddrPart1').val();
				var mfindStr = "구 ";
				
				$('#mjibunAddr').val(mjibunAddr);				
				$('#MBLD_DONG').val( mStrArray[2]);
				$('#MBLD_ADDR1').val( mStrArray[3]);
				$('#MBLD_ADDR_ROAD').val(mroadAddrPart1.substring(mroadAddrPart1.indexOf(mfindStr)+1));
				$('#MFULL_BLD_ADDR').val(mjibunAddr);
				$('#MFULL_BLD_ADDR_ROAD').val(mroadAddrPart1);
				
			}
			
						
			if(confirm('데이터를 등록하시겠습니까?'))
			{							
				comSubmit.setUrl("<c:url value='/daejang/insBldMngDaejang.do' />");									
				comSubmit.submit();
			}
		}
		
		
		function trim(str) {
		    return str.replace(/(^\s*)|(\s*$)/gi, '');
		}	

		function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo){
				
			    
				// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
				//document.form1.roadFullAddr.value = roadFullAddr;
				document.form1.roadAddrPart1.value = roadAddrPart1;
				//document.form.roadAddrPart2.value = roadAddrPart2;
				document.form1.addrDetail.value = addrDetail;
				//document.form.engAddr.value = engAddr;
				document.form1.jibunAddr.value = jibunAddr;				
				document.form1.BLD_ZIPNO.value = zipNo;
				
				
				var findStr = "구 ";				
				var StrArray = jibunAddr.split(' ');
				var strbuff = "";
								
				
				document.form1.BLD_DONG.value = StrArray[2];			
				document.form1.BLD_ADDR2.value = addrDetail;
				//alert('333');
				//document.form1.BLD_ADDR1.value = jibunAddr.substring(jibunAddr.indexOf(findStr)+1);  // 지번주소(강남구 이전 제외)
				document.form1.BLD_ADDR1.value = StrArray[3];  // 지번주소(동 이전 제외)				
				//document.form1.BLD_ADDR_ROAD.value = roadAddrPart1.substring(roadAddrPart1.indexOf(findStr)+1).trim(); //도로명주소 (강남구 이전 제외 )
				document.form1.BLD_ADDR_ROAD.value = roadAddrPart1.substring(roadAddrPart1.indexOf(findStr)+1); //도로명주소 (강남구 이전 제외 )
				//alert('444');
				$('#FULL_BLD_ADDR').val(jibunAddr);				
				$('#FULL_BLD_ADDR_ROAD').val(roadAddrPart1);
		}
		
		
		
		function mjusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo){
			
		    
			
			document.form1.mroadAddrPart1.value = roadAddrPart1;
			document.form1.maddrDetail.value = addrDetail;
			document.form1.mjibunAddr.value = jibunAddr;				
			document.form1.MBLD_ZIPNO.value = zipNo;
			
			
			var findStr = "구 ";				
			var StrArray = jibunAddr.split(' ');
			var strbuff = "";
			
			document.form1.MBLD_DONG.value = StrArray[2];			
			document.form1.MBLD_ADDR2.value = addrDetail;
			document.form1.MBLD_ADDR1.value = jibunAddr;
			document.form1.MBLD_ADDR_ROAD.value = roadAddrPart1;
			
			$('#MFULL_BLD_ADDR').val(jibunAddr);				
			$('#MFULL_BLD_ADDR_ROAD').val(roadAddrPart1);
	}
				
	


	</script>
	
	
<style>

input:read-only { 
	background-color:blue;
	}
</style>	
</head>

<body>
<form name="form1" id="form1">
<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="INS_DEPT" name="INS_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${map.INS_ID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="BLD_ADDR1" name="BLD_ADDR1" value="">
<input type="hidden" id="BLD_ADDR2" name="BLD_ADDR2" value="">
<input type="hidden" id="BLD_ADDR_ROAD" name="BLD_ADDR_ROAD" value="">
<input type="hidden" id="BLD_DONG" name="BLD_DONG" value="">
<input type="hidden" id="FULL_BLD_ADDR" name="FULL_BLD_ADDR" value="">
<input type="hidden" id="FULL_BLD_ADDR_ROAD" name="FULL_BLD_ADDR_ROAD" value="">
<input type="hidden" id="MBLD_ADDR1" name="MBLD_ADDR1" value="">
<input type="hidden" id="MBLD_ADDR2" name="MBLD_ADDR2" value="">
<input type="hidden" id="MBLD_ADDR_ROAD" name="MBLD_ADDR_ROAD" value="">
<input type="hidden" id="MBLD_DONG" name="MBLD_DONG" value="">
<input type="hidden" id="MFULL_BLD_ADDR" name="MFULL_BLD_ADDR" value="">
<input type="hidden" id="MFULL_BLD_ADDR_ROAD" name="MFULL_BLD_ADDR_ROAD" value="">
<input type="hidden" id="PERFORM_USERID" name="PERFORM_USERID" value="">
<input type="hidden" id="PERFORM_USERHNO" name="PERFORM_USERHNO" value="">
<input type="hidden" id="DISPLAY_YN" name="DISPLAY_YN" value="0">
<input type="hidden" id="UPLOAD_FLAG" name="UPLOAD_FLAG" value="0">


<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/vbms/images/popup_title.gif" align="absmiddle">위반건축물 관리시스템</td>
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
	            <td width="140" class="tbl_field">건축물 주소</td>
	            <td width="630" colspan="3" class="tbl_list_left"><input type="text" id="BLD_ZIPNO" name="BLD_ZIPNO" class="input" style="width:70;"> &nbsp;<input type="text" id="jibunAddr" name="jibunAddr" class="input" style="width:300;">&nbsp; <input type="text" id="addrDetail" name="addrDetail" class="input" style="width:100;">	            
	            <a href="#this" class="btn" id="juso">주소찾기</a> &nbsp;&nbsp;<input type="checkbox" id="m_chk" name="m_chk" class="input"> <a href="#this" class="btn" id="manual">직접입력</a>
	            </td>									
	          </tr>
	          <tr>
	            <td width="140" class="tbl_field">건축물 도로명 주소</td>
	            <td width="630" colspan="3" class="tbl_list_left"><input type="text" id="roadAddrPart1" name="roadAddrPart1" class="input" style="width:400;"></td>									
	          </tr>
	          <tr>
	            <td width="140" class="tbl_field">건축주</td>
	            <td width="630" colspan="3" class="tbl_list_left"><input type="text" id="BLD_OWNER" name="BLD_OWNER" class="input" style="width:200;"></td>									
	          </tr>
	           <tr>
	            <td width="140" class="tbl_field">건축주 주소</td>
	            <td width="630" colspan="3" class="tbl_list_left"><input type="text" id="MBLD_ZIPNO" name="MBLD_ZIPNO" class="input" style="width:70;"> &nbsp;<input type="text" id="mjibunAddr" name="mjibunAddr" class="input" style="width:300;">&nbsp; <input type="text" id="maddrDetail" name="maddrDetail" class="input" style="width:100;">	            
	            <a href="#this" class="btn" id="mjuso">주소찾기</a> &nbsp;&nbsp;<input type="checkbox" id="m_chk2" name="m_chk2" class="input"> <a href="#this" class="btn" id="manual2">직접입력</a>
	            </td>									
	          </tr>
	          <tr>
	            <td width="140" class="tbl_field">건축주 도로명 주소</td>
	            <td width="630" colspan="3" class="tbl_list_left"><input type="text" id="mroadAddrPart1" name="mroadAddrPart1" class="input" style="width:400;"></td>									
	          </tr>	          
	          <tr>
	            <td width="140" class="tbl_field">구조</td>
	            <td width="630" colspan="3" class="tbl_list_left">
	            <select id="BLD_STRUCTURE" name="BLD_STRUCTURE" class="input">
	            	<option value="00">------------------------</option>	                	
	            </select> &nbsp;&nbsp;<input type="text" id="STRUCTURE_DETAIL" name="STRUCTURE_DETAIL" class="input" style="width:200;">
	            </td>									
	          </tr>
	          <tr>
	            <td width="140" class="tbl_field">면적</td>
	            <td width="630" colspan="3" class="tbl_list_left"><input type="text" id="VIOL_AREA" name="VIOL_AREA" class="input" style="width:200;"></td>									
	          </tr>	          
	          <tr>
	            <td width="140" class="tbl_field">용도</td>
	            <td width="630" colspan="3" class="tbl_list_left">
	            <select id="PURPOSE" name="PURPOSE" class="input">
	            	<option value="00">--------</option>	                	
	            </select> &nbsp;<input type="text" id="PURPOSE_DETAIL" name="PURPOSE_DETAIL" class="input" style="width:200;">
	            </td>									
	          </tr>
	          <tr>
	            <td width="140" class="tbl_field">층</td>
	            <td width="630" colspan="3" class="tbl_list_left"><input type="text" id="LOCATION" name="LOCATION" class="input" style="width:200;"></td>									
	          </tr>
	          <tr>
	            <td width="140" class="tbl_field">적발방법</td>
	            <td width="630" colspan="3" class="tbl_list_left"><input type="text" id="EXPOSURE_DETAILS" name="EXPOSURE_DETAILS" class="input" style="width:200;"></td>									
	          </tr>	          
	          <tr>
	            <td width="140" class="tbl_field">시정명령 사전예고</td>
	            <td width="630" colspan="3" class="tbl_list_left"><input type="text" id="PRE_CORRTN_ORDER" name="PRE_CORRTN_ORDER" class="input" style="width:200;"></td>									
	          </tr>
	           
	          <tr>
	            <td width="140" class="tbl_field">시정명령</td>
	            <td width="630" colspan="3" class="tbl_list_left"><input type="text" id="CORRTN_ORDER" name="CORRTN_ORDER" class="input" style="width:200;"></td>									
	          </tr>
	          <!-- 
	          <tr>
	            <td width="140" class="tbl_field">위반건축물 등재일</td>
	            <td width="630" colspan="3" class="tbl_list_left"><input type="text" id="VIOL_BUILDING_REGDATE" name="VIOL_BUILDING_REGDATE" class="input" style="width:200;"></td>									
	          </tr>	          	           
	          <tr>
	            <td width="140" class="tbl_field">세무과통보</td>
	            <td width="630" colspan="3" class="tbl_list_left"><input type="text" id="TAX_DEP_NOTEDATE" name="TAX_DEP_NOTEDATE" class="input" style="width:200;"></td>									
	          </tr>
	          -->
	          <tr>
	            <td width="140" class="tbl_field">이행강제금부과 사전예고</td>
	            <td width="630" colspan="3" class="tbl_list_left"><input type="text" id="OPINION_STATE_LIMIT" name="OPINION_STATE_LIMIT" class="input" style="width:200;"></td>									
	          </tr>
	          <tr>
	            <td width="140" class="tbl_field">이행강제금부과일</td>
	            <td width="630" colspan="3" class="tbl_list_left"><input type="text" id="EXEC_IMPOSE_DATE" name="EXEC_IMPOSE_DATE" class="input" style="width:200;"></td>									
	          </tr>
	          <tr>
	            <td width="140" class="tbl_field">이행강제금</td>
	            <td width="630" colspan="3" class="tbl_list_left"><input type="text" id="EXEC_IMPOSE_AMT" name="EXEC_IMPOSE_AMT" class="input" style="width:200;"></td>									
	          </tr>	          
	          <tr>
	            <td width="140" class="tbl_field">처리결과</td>
	            <td width="630" colspan="3" class="tbl_list_left">
	            <select id="STATE" name="STATE" class="input">
	            	<option value="00">------</option>	                	
	            </select> &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="STATE_DETAIL" name="STATE_DETAIL" class="input" style="width:100;">
	            </td>									
	          </tr>
	          
	          
	          <tr>
	            <td width="140" class="tbl_field">담당직원</td>
	            <td width="630" colspan="3" class="tbl_list_left"><input type="text" id="PERFORM_PERSON" name="PERFORM_PERSON" class="input" style="width:200;"> 
	            <p><input type="text" id="USER_NAME" name="USER_NAME" class="input" style="width:80;" > <a href="#this" id="open" class="bk">찾기</a> <a href="#this" id="clear" class="bk">지우기</a></p></td>									
	          </tr>
	          
	          
	          <tr>
	            <td width="140" class="tbl_field">비고</td>
	            <td width="630" colspan="3" class="tbl_list_left"><input type="text" id="BIGO" name="BIGO" class="input" style="width:200;"></td>									
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
                    <td><img src="/vbms/images/btn_type0_head.gif"></td>
                    <td background="/vbms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write"><font color="white">등록</font></a></td>
                    <td><img src="/vbms/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>                               	
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/vbms/images/btn_type1_head.gif"></td>
                    <td background="/vbms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
                    <td><img src="/vbms/images/btn_type1_end.gif"></td>
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

</body>
</html>
