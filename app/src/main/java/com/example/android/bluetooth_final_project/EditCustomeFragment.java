package com.example.android.bluetooth_final_project;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.bluetooth_final_project.utils.CustomeDatabase;
import com.example.android.bluetooth_final_project.utils.customeAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Objects;

public class EditCustomeFragment extends Fragment {

    CustomeDatabase myDBallcustome;
    RecyclerView rcyview;
    ImageView add;
    customeAdapter cadapter;
    EditText textView1,textView2,textView3,textView4,textView5,textView6,txt_name;
    InputFilter filter;
    boolean isTextChangedByFilter = false;
    AppCompatButton Save,Skip;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_custome, container, false);


        textView1 = view.findViewById(R.id.textView1);
        textView2 = view.findViewById(R.id.textView2);
        textView3 = view.findViewById(R.id.textView3);
        textView4 = view.findViewById(R.id.textView4);
        textView5 = view.findViewById(R.id.textView5);
        textView6 = view.findViewById(R.id.textView6);

        add = view.findViewById(R.id.custome);

        rcyview = view.findViewById(R.id.rcyview);
        myDBallcustome = new CustomeDatabase(getActivity());

        textView1.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isTextChangedByFilter) {
                    if (count > 0) {
                        char text = s.charAt(start);
                        // Do something with the written character
                        Log.e("filter", String.valueOf(text));
                        Log.e("filter", String.valueOf(text));
                        String texts= String.valueOf(text);
                        isTextChangedByFilter = true;
                        textView1.setText(texts);
                        isTextChangedByFilter = false;
                    }
                }
            }
        });
        textView2.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isTextChangedByFilter) {
                    if (count > 0) {
                        char text = s.charAt(start);
                        // Do something with the written character
                        Log.e("filter", String.valueOf(text));
                        Log.e("filter", String.valueOf(text));
                        String texts= String.valueOf(text);
                        isTextChangedByFilter = true;
                        textView2.setText(texts);
                        isTextChangedByFilter = false;
                    }
                }
            }
        });
        textView3.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isTextChangedByFilter) {
                    if (count > 0) {
                        char text = s.charAt(start);
                        // Do something with the written character
                        Log.e("filter", String.valueOf(text));
                        Log.e("filter", String.valueOf(text));
                        String texts= String.valueOf(text);
                        isTextChangedByFilter = true;
                        textView3.setText(texts);
                        isTextChangedByFilter = false;
                    }
                }
            }
        });
        textView4.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isTextChangedByFilter) {
                    if (count > 0) {
                        char text = s.charAt(start);
                        // Do something with the written character
                        Log.e("filter", String.valueOf(text));
                        Log.e("filter", String.valueOf(text));
                        String texts= String.valueOf(text);
                        isTextChangedByFilter = true;
                        textView4.setText(texts);
                        isTextChangedByFilter = false;
                    }
                }
            }
        });
        textView5.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isTextChangedByFilter) {
                    if (count > 0) {
                        char text = s.charAt(start);
                        // Do something with the written character
                        Log.e("filter", String.valueOf(text));
                        Log.e("filter", String.valueOf(text));
                        String texts= String.valueOf(text);
                        isTextChangedByFilter = true;
                        textView5.setText(texts);
                        isTextChangedByFilter = false;
                    }
                }
            }
        });
        textView6.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isTextChangedByFilter) {
                    if (count > 0) {
                        char text = s.charAt(start);
                        // Do something with the written character
                        Log.e("filter", String.valueOf(text));
                        Log.e("filter", String.valueOf(text));
                        String texts= String.valueOf(text);
                        isTextChangedByFilter = true;
                        textView6.setText(texts);
                        isTextChangedByFilter = false;
                    }
                }
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String custome=textView1.getText().toString()+","+textView2.getText().toString()+","+textView3.getText().toString()+","+
                        textView4.getText().toString()+","+textView5.getText().toString()+","+textView6.getText().toString();
                Log.e("custome",custome);
                ShowDialog(custome);
            }
        });

        getlocalviewdata();

        return view;
    }


    private void ShowDialog(String custome) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custome_dailog_model, null);
        builder.setView(dialogView);
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();

        txt_name=dialogView.findViewById(R.id.txt_name);


        Save=dialogView.findViewById(R.id.Save);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Cursor cursor = myDBallcustome.readAllData();
