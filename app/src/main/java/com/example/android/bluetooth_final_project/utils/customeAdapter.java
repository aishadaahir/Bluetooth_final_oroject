package com.example.android.bluetooth_final_project.utils;

import android.content.Context;
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


public class customeAdapter extends RecyclerView.Adapter<customeAdapter.ViewHolder> {
    Context context;
    JsonArray property_filter;


    public customeAdapter(Context context, JsonArray property) {
        this.context = context;
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
//        if (property_list.has("name")) {
//            holder.classname.setText(property_list.get("name").getAsString());
//        }


    }

    @Override
    public int getItemCount() {
        return property_filter.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text1;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.text1);
            delete = itemView.findViewById(R.id.delete);

        }

    }


}
