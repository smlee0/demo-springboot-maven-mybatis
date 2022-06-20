
var $sampleBoardReg = {
	
	validItems : {'#frmTitle':'제목을 입력하세요'},
	
	setViewData : function(){
		$("#f-sch-sel option:contains(" + $("#category").val() + ")").prop("selected", true);
	},
	
	/**
	 * 초기화
	 */
	init : function() {
		$('#openDt').datepicker('setDate', 'now');
		this.fnAddEventListener();
	},

	fnAddEventListener : function () {

		$('#btnCancel').click(function(){
			$sampleBoardReg.fnGoIndex();
		});
		
		$('#btnReg').click(function(e) {
			if ( $("#btnReg").text().trim() == '수정' ) {
				$sampleBoardReg.updData();
			} else {
				$sampleBoardReg.regData();
			}
		});

		$('#fileAddBtn').click(function(){
			$sampleBoardReg.fileAddBtn();
		});

		$('#fileCancelBtn').click(function(){
			$sampleBoardReg.fileCancelBtn();
		});

		$('[id^=fileDelBtn]').click(function(){
			$sampleBoardReg.fileDel($(this));
		});

		$('input[name="frmFile[]"]').off().on("change", function(){
			if (this.files && this.files[0]) {
				var maxSize = 10 * 1024 * 1024;
				var fileSize = this.files[0].size;
				if(fileSize > maxSize){
					alert("첨부파일 사이즈는 10MB 이내로 등록 가능합니다.");
					// $(this).val('');
					return false;
				}
			}
		});

 	},
	

	/**
	 * 파일 업로드 및 저장
	 */
	regData : function() {
		let targetUrl;
		if( $commUtil.fnJsValidation( $sampleBoardReg.validItems) ){
			
			$commUtil.fnUpdateContent("frmCntnt");
			
			if( !$("#frmTitle").val() ) {
				alert("제목을 입력하세요.");
				$("#frmTitle").focus();
				return false;
			}
			
			$("#frmTitle").val($("#frmTitle").val().trim())
			
			if( $("#frmTitle").val().length > 200 ) {
				alert("제목 길이가 200자를 초과 하였습니다.");
				$("#frmTitle").val('');
				$("#frmTitle").focus();
				return false;
			}
			
			if( $commUtil.fnCheckBoolContent("frmCntnt") ) {
				alert("내용을 입력하세요.");
				CKEDITOR.instances["frmCntnt"].focus();
				return false;
			}

			for(var i=0; i<$('input[name="frmFile[]"]').length; i++ ) {
				if ($('input[name="frmFile[]"]').eq(i).val() != null && $('input[name="frmFile[]"]').eq(i).val() != '') {
					let fileReg = /(.*?)\.(gif|png|jpg|jpeg|doc|docx|xls|xlsx|hwp|pdf|ppt|pptx|zip|mp4)$/;
					if(!$('input[name="frmFile[]"]').eq(i).val().toLowerCase().match(fileReg)) {
						alert("지원하는 파일 확장자가 아닙니다.\n파일명:"+ $('input[name="frmFile[]"]').eq(i).val());

						return;
					}
				}
			}
			
			if($("#replyIdx").val() != null && $("#replyIdx").val() != '') {
				targetUrl = 'addReply.act';
			} else {
				targetUrl = './';
			}

			$commUtil.fnConfirmDialog('등록 하시겠습니까?').then(function(response){
				if(response.value){
					$('#frm').ajaxForm({
					    url: targetUrl,
					    enctype: 'multipart/form-data',
					    method: 'POST',
					    beforeSubmit: function(data,form,option){
					    	
					    },
					    // error : function(data){
					    // 	console.log(data);
					    // },
					    success: function(data) {
					    	if (data.success) {
						    	location.reload();
						    	alert(data.message);
								$sampleBoardReg.fnGoIndex();
					    	} else {
					    		alert(data.message);
					    	}
					    }
					});
					$('#frm').submit();
				}
			});
		}
	},
	
	
	updData : function () {
		if( $commUtil.fnJsValidation($sampleBoardReg.validItems) ){
			
			$commUtil.fnUpdateContent("frmCntnt");
			
			if( !$("#frmTitle").val() ) {
				alert("제목을 입력하세요.");
				$("#frmTitle").focus();
				return false;
			}
			
			$("#frmTitle").val($("#frmTitle").val().trim())
			
			if( $("#frmTitle").val().length > 200 ) {
				alert("제목 길이가 200자를 초과 하였습니다.");
				$("#frmTitle").val('');
				$("#frmTitle").focus();
				return false;
			}
			
			if( $commUtil.fnCheckBoolContent("frmCntnt") ) {
				alert("내용을 입력하세요.");
				CKEDITOR.instances["frmCntnt"].focus();
				return false;
			}

			for(var i=0; i<$('input[name="frmFile[]"]').length; i++ ) {
				if ($('input[name="frmFile[]"]').eq(i).val() != null && $('input[name="frmFile[]"]').eq(i).val() != '') {
					// console.log($('input[name="frmFile[]"]')[i].value);
					let fileReg = /(.*?)\.(gif|png|jpg|jpeg|doc|docx|xls|xlsx|hwp|pdf|ppt|pptx|zip|mp4)$/;
					if(!$('input[name="frmFile[]"]').eq(i).val().toLowerCase().match(fileReg)) {
						alert("지원하는 파일 확장자가 아닙니다.\n파일명:"+ $('input[name="frmFile[]"]').eq(i).val());

						// if ( /(MSIE|Trident)/.test(navigator.userAgent) ) {
						// 	$('input[name="frmFile[]"]').eq(i).replaceWith( $('input[name="frmFile[]"]').eq(i).clone(true) );
						// 	$("#frmFile").parent().attr("data-filename", "")
						// } else {
						// 	$('input[name="frmFile[]"]').eq(i).val("");
						// 	$('input[name="frmFile[]"]').eq(i).parent().attr("data-filename", "")
						// }
						return;
					}
				}
			}

			// if ( $("#frmFile").val() != null && $("#frmFile").val() != '' ) {
			// 	var fileReg = /(.*?)\.(gif|png|jpg|jpeg|doc|docx|xls|xlsx|hwp|pdf|ppt|pptx|zip|mp4)$/;
			//   	if(!$("#frmFile").val().toLowerCase().match(fileReg)) {
			// 		alert("지원하는 파일 확장자가 아닙니다.");
			//
			// 		if ( /(MSIE|Trident)/.test(navigator.userAgent) ) {
			// 			$("#frmFile").replaceWith( $("#frmFile").clone(true) );
			// 			$("#frmFile").parent().attr("data-filename", "")
			// 		} else {
			// 		    $("#frmFile").val("");
			// 		    $("#frmFile").parent().attr("data-filename", "")
			// 		}
			// 		return;
			// 	}
			// }

			$commUtil.fnConfirmDialog('수정 하시겠습니까?').then(function(response){
				if(response.value){
					$('#frm').ajaxForm({
					    url: "./",
					    enctype: 'multipart/form-data',
					    method: 'PUT',
					    beforeSubmit: function(data,form,option){
					    	
					    },
					    // error : function(data){
					    // 	console.log(data);
					    // },
					    success: function(data) {
					    	if (data.success) {
						    	location.reload();
						    	alert("저장되었습니다.");
								$sampleBoardReg.fnGoIndex();
					    	} else {
					    		alert(data.message);
					    	}
					    }
					});
					$('#frm').submit();
				}
			});
		}
	},
	
	
	fnGoIndex : function (){
		ref.go("/manage/board/dataroom/list");
	},

	fileAddBtn : function (){
		let fCount = 5;
		let fCountTmp = $("#fCount").val();

		if(fCount < $("#useFile input[type=file]").length+1){return;}
		let html = '<p><label class="fm-file w-side" data-filename="">' +
						// '<input id="frmFile' + fCountTmp +'" type="file" multiple="multiple" name="file" />' +
						'<input name="frmFile[]" type="file" />' +
						'<span class="btn">파일 선택</span>' +
					'</label>' +
					'&nbsp;<button type="button" class="btn-t c2" id="fileCancelBtn" onclick="$sampleBoardReg.fileCancelBtn1(this)">제거</button></p>';
		$("#file-add-list").append(html);

		fCountTmp = parseInt($("#fCount").val()) + 1;
		$("#fCount").val(fCountTmp);
	},

	fileCancelBtn : function (){
		let fCountTmp = $("#fCount").val();

		if($("#useFile input[type=file]").length > 1){
			$("#file-add-list > label:last").remove();
			fCountTmp = parseInt($("#fCount").val()) - 1;
			$("#fCount").val(fCountTmp);
		}
	},

	fileCancelBtn1 : function (data) {
		$(data).parent().remove();
	},

	fileDel : function (param){
		let no = param.val();
		$commUtil.fnConfirmDialog('삭제한 파일은 복원할 수 없습니다.\n삭제를 진행하시겠습니까?').then(function(response){
			if(response.value){
				$.ajax({
					type: 'DELETE',
					url: 'file/'+ no,
					success: function(data) {
						if (data.success) {
							param.parent().remove();
							alert(data.message);
						} else {
							alert(data.message);
						}
					},
					// error: function(request, status, error) {
					// 	console.log('code:' + request.status + '\n' + 'error:' + error);
					// }
				});
			}
		});
	}
	
};

$(function(){
	CKEDITOR.replace( 'frmCntnt' , {
		width:'100%',
		height:'200px',
		filebrowserImageUploadUrl: '/file/editorImageUpload?a=1'
	});
	CKEDITOR.on('dialogDefinition', function( ev ){
		var dialogName = ev.data.name;
		var dialogDefinition = ev.data.definition;
	
		switch (dialogName) {
			case 'image': //Image Properties dialog
				//dialogDefinition.removeContents('info');
				dialogDefinition.removeContents('Link');
				dialogDefinition.removeContents('advanced');
				break;
		}
	});
	
	if( $("#frmTitle").val() != null && $("#frmTitle").val() !='' ){
		$sampleBoardReg.setViewData();
	}

	$sampleBoardReg.init();
});

