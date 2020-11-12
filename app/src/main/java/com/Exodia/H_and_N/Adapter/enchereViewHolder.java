package com.Exodia.H_and_N.Adapter;

        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.recyclerview.widget.RecyclerView;

        import com.Exodia.H_and_N.R;

public class enchereViewHolder extends RecyclerView.ViewHolder {
    TextView name,descrption,cost,dateajout,costb;
    ImageView image;
    Button bid,aff;

    public  enchereViewHolder(View itemView){
        super(itemView);
        image = itemView.findViewById(R.id.image_a);
        name= itemView.findViewById(R.id.name_a);
        descrption = itemView.findViewById(R.id.description_a);
        dateajout = itemView.findViewById(R.id.dateajout);
        cost = itemView.findViewById(R.id.cost_a);
        costb = itemView.findViewById(R.id.cost_b);
        bid = itemView.findViewById(R.id.bid_a);
        aff =itemView.findViewById(R.id.afficherbid_a);

    }

}
