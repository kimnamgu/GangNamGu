/**
 * 작성일: 2013.03.05
 * 작성자: 대리 서동찬
 * 모듈명: 주요업무관리
 * 설명:
 */
 
function resetFile(objName) {
	document.getElementById(objName).outerHTML = document.getElementById(objName).outerHTML;
}

function workResultSave(wForm) {	
	if ( wForm.title.value.trim() == "" ) {
		alert("실적요약을 입력하세요");
		wForm.title.focus();
		return;
	}
	
	if ( confirm("저장하시겠습니까?") ) {
		//HTML에디터에서 [미리보기->소스보기]로 전환시 잘못된 태그부분 자동 수정됨
    var fck = FCKeditorAPI.GetInstance("content");
    if(fck.EditMode == 0) {
			fck.SwitchEditMode();		//EditMode=0(미리보기)일 때 SwitchEditMode()를 호출하지 않으면 오류 발생
		}
		wForm.content.value = fck.GetXHTML(true);

		wForm.submit();
	}
}

function workResultDelete(action) {
	if ( confirm("삭제하시겠습니까?") ) {
		location.href = action;
	}
}

function frameResize(tId, fId) {
	var frmDoc = document.getElementById(fId).contentWindow.document;
	if(frmDoc.readyState == "complete") {		
		var sh = frmDoc.body.scrollHeight;
		document.getElementById(tId).height = ( frmDoc.location.href == "about:blank" ) ? 1 : sh;
	}
}