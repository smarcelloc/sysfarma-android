package br.com.sysfarma.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.database.SQLException;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import br.com.sysfarma.R;
import br.com.sysfarma.activity.MedicamentoActivity;
import br.com.sysfarma.dao.MedicamentoDAO;
import br.com.sysfarma.domain.Fornecedor;
import br.com.sysfarma.domain.Medicamento;
import br.com.sysfarma.domain.TipoMedicamento;

public class MedicamentoDialog {
    private Context context;
    private MedicamentoDAO medicamentoDAO;
    private MedicamentoActivity medicamentoActivity;
    private View dialogoView;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;

    private List<Fornecedor> fornecedors;
    private List<TipoMedicamento> tipoMedicamentos;

    private EditText inputPreco;
    private EditText inputNome;
    private Spinner spinnerFornecedor;
    private Spinner spinnerTipoMedicamento;

    private Fornecedor fornecedor;
    private TipoMedicamento tipoMedicamento;
    private Double preco;
    private String nome;


    public MedicamentoDialog(MedicamentoActivity medicamentoActivity, Context context, MedicamentoDAO medicamentoDAO, List<Fornecedor> fornecedors, List<TipoMedicamento> tipoMedicamentos){
        this.context = context;
        this.medicamentoDAO = medicamentoDAO;
        this.medicamentoActivity = medicamentoActivity;
        this.tipoMedicamentos = tipoMedicamentos;
        this.fornecedors = fornecedors;

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this.context);
        dialogoView = layoutInflaterAndroid.inflate(R.layout.dialog_medicamento, null);

