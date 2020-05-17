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
import br.com.sysfarma.activity.TipoMedicamentoActivity;
import br.com.sysfarma.dao.TipoMedicamentoDAO;
import br.com.sysfarma.domain.TipoMedicamento;

public class TipoMedicamentoDialog {
    private Context context;
    private TipoMedicamentoDAO tipoMedicamentoDAO;
    private TipoMedicamentoActivity tipoMedicamentoActivity;
    private View dialogoView;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;

    private EditText inputNome;
    private String nome;


    public TipoMedicamentoDialog(TipoMedicamentoActivity tipoMedicamentoActivity, Context context, TipoMedicamentoDAO tipoMedicamentoDAO){
        this.context = context;
        this.tipoMedicamentoDAO = tipoMedicamentoDAO;
        this.tipoMedicamentoActivity = tipoMedicamentoActivity;

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this.context);
        dialogoView = layoutInflaterAndroid.inflate(R.layout.dialog_tipomedicamento, null);

        definirEditText();
        alertDialogBuilder = new AlertDialog.Builder(tipoMedicamentoActivity);
    }

    public void dialogoAdicionar(){
        componentesDoDialogoAdicionar();
        onClickBotaoAdicionar();
    }

    public void dialogoAlterar(TipoMedicamento tipoMedicamento){
        componenetesDoDialogoAlterar();
        inserirValorNoEditText(tipoMedicamento);

        onClickBotaoExcluir(tipoMedicamento.getId());
        onClickBotaoAlterar(tipoMedicamento);
    }



    private void definirEditText(){
        inputNome = (EditText) dialogoView.findViewById(R.id.input_nome);
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

    private void onClickBotaoAlterar(final TipoMedicamento tipoMedicamento){
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()){
                    converterEditTextParaString();
                    alterarBD(tipoMedicamento);
                }
            }
        });
    }

    private void onClickBotaoExcluir(final int id){
        alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipoMedicamentoDAO.deletar(id);
                tipoMedicamentoActivity.atualizarListaRecyclerView();
                alertDialog.dismiss();
            }
        });
    }

    private boolean validarCampos(){
        if(validaCampoObrigatorio(inputNome)){
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
    }

    private void inserirBD(){

        try {
            tipoMedicamentoDAO.inserir(getTipoMedicamentoInserir());
            tipoMedicamentoActivity.atualizarListaRecyclerView();
            alertDialog.dismiss();
        }catch (SQLException ex){
            toast(R.string.erro_inserir_sql);
        }

    }

    private void alterarBD(TipoMedicamento tipoMedicamento){
        tipoMedicamentoDAO.alterar(getTipoMedicamentoAlterar(tipoMedicamento));
        tipoMedicamentoActivity.atualizarListaRecyclerView();
        alertDialog.dismiss();
    }

    private TipoMedicamento getTipoMedicamentoInserir(){
        return new TipoMedicamento(null, nome);
    }

    private TipoMedicamento getTipoMedicamentoAlterar(TipoMedicamento tipoMedicamento){
        return new TipoMedicamento(tipoMedicamento.getId(), nome);
    }

    private void inserirValorNoEditText(TipoMedicamento tipoMedicamento){
        inputNome.setText(tipoMedicamento.getNome());
    }

    private void toast(int mensagem){
        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
    }
}
