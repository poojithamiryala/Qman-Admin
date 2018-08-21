package com.example.poojithamiryala.admin_qma;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.base.Strings;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.poojithamiryala.admin_qma.Azure.mOrgan;
import static com.example.poojithamiryala.admin_qma.Main_1.SHAREDPREFFILE;
import static com.example.poojithamiryala.admin_qma.Main_1.USERIDPREF;

public class StartQueue extends AppCompatActivity {
    ListView lv;
    List<String> list;
    TextView tv;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_queue);
        lv=(ListView)findViewById(R.id.lv);
        tv=(TextView)findViewById(R.id.nameorg);
        list= new ArrayList<>();
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);
        setList();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String item=list.get(i).toString();
                Intent in=new Intent(getApplicationContext(),Fragment11.class);
                Bundle b=new Bundle();
                b.putString("service",item);
                b.putString("name",tv.getText().toString());
                in.putExtras(b);
                startActivity(in);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menustart, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId())
        {
            case R.id.settings:
                break;
        }
        return true;
    }
    public void setList()
    {
        List<Organization_details> item;
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                   //String uname=loadUserTokenCache(Azure.mClient);
                    //String uname=Azure.userName;
                    final List<Organization_details> item= mOrgan.where().field("Username").eq(Main_1.username1).execute().get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Signup_details entity = addItemInTable(item);
                            Log.e("Added","1");
                            for(Organization_details i:item)
                            {
                                list.addAll(Arrays.asList(i.getServices().split(",")));
                                tv.setText(i.getName());
                                adapter.notifyDataSetChanged();
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
