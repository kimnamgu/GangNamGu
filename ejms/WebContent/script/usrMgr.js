var select_dept_id	  = "";
var select_dept_name  = "";
var select_dept_level = 0;

var select_type = "";
var select_code = "";

//����ڿ� �μ� ����
function setbtndel(typ,data){
	if(typ=="USR") desc="�����";
	else if(typ=="DEPT") desc="�μ�";
	else desc = "";
	
	select_type = typ;
	select_code = data;
	
	if(desc=="") {
		select_type = "";
		select_code = "";
		btn_del.innerText = "[�μ�/����ڸ� �����ϼ���]";
	
	} else {
		btn_del.innerText = "["+desc+"] "+data+"";
	}
}

//������������ �̵�(p_err:�����ڵ�)
function goBack(p_err){
	var s_msg="";
	if (p_err.toUpperCase() =="USR_I") {
		s_msg = "�̹� �����ϴ� ����ھ��̵� �Դϴ�.";
	} else if (p_err.toUpperCase()=="DEPT_I") {
		s_msg = "�̹� �����ϴ� �μ��ڵ� �Դϴ�.";
	}
  	alert(s_msg);
	history.back();
}

/******************** ù���� iframe START *******************************/

//ù��° iframe
function click_deptname(dept_id, dept_name, dept_level){
	var orggbn = document.forms[0].orggbn.value;
	window.open('/usrMgrFrame2.do?dept_id='+dept_id+'&orggbn='+orggbn,'ifrm2');
	select_dept.innerHTML = "���úμ� :<B> "+dept_name+"</B>" ;
	select_dept_id 		= dept_id;
	select_dept_name 	= dept_name;
	select_dept_level = dept_level;
	select_orggbn 	  = document.forms[0].orggbn.value;
	setbtndel('','');
	window.open("about:blank","ifrm3");
}

function orggbnChange(frm){
  window.open('/usrMgrFrame1.do?orggbn='+frm.orggbn.value,'ifrm1');

}
/********************* ù���� iframe END ********************************/

/******************** �ι�° iframe START *******************************/

//�����μ��� 
function click_edit_dept(dept_id, dept_name){
	setbtndel('DEPT',dept_id);
	window.open('/usrMgrFrame3.do?mode=dept_M&dept_id='+dept_id,'ifrm3');
}

//��������ڸ�
function click_edit_usr(user_id, user_name, dept_id){
	setbtndel('USR',user_id);
	window.open('/usrMgrFrame3.do?mode=usr_M&user_id='+user_id+'&dept_id='+dept_id,'ifrm3');
}

//����� �˻�
function clickSearch(p_dept_id, p_dept_name, p_user_id){
	window.open('/usrMgrFrame2.do?dept_id='+p_dept_id,'ifrm2');
	select_dept.innerHTML = "���缱�úμ�: <B>("+p_dept_id+") "+p_dept_name+"</B>" ;
	select_dept_id    = p_dept_id;
	select_dept_name  = p_dept_name;

	window.open('/usrMgrFrame3.do?mode=usr_M&user_id='+p_user_id+'&dept_id='+p_dept_id,'ifrm3');
	setbtndel('USR',p_user_id);
}

//����
function click_del(){
	if(select_type!="USR" && select_type!="DEPT") {
		alert("�μ�/����ڸ� �����ϼž� �մϴ�.");
		return;
	}
	if(select_type=="USR") {
		if(!confirm('����� '+select_code+'��(��) �����Ͻðڽ��ϱ�?')) return;				
		window.open('/deptManageDB.do?mode=usr_d&id='+select_code,'ifrm3');
	}
	if(select_type=="DEPT") {
		if(!confirm('�μ� '+select_code+'��(��) �����Ͻðڽ��ϱ�?')) return;
		window.open('/deptManageDB.do?mode=dept_d&id='+select_code,"ifrm3");
	}
}

//����� �̵�
function ifrm2_refresh(gubun){
	if(gubun == "I") alert("����Ǿ����ϴ�.");
	if(gubun == "U") alert("�����Ǿ����ϴ�.");
	if(gubun == "D") alert("�����Ǿ����ϴ�.");
	click_deptname(select_dept_id, select_dept_name);
}

//�μ� �̵�
function ifrm1_refresh(gubun){
	if(gubun == "I") alert("����Ǿ����ϴ�.");
	if(gubun == "U") alert("�����Ǿ����ϴ�.");
	if(gubun == "D") alert("�����Ǿ����ϴ�.");
	if(gubun == "P") alert("��ȣ�� �ʱ�ȭ �Ǿ����ϴ�.");
  window.open('/usrMgrFrame1.do','ifrm1');
}
/********************* �ι�° iframe END ********************************/

/******************** ����°  iframe START *******************************/

