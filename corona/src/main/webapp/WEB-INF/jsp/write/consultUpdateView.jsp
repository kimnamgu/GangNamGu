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
			
			//모니터링 시작일
			$("#COME_DATE").datepicker({
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			//모니터링 종료일
			$("#FREE_DATE").datepicker({
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
		});
	
	//정보 입력
	function fn_update(){
		var comSubmit = new ComSubmit("form1");
		
		var tmp1 = "${map.ID}";
		comSubmit.addParam("ID", "${map.ID}");
		comSubmit.addParam("WRITE_DATE", $("#WRITE_DATE").val().replace(/-/gi, ""));
		
		if($('#WRITE_DATE').val() == "")
		{
			alert('입력일자를 입력하세요!!');
			$('#WRITE_DATE').focus();
			return false;
		}
		
		if($('#CONSULT_GUBUN').val() == "")
		{
			alert('상담구분을 입력하세요!!');
			$('#CONSULT_GUBUN').focus();
			return false;
		}
		
		if($('#MINWON_CONTENT').val() == "")
		{
			alert('민원내용을 입력하세요!!');
			$('#MINWON_CONTENT').focus();
			return false;
		}
		
		if(confirm('데이터를 수정하시겠습니까?'))
		{					
			comSubmit.setUrl("<c:url value='/write/updateConsult.do' />");									
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
	
	//자가격리주소 jusoPopupIsoCallBack
	function jusoPopupIsoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo){
		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
		$('#SELF_ISO_AREA_ADDRESS').val(roadAddrPart1);
		$('#SELF_ISO_AREA_ADDRESS_DTL').val(addrDetail);
	}
	
	function fn_setting_val(){
		var tmp1 = "${map.SEX}";

		//성별체크
		if(tmp1 == 'M')
			$('#SEX_M').attr("checked", true);
		
		if(tmp1 == 'W')
			$('#SEX_W').attr("checked", true);
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
	</a>민원상담 내역수정
	
	<div style="padding-right:10px;padding-top:7px;float:right;">
		<a href="/corona/manage/consultManage.do" class="btn btn-success" id="statistics"><i class="fa fa-reply"></i> 상담민원 관리화면</a>
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
				<td>시간</td>
				<td><input type="text" class="form-control" id="CONSULT_TIME" name="CONSULT_TIME" value="${map.CONSULT_TIME}"></td>
			</tr>
			<tr>
				<td>성명</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME" value="${map.MINWON_NAME}"></td>
			</tr>
			
			<tr>
				<td>성별</td>
				<td>
					<input type="radio" id="SEX_M" name="SEX" value="M"> 남자 &nbsp; 
					<input type="radio" id="SEX_W" name="SEX" value="W"> 여자 &nbsp; 
				</td>
			</tr>
			<tr>
				<td>연락처</td>
				<td><input type="text" class="form-control" id="MINWON_PHONE" name="MINWON_PHONE" value="${map.MINWON_PHONE}"></td>
			</tr>
			<tr>
				<td><font color='RED'>* 상담구분</font></td>
				<td>
					<select name="CONSULT_GUBUN">
					    <option value="">선택</option>
						<option value="1. 검사결과 문의">1. 검사결과 문의</option>
						<option value="2. 검사관련 문의">2. 검사관련 문의</option>
						<option value="3. 자가격리(자)관련 문의">3. 자가격리(자)관련 문의</option>
						<option value="4. 선별진료소(시간)관련 문의">4. 선별진료소(시간)관련 문의</option>
						<option value="5. 확진자 관련 문의">5. 확진자 관련 문의</option>
						<option value="6. 역학조사 관련문의">6. 역학조사 관련문의</option>
						<option value="7. 임시생활시설관련 문의">7. 임시생활시설관련 문의</option>
						<option value="8. 사회적 거리두기 관련 문의">8. 사회적 거리두기 관련 문의</option>
						<option value="9. 방역관련 문의">9. 방역관련 문의</option>
						<option value="10. 기타 문의">10. 기타 문의</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><font color='RED'>* 상담내용</font></td>
				<td><input type="text" class="form-control" id="MINWON_CONTENT" name="MINWON_CONTENT" value="${map.MINWON_CONTENT}"></td>
			</tr>
			<tr>
				<td>처리결과</td>
				<td><input type="text" class="form-control" id="PROCESS_RESULT" name="PROCESS_RESULT" value="${map.PROCESS_RESULT}"></td>
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