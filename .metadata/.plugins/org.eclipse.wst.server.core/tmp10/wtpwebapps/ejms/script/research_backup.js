/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사
 * 설명:
 */

function changeFormseqCheck(obj) {
	var changeFormseqObj = document.getElementsByName(obj.name);
	for ( var i = 0; i < changeFormseqObj.length; i++ ) {
		if ( changeFormseqObj[i].oldvalue == obj.value ) {
			changeFormseqObj[i].value = obj.oldvalue;
			changeFormseqObj[i].oldvalue = changeFormseqObj[i].value;
			obj.oldvalue = obj.value;
		}
	}
}
 
function previewImage(divObj, imgObj, src, width, height) {
	if ( typeof(src) != "undefined" ) {
		var filenm = src.toLowerCase().trim();
		if ( filenm.length > 0 &&
					(filenm.length < 8 ||
						!(filenm.substring(filenm.length - 4) == ".gif" ||
							filenm.substring(filenm.length - 4) == ".jpg" ||
							filenm.substring(filenm.length - 4) == ".png")) ) {
	
			alert("GIF, JPG, PNG 파일만 미리보기 하실 수 있습니다.");
			return;
		}
	}
	
	if ( divObj.style.display != "block" ) {
		var resizeObj = function() {
			divObj.style.width = width + 20;
			divObj.style.height = height + 20;
			document.getElementById(divObj.id + "_f").width = width + 10;
			document.getElementById(divObj.id + "_f").height = height + 10;
			document.getElementById(divObj.id + "_t1").width = width + 20;
			document.getElementById(divObj.id + "_t1").height = height + 20;
			document.getElementById(divObj.id + "_t2").width = width + 20;
			document.getElementById(divObj.id + "_t2").height = height + 20;
			document.getElementById(imgObj).width = width;
			document.getElementById(imgObj).height = height;
			document.getElementById(imgObj).src = src;
		}
		resizeObj();

		try {
			var setRealSize = function() {
				if ( !(img.width < width && img.height < height) ) {
					width = img.width;
					height = img.height;
				}
				resizeObj();
				fullSizeWindow();
			}
			
			var img = new Image();
			img.src = src;
			if ( img.readyState != "complete" ) {
				img.onreadystatechange = setRealSize;
			} else {
				setRealSize();
			}
		} catch ( exception ) {}
		
		divObj.style.display = "block";
	} else {
		divObj.style.display = "none";
	}
	fullSizeWindow();
}
 
function checkFileType(fileObj) {
	return;	//모든 파일 첨부가능
	
	var filenm = fileObj.value.toLowerCase().trim();

	if ( filenm.length > 0 &&
				(filenm.length < 8 || filenm.substring(1, 3) != ":\\" ||
					!(filenm.substring(filenm.length - 4) == ".gif" ||
						filenm.substring(filenm.length - 4) == ".jpg" ||
						filenm.substring(filenm.length - 4) == ".png")) ) {

		alert("GIF, JPG, PNG 파일만 첨부할 수 있습니다.");
		resetFile(fileObj.name);
		return;
	}
}

function resetFile(objName) {
	document.getElementById(objName).outerHTML = document.getElementById(objName).outerHTML;
}
 
function showFileUpload(divObj, divObjIdx, examObj, examIdx) {
	divObj = document.getElementById(divObj + divObjIdx);

	if ( eval("document.forms[0]." + examObj).length == undefined ) {
		examObj = eval("document.forms[0]." + examObj);
	} else {
		examObj = eval("document.forms[0]." + examObj)[examIdx];
	}
	examValue = examObj.value;

	if ( divObj.style.display == "none" ) {
		divObj.style.display = "block";
		
		if ( examValue.length == 0 ) {
			examObj.value = "파일첨부";
		}
	} else {
		divObj.style.display = "none";
		if ( examValue.substring(examValue.length - 4) == "파일첨부" ) {
			examObj.value = examValue.substring(0, examValue.length - 4);
		}
	}
}
 
function setSearchDate() {
	var frm = document.forms[0];
	var a = frm.strdt;			//기간시작
	var b = frm.enddt;			//기간종료
	
	if (a.onpropertychange == null) {
		a.onpropertychange = setSearchDate;
		b.onpropertychange = setSearchDate;
	} else if (event.propertyName.toLowerCase() == "value") {
		var src = event.srcElement;
		if (src == a) {
			if (a.value > b.value) {
				b.onpropertychange = null;
				b.value = a.value;
				b.onpropertychange = setSearchDate;
			}
		} else if (src == b) {
			if (a.value > b.value || a.value == "") {
				a.onpropertychange = null;
				a.value = b.value;
				a.onpropertychange = setSearchDate;
			}
		}
	}
}

