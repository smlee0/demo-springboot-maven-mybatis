// 해당 js는 jquery-3.4.1.js, jquery.cookie.js가 필요합니다.

const maxRecentViewedFundCnt = 10; //최근 본 펀드 최대 개수
const maxCartCnt = 10; //장바구니 최대 개수
const maxFundCalcCnt = 10; //펀드계산기
const maxFundCompareCnt = 10; //펀드비교
const maxSimulateFundCnt = 10; //모의투자
const maxMyPortfolioCnt = 10; //포트폴리오

function _getCookie(cookieName) {

	if ($.cookie(cookieName) === undefined || $.cookie(cookieName) == "") {
		var _cookie = [];
	} else {
		var _cookie = JSON.parse($.cookie(cookieName));
	}

	return _cookie;
}

function _setCookie(cookieName, cookieValue, maxCookie) {
	var _cookie = new Array();
	if ($.cookie(cookieName) === undefined || $.cookie(cookieName) == "") {
		_cookie.unshift(cookieValue);
	} else {
		_cookie = JSON.parse($.cookie(cookieName));
		if ($.inArray(cookieValue, _cookie) == -1) {
			if (_cookie.length == maxCookie) {
				_cookie = _cookie.slice(0,maxCookie-1);
			}
			_cookie.unshift(cookieValue);
		} else {
			_cookie.unshift(cookieValue);
			_cookie = Array.from(new Set(_cookie));
		}
	}

	$.cookie(cookieName, JSON.stringify(_cookie), { expires: 1, path: '/'});
}


function _removeCookie(cookieName, cookieValue) {
	var _cookie = new Array();

	if (cookieValue == null || cookieValue == "") {
		$.removeCookie(cookieName);
	} else {
		_cookie = JSON.parse($.cookie(cookieName));

		var searchIdx = $.inArray(cookieValue, _cookie);
		if (searchIdx != -1) {
			_cookie.splice(searchIdx, 1);
		}
	}
	$.cookie(cookieName, JSON.stringify(_cookie), { expires: 1, path: '/' });
}

/**
 * 최근 본 펀드 쿠키 조회
 * @returns array
 */
function getRecentViewedFund() {
	return this._getCookie("recentViewedFund");
}

/**
 * 최근 본 펀드 쿠키 설정
 * @returns
 */
function setRecentViewedFund(fundCd,fundNm) {
	
	var viewList = getRecentViewedFund()
	
	if( viewList.length > 0 ){
		for(var i=0; i<viewList.length; i++) {	
			var _fcd = viewList[i].fcd;
			if( fundCd == _fcd){
				return;
			}
		}
	}
	
	this._setCookie("recentViewedFund", {fcd:fundCd,fnm:fundNm}, maxRecentViewedFundCnt);
	gnbRecentFundView();
}

/**
 * 장바구니 쿠키 조회
 * @returns array
 */
function getCart() {
	return this._getCookie("cart");
}

/**
 * 장바구니 쿠키 설정
 * @returns
 */
function setCart(fundCd) {
	
	if( $("#U_IDN").val() == ""){
		ui.toastMsg('장바구니에 담겼습니다.', $('.header .user'));
		this._setCookie("cart", fundCd, maxCartCnt);
		gnbCartCountView();
	}else{
		$.ajax({
	        url         :   "/setUserMyCart.do",
	        dataType    :   "json",
	        type        :   "post",
	        data        :   {fundCd:fundCd},
	        success     :   function(response){
	        	
	        	if( response.success ){
	        		
	        		$("#U_FCD").val(response.data.fcd);
	        		gnbCartCountView();
	        		ui.toastMsg('장바구니에 담겼습니다.', $('.header .user'));
            	}else{
            		console.log( "error ");
            	}
	        	
	        },
	        error       :   function(request, status, error){
	            console.log("AJAX_ERROR");
	            console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	        }
	    });
	}
	
}

