package com.form.web;



import com.form.beans.SSHClient;
import com.form.logic.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class WebController {

    private SSHClient client;

    @Autowired
    public void setClient(SSHClient client){
        this.client = client;
    }

    @RequestMapping("/index")
    public ModelAndView index(){

        return new ModelAndView("index");
    }

    @RequestMapping("/testM")
    public MenuItem getMenu(){
        //свои значения
        String address="";
        String login="";
        String pass="";
        int port=0;
        client.ssh(address,port,login,pass);
        MenuItem root = new MenuItem();

        client.getMenu("",root);

        return root;
    }


}
