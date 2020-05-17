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
import br.com.sysfarma.activity.FuncionarioActivity;
import br.com.sysfarma.dao.FuncionarioDAO;
import br.com.sysfarma.domain.Funcionario;

public class FuncionarioDialog {
    private Context context;
    private FuncionarioDAO funcionarioDAO;
    private FuncionarioActivity funcionarioActivity;
    private View dialogoView;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;

    private EditText inputNome;
    private EditText inputEmail;

    private String nome;
    private String email;


    public FuncionarioDialog(FuncionarioActivity funcionarioActivity, Context context, FuncionarioDAO funcionarioDAO){
        this.context = context;
        this.funcionarioDAO = funcionarioDAO;
        this.funcionarioActivity = funcionarioActivity;

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this.context);
        dialogoView = layoutInflaterAndroid.inflate(R.layout.dialog_funcionario, null);

        definirEditText();
        alertDialogBuilder = new AlertDialog.Builder(funcionarioActivity);
    }

    public void dialogoAdicionar(){
        componentesDoDialogoAdicionar();
        onClickBotaoAdicionar();
    }

    public void dialogoAlterar(Funcionario funcionario){
        componenetesDoDialogoAlterar();
        inserirValorNoEditText(funcionario);

        onClickBotaoExcluir(funcionario.getId());
        onClickBotaoAlterar(funcionario);
    }



    private void definirEditText(){
        inputNome = (EditText) dialogoView.findViewById(R.id.input_nome);
        inputEmail = (EditText) dialogoView.findViewById(R.id.input_email);
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

    private void onClickBotaoAlterar(final Funcionario funcionario){
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()){
                    converterEditTextParaString();
                    alterarBD(funcionario);
                }
            }
        });
    }

    private void onClickBotaoExcluir(final int id){
        alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funcionarioDAO.deletar(id);
                funcionarioActivity.atualizarListaRecyclerView();
                alertDialog.dismiss();
            }
        });
    }

    private boolean validarCampos(){
        if(validaCampoObrigatorio(inputNome) || validaCampoObrigatorio(inputEmail)){
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
        email = inputEmail.getText().toString().trim();
    }

    private void inserirBD(){

        try {
            funcionarioDAO.inserir(getFuncionarioInserir());
            funcionarioActivity.atualizarListaRecyclerView();
            alertDialog.dismiss();
        }catch (SQLException ex){
            toast(R.string.erro_inserir_sql);
        }

    }

    private void alterarBD(Funcionario funcionario){
        funcionarioDAO.alterar(getFuncionarioAlterar(funcionario));
        funcionarioActivity.atualizarListaRecyclerView();
        alertDialog.dismiss();
    }

    private Funcionario getFuncionarioInserir(){
        return new Funcionario(null, nome, email);
    }

    private Funcionario getFuncionarioAlterar(Funcionario funcionario){
        return new Funcionario(funcionario.getId(), nome, email);
    }

    private void inserirValorNoEditText(Funcionario funcionario){
        inputNome.setText(funcionario.getNome());
        inputEmail.setText(funcionario.getEmail());
    }

    private void toast(int mensagem){
        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
    }
}
