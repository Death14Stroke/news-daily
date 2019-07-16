package com.andruid.magic.newsdaily.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.andruid.magic.newsdaily.R;
import com.andruid.magic.newsdaily.databinding.ActivityWebViewBinding;
import com.andruid.magic.newsdaily.ui.MyWebChromeClient;

import java.util.Objects;

import static com.andruid.magic.newsdaily.data.Constants.KEY_URL;

public class WebViewActivity extends AppCompatActivity {
    private static final String CLIP_NEWS = "news";
    private ActivityWebViewBinding binding;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);
        Bundle extras = getIntent().getExtras();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        if(extras != null){
            url = extras.getString(KEY_URL);
            setWebView();
            if(actionBar != null)
                actionBar.setTitle(url);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.unbind();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView() {
        binding.webView.setWebChromeClient(new MyWebChromeClient(binding.progressBar));
        binding.webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });
        binding.webView.loadUrl(url);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setBuiltInZoomControls(true);
        binding.webView.getSettings().setUseWideViewPort(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_open_browser:
                Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse(url));
                startActivity(intent);
                break;
            case R.id.menu_copy:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(CLIP_NEWS, url);
                Objects.requireNonNull(clipboard).setPrimaryClip(clip);
                Toast.makeText(this, "Copied url", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}