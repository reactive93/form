package com.form.web;

import com.form.beans.SSHClient;
import com.form.logic.MenuItem;

public class Programm {

    public static void main(String[] args) {

        SSHClient sshClient = new SSHClient();
        String address="127.0.0.1";
        String login="test";
        String pass="test1";
        int port=25;
        sshClient.ssh(address,port,login,pass);
        MenuItem root = new MenuItem();
        root.setPath("/");
        sshClient.getMenu("/",root);


    }


}
