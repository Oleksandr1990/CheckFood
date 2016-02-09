package org.checkfood.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by alexander on 11.01.2016.
 */
public class Product {
    private String name;
    private int danger;
    private String link;
    private String imageLink;
    private String barCode;
    private List<Additive> additiveList = new LinkedList<Additive>();



    private boolean notFound;

    public boolean getNotFound(){
        return this.notFound;
    }

    public void setNotFound(boolean notFound){
        this.notFound=notFound;
    }

    public List<Additive> getAdditiveList(){
        return this.additiveList;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDanger() {
        return danger;
    }

    public void setDanger(int danger) {
        this.danger = danger;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
