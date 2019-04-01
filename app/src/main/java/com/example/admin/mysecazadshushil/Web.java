package com.example.admin.mysecazadshushil;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class Web extends AppCompatActivity {
WebView mysec;
ProgressBar webprogress;
LinearLayout weblayout, webprotext;
RelativeLayout errormassege;
TextView webtext;
String currentUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        //getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        try{
            ViewConfiguration config=ViewConfiguration.get(this);
            Field menuKeyField=ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField !=null)
                menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        }catch(Exception ex)
        {

        }

        mysec=(WebView)findViewById(R.id.mysec);
        webprogress=(ProgressBar)findViewById(R.id.webprogress);
        webprogress.setMax(100);

        errormassege=(RelativeLayout)findViewById(R.id.errormassege);
        weblayout=(LinearLayout)findViewById(R.id.weblayout);
        webprotext=(LinearLayout)findViewById(R.id.webprotext);
        webtext=(TextView)findViewById(R.id.webtext);

        if(isNetworkAvailable()==false)
        {
            errormassege.setVisibility(View.VISIBLE);
            webprotext.setVisibility(View.GONE);
        }
        WebSettings webSettings=mysec.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mysec.loadUrl("https://www.sec.ac.bd/");
        mysec.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                webprotext.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myseczoom);
                webtext.startAnimation(animation);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                currentUrl=url;
                webprotext.setVisibility(View.GONE);
            }

        });
        mysec.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                webprogress.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                getSupportActionBar().setTitle(title);
            }

            //@Override
            //public void onReceivedIcon(WebView view, Bitmap icon) {
                //super.onReceivedIcon(view, icon);
                //webimageView.setImageBitmap(icon);
            //}
        });
        mysec.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(url));

                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "SEC.pdf");
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading File...Please wait", //To notify the Client that the file is being downloaded
                        Toast.LENGTH_LONG).show();

            }
        });

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.webmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.webback:
                onBackPressed();
                break;
            case R.id.webforward:
                onForward();
                break;
            case R.id.webhome:
                Intent homeintent=new Intent(getApplicationContext(), Home.class);
                startActivity(homeintent);
                finish();
                break;
            case R.id.webrefresh:
                mysec.reload();
                break;
            case R.id.webshare:
                Intent shareapp=new Intent(Intent.ACTION_SEND);
                shareapp.setType("text/plain");
                shareapp.putExtra(Intent.EXTRA_TEXT, currentUrl);
                String applink="http://play.google.com";
                shareapp.putExtra(Intent.EXTRA_SUBJECT, "Copied URL");
                startActivity(Intent.createChooser(shareapp, "Share url with your friends"));
                break;
            case R.id.webradio:
                mysec.loadUrl("https://sec.ac.bd/campusradio/");
        }
        return super.onOptionsItemSelected(item);
    }
    public void onForward()
    {
        if(mysec.canGoForward())
        {
            mysec.goForward();
        }
        else
        {
            Toast.makeText(this, "Sorry! Can't Go Further", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        if(mysec.canGoBack())
        {
            mysec.goBack();
        }
        else
        {
            super.onBackPressed();
        }
    }
}
