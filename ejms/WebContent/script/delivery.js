/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기
 * 설명:
 */

/**
 * 반환값 저장 변수
 */
var result;

function viewForm() {	
	//양식종류,시스템문서번호,양식번호,추가매개변수
	var args = viewForm.arguments;
	var childURL = "";
	var parentURL = "";

	if (args[0] == "01")			childURL = "/deliveryFormatLineView.do";
	else if (args[0] == "02")	childURL = "/deliveryFormatFixedView.do";
	else if (args[0] == "03") childURL = "/deliveryFormatBookView.do";
	
	location.href = parentURL + childURL + "?formkind=" + args[0] + "&sysdocno=" + args[1] + "&formseq=" + args[2] + "&" + args[3];
}

// 배부처리시 유효성 체크
function IsAssignCharge(sysdocno) {
	var params = '';
	
	params += 'sysdocno=' + encodeURIComponent(sysdocno);

	result = new Ajax.Request('/isAssignCharge.do', {method: 'post', postBody: params, onComplete: callBack});
}

function callBack(resXML){
	var xmlDoc = resXML.responseXML;
	var sysdocno, gubun, retCode;
	
	var resultNode = xmlDoc.getElementsByTagName('result');

	sysdocno = resultNode[0].childNodes[0].text;
	retCode = resultNode[0].childNodes[1].text;
	
	if(retCode == "OVERTIME") {
		alert("마감시한이 초과된 문서입니다.\n배부하시려면 취합담당자에게 마감시한 연장 요청을 하시기 바랍니다.");
		return;
	} else if(retCode == "NONINPUTUSR") {
		alert("입력담당자가 지정이 되지 않았습니다.\n입력담당자를 지정하십시오.");
		location.href = "/deliveryCollDocInfoView.do" + location.href.substring(location.href.indexOf("?"));
		return;
	} else if(retCode == "NONAPPROVAL") {
//			var cnf = window.confirm("승인자가 지정되지 않았습니다.\n담당자 전결로 처리하시겠습니까?");
			
//			if(cnf) {
				location.href = "/deliveryProcess.do?sysdocno=" + sysdocno;
//				return;
//			}
			
			return;
	} else if(retCode == "OK") {
		var cnf = window.confirm("배부하시겠습니까?");
		
		if(cnf) {
			location.href = "/deliveryProcess.do?sysdocno=" + sysdocno;
		}
	} else {
		alert("알수없는 오류가 발생했습니다. 잠시후 다시 시도하세요.\n문제가 지속될 경우 관리자에게 문의하세요.");
		return;
	}
}

function chkDeliveryProc(sysdocno)
{
	IsAssignCharge(sysdocno);
}

//검색 버튼 클릭시
function getSearch(frm, isSysMgr){
  if(isSysMgr == '001'){
		if(frm.sch_deptnm.value == '' && frm.sch_usernm.value == '') frm.initentry.value = "first";
		else	frm.initentry.value = "second";
	}else{
		frm.initentry.value = "first";
	}
	frm.submit();
}

//조직도 페이지 호출시
var formationObj;
function fTree(frm, orggbn, orgid){
	if(orggbn == 'null') orggbn = "";
	if(orgid == 'null')  orgid = "";
	
	var url  = '/formation/formation.jsp?orggbn=' + orggbn + '&orgid=' + orgid;
	formationObj = window.open(url, "popup", "width=390,height=700");
}

//조직도에서 넘어오는 데이터 : 조직도 더블클릭시 호출 메소드 
function fTreeDblClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn) {
	if(formationObj != null) formationObj.close();
	if(grpgbn == 2){
		document.forms[0].sch_old_deptcd.value = orgid;
		document.forms[0].sch_deptnm.value 		 = orgfullnm;
		document.forms[0].sch_old_userid.value = "";
		document.forms[0].sch_usernm.value 		 = "";
	}else{
		document.forms[0].sch_old_deptcd.value = upper_orgid;
		document.forms[0].sch_deptnm.value 	 	 = upper_orgfullnm;
		document.forms[0].sch_old_userid.value = orgid;
		document.forms[0].sch_usernm.value 		 = orgnm;
	}
	getSearch(document.forms[0], '001');
}

function click_popup(actionpath, width, height) {
	var left = (screen.width - width) / 2;
	var top = (screen.height - height) / 2;
	var option = 
		"dialogWidth:" + width + "px; " +
		"dialogHeight:" + height + "px; " +
		"dialogLeft:" + left + "px; " +
		"dialogTop:" + top + "px; " +
		"resizable:yes; scroll:yes; status:yes; center:no; " +
		"dialogHide:no; edge:raised; help:no; unadorned:no";

	try {
		window.showModalDialog(actionpath , window, option);
	} catch(exception) {
		alert("미리보기 기능을 사용하시려면 팝업 차단 사용을 꺼주시거나\n" +
					"팝업 허용 사이트로 등록해주시기 바랍니다.\n\n" +
					"제어판 → 인터넷옵션 → 개인정보 → 팝업차단사용(체크해제)\n" +
					"또는 설정(버튼클릭) → 허용할 웹 사이트 주소 입력 후 추가");
		try {processingHide();} catch(ex) {}
		try {parent.processingHide();} catch(ex) {}
	}
}