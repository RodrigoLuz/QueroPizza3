package com.pap.queropizza3.models;

/**
 * Created by Rodrigo on 16/05/2016.
 */
public class TCardapioItem {
    private int id_item;
    private int cod_item;
    private String nome;
    private String descricao;
    private Double valor;
    private TCardapioSubGrupo subgrupo;

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public int getCod_item() {
        return cod_item;
    }

    public void setCod_item(int cod_item) {
        this.cod_item = cod_item;
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
