/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �׽�Ʈ��
 * ����:
 */

//<input type="button" value="�˾�"
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
	try {	//�ݱ�� ���� �۾�
		
	} finally {

	}
}