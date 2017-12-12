package com.form.logic;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {

    private boolean isFolder;
    private String path;
    private String name;
    @JsonBackReference
    private MenuItem parent;

    private List<MenuItem> children;




    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }



    public void setParent(MenuItem parent) {
        this.parent = parent;
    }

    public void setChildren(List<MenuItem> children) {
        this.children = children;
    }


    public boolean isFolder() {
        return isFolder;
    }

    public void setFolder(boolean folder) {
        isFolder = folder;
    }

    public void SetPath(String path){
        this.path=path;
    }

    public String getPath(){
        return this.path;
    }

    public String getName() {
        return name;
    }

    public MenuItem getParent() {

        if (this.parent==null){
            return this;
        }

        return parent;
    }

    public List<MenuItem> getChildren() {

        if (this.children==null){
            this.children = new ArrayList<>();
            return this.children;
        }
        return children;

    }


}
