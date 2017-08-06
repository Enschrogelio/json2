package com.ruby.x.json2.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataTask {

    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String _id) {
        id = _id;
    }


    @SerializedName("title")
    @Expose
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String _title) {
        title = _title;
    }



    @SerializedName("apellido")
    @Expose
    private String apellido;

    public String getApellido(){
        return apellido;
    }

    public void setApellido(String _apellido) {apellido = _apellido;
    }



    @SerializedName("estado")
    @Expose
    private String estado;

    public String getEstado(){
        return estado;
    }

    public void setEstado(String _estado) {estado = _estado;
    }


    @SerializedName("municipio")
    @Expose
    private String municipio;

    public String getMunicipio(){
        return municipio;
    }

    public void setMunicipio(String _municipio) {municipio = _municipio;
    }



    @SerializedName("description")
    @Expose
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String _description) {
        description = _description;
    }



    @SerializedName("lat")
    @Expose
    private String lat;

    public String getLat() {
        return lat;
    }

    public void setLat(String _lat) {
        lat = _lat;
    }



    @SerializedName("lng")
    @Expose
    private String lng;

    public String getLng() {
        return lng;
    }

    public void setLng(String _lng) {
        lng = _lng;
    }



    @SerializedName("created_date")
    @Expose
    private String created_date;

    public String getCreatedDate() {
        return created_date;
    }

    public void setCreatedDate(String _created_date) {
        created_date = _created_date;
    }


    @SerializedName("file_documentation")
    @Expose
    private String file_documentation;

    public String getFileDocumentation() {
        return file_documentation;
    }

    public void setFileDocumentation(String _file_documentation) {
        file_documentation = _file_documentation;
    }

    @SerializedName("file_documentation1")
    @Expose
    private String file_documentation1;

    public String getFileDocumentation1() {
        return file_documentation1;
    }

    public void setFileDocumentation1(String _file_documentation1) {
        file_documentation1 = _file_documentation1;
    }

}
