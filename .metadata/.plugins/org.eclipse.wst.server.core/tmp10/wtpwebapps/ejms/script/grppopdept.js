/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 관리자 배포목록관리
 * 설명:
 */

// 부서 또는 사용자 검색을 통해 사용자 객체를 담을 전역 배열변수
var globalObjStorage;
var treeformationState=false;
/**
 * 부서를 새로 가져온다.
 *
 */
 
 /**
 * 사용자 정보를 담을 객체
 */
function deptObject(){
	this.deptId = '';
	this.deptName = '';
	this.deptFullName = '';
	this.grpGbn = '';
	this.mainFl = '';
	return this;
}

/**
 * 설명 : 사용자 객체에 특정 속성의 데이터를 설정한다.
 * 인자1 obj - 속성정보를 저장할 사용자 객체
 * 인자2 node - 속성정보를 갖고 있는 XML Node객체
 */
deptObject.prototype.setData = function(obj, node){
	// obj의 속성(prop)에 대해 루프를 돌면서 
	for (prop in obj){
		if (prop == node.attributes[0].value){
			obj[prop] = node.text;
			break;
		}
	}
	return obj;
};

function loadFormation(orgid) {
	var orggbn = document.forms[0].sch_orggbn.value;
	setUrl("/formation.do");
	setType("DEPTUSER");
	setViewObjectId(treebox_formation);
	setSearchData(document.forms[0].searchType.value, document.forms[0].searchValue.value);
	if ( treeformationState == false) {
		getFormation(true, orggbn, orgid);
		treeformationState = true;
	}
}

function searchFormation() {
	if ( document.forms[0].searchValue.value.trim() != "" ) {		
		setUrl("/formation.do");
		setType("DEPTUSER");
		setViewObjectId(treebox_formation);
		setSearchData(document.forms[0].searchType.value, document.forms[0].searchValue.value);
		getFormation(true, "", "");
	} else {
		setSearchData("", "");
		treeformationState = false;
		loadFormation();
	}
}

// 콤보박스에서 선택 
function orggbnChange(){
	treeformationState = false;
	loadFormation();
}

/**
 * 선택한 역할명에 대한 정보를 출력한다.
 * 인자1 - id : 선택한 역할명에 대한 역할ID 
 */
function getDetailList(sch_grplist){
	var params = 'sch_grplist=' + encodeURIComponent(sch_grplist) + '&mode=sub_l';
	var detailList = new Ajax.Request('/grpPopup.do', {method: 'post', postBody: params, onComplete: parseDetail});
}

/**
 * 역할사용자에 대한 결과(XML)를 파싱한다.
 */
function parseDetail(httpXML){
	var xmlDoc = httpXML.responseXML;
	
	var root = xmlDoc.documentElement;
	var dataList = root.childNodes;

	globalObjStorage = new Array();

	for (var i = 0 ; i < dataList.length; i++){
		
		var dept = new deptObject();
		for (var j = 0 ; j < dataList[i].childNodes.length; j++){
			dept.setData(dept, dataList[i].childNodes[j]);
		}
		
		globalObjStorage[i] = dept;
		dept = null;
	}
	writeDetail();
}
/**
 * 업무영역의 세부업무(역할그룹) 선택 시 해당하는 역할 사용자를 화면에 출력한다.
 */
function writeDetail(){
	var grpDetail = document.forms[0].deptList;
	grpDetail.innerHTML = '';	
	if (globalObjStorage.length > 0){
		var opt;
		for (var i = 0 ; i < globalObjStorage.length ; i++){
			dept = globalObjStorage[i];
			opt = document.createElement('OPTION');
			opt.value = dept.deptId + "(" + dept.grpGbn + ")";
			opt.text = dept.deptFullName;
			opt.originText = dept.deptName;
			
			grpDetail.add(opt);
			dept = null;
		}
	}else{
		grpDetail.innerHTML = '';
	}
	
	globalObjStorage = null;
}

/**
 * 결제선 지정  사용자를 화면에 출력한다.
 */
function writeDept(){
	var DeptList = document.forms[0].deptList;
	DeptList.innerHTML = '';	
	
	if (globalObjStorage.length > 0){
		var opt;
		var opt1;
		for (var i = 0 ; i < globalObjStorage.length ; i++){
			dept = globalObjStorage[i];
			opt = document.createElement('OPTION');
			opt.value = dept.deptId + '('+ dept.grpGbn + ')';
			opt.text = dept.deptName;
			DeptList.add(opt);	
			user = null;
		}
	}else{
		DeptList.innerHTML = '';
	}
	
	globalObjStorage = null;
}

/**
 * 선택된 부서를  추가한다.
 */
function addDept(){
	if ( SELECTED_ITEM == null ) return;
	
	var level = SELECTED_ITEM.level;
	var grpgbn = SELECTED_ITEM.grpgbn;
	var mainyn = SELECTED_ITEM.main_yn;
	var deptId = SELECTED_ITEM.dept_id;
	var deptName = SELECTED_ITEM.dept_name;
	var deptFullName = SELECTED_ITEM.dept_fullname;
	
	// 부서를 선택 했는지 여부 확인
	if (!chkDeptStatus()){
		return;
	}	

	// 배포목록을 선택 했는지 여부 확인
	if (document.forms[0].sch_grplist.value == ""){
		alert("배포목록을 선택하여 주십시오.");
		return;
	}	
	
	if ( mainyn != "Y" ) {
		alert("취합대상이 아닙니다");
		return;
	}
	
	var addDeptList = new Array();
	if (deptId){
		dept = new deptObject();
		dept.grpGbn = grpgbn;
		dept.deptId = deptId;
		dept.deptName = deptName;
		dept.deptFullName = deptFullName;

		addDeptList.push(dept);
		dept = null;
	}
		
	addList(addDeptList);
}

