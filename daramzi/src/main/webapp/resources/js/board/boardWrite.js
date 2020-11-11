var files = {};
var previewIndex = 0;

// image preview 기능 구현
// input = file object[]
function addPreview(input) {
	if (input[0].files) {
		//파일 선택이 여러개였을 시의 대응
		for (var fileIndex = 0; fileIndex < input[0].files.length; fileIndex++) {
			var file = input[0].files[fileIndex];
			if (validation(file.name))
				continue;
			setPreviewForm(file);
		}
	} else{
		alert('invalid file input'); // 첨부클릭 후 취소시의 대응책은 세우지 않았다.
	}
}

function setPreviewForm(file, img) {
	var reader = new FileReader();

	//div id="preview" 내에 동적코드추가.
	//이 부분을 수정해서 이미지 링크 외 파일명, 사이즈 등의 부가설명을 할 수 있을 것이다.
	reader.onload = function(img) {
		var imgNum = previewIndex++;
		
		var fileNameSplit = file.name.split('.');
		//파일명 : .으로 나눈 앞부분
		var fileName = fileNameSplit[0];
		//파일 확장자 : .으로 나눈 뒷부분
		var fileExt = fileNameSplit[1];

		
		$("#preview").append(
				"<div class=\"preview-box\" value=\"" + imgNum + "\">"
						+ "<img class=\"thumbnail\" src=\"" + img.target.result + "\"\/>"
						+ "<p>" + file.name + "</p>"
						+ "<a href=\"#\" value=\"" + imgNum + "\" onclick=\"deletePreview(this)\">" + "삭제" + "</a>"
						+ "</div>"
						
		);
		
		files[imgNum] = file;
	};

	reader.readAsDataURL(file);
}

//preview 영역에서 삭제 버튼 클릭시 해당 미리보기이미지 영역 삭제
function deletePreview(obj) {
	var imgNum = obj.attributes['value'].value;
	delete files[imgNum];
	$("#preview .preview-box[value=" + imgNum + "]").remove();
	resizeHeight();
}

//client-side validation
//always server-side validation required
function validation(fileName) {
	fileName = fileName + "";
	var fileNameExtensionIndex = fileName.lastIndexOf('.') + 1;
	var fileNameExtension = fileName.toLowerCase().substring(
			fileNameExtensionIndex, fileName.length);
	if (!((fileNameExtension === 'jpg') || (fileNameExtension === 'gif') || (fileNameExtension === 'png'))) {
		alert('jpg, gif, png 확장자만 업로드 가능합니다.');
		return true;
	} else {
		return false;
	}
}


//초기 js
$(document).ready(function() {
	// submit 등록. 실제로 submit type은 아니다.
	$('input[name=submitBtn]').on('click', function() {
		
		check_write();
		var form = $('#boardWrite')[0];
		var formData = new FormData(form);
		
		$.each(files, function(key, value){
		    formData.append('files', files[key]);
		});
		
		//ajax 통신으로 multipart form을 전송한다.
		$.ajax({
			type : 'POST',
			enctype : 'multipart/form-data',
			processData : false,
			contentType : false,
			cache : false,
			timeout : 600000,
			url : '/board/boardWrite',
			dataType : 'JSON',
			data : formData,
			success : function(result) {
				
				
				//이 부분을 수정해서 다양한 행동을 할 수 있으며,
				//여기서는 데이터를 전송받았다면 순수하게 OK 만을 보내기로 하였다.
				//-1 = 잘못된 확장자 업로드, -2 = 용량초과, 그외 = 성공(1)
				if (result === -1) {
					alert('jpg, gif, png, bmp 확장자만 업로드 가능합니다.');
					// 이후 동작 ...
				} else if (result === -2) {
					alert('파일이 100MB를 초과하였습니다.'); //임시값임 나중에 수정해야함
					// 이후 동작 ...
				} else {
					alert('글등록 성공');
					$('form[name="search"]').submit();
				}
			}
		//전송실패에대한 핸들링은 고려하지 않음
		});
	});
	// <input type=file> 태그 기능 구현
	$('#boardWrite input[type=file]').change(function() {
		addPreview($(this)); //preview form 추가하기
	});
	
});


function check_write() {
	if ($("#title").val() == "" || $("#title").val() == null) {
		alert("제목을 입력하세요.");
		$("#title").focus();
		return false;
	} else if ($("#content").val() == "" || $("#content").val() == null) {
		alert("내용을 입력하세요.");
		$("#content").focus();
		return false;
	} 
}

function check_modify() {
	if ($("#title").val() == "" || $("#title").val() == null) {
		alert("제목을 입력하세요.");
		$("#title").focus();
		return;
	} else if ($("#content").val() == "" || $("#content").val() == null) {
		alert("내용을 입력하세요.");
		$("#content").focus();
		return;
	} else {
		$("#modComp").submit();
	}
}

//사진삭제
function go_fileDel(fileIdx) {
	if(confirm("사진은 삭제시 복구 되지 않습니다. 삭제하시겠습니까?")==false){
		return false;    		
	}
	
	
    $.ajax({
        type: "post",
    url : "/board/fileDel",
    data: {
    	fileIdx : fileIdx
    },

    success:function (data) {//사진삭제 - 성공시
    	alert("파일을 삭제하였습니다.");
    	$("#" + fileIdx).empty();
    },

    error: function (data) {//사진삭제 - 실패시
    	alert("파일을 삭제하지 못했습니다.관리자에게 문의 바랍니다.");
        }

 	});
}