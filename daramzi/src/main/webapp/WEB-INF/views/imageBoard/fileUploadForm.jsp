<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0"/>
<link rel="stylesheet" href="/resources/css/imageBoard/style.css"/>
<script language="javascript" type="text/javascript" src="/resources/js/imageBoard/multiupload.js"></script>

<!-- 폴더생성 -->
<input type="text" size="16" name="folderName" id="mkDirNm"> 
<input type="button" class="btn btn-info" value="폴더생성" onClick="go_mkDir(${authInfo.name})">

<!-- 폴더삭제 -->
<input type="text" size="16" name="folderName" id="delDirNm"> 
<input type="button" value="폴더삭제" class="btn btn-danger" onClick="go_delDir(${authInfo.name})">

<form id="uploadForm">
	<div id="folderView">
	</div>
	<div id="form">
	    <input type="file" name="file" id="filename" multiple="multiple" accept=".jpg,.jpeg,.png,.gif,.bmp">
	    <input type="button" name="submitBtn" class="btn btn-success" value="사진올리기">
	</div>
</form>
<!-- 해당 위치 안에 이미지가 쌓이게 된다. -->
<div id="preview"></div>



			