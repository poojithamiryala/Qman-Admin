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
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceUser;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.example.poojithamiryala.admin_qma.Azure.mClient;
import static com.example.poojithamiryala.admin_qma.Azure.mSignIn;
import static com.example.poojithamiryala.admin_qma.Azure.mToDoTable;
import static com.example.poojithamiryala.admin_qma.Azure.user;
import static com.example.poojithamiryala.admin_qma.Main_1.TOKENPREF;
import static com.example.poojithamiryala.admin_qma.Main_1.USERIDPREF;

public class MainActivity extends AppCompatActivity {
    EditText uname;
    EditText pass;
    Button login;
    Button signup;
    List<Signup_details> result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* try {
            mclient = new MobileServiceClient("https://qman.azurewebsites.net",this);
            Azure.mclient=mclient;
            mclient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                @Override
                public OkHttpClient createOkHttpClient() {
                    OkHttpClient client = new OkHttpClient();
                    client.setReadTimeout(20, TimeUnit.SECONDS);
                    client.setWriteTimeout(20, TimeUnit.SECONDS);
                    return client;
                }
            });
            /*Azure.mToDoTable = mclient.getTable(LoginAdmin.class);
            try {
                initLocalStore().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (MobileServiceLocalStoreException e) {
                e.printStackTrace();
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }*/
        uname=(EditText)findViewById(R.id.un);
        pass=(EditText)findViewById(R.id.pass);
        login=(Button)findViewById(R.id.login);
        signup=(Button)findViewById(R.id.btn_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddContact.class);
                startActivity(intent);
            }
        });
        /*login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Admin_Login.class);
                startActivity(intent);
            }
        });*/
    }
    public void addItem(View v)
    {
        String password;
        cacheUserToken(user);
        Main_1.username1=uname.getText().toString();
        password = pass.getText().toString();
            if (mClient == null) {
                return;
            }
           // final SignIn item = new SignIn();
           // item.setPassword(password);
            //item.setUsername(uname.getText().toString());
            // Insert the new item
        final String user1=uname.getText().toString();
            AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        //SignIn entity = addItemInTable(item);
                        result = mToDoTable.where().field("username").eq(user1).execute().get();
                        if(result.size()!=0)
                        {
                            Intent intent = new Intent(getApplicationContext(),StartQueue.class);
                            startActivity(intent);
                        }
                        else
                        {
                            throw new Exception("Username already in use!!Please do register with new username.");
                            // Toast.makeText(getApplicationContext(),"Username already in use!!",Toast.LENGTH_LONG).show();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Signup_details entity = addItemInTable(item);
                                //Toast.makeText(getApplicationContext(),"Signned up succesfully",Toast.LENGTH_LONG).show();

                            }
                        });
                    } catch (final Exception e) {
                        createAndShowDialogFromTask(e, "Error");
                    }
                    return null;
                }
            };

            runAsyncTask(task);
            uname.setText("");
            pass.setText("");
    }

    private void cacheUserToken(MobileServiceUser user)
    {
        SharedPreferences prefs = getSharedPreferences(Main_1.SHAREDPREFFILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Main_1.USERIDPREF,uname.getText().toString());
        editor.putString(Main_1.TOKENPREF,pass.getText().toString());
        editor.commit();
    }
    /**
     * Add an item to the Mobile Service Table
     *
     * @param item
     *            The item to Add
     */
    public SignIn addItemInTable(SignIn item) throws ExecutionException, InterruptedException {
        SignIn entity = mSignIn.insert(item).get();
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


   /* private AsyncTask<Void, Void, Void> initLocalStore() throws MobileServiceLocalStoreException, ExecutionException, InterruptedException {

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    MobileServiceSyncContext syncContext = mClient.getSyncContext();

                    if (syncContext.isInitialized())
                        return null;

                    SQLiteLocalStore localStore = new SQLiteLocalStore(mClient.getContext(), "OfflineStore", null, 1);

                    Map<String, ColumnDataType> tableDefinition = new HashMap<String, ColumnDataType>();
                    tableDefinition.put("uname", ColumnDataType.String);
                    tableDefinition.put("password", ColumnDataType.String);
                    tableDefinition.put("email",ColumnDataType.String);
                    tableDefinition.put("contact",ColumnDataType.String);
                    //tableDefinition.put("complete", ColumnDataType.Boolean);

                    localStore.defineTable("Signup_details", tableDefinition);

                    SimpleSyncHandler handler = new SimpleSyncHandler();

                    syncContext.initialize(localStore, handler).get();

                } catch (final Exception e) {
                    //createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };

        return runAsyncTask(task);
    }

    private AsyncTask<Void,Void,Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            //return task.execute();
        } else {
            return task.execute();
        }
    }*/
}
