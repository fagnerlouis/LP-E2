package org.example.musicaapp;
public class Personagem {
    private long id;
    private String nome;
    private String origem;
    private boolean vivo;
    public Personagem(){}
    public Personagem(long id, String nome, String origem, boolean vivo){
        this.id=id; this.nome=nome; this.origem=origem; this.vivo=vivo;
    }
    public Personagem(String nome, String origem, boolean vivo){ this(0,nome,origem,vivo); }
    public long getId(){return id;} public void setId(long id){this.id=id;}
    public String getNome(){return nome;} public void setNome(String n){this.nome=n;}
    public String getOrigem(){return origem;} public void setOrigem(String o){this.origem=o;}
    public boolean isVivo(){return vivo;} public void setVivo(boolean v){this.vivo=v;}
}
