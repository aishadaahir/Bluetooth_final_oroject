package com.example.android.bluetooth_final_project.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bluetooth_final_project.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Objects;


public class customeAdapter extends RecyclerView.Adapter<customeAdapter.ViewHolder> {
    Context context;
    JsonArray property_filter;
    Activity activity;
    ItemClicklistner ItemClicklistner;
    ItemClicklistner2 ItemClicklistner2;
    public customeAdapter(Context context,Activity activity, JsonArray property,ItemClicklistner ItemClicklistner,ItemClicklistner2 ItemClicklistner2) {
        this.context = context;
        this.activity = activity;
        this.ItemClicklistner = ItemClicklistner;
        this.ItemClicklistner2 = ItemClicklistner2;
        this.property_filter = property;
    }

    @NonNull
    @Override
    public customeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        return new customeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final customeAdapter.ViewHolder holder, int position) {
        final JsonObject property_list = property_filter.get(position).getAsJsonObject();

//        if(property_list.get())
//
        if (property_list.has("name")) {
            if(Objects.equals(property_list.get("name").getAsString(), "")){
                holder.text1.setText(property_list.get("array").getAsString());
            }
            else{
                holder.text1.setText(property_list.get("name").getAsString());
                holder.text2.setText(property_list.get("array").getAsString());
            }

        }

        holder.text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, activity.getClass());
                intent.putExtra("array", property_list.get("array").getAsString());

                ItemClicklistner2.onItem(position,intent);
            }
        });
        holder.text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, activity.getClass());
                intent.putExtra("array", property_list.get("array").getAsString());

                ItemClicklistner2.onItem(position,intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, activity.getClass());
                intent.putExtra("ID", property_list.get("id").getAsString());

                ItemClicklistner.onItem(position,intent);
            }
        });


    }

    public interface ItemClicklistner {
        void onItem(int position, Intent intent);
    }
    public interface ItemClicklistner2 {
        void onItem(int position, Intent intent);
    }

    @Override
    public int getItemCount() {
        return property_filter.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text1,text2;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
            delete = itemView.findViewById(R.id.delete);

        }

    }


}
