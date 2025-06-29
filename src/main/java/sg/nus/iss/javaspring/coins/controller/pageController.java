package sg.nus.iss.javaspring.coins.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class pageController {

    @GetMapping("/coins-app")
    public String showCoinsApp() {
        return "mainPage";
    }
}