        definirEditText();
        alertDialogBuilder = new AlertDialog.Builder(medicamentoActivity);
        listarSpinnerFornecedor();
        listarSpinnerTipoMedicamento();
    }

    public void dialogoAdicionar(){
        componentesDoDialogoAdicionar();
        onClickBotaoAdicionar();
    }

    public void dialogoAlterar(Medicamento medicamento){
        componenetesDoDialogoAlterar();
        inserirValorNoEditText(medicamento);
        selecionarSprinnerFornecedor(medicamento.getFornecedor());
        selecionarSprinnerTipoMedicamento(medicamento.getTipoMedicamento());
        onClickBotaoExcluir(medicamento.getId());
        onClickBotaoAlterar(medicamento);
    }


    private void selecionarSprinnerFornecedor(Fornecedor fornecedor){
        int i = 1;
        int posicao = 0;
        for (Fornecedor f: fornecedors) {
            if(f.getId().equals(fornecedor.getId())){
                posicao = i;
                break;
            }
            i++;
        }
        spinnerFornecedor.setSelection(posicao);
    }

    private void selecionarSprinnerTipoMedicamento(TipoMedicamento tipoMedicamento){
        int i = 1;
        int posicao = 0;
        for (TipoMedicamento tm: tipoMedicamentos) {
            if(tm.getId().equals(tipoMedicamento.getId())){
                posicao = i;
                break;
            }
            i++;
        }
        spinnerTipoMedicamento.setSelection(posicao);
    }

    private void listarSpinnerFornecedor(){
        List<String> nomeFornecedor = new ArrayList<String>();

        nomeFornecedor.add("Não existe fornecedor");
        if(fornecedors.size() > 0) {
            for (Fornecedor f : fornecedors) {
                nomeFornecedor.add("ID: " + f.getId() + " nome: " + f.getNome());
            }
        }

        ArrayAdapter<String> fornecedorArrayAdapter = new ArrayAdapter<>(this.context, android.R.layout.simple_spinner_dropdown_item, nomeFornecedor);
        fornecedorArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFornecedor.setAdapter(fornecedorArrayAdapter);

        if(fornecedors.size() > 0){
            spinnerFornecedor.setSelection(1);
        }
    }

    private void listarSpinnerTipoMedicamento(){
        List<String> nomeTipoMedicamento = new ArrayList<String>();

        nomeTipoMedicamento.add("Não existe tipo");

        if(tipoMedicamentos.size() > 0) {
            for (TipoMedicamento tm : tipoMedicamentos) {
                nomeTipoMedicamento.add("ID: " + tm.getId() + " nome: " + tm.getNome());
            }
        }

        ArrayAdapter<String> tipoMedicamentoArrayAdapter = new ArrayAdapter<>(this.context, android.R.layout.simple_spinner_dropdown_item, nomeTipoMedicamento);
        tipoMedicamentoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTipoMedicamento.setAdapter(tipoMedicamentoArrayAdapter);

        if(tipoMedicamentos.size() > 0){
            spinnerTipoMedicamento.setSelection(1);
        }
    }


    private void definirEditText(){
        inputNome = (EditText) dialogoView.findViewById(R.id.input_nome);
        inputPreco = (EditText) dialogoView.findViewById(R.id.input_preco);
        spinnerFornecedor = (Spinner) dialogoView.findViewById(R.id.spinner_fornecedor);
        spinnerTipoMedicamento = (Spinner) dialogoView.findViewById(R.id.spinner_tipomedicamento);
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
                    inserirBD();
                }
            }
        });
    }

    private void onClickBotaoAlterar(final Medicamento medicamento){
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()){
                    alterarBD(medicamento);
                }
            }
        });
    }

    private void onClickBotaoExcluir(final int id){
        alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicamentoDAO.deletar(id);
                medicamentoActivity.atualizarListaRecyclerView();
                alertDialog.dismiss();
            }
        });
    }

    private boolean validarCampos(){
        if(validarCampoObrigatorio(inputNome)){
            toast(R.string.input_nome_obrigatorio);
            return false;
        }

        if(validarCampoDouble(inputPreco)){
            toast(R.string.total_validacao);
            return false;
        }

        return true;
    }

    private boolean validarCampoDouble(EditText editText){

        if(TextUtils.isEmpty(editText.getText())){
            return true;
        }

       Double numeroDecimal = Double.parseDouble(editText.getText().toString());
        if(numeroDecimal > 0){
            preco = numeroDecimal;
            return false;
        }

        return true;
    }

    private boolean validarCampoObrigatorio(EditText editText) {

        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            return true;
        }

        nome = inputNome.getText().toString();

        return false;
    }

    private void inserirBD(){

        try {

            selectedItemPosicaoSnipperFornecedor();
            selectedItemPosicaoSnipperTipoMedicamento();
            medicamentoDAO.inserir(getMedicamentoInserir());
            medicamentoActivity.atualizarListaRecyclerView();
            alertDialog.dismiss();
        }catch (SQLException ex){
            toast(R.string.erro_inserir_sql);
        }

    }

    private void alterarBD(Medicamento medicamento){
        selectedItemPosicaoSnipperFornecedor();
        selectedItemPosicaoSnipperTipoMedicamento();
        medicamentoDAO.alterar(getMedicamentoAlterar(medicamento));
        medicamentoActivity.atualizarListaRecyclerView();
        alertDialog.dismiss();
    }

    private void selectedItemPosicaoSnipperFornecedor(){
        if(spinnerFornecedor.getSelectedItemPosition() == 0){
            fornecedor = new Fornecedor(null, null,null);
        }else{
            fornecedor = fornecedors.get(spinnerFornecedor.getSelectedItemPosition() - 1);
        }
    }

    private void selectedItemPosicaoSnipperTipoMedicamento(){
        if(spinnerTipoMedicamento.getSelectedItemPosition() == 0){
            tipoMedicamento = new TipoMedicamento(null, null);
        }else{
            tipoMedicamento = tipoMedicamentos.get(spinnerTipoMedicamento.getSelectedItemPosition() - 1);
        }
    }

    private Medicamento getMedicamentoInserir(){
        return new Medicamento(null, nome, preco, fornecedor, tipoMedicamento);
    }

    private Medicamento getMedicamentoAlterar(Medicamento medicamento){
        return new Medicamento(medicamento.getId(), nome,preco, fornecedor, tipoMedicamento);
    }

    private void inserirValorNoEditText(Medicamento medicamento){
        inputNome.setText(medicamento.getNome());
        inputPreco.setText(medicamento.getPreco().toString());
    }

    private void toast(int mensagem){
        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
    }
}
