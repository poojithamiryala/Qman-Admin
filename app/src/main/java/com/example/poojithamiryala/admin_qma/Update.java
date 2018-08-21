package com.example.poojithamiryala.admin_qma;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.poojithamiryala.admin_qma.Azure.mOrgan;
import static com.example.poojithamiryala.admin_qma.Main_1.SHAREDPREFFILE;
import static com.example.poojithamiryala.admin_qma.Main_1.USERIDPREF;

public class Update extends AppCompatActivity {
    EditText e1;
    EditText e2;
    EditText e3;
    EditText e4;
    Spinner s1;
    Button b1;
    TextView tv;
    List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        tv=(TextView)findViewById(R.id.username);
        b1=(Button)findViewById(R.id.submit);
        s1=(Spinner)findViewById(R.id.category);
        e1=(EditText)findViewById(R.id.nameorg);
        e2=(EditText)findViewById(R.id.city);
        e3=(EditText)findViewById(R.id.branch);
        e4=(EditText)findViewById(R.id.service);
        setValues();
    }
    public void setValues()
    {
        List<Organization_details> item;
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    //final String uname=loadUserTokenCache(Azure.mClient);
                    final List<Organization_details> item= mOrgan.where().field("Username").eq(Main_1.username1).execute().get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Signup_details entity = addItemInTable(item);
                                Log.e("Added","1");
                                for(Organization_details i:item)
                                {
                                    if(i.getUsername().equals(Main_1.username1)) {
                                        Log.e(i.getCategory().toString(), "1");
                                        tv.setText(Main_1.username1);
                                        s1.setSelection(getIndex(s1, i.getCategory().toString()));
                                        e1.setText(i.getName().toString());
                                        e2.setText(i.getCity().toString());
                                        e3.setText(i.getBranches().toString());
                                        e4.setText(i.getServices().toString());
                                        break;
                                        //list = new ArrayList<String>(Arrays.asList(i.getServices().split(",")));
                                    }
                                }
                        }
                    });
                } catch (final Exception e) {
                 //   createAndShowDialogFromTask(e, "Error");
                }
                return null;
            }
        };

        runAsyncTask(task);

    }
    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
    private String loadUserTokenCache(MobileServiceClient client)
    {
        SharedPreferences prefs = getSharedPreferences(SHAREDPREFFILE, Context.MODE_PRIVATE);
        String userId = prefs.getString(USERIDPREF, null);
        return userId;
    }
    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }
}
