package com.petcab.work.smscheck;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SmsCheckController {
	
	@ResponseBody
   @RequestMapping(value = "/sendSms.do", method = RequestMethod.POST)
     public String sendSms(HttpServletRequest request) throws Exception {

       String api_key = "NCSK1JQYXW5BY5RP";
       String api_secret = "UQ3FWL6HJ69ZPOODDQHAQX7WMQMDNU9C";

       Coolsms coolsms = new Coolsms(api_key, api_secret);
       
       HashMap<String, String> set = new HashMap<String, String>();

       set.put("to", (String)request.getParameter("to")); // 받는 사람
       set.put("from", "01072439835"); // 발신번호
       set.put("text", "안녕하세요 인증번호는 ["+(String)request.getParameter("text")+"]입니다"); // 문자내용
       set.put("type", "sms"); // 문자 타입
       
       JSONObject result = coolsms.send(set); // 보내기&전송결과받기

       if ((boolean)result.get("status") == true) {
         // 메시지 보내기 성공 및 전송결과 출력
         System.out.println("성공");
         System.out.println(result.get("group_id")); // 그룹아이디
         System.out.println(result.get("result_code")); // 결과코드
         System.out.println(result.get("result_message")); // 결과 메시지
         System.out.println(result.get("success_count")); // 메시지아이디
         System.out.println(result.get("error_count")); // 여러개 보낼시 오류난 메시지 수
         return "success";
       } else {
         // 메시지 보내기 실패
         System.out.println("실패");
         System.out.println(result.get("code")); // REST API 에러코드
         System.out.println(result.get("message")); // 에러메시지
         return "fail";
       }
      
	   }
	
}
