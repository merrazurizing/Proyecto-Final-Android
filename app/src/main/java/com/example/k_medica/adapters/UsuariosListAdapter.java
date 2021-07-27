package com.example.k_medica.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.k_medica.R;

import java.util.ArrayList;
import java.util.List;

public class UsuariosListAsapter extends RecyclerView.Adapter<UsuariosListAsapter.ViewHolder> {


    List<Nota_Usuario> ShowList;
    Context context;
    int position;
    private OnItemClickListener itemClickListener;


    public UsuariosListAsapter(List<Nota_Usuario> showList, OnItemClickListener itemClickListener) {
        ShowList = showList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public UsuariosListAsapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_usuario,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        context = parent.getContext();
        position = viewType;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsuariosListAsapter.ViewHolder viewHolder, final int i) {
        /*se pasa a cada uno de los elementos el evento listener junto con el respectivo elemento*/

        viewHolder.bind(ShowList.get(i), itemClickListener,i);
        position = i;
    }

    @Override
    public int getItemCount() {
        return ShowList.size();
    }
    /*Se remplaza el listado*/
    public void updateList(ArrayList<Nota_Usuario> data) {
        ShowList = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textId,textNombre,textFecha;
        ImageButton botonEliminar;
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);

            textId = itemView.findViewById(R.id.list_usuario_nombre);
            botonEliminar = itemView.findViewById(R.id.list_usuario_btn_borrar);
            cv = itemView.findViewById(R.id.cardViewItem);


        }

        public void bind(final Nota_Usuario notaUsuario, final OnItemClickListener listener,final int i){



            textNombre.setText(notaUsuario.getNombre());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(notaUsuario,getAdapterPosition());
                }
            });

            botonEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnDeleteClick(notaUsuario, getAdapterPosition());

                }
            });

        }
    }
    public interface OnItemClickListener {
        void OnItemClick(Nota_Usuario notaUsuario, int position);
        void OnDeleteClick(Nota_Usuario notaUsuario, int position);
    }

    public void removeItem(int position) {
        ShowList.remove(position);
        notifyItemRemoved(position);
    }
}
