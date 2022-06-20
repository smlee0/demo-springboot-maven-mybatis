
var $sampleBoardView = {
	
	/**
	 * 초기화
	 */
	init : function () {
		this.fnAddEventListener();
	},

	fnAddEventListener : function () {
		
		$('#listBtn').click( function() {
			$sampleBoardView.fnList();
		});
		
		$('#setBtn').click( function() {
			$sampleBoardView.fnSetData($("#thisSeq").val());
		});
		
		$('#delBtn').click( function() {
			$sampleBoardView.fnDelData($("#thisSeq").val());
		});
		
 	},
	
	
	fnList : function (){
		location.href = "/sample/board/list";
	},
	
	fnDelData : function ( delNo ){
		var json = new Object();
	    json.no = delNo;
	    
	    var array = new Array();
		array.push(json);
		
		$commUtil.fnConfirmDialog('삭제한 글은 복원할 수 없습니다.\n삭제를 진행하시겠습니까?').then(function(response){
			if(response.value){
				$.ajax({
					type: 'DELETE',
				    url: '../',
				    contentType: 'application/json',
				    data: JSON.stringify(array),
				    dataType: 'json',
				    
				    success: function(data) {
				    	if (data.success) {
							$sampleBoardView.fnGoIndex();
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
	},
	
	fnSetData : function (seq) {
		location.href = "/sample/board/edit/" + seq;
	},
	
	
	
	
	createFrm : function (method, name, action, obj) {
		var form = document.createElement("form");
		form.setAttribute("charset", "UTF-8");
		form.setAttribute("method", method);
		form.setAttribute("name", name);
		form.setAttribute("action", action);
		
		obj.forEach(function(elem) {
			var hiddenField = document.createElement("input");
			hiddenField.setAttribute("type", "hidden");
			hiddenField.setAttribute("name", elem.name);
			hiddenField.setAttribute("value", elem.value);
			form.appendChild(hiddenField);
		});
		document.body.appendChild(form);
	}

	
};	
	
$(function(){
	$sampleBoardView.init();
});