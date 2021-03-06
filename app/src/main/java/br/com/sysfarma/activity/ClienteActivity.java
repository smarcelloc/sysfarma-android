package br.com.sysfarma.activity;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import br.com.sysfarma.R;
import br.com.sysfarma.adapter.ClienteAdapter;
import br.com.sysfarma.dao.ClienteDAO;
import br.com.sysfarma.dialog.ClienteDialog;
import br.com.sysfarma.factory.Database;

public class ClienteActivity extends AppCompatActivity {

    private ClienteDialog clienteDialog;
    private ClienteDAO clienteDAO;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        criarConexao();

        listarRecyclerView();
        botaoVoltarNoToolbar(toolbar);
        botaoAdicionar();

    }


    private void criarConexao(){
        try{
            SQLiteDatabase conexao = new Database(this).getWritableDatabase();
            clienteDAO = new ClienteDAO(conexao);
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
                clienteDialog = new ClienteDialog(ClienteActivity.this, getApplicationContext(), clienteDAO);
                clienteDialog.dialogoAdicionar();
            }
        });
    }

    private void listarRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_cliente);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ClienteAdapter(ClienteActivity.this, clienteDAO));
        recyclerView.setHasFixedSize(true);
    }

    public void atualizarListaRecyclerView(){
        recyclerView.setAdapter(new ClienteAdapter(ClienteActivity.this, clienteDAO));
    }

    private void toast(int mensagem){
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
    }
}
