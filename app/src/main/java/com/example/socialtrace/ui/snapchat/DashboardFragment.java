package com.example.socialtrace.ui.snapchat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.socialtrace.R;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M )
        {
            if(!checkpermission())
            {
                requestpermission();
            }        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M )
        {
            Check_USer_Location_PERMSIISSION();
        }
        WebView mWebView;
        mWebView = (WebView) root.findViewById(R.id.webview);

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);



        WebSettings mywebsettings = mWebView.getSettings();
        mywebsettings.setJavaScriptEnabled(true);

        mywebsettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mywebsettings.setLoadWithOverviewMode(true);
        mywebsettings.setUseWideViewPort(true);
        mywebsettings.setPluginState(WebSettings.PluginState.ON);
        mywebsettings.setGeolocationEnabled(true);
        mywebsettings.setLoadsImagesAutomatically(true);
        mWebView.setWebChromeClient((new WebChromeClient()));
        mywebsettings.setDomStorageEnabled(true);
        mywebsettings.setAppCacheEnabled(true);
        mywebsettings.setBuiltInZoomControls(true);
        mywebsettings.setDatabaseEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });
        mWebView.setWebViewClient(new WebViewClient());

         final ProgressBar progressbar;

        progressbar = root.findViewById(R.id.progressBar);
        mWebView.loadUrl("https://map.snapchat.com/");

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressbar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressbar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });







        return root;
    }
    public boolean Check_USer_Location_PERMSIISSION()
    {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
            }
            else {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);

            }
            return false;
        }
        else
        {
            return true;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestpermission() {
        requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 2);
    }

    private boolean checkpermission() {
        return (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PERMISSION_GRANTED);
    }
}
