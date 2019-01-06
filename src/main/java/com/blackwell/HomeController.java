package com.blackwell;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/haha")
    @ResponseBody
    public String home() {
        return "hahahahahahahhahah";
    }
}
