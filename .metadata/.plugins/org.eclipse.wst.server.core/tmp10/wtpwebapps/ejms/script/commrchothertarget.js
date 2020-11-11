/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력담당자지정
 * 설명:
 */

// 부서 또는 사용자 검색을 통해 사용자 객체를 담을 전역 배열변수
var globalObjStorage;
/**
 * 부서를 새로 가져온다.
 *
 */
 
 /**
 * 사용자 정보를 담을 객체
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
 * 설명 : 사용자 객체에 특정 속성의 데이터를 설정한다.
 * 인자1 obj - 속성정보를 저장할 사용자 객체
 * 인자2 node - 속성정보를 갖고 있는 XML Node객체
 */
userObject.prototype.setData = function(obj, node){
	// obj의 속성(prop)에 대해 루프를 돌면서 
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
 * 선택된 사용자를 역할그룹에 추가한다.
 */
function addUser(){
	var addUserList = new Array();
	
	// 사용자를 선택 했는지 여부 확인
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
 * 설명 : 역할_사용자 목록에 선택된 사용자 목록을 추가한다.
 * 인자1 userObject객체를 담고 있는 Array
 */
function addList(userList){
	var opt;
	var user;
	
	for (var i = 0; i < userList.length ; i++){
		user = userList[i];
		// 기존에 존재하지 않는 사용자에 대해서만 추가
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
 * 설명 : 사용자가 존재하는지 여부를 체크
 * 인자 : 사용자ID, 구
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
 * 선택된 사용자를 삭제한다.
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
 * 설명 : 역할_사용자 추가/삭제 시 사람이 선택되어 있는지 상태체크
 */
function chkUserStatus(){
	var selectedUserId = tree.getSelectedItemId();
	
	if (selectedUserId == ''){
		alert('선택한 대상이 없습니다.');
		return false;
	}
	return true;
}

/**
 * 설명 : 입력담당자 기존등록내용보기.
 * 인자 : 시스템문서번호
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
 * 사용자에 대한 결과(XML)를 파싱한다.
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
 * 결제선 지정  사용자를 화면에 출력한다.
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
 * 대상을 등록한다. 
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
			alert('대상지정 저장시 에러가 발생하였습니다.');
		}
	}
}

function errMsg(e){
	alert(e);
}

function cancel(){
	self.close();
}