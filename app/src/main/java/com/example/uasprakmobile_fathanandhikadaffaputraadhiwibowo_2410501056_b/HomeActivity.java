package com.example.uasprakmobile_fathanandhikadaffaputraadhiwibowo_2410501056_b;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class HomeActivity extends AppCompatActivity {

    private EditText etSearch;
    private MaterialCardView searchCard;
    private TextView tvToolbarTitle;
    private boolean isSearchActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        tvToolbarTitle = findViewById(R.id.tv_toolbar_title);
        searchCard = findViewById(R.id.search_card);
        etSearch = findViewById(R.id.et_search);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCurrentFragment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HewanFragment())
                    .commit();
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();
            if (itemId == R.id.nav_hewan) {
                selectedFragment = new HewanFragment();
            } else if (itemId == R.id.nav_tumbuhan) {
                selectedFragment = new TumbuhanFragment();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                
                updateToolbarForMain();
            }
            return true;
        });
    }

    private void filterCurrentFragment(String query) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (currentFragment instanceof HewanFragment) {
            ((HewanFragment) currentFragment).filter(query);
        } else if (currentFragment instanceof TumbuhanFragment) {
            ((TumbuhanFragment) currentFragment).filter(query);
        }
    }

    public void updateToolbarForMain() {
        isSearchActive = false;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        if (tvToolbarTitle != null) {
            tvToolbarTitle.setText("EndemikDB");
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) tvToolbarTitle.getLayoutParams();
            params.horizontalBias = 0.0f;
            params.setMarginStart((int) (16 * getResources().getDisplayMetrics().density));
            tvToolbarTitle.setLayoutParams(params);
        }
        hideSearch();
        invalidateOptionsMenu();
    }

    public void hideSearch() {
        if (searchCard != null) {
            searchCard.setVisibility(View.GONE);
            etSearch.setText("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (isSearchActive) {
            menu.findItem(R.id.action_search).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_search) {
            showSearchMode();
            return true;
        } else if (itemId == R.id.action_view_favorit) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FavoritFragment())
                    .addToBackStack(null)
                    .commit();
            hideSearch();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSearchMode() {
        isSearchActive = true;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (tvToolbarTitle != null) {
            tvToolbarTitle.setText("Pencarian");
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) tvToolbarTitle.getLayoutParams();
            params.horizontalBias = 0.5f;
            params.setMarginStart(0);
            tvToolbarTitle.setLayoutParams(params);
        }
        searchCard.setVisibility(View.VISIBLE);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (isSearchActive) {
            updateToolbarForMain();
            return true;
        }
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}
