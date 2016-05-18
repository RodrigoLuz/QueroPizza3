package com.pap.queropizza3.models;

/**
 * Created by Rodrigo on 16/05/2016.
 */
public class TCardapioSubGrupo {
    int idSubGrupo;
    String nome;
    TCardapioGrupo grupo;

    public int getIdSubGrupo() {
        return idSubGrupo;
    }

    public void setIdSubGrupo(int idSubGrupo) {
        this.idSubGrupo = idSubGrupo;
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
