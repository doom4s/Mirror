package com.maric.vlajko.mirror;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import butterknife.Bind;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity {

    @Bind(R.id.myListView) ListView myListView;
    PicturesAdapter picturesAdapter;
    MyPictures[] myImageArray= new MyPictures[]{
            new MyPictures("Feather","by Vlajko","background10_thumb"),
            new MyPictures("Feather","by Vlajko","background11_thumb"),
            new MyPictures("Feather","by Vlajko","background12_thumb"),};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        picturesAdapter = new PicturesAdapter(this, R.layout.row, myImageArray);
        if(picturesAdapter!=null){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    myListView.setAdapter(picturesAdapter);
                }
            });
            t.start();

        }
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent returnIntent = new Intent(getApplicationContext(),Main2Activity.class);
                returnIntent.putExtra("result",position);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        });

    }
}