/**
 * 설명 : 선택된 부서  추가한다.
 * 인자1 userObject객체를 담고 있는 Array
 */
function addList(deptList){
	var opt;
	var dept;
	
	for (var i = 0; i < deptList.length ; i++){
		dept = deptList[i];
		// 기존에 존재하지 않는 부서에 대해서만 추가
		if (!existDeptChk(dept.deptId)){
		
			opt = document.createElement('OPTION');
			opt.value = dept.deptId + '('+ dept.grpGbn + ')';
			opt.text = dept.deptFullName;
			opt.originText = dept.deptName;

			document.forms[0].deptList.add(opt);
			dept = null;
			opt = null;
		}
	}
}

function fTreeClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn) {
	if ( grpgbn == 1 ) {
		getDetailList(orgid);
	}
}

function fTreeDblClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn) {
	if ( grpgbn == 1 ) {
		addDept1();
	} else if ( grpgbn == 2 ) {
		if ( SELECTED_ITEM.main_yn == "Y" ) {
			addDept();
			event.cancelBubble = true;
		}
	} else if ( grpgbn == 3 ) {
		if ( SELECTED_ITEM.main_yn == "Y" ) {
			addDept();
			event.cancelBubble = true;
		}
	}
}

/**
 * 설명 : 존재하는지 여부를 체크
 * 인자 : 부서코드
 */
function existDeptChk(deptId){
	var DeptList= document.forms[0].deptList.options;
	
	if (DeptList.length > 0){
		for (var i = 0 ; i < DeptList.length ; i++){
		
				localId = DeptList[i].value;
			startIdx = localId.indexOf('(');
			if (localId.substring(0,(startIdx>-1)? startIdx: localId.length) == deptId){
				return true;
				break;
			}
		}
	}
	return false;
}

/**
 * 선택된 부서를 삭제한다.
 */
function delDept(){
	var DeptList= document.forms[0].deptList;
	var DeptCnt =DeptList.options.length;
	for (var i = (DeptCnt - 1) ; i > -1 ; i--){
		if (DeptList.options[i].selected){
			DeptList.remove(i);
		}
	}
}

/**
 * 설명 :부서 추가/삭제 시 부서가 선택되어 있는지 상태체크
 */
function chkDeptStatus(){
	if ( SELECTED_ITEM == null ) return;
	var selectedDeptId = SELECTED_ITEM.dept_id;
	
	if (selectedDeptId == ''){
		alert('선택하신 부서가 없습니다.');
		return false;
	}
	return true;
}

function validation(){
	var url = '/grpDB.do';
	var params = '';
	var deptList = document.forms[0].deptList;
	var sch_grplist = document.forms[0].sch_grplist.value;
	
	var idList = new Array();
	var grpgbnList = new Array();
	var nameList = new Array();
	
	var tmpId = '';
	var startIdx = 0;
	for (var i = 0 ; i < deptList.options.length; i++){
		tmpId = deptList.options[i].value ;
		startIdx = tmpId.indexOf('(');
		
		idList.push(tmpId.substring(0, (startIdx > -1)? startIdx : tmpId.length));
		grpgbnList.push(tmpId.substring((startIdx + 1),(startIdx + 2)));
		nameList.push(deptList.options[i].originText);
	}
	
	params = 'cmd=INSERT' + '&mode=sub_i&sch_grplist='+ encodeURIComponent(sch_grplist) + '&idList=' + encodeURIComponent(idList.join(';')) + 
					 '&grpgbnList=' + encodeURIComponent(grpgbnList.join(';')) + '&nameList=' + encodeURIComponent(nameList.join(';'));
					 
	var insert = new Ajax.Request(url, {method: 'post', postBody: params, onComplete: callBack, onFailure: errMsg});
}


function callBack(resXML){
	var opener = window.dialogArguments; 
	var xmlDoc = resXML.responseXML;
	var cmd, retCode;
	
	var resultNode = xmlDoc.getElementsByTagName('result');

	cmd = resultNode[0].childNodes[0].text;
	retCode = resultNode[0].childNodes[1].text;
	
	if (cmd == 'INSERT'){
		
		if (parseInt(retCode, 10) > 0){
			if ( location.search.indexOf("userModifyMode=y") == -1 ) {
				var sch_grplist = document.forms[0].sch_grplist.value;
				var ppos = opener.parentdiv.scrollTop;	 //부모창스크롤(좌측)
				var cpos = opener.childdiv.scrollTop;	 //자식창스크롤(우측)
				opener.location.href = '/grpList.do?grplistcd='+sch_grplist+'&posi='+ppos+'&cposi='+cpos;
			}
			self.close();
		} else if (parseInt(retCode, 10) == 0){
			alert('배포목록 세부부서지정 에러가 발생하였습니다.');
		}
	}
}

function errMsg(e){
	alert(e);
}

function cancel(){
	self.close();
}