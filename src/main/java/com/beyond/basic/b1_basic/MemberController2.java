package com.beyond.basic.b1_basic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/member2")
public class MemberController2 {
//    case1. 서버가 사용자에게 text데이터 return

    @GetMapping("")
    @ResponseBody
    public String textDataReturn2(){
        return "hongildong2";
    }

//    case2. 서버가 사용자에게 json형식의 문자데이터 return
    @GetMapping("/json2")
    @ResponseBody
    public String jsonDataReturn() throws JsonProcessingException {
        Member2 m2 = new Member2("hong2","hong2@naver.com");
        ObjectMapper o2 = new ObjectMapper();
        String jsonData = o2.writeValueAsString(m2);
        return jsonData;
    }

//    case3. 서버가 사용자에게 html return
    @GetMapping("/html2")
    public String htmlDataReturn(){
        return "simple_html2.html";
    }
}
