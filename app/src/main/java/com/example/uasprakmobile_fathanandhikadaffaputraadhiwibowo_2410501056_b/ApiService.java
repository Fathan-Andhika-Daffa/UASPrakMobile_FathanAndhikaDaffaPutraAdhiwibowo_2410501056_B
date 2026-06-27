package com.example.uasprakmobile_fathanandhikadaffaputraadhiwibowo_2410501056_b;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("data_endemik/endemik.json")
    Call<List<Endemik>> getEndemik();
}
