package com.springboot.api.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/delete-api")
public class DeleteController {
   @DeleteMapping(value="/{variable}")
    public String DeleteVariable(@PathVariable String variable){
       return variable;
   }

   @DeleteMapping(value = "/request1")
    public String getRequsestParam(@RequestParam String email){  // 이메일 값으로 delete 값을 지정함
       return "e-mail:"+ email;
   }
}
