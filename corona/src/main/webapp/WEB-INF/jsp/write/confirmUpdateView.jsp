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
			
			//생년월일
			$("#BIRTH").datepicker({
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			//확진 판정일
			$("#CONFIRM_DATE").datepicker({
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			//퇴원일
			$("#DISCHARGE_DATE").datepicker({
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			
			//기본주소찾기	
			$("#juso_org").on("click", function(e){ 				
				e.preventDefault();
				var pop = window.open("/corona/common/jusoPopupOrg.do","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
			});
			
			//추정주소찾기	
			$("#juso_aspect").on("click", function(e){ 				
				e.preventDefault();
				var pop = window.open("/corona/common/jusoPopupAspect.do","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
			});
			
			
			
			//정보수정
			$("#update").on("click", function(e){							
				e.preventDefault();				
				fn_update();				
			});
		});
	
	//정보 입력
	function fn_update(){
		
		var comSubmit = new ComSubmit("form1");
		
		var tmp1 = "${map.ID}";
		comSubmit.addParam("ID", "${map.ID}");
		
		comSubmit.addParam("WRITE_DATE", $("#WRITE_DATE").val().replace(/-/gi, ""));
		comSubmit.addParam("BIRTH", $("#BIRTH").val().replace(/-/gi, ""));
		comSubmit.addParam("CONFIRM_DATE", $("#CONFIRM_DATE").val().replace(/-/gi, ""));
		comSubmit.addParam("DISCHARGE_DATE", $("#DISCHARGE_DATE").val().replace(/-/gi, ""));
		
		if($('input:checkbox[id="DEATH_CHK"]').is(":checked")){
			comSubmit.addParam("DEATH", "Y");
		}else{
			comSubmit.addParam("DEATH", "N");
		}
		
		if($('input:checkbox[id="TA_CONFIRM_CHK"]').is(":checked")){
			comSubmit.addParam("TA_CONFIRM_YN", "Y");
		}else{
			comSubmit.addParam("TA_CONFIRM_YN", "N");
		}
		
		if($('input:checkbox[id="ICHUP_CHK"]').is(":checked")){
			comSubmit.addParam("ICHUP_YN", "Y");
		}else{
			comSubmit.addParam("ICHUP_YN", "N");
		}
		
		var infect_gubun_checked = $('input[name="INFECT_GUBUN"]:checked').val(); 

		
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
		
		if($('#PATIENT_NUM').val() == "")
		{
			alert('환자번호를 입력하세요!!');
			$('#PATIENT_NUM').focus();
			return false;
		}
		
		if($('#PATIENT_NAME').val() == "")
		{
			alert('환자명을 입력하세요!!');
			$('#PATIENT_NAME').focus();
			return false;
		}
		
		if($('#INFECT_GUBUN').val() == "")
		{
			alert('감염경로구분를 입력하세요!!');
			$('#INFECT_GUBUN').focus();
			return false;
		}
		
/* 		if(infect_gubun_checked == "강남구" && $('#INFECT_DONG').val() == "")
		{
			alert('감염경로동을 입력하세요!!');
			$('#INFECT_DONG').focus();
			return false;
		}
		
		if($('#INFECT_DAE').val() == "")
		{
			alert('감염경로대구분을 입력하세요!!');
			$('#INFECT_DAE').focus();
			return false;
		}
		if($('#INFECT_SO').val() == "")
		{
			alert('감염경로소구분을 입력하세요!!');
			$('#INFECT_SO').focus();
			return false;
		}
		 */
		if(infect_gubun_checked != "해외" 
			&& infect_gubun_checked != "불분명"
			&& $('#INFECT_ASFECT_AREA').val() == "")
		{
			alert('추정감염경로(상세 주소를  검색하세요!!\n\n오른쪽 끝 주소찾기를 클릭하세요.');
			$('#INFECT_ASFECT_AREA').focus();
			return false;
		}
		
		if(typeof $('input[name="INFECT_JIPDAN_GUBUN"]:checked').val() == "undefined")
		{
			alert('집단감염구분을 입력하세요!!');
			$('#INFECT_JIPDAN_GUBUN').focus();
			return false;
		}
		
/* 		if($('#CONFIRM_DATE').val() == "")
		{
			alert('확진 판정일을 입력하세요!!');
			$('#CONFIRM_DATE').focus();
			return false;
		}
		
		if($('#HOSPITAL').val() == "")
		{
			alert('입원병원을 입력하세요!!');
			$('#HOSPITAL').focus();
			return false;
		} */

		if(confirm('데이터를 수정하시겠습니까?'))
		{					
			comSubmit.setUrl("<c:url value='/write/updateConfirm.do' />");									
			comSubmit.submit();
			alert('수정 되었습니다.!!');
		}
	}
			
			
	function trim(str) {
	    return str.replace(/(^\s*)|(\s*$)/gi, '');
	}	

	
	//기존주소 jusoPopupOrgCallBack
	function jusoPopupOrgCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo){
		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
		$('#ORG_ADDRESS').val(roadAddrPart1);
		$('#ORG_ADDRESS_DTL').val(addrDetail);
	}
	
	//추정주소 jusoPopupAsfectCallBack
	function jusoPopupAspectCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo){
		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
		$('#INFECT_ASFECT_AREA').val(roadAddrPart1);
		$('#INFECT_ASFECT_AREA_DTL').val(addrDetail);
	}
	
	function fn_setting_val(){
		$('input[name="SEX"]').val(["${map.SEX}"]);
		$('input[name="INFECT_JIPDAN_GUBUN"]').val(["${map.INFECT_JIPDAN_GUBUN}"]);
		$('input[name="INFECT_GUBUN"]').val(["${map.INFECT_GUBUN}"]);
		
		if("${map.DEATH}" == "Y"){
			$('input:checkbox[id="DEATH_CHK"]').attr("checked", true);
		}
		
		if("${map.TA_CONFIRM_YN}" == "Y"){
			$('input:checkbox[id="TA_CONFIRM_CHK"]').attr("checked", true);
		}
		
		if("${map.ICHUP_YN}" == "Y"){
			$('input:checkbox[id="ICHUP_CHK"]').attr("checked", true);
		}
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
	</a>확진자수정
	
	<div style="padding-right:10px;padding-top:7px;float:right;">
		<a href="/corona/manage/confirmManage.do" class="btn btn-success" id="statistics"><i class="fa fa-reply"></i> 확진자 관리화면</a>
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
				<td><font color='RED'>* 핸드폰번호</font></td>
				<td><input type="text" class="form-control" id="CELL_NUM" name="CELL_NUM" value="${map.CELL_NUM}"></td>
			</tr>
			<tr>
				<td><font color='RED'>* 환자번호</font></td>
				<td><input type="text" class="form-control" id="PATIENT_NUM" name="PATIENT_NUM" value="${map.PATIENT_NUM}"></td>
			</tr>
			<tr>
				<td><font color='RED'>* 환자명</font></td>
				<td><input type="text" class="form-control" id="PATIENT_NAME" name="PATIENT_NAME" value="${map.PATIENT_NAME}"></td>
			</tr>
			<tr>
				<td>접촉자번호</td>
				<td><input type="text" class="form-control" id="CONTACT_NUM" name="CONTACT_NUM" value="${map.CONTACT_NUM}"></td>
			</tr>
			<tr>
				<td>접촉자명</td>
				<td><input type="text" class="form-control" id="CONTACT_NAME" name="CONTACT_NAME" value="${map.CONTACT_NAME}"></td>
			</tr>
			<tr>
				<td>확진차수</td>
				<td><input type="text" class="form-control" id="CONFIRM_GRADE" name="CONFIRM_GRADE" value="${map.CONFIRM_GRADE}"></td>
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
				<td><font color='RED'>* 주소대분류</font></td>
				<td>
					<input type="text" class="form-control" id="ORG_GUBUN" name="ORG_GUBUN" value="${map.ORG_GUBUN}">
					<font color='#DF0174'>* 강남구는 '강남구' 타시구는 '시'를 적어주시기 바랍니다.</font>
				</td>
			</tr>
			<tr>
				<td><font color='RED'>* 주소소분류</font></td>
				<td>
					<input type="text" class="form-control" id="ORG_DONG" name="ORG_DONG" value="${map.ORG_DONG}">
					<font color='#DF0174'>* 강남구는 '동' 타시구는 '구'를 적어주시기 바랍니다.</font>
				</td>
			</tr>
			<tr>
				<td>기존주소</td>
	            <td width="607">
					<input type="text" id="ORG_ADDRESS" name="ORG_ADDRESS" class="form-control" style="width:400;"  value="${map.ORG_ADDRESS}">
					<input type="text" id="ORG_ADDRESS_DTL" name="ORG_ADDRESS_DTL" class="form-control" style="width:400;"  value="${map.ORG_ADDRESS_DTL}">       
		            <a href="#this" class="btn" id="juso_org">주소찾기</a>
	            </td>	
				
			</tr>
			<tr>
				<td><font color='RED'>* 감염경로구분</font></td>
				<td>
					<input type="radio" id="INFECT_GUBUN_GANGNAM" name="INFECT_GUBUN" value="강남구"> 강남구 &nbsp; 
					<input type="radio" id="INFECT_GUBUN_TASIGU" name="INFECT_GUBUN" value="타지역"> 타지역 &nbsp;
					<input type="radio" id="INFECT_GUBUN_OVERSEA" name="INFECT_GUBUN" value="해외"> 해외 &nbsp;  
					<input type="radio" id="INFECT_GUBUN_TASIGU_JIPDAN" name="INFECT_GUBUN" value="타지역집단감염"> 타지역집단감염 &nbsp;
					<input type="radio" id="INFECT_GUBUN_NO" name="INFECT_GUBUN" value="불분명"> 불분명 &nbsp;  
				</td>
				
			</tr>
			<tr>
				<td><font color='RED'>* 감염경로동</font></td>
				<td><input type="text" class="form-control" id="INFECT_DONG" name="INFECT_DONG" value="${map.INFECT_DONG}"><font color='#DF0174'>* 감염경로구분이 강남구면 '동'을 필수로 적어주시기 바랍니다.</font></td>
			</tr>
			<tr>
				<td><font color='RED'>* 감염경로대구분</font></td>
				<td>
					<input type="text" class="form-control" id="INFECT_DAE" name="INFECT_DAE" value="${map.INFECT_DAE}">
					<font color='#DF0174'>ex) 해외 - 유럽,미국,중국...</font><br>
					<font color='#DF0174'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;국내 - 신천지,대구,이태원...</font>	
				</td>
			</tr>
			<tr>
				<td><font color='RED'>* 감염경로소구분</font></td>
				<td>
					<input type="text" class="form-control" id="INFECT_SO" name="INFECT_SO" value="${map.INFECT_SO}">
					<font color='#DF0174'>ex) 해외 - 프랑스, 플로리다,우한...</font><br>
					<font color='#DF0174'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;국내 - 비전교회,대구동,이태원클럽명...</font>	
				</td>
			</tr>
			<tr>
				<td><font color='RED'>* 추정감염경로(상세)</font></td>
	            <td width="607">
					<input type="text" id="INFECT_ASFECT_AREA" name="INFECT_ASFECT_AREA" class="form-control" style="width:400;"  value="${map.INFECT_ASFECT_AREA}">
					<input type="text" id="INFECT_ASFECT_AREA_DTL" name="INFECT_ASFECT_AREA_DTL" class="form-control" style="width:400;"  value="${map.INFECT_ASFECT_AREA_DTL}">       
		            <a href="#this" class="btn" id="juso_aspect">주소찾기</a>
	            </td>	
			</tr>
			<tr>
				<td><font color='RED'>* 집단감염구분</font></td>
				<td>
					<input type="radio" id="INFECT_ONE" name="INFECT_JIPDAN_GUBUN" value="개별"> 개별 &nbsp; 
					<input type="radio" id="INFECT_JIPDAN" name="INFECT_JIPDAN_GUBUN" value="집단"> 집단 &nbsp; 
				</td>
			</tr>
			<tr>
				<td>감염시설</td>
				<td>
					<input type="text" class="form-control" id="INFECT_JIPDAN_GUBUN_FACILITY" name="INFECT_JIPDAN_GUBUN_FACILITY" value="${map.INFECT_JIPDAN_GUBUN_FACILITY}">
					<font color='#DF0174'>* 위험시설군으로 지정된 장소의 경우 입력</font>
				</td>
			</tr>
			
			<tr>
				<td><font color='RED'>* 확진 판정일</font></td>
				<td><input type="text" class="form-control" id="CONFIRM_DATE" value="${map.CONFIRM_DATE}"></td>
			</tr>
			<tr>
				<td><font color='RED'>* 입원병원</font></td>
				<td><input type="text" class="form-control" id="HOSPITAL" name="HOSPITAL" value="${map.HOSPITAL}"></td>
			</tr>
			<tr>
				<td>퇴원일자</td>
				<td><input type="text" class="form-control" id="DISCHARGE_DATE" value="${map.DISCHARGE_DATE}"></td>
			</tr>
			<tr>
				<td>비고</td>
				<td><input type="text" class="form-control" id="REMARK" name="REMARK" value="${map.REMARK}"></td>
			</tr>
			<tr>
				<td>사망</td>
				<td>
					<input name="DEATH_CHK" id="DEATH_CHK" type="checkbox" value="1">
				</td>
			</tr>
			<tr>
				<td>타지역 확진 판정</td>
				<td>
					<input name="TA_CONFIRM_CHK" id="TA_CONFIRM_CHK" type="checkbox">
				</td>
			</tr>
			<tr>
				<td>강남구민 이첩 자료</td>
				<td>
					<input name="ICHUP_CHK" id="ICHUP_CHK" type="checkbox">
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