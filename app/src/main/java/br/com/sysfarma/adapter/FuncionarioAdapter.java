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
import br.com.sysfarma.activity.FuncionarioActivity;
import br.com.sysfarma.dao.FuncionarioDAO;
import br.com.sysfarma.dialog.FuncionarioDialog;
import br.com.sysfarma.domain.Funcionario;

public class FuncionarioAdapter extends RecyclerView.Adapter<FuncionarioAdapter.ViewHolderFuncionario> {

    private List<Funcionario> funcionarios;
    private FuncionarioDAO funcionarioDAO;
    private FuncionarioActivity funcionarioActivity;

    public FuncionarioAdapter(FuncionarioActivity funcionarioActivity, FuncionarioDAO funcionarioDAO){
        this.funcionarioActivity = funcionarioActivity;
        this.funcionarioDAO = funcionarioDAO;
        this.funcionarios = funcionarioDAO.getAll();
    }

    @NonNull
    @Override
    public FuncionarioAdapter.ViewHolderFuncionario onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return referenciaLayoutRecyclerView(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull FuncionarioAdapter.ViewHolderFuncionario holder, int position) {

        if(funcionarios.size() > 0){
            Funcionario funcionario = funcionarios.get(position);

            holder.textoPrincipal.setText(funcionario.getNome());
            CharSequence charSequenceTextoSecundario = funcionario.getEmail();
            holder.textoSecundario.setText(charSequenceTextoSecundario);
        }
    }

    @Override
    public int getItemCount() {
        return funcionarios.size();
    }


    private ViewHolderFuncionario referenciaLayoutRecyclerView(ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listaView = layoutInflater.inflate(R.layout.list, parent, false);

        return new ViewHolderFuncionario(listaView, parent.getContext());
    }




    class ViewHolderFuncionario extends RecyclerView.ViewHolder{

        private TextView textoPrincipal;
        private TextView textoSecundario;
        private final Context context;

        ViewHolderFuncionario(@NonNull View itemView, final Context context) {
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
                    chamarDialogoAlterar(retornarPosicaoDoFuncionario());
                }
            });
        }

        private Funcionario retornarPosicaoDoFuncionario(){
            if(funcionarios.size() > 0){
                return funcionarios.get(getLayoutPosition());
            }else {
                return new Funcionario();
            }
        }

        private void chamarDialogoAlterar(Funcionario funcionario){
            FuncionarioDialog funcionarioDialog = new FuncionarioDialog(funcionarioActivity, context, funcionarioDAO);
            funcionarioDialog.dialogoAlterar(funcionario);
        }
    }
}
