package com.pap.queropizza3.models;

/**
 * Created by Rodrigo on 16/05/2016.
 */
public class TCardapioSubGrupo {
    private int codSubGrupo;
    private String nome;
    private TCardapioGrupo grupo;

    public int getCodSubGrupo() {
        return codSubGrupo;
    }

    public void setCodSubGrupo(int codSubGrupo) {
        this.codSubGrupo = codSubGrupo;
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
