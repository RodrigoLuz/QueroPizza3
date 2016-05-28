package com.pap.queropizza3.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pap.queropizza3.models.TCardapioGrupo;
import com.pap.queropizza3.models.TCardapioItem;
import com.pap.queropizza3.models.TCardapioSubGrupo;
import com.pap.queropizza3.models.TCliente;
import com.pap.queropizza3.models.TItemTela;
import com.pap.queropizza3.models.TPedido;
import com.pap.queropizza3.models.TPedidoItem;
import com.pap.queropizza3.models.TPedidoDetalhe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rodrigo on 19/05/2016.
 */
public class AppSQLDao {

    private AppSQLHelper helper;
    public AppSQLDao(Context ctx){
        helper = new AppSQLHelper(ctx);
    }

    public int inserirCardapioGrupo(TCardapioGrupo grupo) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.f_grupo_cod_grupo, grupo.getCodGrupo());
        cv.put(AppSQLHelper.f_grupo_nome, grupo.getNome());

        int id = (int) db.insert(AppSQLHelper.t_grupo, null, cv);
        db.close();
        return id;
    }

    public int inserirCardapioSubGrupo(TCardapioSubGrupo subgrupo) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.f_sub_grupo_cod_sub_grupo, subgrupo.getCodSubGrupo());
        cv.put(AppSQLHelper.f_sub_grupo_nome, subgrupo.getNome());
        cv.put(AppSQLHelper.f_sub_grupo_grupo, subgrupo.getGrupo().getCodGrupo());

        int id = (int) db.insert(AppSQLHelper.t_sub_grupo, null, cv);
        db.close();
        return id;
    }

    public int inserirCardapioItem(TCardapioItem item) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.f_item_nome, item.getNome());
        cv.put(AppSQLHelper.f_item_descricao, item.getDescricao());
        cv.put(AppSQLHelper.f_item_valor, item.getValor());
        cv.put(AppSQLHelper.f_item_sub_grupo, item.getSubgrupo().getCodSubGrupo());

        int id = (int) db.insert(AppSQLHelper.t_item, null, cv);
        db.close();
        return id;
    }

    public List<TCardapioItem> listaItem(TCardapioSubGrupo subgrupo) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM "+ AppSQLHelper.t_item;
        String[] argumentos = null;
        if (subgrupo != null) {
            sql += " WHERE "+ AppSQLHelper.f_item_sub_grupo +" = ?";
            argumentos = new String[]{ String.valueOf(subgrupo.getCodSubGrupo()) };
        }
        sql += " ORDER BY "+ AppSQLHelper.f_item_nome;

        Cursor cursor = db.rawQuery(sql, argumentos);

        List<TCardapioItem> itens = new ArrayList<>();
        while (cursor.moveToNext()) {
            int cod_item = cursor.getInt(
                    cursor.getColumnIndex(
                            AppSQLHelper.f_item_cod_item));
            String nome = cursor.getString(
                    cursor.getColumnIndex(
                            AppSQLHelper.f_item_nome));
            String descricao = cursor.getString(
                    cursor.getColumnIndex(
                            AppSQLHelper.f_item_descricao));
            double valor = cursor.getFloat(
                    cursor.getColumnIndex(
                            AppSQLHelper.f_item_valor));

            TCardapioItem item = new TCardapioItem();
            item.setSubgrupo(subgrupo);
            item.setCodCardapioItem(cod_item);
            item.setNome(nome);
            item.setDescricao(descricao);
            item.setValor(valor);

            itens.add(item);
        }
        cursor.close();
        db.close();

        return itens;
    }

    public List<TCardapioSubGrupo> listaSubGrupo (TCardapioGrupo grupo) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM "+ AppSQLHelper.t_sub_grupo;
        String[] argumentos = null;
        if (grupo != null) {
            sql += " WHERE "+ AppSQLHelper.f_sub_grupo_grupo +" = ?";
            argumentos = new String[]{ String.valueOf(grupo.getCodGrupo()) };
        }

        sql += " ORDER BY "+ AppSQLHelper.f_sub_grupo_nome;

        Cursor cursor = db.rawQuery(sql, argumentos);

        List<TCardapioSubGrupo> itens = new ArrayList<>();
        while (cursor.moveToNext()) {
            int cod_sub_grupo = cursor.getInt(
                    cursor.getColumnIndex(
                            AppSQLHelper.f_sub_grupo_cod_sub_grupo));
            String nome = cursor.getString(
                    cursor.getColumnIndex(
                            AppSQLHelper.f_sub_grupo_nome));

            TCardapioSubGrupo item = new TCardapioSubGrupo();
            item.setGrupo(grupo);
            item.setCodSubGrupo(cod_sub_grupo);
            item.setNome(nome);
            itens.add(item);
        }
        cursor.close();
        db.close();

        return itens;
    }

    public int inserirPedido(TPedido p) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.f_ped_nome, p.getCliente().getNome());
        cv.put(AppSQLHelper.f_ped_endereco, p.getCliente().getEndereco());
        cv.put(AppSQLHelper.f_ped_numero, p.getCliente().getNumero());
        cv.put(AppSQLHelper.f_ped_complemento, p.getCliente().getComplemento());
        cv.put(AppSQLHelper.f_ped_bairro, p.getCliente().getBairro());
        cv.put(AppSQLHelper.f_ped_cidade, p.getCliente().getCidade());;
        cv.put(AppSQLHelper.f_ped_uf, p.getCliente().getUf());;
        cv.put(AppSQLHelper.f_ped_email, p.getCliente().getEmail());;
        cv.put(AppSQLHelper.f_ped_telefone, p.getCliente().getTelefone());;
        cv.put(AppSQLHelper.f_ped_delivery, p.isDelivery());
        cv.put(AppSQLHelper.f_ped_taxa, p.getTaxa());
        cv.put(AppSQLHelper.f_ped_datahora, p.getDatahora());

        int id = (int) db.insert(AppSQLHelper.t_pedido, null, cv);
        p.setId_pedido(id);
        db.close();
        return id;
    }

    public int inserirCliente(TCliente c) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.f_cli_nome, c.getNome());
        cv.put(AppSQLHelper.f_cli_endereco, c.getEndereco());
        cv.put(AppSQLHelper.f_cli_numero, c.getNumero());
        cv.put(AppSQLHelper.f_cli_complemento, c.getComplemento());
        cv.put(AppSQLHelper.f_cli_bairro, c.getBairro());
        cv.put(AppSQLHelper.f_cli_cidade, c.getCidade());;
        cv.put(AppSQLHelper.f_cli_uf, c.getUf());;
        cv.put(AppSQLHelper.f_cli_email, c.getEmail());;
        cv.put(AppSQLHelper.f_cli_telefone, c.getTelefone());;

        int id = (int) db.insert(AppSQLHelper.t_cliente, null, cv);
        c.setId_cliente(id);
        db.close();
        return id;
    }

    public List<TCliente> listaCliente () {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM "+ AppSQLHelper.t_cliente;
        Cursor cursor = db.query(AppSQLHelper.t_cliente, null, null, null, null, null, null);
        List<TCliente> itens = new ArrayList<>();

        while (cursor.moveToNext()) {
            TCliente item = new TCliente();
            item.setId_cliente(cursor.getInt(cursor.getColumnIndex(AppSQLHelper.f_cli_id)));
            item.setNome(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_nome)));
            item.setCep(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_cep)));
            item.setEndereco(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_endereco)));
            item.setNumero(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_numero)));
            item.setComplemento(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_complemento)));
            item.setBairro(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_bairro)));
            item.setCidade(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_cidade)));
            item.setUf(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_uf)));
            item.setEmail(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_email)));
            item.setTelefone(cursor.getString(cursor.getColumnIndex(AppSQLHelper.f_cli_telefone)));
            itens.add(item);
        }
        cursor.close();
        db.close();
        return itens;
    }

    public List<TItemTela> retornarItensPorGrupo(int id_grupo){
        List<TItemTela> itenstela = new ArrayList<TItemTela>();

        TCardapioGrupo grupo = new TCardapioGrupo(); // cria um obj grupo com o código enviado para poder passar para lista
        grupo.setCodGrupo(id_grupo); // grupo de pizzas, código está fixo, verificar

        List<TCardapioSubGrupo> subgrupos = listaSubGrupo(grupo); // percorre subgrupos do grupo informado e retorna or itens
        for(int i = 0 ; i < subgrupos.size(); i++){
            List<TCardapioItem> itens = listaItem(subgrupos.get(i));
            for(int j = 0 ; j < itens.size(); j++){
                TItemTela linha = new TItemTela();
                linha.setCardapio_item(itens.get(j));
                itenstela.add(linha);
            }
        }
        return itenstela;
    }

    public List<TItemTela> retornarItensPorSubGrupo(int id_subgrupo){
        List<TItemTela> itenstela = new ArrayList<TItemTela>();

        TCardapioSubGrupo subgrupo = new TCardapioSubGrupo(); // cria um obj subgrupo com o código enviado para poder passar para lista
        subgrupo.setCodSubGrupo(id_subgrupo);

        List<TCardapioItem> itens = listaItem(subgrupo);
        for(int j = 0 ; j < itens.size(); j++){
            TItemTela linha = new TItemTela();
            linha.setCardapio_item(itens.get(j));
            itenstela.add(linha);
        }
        return itenstela;
    }


    public int inserirPedidoItem(TPedidoItem obj) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AppSQLHelper.f_ped_item_pedido_id, obj.getId_pedido());
        cv.put(AppSQLHelper.f_ped_item_quantidade, obj.getQuantidade());
        cv.put(AppSQLHelper.f_ped_item_valor, obj.getValor());
        cv.put(AppSQLHelper.f_ped_item_tamanho, obj.getTamanho());
        cv.put(AppSQLHelper.f_ped_item_obs, obj.getObservacao());

        int id = (int) db.insert(AppSQLHelper.t_pedido_item, null, cv);
        obj.setId_item(id);
        db.close();
        return id;
    }


    // se não existir o item irá criar
    public int inserirPedidoSubItem(TPedidoDetalhe obj) {
        SQLiteDatabase db = helper.getWritableDatabase();

        int id_item;
        ContentValues cv = new ContentValues();
        if (obj.getId_item() == -1){
            TPedidoItem pedidoItem = new TPedidoItem();
            pedidoItem.setQuantidade(1);
            pedidoItem.setTamanho(-1);
            pedidoItem.setObservacao("");
            pedidoItem.setValor(obj.getValor());
            id_item = inserirPedidoItem(pedidoItem);
        } else
        {
            id_item = obj.getId_item();
        }

        cv.put(AppSQLHelper.f_ped_detalhe_cardapio_id, obj.getCardapio_item().getCodCardapioItem());
        cv.put(AppSQLHelper.f_ped_detalhe_valor, obj.getValor());
        cv.put(AppSQLHelper.f_ped_detalhe_ped_itens_id, id_item);

        int id = (int) db.insert(AppSQLHelper.t_pedido_item, null, cv);
        obj.setId_detalhe(id);
        db.close();
        return id;
    }

}
