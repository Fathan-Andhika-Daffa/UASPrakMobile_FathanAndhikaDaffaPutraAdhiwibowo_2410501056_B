package com.example.uasprakmobile_fathanandhikadaffaputraadhiwibowo_2410501056_b;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorit")
public class Favorit {
    @PrimaryKey
    @NonNull
    private String id;
    private String tipe;
    private String nama;
    private String namaLatin;
    private String deskripsi;
    private String foto;
    private String asal;

    public Favorit() {}

    public Favorit(@NonNull String id, String tipe, String nama, String namaLatin, String deskripsi, String foto, String asal) {
        this.id = id;
        this.tipe = tipe;
        this.nama = nama;
        this.namaLatin = namaLatin;
        this.deskripsi = deskripsi;
        this.foto = foto;
        this.asal = asal;
    }

    @NonNull
    public String getId() { return id; }
    public void setId(@NonNull String id) { this.id = id; }

    public String getTipe() { return tipe; }
    public void setTipe(String tipe) { this.tipe = tipe; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNamaLatin() { return namaLatin; }
    public void setNamaLatin(String namaLatin) { this.namaLatin = namaLatin; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public String getAsal() { return asal; }
    public void setAsal(String asal) { this.asal = asal; }
}
