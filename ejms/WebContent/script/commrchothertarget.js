/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է´��������
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
	this.className = '';
	this.deptName = '';
	this.sancYn = '';
	this.chrgunitNm ='';
	this.tgtcode="";
	this.tgtname="";
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
 
function loadUser(){
	var treeUser = document.getElementById('treebox_user');
	treeUser.innerHTML = '';
	tree=new dhtmlxtreeObject("treebox_user","100%","100%",0);
	tree.setImagePath("/dhtmlxtree/imgs/");
	  
	//tree.enableCheckBoxes(true);
	tree.setOnDblClickHandler(addUser);
	tree.loadXML("/commrchothertargetList.do");
}

/**
 * ���õ� ����ڸ� ���ұ׷쿡 �߰��Ѵ�.
 */
function addUser(){
	var addUserList = new Array();
	
	// ����ڸ� ���� �ߴ��� ���� Ȯ��
	if (!chkUserStatus()){
		return;
	}	
	
	var userId = tree.getSelectedItemId();
	var level = tree.getLevel(userId);
	var userName = tree.getItemText(userId);
	
	if (userId){
		user = new userObject();
		user.userId = userId;
		user.userName = userName;
		
		addUserList.push(user);
		user = null;
	}
		
	addList(addUserList);
	
	return;
}

/**
 * ���� : ����_����� ��Ͽ� ���õ� ����� ����� �߰��Ѵ�.
 * ����1 userObject��ü�� ��� �ִ� Array
 */
function addList(userList){
	var opt;
	var user;
	
	for (var i = 0; i < userList.length ; i++){
		user = userList[i];
		// ������ �������� �ʴ� ����ڿ� ���ؼ��� �߰�
		if (!existUserChk(user.userId)){
		
			opt = document.createElement('OPTION');
			opt.value = user.userId;
			opt.text = user.userName ;
			
			document.frm.userList.add(opt);
			
			user = null;
			opt = null;
		}
	}
}

/**
 * ���� : ����ڰ� �����ϴ��� ���θ� üũ
 * ���� : �����ID, ��
 */
function existUserChk(userId){
	var startIdx = 0;
	var localId = "";
	var UserList= document.frm.userList.options;
	
	if (UserList.length > 0){
		for (var i = 0 ; i < UserList.length ; i++){
			localId = UserList[i].value;
			startIdx = localId.indexOf('(');	
			if (localId.substring(0,(startIdx>-1)? startIdx: localId.length)== userId){
				return true;
				break;
			}
		}
	}
	return false;
}

/**
 * ���õ� ����ڸ� �����Ѵ�.
 */
function delUser(){
	var userList= document.frm.userList;
	var UserCnt =userList.options.length;
	
	for (var i = (UserCnt - 1) ; i > -1 ; i--){
		if (userList.options[i].selected){
			userList.remove(i);
		}
	}
}

/**
 * ���� : ����_����� �߰�/���� �� ����� ���õǾ� �ִ��� ����üũ
 */
function chkUserStatus(){
	var selectedUserId = tree.getSelectedItemId();
	
	if (selectedUserId == ''){
		alert('������ ����� �����ϴ�.');
		return false;
	}
	return true;
}

/**
 * ���� : �Է´���� ������ϳ��뺸��.
 * ���� : �ý��۹�����ȣ
 */
function viewUser(){

	var params = '';
	var rchno = document.frm.rchno;
	var rchgrpno = document.frm.rchgrpno;
	
	params += 'rchno=' + encodeURIComponent(rchno.value);
	params += '&rchgrpno=' + encodeURIComponent(rchgrpno.value);

	var userList = new Ajax.Request('/commrchothertargetView.do', {method: 'post', postBody: params, onComplete: parseGumToUser});
	return false;
}
/**
 * ����ڿ� ���� ���(XML)�� �Ľ��Ѵ�.
 */
function parseGumToUser(httpXML){
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
	
	writeGumToUser();
}
/**
 * ������ ����  ����ڸ� ȭ�鿡 ����Ѵ�.
 */
function writeGumToUser(){
	var userList = document.frm.userList;
	userList.innerHTML = '';	
	
	if (globalObjStorage.length > 0){
		var opt;
		var opt1;
			  
		for (var i = 0 ; i < globalObjStorage.length ; i++){
			user = globalObjStorage[i];
			opt = document.createElement('OPTION');
			opt.value = user.tgtcode;
			opt.text = user.tgtname + '(' + user.tgtcode + ')';
			userList.add(opt);	
			user = null;
		}
	}else{
		userList.innerHTML = '';
	}
	
	globalObjStorage = null;
}
/**
 * ����� ����Ѵ�. 
 */
function validation(){
	var opener = window.dialogArguments; 
	var url = '/commrchothertargetInsert.do';
	var params = '';
	var approvalUser = document.frm.userList;
	var rchno = document.frm.rchno.value;
	var rchgrpno = document.frm.rchgrpno.value;
	
	var idList = new Array();
	var nameList = new Array();
	var nameList1 = new Array();
	var gbnList = new Array();
	
	var tmpId = '';
	var tmpName = '';
	var startIdx = 0;
	
	for (var i = 0 ; i < approvalUser.options.length; i++){
		user = new userObject();
		tmpId 	= approvalUser.options[i].value;
		tmpName = approvalUser.options[i].text;
		
	  startIdx = tmpId.indexOf('(');
		idList.push(tmpId.substring(0, (startIdx > -1)? startIdx : tmpId.length));
		
		startIdx = tmpName.indexOf('(');
		nameList.push(tmpName.substring(0, (startIdx > -1)? startIdx : tmpName.length));
		nameList1.push(tmpName);
		
		gbnList.push("1");
		
		user = null;
	}

	opener.document.forms[0].othertgtnm.value = nameList1;

	params = 'cmd=INSERT' + '&rchno=' + rchno + '&rchgrpno=' + rchgrpno + '&idList=' + encodeURIComponent(idList.join(':')) 
			+ '&nameList=' + encodeURIComponent(nameList.join(':')) + '&gbnList=' + encodeURIComponent(gbnList.join(':'));
			
	var insert = new Ajax.Request(url, {method: 'post', postBody: params, onComplete: callBack, onFailure: errMsg});
}


function callBack(resXML){
	var xmlDoc = resXML.responseXML;
	var cmd, retCode, msg;
	
	var resultNode = xmlDoc.getElementsByTagName('result');

	cmd = resultNode[0].childNodes[0].text;
	retCode = resultNode[0].childNodes[1].text;
	msg = resultNode[0].childNodes[2].text;
	
	if (cmd == 'INSERT'){
		if (parseInt(retCode, 10) > 0){
			if(msg != "") {
				window.dialogArguments.alert(msg);
			}
			self.close();
		} else if (parseInt(retCode, 10) == 0){
			alert('������� ����� ������ �߻��Ͽ����ϴ�.');
		}
	}
}

function errMsg(e){
	alert(e);
}

function cancel(){
	self.close();
}