function showSwtichLayer(obj, option) {	
	if(option == "1" || option == "2") {
		//(단일,중복)선택형
		eval("exam"+obj).style.display = "block";	
	} else if(option == "3" || option == "4") {
	  //단답형, 장문형
		eval("exam"+obj).style.display = "none";		
	}
	
	//보안처리
	if(option == "1" || option == "2" || option == "4"){
		eval("secu"+obj).style.visibility = "hidden";    	
	} else {
		eval("secu"+obj).style.visibility = "visible";  
	}
}
	
function showOpenType(option){
	var range = document.getElementsByName("range");
	var rangedetail = document.getElementsByName("rangedetail");
	
	if ( option == 1 ) {
		eval(opentype1).style.display = "block";
		eval(opentype2).style.display = "block";
		showTgtType('s');
		for ( var i = 0; i < rangedetail.length; i++ ) {
			rangedetail[i].checked = false;
		}
	} else {
		eval(opentype1).style.display = "block";
		eval(opentype2).style.display = "block";
		showTgtType('h');
		for ( var i = 0; i < rangedetail.length; i++ ) {
			if ( rangedetail[i].checked ) break;
			if ( i + 1 == rangedetail.length && range[1].checked ) rangedetail[0].checked = true;
		}
	}	
}

function showOpenTypeDetail(option){
	var range = document.getElementsByName("range");
	
	if ( option == 1 ) {
		eval(opentype1).style.display = "block";
		eval(opentype2).style.display = "block";
		showTgtType('s');
	} else {
		eval(opentype1).style.display = "none";
		eval(opentype2).style.display = "none";
		showTgtType('h');
		range[1].checked = true;
	}	
	
	showTgtType(option);
}

function showTgtType(option){
	var tgtdept1 = document.getElementById("tgtdept1");
	var tgtdept2 = document.getElementById("tgtdept2");
	var tgtdept3 = document.getElementById("tgtdept3");
	var tgtdept4 = document.getElementById("tgtdept4");
	var tgtdept5 = document.getElementById("tgtdept5");
	var tgtdept6 = document.getElementById("tgtdept6");
	var opentype = document.forms[0].opentype;
	
	if ( option == "s" && opentype[0].checked == true ) {
		tgtdept1.style.display = "block";
		tgtdept2.style.display = "block";
		tgtdept3.style.display = "block";
		tgtdept4.style.display = "block";
		tgtdept5.style.display = "block";
		tgtdept6.style.display = "block";
	} else {
		tgtdept1.style.display = "none";
		tgtdept2.style.display = "none";
		tgtdept3.style.display = "none";
		tgtdept4.style.display = "none";
		tgtdept5.style.display = "none";
		tgtdept6.style.display = "none";
	}
}

function makeForm(frm, mode){
	if(frm.title.value.trim() == "") {
		alert("제목을 입력하세요.");			
		frm.title.focus();
		return;
	}
	
	if(frm.formcount.value > 99 || frm.formcount.value < 1){
		alert('문항만들기는 1~99개 까지 만들 수 있습니다.');
		frm.formcount.focus();
		return;
	}
	
	if(frm.examcount.value > 50 || frm.examcount.value < 1){
		alert('한 문항당 보기는 1~50개 까지 만들 수 있습니다.');
		frm.examcount.focus();
		return;
	}
	if(frm.limit1chk.checked != ""){
		if(frm.tgtdeptnm.value == ""){
			alert('설문대상 제한여부를 확인해주세요.');
			return;
		}
	}
	if(frm.limit2chk.checked != ""){
		if(frm.othertgtnm.value == ""){
			alert('설문기타대상 제한여부를 확인해주세요.');
			return;
		}
	}
	
	if(mode == "add"){
		frm.posscroll.value = document.body.scrollTop + 85;
	}
	
	frm.mode.value = mode;
	processingShow();
	frm.submit();	
}	

//완료시에 확인해야 할 사항
function check_complete(frm, type){
	var obj = document.forms[0];
	var range;
	
	if(obj.title.value.trim() == "") {
		alert("제목을 입력하세요.");			
		obj.title.focus();
		return;
	}
	
	if(obj.formcount.value < 1){		
		alert('문항만들기를 먼저 수행하세요!');
		return;
	}

	if(typeof(obj.formseq) == 'undefined'){
		if(obj.formcount.value > 0){		
			alert('문항만들기 버튼을 누르세요!');
			return;
		}
	} else {
		if(typeof(obj.formseq.value) != 'undefined'){
			if(obj.formseq.value != obj.formcount.value){
				alert('문항만들기를 먼저 수행하세요!');
				return;
			}
			if(obj.formtitle.value.trim() == ""){
					alert("1번 문항의 제목을 입력하세요.");
					obj.formtitle.focus();
					return;
			}
		}	else {
			if(obj.formseq.length != obj.formcount.value){
				alert('문항만들기를 먼저 수행하세요!');
				return;
			}
			for(var i=0;i<obj.formtitle.length;i++){
				if(obj.formtitle[i].value.trim() == ""){
					alert(i+1+"번 문항의 제목을 입력하세요.");
					obj.formtitle[i].value = "";
					obj.formtitle[i].focus();
					return;
				}
			}
		}		
	}
	
	frm.mode.value = type;
	frm.submit();
}