//                if(cursor.getCount() == 0){
//
//                    myDBallcustome.addData(txt_name.getText().toString(),custome);
//                    myDBallcustome.delete();
//                    myDBallcustome.addData(txt_name.getText().toString(),custome);
//                }
//                else{
//                    myDBallcustome.addData(txt_name.getText().toString(),custome);
//                }

                myDBallcustome.addData(txt_name.getText().toString(),custome);
                Log.e("delete",custome);
                try {
                    if(Home.outputStream==null){
                        showDialog();
                    }
                    else{
                        Home.outputStream.write(custome.getBytes());
                        Toast.makeText(getActivity(), "Custom sent", Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                getlocalviewdata();
                alertDialog.dismiss();
                alertDialog.cancel();
            }
        });

        Skip=dialogView.findViewById(R.id.Skip);
        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Cursor cursor = myDBallcustome.readAllData();
//                if(cursor.getCount() == 0){
//                    myDBallcustome.addData("",custome);
//                    myDBallcustome.delete();
//                    myDBallcustome.addData("",custome);
//                }
//                else{
//                    myDBallcustome.addData("",custome);
//                }

                myDBallcustome.addData("",custome);

                Log.e("delete",custome);
                try {
                    if(Home.outputStream==null){
                        showDialog();
                    }
                    else{
                        Home.outputStream.write(custome.getBytes());
                        Toast.makeText(getActivity(), "Custom sent", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                getlocalviewdata();
                alertDialog.dismiss();
                alertDialog.cancel();
            }
        });





        builder.setCancelable(true);
//        builder.create().show();
        alertDialog.show();
    }



    private void getlocalviewdata(){
        rcyview.setAdapter(null);
        Log.e("error55111__+---+3class", "innnnnnnclassss");
        JsonArray jsonArray = new JsonArray();
        Cursor cursor = myDBallcustome.readAllData();
        if(cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){

                String id = cursor.getString(0);
                String name = cursor.getString(2);
                String array = cursor.getString(3);
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
                cadapter = new customeAdapter(getContext(),getActivity(),jsonArray,new customeAdapter.ItemClicklistner() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onItem(int position, Intent intent) {
                        Long ID = Long.valueOf(intent.getExtras().getString("ID"));
                        Log.e("delete", String.valueOf(ID));
                        myDBallcustome.deletecustome(ID);
                        Toast.makeText(getActivity(), "Custom removed from list", Toast.LENGTH_SHORT).show();
                        getlocalviewdata();


                    }
                },new customeAdapter.ItemClicklistner2() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onItem(int position, Intent intent) {
                        String array = intent.getExtras().getString("array");
                        Log.e("array",array);
                        try {
                            if(Home.outputStream==null){
                                showDialog();
                            }
                            else{
                                Home.outputStream.write(array.getBytes());
                                Toast.makeText(getActivity(), "Custom sent", Toast.LENGTH_SHORT).show();
                            }

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    }
                });

                rcyview.setAdapter(cadapter);
            } else {
                rcyview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                rcyview.setAdapter(null);
                Log.e("delete","empty");
                // Handle empty response or display appropriate message
            }


        }

    }

    public void showDialog() {
        // Use the Builder class for convenient dialog construction.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        builder.setTitle("warning");
        builder.setMessage("Bluetooth is not connected,\n send again when there is a connection")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        alertDialog.dismiss();
                        alertDialog.cancel();
                        // START THE GAME!
                    }
                });

        builder.create().show();
        builder.setCancelable(true);
//         Create the AlertDialog object and return it.
//        return builder.create();
    }

}