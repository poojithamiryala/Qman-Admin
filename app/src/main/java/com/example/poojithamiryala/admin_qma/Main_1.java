package com.example.poojithamiryala.admin_qma;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceUser;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import static com.example.poojithamiryala.admin_qma.Azure.mbook;

public class Main_1 extends AppCompatActivity {
    MobileServiceClient mclient;
    public static final String SHAREDPREFFILE = "temp";
    public static final String USERIDPREF = "uid";
    public static final String TOKENPREF = "tkn";
    public static String username1;
    boolean auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);
        try {
            mclient = new MobileServiceClient("https://queueman.azurewebsites.net", this);
            Azure.mClient = mclient;
            mclient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                @Override
                public OkHttpClient createOkHttpClient() {
                    OkHttpClient client = new OkHttpClient();
                    client.setReadTimeout(20, TimeUnit.SECONDS);
                    client.setWriteTimeout(20, TimeUnit.SECONDS);
                    return client;
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Azure.mToDoTable = Azure.mClient.getTable(Signup_details.class);
        Azure.mSignIn=Azure.mClient.getTable(SignIn.class);
        Azure.mOrgan=Azure.mClient.getTable(Organization_details.class);
        Azure.madmin=Azure.mClient.getTable(AdminQ.class);
        mbook=Azure.mClient.getTable(BookUser.class);
        auth=loadUserTokenCache(mclient);
        if(auth==true)
        {
            Intent i=new Intent(getApplicationContext(),StartQueue.class);
            username1=loadUserTokenCache1(Azure.mClient);
            Toast.makeText(getApplicationContext(),loadUserTokenCache1(Azure.mClient),Toast.LENGTH_LONG).show();
            startActivity(i);
        }
        else {
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }

    }

    private boolean loadUserTokenCache(MobileServiceClient client)
    {
        SharedPreferences prefs = getSharedPreferences(SHAREDPREFFILE, Context.MODE_PRIVATE);
        String userId = prefs.getString(USERIDPREF, null);
        if (userId == null || userId=="")
            return false;
        String token = prefs.getString(TOKENPREF, null);
        if (token == null)
            return false;
        return true;
    }
    private String loadUserTokenCache1(MobileServiceClient client)
    {
        SharedPreferences prefs = getSharedPreferences(SHAREDPREFFILE, Context.MODE_PRIVATE);
        String userId = prefs.getString(USERIDPREF, null);
        if (userId != null || userId=="")
            return userId;
        else
            return null;

    }
}
