package com.emailpart.emaildemo.controller;

import com.emailpart.emaildemo.model.ConfirmationToken;
import com.emailpart.emaildemo.model.User;
import com.emailpart.emaildemo.repository.ConfirmationTokenRepository;
import com.emailpart.emaildemo.repository.UserRepository;
import com.emailpart.emaildemo.service.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class UserAccountController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSender emailSender;

    @RequestMapping(value="/",method=RequestMethod.GET)
    public String index(){
        return "index";
    }
    @RequestMapping(value = "/loginform",method = RequestMethod.POST)
    public ModelAndView login(ModelAndView modelAndView,User user){
//        User usr = userRepository.findByEmailIdIgnoreCase(user.getEmailId());
//        System.out.println(user.getEmailId());
//        System.out.println(user.getPassword());
//        System.out.println(usr.getPassword());
//        System.out.println(usr.getEmailId());
//        System.out.println();
//        if(usr!=null){
//            modelAndView.setViewName("dashboard");
//        }else{
////            modelAndView.addObject("loginError",true);
//            modelAndView.addObject("message","This mail does not exist");
//            modelAndView.setViewName("error");
//        }
//        return modelAndView;
        modelAndView.setViewName("/dashboard");
        return modelAndView;
    }

    @RequestMapping(value ="/register",method = RequestMethod.POST)
    public ModelAndView registerUser(ModelAndView modelAndView,User user){
        User existingUser = userRepository.findByEmailIdIgnoreCase(user.getEmailId());
        System.out.println("abc");
        if(existingUser != null){
            System.out.println("This email already exist");
            modelAndView.addObject("message","This mail already exists");
            modelAndView.setViewName("error");
        }else{
            userRepository.save(user);
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmailId());
            mailMessage.setSubject("COMPLETE REGISTRATION");
            mailMessage.setFrom("ritu.s@consultadd.com");
            mailMessage.setText("To confirm your account, please click: "+"http://localhost:8082/confirm?token="+confirmationToken.getConfirmationToken());

            emailSender.sendMail(mailMessage);
            modelAndView.addObject("emailId", user.getEmailId());
            modelAndView.setViewName("successfulRegistration");
        }
        return modelAndView;
    }

    @RequestMapping(value="/confirm",method = {
            RequestMethod.GET,RequestMethod.POST
    })
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken){
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        System.out.println(confirmationToken);
        if(token != null){
            User user = userRepository.findByEmailIdIgnoreCase(token.getUser().getEmailId());
            user.setEnabled(true);
            userRepository.save(user);
            modelAndView.setViewName("accountVerified");
        }else{
            modelAndView.addObject("message","the link is invalid");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public ModelAndView displayRegistration(ModelAndView modelAndView,User user){
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value="/dashboard",method = RequestMethod.GET)
    public ModelAndView getUserDetails(ModelAndView modelAndView) {
        List<User> result = (List<User>) userRepository.findAll();
        modelAndView.addObject("users",result);
        modelAndView.setViewName("/dashboard");
        return modelAndView;
    }

    @RequestMapping(value = "/loginform", method = RequestMethod.GET)
    public ModelAndView displayLogin(ModelAndView modelAndView,User user){
        modelAndView.addObject("user",user);
        modelAndView.setViewName("loginform");
        return modelAndView;
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "delete/{id}")
    public String deleteUser(@PathVariable Long id){
        Optional<User> usr = userRepository.findById(id);
        if(usr.isPresent())
            userRepository.deleteById(id);
        return "redirect:/dashboard";
    }



}



