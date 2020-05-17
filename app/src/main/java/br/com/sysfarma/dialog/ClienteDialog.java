package br.com.sysfarma.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.database.SQLException;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import br.com.sysfarma.R;
import br.com.sysfarma.activity.ClienteActivity;
import br.com.sysfarma.dao.ClienteDAO;
import br.com.sysfarma.domain.Cliente;

public class ClienteDialog {
    private Context context;
    private ClienteDAO clienteDAO;
    private ClienteActivity clienteActivity;
    private View dialogoView;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;

    private EditText inputNome;
    private EditText inputEndereco;
    private EditText inputNumeroEndereco;

    private String nome;
    private String endereco;
    private String numeroEndereco;


    public ClienteDialog(ClienteActivity clienteActivity, Context context, ClienteDAO clienteDAO){
        this.context = context;
        this.clienteDAO = clienteDAO;
        this.clienteActivity = clienteActivity;

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this.context);
        dialogoView = layoutInflaterAndroid.inflate(R.layout.dialog_cliente, null);

        definirEditText();
        alertDialogBuilder = new AlertDialog.Builder(clienteActivity);
    }

    public void dialogoAdicionar(){
        componentesDoDialogoAdicionar();
        onClickBotaoAdicionar();
    }

    public void dialogoAlterar(Cliente cliente){
        componenetesDoDialogoAlterar();
        inserirValorNoEditText(cliente);

        onClickBotaoExcluir(cliente.getId());
        onClickBotaoAlterar(cliente);
    }



    private void definirEditText(){
        inputNome = (EditText) dialogoView.findViewById(R.id.input_nome);
        inputEndereco = (EditText) dialogoView.findViewById(R.id.input_endereco);
        inputNumeroEndereco = (EditText) dialogoView.findViewById(R.id.input_n_endereco);
    }

    private void componentesDoDialogoAdicionar(){
        alertDialogBuilder.setView(dialogoView);
        alertDialogBuilder.setTitle(R.string.dialogo_adicionar);
        alertDialogBuilder.setPositiveButton(R.string.dialogo_adicionar, null);

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void componenetesDoDialogoAlterar(){
        alertDialogBuilder.setView(dialogoView);
        alertDialogBuilder.setTitle(R.string.dialogo_exluir_alterar);
        alertDialogBuilder.setPositiveButton(R.string.dialogo_alterar, null);
        alertDialogBuilder.setNeutralButton(R.string.dialogo_deletar, null);

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void onClickBotaoAdicionar(){
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()){
                    converterEditTextParaString();
                    inserirBD();
                }
            }
        });
    }

    private void onClickBotaoAlterar(final Cliente cliente){
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()){
                    converterEditTextParaString();
                    alterarBD(cliente);
                }
            }
        });
    }

    private void onClickBotaoExcluir(final int id){
        alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clienteDAO.deletar(id);
                clienteActivity.atualizarListaRecyclerView();
                alertDialog.dismiss();
            }
        });
    }

    private boolean validarCampos(){
        if(validaCampoObrigatorio(inputNome) || validaCampoObrigatorio(inputEndereco) || validaCampoObrigatorio(inputNumeroEndereco)){
            toast(R.string.valida_campos_obrigatorios);
            return false;
        }
        return true;
    }

    private boolean validaCampoObrigatorio(EditText editText){
        return TextUtils.isEmpty(editText.getText().toString().trim());
    }

    private void converterEditTextParaString(){
        nome = inputNome.getText().toString().trim();
        endereco = inputEndereco.getText().toString().trim();
        numeroEndereco = inputNumeroEndereco.getText().toString().trim();
    }

    private void inserirBD(){

        try {
            clienteDAO.inserir(getClienteInserir());
            clienteActivity.atualizarListaRecyclerView();
            alertDialog.dismiss();
        }catch (SQLException ex){
            toast(R.string.erro_inserir_sql);
        }

    }

    private void alterarBD(Cliente cliente){
        clienteDAO.alterar(getClienteAlterar(cliente));
        clienteActivity.atualizarListaRecyclerView();
        alertDialog.dismiss();
    }

    private Cliente getClienteInserir(){
        return new Cliente(null, nome, endereco, numeroEndereco);
    }

    private Cliente getClienteAlterar(Cliente cliente){
        return new Cliente(cliente.getId(), nome, endereco, numeroEndereco);
    }

    private void inserirValorNoEditText(Cliente cliente){
        inputNome.setText(cliente.getNome());
        inputEndereco.setText(cliente.getEndereco());
        inputNumeroEndereco.setText(cliente.getNumeroEndereco());
    }

    private void toast(int mensagem){
        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
    }
}
