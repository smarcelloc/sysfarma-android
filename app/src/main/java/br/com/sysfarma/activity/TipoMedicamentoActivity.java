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
import br.com.sysfarma.adapter.TipoMedicamentoAdapter;
import br.com.sysfarma.dao.TipoMedicamentoDAO;
import br.com.sysfarma.dialog.TipoMedicamentoDialog;
import br.com.sysfarma.factory.Database;

public class TipoMedicamentoActivity extends AppCompatActivity {

    private TipoMedicamentoDialog tipoMedicamentoDialog;
    private TipoMedicamentoDAO tipoMedicamentoDAO;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipomedicamento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        criarConexao();

        listarRecyclerView();
        botaoVoltarNoToolbar(toolbar);
        botaoAdicionar();
    }


    private void criarConexao(){
        try{
            SQLiteDatabase conexao = new Database(TipoMedicamentoActivity.this).getWritableDatabase();
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
                tipoMedicamentoDialog = new TipoMedicamentoDialog(TipoMedicamentoActivity.this, getApplicationContext(), tipoMedicamentoDAO);
                tipoMedicamentoDialog.dialogoAdicionar();
            }
        });
    }

    private void listarRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_tipoMedicamento);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TipoMedicamentoAdapter(TipoMedicamentoActivity.this, tipoMedicamentoDAO));
        recyclerView.setHasFixedSize(true);
    }

    public void atualizarListaRecyclerView(){
        recyclerView.setAdapter(new TipoMedicamentoAdapter(TipoMedicamentoActivity.this, tipoMedicamentoDAO));
    }

    private void toast(int mensagem){
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
    }
}
