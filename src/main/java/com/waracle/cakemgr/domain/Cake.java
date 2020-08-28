package com.waracle.cakemgr.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "Cake", uniqueConstraints = {@UniqueConstraint(columnNames = "ID"), @UniqueConstraint(columnNames = "TITLE")})
public class Cake implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    @JsonProperty("id")
    private Integer cakeId;

    @Column(name = "TITLE", unique = true, nullable = false, length = 100)
    @JsonProperty("title")
    private String title;

    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    @JsonProperty("desc")
    private String desc;

    @Column(name = "IMAGE_URL", nullable = false, length = 300)
    @JsonProperty("image")
    private String image;

    public Integer getCakeId() {
        return cakeId;
    }

    public void setCakeId(Integer cakeId) {
        this.cakeId = cakeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "{\n" +
                "        \"title\": \"" + title + "\",\n" +
                "        \"desc\": \"" + desc + "\",\n" +
                "        \"image\": \"" + image + "\",\n" +
                "    }";
    }

    public Cake(String title, String desc, String image) {
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    public Cake() {
    }
}
