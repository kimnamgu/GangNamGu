/**
 * �ۼ���: 2013.03.05
 * �ۼ���: �븮 ������
 * ����: �ֿ��������
 * ����:
 */
 
function resetFile(objName) {
	document.getElementById(objName).outerHTML = document.getElementById(objName).outerHTML;
}

function workResultSave(wForm) {	
	if ( wForm.title.value.trim() == "" ) {
		alert("��������� �Է��ϼ���");
		wForm.title.focus();
		return;
	}
	
	if ( confirm("�����Ͻðڽ��ϱ�?") ) {
		//HTML�����Ϳ��� [�̸�����->�ҽ�����]�� ��ȯ�� �߸��� �±׺κ� �ڵ� ������
    var fck = FCKeditorAPI.GetInstance("content");
    if(fck.EditMode == 0) {
			fck.SwitchEditMode();		//EditMode=0(�̸�����)�� �� SwitchEditMode()�� ȣ������ ������ ���� �߻�
		}
		wForm.content.value = fck.GetXHTML(true);

		wForm.submit();
	}
}

function workResultDelete(action) {
	if ( confirm("�����Ͻðڽ��ϱ�?") ) {
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