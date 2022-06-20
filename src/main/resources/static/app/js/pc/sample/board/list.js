
var $sampleBoardView = {

    /**
     * 초기화
     */
    init : function() {
        this.fnAddEventListener();
    },

    /**
     * 이벤트 등록
     */
    fnAddEventListener : function() {
	
		$("#btnSearch").click(function(){
            $sampleBoardView.setForm();
	    });

		$('.paging a').off("click").on("click", function(e) {
			e.preventDefault();
			$('#listFrm #listCurrentPageNo').val(this.rel);
			$sampleBoardView.setForm();
		});

		$("#addBtn").click(function() {
			$sampleBoardView.fnAdd();
		});

		$("#searchKeyword").keydown(function(key) {
			if (key.keyCode == 13) {
				$sampleBoardView.setForm();
			}
		});

		$("#searchKeyword").val($('#listSearchTxt').val());
		$("#searchType").val($('#listSearchType').val());
		
    },

    /**
     * 리스트 폼 세팅
     */
    setForm : function () {
        var searchFrm = $('#listFrm');
        //searchFrm.find('#list-ctgrySearch').val( $('#ctgry-tab a.on').attr('rel') );
        searchFrm.find('#listSearchTxt').val( $("#searchKeyword").val() );
        searchFrm.find('#listSearchType').val( $("#searchType").val() );
        location.href="list?" + searchFrm.serialize();
    },

    /**
     * 게시글 등록하기
     */
    fnAdd : function() {
        location.href = 'reg';
    }

};

$(function(){
    $sampleBoardView.init();
});