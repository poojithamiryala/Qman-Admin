package com.example.poojithamiryala.admin_qma;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import java.util.concurrent.ExecutionException;

import static com.example.poojithamiryala.admin_qma.Main_1.SHAREDPREFFILE;
import static com.example.poojithamiryala.admin_qma.Main_1.USERIDPREF;

public class Step_add1 extends AppCompatActivity {
    Button b1;
    Spinner s1;
    EditText e1;
    EditText e2;
    EditText e3;
    EditText e4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_add1);
        b1=(Button)findViewById(R.id.submit);
        s1=(Spinner)findViewById(R.id.category);
        e1=(EditText)findViewById(R.id.nameorg);
        e2=(EditText)findViewById(R.id.city);
        e3=(EditText)findViewById(R.id.branch);
        e4=(EditText)findViewById(R.id.service);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });
    }
    public void addItem(View v)
    {
        //check that all fields must be entered
        String name=e1.getText().toString();
        String branch=e3.getText().toString();
        String city=e2.getText().toString();
        String service=e4.getText().toString();
        String category=s1.getSelectedItem().toString();
       // String username=loadUserTokenCache(Azure.mClient);
        final Organization_details item=new Organization_details();
        item.setUsername(Main_1.username1);
        item.setBranches(branch);
        item.setCategory(category);
        item.setName(name);
        item.setCity(city);
        item.setServices(service);
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Organization_details entity = addItemInTable(item);
                    Intent intent = new Intent(getApplicationContext(),Admin_Login.class);
                    startActivity(intent);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                } catch (final Exception e) {
                    createAndShowDialogFromTask(e, "Error");
                }
                return null;
            }
        };

        runAsyncTask(task);
    }
    public Organization_details addItemInTable(Organization_details item) throws ExecutionException, InterruptedException {
        Organization_details entity = Azure.mOrgan.insert(item).get();
        return entity;
    }
    private void createAndShowDialogFromTask(final Exception exception, String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createAndShowDialog(exception, "Error");
            }
        });
    }


    /**
     * Creates a dialog and shows it
     *
     * @param exception
     *            The exception to show in the dialog
     * @param title
     *            The dialog title
     */
    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if(exception.getCause() != null){
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

    /**
     * Creates a dialog and shows it
     *
     * @param message
     *            The dialog message
     * @param title
     *            The dialog title
     */
    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

    /**
     * Run an ASync task on the corresponding executor
     * @param task
     * @return
     */
    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }


    private String loadUserTokenCache(MobileServiceClient client)
    {
        SharedPreferences prefs = getSharedPreferences(SHAREDPREFFILE, Context.MODE_PRIVATE);
        String userId = prefs.getString(USERIDPREF, null);
        return userId;
    }
}
