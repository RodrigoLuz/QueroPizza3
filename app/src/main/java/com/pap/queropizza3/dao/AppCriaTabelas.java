package com.pap.queropizza3.dao;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Rodrigo on 26/06/2016.
 */
public class AppCriaTabelas {

    public static final String t_cliente = "cliente"; // tabela
    public static final String f_cli_id = "id_cliente"; // pk
    public static final String f_cli_nome = "nome";
    public static final String f_cli_cep = "cep";
    public static final String f_cli_endereco = "endereco";
    public static final String f_cli_numero = "numero";
    public static final String f_cli_complemento = "complemento";
    public static final String f_cli_bairro = "bairro";
    public static final String f_cli_cidade = "cidade";
    public static final String f_cli_uf = "uf";
    public static final String f_cli_email = "email";
    public static final String f_cli_telefone = "telefone";

    public static final String t_pedido = "pedido"; // tabela
    public static final String f_ped_id = "id_pedido"; // pk
    public static final String f_ped_nome = "nome";
    public static final String f_ped_cep = "cep";
    public static final String f_ped_endereco = "endereco";
    public static final String f_ped_numero = "numero";
    public static final String f_ped_complemento = "complemento";
    public static final String f_ped_bairro = "bairro";
    public static final String f_ped_cidade = "cidade";
    public static final String f_ped_uf = "uf";
    public static final String f_ped_email = "email";
    public static final String f_ped_telefone = "telefone";
    public static final String f_ped_delivery = "delivery";
    public static final String f_ped_f_pagamento = "f_pagamento";
    public static final String f_ped_troco_para = "troco_para";
    public static final String f_ped_taxa = "taxa";
    public static final String f_ped_datahora = "datahora";

    public static final String t_pedido_item = "pedido_item"; // tabela
    public static final String f_ped_item_id = "id_pedido_itens"; // pk
    public static final String f_ped_item_pedido_id = "id_pedido"; // fk
    public static final String f_ped_item_quantidade = "quantidade";
    public static final String f_ped_item_valor = "valor";
    public static final String f_ped_item_tamanho = "tamanho";
    public static final String f_ped_item_obs = "observacao";

    public static final String t_pedido_detalhe = "pedido_detalhe"; // tabela
    public static final String f_ped_detalhe_id = "id_pedido_detalhe"; // pk
    public static final String f_ped_detalhe_ped_itens_id = "id_pedido_itens"; //fk
    public static final String f_ped_detalhe_cardapio_id = "id_cardapio";
    public static final String f_ped_detalhe_valor = "valor";

    public static final String t_grupo = "cardapio_grupo"; // tabela
    public static final String f_grupo_id = "id_grupo";
    public static final String f_grupo_cod_grupo = "cod_grupo";
    public static final String f_grupo_nome = "nome";

    public static final String t_sub_grupo = "cardapio_sub_grupo"; // tabela
    public static final String f_sub_grupo_id = "id_sub_grupo";
    public static final String f_sub_grupo_cod_sub_grupo = "cod_sub_grupo";
    public static final String f_sub_grupo_nome = "nome";
    public static final String f_sub_grupo_grupo = "id_grupo";

    public static final String t_item = "cardapio_item"; // tabela
    public static final String f_item_id = "id_item";
    public static final String f_item_cod_item = "cod_item";
    public static final String f_item_nome = "nome";
    public static final String f_item_descricao = "descricao";
    public static final String f_item_valor = "valor";
    public static final String f_item_sub_grupo = "id_sub_grupo";

    public void criaTabelas(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table " + t_cliente + "(" +
                        f_cli_id + " integer primary key autoincrement, " +
                        f_cli_nome + " text," +
                        f_cli_cep + " text," +
                        f_cli_endereco + " text," +
                        f_cli_numero + " text," +
                        f_cli_complemento + " text," +
                        f_cli_bairro + " text," +
                        f_cli_cidade + " text," +
                        f_cli_uf + " text," +
                        f_cli_email + " text," +
                        f_cli_telefone + " text)"
        );

        sqLiteDatabase.execSQL(
                "create table " + t_pedido + "(" +
                        f_ped_id + " integer primary key autoincrement, " +
                        f_ped_nome + " text," +
                        f_ped_cep + " text," +
                        f_ped_endereco + " text," +
                        f_ped_numero + " text," +
                        f_ped_complemento + " text," +
                        f_ped_bairro + " text," +
                        f_ped_cidade + " text," +
                        f_ped_uf + " text," +
                        f_ped_email + " text," +
                        f_ped_telefone + " text," +
                        f_ped_delivery + " integer," +
                        f_ped_f_pagamento + " integer," +
                        f_ped_troco_para + " real," +
                        f_ped_taxa + " real," +
                        f_ped_datahora + " text)"
        );

        sqLiteDatabase.execSQL(
                "create table " + t_pedido_item + "(" +
                        f_ped_item_id + " integer primary key autoincrement, " +
                        f_ped_item_pedido_id + " integer, " +
                        f_ped_item_quantidade + " integer," +
                        f_ped_item_valor + " real," +
                        f_ped_item_tamanho + " integer," +
                        f_ped_item_obs + " text, " +
                        "CONSTRAINT [fk_pedido] FOREIGN KEY([id_pedido]) REFERENCES pedido([id_pedido]) ON DELETE CASCADE ON UPDATE CASCADE)"
        );

        sqLiteDatabase.execSQL(
                "create table " + t_pedido_detalhe + "(" +
                        f_ped_detalhe_id + " integer primary key autoincrement, " +
                        f_ped_detalhe_ped_itens_id + " integer, " +
                        f_ped_detalhe_cardapio_id + " integer, " +
                        f_ped_detalhe_valor + " real, " +
                        " CONSTRAINT [fk_pedido_item] FOREIGN KEY([id_pedido_itens]) REFERENCES pedido_item([id_pedido_itens]) ON DELETE CASCADE ON UPDATE CASCADE)"
        );

        sqLiteDatabase.execSQL(
                "create table " + t_grupo + "(" +
                        f_grupo_id + " integer primary key autoincrement, " +
                        f_grupo_cod_grupo + " integer, " +
                        f_grupo_nome + " text)"
        );

        sqLiteDatabase.execSQL(
                "create table " + t_sub_grupo + "(" +
                        f_sub_grupo_id + " integer primary key autoincrement, " +
                        f_sub_grupo_cod_sub_grupo + " integer, " +
                        f_sub_grupo_nome + " text, " +
                        f_sub_grupo_grupo + " integer, "+
                        " CONSTRAINT [fk_cardapio_grupo] FOREIGN KEY([id_grupo]) REFERENCES cardapio_grupo([id_grupo]) ON DELETE CASCADE ON UPDATE CASCADE)"
        );

        sqLiteDatabase.execSQL(
                "create table " + t_item + "(" +
                        f_item_id + " integer primary key autoincrement, " +
                        f_item_cod_item + " integer, " +
                        f_item_nome + " text, " +
                        f_item_descricao + " text, " +
                        f_item_valor + " real, " +
                        f_item_sub_grupo + " integer, " +
                        "CONSTRAINT [fk_cardapio_sub_grupo] FOREIGN KEY([id_sub_grupo]) REFERENCES cardapio_sub_grupo([id_sub_grupo]) ON DELETE CASCADE ON UPDATE CASCADE)"
        );

    }
}
