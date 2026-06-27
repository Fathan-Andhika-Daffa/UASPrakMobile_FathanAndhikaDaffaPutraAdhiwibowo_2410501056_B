package com.example.uasprakmobile_fathanandhikadaffaputraadhiwibowo_2410501056_b;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Endemik implements Serializable {
    @SerializedName("id")
    private String id;
    
    @SerializedName("tipe")
    private String tipe;
    
    @SerializedName("nama")
    private String nama;
    
    @SerializedName("nama_latin")
    private String namaLatin;
    
    @SerializedName("deskripsi")
    private String deskripsi;
    
    @SerializedName("foto")
    private String foto;

    @SerializedName("asal")
    private String asal;

    public Endemik() {}

    public Endemik(String id, String tipe, String nama, String namaLatin, String deskripsi, String foto, String asal) {
        this.id = id;
        this.tipe = tipe;
        this.nama = nama;
        this.namaLatin = namaLatin;
        this.deskripsi = deskripsi;
        this.foto = foto;
        this.asal = asal;
    }

    // Getters
    public String getId() { return id; }
    public String getTipe() { return tipe; }
    public String getNama() { return nama; }
    public String getNamaLatin() { return namaLatin; }
    public String getDeskripsi() { return deskripsi; }
    public String getFoto() { return foto; }
    public String getAsal() { return asal; }
}
