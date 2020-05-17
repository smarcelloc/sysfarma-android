package br.com.sysfarma.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.sysfarma.domain.Fornecedor;

public class FornecedorDAO {

    private SQLiteDatabase conexao;


    public FornecedorDAO(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void inserir(Fornecedor fornecedor){
        conexao.insertOrThrow("Fornecedor", null, valorAdd(fornecedor));
    }

    public void alterar(Fornecedor fornecedor){
        conexao.update("Fornecedor", valor(fornecedor), "id == ?", whereArgsID(fornecedor.getId()));
    }

    public void deletar(int id){
        conexao.delete("Fornecedor", "id == ?", whereArgsID(id));
    }

    public List<Fornecedor> getAll(){
        List<Fornecedor> fornecedors = new ArrayList<Fornecedor>();

        @SuppressLint("Recycle")
        Cursor cursor = conexao.rawQuery("SELECT * FROM Fornecedor ORDER BY id DESC", null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                fornecedors.add(inserirFornecedorNaConsulta(cursor));
            }while (cursor.moveToNext());
        }

        return fornecedors;
    }


    private String[] whereArgsID(int id){
        return new String[]{String.valueOf(id)};
    }

    private ContentValues valorAdd(Fornecedor fornecedor){
        ContentValues values = new ContentValues();
        values.put("nome", fornecedor.getNome());
        values.put("cnpj", fornecedor.getCnpj());

        return values;
    }

    private ContentValues valor(Fornecedor fornecedor){
        ContentValues values = new ContentValues();
        values.put("id", fornecedor.getId());
        values.put("nome", fornecedor.getNome());
        values.put("cnpj", fornecedor.getCnpj());

        return values;
    }

    private Fornecedor inserirFornecedorNaConsulta(Cursor cursor){
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        fornecedor.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
        fornecedor.setCnpj(cursor.getString(cursor.getColumnIndexOrThrow("cnpj")));

        return fornecedor;
    }
}
