package com.example.uasprakmobile_fathanandhikadaffaputraadhiwibowo_2410501056_b;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;

public class DetailFragment extends Fragment {
    private static final String ARG_ENDEMIK = "endemik";
    private Endemik endemik;
    private boolean isFavorit = false;
    private MenuItem favoriteMenuItem;

    public static DetailFragment newInstance(Endemik endemik) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ENDEMIK, endemik);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            endemik = (Endemik) getArguments().getSerializable(ARG_ENDEMIK);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imgDetail = view.findViewById(R.id.img_detail);
        TextView tvNama = view.findViewById(R.id.tv_detail_nama);
        TextView tvNamaLatin = view.findViewById(R.id.tv_detail_nama_latin);
        TextView tvAsal = view.findViewById(R.id.tv_detail_asal);
        TextView tvDeskripsi = view.findViewById(R.id.tv_detail_deskripsi);

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                TextView toolbarTitle = activity.findViewById(R.id.tv_toolbar_title);
                if (toolbarTitle != null && endemik != null) {
                    toolbarTitle.setText(endemik.getNama());
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) toolbarTitle.getLayoutParams();
                    params.horizontalBias = 0.5f;
                    params.setMarginStart(0); // Remove the 16dp margin for centering
                    toolbarTitle.setLayoutParams(params);
                }
            }
            if (activity instanceof HomeActivity) {
                ((HomeActivity) activity).hideSearch();
            }
        }

        if (endemik != null) {
            tvNama.setText(endemik.getNama());
            tvNamaLatin.setText(endemik.getNamaLatin());
            tvAsal.setText(getString(R.string.origin_label, endemik.getAsal()));
            tvDeskripsi.setText(endemik.getDeskripsi());

            Glide.with(this)
                    .load(endemik.getFoto())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(imgDetail);

            FavoritDao dao = AppDatabase.getInstance(getContext()).favoritDao();
            isFavorit = dao.isFavorit(endemik.getId());
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.detail_menu, menu);
        favoriteMenuItem = menu.findItem(R.id.action_favorite);
        updateMenuIcon();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            toggleFavorite();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleFavorite() {
        if (endemik == null) return;
        
        FavoritDao dao = AppDatabase.getInstance(getContext()).favoritDao();
        if (isFavorit) {
            dao.delete(new Favorit(
                    endemik.getId(), endemik.getTipe(), endemik.getNama(),
                    endemik.getNamaLatin(), endemik.getDeskripsi(),
                    endemik.getFoto(), endemik.getAsal()
            ));
        } else {
            dao.insert(new Favorit(
                    endemik.getId(), endemik.getTipe(), endemik.getNama(),
                    endemik.getNamaLatin(), endemik.getDeskripsi(),
                    endemik.getFoto(), endemik.getAsal()
            ));
        }
        isFavorit = !isFavorit;
        updateMenuIcon();
    }

    private void updateMenuIcon() {
        if (favoriteMenuItem != null) {
            if (isFavorit) {
                favoriteMenuItem.setIcon(R.drawable.ic_favorit);
                favoriteMenuItem.getIcon().setTint(ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark));
            } else {
                favoriteMenuItem.setIcon(R.drawable.ic_favorit_border);
                favoriteMenuItem.getIcon().setTint(ContextCompat.getColor(requireContext(), android.R.color.darker_gray));
            }
        }
    }
}
