package com.Exodia.H_and_N.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Exodia.H_and_N.R;

public class UserViewHolder extends RecyclerView.ViewHolder {
    TextView name,descrption,cost;
    ImageView image;
    Button supprimer,aubid,modifier;

    public  UserViewHolder(View itemView){
        super(itemView);
        image = itemView.findViewById(R.id.image_a);
        name= itemView.findViewById(R.id.name_a);
         descrption = itemView.findViewById(R.id.description_a);
         cost = itemView.findViewById(R.id.cost_a);
         supprimer = itemView.findViewById(R.id.supprimerproduit_a);
         modifier =itemView.findViewById(R.id.modifierproduit_a);
         aubid = itemView.findViewById(R.id.ajouterabid_a);

    }

}
