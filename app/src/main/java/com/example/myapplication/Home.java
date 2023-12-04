package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Home extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton fab_main, fab_sub1;
    private boolean isFabOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fab_main = findViewById(R.id.fab_main);
        fab_sub1 = findViewById(R.id.fab_sub1);

        // OnClickListener 설정
        fab_main.setOnClickListener(this);
        fab_sub1.setOnClickListener(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        // 앱 시작 시 홈 화면을 표시합니다.
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    int itemId = item.getItemId();

                    if (itemId == R.id.menu_home) {
                        selectedFragment = new HomeFragment();
                    } else if (itemId == R.id.menu_profile) {
                        selectedFragment = new ProfileFragment();
                    } else if (itemId == R.id.menu_search) {
                        selectedFragment = new SearchFragment();
                    }

                    if (selectedFragment != null) {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragmentContainer, selectedFragment);
                        transaction.commit();
                    }

                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_home) {
            // Home 아이템이 선택되었을 때의 동작
            return true;
        } else if (itemId == R.id.menu_profile) {
            // Profile 아이템이 선택되었을 때의 동작
            return true;
        }else if (itemId == R.id.menu_search) {
            // Profile 아이템이 선택되었을 때의 동작
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.fab_main) {
            toggleFab();
        } else if (viewId == R.id.fab_sub1) {
            toggleFab();
            // 포스팅 화면으로 이동
            Intent intent = new Intent(this, activity_posting.class);
            startActivity(intent);
        }
    }

    private void toggleFab() {
        if (isFabOpen) {
            fab_main.setImageResource(R.drawable.baseline_add_24);
            fab_sub1.setVisibility(View.GONE);
            fab_sub1.setClickable(false);
            isFabOpen = false;
        } else {
            fab_main.setImageResource(R.drawable.baseline_close_24);
            fab_sub1.setClickable(true);
            fab_sub1.setVisibility(View.VISIBLE);
            isFabOpen = true;
        }
    }
}
