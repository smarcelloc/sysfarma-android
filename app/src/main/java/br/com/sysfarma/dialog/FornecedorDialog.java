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
import br.com.sysfarma.activity.FornecedorActivity;
import br.com.sysfarma.dao.FornecedorDAO;
import br.com.sysfarma.domain.Fornecedor;

public class FornecedorDialog {
    private Context context;
    private FornecedorDAO fornecedorDAO;
    private FornecedorActivity fornecedorActivity;
    private View dialogoView;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;

    private EditText inputNome;
    private EditText inputCnpj;

    private String nome;
    private String cnpj;


    public FornecedorDialog(FornecedorActivity fornecedorActivity, Context context, FornecedorDAO fornecedorDAO){
        this.context = context;
        this.fornecedorDAO = fornecedorDAO;
        this.fornecedorActivity = fornecedorActivity;

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this.context);
        dialogoView = layoutInflaterAndroid.inflate(R.layout.dialog_fornecedor, null);

        definirEditText();
        alertDialogBuilder = new AlertDialog.Builder(fornecedorActivity);
    }

    public void dialogoAdicionar(){
        componentesDoDialogoAdicionar();
        onClickBotaoAdicionar();
    }

    public void dialogoAlterar(Fornecedor fornecedor){
        componenetesDoDialogoAlterar();
        inserirValorNoEditText(fornecedor);

        onClickBotaoExcluir(fornecedor.getId());
        onClickBotaoAlterar(fornecedor);
    }



    private void definirEditText(){
        inputNome = (EditText) dialogoView.findViewById(R.id.input_nome);
        inputCnpj = (EditText) dialogoView.findViewById(R.id.input_cnpj);
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

    private void onClickBotaoAlterar(final Fornecedor fornecedor){
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()){
                    converterEditTextParaString();
                    alterarBD(fornecedor);
                }
            }
        });
    }

    private void onClickBotaoExcluir(final int id){
        alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fornecedorDAO.deletar(id);
                fornecedorActivity.atualizarListaRecyclerView();
                alertDialog.dismiss();
            }
        });
    }

    private boolean validarCampos(){
        if(validaCampoObrigatorio(inputNome) || validaCampoObrigatorio(inputCnpj)){
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
        cnpj = inputCnpj.getText().toString().trim();
    }

    private void inserirBD(){

        try {
            fornecedorDAO.inserir(getFornecedorInserir());
            fornecedorActivity.atualizarListaRecyclerView();
            alertDialog.dismiss();
        }catch (SQLException ex){
            toast(R.string.erro_inserir_sql);
        }

    }

    private void alterarBD(Fornecedor fornecedor){
        fornecedorDAO.alterar(getFornecedorAlterar(fornecedor));
        fornecedorActivity.atualizarListaRecyclerView();
        alertDialog.dismiss();
    }

    private Fornecedor getFornecedorInserir(){
        return new Fornecedor(null, nome, cnpj);
    }

    private Fornecedor getFornecedorAlterar(Fornecedor fornecedor){
        return new Fornecedor(fornecedor.getId(), nome, cnpj);
    }

    private void inserirValorNoEditText(Fornecedor fornecedor){
        inputNome.setText(fornecedor.getNome());
        inputCnpj.setText(fornecedor.getCnpj());
    }

    private void toast(int mensagem){
        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
    }
}
