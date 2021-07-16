package com.techdecode.startechattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techdecode.startechattendance.models.Leave_Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity_dashboard extends AppCompatActivity {
    String email,password;
    TextView result12;
     EditText LEAVE;
    CardView cardhome;
    CardView cardleave1;
    CardView cardattendance;
    CardView cardapproval;
    CardView cardsetting;
    CardView cardlogout;
    String leave_bal="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        LEAVE=findViewById(R.id.leave);

        result12 = findViewById(R.id.name);
        Intent dp=getIntent();
        String userName=dp.getStringExtra("username");
        result12.setText(" "+userName);
        email = result12.getText().toString().trim();


        cardhome=findViewById(R.id.cardhome);
        cardleave1=findViewById(R.id.cardleave);
        cardattendance=findViewById(R.id.cardattendance);
        cardapproval=findViewById(R.id.cardapproval);
        cardsetting=findViewById(R.id.cardsetting);
        cardlogout=findViewById(R.id.cardlogout);
        cardhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Hi Elite");
            }
        });

    cardleave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(MainActivity_dashboard.this,  "Leave Form Will Open!Thanks"  ,
                        Toast.LENGTH_LONG).show();
                Intent kp = new Intent(MainActivity_dashboard.this, MainActivity_leave_from.class);
                kp.putExtra("username",email);
                startActivity(kp);

            }

        });



        cardattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(MainActivity_dashboard.this,  "Attendance Form Will Open!Thanks"  ,
                        Toast.LENGTH_LONG).show();
                Intent ap = new Intent(MainActivity_dashboard.this, MainActivity2.class);
                startActivity(ap);
            }
        });
        cardapproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity_dashboard.this,  "Approval Form Will Open!Thanks"  ,
                        Toast.LENGTH_LONG).show();
                Intent ap = new Intent(MainActivity_dashboard.this, MainActivity_leave_approval.class);
                startActivity(ap);
            }
        });
        cardsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Hi settings");
            }
        });
        cardlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Hi Logout");
            }
        });
    }

    private void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}