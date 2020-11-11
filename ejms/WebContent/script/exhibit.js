/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 자료공유
 * 설명:
 */

/**
 * 반환값 저장 변수
 */
var result;
var returnpage;
var formkind;

function viewForm() {
	//양식종류,시스템문서번호,양식번호,추가매개변수
	var args = viewForm.arguments;
	var childURL = "";
	var parentURL = "/exhibit";
	var addQueryString = "";

	if (args[0] == "01")			{childURL = "/formatLineView.do"; addQueryString = "&includeNotSubmitData=true";}
	else if (args[0] == "02")	{childURL = "/formatFixedView.do"; addQueryString = "&includeNotSubmitData=true";}
	else if (args[0] == "03") {childURL = "/formatBookView.do"; addQueryString = "&includeNotSubmitData=true";}
	
	location.href = parentURL + childURL + "?formkind=" + args[0] + "&sysdocno=" + args[1] + "&formseq=" + args[2] + "&" + args[3] + addQueryString;
}

function getAbsoluteLeft(htmlObject) {
	var xPos = htmlObject.offsetLeft;
	var temp = htmlObject.offsetParent;
	while (temp != null) {
		xPos += temp.offsetLeft;
		temp = temp.offsetParent;
	}
	return xPos;
}

function getAbsoluteTop(htmlObject) {
	var yPos = htmlObject.offsetTop;
	var temp = htmlObject.offsetParent;
	while (temp != null) {
		yPos += temp.offsetTop;
		temp = temp.offsetParent;
	}
	return yPos;
}
	
function sort(order, column, startIdx) {
	var tblObj = document.getElementById("tbl");
	for(var i = startIdx; i < tblObj.rows.length - 1; i++) {
		for(var j = i + 1; j < tblObj.rows.length; j++) {
			if(order == "up") {
				if(tblObj.rows[i].cells[column].innerHTML < tblObj.rows[j].cells[column].innerHTML) {
					tblObj.moveRow(i, j);
					tblObj.moveRow(j - 1, i);
				}
			} else {
				if(tblObj.rows[i].cells[column].innerHTML > tblObj.rows[j].cells[column].innerHTML) {
					tblObj.moveRow(i, j);
					tblObj.moveRow(j - 1, i);
				}
			}
		}
	}
	
	if(order == "up") {
		event.srcElement.onclick = function() {sort('down', column, startIdx);};
	} else {
		event.srcElement.onclick = function() {sort('up', column, startIdx);};
	}
}
function commentView() {
	var divObj = document.getElementById("comment");
	var tblObj = document.getElementById("tbl");
	if(divObj.style.display == "none") {
		divObj.style.pixelLeft = getAbsoluteLeft(event.srcElement);
		divObj.style.pixelTop = getAbsoluteTop(event.srcElement) + event.srcElement.clientHeight;
		divObj.style.display = "block";
	} else {
		divObj.style.display = "none";
	}
}

function sch_Reset() {
	var objName = event.srcElement.name;
	var no = objName.substring(objName.length - 1);
	
	if ( parseInt(no) <= 6 ) {
		for ( var i = parseInt(no) + 1; i <= 6; i++ ) {
			try { eval("document.forms[0].sch_deptcd" + i + "[0].selected = true") } catch (e) {}
		}
		document.forms[0].sch_chrgunitcd[0].selected = true;
		document.forms[0].sch_inputusrid[0].selected = true;
	} else if ( objName == "sch_chrgunitcd" ) {
		document.forms[0].sch_inputusrid[0].selected = true;
	}
}

function closeCheck(sysdocno){	
	var cnf = window.confirm("공유해제하시겠습니까?");

	if(cnf) {
		location.href = "/exhibitClose.do?radiochk=2&sysdocno=" + sysdocno;
	}
}