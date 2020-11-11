/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����ں���
 * ����:
 */

// �μ� �Ǵ� ����� �˻��� ���� ����� ��ü�� ���� ���� �迭����
var globalObjStorage;
/**
 * �μ��� ���� �����´�.
 *
 */
 
 /**
 * ����� ������ ���� ��ü
 */
function userObject(){
	this.userId = '';
	this.userName = '';
	this.deptId = '';
	this.deptName = '';
	this.grpGbn = '';
	this.mainFl = '';
	return this;
}

/**
 * ���� : ����� ��ü�� Ư�� �Ӽ��� �����͸� �����Ѵ�.
 * ����1 obj - �Ӽ������� ������ ����� ��ü
 * ����2 node - �Ӽ������� ���� �ִ� XML Node��ü
 */
userObject.prototype.setData = function(obj, node){
	// obj�� �Ӽ�(prop)�� ���� ������ ���鼭 
	for (prop in obj){
		if (prop == node.attributes[0].value){
			obj[prop] = node.text;
			break;
		}
	}
	return obj;
};
 
 /*
function loadFormation(){
	var treeFormation = document.getElementById('treebox_formation');
	treeFormation.innerHTML = '';
	tree = new dhtmlxtreeObject("treebox_formation","100%","100%",0);
	tree.setImagePath("/dhtmlxtree/imgs/");
	  
	//tree.enableCheckBoxes(true);
	tree.setOnClickHandler(getUserList);
	tree.loadXML("/commsubdept/formationList.do");
}
*/

/**
 * �������� ǥ���Ѵ�.
 */
function loadFormation(orgid){
	var orggbn = document.forms[0].orggbn.value;
	setUrl("/formation.do");
	setType("DEPT");
	setViewObjectId(formationLayer);
	getFormation(true, orggbn, orgid);
}

//�޺��ڽ����� ���� 
function orggbnChange(){
	loadFormation();
}

//���������� �Ѿ���� ������ : ������ ����Ŭ���� ȣ�� �޼ҵ� 
function fTreeClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn) {
	getUserList(orgid);
}

/**
 * ���� : ����_����� �߰�/���� �� ����� ���õǾ� �ִ��� ����üũ
 */
function chkDeptStatus(){
	var selectedDeptId = tree.getSelectedItemId();
	
	if (selectedDeptId == ''){
		alert('�����Ͻ� �μ��� �����ϴ�.');
		return false;
	}
	return true;
}

/**
 * ���� : ���������� �μ��� ������ ��� �ش�μ��� ����ڸ� ��ȸ�Ѵ�.
 * ���� : ���������� ���õ� �μ��ڵ�
 */
function getUserList(id){
	searchUser(id);
}

/**
 * ���� : �μ� �Ǵ� ����ڰ˻� �� ����ڸ� ��ȸ�Ѵ�.
 * ���� : �μ��ڵ�
 */
function searchUser(deptCode){
	
	var params = "";
	var vUserName = document.searchForm.userName;
	
	if (deptCode != null && deptCode != undefined){
		params += 'deptCode=' + encodeURIComponent(deptCode);
		vUserName.value = '';
	}else{
		if (vUserName.value == ''){
			alert('�˻��� �������� �Է����ּ���');
			vUserName.focus();
			return false;
		}
		params += '&userName=' + encodeURIComponent(vUserName.value);
	}
	var userList = new Ajax.Request('/commsubdept/deptuserList.do', {method: 'post', postBody: params, onComplete: parseDeptUser});
	return false;
}

/**
 * �μ��� ����� ��ȸ�� ���� ���XML��  �Ľ��Ѵ�.
 */
function parseDeptUser(httpXML){

	var xmlDoc = httpXML.responseXML;
	
	var root = xmlDoc.documentElement;
	var dataList = root.childNodes;

	globalObjStorage = new Array();

	for (var i = 0 ; i < dataList.length; i++){
		
		var user = new userObject();
		for (var j = 0 ; j < dataList[i].childNodes.length; j++){
			user.setData(user, dataList[i].childNodes[j]);
		}
		
		globalObjStorage[i] = user;
		user = null;
	}
	
	writeDeptUser();
}

/**
 * ���� : �Ľ̵� ����� ������ ȭ�鿡 ����Ѵ�.
 */
function writeDeptUser(){

	var	htmlStr = '        <form name="userForm" onsubmit="return false">'
				+ '        <table width="340" border="0" align="ceneter" cellpadding="1" cellspacing="0">';
	
	if (globalObjStorage.length > 0){
		for (var i = 0 ; i < globalObjStorage.length ; i++){
			user = globalObjStorage[i];
			htmlStr += '          <tr>'
					+ '            <td width="40" align="center"><input type="hidden" name="deptName" value="' + user.deptName + '"><input type="hidden" name="userName" value="' + user.userName + '"><input type="checkbox" style="border:0;background-color:transparent;" name="userId" value="' + user.userId + '"></td>'
					+ '            <td width="200" align="center">' + user.deptName + '</td>'
					+ '            <td width="100" align="center">' + user.userName  + '</td>'
					+ '          </tr>';
					
			user = null;
		}
	}else{
		htmlStr += '          <tr>'
				+ '            <td height="25" align="center" colspan="3">������ �������� �ʽ��ϴ�.</td>'
				+ '          </tr>';
	}
	
	htmlStr += '        </table>'
			+ '        </form>';
	
	$('userList').innerHTML = htmlStr;
	globalObjStorage = null;
}

/**
 * ���� : ��� ���ÿ� ���� ����� üũ�ڽ� ����
 *
 */
function allCheck(obj){
	var idList;
	var chk = false;
	
	if (obj.checked)
		chk = true;	
	
	if (document.forms('userForm')){

		idList = userForm.elements['userId'];

		if (idList){
			if (idList.length){
//				alert('��������� 2���̻�');
				for (var i = 0 ; i < idList.length; i++){
					idList[i].checked = chk;
				}
			}else{
//				alert('��������� 1��');
				idList.checked = chk;
			}
		}

	}
}

/**
 * ���õ� ����ڸ� �θ�â�� ���Ѱ��ֱ� 
 */
function addUser(){
	var userId, userName, deptName;
	var message = "";
	var addUserList = new Array();

	if (document.forms('userForm')){
		
		userId = userForm.elements['userId'];
		deptName = userForm.elements['deptName'];
		userName = userForm.elements['userName'];
		
		if (userId){
			if (userId.length){

				for (var i = 0 ; i < userId.length ; i++){

					if (userId[i].checked){
						user = new userObject();
						user.userId = userId[i].value;
						user.userName = userName[i].value;
						user.deptName = deptName[i].value;
						
						addUserList.push(user);
						user = null;
						message = '';
					}
				}
			}else{
				if (userId.checked){
					user = new userObject();
					user.userId = userId.value;
					user.userName = userName.value;
					user.deptName = deptName.value;
					
					addUserList.push(user);
					message = '';
				}
			}// end (userId.length)
			
			if (addUserList.length == 0)
				message = '�����Ͻ� ������ �����ϴ�.';
		}// end (userId)
	}

	if (message != ''){
		alert(message);
	}else{
		validation(addUserList);
	}
	return;
}

function validation(userList){
	var opener = window.dialogArguments; 
	
	var idList = new Array();
	var nameList = new Array();

	for (var i = 0 ; i < userList.length; i++){
		user = userList[i];
		idList.push(user.userId);
		nameList.push(user.userName);
	}
	
	opener.document.forms[0].user_id.value = idList;

  cancel();
}

function errMsg(e){
	alert(e);
}

function cancel(){
	self.close();
}
