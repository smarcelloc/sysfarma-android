package br.com.sysfarma.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.sysfarma.domain.TipoMedicamento;

public class TipoMedicamentoDAO {

    private SQLiteDatabase conexao;


    public TipoMedicamentoDAO(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void inserir(TipoMedicamento tipoMedicamento){
        conexao.insertOrThrow("TipoMedicamento", null, valorAdd(tipoMedicamento));
    }

    public void alterar(TipoMedicamento tipoMedicamento){
        conexao.update("TipoMedicamento", valor(tipoMedicamento), "id == ?", whereArgsID(tipoMedicamento.getId()));
    }

    public void deletar(int id){
        conexao.delete("TipoMedicamento", "id == ?", whereArgsID(id));
    }

    public List<TipoMedicamento> getAll(){
        List<TipoMedicamento> tipoMedicamentos = new ArrayList<TipoMedicamento>();

        @SuppressLint("Recycle")
        Cursor cursor = conexao.rawQuery("SELECT * FROM TipoMedicamento ORDER BY id DESC", null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                tipoMedicamentos.add(inserirTipoMedicamentoNaConsulta(cursor));
            }while (cursor.moveToNext());
        }

        return tipoMedicamentos;
    }


    private String[] whereArgsID(int id){
        return new String[]{String.valueOf(id)};
    }

    private ContentValues valorAdd(TipoMedicamento tipoMedicamento){
        ContentValues values = new ContentValues();
        values.put("nome", tipoMedicamento.getNome());

        return values;
    }

    private ContentValues valor(TipoMedicamento tipoMedicamento){
        ContentValues values = new ContentValues();
        values.put("id", tipoMedicamento.getId());
        values.put("nome", tipoMedicamento.getNome());

        return values;
    }

    private TipoMedicamento inserirTipoMedicamentoNaConsulta(Cursor cursor){
        TipoMedicamento tipoMedicamento = new TipoMedicamento();
        tipoMedicamento.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        tipoMedicamento.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));

        return tipoMedicamento;
    }
}
