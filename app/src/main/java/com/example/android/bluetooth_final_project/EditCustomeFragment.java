package com.example.android.bluetooth_final_project;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.bluetooth_final_project.utils.CustomeDatabase;
import com.example.android.bluetooth_final_project.utils.customeAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class EditCustomeFragment extends Fragment {

    CustomeDatabase myDBallcustome;
    RecyclerView rcyview;
    ImageView add;
    customeAdapter cadapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_custome, container, false);


        rcyview = view.findViewById(R.id.rcyview);
        myDBallcustome = new CustomeDatabase(getActivity());

        getlocalviewdata();

        return view;
    }

    private void getlocalviewdata(){
        Log.e("error55111__+---+3class", "innnnnnnclassss");
        JsonArray jsonArray = new JsonArray();
        Cursor cursor = myDBallcustome.readAllData();
        if(cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){

                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String array = cursor.getString(2);
                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty("id",id);
                jsonObject.addProperty("name",name);
                jsonObject.addProperty("array",array);

//                jsonArray = new Gson().fromJson(jsonString, JsonArray.class);
                jsonArray.add(jsonObject);


            }

            Log.e("error55111__+---+3class", new Gson().toJson(jsonArray));
            cursor.close();

            if (jsonArray.size() > 0) {
                Log.e("asdasdasd", "999999999999999999999");
                Log.e("asdasdasd", jsonArray.toString());
                rcyview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                cadapter = new customeAdapter(getContext(),jsonArray);

                rcyview.setAdapter(cadapter);
            } else {
                rcyview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                rcyview.setAdapter(null);
                // Handle empty response or display appropriate message
            }


        }

    }

}