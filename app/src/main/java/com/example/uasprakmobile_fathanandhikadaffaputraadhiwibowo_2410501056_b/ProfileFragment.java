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

public class ProfileFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                TextView toolbarTitle = activity.findViewById(R.id.tv_toolbar_title);
                if (toolbarTitle != null) {
                    toolbarTitle.setText(R.string.nav_profile);
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
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear(); // Hide menu items in profile
        super.onCreateOptionsMenu(menu, inflater);
    }
}
