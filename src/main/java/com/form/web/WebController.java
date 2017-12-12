package com.form.web;



import com.form.beans.SSHClient;
import com.form.logic.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
public class WebController {

    private SSHClient client;

    @Autowired
    public void setClient(SSHClient client){
        this.client = client;
    }

    @RequestMapping("/index")
    public ModelAndView index(Model model){

        String address="127.0.0.1";
        String login="test";
        String pass="test1";
        int port=25;
        client.ssh(address,port,login,pass);
        MenuItem root = new MenuItem();
        root.setPath("/");
        root.setName("root");
        client.getMenu("/",root);

        model.addAttribute("menu",root);

        return new ModelAndView("index", (Map<String, ?>) model);
    }

    @RequestMapping(value = "/testM",produces = {"application/json"})
    public MenuItem getMenu(){
        //свои значения
        String address="127.0.0.1";
        String login="test";
        String pass="test1";
        int port=25;
        client.ssh(address,port,login,pass);
        MenuItem root = new MenuItem();
        root.setPath("/");
        client.getMenu("/",root);

        return root;
    }


}
