package com.ckfcsteam.replikapp.models;

public class DataModel {
    /*Attributs*/
    int img_ressource; /*Contiendra l'int de la ressource de l'image*/
    String lvl_number, lvl_desc; /*Le lvl id, et sa description*/

    /*Constructeur*/
    public DataModel(int img_ressource, String lvl_number, String lvl_desc){
        this.img_ressource = img_ressource;
        this.lvl_number = lvl_number;
        this.lvl_desc = lvl_desc;
    }

    /*Getters*/
    public int getImg_ressource() {return img_ressource;} /*renvoie de l'int de la ressource de l'image*/
    public String getLvl_desc() {return lvl_desc;} /*renvoie de la description du niveau*/
    public String getLvl_number() {return lvl_number;} /*renvoie du lvl id*/

    /*Setter*/
    public void setImg_ressource(int img_ressource) {this.img_ressource = img_ressource;} /*Modifier l'int de la ressource de l'image*/
}