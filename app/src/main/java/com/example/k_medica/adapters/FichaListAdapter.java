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
import com.example.k_medica.models.Ficha;
import com.example.k_medica.models.Paciente;

import java.util.ArrayList;
import java.util.List;

public class FichaListAdapter extends RecyclerView.Adapter<FichaListAdapter.ViewHolder>{

    List<Ficha> ShowList;
    Context context;
    int position;
    private FichaListAdapter.OnItemClickListener itemClickListener;


    public FichaListAdapter(List<Ficha> showList, FichaListAdapter.OnItemClickListener itemClickListener) {
        ShowList = showList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public FichaListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ficha,parent,false);

        FichaListAdapter.ViewHolder viewHolder = new FichaListAdapter.ViewHolder(view);

        context = parent.getContext();
        position = viewType;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FichaListAdapter.ViewHolder viewHolder, final int i) {
        /*se pasa a cada uno de los elementos el evento listener junto con el respectivo elemento*/

        viewHolder.bind(ShowList.get(i), itemClickListener,i);
        position = i;
    }

    @Override
    public int getItemCount() {
        return ShowList.size();
    }
    /*Se remplaza el listado*/
    public void updateList(ArrayList<Ficha> data) {
        ShowList = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textId,textNombre,textFecha;
        ImageButton botonEliminar;
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);

            textNombre = itemView.findViewById(R.id.list_ficha_id);
            botonEliminar = itemView.findViewById(R.id.list_ficha_btn_borrar);
            cv = itemView.findViewById(R.id.cardViewItem);


        }

        public void bind(final Ficha ficha, final FichaListAdapter.OnItemClickListener listener, final int i){

            textNombre.setText(String.valueOf(ficha.getId()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(ficha,getAdapterPosition());

                }
            });


            botonEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnDeleteClick(ficha, getAdapterPosition());

                }
            });

        }
    }
    public interface OnItemClickListener {
        void OnItemClick(Ficha ficha, int position);
        void OnDeleteClick(Ficha ficha, int position);
    }

    public void removeItem(int position) {
        ShowList.remove(position);
        notifyItemRemoved(position);
    }

}
