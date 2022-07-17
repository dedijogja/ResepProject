package id.varianresep.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import id.varianresep.R;


public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ((WebView)findViewById(R.id.helpWebView)).loadUrl("file:///android_asset/about.html");

    }
}
