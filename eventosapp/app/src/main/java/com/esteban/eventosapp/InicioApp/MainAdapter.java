package com.esteban.eventosapp.InicioApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.esteban.eventosapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class MainAdapter extends FirebaseRecyclerAdapter<MainModel, MainAdapter.ViewHolder> {
    private Context context;

    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull MainModel model) {
        holder.course.setText(model.getFecha());
        holder.email.setText(model.getPartido());

        Glide.with(context)
                .load(model.getUrl())
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .placeholder(R.drawable.c)
                .error(R.drawable.c)
                .into(holder.img);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView course, email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img1);
            course = itemView.findViewById(R.id.course);
            email = itemView.findViewById(R.id.emailtext);
        }
    }
}