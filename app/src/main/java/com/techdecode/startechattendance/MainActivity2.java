package com.techdecode.startechattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    RequestQueue requestQueue;
    FusedLocationProviderClient fusedLocationProviderClient;
    EditText lattitude,longitude,address,city,country,remarks_value;
    Spinner spinner;

    EditText autoD8, autoTime;
    TextView result12,count1;
    Button getLocation,insert_value;
    String item,set;
    private final static int REQUEST_CODE = 100;

    String showUrl="#";

    private ArrayList<String> data=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        result12 = findViewById(R.id.name);
        Intent dp=getIntent();
        String userName=dp.getStringExtra("username");
        result12.setText(""+userName);
        lattitude = findViewById(R.id.lattitude);
        spinner=findViewById(R.id.spinner);

        longitude = findViewById(R.id.longitude);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        country = findViewById(R.id.country);
        getLocation = findViewById(R.id.getLocation);
        insert_value = findViewById(R.id.insert);
        remarks_value=findViewById(R.id.remarks);
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLastLocation();

            }
        });
        List<String>categories=new ArrayList<>();
        categories.add(0,"Select Your Attendance Reason");
        categories.add("ID Card Lost");
        categories.add("Machine Problem");
        categories.add("Home Office");
        categories.add("Market Visit");
        categories.add("Branch Visit");
        categories.add("Audit");
        categories.add("Service & Solutions");
        categories.add("Office Tour");
        categories.add("Promotional Tour");
        categories.add("Weekend Duty");
        categories.add("Office Picnic");
        categories.add("Corporate Visit");
        categories.add("Please Specify");

        ArrayAdapter<String> dataAdapter;
        dataAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Select Your Attendance Reason"))
                {
                 set="C";

                }

                else{
                     item=parent.getItemAtPosition(position).toString();
                     set=null;
                    Toast.makeText(parent.getContext(),"You Selected Attendance Reason is: "+item,Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        EditText autoD8 = (EditText)findViewById(R.id.autoD8);
        EditText autoTime = (EditText)findViewById(R.id.autoTime);

        SimpleDateFormat dateF = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault());
        SimpleDateFormat timeF = new SimpleDateFormat(" HH:mm:ss", Locale.getDefault());
        String date = dateF.format(Calendar.getInstance().getTime());
        String time = timeF.format(Calendar.getInstance().getTime());

        autoD8.setText(date);
        autoTime.setText(time);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        //insert_database
        insert_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        if(item==null||set=="C"){
    Toast.makeText(getApplicationContext(),"Reason hasn't Valid values.Please choose Valid Reason",
            Toast.LENGTH_LONG).show();
}


                if (lattitude.getText().toString().length() == 0  || longitude.getText().toString().length() == 0||address.getText().toString().length() == 0||country.getText().toString().length() == 0||city.getText().toString().length() == 0) {

                    Toast.makeText(getApplicationContext(),"Please Press The Get Location first for Track Your Location",
                            Toast.LENGTH_LONG).show();

                   if (address.getText().toString().length() == 0)
                        address.setError("Address is required!");





                }








else {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, showUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("Success")) {
                                Toast.makeText(getBaseContext(), "Attendance Data Insert Sucessfully!", Toast.LENGTH_LONG).show();

                            }
                            else {
                                Toast.makeText(getBaseContext(), "For Today Your attendance has Already Submitted!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> parms = new HashMap<String, String>();
                            parms.put("lal", lattitude.getText().toString());
                            //Log.e("Log", "Shawon" + lattitude.getText().toString() );ss
                            parms.put("long", longitude.getText().toString());
                            parms.put("add", address.getText().toString());
                            parms.put("id", result12.getText().toString());
                            // Log.e("Log", "Shawon1" + result.getText().toString());
                            parms.put("city", city.getText().toString());
                            parms.put("rem", remarks_value.getText().toString());
                            parms.put("reason",item);
                            Log.e("Log", "Shawon1" + city.getText().toString());
                            return parms;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }
            }
        });


    }




    private void getLastLocation(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){


            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null){



                                try {
                                    Geocoder geocoder = new Geocoder(MainActivity2.this, Locale.getDefault());
                                    String result = null;
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    lattitude.setText(""+ addresses.get(0).getLatitude());
                                    longitude.setText(""+ addresses.get(0).getLongitude());
                                    address.setText(addresses.get(0).getAddressLine(0));
                                    // city.setText("City: "+addresses.get(0).getLocality());
                                    country.setText(addresses.get(0).getCountryName());

                                    /*test*/
                                    if (addresses != null && addresses.size() > 0) {
                                        Address address = addresses.get(0);
                                        StringBuilder sb = new StringBuilder();
                                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                                            sb.append(address.getAddressLine(i)).append(",");//adress
                                        }
                                        sb.append(address.getLocality()).append(",");//village


                                        sb.append(address.getAdminArea()).append(","); //state

                                        sb.append(address.getSubAdminArea()).append("");//district



                                        result = sb.toString();

                                        
                                        city.setText(result);
                                    }







                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }



                        }
                    });


        }else {

            askPermission();


        }


    }

    private void askPermission() {

        ActivityCompat.requestPermissions(MainActivity2.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {

        if (requestCode == REQUEST_CODE){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){


                getLastLocation();

            }else {


                Toast.makeText(MainActivity2.this,"Please provide the required permission",Toast.LENGTH_SHORT).show();

            }



        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}