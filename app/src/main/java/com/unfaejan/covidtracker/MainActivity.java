package com.unfaejan.covidtracker;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mtotalCasesTV;
    private TextView mtotalRecoveryTV;
    private TextView mtotalDeathTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtotalCasesTV = findViewById(R.id.totalCasesTV);
        mtotalRecoveryTV = findViewById(R.id.totalRecoveredTV);
        mtotalDeathTV = findViewById(R.id.totalDeathTV);

        getCovidAll();
    }

    private void getCovidAll() {
        Toast.makeText(getApplicationContext() ,"Sedang Menghubung ke Server...",Toast.LENGTH_SHORT).show();
        Call<Covid> callCovid = RetrofitClient.getInstance().getMyApi().getCovidAll();

        callCovid.enqueue(new Callback<Covid>() {
            @Override
            public void onResponse(@NonNull Call<Covid> call, @NonNull Response<Covid> response) {
                Covid dataCovid = response.body();

                assert dataCovid != null;
                mtotalCasesTV.setText(String.valueOf(dataCovid.getTotalCases()));
                mtotalRecoveryTV.setText(String.valueOf(dataCovid.getTotalRecovered()));
                mtotalDeathTV.setText(String.valueOf(dataCovid.getTotalDeath()));

                System.out.println(dataCovid.getTotalCases());
                System.out.println(dataCovid.getTotalDeath());
                System.out.println(dataCovid.getTotalRecovered());
                Toast.makeText(getApplicationContext() ,"Berhasil Terhubung",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<Covid> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext() ,"Gagal Terhubung",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext() ,String.valueOf(t),Toast.LENGTH_LONG).show();
            }
        });

    }
}