package com.leudroid.counter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Declaration of Variables
    TextView textNumber, textLimit;
    int counter = 0, limit = 0;
    boolean limitOnOff = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initializing the variables
        textNumber = (TextView) findViewById(R.id.textnumber);
        textLimit = (TextView) findViewById(R.id.textlimit);

        //on button + click action
        findViewById(R.id.btnadd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add one to current number
                addOne();
            }
        });

        //on button - click action
        findViewById(R.id.btnmin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //subtract one from current number
                subOne();
            }
        });

        //on reset button click action
        findViewById(R.id.btnreset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter=0;
                textNumber.setText(counter+"");
                changeNumberBackground();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //setting up the limit for counter
        if (id == R.id.action_limit) {
            if(!limitOnOff){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Enter Limit");

                final EditText input = new EditText(this);
                //TODO: move this into layouts
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String temp = input.getText().toString();
                        if(temp.equalsIgnoreCase("")){
                            input.setError("Enter Number");
                            return;
                        }
                        limit = Integer.parseInt(temp);
                        limitOnOff = true;
                        changeNumberBackground();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        limitOnOff = false;
                        changeNumberBackground();
                    }
                });

                builder.show();
            }else{
                limitOnOff=false;
                changeNumberBackground();
            }
        } else if (id == R.id.action_detail) {
            Toast.makeText(this, "Detail Count Coming Soon", Toast.LENGTH_SHORT).show();
            return true;
        }else if (id == R.id.action_settings) {
            Toast.makeText(this, "Customising options Coming Soon", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_limit);
        if (item.getTitle().equals("Turn ON limit") && limitOnOff) {
            item.setTitle("Turn OFF limit");
        } else {
            item.setTitle("Turn ON limit");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    // function to set background for text view
    private void changeNumberBackground() {

        if(limitOnOff){
            textLimit.setVisibility(View.VISIBLE);
            textLimit.setText("Limit : " + limit);
            if(counter>limit-1){
                textNumber.setBackgroundResource(R.drawable.red_circle);
            }else{
                textNumber.setBackgroundResource(R.drawable.green_circle);
            }
        }else {
            textLimit.setVisibility(View.INVISIBLE);
            textNumber.setBackgroundResource(R.drawable.border);
        }

    }

    // Function to add one to counter
    public void addOne(){
        counter = Integer.parseInt(textNumber.getText().toString());
        counter = counter+1;
        textNumber.setText((counter)+"");
        changeNumberBackground();
    }

    //function to subtract one from counter
    public void subOne(){
        counter = Integer.parseInt(textNumber.getText().toString());
        counter = counter-1;
        if(counter < 0)
            counter = 0;
        textNumber.setText(""+counter);
        changeNumberBackground();
    }

    //on volume up and back button press action
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN){
            subOne();
        }else if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setTitle("Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            MainActivity.super.onBackPressed();
                        }
                    }).create().show();
        }
        return true;
    }

    //on volume down button press
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP){
            addOne();
        }
        return true;
    }

}