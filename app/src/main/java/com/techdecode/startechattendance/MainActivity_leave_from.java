package com.techdecode.startechattendance;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techdecode.startechattendance.models.Employee;
import com.techdecode.startechattendance.models.Leave_Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity_leave_from extends AppCompatActivity {
    RadioButton b1, b2;
    String selectedSuperStar;
    Button submit,sumbit1;
    EditText dob,tob,LEAVE,address,reason;
    Date date;
    TextView result12;
    Calendar mcurrentdate;
    Spinner spinner,spinner1;
    int day,month,year;

String empcode;

    String showUrl="";
    String leave="";
    String emp="";
    String leave_bal="";

    List<Leave_Type> leave_types;
    List<Employee>employees;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_leave_from);

        LEAVE = findViewById(R.id.leave_balance);





        result12 = findViewById(R.id.name);
        Intent kp=getIntent();
        String userName=kp.getStringExtra("username");
        result12.setText(""+userName);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner1=(Spinner) findViewById(R.id.spin_re);
        b1 = (RadioButton) findViewById(R.id.radioButton1);
        b2 = (RadioButton) findViewById(R.id.radioButton2);
        address = findViewById(R.id.address);
        reason = findViewById(R.id.reason);
        dob=findViewById(R.id.fd);
        tob=findViewById(R.id.td);
        mcurrentdate=Calendar.getInstance();
        day=mcurrentdate.get(Calendar.DAY_OF_MONTH);
        month=mcurrentdate.get(Calendar.MONTH);
        year=mcurrentdate.get(Calendar.YEAR);
        month=month+1;
        leave_types = new ArrayList<>();
        employees = new ArrayList<>();

        loadSpinnerData5(leave);
        loadSpinnerData4(emp);

        //dob.setText(month+"/"+day+"/"+year);
        dob.setOnClickListener(new View.OnClickListener() {
                                   @RequiresApi(api = Build.VERSION_CODES.N)
                                   @Override
                                   public void onClick(View v) {
                                       DatePickerDialog datePickerDialog=new DatePickerDialog(MainActivity_leave_from.this, new DatePickerDialog.OnDateSetListener() {
                                           @Override
                                           public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                               month=month+1;
                                               dob.setText(dayOfMonth+"/"+month+"/"+year);
                                           }
                                       },year,month,day);
                                       datePickerDialog.show();
                                   }
                               });


        tob.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(MainActivity_leave_from.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        tob.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });





                submit = (Button) findViewById(R.id.button1);




        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, leave_bal, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);


                            JSONArray jsonArray = jsonObject.getJSONArray("result");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                            String firstName = jsonObject1.getString("Leave");


                            LEAVE.setText(firstName);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity_leave_from.this, "Something went wrong",Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parameters = new HashMap<String, String>();
                        parameters.put("id", result12.getText().toString());
                        return parameters;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);


            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b1.isChecked()) {

                            //if(b1.getText().toString()!=" "){
                                selectedSuperStar ="AD";
                           // };
            }
                else if(b2.isChecked()){
                    selectedSuperStar = "AB";
                }
                //Toast.makeText(getApplicationContext(), selectedSuperStar, Toast.LENGTH_LONG).show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, showUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Success")) {
                            Toast.makeText(getBaseContext(), "True!", Toast.LENGTH_LONG).show();

                        }
                        else {
                            Toast.makeText(getBaseContext(), "False!", Toast.LENGTH_LONG).show();
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
                        parms.put("leave_cat", selectedSuperStar);
                        parms.put("leave_from", dob.getText().toString());
                        parms.put("leave_to", tob.getText().toString());
                        parms.put("address", address.getText().toString());
                        parms.put("code",employees.get(spinner1.getSelectedItemPosition()).getCode());
                        parms.put("leave_type",leave_types.get(spinner.getSelectedItemPosition()).getSOFT_CODE());
                        parms.put("reason", reason.getText().toString());
                        parms.put("id", result12.getText().toString());


                        //Log.e("Log", "Shawon" + lattitude.getText().toString() );ss

                        return parms;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);

                }
        });
    }
    private void loadSpinnerData5(String leave) {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, leave, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);


                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    int i;
                    for (i = 0; i < jsonArray.length(); i++) {


                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);



                        String b = jsonObject1.getString("CODE_DESC");
                        String id = jsonObject1.getString("SOFT_CODE");


                        leave_types.add(new Leave_Type(b,id));
                        //users_2.add(new User2(id, b));


                    }

                    if (leave_types.size() > 0) {
                        ArrayAdapter<Leave_Type> adapter = new ArrayAdapter<Leave_Type>(MainActivity_leave_from.this, android.R.layout.simple_spinner_item, leave_types);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                               // String idd1 = leave_types.get(position).getCODE_DESC();
                                //String desc1 = leave_types.get(position).getSOFT_CODE();


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

            }

        });

        int socketTimeout = 30000;

        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        requestQueue.add(stringRequest);

    }



    private void loadSpinnerData4(String emp) {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, emp, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);


                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    int i;
                    for (i = 0; i < jsonArray.length(); i++) {


                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);



                        String b = jsonObject1.getString("EMPNAME");
                        String id = jsonObject1.getString("ID_CARD_NO");
                        String c = jsonObject1.getString("EMPCODE");


                        employees.add(new Employee(b,id,c));
                        //users_2.add(new User2(id, b));


                    }

                    if (employees.size() > 0) {
                        ArrayAdapter<Employee> adapter = new ArrayAdapter<Employee>(MainActivity_leave_from.this, android.R.layout.simple_spinner_item, employees);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner1.setAdapter(adapter);
                        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                 empcode= employees.get(position).getCode();



                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

            }

        });

        int socketTimeout = 30000;

        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        requestQueue.add(stringRequest);

    }


}