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
import br.com.sysfarma.activity.FornecedorActivity;
import br.com.sysfarma.dao.FornecedorDAO;
import br.com.sysfarma.dialog.FornecedorDialog;
import br.com.sysfarma.domain.Fornecedor;

public class FornecedorAdapter extends RecyclerView.Adapter<FornecedorAdapter.ViewHolderFornecedor> {

    private List<Fornecedor> fornecedors;
    private FornecedorDAO fornecedorDAO;
    private FornecedorActivity fornecedorActivity;

    public FornecedorAdapter(FornecedorActivity fornecedorActivity, FornecedorDAO fornecedorDAO){
        this.fornecedorActivity = fornecedorActivity;
        this.fornecedorDAO = fornecedorDAO;
        this.fornecedors = fornecedorDAO.getAll();
    }

    @NonNull
    @Override
    public FornecedorAdapter.ViewHolderFornecedor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return referenciaLayoutRecyclerView(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull FornecedorAdapter.ViewHolderFornecedor holder, int position) {

        if(fornecedors.size() > 0){
            Fornecedor fornecedor = fornecedors.get(position);

            holder.textoPrincipal.setText(fornecedor.getNome());
            CharSequence charSequenceTextoSecundario = fornecedor.getCnpj();
            holder.textoSecundario.setText(charSequenceTextoSecundario);
        }
    }

    @Override
    public int getItemCount() {
        return fornecedors.size();
    }


    private ViewHolderFornecedor referenciaLayoutRecyclerView(ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listaView = layoutInflater.inflate(R.layout.list, parent, false);

        return new ViewHolderFornecedor(listaView, parent.getContext());
    }




    class ViewHolderFornecedor extends RecyclerView.ViewHolder{

        private TextView textoPrincipal;
        private TextView textoSecundario;
        private final Context context;

        ViewHolderFornecedor(@NonNull View itemView, final Context context) {
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
                    chamarDialogoAlterar(retornarPosicaoDoFornecedor());
                }
            });
        }

        private Fornecedor retornarPosicaoDoFornecedor(){
            if(fornecedors.size() > 0){
                return fornecedors.get(getLayoutPosition());
            }else {
                return new Fornecedor();
            }
        }

        private void chamarDialogoAlterar(Fornecedor fornecedor){
            FornecedorDialog fornecedorDialog = new FornecedorDialog(fornecedorActivity, context, fornecedorDAO);
            fornecedorDialog.dialogoAlterar(fornecedor);
        }
    }
}