/**
 * 장바구니 쿠키 삭제
 * @returns
 */
function removeCart(fundCd) {
	
	if( $("#U_IDN").val() == ""){
		ui.toastMsg('장바구니에서 빠졌습니다.', $('.header .user'));
		this._removeCookie("cart", fundCd);
		gnbCartCountView();
	}else{
		$.ajax({
	        url         :   "/removeUserMyCart.do",
	        dataType    :   "json",
	        type        :   "post",
	        data        :   {fundCd:fundCd},
	        success     :   function(response){
	        	if( response.success ){
	        		
	        		$("#U_FCD").val(response.data.fcd);
	        		gnbCartCountView();
	        		ui.toastMsg('장바구니에서 빠졌습니다.', $('.header .user'));
            	}else{
            		console.log( "error ");
            	}
	        	
	        },
	        error       :   function(request, status, error){
	            console.log("AJAX_ERROR");
	            console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	        }
	    });
	}
	
	
	
	//gnbCartCountView();
}

/**
 * 장바구니 쿠키 모두 삭제
 * @returns
 */
function removeAllCart() {
	this._removeCookie("cart", "");
	gnbCartCountView();
}



/**
 * 펀드계산기 쿠키 조회
 * @returns array
 */
function getFundCalc() {
	return this._getCookie("fundcalc");
}

/**
 * 펀드계산기 쿠키 설정
 * @returns
 */
function setFundCalc(idn) {
	this._setCookie("fundcalc", idn, maxFundCalcCnt);
}

/**
 * 펀드계산기 쿠키 삭제
 * @returns
 */
function removeFundCalc(idn) {
	this._removeCookie("fundcalc", idn);
}

/**
 * 펀드계산기 쿠키 모두 삭제
 * @returns
 */
function removeAllFundCalc() {
	this._removeCookie("fundcalc", "");
}


/**
 * 펀드포트폴리오 쿠키 조회
 * @returns array
 */
function getMyPortfolio() {
	return this._getCookie("myportfolio");
}

/**
 * 펀드포트폴리오 쿠키 설정
 * @returns
 */
function setMyPortfolio(idn) {
	this._setCookie("myportfolio", idn, maxMyPortfolioCnt);
}

/**
 * 펀드포트폴리오 쿠키 삭제
 * @returns
 */
function removeMyPortfolio(idn) {
	this._removeCookie("myportfolio", idn);
}

/**
 * 펀드포트폴리오 쿠키 모두 삭제
 * @returns
 */
function removeAllMyPortfolio() {
	this._removeCookie("myportfolio", "");
}




/**
 * 모의투자 쿠키 조회
 * @returns array
 */
function getMySimulateFund() {
	return this._getCookie("mysimulate");
}

/**
 * 모의투자 쿠키 설정
 * @returns
 */
function setMySimulateFund(idn) {
	this._setCookie("mysimulate", idn, maxSimulateFundCnt);
}

/**
 * 모의투자 쿠키 삭제
 * @returns
 */
function removeMySimulateFund(idn) {
	this._removeCookie("mysimulate", idn);
}

/**
 * 모의투자 쿠키 모두 삭제
 * @returns
 */
function removeAllMySimulateFund() {
	this._removeCookie("mysimulate", "");
}




/**
 * 펀드비교 쿠키 조회
 * @returns array
 */
function getMyfundcompare() {
	return this._getCookie("myfundcompare");
}


/**
 * 펀드비교 쿠키 설정
 * @returns
 */
function setMyFundCompare(idn) {
	this._setCookie("myfundcompare", idn, maxFundCompareCnt);
}

/**
 * 펀드비교 쿠키 삭제
 * @returns
 */
function removeMyFundCompare(idn) {
	this._removeCookie("myfundcompare", idn);
}

/**
 * 펀드비교 쿠키 모두 삭제
 * @returns
 */
function removeAllMyFundCompare() {
	this._removeCookie("myfundcompare", "");
}





