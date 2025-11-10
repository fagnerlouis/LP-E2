package org.example.musicaapp;
public class Arma {
    private long id;
    private String modelo;
    private int qtdTiros;
    private String dono;
    public Arma() {}
    public Arma(long id, String modelo, int qtdTiros, String dono) {
        this.id=id; this.modelo=modelo; this.qtdTiros=qtdTiros; this.dono=dono;
    }
    public Arma(String modelo, int qtdTiros, String dono) { this(0,modelo,qtdTiros,dono); }
    public long getId(){return id;} public void setId(long id){this.id=id;}
    public String getModelo(){return modelo;} public void setModelo(String m){this.modelo=m;}
    public int getQtdTiros(){return qtdTiros;} public void setQtdTiros(int q){this.qtdTiros=q;}
    public String getDono(){return dono;} public void setDono(String d){this.dono=d;}
}
