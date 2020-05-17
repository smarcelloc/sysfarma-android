package br.com.sysfarma.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.sysfarma.R;
import br.com.sysfarma.activity.VendaActivity;
import br.com.sysfarma.dao.ClienteDAO;
import br.com.sysfarma.dao.VendaDAO;
import br.com.sysfarma.dialog.VendaDialog;
import br.com.sysfarma.domain.Cliente;
import br.com.sysfarma.domain.Venda;

public class VendaAdapter extends RecyclerView.Adapter<VendaAdapter.ViewHolderVenda> {

    private List<Venda> vendas;
    private VendaDAO vendaDAO;
    private ClienteDAO clienteDAO;
    private VendaActivity vendaActivity;

    public VendaAdapter(VendaActivity vendaActivity, VendaDAO vendaDAO, ClienteDAO clienteDAO){
        this.vendaActivity = vendaActivity;
        this.vendaDAO = vendaDAO;
        this.vendas = vendaDAO.getAll();
        this.clienteDAO = clienteDAO;
    }

    @NonNull
    @Override
    public VendaAdapter.ViewHolderVenda onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return referenciaLayoutRecyclerView(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull VendaAdapter.ViewHolderVenda holder, int position) {

        if(vendas.size() > 0){
            Venda venda = vendas.get(position);

            String nomeCliente = vendaDAO.BuscarClienteID(venda.getCliente().getId());

            if(TextUtils.isEmpty(nomeCliente)){
                nomeCliente = "Cliente Anônimo";
            }

            CharSequence charSequenceVenda = " R$ " + venda.getTotal() + " pelo " + nomeCliente;
            holder.textoPrincipal.setText(charSequenceVenda);
            holder.textoSecundario.setText(venda.getDataAtual());
        }
    }

    @Override
    public int getItemCount() {
        return vendas.size();
    }


    private ViewHolderVenda referenciaLayoutRecyclerView(ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listaView = layoutInflater.inflate(R.layout.list, parent, false);

        return new ViewHolderVenda(listaView, parent.getContext());
    }




    class ViewHolderVenda extends RecyclerView.ViewHolder{

        private TextView textoPrincipal;
        private TextView textoSecundario;
        private final Context context;

        ViewHolderVenda(@NonNull View itemView, final Context context) {
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
                    chamarDialogoAlterar(retornarPosicaoDoVenda());
                }
            });
        }

        private Venda retornarPosicaoDoVenda(){
            if(vendas.size() > 0){
                return vendas.get(getLayoutPosition());
            }else {
                return new Venda();
            }
        }

        private void chamarDialogoAlterar(Venda venda){
            VendaDialog vendaDialog = new VendaDialog(vendaActivity, context, vendaDAO, clienteDAO.getAll());
            vendaDialog.dialogoAlterar(venda);
        }
    }
}
