package com.pixelcowboy.matchit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class player extends AppCompatActivity {
    ArrayList<JSONObject> cards = new ArrayList<JSONObject>();
    ImageButton imgBtn1;
    ImageButton imgBtn2;
    ImageButton imgBtn3;
    ImageButton imgBtn4;
    ImageButton imgBtn5;
    ImageButton imgBtn6;

    int[] selectedIndex = new int[]{-1,-1};
    ArrayList<String> guessed = new ArrayList<String>();

    Drawable ico;

    @Override
    protected void onStart() {
        super.onStart();

        //INITIALIZE
        if(cards.size() == 0){
            try {
                cards.add(new JSONObject()
                        .put("img", "file:///android_asset/img/1.jpg")
                        .put("id", "jordan"));
                cards.add(new JSONObject()
                        .put("img", "file:///android_asset/img/1.jpg")
                        .put("id", "jordan"));
                cards.add(new JSONObject()
                        .put("img", "file:///android_asset/img/2.jpg")
                        .put("id", "nike"));
                cards.add(new JSONObject()
                        .put("img", "file:///android_asset/img/2.jpg")
                        .put("id", "nike"));
                cards.add(new JSONObject()
                        .put("img", "file:///android_asset/img/3.jpg")
                        .put("id", "addidas"));
                cards.add(new JSONObject()
                        .put("img", "file:///android_asset/img/3.jpg")
                        .put("id", "addidas"));

            } catch (Exception ex) {
                Log.e("jsonErr", ex.getMessage());
            }

            Collections.shuffle(cards);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        imgBtn1 = findViewById(R.id.imgBtn1);
        imgBtn2 = findViewById(R.id.imgBtn2);
        imgBtn3 = findViewById(R.id.imgBtn3);
        imgBtn4 = findViewById(R.id.imgBtn4);
        imgBtn5 = findViewById(R.id.imgBtn5);
        imgBtn6 = findViewById(R.id.imgBtn6);

        ico = getApplicationContext().getDrawable(R.drawable.baseline_question_mark_24);

        imgBtn1.setImageDrawable(ico);
        imgBtn2.setImageDrawable(ico);
        imgBtn3.setImageDrawable(ico);
        imgBtn4.setImageDrawable(ico);
        imgBtn5.setImageDrawable(ico);
        imgBtn6.setImageDrawable(ico);
    }

    public void revealCard(int index){
        try{
            JSONObject j = cards.get(index);

            if(index == 0) Glide.with(this).load(j.getString("img")).into(imgBtn1);
            if(index == 1) Glide.with(this).load(j.getString("img")).into(imgBtn2);
            if(index == 2) Glide.with(this).load(j.getString("img")).into(imgBtn3);
            if(index == 3) Glide.with(this).load(j.getString("img")).into(imgBtn4);
            if(index == 4) Glide.with(this).load(j.getString("img")).into(imgBtn5);
            if(index == 5) Glide.with(this).load(j.getString("img")).into(imgBtn6);
        }catch (Exception ex){
            Log.e("showVal", ex.getMessage());
        }
    }

    public void resetCard(int index){
        try{
            if(index == 0) imgBtn1.setImageDrawable(ico);
            if(index == 1) imgBtn2.setImageDrawable(ico);
            if(index == 2) imgBtn3.setImageDrawable(ico);
            if(index == 3) imgBtn4.setImageDrawable(ico);
            if(index == 4) imgBtn5.setImageDrawable(ico);
            if(index == 5) imgBtn6.setImageDrawable(ico);
        }catch (Exception ex){
            Log.e("resetIndex", ex.getMessage());
        }
    }

    public void addSelection(int index) {
        if (selectedIndex[0] == -1) {
            selectedIndex[0] = index;
            revealCard(index);
            return;
        }

        if (selectedIndex[1] == -1) {
            selectedIndex[1] = index;
            revealCard(index);
        }

        try {
            if (selectedIndex[0] != -1 && selectedIndex[1] != -1) {
                disableAllButtons();

                JSONObject j1 = cards.get(selectedIndex[0]);
                JSONObject j2 = cards.get(selectedIndex[1]);

                if (!j1.getString("id").equals(j2.getString("id"))) {
                    new android.os.Handler().postDelayed(() -> {
                        resetCard(selectedIndex[0]);
                        resetCard(selectedIndex[1]);
                        selectedIndex = new int[]{-1, -1};
                        enableAllButtons();
                    }, 1000);

                    return;
                }

                guessed.add(j1.getString("id"));
                selectedIndex = new int[]{-1, -1};
                enableAllButtons();
            }


            //Check if all cards are guessed
            if(guessed.size() >= 3){
                Toast.makeText(getApplicationContext(), "Congrats you have match all cards", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Log.e("onSelect", ex.getMessage());
        }
    }

    public void disableAllButtons() {
        imgBtn1.setEnabled(false);
        imgBtn2.setEnabled(false);
        imgBtn3.setEnabled(false);
        imgBtn4.setEnabled(false);
        imgBtn5.setEnabled(false);
        imgBtn6.setEnabled(false);
    }

    public void enableAllButtons() {
        imgBtn1.setEnabled(true);
        imgBtn2.setEnabled(true);
        imgBtn3.setEnabled(true);
        imgBtn4.setEnabled(true);
        imgBtn5.setEnabled(true);
        imgBtn6.setEnabled(true);
    }



    public void onClickImg1(View view) {
        try {
            int index = 0;
            JSONObject j = cards.get(index);
            Glide.with(this).load(j.getString("img")).into(imgBtn1);
            addSelection(index);
        } catch (Exception ex) {
            Log.e("opt", ex.getMessage());
        }
    }

    public void onClickImg2(View view) {
        int index = 1;
        addSelection(index);

        try {
            JSONObject j = cards.get(index);
            Glide.with(this).load(j.getString("img")).into(imgBtn2);
        } catch (Exception ex) {
            Log.e("opt", ex.getMessage());
        }
    }

    public void onClickImg3(View view) {
        int index = 2;
        addSelection(index);
        try {
            JSONObject j = cards.get(index);
            Glide.with(this).load(j.getString("img")).into(imgBtn3);
        } catch (Exception ex) {
            Log.e("opt", ex.getMessage());
        }
    }

    public void onClickImg4(View view) {
        int index = 3;
        addSelection(index);
        try {
            JSONObject j = cards.get(3);
            Glide.with(this).load(j.getString("img")).into(imgBtn4);
        } catch (Exception ex) {
            Log.e("opt", ex.getMessage());
        }
    }

    public void onClickImg5(View view) {
        int index = 4;
        addSelection(index);
        try {
            JSONObject j = cards.get(4);
            Glide.with(this).load(j.getString("img")).into(imgBtn5);
        } catch (Exception ex) {
            Log.e("opt", ex.getMessage());
        }
    }

    public void onClickImg6(View view) {
        int index = 5;
        addSelection(index);
        try {
            JSONObject j = cards.get(5);
            Glide.with(this).load(j.getString("img")).into(imgBtn6);
        } catch (Exception ex) {
            Log.e("opt", ex.getMessage());
        }
    }
}