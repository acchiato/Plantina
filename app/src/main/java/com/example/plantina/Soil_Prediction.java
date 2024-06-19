package com.example.plantina;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Soil_Prediction extends AppCompatActivity {
    EditText ph, temperature;

    TextView predictionValue;
    Button predictbutton,okclickbtn;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        ph = findViewById(R.id.pdata);
        temperature = findViewById(R.id.tempdata);
        predictionValue = findViewById(R.id.predictionValue);
        okclickbtn = findViewById(R.id.okclickbtn);

        predictbutton = findViewById(R.id.pbutton);

        predictbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApiClient.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiClient apiInterface = retrofit.create(ApiClient.class);


                try {
                    String pH = ph.getText().toString();
                    String Temperature = temperature.getText().toString();
                    RequestData req = new RequestData();
                    req.setpH(pH);
                    req.setTemperature(Temperature);

                    Call<ResultData> userCall = apiInterface.getresult(req);
                    userCall.enqueue(new Callback<ResultData>() {
                        @Override
                        public void onResponse(Call<ResultData> call, Response<ResultData> response) {


                            ResultData res = response.body();

                            if (res == null)
                                return;


                            predictionValue.setText(res.getPrediction());

                        }

                        @Override
                        public void onFailure(Call<ResultData> call, Throwable t) {
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        okclickbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Soil_Prediction.this  , Plant_Details_Page.class);
                intent.putExtra("Result", predictionValue.getText().toString());
                startActivity(intent);
            }
        });

    }
}
