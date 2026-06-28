package com.example.uasprakmobile_fathanandhikadaffaputraadhiwibowo_2410501056_b;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FavoritFragment extends Fragment {
    private RecyclerView rvFavorit;
    private TextView tvEmpty;
    private EndemikAdapter adapter;
    private List<Endemik> favoritList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFavorit = view.findViewById(R.id.rv_favorit);
        tvEmpty = view.findViewById(R.id.tv_empty);

        rvFavorit.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                TextView toolbarTitle = activity.findViewById(R.id.tv_toolbar_title);
                if (toolbarTitle != null) {
                    toolbarTitle.setText(R.string.nav_favorit);
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) toolbarTitle.getLayoutParams();
                    params.horizontalBias = 0.5f;
                    params.setMarginStart(0);
                    toolbarTitle.setLayoutParams(params);
                }
            }
            if (activity instanceof HomeActivity) {
                ((HomeActivity) activity).hideSearch();
            }
        }
        loadFavorit();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear(); // Hide favorite icon in favorites screen
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loadFavorit() {
        List<Favorit> localFavorit = AppDatabase.getInstance(getContext()).favoritDao().getAllFavorit();
        favoritList.clear();
        for (Favorit f : localFavorit) {
            favoritList.add(new Endemik(
                    f.getId(), f.getTipe(), f.getNama(), f.getNamaLatin(),
                    f.getDeskripsi(), f.getFoto(), f.getAsal()
            ));
        }

        if (favoritList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvFavorit.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvFavorit.setVisibility(View.VISIBLE);
            adapter = new EndemikAdapter(favoritList, endemik -> {
                DetailFragment detailFragment = DetailFragment.newInstance(endemik);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, detailFragment)
                        .addToBackStack(null)
                        .commit();
            });
            rvFavorit.setAdapter(adapter);
        }
    }
}
