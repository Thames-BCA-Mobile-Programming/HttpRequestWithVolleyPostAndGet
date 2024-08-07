package com.api.example;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //    JSONObject postParams = new JSONObject();
//        try {
//        postParams.put("title", "foo");
//        postParams.put("body", "bar");
//        postParams.put("userId", 1);
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }
    RequestQueue myQueue;
    ListView myListView;

    List<String> items = new ArrayList<>();

    Button button ,button_login;
//    ArrayAdapter<String> myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myListView = findViewById(R.id.lv);
        button = findViewById(R.id.btn1);
        button_login = findViewById(R.id.btn_login);
        myQueue = Volley.newRequestQueue(this);
//        myAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items);
        ArrayAdapter<String> myAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items);
        myListView.setAdapter(myAdapter);
        fetchDataFromNetwork(myAdapter);

        methodToLogin();



    }

    void methodToLogin(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username","emilys");
            jsonObject.put("password","emilyspass");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, "https://dummyjson.com/auth/login", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(MainActivity.this, ""+response.toString(), Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myQueue.add(loginRequest);

            }
        });

    }

    void fetchDataFromNetwork(ArrayAdapter<String> myAdapter) {
        String url = "https://jsonplaceholder.typicode.com/posts";

        JsonArrayRequest jsr = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObjectRequest = response.getJSONObject(i);
                        items.add(jsonObjectRequest.getString("body"));


                        myAdapter.notifyDataSetChanged();


                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myQueue.add(jsr);

            }
        });
    }
}