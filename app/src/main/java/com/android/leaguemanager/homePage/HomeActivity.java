package com.android.leaguemanager.homePage;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.leaguemanager.R;
import com.android.leaguemanager.homePage.homePageFragments.UserHomeFragment;
import com.android.leaguemanager.homePage.homePageFragments.UserLeagueFragment;
import com.android.leaguemanager.homePage.homePageFragments.UserProfileFragment;
import com.android.leaguemanager.homePage.homePageFragments.UserSearchFragment;
import com.android.leaguemanager.homePage.homePageFragments.UserStreamsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameContainer;
    private TextView tvToolbarTitle;
    private int fragmentToLoad;
    private Fragment fm;
    private FloatingActionButton fbNavigationSearch;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        frameContainer = findViewById(R.id.frame_container);
        tvToolbarTitle = findViewById(R.id.toolbar_title);
        fbNavigationSearch = findViewById(R.id.floating_button_search);

        // home is the default
        fragmentToLoad = 0;

        // navigation items -  bottom
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        // setting default view
        if (savedInstanceState == null) {
            if (fragmentToLoad != 2) {
                MenuItem item = bottomNavigationView.getMenu().getItem(fragmentToLoad);
                onNavigationItemSelected(item);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // making sure only one item can be checked
        if (item.isChecked()) item.setChecked(false);
        else item.setChecked(true);
        invalidateOptionsMenu();

        // setting the default behavior of the items on the bottom navigation
        switch (item.getItemId()) {
            case R.id.action_home:
                clearSearchNavigationBarTint();
                fm = new UserHomeFragment();
                // tag for home (required to update the fragment)
                break;
            case R.id.action_league:
                clearSearchNavigationBarTint();
                fm = new UserLeagueFragment();
                break;
            case R.id.action_search:
                tintSearchNavigationBar();
                fm = new UserSearchFragment();
                break;
            case R.id.action_streams:
                clearSearchNavigationBarTint();
                fm = new UserStreamsFragment();
                break;
            case R.id.action_profile:
                clearSearchNavigationBarTint();
                fm = new UserProfileFragment();
                break;
            default:
                break;
        }
        replaceAndAttachFragment(fm);
        return true;
    }

    private void clearSearchNavigationBarTint() {
        fbNavigationSearch.setColorFilter(null);
    }

    private void tintSearchNavigationBar() {
        fbNavigationSearch.setColorFilter(ContextCompat.getColor(this, R.color.blue), android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    private void replaceAndAttachFragment(Fragment fragment) {
        if (fragment != null) {
            // setting the fragment to the current view
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fm)
                    .commit();
        }
    }
}
