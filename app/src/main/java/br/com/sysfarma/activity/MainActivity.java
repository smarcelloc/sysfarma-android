package br.com.sysfarma.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import br.com.sysfarma.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        onClickBotaoCliente();
        onClickBotaoFuncionario();
        onClickBotaoFornecedor();
        onClickBotaoTipoMedicamento();
        onClickBotaoVenda();
        onClickBotaoMedicamento();
    }

    private void onClickBotaoCliente(){
        Button btnCliente = findViewById(R.id.btn_cliente);
        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ClienteActivity.class));
            }
        });
    }

    private void onClickBotaoFuncionario(){
        Button btnCliente = findViewById(R.id.btn_funcionario);
        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FuncionarioActivity.class));
            }
        });
    }

    private void onClickBotaoFornecedor(){
        Button btnCliente = findViewById(R.id.btn_fornec);
        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FornecedorActivity.class));
            }
        });
    }

    private void onClickBotaoTipoMedicamento(){
        Button btnCliente = findViewById(R.id.btn_tipo_medic);
        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TipoMedicamentoActivity.class));
            }
        });
    }

    private void onClickBotaoVenda(){
        Button btnCliente = findViewById(R.id.btn_venda);
        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, VendaActivity.class));
            }
        });
    }

    private void onClickBotaoMedicamento(){
        Button btnCliente = findViewById(R.id.btn_medic);
        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MedicamentoActivity.class));
            }
        });
    }
}
