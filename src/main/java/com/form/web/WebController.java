package com.form.web;



import com.form.beans.SSHClient;
import com.form.logic.MenuItem;
import com.jcraft.jsch.SftpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
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

    @RequestMapping(value = "/upload",method = {RequestMethod.POST})
    public String UploadFile(@RequestParam("file")MultipartFile file,@RequestParam("path")String path){

        String result="";

        if (file.isEmpty()){
            System.out.println("EMPTY FILE");
        }
        else {

            try {
                String name=file.getOriginalFilename();
                String path1 = path+name;
                client.upLoad(file.getInputStream(),path1);
                result= "Success upload";
            } catch (IOException e) {
                result="Failed upload";
                System.out.println("WRITE FAILED");

                e.printStackTrace();
            } catch (SftpException e) {
                System.out.println("SFTP FAILED");

                e.printStackTrace();
            }

        }
        return result;

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
