package com.form.beans;

import com.form.logic.MenuItem;
import com.jcraft.jsch.*;
import org.springframework.stereotype.Component;

import java.util.Vector;


@Component
public class SSHClient {

    private ChannelSftp sftp;
    private Session session;
    private Channel channel;

    private String address;
    private int port;
    private String login;
    private String pass;

    public void ssh(String address, int port, String login, String pass){
        this.address=address;
        this.port=port;
        this.login=login;
        this.pass=pass;
        JSch jSch = new JSch();
        try {
            session = jSch.getSession(login,address,port);
            session.setPassword(pass);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
        } catch (JSchException e) {
            System.out.println("Error getting session");
            e.printStackTrace();
        }
        try {
            channel = session.openChannel("sftp");
        } catch (JSchException e) {
            System.out.println("Error getting channel");
            e.printStackTrace();
        }
        sftp = (ChannelSftp)channel;

    }
    private String prev="";
    public void getMenu(String path,MenuItem root){
        prev+="/";
        String current="";
        current+=prev+path;
        try {
            Vector<ChannelSftp.LsEntry> list= sftp.ls(current);

            for (ChannelSftp.LsEntry item:list) {

                if(!(item.getFilename().charAt(item.getFilename().length()-1)=='.')) {

                    if (item.getAttrs().isDir()){

                        prev+=path;
                        String nameFolder = item.getFilename();
                        MenuItem folder = new MenuItem();
                        folder.setName(nameFolder);
                        folder.setPath(path);
                        folder.setFolder(true);
                        folder.setParent(root);
                        root.getChildren().add(folder);
                        getMenu(nameFolder,folder);
                    }
                    else {
                        String name = item.getFilename();
                        MenuItem file = new MenuItem();
                        file.setName(name);
                        root.getChildren().add(file);
                    }

                }
                prev="";
            }



        } catch (SftpException e) {
            e.printStackTrace();
        }

    }

}
