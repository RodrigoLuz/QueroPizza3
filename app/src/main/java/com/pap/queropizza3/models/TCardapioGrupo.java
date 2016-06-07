package com.pap.queropizza3.models;

import java.io.Serializable;

/**
 * Created by Rodrigo on 16/05/2016.
 */
public class TCardapioGrupo implements Serializable {
    private int id_grupo;
    private int cod_grupo;
    private String nome;

    public int getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(int id_grupo) {
        this.id_grupo = id_grupo;
    }

    public int getCod_grupo() {
        return cod_grupo;
    }

    public void setCod_grupo(int cod_grupo) {
        this.cod_grupo = cod_grupo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
