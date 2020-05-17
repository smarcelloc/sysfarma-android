package br.com.sysfarma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.sysfarma.R;
import br.com.sysfarma.activity.ClienteActivity;
import br.com.sysfarma.dao.ClienteDAO;
import br.com.sysfarma.dialog.ClienteDialog;
import br.com.sysfarma.domain.Cliente;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolderCliente> {

    private List<Cliente> clientes;
    private ClienteDAO clienteDAO;
    private ClienteActivity clienteActivity;

    public ClienteAdapter(ClienteActivity clienteActivity, ClienteDAO clienteDAO){
        this.clienteActivity = clienteActivity;
        this.clienteDAO = clienteDAO;
        this.clientes = clienteDAO.getAll();
    }

    @NonNull
    @Override
    public ClienteAdapter.ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return referenciaLayoutRecyclerView(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteAdapter.ViewHolderCliente holder, int position) {

        if(clientes.size() > 0){
            Cliente cliente = clientes.get(position);

            holder.textoPrincipal.setText(cliente.getNome());
            CharSequence charSequenceTextoSecundario = cliente.getEndereco() + " Nº " + cliente.getNumeroEndereco();
            holder.textoSecundario.setText(charSequenceTextoSecundario);
        }
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }


    private ViewHolderCliente referenciaLayoutRecyclerView(ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listaView = layoutInflater.inflate(R.layout.list, parent, false);

        return new ViewHolderCliente(listaView, parent.getContext());
    }




    class ViewHolderCliente extends RecyclerView.ViewHolder{

        private TextView textoPrincipal;
        private TextView textoSecundario;
        private final Context context;

        ViewHolderCliente(@NonNull View itemView, final Context context) {
            super(itemView);
            this.context = context;

            passarTextViewParaVariavel();
            onClickComponentesRecyclerView();
        }


        private void passarTextViewParaVariavel(){
            textoPrincipal = itemView.findViewById(R.id.textoPrimario);
            textoSecundario = itemView.findViewById(R.id.textoSecundario);
        }

        private void onClickComponentesRecyclerView(){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chamarDialogoAlterar(retornarPosicaoDoCliente());
                }
            });
        }

        private Cliente retornarPosicaoDoCliente(){
            if(clientes.size() > 0){
                return clientes.get(getLayoutPosition());
            }else {
                return new Cliente();
            }
        }

        private void chamarDialogoAlterar(Cliente cliente){
            ClienteDialog clienteDialog = new ClienteDialog(clienteActivity, context, clienteDAO);
            clienteDialog.dialogoAlterar(cliente);
        }
    }
}
