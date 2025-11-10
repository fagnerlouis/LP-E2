package org.example.musicaapp;
public class Cidade {
    private long id;
    private String nome;
    private String regiao;
    private boolean violenta;
    public Cidade(){}
    public Cidade(long id, String nome, String regiao, boolean violenta){
        this.id=id; this.nome=nome; this.regiao=regiao; this.violenta=violenta;
    }
    public Cidade(String nome, String regiao, boolean violenta){ this(0,nome,regiao,violenta); }
    public long getId(){return id;} public void setId(long id){this.id=id;}
    public String getNome(){return nome;} public void setNome(String n){this.nome=n;}
    public String getRegiao(){return regiao;} public void setRegiao(String r){this.regiao=r;}
    public boolean isViolenta(){return violenta;} public void setViolenta(boolean v){this.violenta=v;}
}