//완료시에 확인해야 할 사항
function check_grpcomplete(frm, type){
	var obj = document.forms[0];
	
	if(obj.title.value.trim() == "") {
		alert("제목을 입력하세요.");			
		obj.title.focus();
		return;
	}
	
	if ( obj.rchnolist.value.trim() == "" || obj.rchname.value.trim() == "" ) {
		if ( type != "temp" ) {
			alert('설문조사를 지정하세요.');
			return;
		}
	}
	
	frm.mode.value = type;
	frm.submit();
}

//삭제시에 확인해야 할 사항
function check_delete(frm, seq, delseq){
	if(confirm(seq+"번 문항을 삭제합니다")){
		frm.mode.value = "del";
		frm.delseq.value = delseq;
		frm.submit();
	}
}

//미리보기에 확인해야 할 사항
function check_preview(frm){	
	var obj = frm;	

	if(obj.formcount.value < 1){		
		alert('문항만들기를 먼저 수행하세요!');
		return;
	}

	if(typeof(obj.formseq) == 'undefined'){
		if(obj.formcount.value > 0){		
			alert('문항만들기 버튼을 누르세요!');
			return;
		}
	} else {
		if(typeof(obj.formseq.value) != 'undefined'){
			if(obj.formseq.value != obj.formcount.value){
				alert('문항만들기를 먼저 수행하세요!');
				return;
			}
			if(obj.formtitle.value.trim() == ""){
					alert("1번 문항의 제목을 입력하세요.");
					obj.formtitle.focus();
					return;
			}
		}	else {
			if(obj.formseq.length != obj.formcount.value){
				alert('문항만들기를 먼저 수행하세요!');
				return;
			}
			for(var i=0;i<obj.formtitle.length;i++){
				if(obj.formtitle[i].value.trim() == ""){
					alert(i+1+"번 문항의 제목을 입력하세요.");
					obj.formtitle[i].value = "";
					obj.formtitle[i].focus();
					return;
				}
			}
		}		
	}

	frm.target = "actionFrame";
	frm.mode.value = "prev";				
	frm.submit();		
	frm.target = "";
}

//미리보기에 확인해야 할 사항
function check_grppreview(frm){	
	var obj = frm;	

	if ( obj.rchnolist.value.trim() == "" || obj.rchname.value.trim() == "" ) {		
		alert('설문조사를 지정하세요.');
		return;
	}

	frm.target = "actionFrame";
	frm.mode.value = "prev";				
	frm.submit();		
	frm.target = "";
}

//마감처리
function close_check(seq, range){
	if(confirm('설문을 마감처리 하시겠습니까?')){
		document.location.href="/researchClose.do?rchno="+seq+"&range="+range;
	}
}

//마감처리
function close_grpcheck(seq, range){
	if(confirm('설문을 마감처리 하시겠습니까?')){
		document.location.href="/researchGrpClose.do?rchgrpno="+seq+"&range="+range;
	}
}

function cancel() {
	if(confirm("취소하시겠습니까?")){
		golist();
	}else{
		return;
	}
}

function golist() {
		location.href = "/researchMyList.do";
}

function gogrplist() {
		location.href = "/researchGrpMyList.do";
}

//검색 버튼 클릭시
function getSearch(frm, isSysMgr){
  if(isSysMgr == '001'){
		if(frm.sch_deptnm.value == '' && frm.sch_usernm.value == '') frm.initentry.value = "first";
		else	frm.initentry.value = "second";
	}else{
		frm.initentry.value = "first";
	}
	frm.submit();
}

//조직도 페이지 호출시
var formationObj;
function fTree(frm, orggbn, orgid){
	if(orggbn == 'null') orggbn = "";
	if(orgid == 'null')  orgid = "";
	
	var url  = '/formation/formation.jsp?orggbn=' + orggbn + '&orgid=' + orgid;
	formationObj = window.open(url, "popup", "width=390,height=700");
}

//조직도에서 넘어오는 데이터 : 조직도 더블클릭시 호출 메소드 
function fTreeDblClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn) {
	if(formationObj != null) formationObj.close();
	if(grpgbn == 2){
		document.forms[0].sch_old_deptcd.value = orgid;
		document.forms[0].sch_deptnm.value 		 = orgfullnm;
		document.forms[0].sch_old_userid.value = "";
		document.forms[0].sch_usernm.value 		 = "";
	}else{
		document.forms[0].sch_old_deptcd.value = upper_orgid;
		document.forms[0].sch_deptnm.value 	 	 = upper_orgfullnm;
		document.forms[0].sch_old_userid.value = orgid;
		document.forms[0].sch_usernm.value 		 = orgnm;
	}
	getSearch(document.forms[0], '001');
}