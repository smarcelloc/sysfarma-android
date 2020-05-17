package br.com.sysfarma.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.sysfarma.domain.Funcionario;

public class FuncionarioDAO {

    private SQLiteDatabase conexao;


    public FuncionarioDAO(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void inserir(Funcionario funcionario){
        conexao.insertOrThrow("Funcionario", null, valorAdd(funcionario));
    }

    public void alterar(Funcionario funcionario){
        conexao.update("Funcionario", valor(funcionario), "id == ?", whereArgsID(funcionario.getId()));
    }

    public void deletar(int id){
        conexao.delete("Funcionario", "id == ?", whereArgsID(id));
    }

    public List<Funcionario> getAll(){
        List<Funcionario> funcionarios = new ArrayList<Funcionario>();

        @SuppressLint("Recycle")
        Cursor cursor = conexao.rawQuery("SELECT * FROM Funcionario ORDER BY id DESC", null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                funcionarios.add(inserirFuncionarioNaConsulta(cursor));
            }while (cursor.moveToNext());
        }

        return funcionarios;
    }


    private String[] whereArgsID(int id){
        return new String[]{String.valueOf(id)};
    }

    private ContentValues valorAdd(Funcionario funcionario){
        ContentValues values = new ContentValues();
        values.put("nome", funcionario.getNome());
        values.put("email", funcionario.getEmail());

        return values;
    }

    private ContentValues valor(Funcionario funcionario){
        ContentValues values = new ContentValues();
        values.put("id", funcionario.getId());
        values.put("nome", funcionario.getNome());
        values.put("email", funcionario.getEmail());

        return values;
    }

    private Funcionario inserirFuncionarioNaConsulta(Cursor cursor){
        Funcionario funcionario = new Funcionario();
        funcionario.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        funcionario.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
        funcionario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));

        return funcionario;
    }
}
