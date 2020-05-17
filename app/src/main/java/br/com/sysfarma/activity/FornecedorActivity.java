package br.com.sysfarma.activity;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.sysfarma.R;
import br.com.sysfarma.adapter.FornecedorAdapter;
import br.com.sysfarma.dao.FornecedorDAO;
import br.com.sysfarma.dialog.FornecedorDialog;
import br.com.sysfarma.factory.Database;

public class FornecedorActivity extends AppCompatActivity {

    private FornecedorDialog fornecedorDialog;
    private FornecedorDAO fornecedorDAO;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fornecedor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        criarConexao();

        listarRecyclerView();
        botaoVoltarNoToolbar(toolbar);
        botaoAdicionar();
    }


    private void criarConexao(){
        try{
            SQLiteDatabase conexao = new Database(FornecedorActivity.this).getWritableDatabase();
            fornecedorDAO = new FornecedorDAO(conexao);
        }catch (SQLException ex){
            toast(R.string.erro_conexao_sql);
        }
    }

    private void botaoVoltarNoToolbar(Toolbar toolbar){
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void botaoAdicionar(){
        FloatingActionButton fab = findViewById(R.id.btn_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fornecedorDialog = new FornecedorDialog(FornecedorActivity.this, getApplicationContext(), fornecedorDAO);
                fornecedorDialog.dialogoAdicionar();
            }
        });
    }

    private void listarRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_fornecedor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FornecedorAdapter(FornecedorActivity.this, fornecedorDAO));
        recyclerView.setHasFixedSize(true);
    }

    public void atualizarListaRecyclerView(){
        recyclerView.setAdapter(new FornecedorAdapter(FornecedorActivity.this, fornecedorDAO));
    }

    private void toast(int mensagem){
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
    }
}
