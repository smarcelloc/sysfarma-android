package br.com.sysfarma.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.sysfarma.domain.Venda;

public class VendaDAO {

    private SQLiteDatabase conexao;


    public VendaDAO(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void inserir(Venda venda){
        conexao.insertOrThrow("Venda", null, valorAdd(venda));
    }

    public void alterar(Venda venda){
        conexao.update("Venda", valor(venda), "id == ?", whereArgsID(venda.getId()));
    }

    public void deletar(int id){
        conexao.delete("Venda", "id == ?", whereArgsID(id));
    }

    public List<Venda> getAll(){
        List<Venda> vendas = new ArrayList<Venda>();

        @SuppressLint("Recycle")
        Cursor cursor = conexao.rawQuery("SELECT * FROM Venda ORDER BY id DESC", null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                vendas.add(inserirVendaNaConsulta(cursor));
            }while (cursor.moveToNext());
        }

        return vendas;
    }

    public String BuscarClienteID(int id){
        @SuppressLint("Recycle")
        Cursor cursor = conexao.rawQuery("SELECT nome FROM Cliente WHERE id == ?", whereArgsID(id));

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            return cursor.getString(cursor.getColumnIndexOrThrow("nome"));
        }

        return null;
    }


    private String[] whereArgsID(int id){
        return new String[]{String.valueOf(id)};
    }

    private ContentValues valorAdd(Venda venda){
        ContentValues values = new ContentValues();
        values.put("total", venda.getTotal());
        values.put("data_atual", venda.getDataAtual());
        values.put("cliente_id", venda.getCliente().getId());

        return values;
    }

    private ContentValues valor(Venda venda){
        ContentValues values = new ContentValues();
        values.put("id", venda.getId());
        values.put("total", venda.getTotal());
        values.put("cliente_id", venda.getCliente().getId());

        return values;
    }

    private Venda inserirVendaNaConsulta(Cursor cursor){
        Venda venda = new Venda();
        venda.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        venda.setTotal(cursor.getDouble(cursor.getColumnIndexOrThrow("total")));
        venda.getCliente().setId(cursor.getInt(cursor.getColumnIndexOrThrow("cliente_id")));
        venda.setDataAtual(cursor.getString(cursor.getColumnIndexOrThrow("data_atual")));

        return venda;
    }
}
