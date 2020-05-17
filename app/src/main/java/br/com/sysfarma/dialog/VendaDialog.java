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
import br.com.sysfarma.activity.VendaActivity;
import br.com.sysfarma.dao.VendaDAO;
import br.com.sysfarma.domain.Cliente;
import br.com.sysfarma.domain.Venda;

public class VendaDialog {
    private Context context;
    private VendaDAO vendaDAO;
    private VendaActivity vendaActivity;
    private View dialogoView;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;

    private List<Cliente> clientes;

    private EditText inputTotal;
    private Spinner spinnerCliente;

    private Cliente cliente;
    private Double total;


    public VendaDialog(VendaActivity vendaActivity, Context context, VendaDAO vendaDAO, List<Cliente> clientes){
        this.context = context;
        this.vendaDAO = vendaDAO;
        this.vendaActivity = vendaActivity;
        this.clientes = clientes;

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this.context);
        dialogoView = layoutInflaterAndroid.inflate(R.layout.dialog_venda, null);

        definirEditText();
        alertDialogBuilder = new AlertDialog.Builder(vendaActivity);
        listarSpinnerCliente();
    }

    public void dialogoAdicionar(){
        componentesDoDialogoAdicionar();
        onClickBotaoAdicionar();
    }

    public void dialogoAlterar(Venda venda){
        componenetesDoDialogoAlterar();
        inserirValorNoEditText(venda);
        selecionarSprinnerCliente(venda.getCliente());
        onClickBotaoExcluir(venda.getId());
        onClickBotaoAlterar(venda);
    }


    private void selecionarSprinnerCliente(Cliente cliente){
        int i = 1;
        int posicao = 0;
        for (Cliente c: clientes) {
            if(c.getId().equals(cliente.getId())){
                posicao = i;
                break;
            }
            i++;
        }
        spinnerCliente.setSelection(posicao);
    }

    private void listarSpinnerCliente(){
        List<String> nomesClientes = new ArrayList<String>();

        nomesClientes.add("Não existe cliente");

        if(clientes.size() > 0) {

            for (Cliente c : clientes) {
                nomesClientes.add("ID: " + c.getId() + " nome: " + c.getNome());
            }
        }

            ArrayAdapter<String> clienteArrayAdapter = new ArrayAdapter<>(this.context, android.R.layout.simple_spinner_dropdown_item, nomesClientes);
            clienteArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerCliente.setAdapter(clienteArrayAdapter);

            if(clientes.size() > 0){
                spinnerCliente.setSelection(1);
            }

    }


    private void definirEditText(){
        inputTotal = (EditText) dialogoView.findViewById(R.id.input_preco);
        spinnerCliente = (Spinner) dialogoView.findViewById(R.id.spinner_fornecedor);
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

    private void onClickBotaoAlterar(final Venda venda){
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()){
                    alterarBD(venda);
                }
            }
        });
    }

    private void onClickBotaoExcluir(final int id){
        alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vendaDAO.deletar(id);
                vendaActivity.atualizarListaRecyclerView();
                alertDialog.dismiss();
            }
        });
    }

    private boolean validarCampos(){
        if(validarCampoDouble(inputTotal)){
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
            total = numeroDecimal;
            return false;
        }

        return true;
    }

    private void inserirBD(){

        try {

            selectedItemPosicaoSnipper();
            vendaDAO.inserir(getVendaInserir());
            vendaActivity.atualizarListaRecyclerView();
            alertDialog.dismiss();
        }catch (SQLException ex){
            toast(R.string.erro_inserir_sql);
        }

    }

    private void alterarBD(Venda venda){
        selectedItemPosicaoSnipper();
        vendaDAO.alterar(getVendaAlterar(venda));
        vendaActivity.atualizarListaRecyclerView();
        alertDialog.dismiss();
    }

    private void selectedItemPosicaoSnipper(){
        if(spinnerCliente.getSelectedItemPosition() == 0){
            cliente = new Cliente(null, null,null, null);
        }else{
            cliente = clientes.get(spinnerCliente.getSelectedItemPosition() - 1);
        }
    }

    private Venda getVendaInserir(){
        return new Venda(null, total, cliente);
    }

    private Venda getVendaAlterar(Venda venda){
        return new Venda(venda.getId(), total, cliente);
    }

    private void inserirValorNoEditText(Venda venda){
        inputTotal.setText(venda.getTotal().toString());
    }

    private void toast(int mensagem){
        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
    }
}
