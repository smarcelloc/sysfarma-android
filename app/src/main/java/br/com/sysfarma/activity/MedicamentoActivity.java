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
import br.com.sysfarma.adapter.MedicamentoAdapter;
import br.com.sysfarma.dao.ClienteDAO;
import br.com.sysfarma.dao.FornecedorDAO;
import br.com.sysfarma.dao.MedicamentoDAO;
import br.com.sysfarma.dao.TipoMedicamentoDAO;
import br.com.sysfarma.dialog.MedicamentoDialog;
import br.com.sysfarma.domain.TipoMedicamento;
import br.com.sysfarma.factory.Database;

public class MedicamentoActivity extends AppCompatActivity {

    private MedicamentoDialog medicamentoDialog;
    private MedicamentoDAO medicamentoDAO;

    private FornecedorDAO fornecedorDAO;
    private TipoMedicamentoDAO tipoMedicamentoDAO;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);
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
            medicamentoDAO = new MedicamentoDAO(conexao);
            fornecedorDAO = new FornecedorDAO(conexao);
            tipoMedicamentoDAO = new TipoMedicamentoDAO(conexao);
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
                medicamentoDialog = new MedicamentoDialog(MedicamentoActivity.this, getApplicationContext(), medicamentoDAO, fornecedorDAO.getAll(), tipoMedicamentoDAO.getAll());
                medicamentoDialog.dialogoAdicionar();
            }
        });
    }

    private void listarRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_medicamento);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MedicamentoAdapter(MedicamentoActivity.this, medicamentoDAO, fornecedorDAO, tipoMedicamentoDAO));
        recyclerView.setHasFixedSize(true);
    }

    public void atualizarListaRecyclerView(){
        recyclerView.setAdapter(new MedicamentoAdapter(MedicamentoActivity.this, medicamentoDAO, fornecedorDAO, tipoMedicamentoDAO));
    }

    private void toast(int mensagem){
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
    }
}
