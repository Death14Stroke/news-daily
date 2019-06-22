package com.andruid.magic.newsdaily.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.andruid.magic.newsdaily.R;
import com.andruid.magic.newsdaily.databinding.ActivityHomeBinding;
import com.andruid.magic.newsdaily.fragment.NewsFragment;
import com.andruid.magic.newsdaily.pref.SettingsFragment;
import com.andruid.magic.newsdaily.util.AssetsUtil;
import com.andruid.magic.newsdaily.util.StringUtils;
import com.cleveroad.loopbar.widget.OnItemClickListener;

import java.io.IOException;
import java.util.List;

import timber.log.Timber;

import static com.andruid.magic.newsdaily.data.Constants.POS_SETTINGS;

public class HomeActivity extends AppCompatActivity implements OnItemClickListener {
    private static final String TAG = "assetslog";
    private ActivityHomeBinding binding;
    private List<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        setSupportActionBar(binding.toolBar);
        loadCategories();
        binding.loopBar.addOnItemClickListener(this);
    }

    private void loadCategories() {
        try {
            categories = AssetsUtil.readCategories(getAssets());
            loadFirstFrag();
            Timber.tag(TAG).d("categories try %d", categories.size());
        } catch (IOException e) {
            Timber.tag(TAG).d("categories catch");
            e.printStackTrace();
        }
    }

    private void loadFirstFrag() {
        String category = categories.get(0);
        Fragment fragment = NewsFragment.newInstance(category);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commit();
        binding.toolBar.setTitle(StringUtils.capFirstLetter(category));
    }

    @Override
    public void onItemClicked(int position) {
        Timber.tag(TAG).d("clicked: %d", position);
        if(position >= 0 && position < categories.size()) {
            String category = categories.get(position);
            Fragment fragment = NewsFragment.newInstance(category);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            binding.toolBar.setTitle(StringUtils.capFirstLetter(category));
        }
        else if(position == POS_SETTINGS){
            Fragment fragment = SettingsFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            binding.toolBar.setTitle("Settings");
        }

    }
}