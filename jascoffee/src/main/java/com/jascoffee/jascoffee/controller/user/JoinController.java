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

    // @RequestBody를 사용하여 JSON 형식으로 데이터를 받을 때
    @PostMapping("/join")
    public String joinProcess(@RequestBody JoinDTO joinDTO) {
        joinService.joinProcess(joinDTO);
        return "ok";
    }

    // @ModelAttribute를 사용하여 form-data를 받을 때
//    @PostMapping("/join/form")
//    public String joinProcessForm(JoinDTO joinDTO) {
//        joinService.joinProcess(joinDTO);
//        return "ok";
//    }

}
