package com.example.uasprakmobile_fathanandhikadaffaputraadhiwibowo_2410501056_b;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HewanFragment extends Fragment {
    private RecyclerView rvHewan;
    private ProgressBar progressBar;
    private EndemikAdapter adapter;
    private List<Endemik> listHewan = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hewan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        rvHewan = view.findViewById(R.id.rv_hewan);
        progressBar = view.findViewById(R.id.progress_bar);
        
        rvHewan.setLayoutManager(new GridLayoutManager(getContext(), 2));
        
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.invalidateOptionsMenu();
            if (activity instanceof HomeActivity) {
                ((HomeActivity) activity).updateToolbarForMain();
            }
        }
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getApiService().getEndemik().enqueue(new Callback<List<Endemik>>() {
            @Override
            public void onResponse(Call<List<Endemik>> call, Response<List<Endemik>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    listHewan.clear();
                    for (Endemik item : response.body()) {
                        if ("Hewan".equalsIgnoreCase(item.getTipe())) {
                            listHewan.add(item);
                        }
                    }
                    adapter = new EndemikAdapter(listHewan, endemik -> {
                        DetailFragment detailFragment = DetailFragment.newInstance(endemik);
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, detailFragment)
                                .addToBackStack(null)
                                .commit();
                    });
                    rvHewan.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Endemik>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Gagal memuat data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void filter(String query) {
        List<Endemik> filteredList = new ArrayList<>();
        for (Endemik item : listHewan) {
            if (item.getNama().toLowerCase().contains(query.toLowerCase()) || 
                item.getNamaLatin().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (adapter != null) {
            adapter.filterList(filteredList);
        }
    }
}
