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
import br.com.sysfarma.activity.MedicamentoActivity;
import br.com.sysfarma.dao.ClienteDAO;
import br.com.sysfarma.dao.FornecedorDAO;
import br.com.sysfarma.dao.MedicamentoDAO;
import br.com.sysfarma.dao.TipoMedicamentoDAO;
import br.com.sysfarma.dialog.MedicamentoDialog;
import br.com.sysfarma.domain.Medicamento;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.ViewHolderMedicamento> {

    private List<Medicamento> medicamentos;
    private MedicamentoDAO medicamentoDAO;
    private FornecedorDAO fornecedorDAO;
    private TipoMedicamentoDAO tipoMedicamentoDAO;
    private MedicamentoActivity medicamentoActivity;

    public MedicamentoAdapter(MedicamentoActivity medicamentoActivity, MedicamentoDAO medicamentoDAO, FornecedorDAO fornecedorDAO, TipoMedicamentoDAO tipoMedicamentoDAO){
        this.medicamentoActivity = medicamentoActivity;
        this.medicamentoDAO = medicamentoDAO;
        this.medicamentos = medicamentoDAO.getAll();
        this.fornecedorDAO = fornecedorDAO;
        this.tipoMedicamentoDAO = tipoMedicamentoDAO;
    }

    @NonNull
    @Override
    public MedicamentoAdapter.ViewHolderMedicamento onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return referenciaLayoutRecyclerView(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoAdapter.ViewHolderMedicamento holder, int position) {

        if(medicamentos.size() > 0){
            Medicamento medicamento = medicamentos.get(position);

            String nomeFornecedor = medicamentoDAO.BuscarFornecedorID(medicamento.getFornecedor().getId());
            String nomeTipoMedicamento = medicamentoDAO.BuscarTipoMedicamentoID(medicamento.getTipoMedicamento().getId());

            if(TextUtils.isEmpty(nomeFornecedor)){
                nomeFornecedor = "Fornecedor Anônimo";
            }

            if(TextUtils.isEmpty(nomeTipoMedicamento)){
                nomeTipoMedicamento = "Sem tipo medicamento";
            }

            CharSequence charSequenceMedicamento = medicamento.getNome() + " R$ " + medicamento.getPreco();
            CharSequence charSequenceMedicamento2 = "Fornecedor: " + nomeFornecedor + "\nTipo: " + nomeTipoMedicamento;

            holder.textoPrincipal.setText(charSequenceMedicamento);
            holder.textoSecundario.setText(charSequenceMedicamento2);
        }
    }

    @Override
    public int getItemCount() {
        return medicamentos.size();
    }


    private ViewHolderMedicamento referenciaLayoutRecyclerView(ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listaView = layoutInflater.inflate(R.layout.list, parent, false);

        return new ViewHolderMedicamento(listaView, parent.getContext());
    }




    class ViewHolderMedicamento extends RecyclerView.ViewHolder{

        private TextView textoPrincipal;
        private TextView textoSecundario;
        private final Context context;

        ViewHolderMedicamento(@NonNull View itemView, final Context context) {
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
                    chamarDialogoAlterar(retornarPosicaoDoMedicamento());
                }
            });
        }

        private Medicamento retornarPosicaoDoMedicamento(){
            if(medicamentos.size() > 0){
                return medicamentos.get(getLayoutPosition());
            }else {
                return new Medicamento();
            }
        }

        private void chamarDialogoAlterar(Medicamento medicamento){
            MedicamentoDialog medicamentoDialog = new MedicamentoDialog(medicamentoActivity, context, medicamentoDAO, fornecedorDAO.getAll(), tipoMedicamentoDAO.getAll());
            medicamentoDialog.dialogoAlterar(medicamento);
        }
    }
}
