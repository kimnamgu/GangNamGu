<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 목록</title>

<link rel = "stylesheet" type = "text/css" media = "screen" href = "/resources/jqgrid/css/jquery-ui-1.10.3.custom.min.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="/resources/jqgrid/css/ui.jqgrid.css" />

<script type="text/javascript" src="/resources/jqgrid/js/jquery-1.11.0.min.js" ></script>
<script type="text/javascript" src="/resources/jqgrid/js/i18n/grid.locale-kr.js"></script>
<script type="text/javascript" src="/resources/jqgrid/js/jquery.jqGrid.min.js" ></script>

<script language="javascript" type="text/javascript" src="/resources/js/imageBoard/imageBoard.js"></script>

<script>
$(function(){
	$("#myFileList").jqGrid({
	    url : '/myFileMng/myFileList',
	    datatype : "json",
        colNames : ['파일IDX','파일순번','파일명','파일확장자','파일원본명','파일크기','게시판IDX','ID','폴더경로','삭제YN','공개YN','다운YN','파일설명','입력일','수정일'],
        colModel : [
					{ name : 'fileIdx',		  	index : 'fileIdx',		  	 width : 40,   align : 'center' , key:true},
					{ name : 'fileSeq',		  	index : 'fileSeq',		  	 width : 40,   align : 'center' },
					{ name : 'fileName',		index : 'fileName',		     width : 40,   align : 'center' , editable:true},
					{ name : 'fileExtension',	index : 'fileExtension',     width : 40,   align : 'center' },
					{ name : 'fileRealName',	index : 'fileRealName',	     width : 100,  align : 'center' , formatter: imageFormatter },
					{ name : 'fileSize',		index : 'fileSize',		     width : 40,   align : 'center' },
					{ name : 'boardIdx',		index : 'boardIdx',		 	 width : 40,   align : 'center' },
					{ name : 'id',		  		index : 'id',		  	 	 width : 40,   align : 'center' },
					{ name : 'folderPath',		index : 'folderPath',		 width : 40,   align : 'center' },
					{ name : 'del_yn',		  	index : 'del_yn',		  	 width : 40,   align : 'center' , editable:true, edittype:"select",editoptions:{value:"Y:Y;N:N"}},
					{ name : 'open_yn',		  	index : 'open_yn',		  	 width : 40,   align : 'center' , editable:true, edittype:"select",editoptions:{value:"Y:Y;N:N"}},
					{ name : 'down_yn',		  	index : 'down_yn',		  	 width : 40,   align : 'center' , editable:true, edittype:"select",editoptions:{value:"Y:Y;N:N"}},
					{ name : 'fileDescription',	index : 'fileDescription',	 width : 40,   align : 'center' , editable:true },  
					{ name : 'ins_dt',		  	index : 'ins_dt',		  	 width : 60,   align : 'center' , formatter: 'date', formatoptions: { srcformat: 'U/1000', newformat:'Y-m-d' }},
					{ name : 'upt_dt',		  	index : 'upt_dt',		  	 width : 60,   align : 'center' , formatter: 'date', formatoptions: { srcformat: 'U/1000', newformat:'Y-m-d' }}
					],
		height: 480,
	    multiselect: true,
	    multiboxonly: true, // 체크 박스를 클릭해야 다중 선택 가능 
	    rownumbers: true, // 행번호 표시여부
	    rownumWidth: 30, // 행번호 열의 너비
	    shrinkToFit: false, /* RowWidth 고정 */

	    rowNum:10,
	    rowList:[10,20,30],
	    pager:'#pager',
	    sortname:'fileIdx',
	    viewrecords: true,
	    sortorder:"desc",
	    caption:"내 파일 목록",
	    jsonReader: {
	         repeatitems:false
	    }
	/*
	    cellEdit:true,
	    cellsubmit:'remote',
	    cellurl:'/myFileMng/myFileUpdate', //변경을 수행할 url
	    beforeSubmitCell : function(rowid, cellname, value) {   // submit 전 rowid 는 key:true로 설정한값
	    	alert("id :" + rowid + ",cellName:" + cellname + ",cellValue:" +value);
	          return {"id":rowid, "cellName":cellname, "cellValue":value}
	    },
	    afterSubmitCell : function(res) {    // 변경 후
	         var aResult = $.parseJSON(res.responseText);
	         var userMsg = "";
	         if((aResult.msg == "success")) {
	             userMsg = "데이터가 변경되었습니다.";
	         }

	         return [(aResult.msg == "success") ? true : false, userMsg];

	     }
	    */
	});
	
	$("#myFileList").jqGrid('navGrid','#pager',{edit:false,add:false,del:false});
	
	//선택행 edit
	$("#ed1").click( function() {
		
		var s;
		s = $("#myFileList").jqGrid('getGridParam','selarrrow'); //체크된 로우 정보 가져오기
		
		s.forEach(function(element){
			$("#myFileList").jqGrid('editRow',element);
		});

		this.disabled = 'true';
		$("#sved1,#cned1").attr("disabled",false);
	});
	
	//선택행 save
	$("#sved1").click( function() {

		var s;
		s = $("#myFileList").jqGrid('getGridParam','selarrrow');

		var resultMsg = ""; // 성공실패 확인
		
		s.forEach(function(element){
			$("#myFileList").jqGrid('saveRow',element);
			
			var myObj = $( "#myFileList" ).jqGrid('getRowData', element);
			console.log(JSON.stringify(myObj));
			
		    $.ajax({
		        type: "GET",
		        url : "/myFileMng/myFileUpdate",
		        data: myObj,
		        async: true,
		        success : function(res) {    // 변경 후
		        	//alert("데이터가 변경되었습니다.");
			     },
		        error: function(res) {    // 변경 후
		        	//alert("데이터가 변경을 실패하였습니다.");
			     }
		    });			
		});
		
		$("#sved1,#cned1").attr("disabled",true);
		$("#ed1").attr("disabled",false);
	});
	
	//선택행 초기화
	$("#cned1").click( function() {
		var s;
		s = $("#myFileList").jqGrid('getGridParam','selarrrow');
		
		s.forEach(function(element){

			//alert("element : " + element);
			$("#myFileList").jqGrid('restoreRow',element);

		});
		
		$("#sved1,#cned1").attr("disabled",true);
		$("#ed1").attr("disabled",false);
	});
	
	//행삭제
	$("#delFile").click( function() {
		var s;
		s = $("#myFileList").jqGrid('getGridParam','selarrrow');
		
		s.forEach(function(element){
		    $.ajax({
		        type: "GET",
		        url : "/myFileMng/myFileDelete",
		        data: { "id" : element },
		        async: true,
		        success : function(res) {    // 변경 후
		        	$("#myFileList").jqGrid('delRowData',element);
			     },
		        error: function(res) {    // 변경 후
		        	alert("데이터가 삭제 실패하였습니다.");
			     }
		    });			
		});
	});
	
	
	//이미지 표시
	function imageFormatter(cellvalue, options, rowObject)
	{
	     // rowObject  : JqGrid의 행 정보를 담고 있다.
	    console.log(rowObject);
	
		var imageView = '<a style="cursor:pointer;" onclick="javascript:viewPic(\'/resources/uploadImage/' + rowObject.folderPath + '/' + rowObject.fileRealName + '\')">';
			imageView+= '<img alt="" src="/resources/uploadImage/' + rowObject.folderPath + '/' + rowObject.fileRealName + '" style="width: 100px; height:auto;">';
			imageView+= '</a>';
		
	    return imageView;
	} 
	
});
</script>
</head>
 
<body>
<input type="BUTTON" style="margin-bottom:10px;" class="btn btn-primary" id="ed1" value="선택수정" />
<input type="BUTTON" style="margin-bottom:10px;"class="btn btn-primary" id="sved1" disabled='true' value="저장" />
<input type="BUTTON" style="margin-bottom:10px;"class="btn btn-primary" id="cned1" disabled='true' value="수정취소" />
<input type="BUTTON" style="margin-bottom:10px;"class="btn btn-danger" id="delFile" value="선택삭제" />
	<table id="myFileList"></table>
	<div id="pager"></div>
</body>
</html>

