package jp.co.conic.conic1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HomePageActivity extends AppCompatActivity {
    private final String HOMEPAGEURL = "http://www.conic.co.jp/index.html";
    WebView homePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mapView();
    }

    private void mapView() {
        homePage = findViewById(R.id.webHomePage);
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void displayHomePage() {
        homePage.setWebViewClient(new WebViewClient());
        homePage.loadUrl(HOMEPAGEURL);
        WebSettings settings =homePage.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayHomePage();
    }
}
