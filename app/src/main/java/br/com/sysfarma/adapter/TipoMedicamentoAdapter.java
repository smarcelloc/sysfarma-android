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
import br.com.sysfarma.activity.TipoMedicamentoActivity;
import br.com.sysfarma.dao.TipoMedicamentoDAO;
import br.com.sysfarma.dialog.TipoMedicamentoDialog;
import br.com.sysfarma.domain.TipoMedicamento;

public class TipoMedicamentoAdapter extends RecyclerView.Adapter<TipoMedicamentoAdapter.ViewHolderTipoMedicamento> {

    private List<TipoMedicamento> tipoMedicamentos;
    private TipoMedicamentoDAO tipoMedicamentoDAO;
    private TipoMedicamentoActivity tipoMedicamentoActivity;

    public TipoMedicamentoAdapter(TipoMedicamentoActivity tipoMedicamentoActivity, TipoMedicamentoDAO tipoMedicamentoDAO){
        this.tipoMedicamentoActivity = tipoMedicamentoActivity;
        this.tipoMedicamentoDAO = tipoMedicamentoDAO;
        this.tipoMedicamentos = tipoMedicamentoDAO.getAll();
    }

    @NonNull
    @Override
    public TipoMedicamentoAdapter.ViewHolderTipoMedicamento onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return referenciaLayoutRecyclerView(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TipoMedicamentoAdapter.ViewHolderTipoMedicamento holder, int position) {

        if(tipoMedicamentos.size() > 0){
            TipoMedicamento tipoMedicamento = tipoMedicamentos.get(position);

            holder.textoPrincipal.setText(tipoMedicamento.getNome());
            holder.textoSecundario.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return tipoMedicamentos.size();
    }


    private ViewHolderTipoMedicamento referenciaLayoutRecyclerView(ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listaView = layoutInflater.inflate(R.layout.list, parent, false);

        return new ViewHolderTipoMedicamento(listaView, parent.getContext());
    }




    class ViewHolderTipoMedicamento extends RecyclerView.ViewHolder{

        private TextView textoPrincipal;
        private TextView textoSecundario;
        private final Context context;

        ViewHolderTipoMedicamento(@NonNull View itemView, final Context context) {
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
                    chamarDialogoAlterar(retornarPosicaoDoTipoMedicamento());
                }
            });
        }

        private TipoMedicamento retornarPosicaoDoTipoMedicamento(){
            if(tipoMedicamentos.size() > 0){
                return tipoMedicamentos.get(getLayoutPosition());
            }else {
                return new TipoMedicamento();
            }
        }

        private void chamarDialogoAlterar(TipoMedicamento tipoMedicamento){
            TipoMedicamentoDialog tipoMedicamentoDialog = new TipoMedicamentoDialog(tipoMedicamentoActivity, context, tipoMedicamentoDAO);
            tipoMedicamentoDialog.dialogoAlterar(tipoMedicamento);
        }
    }
}