//�űԺμ�
function click_new_dept(){
	if(select_dept_id.trim()=="") { alert("�μ��� �����ϼ���"); return; }
	setbtndel('','');
	if(select_dept_level == 6) {
		window.open("about:blank","ifrm3");
		alert("���̻� �����μ��� ���� �� �����ϴ�.");
		return;
	}
	window.open('/usrMgrFrame3.do?mode=dept_I&dept_id='+select_dept_id,'ifrm3');
}

//�űԻ����
function click_new_usr(){
	if(select_dept_id.trim()=="") { alert("�μ��� �����ϼ���"); return; }
	setbtndel('','');
	window.open('/usrMgrFrame3.do?mode=usr_I&dept_id='+select_dept_id,'ifrm3');
}

//����� �н����� ����Ʈ Submit
function UsrDefaultPasswdSumbit(){
	if (confirm("��ȣ�� �ʱ�ȭ�ϸ� 1�� ������ �˴ϴ�. \n��ȣ�� �ʱ�ȭ�Ͻðڽ��ϱ�?")) {
		ifrm3.document.forms[0].mode.value="defaultPasswd";
		ifrm3.document.forms[0].submit();
	} else {
		return false;
	}
}

//�μ� Submit
function DeptSumbit(){
	if(ifrm3.document.forms[0].upper_dept_id.value.trim() == "") {
		alert("�����μ��ڵ尡 �����ϴ�.");
		ifrm3.document.forms[0].upper_dept_id.focus();
		return;
	}
	if(ifrm3.document.forms[0].dept_id.value.trim() == "") {
		alert("�μ��ڵ尡 �����ϴ�.");
		ifrm3.document.forms[0].dept_id.focus();
		return;
	}
	if(getCharLen(ifrm3.document.forms[0].dept_id.value.trim())>40) {
		alert("�μ��ڵ� �Է� �ִ� ���̸� �ʰ��Ͽ����ϴ�.\n����+���� 40��, �ѱ� 20�ڱ��� �Է°����մϴ�.");
		ifrm3.document.forms[0].dept_id.focus();
		return;
	}
	if(ifrm3.document.forms[0].dept_name.value.trim() == "") {
		alert("�μ����� �����ϴ�.");
		ifrm3.document.forms[0].dept_name.focus();
		return;
	}
	if(getCharLen(ifrm3.document.forms[0].dept_name.value.trim())>40) {
		alert("�μ��� �Է� �ִ� ���̸� �ʰ��Ͽ����ϴ�.\n����+���� 40��, �ѱ� 20�ڱ��� �Է°����մϴ�.");
		ifrm3.document.forms[0].dept_name.focus();
		return;
	}
	
	if(ifrm3.document.forms[0].dept_rank.value.trim()!="" && ifrm3.document.forms[0].dept_rank.value.trim()>999999999) {
		alert("�μ����� �Է� �ִ� ���� �ʰ��Ͽ����ϴ�.\n999,999,999���Ϸ� �Է��Ͻñ� �ٶ��ϴ�.");
		ifrm3.document.forms[0].dept_rank.focus();
		return;
	}
	ifrm3.document.forms[0].submit();
}

//����� Submit
function UsrSumbit(){
	if(ifrm3.document.forms[0].user_id.value.trim() == "") {
		alert("����� ���̵� �����ϴ�.");
		ifrm3.document.forms[0].user_id.focus();
		return;
	}
	if(getCharLen(ifrm3.document.forms[0].user_id.value.trim())>40) {
		alert("����� ���̵� �Է� �ִ� ���̸� �ʰ��Ͽ����ϴ�.\n����+���� 40��, �ѱ� 20�ڱ��� �Է°����մϴ�.");
		ifrm3.document.forms[0].user_id.focus();
		return;
	}
	if(ifrm3.document.forms[0].user_name.value.trim() == "") {
		alert("����ڸ��� �����ϴ�.");
		ifrm3.document.forms[0].user_name.focus();
		return;
	}
	if(getCharLen(ifrm3.document.forms[0].user_name.value.trim())>80) {
		alert("����ڸ� �Է� �ִ� ���̸� �ʰ��Ͽ����ϴ�.\n����+���� 80��, �ѱ� 40�ڱ��� �Է°����մϴ�.");
		ifrm3.document.forms[0].user_name.focus();
		return;
	}
	if(ifrm3.document.forms[0].dept_id.value.trim() == "") {
		alert("�μ��� �������� �ʾҽ��ϴ�.");
		ifrm3.document.forms[0].dept_id.focus();
		return;
	}
	
	//�н������Է�
	if(ifrm3.document.forms[0].passwd.value.trim() == "") {
		alert("��ȣ�� �Է��� �ֽñ� �ٶ��ϴ�.");
		ifrm3.document.forms[0].passwd.focus();
		return;
	}
	ifrm3.document.forms[0].submit();
}
/********************* ����° iframe END ********************************/
