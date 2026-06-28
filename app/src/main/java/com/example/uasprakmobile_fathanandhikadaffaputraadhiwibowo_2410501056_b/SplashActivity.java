package com.example.uasprakmobile_fathanandhikadaffaputraadhiwibowo_2410501056_b;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.button.MaterialButton;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar pbLoading;
    private MaterialButton btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splash_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pbLoading = findViewById(R.id.pb_loading);
        btnContinue = findViewById(R.id.btn_continue);

        btnContinue.setOnClickListener(v -> {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            finish();
        });

        fetchData();
    }

    private void fetchData() {
        RetrofitClient.getApiService().getEndemik().enqueue(new Callback<List<Endemik>>() {
            @Override
            public void onResponse(Call<List<Endemik>> call, Response<List<Endemik>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Data is ready
                    pbLoading.setVisibility(View.GONE);
                    btnContinue.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(SplashActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                    // Optional: show retry or move to home anyway
                }
            }

            @Override
            public void onFailure(Call<List<Endemik>> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "Koneksi Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                // move anyway for now or show error state
                pbLoading.setVisibility(View.GONE);
                btnContinue.setVisibility(View.VISIBLE);
            }
        });
    }
}
