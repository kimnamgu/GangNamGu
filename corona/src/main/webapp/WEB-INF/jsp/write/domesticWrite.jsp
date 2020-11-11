<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>


<script type="text/javascript">
	var user_right = "${sessionScope.userinfo.userright}";

	$(document).ready(
		function() {
		
			//입력일
			$("#WRITE_DATE").datepicker({
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
			
			//모니터링 시작일
			$("#MONITOR_START").datepicker({
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			//모니터링 종료일
			$("#MONITOR_END").datepicker({
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			//검색초기화
			fn_initial_setting();
			
			//기본주소찾기	
			$("#juso_org").on("click", function(e){ 				
				e.preventDefault();
				var pop = window.open("/corona/common/jusoPopupOrg.do","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
			});
			
			//자가격리주소찾기	
			$("#juso_iso").on("click", function(e){ 				
				e.preventDefault();
				var pop = window.open("/corona/common/jusoPopupIso.do","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
			});
			
			//접촉주소찾기	
			$("#juso_contact").on("click", function(e){ 				
				e.preventDefault();
				var pop = window.open("/corona/common/jusoPopupContact.do","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
			});
			
			
			
			//정보입력
			$("#insert").on("click", function(e){ //등록하기 버튼							
				e.preventDefault();				
				fn_write();				
			});
			
			
			
			
		});
	

	
	//검색초기화
	function fn_initial_setting(){
	    //입력일자 현재
	    $('#WRITE_DATE').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
	    $('#MONITOR_START').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
	    //$('#MONITOR_END').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)

	    $('input[name="SEX"]').val(['M']);
	    $('input[name="BALGUB_YN"]').val(['Y']);
	    $('input[name="CONTACT_TYPE"]').val(['밀접']);
	    $('input[name="CONTACT_GUBUN"]').val(['가족']);
	    $('input[name="PATIENT_YN"]').val(['접촉자']);
	}
	
	
	//정보 입력
	function fn_write(){
		var comSubmit = new ComSubmit("form1");
		
		comSubmit.addParam("WRITE_DATE", $("#WRITE_DATE").val().replace(/-/gi, ""));
		comSubmit.addParam("BIRTH", $("#BIRTH").val().replace(/-/gi, ""));
		comSubmit.addParam("MONITOR_START", $("#MONITOR_START").val().replace(/-/gi, ""));
		comSubmit.addParam("MONITOR_END", $("#MONITOR_END").val().replace(/-/gi, ""));
		
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
		
		if($('#SELF_ISO_AREA_DONG').val() == "")
		{
			alert('자가격리지동을 입력하세요!!');
			$('#SELF_ISO_AREA_DONG').focus();
			return false;
		}
		
		if($('#SELF_ISO_AREA_ADDRESS').val() == "")
		{
			alert('자가격리주소(상세 주소를  검색하세요!!\n\n오른쪽 끝 주소찾기를 클릭하세요.');
			$('#SELF_ISO_AREA_ADDRESS').focus();
			return false;
		}
		
		if($('#MONITOR_START').val() == "")
		{
			alert('모니터링 시작일을 입력하세요!!');
			$('#MONITOR_START').focus();
			return false;
		}
		
		if($('#MONITOR_END').val() == "")
		{
			alert('모니터링 종료일을 입력하세요!!');
			$('#MONITOR_END').focus();
			return false;
		}
		
		if(confirm('데이터를 등록하시겠습니까?'))
		{					
			comSubmit.setUrl("<c:url value='/write/insertDomesticWrite.do' />");									
			comSubmit.submit();
			alert('등록완료 되었습니다.!!');
		}
	}
			
			
	function trim(str) {
	    return str.replace(/(^\s*)|(\s*$)/gi, '');
	}	

	
	//자가격리주소 jusoPopupIsoCallBack
	function jusoPopupIsoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo){
		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
		$('#SELF_ISO_AREA_ADDRESS').val(roadAddrPart1);
		$('#SELF_ISO_AREA_ADDRESS_DTL').val(addrDetail);
		
		$('#SELF_ISO_AREA_DONG').val(emdNm);
	}
	
	//접촉장소 jusoPopupContactCallBack
	function jusoPopupContactCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo){
		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
		$('#CONTACT_ADDRESS').val(roadAddrPart1);
		$('#CONTACT_ADDRESS_DTL').val(addrDetail);
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
	</a>국내자가격리자입력
	
	<div style="padding-right:10px;padding-top:7px;float:right;">
		<a href="/corona/manage/domesticContactManage.do" class="btn btn-success" id="statistics"><i class="fa fa-reply"></i> 국내자가격리자 관리화면</a>
	</div>
</div>


<form id="form1" name="form1" enctype="multipart/form-data">
	<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
	
		<!--리스트 시작 -->
		<table style="margin:20px;">
			<tr>
				<td><font color='RED'>* 입력일자</font></td>
				<td><input type="text" class="form-control" id="WRITE_DATE"></td>
			</tr>
			<tr>
				<td><font color='RED'>* 핸드폰번호</font></td>
				<td><input type="text" class="form-control" id="CELL_NUM" name="CELL_NUM"></td>
			</tr>
			<tr>
				<td><font color='RED'>* 이름</font></td>
				<td><input type="text" class="form-control" id="NAME" name="NAME"></td>
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
				<td><input type="text" class="form-control" id="BIRTH"></td>
			</tr>
			<tr>
				<td>직업</td>
				<td><input type="text" class="form-control" id="JOB" name="JOB"></td>
			</tr>
			<tr>
				<td><font color='RED'>* 자가격리지동</font></td>
				<td>
					<input type="text" class="form-control" id="SELF_ISO_AREA_DONG" name="SELF_ISO_AREA_DONG">
					<font color='#DF0174'>* 주소찾기로 주소 입력시 자동 입력됩니다.</font>
				</td>
			</tr>
			<tr>
				<td><font color='RED'>* 자가격리주소</font></td>
	            <td width="607">
					<input type="text" id="SELF_ISO_AREA_ADDRESS" name="SELF_ISO_AREA_ADDRESS" class="form-control" style="width:400;" >       
					<input type="text" id="SELF_ISO_AREA_ADDRESS_DTL" name="SELF_ISO_AREA_ADDRESS_DTL" class="form-control" style="width:400;" >
		            <a href="#this" class="btn" id="juso_iso">주소찾기</a>
	            </td>	
				
			</tr>
			<tr>
				<td>환자번호</td>
				<td>
					<input type="text" class="form-control" id="PATIENT_NUM" name="PATIENT_NUM">
				</td>
			</tr>
			<tr>
				<td>환자명</td>
				<td><input type="text" class="form-control" id="PATIENT_NAME" name="PATIENT_NAME"></td>
			</tr>
			<tr>
				<td>접촉자번호</td>
				<td>
					<input type="text" class="form-control" id="CONTACT_NUM" name="CONTACT_NUM">
				</td>
			</tr>
			<tr>
				<td>접촉자명</td>
				<td>
					<input type="text" class="form-control" id="CONTACT_NAME" name="CONTACT_NAME">
				</td>
			</tr>
			<tr>
				<td>최종접촉일</td>
				<td><input type="text" class="form-control" id="FINAL_CONTACT"  name="FINAL_CONTACT"></td>
			</tr>
			<tr>
				<td><font color='RED'>* 모니터링 시작일</font></td>
				<td><input type="text" class="form-control" id="MONITOR_START"></td>
			</tr>
			<tr>
				<td><font color='RED'>* 모니터링 종료일</font></td>
				<td><input type="text" class="form-control" id="MONITOR_END"></td>
			</tr>
			
			<tr>
				<td>접촉장소</td>
	            <td width="607">
					<input type="text" id="CONTACT_ADDRESS" name="CONTACT_ADDRESS" class="form-control" style="width:400;">       
	            </td>	
			</tr>
			<tr>
				<td>접촉유형</td>
				<td>
					<input type="radio" id="CONTACT_TYPE_MILJUB" name="CONTACT_TYPE" value="밀접"> 밀접 &nbsp; 
					<input type="radio" id="CONTACT_TYPE_ILSANG" name="CONTACT_TYPE" value="일상"> 일상&nbsp;
					<input type="radio" id="CONTACT_TYPE_ETC" name="CONTACT_TYPE" value="기타"> 기타&nbsp;  
				</td>
			</tr>
			<tr>
				<td>접촉자구분</td>
				<td>
					<input type="radio" id="CONTACT_GUBUN_FAMILY" name="CONTACT_GUBUN" value="가족"> 가족 &nbsp; 
					<input type="radio" id="CONTACT_GUBUN_COLLEGUE" name="CONTACT_GUBUN" value="동료"> 동료&nbsp;
					<input type="radio" id="CONTACT_GUBUN_ETC" name="CONTACT_GUBUN" value="기타"> 기타&nbsp;  
				</td>
			</tr>
			<tr>
				<td>환자여부</td>
				<td>
					<input type="radio" id="PATIENT_Y" name="PATIENT_YN" value="접촉자"> 접촉자 &nbsp; 
					<input type="radio" id="PATIENT_N" name="PATIENT_YN" value="비접촉자"> 비접촉자 &nbsp; 
				</td>
			</tr>
			<tr>
				<td>통지서 발급여부</td>
				<td>
					<input type="radio" id="BALGUB_Y" name="BALGUB_YN" value="Y"> 발급 &nbsp; 
					<input type="radio" id="BALGUB_N" name="BALGUB_YN" value="N"> 미발급 &nbsp; 
				</td>
			</tr>
			<tr>
				<td>소속</td>
				<td>
					<input type="text" class="form-control" id="DAMDANG_DEPART" name="DAMDANG_DEPART">
					<font color='#DF0174'>* 추후 담당 공무원이 배정되면 입력</font>
				</td>
			</tr>
			<tr>
				<td>직위</td>
				<td>
					<input type="text" class="form-control" id="DAMDANG_CLASS" name="DAMDANG_CLASS">
					<font color='#DF0174'>* 추후 담당 공무원이 배정되면 입력</font>
				</td>
			</tr>
			<tr>
				<td>직급</td>
				<td>
					<input type="text" class="form-control" id="DAMDANG_POSITION" name="DAMDANG_POSITION">
					<font color='#DF0174'>* 추후 담당 공무원이 배정되면 입력</font>
				</td>
			</tr>
			<tr>
				<td>전담공무원명</td>
				<td>
					<input type="text" class="form-control" id="DAMDANG_NAME" name="DAMDANG_NAME">
					<font color='#DF0174'>* 추후 담당 공무원이 배정되면 입력</font>
				</td>
			</tr>
			<tr>
				<td>내선번호</td>
				<td>
					<input type="text" class="form-control" id="DAMDANG_CALL" name="DAMDANG_CALL">
					<font color='#DF0174'>* 추후 담당 공무원이 배정되면 입력</font>
				</td>
			</tr>
			
			
			<tr align="right" >
				<td colspan="2">
					<a href="#this" class="btn btn-info" id="insert">등록</a>
				</td>
			</tr>
		</table>
	</form>

</body>
</html>