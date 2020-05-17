package br.com.sysfarma.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.sysfarma.domain.Cliente;

public class ClienteDAO {

    private SQLiteDatabase conexao;


    public ClienteDAO(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void inserir(Cliente cliente){
        conexao.insertOrThrow("Cliente", null, valorAdd(cliente));
    }

    public void alterar(Cliente cliente){
        conexao.update("Cliente", valor(cliente), "id == ?", whereArgsID(cliente.getId()));
    }

    public void deletar(int id){
        conexao.delete("Cliente", "id == ?", whereArgsID(id));
    }

    public List<Cliente> getAll(){
        List<Cliente> clientes = new ArrayList<Cliente>();

        @SuppressLint("Recycle")
        Cursor cursor = conexao.rawQuery("SELECT * FROM Cliente ORDER BY id DESC", null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                clientes.add(inserirClienteNaConsulta(cursor));
            }while (cursor.moveToNext());
        }

        return clientes;
    }


    private String[] whereArgsID(int id){
        return new String[]{String.valueOf(id)};
    }

    private ContentValues valorAdd(Cliente cliente){
        ContentValues values = new ContentValues();
        values.put("nome", cliente.getNome());
        values.put("endereco", cliente.getEndereco());
        values.put("numero_endereco", cliente.getNumeroEndereco());

        return values;
    }

    private ContentValues valor(Cliente cliente){
        ContentValues values = new ContentValues();
        values.put("id", cliente.getId());
        values.put("nome", cliente.getNome());
        values.put("endereco", cliente.getEndereco());
        values.put("numero_endereco", cliente.getNumeroEndereco());

        return values;
    }

    private Cliente inserirClienteNaConsulta(Cursor cursor){
        Cliente cliente = new Cliente();
        cliente.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        cliente.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
        cliente.setEndereco(cursor.getString(cursor.getColumnIndexOrThrow("endereco")));
        cliente.setNumeroEndereco(cursor.getString(cursor.getColumnIndexOrThrow("numero_endereco")));

        return cliente;
    }
}
