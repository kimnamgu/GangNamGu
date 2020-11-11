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
			
			//검색초기화
			fn_initial_setting();
			
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

	    $('input[name="SEX"]').val(['M']);
	    $('input[name="JUYA_GUBUN"]').val(['주간']);
	}
	
	//정보 입력
	function fn_write(){
		var comSubmit = new ComSubmit("form1");
		
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
		
		
		if(confirm('데이터를 등록하시겠습니까?'))
		{					
			comSubmit.setUrl("<c:url value='/write/insertConsultWrite.do' />");									
			comSubmit.submit();
			alert('등록완료 되었습니다.!!');
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
		<img src="/manpower/images/popup_title.gif" align="absmiddle">
	</a>사업 등록
	
	<div style="padding-right:10px;padding-top:7px;float:right;">
		<a href="/manpower/manage/businessManage.do" class="btn btn-success" id="statistics"><i class="fa fa-reply"></i> 목록보기</a>
	</div>
</div>


<form id="form1" name="form1" enctype="multipart/form-data">
	<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
	
		<!--리스트 시작 -->
		<table style="margin:20px;">
			<tr>
				<td>처리년월</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			<tr>
				<td>사업명</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			<tr>
				<td>인력선택</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			
			개인정보
			
			<tr>
				<td>이름(생년월일)</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			<tr>
				<td>연락처</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			<tr>
				<td>주소</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			<tr>
				<td>부양가족수</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			<tr>
				<td>은행계좌</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			
			근태정보
			
			<tr>
				<td>월차</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			<tr>
				<td>유급휴일</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			
			입금정보
			
			<tr>
				<td>급여총액</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			<tr>
				<td>과세대상</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			
			<tr>
				<td>인건비</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			
			<tr>
				<td>근무일수</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			
			<tr>
				<td>반일근무일수</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			<tr>
				<td>주차일수</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			<tr>
				<td>일당</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			
			<tr>
				<td>월차수당</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			
			<tr>
				<td>교통비 등(비과세)</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			
			<tr>
				<td>기타금액</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			
			<tr>
				<td>기타 금액 내역</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			
			4대보험 정보 기입
			
			<tr>
				<td>공제총액</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			
			
			<tr>
				<td>건강보험</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			<tr>
				<td>장기요양보험</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			
			<tr>
				<td>고용보험</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			<tr>
				<td>보험외공제</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			<tr>
				<td>소득세</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
			</tr>
			<tr>
				<td>주민세</td>
				<td><input type="text" class="form-control" id="MINWON_NAME" name="MINWON_NAME"></td>
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