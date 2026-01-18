package com.beyond.basic.b1_basic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//Controller 어노테이션을 통해 스프링에 의해 객체가 생성되고, 관리되어 개발자가 직접 객체를 생성할 필요없음.
//Controller 어노테이션과 url매핑을 통해 사용자의 요청이 메서드로 분기처리
//화면도 리턴하는 케이스이기 때문에 controller 어노테이션 사용해서 실습함

@Controller
@RequestMapping("/member")
public class MemberController {
//    get요청 리턴의 case : text, json, html(mvc)
//    case1. 서버가 사용자에게 text데이터 return
    @GetMapping("")
//    data(text,json)를 리턴할때에는 responsebody사용. 화면(html)을 리턴할때에는 responsebody생략.
//    controller + responsebody = restcontroller
    @ResponseBody
    public String textDataReturn(){
        return "hongildongdong";
    }

//    case2. 서버가 사용자에게 json형식의 문자데이터 return
    @GetMapping("/json")
    @ResponseBody
    public Member jsonDataReturn() throws JsonProcessingException {
        Member m1 = new Member("h1","h11@naver.com");
//        직접 json을 직렬화 할 필요없이, return 타입에 객체가 있다면 자동으로 직렬화
//        ObjectMapper o1 = new ObjectMapper();
//        String data = o1.writeValueAsString(m1);
        return m1;
    }

//    case3. 서버가 사용자에게 html return
//    case3-1) 정적인 html return
    @GetMapping("/html")
//    ResponseBody가 없고, return타입이 String인 경우 스프링은 templates 폴더 밑에 simple_html.html을 찾아서 리턴.
//    타임리프 의존성이 필요.
    public String htmlReturn(){
        return "simple_html";
    }


//    case3. 서버가 사용자에게 html return
//    case3-2) 서버에서 화면+데이터를 함께 주는 동적인 화면
//    이 방식은 ssr방식(서버사이드 렌더링). csr 방식은 화면은 별도제공하고 서버는 데이터만 제공.
    @GetMapping("/html/dynamic")
//    ResponseBody가 없고, return타입이 String인 경우 스프링은 templates 폴더 밑에 simple_html.html을 찾아서 리턴.
//    타임리프 의존성이 필요.
    public String dynamicHtmlReturn(Model model){
//        model 객체는 데이터를 화면에 전달해주는 역할
//        name=hongildong 형태로 화면에 전달
        model.addAttribute("name","hongildong");
        model.addAttribute("email","hongildong@naver.com");
        return "dynamic_html";
    }



//    get요청의 url의 데이터 추출방식 : pathvariable, 쿼리 파라미터
//    case1. pathvariable방식을 통해 사용자로부터 url에서 데이터 추출
//    데이터의 형식 : /member/path/1
    @GetMapping("/path/{inputId}")
    @ResponseBody
    public String path(@PathVariable Long inputId){
//        별도의 형변환 없이도, 원하는 자료형으로 형변환되어 매개변수로 주입.(Spring이 형변환 해줌)
//        매개변수의 변수명이 url의 변수명과 일치해야함
        System.out.println(inputId);
        return "OK";
    }

//    case2. 쿼리파라미터(parameter)방식을 통한 url에서의 데이터 추출(주로 검색, 정렬 요청 등의 상황에서 사용 -ex) name,email )
//    case2-1) 1개의 파라미터에서 데이터 추출
//    데이터의 형식 : /member/param1?name=hongildong
    @GetMapping("/param1")
    @ResponseBody
    public String param1(@RequestParam(value = "name") String inputName){
        System.out.println(inputName);
        return "OK";
    }
//    case2-2) 2개의 파라미터에서 데이터 추출
//    데이터의 형식 : /member/param2?name=hongildong&email=hongildong@naver.com
    @GetMapping("/param2")
    @ResponseBody
    public String param2(@RequestParam(value = "name") String inputName,@RequestParam(value = "email") String inputEmail){
        System.out.println(inputName);
        System.out.println(inputEmail);
        return "OK";
    }

//    case2-3) 파라미터의 개수가 더 많아질 경우, ModelAttribute를 통한 데이터바인딩
//    데이터바인딩은 파라미터의 데이터를 모아 객체로 자동 매핑 및 생성 (여러개의 데이터를 객체로 받아주겠다는뜻)
//    클래스 안의 변수명과 파라미터의 변수명이 일치해야함
//    데이터의 형식 : /member/param3?name=hongildong&email=hongildong@naver.com
    @GetMapping("/param3")
    @ResponseBody
//    @ModelAttribute는 생략 가능
    public String param3(@ModelAttribute Member member){
        System.out.println(member);
        return "OK";
    }

//    post 요청 처리 case : urlencoded, martipart-formdata, json
//    case1. body의 content-type이 urlencoded형식
//    형식 : name=hongildong&email=hong@naver.com
    @PostMapping("/url-encoded")
    @ResponseBody
//   body부 형식이 url의 파라미터 방식과 동일하므로, RequestParam 또는 데이터바인딩 가능
    public String urlEncoded(@ModelAttribute Member member){
        System.out.println(member);
        return "OK";
    }

//    case2. body의 content-type이 multipart-formdata
//    case2-1) 1개의 이미지만 있는 경우
//    형식 : body부에 name=hongildong&email=hong@naver.com&profileImage=xxxx(바이너리데이터)
    @PostMapping("/multipart-formdata")
    @ResponseBody
//    body형식이 url의 파라미터 방식과 동일하므로, RequestParam 또는 데이터바인딩 가능
//    Member클래스에서 MultipartFile 설정해서 Member객체 전체에 설정가능함.
    public String multipartFormdata(@ModelAttribute Member member, @RequestParam(value = "profileImage")MultipartFile profileImage){
        System.out.println(member);
        System.out.println(profileImage.getOriginalFilename());
        return "OK";
    }

//    case2-2) 여러개의 이미지가 있는 경우
//    형식 : body부에 name=hongildong&email=hong@naver.com&profileImage=xxxx(바이너리데이터)
    @PostMapping("/multipart-formdata-images")
    @ResponseBody
//   body형식이 url의 파라미터 방식과 동일하므로, RequestParam 또는 데이터바인딩 가능
//    여러개의 사진을 넣고 싶으면 List<MultipartFile> 변수 이렇게 하면 됨
    public String multipartFormdataImages(@ModelAttribute Member member, @RequestParam(value = "profileImages") List<MultipartFile> profileImage){
        System.out.println(member);
        System.out.println(profileImage.size());
        return "OK";
    }

//    case3. body의 content-type이 json인 경우
//    case3-1) 일반적인 json데이터 처리
//    이 방식을 제일 많이 사용
//    형식 : {"name":"hongildong", "email":"hong@naver.com"}
    @PostMapping("/json")
    @ResponseBody
//    RequestBody : json데이터를 객체로 파싱하는 어노테이션
    public String json(@RequestBody Member member){
        System.out.println(member);
        return "OK";
    }

//    case3-2) 배열형식의 json데이터 처리
//    형식 : [{"name":"hongildong, "email":"hong1@naver.com"},{"name":"hongildong, "email":"hong123@naver.com"}]
    @PostMapping("/json-list")
    @ResponseBody
    //    RequestBody : json데이터를 객체로 파싱하는 어노테이션
    public String jsonList(@RequestBody List<Member> memberList){
        System.out.println(memberList);
        return "OK";
    }

//    case3-3) 중첩된 json데이터 처리
//    Student클래스 만들어서 중첩json데이터 처리하는 방법 실습
//    데이터형식 : {"name":"hongildong", "email":"hong1@naver.com","scores":[{"subject":"math","point":100},{"subject":"English","point":90},{"subject":"korean","point":100}]}
    @PostMapping("/json-nested")
    @ResponseBody
    public String jsonNested(@RequestBody Student student){
        System.out.println(student);
        return"OK";
    }

//    case3-4) json+file 이 함께있는 데이터 처리
//    json과 파일을 같이 처리하고 싶으면 multipart/formdata만 이미지 처리가능
//    데이터 형식: member={"name":"xx", "email":"yy}&profileImage=바이너리
//    결론은 multipart-formdata구조안에 json을 넣는 방식
    @PostMapping("/json-file")
    @ResponseBody
//    보낼때에는 Multipart-formdata방식
//    json과 file을 함께처리 해야할때에는 일반적으로 RequestPart사용
//    RequestPart가 하나 있으면 파일처리할때에도 같이 RequestPart사용
    public String jsonFile(@RequestPart("member") Member member,
                           @RequestPart("profileImage") MultipartFile profileImage){
        System.out.println(member);
        System.out.println(profileImage.getOriginalFilename());
        return "ok";
    }
}
