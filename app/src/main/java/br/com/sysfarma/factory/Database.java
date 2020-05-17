package br.com.sysfarma.factory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context, "sysfarma", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptCreateTable.CLIENTE);
        db.execSQL(ScriptCreateTable.FUNCIONARIO);
        db.execSQL(ScriptCreateTable.FORNECEDOR);
        db.execSQL(ScriptCreateTable.TIPO_MEDICAMENTO);
        db.execSQL(ScriptCreateTable.VENDA);
        db.execSQL(ScriptCreateTable.MEDICAMENTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
