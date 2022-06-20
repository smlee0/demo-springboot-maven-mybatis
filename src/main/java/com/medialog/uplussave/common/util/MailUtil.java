package com.medialog.uplussave.common.util;//package medialog.mhp.common.utils;
//
//import com.namandnam.nni.common.vo.AttachFiles;
//import com.namandnam.nni.common.vo.NewConsult;
//import com.namandnam.nni.common.web.Const;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.mail.Message;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Properties;
//import java.util.stream.Collectors;
//
//@Component
//public class MailUtil {
//    @Value("${spring.mail.host}")
//    private String mailHost;
//
//    @Value("${spring.mail.port}")
//    private String mailport;
//
//    public void mailSend(String sender, String receiver, String pwd, String title, String content) throws Exception {
//
//        //TODO mailHost / mailport의 값이 없을 때 익셉션 처리 필요
//
//        Properties prop = new Properties();
//		prop.put("mail.smtp.host", mailHost);
//        prop.put("mail.smtp.port", mailport);
//        prop.put("mail.smtp.auth", "true");
//        prop.put("mail.smtp.starttls.enable", "true"); //TLS
//
//        Session session = Session.getInstance(prop,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(sender, pwd);
//                    }
//                });
//
//
//        Message message = new MimeMessage(session);
//        message.setFrom(new InternetAddress(sender));
//        message.setRecipients(
//                Message.RecipientType.TO,
//                InternetAddress.parse(receiver)
//        );
//        message.setSubject(title);
//        //message.setText(content);
//        message.setContent(content,"text/html; charset=utf-8"); //HTML 적용된 메일을 보내기 위한 설정
//
//        Transport.send(message);
//
//    }
//
//    /**
//     * 메일 본문 HTML 만드는 메소드
//     * @param contentData
//     * @return
//     */
//    public static String htmlMailMake(Result contentData){
//        //메일 본문 HTML뼈대 데이터 가져오기
//        String resultcontent = htmlFileReader("templates/mail/question/question-mail.html");
//
//        //rootPath 경로
//        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//        String rootPath = String.valueOf(attr.getRequest().getRequestURL()).replace(attr.getRequest().getRequestURI(),"");
//
//        //FaQ 데이터를 HTML뼈대에 입히기 위한 데이터 저장 Map
//        HashMap<String, String> replaceData = new HashMap<>();
//
//        //NewConsultService.class 의 selectOne 메소드에서 데이터를 input한 key값대로 데이터를 가져옴
//        Object itemObj = contentData.getData().get(Constants.KEY_ITEM);
//        //reflect getDeclareFields로 접근지정 여부와 관계 없이 전체 필드 목록을 가져옴
//        Arrays.stream(itemObj.getClass().getDeclaredFields())
//                .forEach(field -> {
//                    try {
//                        String fieldName = field.getName();
//                        String getter = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length()); //접근지정을 준수하여 데이터를 가져오기 위해 getter 메소드 호출
//                        replaceData.put(fieldName,String.valueOf(itemObj.getClass().getMethod(getter).invoke(itemObj)));    //필드 이름을 key값으로 데이터를 map에 저장
//                    } catch (IllegalAccessException|NoSuchMethodException|InvocationTargetException e) { //getter 메소드를 가져올수 없거나 접근할 수 없을 때 예외
//                        e.printStackTrace();
//                    }
//                });
//
//        //Map에 null 문자열로 들어간 값들을 공백으로 변경
//        replaceData.keySet().stream().filter(key ->replaceData.get(key).equals("null"))
//                .forEach(key -> replaceData.put(key ,""));
//
//        //HTML 뼈대에 key값들을 데이터로 replace
//        resultcontent = replaceData.keySet().stream()
//                .reduce(resultcontent,(content, key)-> content.replace("${"+key+"}",replaceData.get(key)));
//
//        //관련 URL 부분 가져오기 위한 로직
//        NewConsult consult = (NewConsult) itemObj;
//        String linkContent = "";
//        String linkUrl = consult.getLinkUrl();
//        //데이터가 있을 경우 추가로 뼈대를 불러와 입력
//        if(linkUrl != null && !"".equals(linkUrl)){
//            linkContent = htmlFileReader("templates/mail/question/question-url.html");
//        }else{linkUrl = "";}
//
//        resultcontent = resultcontent.replace("${question-url}",linkContent.replace("${linkUrl}",linkUrl));
//
//        //첨부파일 부분 가져오기 위한 로직, 첨부파일은 다수를 가지고 있을 수 있으므로 URL과 로직이 상이함
//        List<AttachFiles> selectFilesList = (List<AttachFiles>) contentData.getData().get(Constants.KEY_FILE_LIST);
//        String downloadContent = "";
//        //파일이 하나라도 있을 경우 다운로드 링크를 만들어 넣어줌
//        if(selectFilesList.size()>0) {
//            String replaceDownloadURL = selectFilesList.stream().map(file -> {
//                try {
//
//                    return  "<a href=\""+rootPath+"/file/encInfo?encData=" + Encrypt.encryptAES256(file.getNo()) + "\" style=\"padding:0 26px 0 0;margin:0;font-size:14px;line-height:21px;color:#25867d;background:url('http://dev.reflection.co.kr/nam/assets/images/email/ico_file_download_b.png') right 0 no-repeat; background-size:20px 20px;\">"
//                            +file.getFilenameOrg()+"</a><br/>";
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return "";
//            }).collect(Collectors.joining());
//
//            downloadContent = htmlFileReader("templates/mail/question/question-file.html").replace("${fileList}",replaceDownloadURL);
//        }
//        resultcontent = resultcontent.replace("${question-file}", downloadContent);
//
//        //어드민 페이지 이동을 위한 링크
//        resultcontent = resultcontent.replace("${adminPage}", rootPath+"/manage");
//
//        return resultcontent;
//    }
//
//    /**
//     * HTML 뼈대를 가져오기 위한 메소드
//     * 인자로 넘어온 경로에서 파일을 불러와 String으로 반환함
//     * @param pathStr
//     * @return
//     */
//    static String htmlFileReader(String pathStr){
//        ClassPathResource resource = new ClassPathResource(pathStr);
//        String resultcontent = "";
//        try {
//            Path path = Paths.get(resource.getURI());
//            List<String> content = Files.readAllLines(path);
//            resultcontent = content.stream().collect(Collectors.joining());
//        } catch (IOException e) {
//        }
//        return resultcontent;
//    }
//}