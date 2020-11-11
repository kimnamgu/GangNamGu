<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>


<script type="text/javascript">
	var user_right = "${sessionScope.userinfo.userright}";

	$(document).ready(
		function() {
			
			fn_setting_val(); //입력된 값 셋팅		
		
			//입력일
			$("#WRITE_DATE").datepicker({
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			//방문일자
			$("#CLINIC_VISIT_DATE").datepicker({
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			//생년월일
			$("#BIRTH").datepicker({
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			//정보수정
			$("#update").on("click", function(e){
				e.preventDefault();				
				fn_update();				
			});
			
			//자가격리주소찾기	
			$("#juso_iso").on("click", function(e){ 				
				e.preventDefault();
				var pop = window.open("/corona/common/jusoPopupOrg.do","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
			});
		});
	
	//자가격리주소 jusoPopupIsoCallBack
	function jusoPopupOrgCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo){
		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
		$('#ORG_ADDRESS').val(roadAddrPart1);
		$('#ORG_ADDRESS_DTL').val(addrDetail);
		
		$('#SUSPICION_DAE').val(emdNm);
	}
	
	//정보 입력
	function fn_update(){
		var comSubmit = new ComSubmit("form1");
		
		var tmp1 = "${map.ID}";
		comSubmit.addParam("ID", "${map.ID}");
		
		comSubmit.addParam("WRITE_DATE", $("#WRITE_DATE").val().replace(/-/gi, ""));
		comSubmit.addParam("BIRTH", $("#BIRTH").val().replace(/-/gi, ""));
		comSubmit.addParam("CLINIC_VISIT_DATE", $("#CLINIC_VISIT_DATE").val().replace(/-/gi, ""));
		
		//입력제한
		var suspicion_gubun = $("input[name='SUSPICION_GUBUN']:checked").val();
		var suspicion_dae = $("#SUSPICION_DAE").val();
		var suspicion_so = $("#SUSPICION_SO").val();
		
		var suspicion_dae_last = suspicion_dae.substr(suspicion_dae.length - 1)
		var suspicion_so_last = suspicion_so.substr(suspicion_so.length - 1)
		
		//강남구일 경우 대구분은 동으로 입력되어야함	
		if(suspicion_gubun == '강남구' && suspicion_dae_last !='동'){
			if(!confirm('구분이 \'강남구\'일경우 대구분은 \'동\'이어야 합니다. 계속 진행 하시겠습니까?')){
				return false;
			}
		}
		
		//타지역일경우
		if(suspicion_gubun == '타지역' ){
			
			if(suspicion_dae.indexOf('서울') != -1
				||suspicion_dae.indexOf('부산') != -1
				||suspicion_dae.indexOf('대구') != -1
				||suspicion_dae.indexOf('인천') != -1
				||suspicion_dae.indexOf('광주') != -1
				||suspicion_dae.indexOf('대전') != -1
				||suspicion_dae.indexOf('울산') != -1
			){
					
				if(suspicion_dae.indexOf('서울') != -1 && suspicion_dae != '서울특별시') {
					alert("서울은 서울특별시로 입력해야 합니다.");
					return false;
				}
				
				if(suspicion_dae.indexOf('부산') != -1 && suspicion_dae != '부산광역시') {
					alert("부산은 부산광역시로 입력해야 합니다.");
					return false;
				}
				
				if(suspicion_dae.indexOf('대구') != -1 && suspicion_dae != '대구광역시') {
					alert("대구는 대구광역시로 입력해야 합니다.");
					return false;
				}
				
				if(suspicion_dae.indexOf('광주') != -1 && suspicion_dae != '광주광역시') {
					alert("광주는 광주광역시로 입력해야 합니다.");
					return false;
				}
				
				if(suspicion_dae.indexOf('대전') != -1 && suspicion_dae != '대전광역시') {
					alert("대전은 대전광역시로 입력해야 합니다.");
					return false;
				}
				
				if(suspicion_dae.indexOf('울산') != -1 && suspicion_dae != '울산광역시') {
					alert("울산은 울산광역시로 입력해야 합니다.");
					return false;
				}
				
				
				if(suspicion_dae.indexOf('부산') != -1 && suspicion_dae != '부산광역시') {
					alert("부산은 부산광역시로 입력해야 합니다.");
					return false;
				}
				
				
				if(suspicion_so_last != '구') {
					if(!confirm('대분류가 \'시 \'일경우 소구분은 \'구\'여야 합니다. 계속 진행 하시겠습니까?')){
						return false;
					}
				}
			}else{
				if(suspicion_dae_last !='도'){
					if(!confirm('구분이 \'타지역\'일경우 대구분은 \'도\'이어야 합니다. 계속 진행 하시겠습니까?')){
						return false;
					}
				}
				
				if(suspicion_so_last !='시'){
					if(!confirm('구분이 \'타지역\'일경우 소구분은 \'시\'이어야 합니다. 계속 진행 하시겠습니까?')){
						return false;
					}
				}
				
			}
		}
		////////////////////
		
		if($('#WRITE_DATE').val() == "")
		{
			alert('입력일자를 입력하세요!!');
			$('#WRITE_DATE').focus();
			return false;
		}
		
		if($('#CELL_NUM').val() == "")
		{
			alert('핸드폰번호를 입력하세요!!');
			$('#CELL_NUM').focus();
			return false;
		}
		
		if($('#NAME').val() == "")
		{
			alert('이름을 입력하세요!!');
			$('#NAME').focus();
			return false;
		}
		
		if($('#SARAE_GUBUN').val() == "")
		{
			alert('사례분류를 선택하세요!!');
			$('#SARAE_GUBUN').focus();
			return false;
		}
		
		if(confirm('데이터를 수정하시겠습니까?'))
		{					
/* 			comSubmit.setUrl("<c:url value='/write/updateClinic.do' />");									
			comSubmit.submit();
			alert('수정 되었습니다.!!'); */
			
			
			$.ajax({
		        type: "post",
		        url : "/corona/write/updateClinic.do",
		        data: $('#form1').serialize(),
		        contentType : "application/x-www-form-urlencoded; charset=utf-8",

		        success:function (data) {//추천성공
		        	alert("수정 되었습니다.");
	            },

		        error: function (data) {//추천실패
		        	alert("실패 하였습니다.관리자에게 문의 바랍니다.");
	            }
	     	});
			
			
			
		}
	}
			
			
	function trim(str) {
	    return str.replace(/(^\s*)|(\s*$)/gi, '');
	}	

	
	function fn_setting_val(){
		var tmp1 = "${map.SEX}";

		//성별체크
		if(tmp1 == 'M')
			$('#SEX_M').attr("checked", true);
		
		if(tmp1 == 'W')
			$('#SEX_W').attr("checked", true);
		
		$('select[name="INSPECTION_CASE"]').val(['${map.INSPECTION_CASE}']);
		$('select[name="SARAE_GUBUN"]').val(['${map.SARAE_GUBUN}']);
		$('input[name="SUSPICION_GUBUN"]').val(['${map.SUSPICION_GUBUN}']);
	}
	
</script>


<style>
  table {
    border: 1px solid #444444;
     border-collapse: collapse;
  }
  th, td {
    border: 1px solid #444444;
    padding : 10px;
  }
</style>
</head>

<body>

<div style="background-color:#170B3B;color:white; margin-bottom:10px; width: 100%;">
	<a href="#this" class="btn" id="logout">
		<img src="/corona/images/popup_title.gif" align="absmiddle">
	</a>선별진료소 검사 수정
	
	<div style="padding-right:10px;padding-top:7px;float:right;">
		<a href="javascript:history.back()" class="btn btn-success" id="statistics"><i class="fa fa-reply"></i> 이전화면</a>
	</div>
</div>


<form id="form1" name="form1" enctype="multipart/form-data">
	<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
	
		<!--리스트 시작 -->
		<table style="margin:20px;">
			<tr>
				<td><font color='RED'>* 입력일자</font></td>
				<td><input type="text" class="form-control" id="WRITE_DATE" value="${map.WRITE_DATE}"></td>
			</tr>
			<tr>
				<td><font color='RED'>* 방문일자</font></td>
				<td><input type="text" class="form-control" id="CLINIC_VISIT_DATE" value="${map.CLINIC_VISIT_DATE}"></td>
			</tr>
			<tr>
				<td><font color='RED'>* 핸드폰번호</font></td>
				<td><input type="text" class="form-control" id="CELL_NUM" name="CELL_NUM" value="${map.CELL_NUM}"></td>
			</tr>
			<tr>
				<td><font color='RED'>* 이름</font></td>
				<td><input type="text" class="form-control" id="NAME" name="NAME" value="${map.NAME}"></td>
			</tr>
			<tr>
				<td>성별</td>
				<td>
					<input type="radio" id="SEX_M" name="SEX" value="M"> 남자 &nbsp; 
					<input type="radio" id="SEX_W" name="SEX" value="W"> 여자 &nbsp; 
				</td>
			</tr>
			<tr>
				<td>생년월일</td>
				<td><input type="text" class="form-control" id="BIRTH" value="${map.BIRTH}"></td>
			</tr>
			<tr>
				<td>직업</td>
				<td><input type="text" class="form-control" id="JOB" name="JOB" value="${map.JOB}"></td>
			</tr>
			<tr>
				<td>검사케이스</td>
				<td>
					<input type="text" class="form-control" id="INSPECTION_CASE" name="INSPECTION_CASE" value="${map.INSPECTION_CASE}">
					<font color='#DF0174'>ex) 자가격리해제자,학생,교직원,운수업종사자,확진자접촉자 등</font>
				</td>
			</tr>
			<tr>
				<td><font color='RED'>* 사례분류</font></td>
				<td>
					<select name="SARAE_GUBUN" id="SARAE_GUBUN">
						<option value="">선택</option>
						<option value="의사환자">의사환자</option>
						<option value="조사대상유증상자">조사대상유증상자</option>
						<option value="사례미해당">사례미해당 </option>
					</select>
				</td>
			</tr>
			<tr>
				<td>경로구분</td>
				<td>
					<input type="radio" id="SUSPICION_GUBUN_GANGNAM" name="SUSPICION_GUBUN" value="강남구"> 강남구 &nbsp; 
					<input type="radio" id="SUSPICION_GUBUN_TASIGU" name="SUSPICION_GUBUN" value="타지역"> 타지역 &nbsp;
					<input type="radio" id="SUSPICION_GUBUN_OVERSEA" name="SUSPICION_GUBUN" value="해외"> 해외 &nbsp;  
				</td>
			</tr>
			<tr>
				<td>경로대구분</td>
				<td>
					<input type="text" class="form-control" id="SUSPICION_DAE" name="SUSPICION_DAE" value="${map.SUSPICION_DAE}">
					<font color='#DF0174'>ex) 강남구 - 동을 입력</font><br>
					<font color='#DF0174'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;타지역 - 광역시나 도를 입력</font><br>
					<font color='#DF0174'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;해외 - 유럽,미국,중국...</font>
				</td>
			</tr>
			<tr>
				<td>경로소구분</td>
				<td>
					<input type="text" class="form-control" id="SUSPICION_SO" name="SUSPICION_SO" value="${map.SUSPICION_SO}">
					<font color='#DF0174'>ex) 강남구 - 비전교회,할리스커피 강남점</font><br>
					<font color='#DF0174'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;타지역 - 구 나 시를 입력</font><br>
					<font color='#DF0174'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;해외 - 프랑스, 플로리다,우한...</font>
				</td>
			</tr>
			
			<tr>
				<td>검사자상세주소</td>
	            <td width="607">
					<input type="text" id="ORG_ADDRESS" name="ORG_ADDRESS" class="form-control" style="width:400;"  value="${map.ORG_ADDRESS}" >       
					<input type="text" id="ORG_ADDRESS_DTL" name="ORG_ADDRESS_DTL" class="form-control" style="width:400;"  value="${map.ORG_ADDRESS_DTL}">
		            <a href="#this" class="btn" id="juso_iso">주소찾기</a>
		            <br><font color='#DF0174'>* 강남구 주민의 경우 입력 바랍니다.</font>
	            </td>	
				
			</tr>
			
			<tr>
				<td>비고</td>
				<td width="607">
					<input type="text" style="width:400;" class="form-control" id="REMARK" name="REMARK" value="${map.REMARK}">
				</td>
			</tr>
			
			
			<tr align="right" >
				<td colspan="2">
					<a href="#this" class="btn btn-warning" id="update">수정</a>
				</td>
			</tr>
		</table>
	</form>

</body>
</html>