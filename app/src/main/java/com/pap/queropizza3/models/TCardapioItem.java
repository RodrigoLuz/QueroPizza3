package com.pap.queropizza3.models;

/**
 * Created by Rodrigo on 16/05/2016.
 */
public class TCardapioItem {
    int idCardapioItem;
    String nome;
    String descricao;
    Double valor;
    TCardapioSubGrupo subgrupo;

    public int getIdCardapioItem() {
        return idCardapioItem;
    }

    public void setIdCardapioItem(int idCardapioItem) {
        this.idCardapioItem = idCardapioItem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public TCardapioSubGrupo getSubgrupo() {
        return subgrupo;
    }

    public void setSubgrupo(TCardapioSubGrupo subgrupo) {
        this.subgrupo = subgrupo;
    }
}
