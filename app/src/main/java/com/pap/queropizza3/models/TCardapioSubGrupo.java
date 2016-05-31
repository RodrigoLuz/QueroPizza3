package com.pap.queropizza3.models;

/**
 * Created by Rodrigo on 16/05/2016.
 */
public class TCardapioSubGrupo {
    private int id_subgrupo;
    private int cod_subgrupo;
    private String nome;
    private TCardapioGrupo grupo;

    public int getId_subgrupo() {
        return id_subgrupo;
    }

    public void setId_subgrupo(int id_subgrupo) {
        this.id_subgrupo = id_subgrupo;
    }

    public int getCod_subgrupo() {
        return cod_subgrupo;
    }

    public void setCod_subgrupo(int cod_subgrupo) {
        this.cod_subgrupo = cod_subgrupo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TCardapioGrupo getGrupo() {
        return grupo;
    }

    public void setGrupo(TCardapioGrupo grupo) {
        this.grupo = grupo;
    }
}
