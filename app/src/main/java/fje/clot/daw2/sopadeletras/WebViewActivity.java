package fje.clot.daw2.sopadeletras;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView myWebView = (WebView) this.findViewById(R.id.webView);
        myWebView.loadUrl("file:///android_asset/portada.html");

    }
}