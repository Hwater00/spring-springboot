package com.springboot.api.controller;

import com.springboot.api.dto.MemberDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/get-api") // 클래스 수준에서 설정하면 내부에 선언한 메서드의 url 히고스 앞에 값을 공통으로 추가된다.
public class GetController {

    // Logger 설정
    private final Logger LOGER = LoggerFactory.getLogger(GetController.class);

    @RequestMapping(value="/hello", method = RequestMethod.GET) // http://localhost:8080/api/v1/get-api/hello
    public String getHello(){
        LOGER.info("getHello 메서드 호출 되었습니다");
        return "hello world";
    }

    @GetMapping(value = "/name")
    public String getName(){
        return "Flature";
    }

    @GetMapping(value = "/variable1/{variable}") // 변수명을 가져다가 getMapping: /{@PathVariable 매개변수 값}
    public String getVariable1(@PathVariable String variable){
        LOGER.info("@PathVariable을 통해 들어온 값: {}",variable);
        return variable+"성공";
    }

    @GetMapping(value= "/variable2/{variable}")
    public String getVariable2(@PathVariable(value= "variable") String var){
        return "test2";
    }

    @ApiOperation(value = "GET 메서드 예제", notes="@RequestParam 을 활용한 GET Method")
    @GetMapping(value = "/request1")
    public String getRequestParam(
    @ApiParam(value = "이름", required = true) @RequestParam String name,
    @ApiParam(value = "이메일", required = true)    @RequestParam String email,
    @ApiParam(value = "회사", required = true)    @RequestParam String organization)
    {
            return name+"" + email+ ""+organization;
    }

    @GetMapping(value = "/request2")
    public String getRequestParam2(@RequestParam Map<String,String> parm){
        StringBuilder sb= new StringBuilder();

        parm.entrySet().forEach(map->{
            sb.append(map.getKey()+":"+map.getValue()+"/n");
        });
        return sb.toString();
    }

    @GetMapping(value = "/request3")
    public String getRequestParam3(MemberDto dto){
        return dto.toString();
    }
}
