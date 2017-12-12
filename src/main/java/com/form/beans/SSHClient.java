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
            channel.connect();
        } catch (JSchException e) {
            System.out.println("Error getting channel");
            e.printStackTrace();
        }
        sftp = (ChannelSftp)channel;

    }

    public void getMenu(String path,MenuItem root){

        try {
            Vector<ChannelSftp.LsEntry> list= this.sftp.ls(path);

            for (ChannelSftp.LsEntry item:list) {

                if(!(item.getFilename().charAt(item.getFilename().length()-1)=='.')) {

                    if (item.getAttrs().isDir()){

                        String nameFolder = item.getFilename();
                        MenuItem folder = new MenuItem();
                        folder.setName(nameFolder);
                        folder.setPath(path+nameFolder+"/");
                        folder.setFolder(true);
                        folder.setParent(root);
                        root.getChildren().add(folder);
                        getMenu(folder.getPath(),folder);
                    }
                    else {
                        String name = item.getFilename();
                        MenuItem file = new MenuItem();
                        file.setPath(path+name);
                        file.setName(name);
                        root.getChildren().add(file);
                    }

                }
            }



        } catch (SftpException e) {
            e.printStackTrace();
        }

    }

}
