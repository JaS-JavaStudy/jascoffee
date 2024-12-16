package com.jascoffee.jascoffee.controller.user;

import com.jascoffee.jascoffee.dto.user.JoinDTO;
import com.jascoffee.jascoffee.service.user.JoinService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController  // @Controller + @ResponseBody 결합
public class JoinController {

    private final JoinService joinService;

    public JoinController (JoinService joinService){
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String joinProcess(@RequestBody JoinDTO joinDTO) {  // @RequestBody 추가

        joinService.joinProcess(joinDTO);

        return "ok";
    }
}
