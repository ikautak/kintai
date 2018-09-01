package com.example.kintai;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
    private static final String COMPANY_CODE = "xxx";
    private static final String ID = "xxx";
    private static final String PASSWORD = "xxx";
    private static final String URL = "https://arc-kkc.com/login.do";

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView)findViewById(R.id.webview_main);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(true);

        mWebView.setWebViewClient(new WebViewClient() {
            String loginCookie = "";
            @Override
            public void onLoadResource(WebView wv, String url) {
                super.onLoadResource(wv, url);
                CookieManager cmgr = CookieManager.getInstance();
                loginCookie = cmgr.getCookie(url);
            }

            @Override
            public void onPageFinished(WebView wv, String url) {
                super.onPageFinished(wv, url);
                CookieManager cmgr = CookieManager.getInstance();
                cmgr.setCookie(url, loginCookie);

                String script = "javascript:document.forms['LoginForm'].companyCode.value='" + COMPANY_CODE + "';";
                script += "document.forms['LoginForm'].id.value='" + ID + "';";
                script += "document.forms['LoginForm'].password.value='" + PASSWORD + "';";
                script += "document.forms['LoginForm'].submit();";
                wv.loadUrl(script);
            }
        });

        mWebView.loadUrl(URL);
        mWebView.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);
            return rootView;
        }
    }

}
