package com.form.web;



import com.form.beans.SSHClient;
import com.form.logic.MenuItem;
import com.jcraft.jsch.SftpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;



import java.io.IOException;
import java.util.Map;

@Controller
public class WebController {

    private SSHClient client;

    @Autowired
    public void setClient(SSHClient client){
        this.client = client;
    }

    @RequestMapping(value = "/login1",method = {RequestMethod.POST})
    public String index2( @RequestParam(value = "ip1" ,required = false)String ip1,@RequestParam(value = "port" ,required = false)String port,@RequestParam(value = "login1",required = false)String login,@RequestParam(value = "pass",required = false) String pass ,Model model ){

        int port1 = Integer.parseInt(port);

        client.ssh(ip1,port1,login,pass);
        MenuItem root = new MenuItem();
        root.setPath("/");
        root.setName("root");
        client.getMenu("/",root);

        model.addAttribute("menu",root);

        return "redirect:index";
    }


    @RequestMapping("/index")
    public ModelAndView index(Model model){

//        String address="127.0.0.1";
//        String login="test";
//        String pass="test1";
//        int port=25;
//        client.ssh(address,port,login,pass);
        boolean isconnecnted =false;
        if (this.client.isConnected()){
            MenuItem root = new MenuItem();
            root.setPath("/");
            root.setName("root");
            client.getMenu("/",root);
            isconnecnted=true;
            model.addAttribute("menu",root);
            model.addAttribute("isconnected",isconnecnted);
            return new ModelAndView("index", (Map<String, ?>) model);
        }
        model.addAttribute("isconnected",isconnecnted);

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
                return "failed";
            } catch (SftpException e) {
                System.out.println("SFTP FAILED");

                e.printStackTrace();
                return "failed";
            }

        }


        return "success";

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
