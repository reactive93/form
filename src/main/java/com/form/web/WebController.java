package com.form.web;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class WebController {


    @RequestMapping("/index")
    public ModelAndView index(){

        return new ModelAndView("index");
    }


}
