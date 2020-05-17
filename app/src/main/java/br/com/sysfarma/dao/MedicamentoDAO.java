package br.com.sysfarma.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.sysfarma.domain.Medicamento;

public class MedicamentoDAO {

    private SQLiteDatabase conexao;


    public MedicamentoDAO(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void inserir(Medicamento medicamento){
        conexao.insertOrThrow("Medicamento", null, valorAdd(medicamento));
    }

    public void alterar(Medicamento medicamento){
        conexao.update("Medicamento", valor(medicamento), "id == ?", whereArgsID(medicamento.getId()));
    }

    public void deletar(int id){
        conexao.delete("Medicamento", "id == ?", whereArgsID(id));
    }

    public List<Medicamento> getAll(){
        List<Medicamento> medicamentos = new ArrayList<Medicamento>();

        @SuppressLint("Recycle")
        Cursor cursor = conexao.rawQuery("SELECT * FROM Medicamento ORDER BY id DESC", null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                medicamentos.add(inserirMedicamentoNaConsulta(cursor));
            }while (cursor.moveToNext());
        }

        return medicamentos;
    }

    public String BuscarFornecedorID(int id){
        @SuppressLint("Recycle")
        Cursor cursor = conexao.rawQuery("SELECT nome FROM Fornecedor WHERE id == ?", whereArgsID(id));

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            return cursor.getString(cursor.getColumnIndexOrThrow("nome"));
        }

        return null;
    }

    public String BuscarTipoMedicamentoID(int id){
        @SuppressLint("Recycle")
        Cursor cursor = conexao.rawQuery("SELECT nome FROM TipoMedicamento WHERE id == ?", whereArgsID(id));

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            return cursor.getString(cursor.getColumnIndexOrThrow("nome"));
        }

        return null;
    }

    private String[] whereArgsID(int id){
        return new String[]{String.valueOf(id)};
    }

    private ContentValues valorAdd(Medicamento medicamento){
        ContentValues values = new ContentValues();
        values.put("nome", medicamento.getNome());
        values.put("preco", medicamento.getPreco());
        values.put("fornecedor_id", medicamento.getFornecedor().getId());
        values.put("tipomedicamento_id", medicamento.getTipoMedicamento().getId());

        return values;
    }

    private ContentValues valor(Medicamento medicamento){
        ContentValues values = new ContentValues();
        values.put("id", medicamento.getId());
        values.put("nome", medicamento.getNome());
        values.put("preco", medicamento.getPreco());
        values.put("fornecedor_id", medicamento.getFornecedor().getId());
        values.put("tipomedicamento_id", medicamento.getTipoMedicamento().getId());
        return values;
    }

    private Medicamento inserirMedicamentoNaConsulta(Cursor cursor){
        Medicamento medicamento = new Medicamento();
        medicamento.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        medicamento.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
        medicamento.setPreco(cursor.getDouble(cursor.getColumnIndexOrThrow("preco")));
        medicamento.getFornecedor().setId(cursor.getInt(cursor.getColumnIndexOrThrow("fornecedor_id")));
        medicamento.getTipoMedicamento().setId(cursor.getInt(cursor.getColumnIndexOrThrow("tipomedicamento_id")));

        return medicamento;
    }
}
