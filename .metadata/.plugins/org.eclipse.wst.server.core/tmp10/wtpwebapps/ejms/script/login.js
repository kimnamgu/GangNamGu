/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �α���
 * ����:
 */

// �յڷ� ������� ���ش�.
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/gi, "");
}

//����Ű �� ���� ������(nextItem)���� ��Ŀ�� �̵�
function enterKey_focus(nextItem) {
	if(event.keyCode == 13) {
		eval("document." + nextItem + ".focus()");
	}
}

function check_submit(){
	document.loginForm.userId.value = document.loginForm.userId.value.trim();
	if(document.loginForm.userId.value == ""){
		alert('���̵� �Է��ϼ���');
		document.loginForm.userId.focus();
		return;
	}
	if(document.loginForm.password.value == ""){
		alert('�н����带  �Է��ϼ���');
		document.loginForm.password.focus();
		return;
	}
	
	document.loginForm.submit();
}

function loadFocus(val){
	if(val.trim() == ""){	
		//���̵� ���� ������ ���̵� ��Ŀ��
		document.loginForm.userId.focus();
	} else {
		//���̵��� ������ �н����忡 ��Ŀ��	                           
		document.loginForm.password.focus();
	}
}

//�α��� ���п��� �޼���
function loginerror()
{
	alert('���̵�/��й�ȣ�� ��ġ�ϴ� ����ڰ� �����ϴ�.');			

	document.loginForm.password.value = '';
	document.loginForm.password.focus();
}