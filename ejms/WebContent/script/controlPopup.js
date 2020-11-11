/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 테스트용
 * 설명:
 */

//<input type="button" value="팝업"
//	onclick="controlPopup('http://www.naver.com', 'width=300,height=300,status=yes', '200')">

var popupWindow = null;
var popupWindowStatus = null;
function controlPopup(url, attrib, checkInterval) {
	popupWindow = window.open(url, "pop", attrib);
	popupWindowStatus = setInterval(function() {
		try {
			if (popupWindow.closed == true) {
				if(popupWindowStatus != null) {
					clearInterval(popupWindowStatus);
					popupWindowStatus = null;
				}
				popupCallback();
				popupWindow = null;
			}
		} catch (exception) {
			popupCallback();
			popupWindow = null;
			popupWindowStatus = null;
		}
	}, checkInterval);
}
function popupCallback() {
	try {	//닫기고 나서 작업
		
	} finally {

	}
}