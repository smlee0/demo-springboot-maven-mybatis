package com.medialog.uplussave.common.util.legacy;

import java.util.Arrays;

public class MaskUtil {

	/**
	 *  Desc : 전화번호 마스킹 처리 (010-1234-5678 -> 010-**34-**78)
	 * @param prodNo
	 * @return
	 */
	public static String phoneMasking(String ctn) {
		StringBuffer sdf = new StringBuffer();
		sdf.append(ctn);

		if(ctn.length() == 13) {
			sdf.replace(4, 6, "**");
			sdf.replace(9, 11, "**");
		}
		return sdf.toString();
	}

	/**
	 *  Desc : 연락처 마스킹 처리 및 하이픈 추가
	 * @param prodNo
	 * @return
	 */
	public static String telNoMasking(String telNo) {
		StringBuffer sdf = new StringBuffer();
		sdf.append(telNo);

		if(sdf.length() == 11) { // 3-4-4
			sdf.replace(3, 5, "**");
			sdf.replace(7, 9, "**");
			sdf.insert(7, "-");
			sdf.insert(3, "-");
		}else if(sdf.length() == 10) { // 2-4-4
			if(sdf.substring(0, 2).equals("02")) {
				sdf.replace(2, 4, "**");
				sdf.replace(6, 8, "**");
				sdf.insert(6, "-");
				sdf.insert(2, "-");
			}else {	// 3-3-4
				sdf.replace(3, 4, "*");
				sdf.replace(6, 8, "**");
				sdf.insert(6, "-");
				sdf.insert(3, "-");
			}
		}else if(sdf.length() == 9) { // 2-3-4
			sdf.replace(2, 3, "*");
			sdf.replace(5, 7, "**");
			sdf.insert(5, "-");
			sdf.insert(2, "-");
		}else if(sdf.length() == 8) {	// 4-4
			sdf.replace(0, 2, "**");
			sdf.replace(4, 6, "**");
			sdf.insert(4, "-");
		}else if(sdf.length() == 7) {	// 3-4
			sdf.replace(0, 1, "*");
			sdf.replace(3, 5, "**");
			sdf.insert(3, "-");
		}

		return sdf.toString();
	}

	/**
	 * 계좌변호를 마스킹한다. (예:1234******1234)
	 * @param account
	 * @return
	 */
	public static String accountMasking(String account) {
		if(account == null) {
            return null;
        }

		int accountLength = account.length();
		if(accountLength <= 8) {
            return account;
        }

		StringBuilder nAccount = new StringBuilder();

		nAccount.append(account.substring(0,4));

		int max = accountLength - 8;
		for(int i=0; i<max; i++) {
            nAccount.append("*");
        }

		nAccount.append(account.substring(accountLength-4));

		return nAccount.toString();
	}

	/**
	 * 카드번호를 마스킹한다. (예:1234-****-****-1234)
	 * @param card
	 * @return
	 */
	public static String cardMasking(String card) {
		if(card == null) {
            return null;
        }

		String fmtCard = CmmnUtil.getCardFormat(card);
		StringBuilder nCard = new StringBuilder();

		if(fmtCard.indexOf("-") > 0) {
			String[] splitCard = fmtCard.split("-");

			int cardLength = splitCard.length;
			if(cardLength <= 2) {
                return card;
            }

			nCard.append(splitCard[0]).append("-");

			for(int i=1; i<cardLength-1; i++) {
				for(int j=0; j<splitCard[i].length(); j++) {
					nCard.append("*");
				}
				nCard.append("-");
			}

			nCard.append(splitCard[cardLength-1]);
		} else {
			int cardLength = card.length();
			if(cardLength <= 8) {
                return card;
            }

			nCard.append(card.substring(0,4));

			int max = cardLength - 8;
			for(int i=0; i<max; i++) {
				nCard.append("*");
			}

			nCard.append(card.substring(cardLength-4));
		}
		return nCard.toString();
	}

	/**
	 * 체크카드번호를 마스킹한다. (예:123*-****-****-1234)
	 * @param checkCard
	 * @return
	 */
	public String checkCardMasking(String card) {
		if(card == null) {
            return null;
        }

		String fmtCard = CmmnUtil.getCardFormat(card);
		StringBuilder nCard = new StringBuilder();

		if(fmtCard.indexOf("-") > 0) {
			return new StringBuilder().append(fmtCard.substring(0,3)).append("*").append("-").append("****").append("-").append("****").append("-").append(fmtCard.substring(15,19)).toString();

		}
		return nCard.toString();
	}

	/**
	 * 단말기 일련번호를 마스킹한다. (예:********1234)
	 * @param serial
	 * @return
	 */
	public String deviceSerialMasking(String serial) {
		if(serial == null) {
            return null;
        }

		int serialLength = serial.length();
		if(serialLength <= 3) {
            return serial;
        }

		StringBuilder nSerial = new StringBuilder();

		int max = serialLength - 4;
		for(int i=0; i<max; i++) {
			nSerial.append("*");
		}

		nSerial.append(serial.substring(serialLength-4));
		return nSerial.toString();
	}

	/**
	 *  Desc : ucube 응답 parameter인 prodNo(12자리)를 화면출력용 핸드폰번호 형태로 변환 (마스킹처리 안함) 2014/10/24 ok84j
	 * @param prodNo
	 * @return
	 */
	public static String convertProdNoToNonMaskingCtn(String prodNo) {
		String ctn = new String();
		ctn = prodNo.substring(0, 3)+"-"+prodNo.substring(4, 8)+"-"+prodNo.substring(8, 12); // xxx0xxxxxxxx -> xxx-xxxx-xxxx 포맷으로 변환

		return ctn;
	}

	/**
	 * 이름을 마스킹처리한다
	 * 이름 5자리 이상 : 홍***동(성+마지막 자리 이름)
	 * 이름 4자리 : 홍**동
	 * 이름 3자 : 홍*동
	 * 이름 2자 : 홍*
	 * @param name - String
	 * @return String
	 */
	public static String maskNameEtc(String name) {
		String maskedName = "";

		if (!"".equals(name)) {
			int num = name.length();
			if (num == 2) {
				maskedName = name.substring(0, 1) + "*";

			}else if (num > 2) {

				String first = name.substring(0, 1);
				String last = name.substring(name.length()-1);

				char[] c = new char[name.length() - 2];
				Arrays.fill(c, '*');

				maskedName = first + String.valueOf(c) + last;
			}
		}

		return maskedName;
	}

	/**
	 *  Desc : 이메일 주소 마스킹 ex)abcd**@ naver.com
	 * @param email
	 * @return
	 */
	public static String emailMasking(String email) {
		String emailResult = "";
		if (!"".equals(email)) {
			String[] tempEmail = email.split("@");
			if(tempEmail[0].length() == 1){
				//1자리 메일 주소 일때
				emailResult = email;
			} else if(tempEmail[0].length() == 2){
				//2자리 메일 주소 일때
				emailResult = tempEmail[0].substring(0, tempEmail[0].length()-1);
				emailResult = emailResult + "*" + "@" + tempEmail[1];
			}else {
				//2자리 이상 메일 주소 일때
				//2018.11.01 *가입신청서 확인 페이지 진입 시 '유효기간 초과' 안내 화면 출력 오류 건 이영근
				//유심등록하기일 경우 이메일 입력하지 않은 케이스를 구분하기 위해 조건문 추가.
				if(tempEmail[0].length() > 0 ) {
					emailResult = tempEmail[0].substring(0, tempEmail[0].length()-2);
					emailResult = emailResult + "**" + "@" + tempEmail[1];
				}
			}
		}
		return emailResult;
	}
}
