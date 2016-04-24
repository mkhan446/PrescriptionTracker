package com.example.brand.goodrx;

import android.app.*;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Spinner;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by brand on 4/23/2016.
 */


public class EventInput extends Activity implements AdapterView.OnItemSelectedListener {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private TextView endView;
    private int year, month, day;
    private int year2, month2, day2;
    private Spinner spinner;
    private int decay = 0;
    private int quantity = 0;
    private int days = 0;

    Button mButton;
    EditText mEdit;
    TextView mText;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventinput);
        mEdit = (EditText)findViewById(R.id.drugname);
        mButton = (Button)findViewById(R.id.submit);
        mText = (EditText)findViewById(R.id.quantity);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                quantity = Integer.parseInt(mText.getText().toString().trim());
                if (!mText.equals("") && quantity != 0){
                    days = quantity / (24/decay);
                    computeEndDate();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("mEdit", String.valueOf(mEdit));
                    intent.putExtra("quantity", quantity);
                    intent.putExtra("year", year);
                    intent.putExtra("year2", year2);
                    intent.putExtra("month", month);
                    intent.putExtra("month2", month2);
                    intent.putExtra("day", day);
                    intent.putExtra("day2", day2);
                    startActivity(intent);
                    System.out.println("Sending!");
                }
                else{
                    System.out.println(":(");
                }
            }
        });
        dateView = (TextView) findViewById(R.id.textView3);
        endView = (TextView) findViewById(R.id.textView1);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        showDate(year, month + 1,day, false);

        Spinner dynamicSpinner = (Spinner) findViewById(R.id.spinner1);

        String[] items = new String[] {"Every 1 Hour", "Every 6 Hours", "Every 12 Hours", "Every 24 Hours"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        dynamicSpinner.setAdapter(adapter);

        dynamicSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                switch (position) {
                    case 0:
                        decay = 1;
                        break;
                    case 1:
                        decay = 6;
                        break;
                    case 2:
                        decay = 12;
                        break;
                    case 3:
                        decay = 24;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                decay = 1;
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1,arg3, false);
        }
    };

    private void showDate(int year, int month, int day, boolean end) {
        if(!end)
            dateView.setText(new StringBuilder().append(month).append("/").append(day).append("/").append(year));
        else
            endView.setText(new StringBuilder().append(month).append("/").append(day).append("/").append(year));
    }

    private void computeEndDate(){
        GregorianCalendar gregDate = new GregorianCalendar(year, month, day);
        calendar.setTime(gregDate.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, days);
        year2 = calendar.get(Calendar.YEAR);
        day2 = calendar.get(Calendar.DAY_OF_MONTH);
        month2 = calendar.get(Calendar.MONTH);
        showDate(year2, month2 + 1, day2, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        decay = 1;
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                decay = 1;
                break;
            case 1:
                decay = 6;
                break;
            case 2:
                decay = 12;
                break;
            case 3:
                decay = 24;
                break;

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "EventInput Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.brand.goodrx/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }


    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "EventInput Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.brand.goodrx/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
    }



