package com.phishme.controller;

import com.phishme.controller.jwt.JwtAuthenticationController;
import com.phishme.model.UserInfo;
import com.phishme.model.jwt.JwtRequest;
import com.phishme.model.jwt.JwtResponse;
import com.phishme.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ApplicationContext context;


    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }


    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/register")
    public String saveUser(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setEmail(email);
        userInfo.setPassword(password);
        if (userInfoService.existsByEmail(email)) {
            model.addAttribute("userAlreadyExists", true);
            return "register";
        }
        userInfoService.saveUserInfo(userInfo);
        model.addAttribute("username", username);
        model.addAttribute("isLogged", true);
        return "home.html";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam String username,
                           @RequestParam String password,
                           Model model){

        if (userInfoService.existsByEmail(username)) {
            JwtRequest jwtRequest = new JwtRequest(username, password);
            ResponseEntity<JwtResponse> response = null;
           try {
               response = context.getBean(JwtAuthenticationController.class).createAuthenticationToken(jwtRequest);
           }catch (Exception e){
               System.out.println("Exception in login : "+e);
               model.addAttribute("wrongDetail", true);
               return "redirect:/login";
           }

            String jwtToken = response.getBody().getToken();
            model.addAttribute("jwtToken", jwtToken);
            model.addAttribute("isLogged", true);
            return "home";
        }

        model.addAttribute("wrongDetail", true);
        return "redirect:/login";
    }




}
