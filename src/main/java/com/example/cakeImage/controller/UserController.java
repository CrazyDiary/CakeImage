package com.example.cakeImage.controller;
import com.example.cakeImage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * @ Author     ：CrazyCake.
 * @ Date       ：Created in 21:00 2019/4/26
 * @ Description：UserController
 * @ Modified By：
 * @Version: 1.0$
 */
@Controller
public class UserController {
    @Autowired
   public  UserService userService;

    @GetMapping("/userLogin")
    public String userLogin(HttpServletRequest request, HttpSession session){
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        String username=userService.login(email,password);
        System.out.println("session.username is "+username);
        if(username!=null){
            session.setAttribute("username",username);
        }
       return "/index";
    }
    @GetMapping("/userRegister")
    public String userRegister( ){
        return "/Register";
    }
}
