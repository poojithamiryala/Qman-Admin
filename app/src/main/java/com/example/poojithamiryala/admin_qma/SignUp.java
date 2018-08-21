package com.example.poojithamiryala.admin_qma;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.R.attr.button;
import static com.example.poojithamiryala.admin_qma.Azure.mClient;
import static com.example.poojithamiryala.admin_qma.Azure.mToDoTable;
import static com.example.poojithamiryala.admin_qma.Azure.user;

public class SignUp extends AppCompatActivity {
    EditText uname;
    EditText pass;
    EditText cpass;
    EditText contact;
    EditText emailid;
    Button signup;
    int size1;
    String password;
    String cpassword;
    String uname1;
    Signup_details item;
    List<Signup_details> result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        uname=(EditText)findViewById(R.id.un);
        pass=(EditText)findViewById(R.id.pass);
        cpass=(EditText)findViewById(R.id.cpass);
        contact=(EditText)findViewById(R.id.contact);
        emailid=(EditText)findViewById(R.id.email);
        signup=(Button)findViewById(R.id.btn_signup);
       // size1=0;
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = pass.getText().toString();
                cpassword=cpass.getText().toString();
                uname1=uname.getText().toString();
                if(password.equals(cpassword))
                {
                    addItem(view);
                    if(size1==0)
                    {
                    }
                  /*  else
                    {
                        Toast.makeText(getApplicationContext(),"Username already in use!!",Toast.LENGTH_LONG).show();
                    }*/
                }
                else
                {
                    //toast message-enter password,cpassword same
                }
            }
        });
    }
    public void addItem(View v)
    {
            if (mClient == null) {
                return;
            }
                item = new Signup_details();
                item.setContactnumber(contact.getText().toString());
                item.setEmail(emailid.getText().toString());
                item.setPassword(password);
                item.setUsername(uname.getText().toString());
                final String user1=uname.getText().toString();
                // Insert the new item
                AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            result = mToDoTable.where().field("username").eq(user1).execute().get();
                            size1=result.size();
                            if(size1==0)
                            {
                                    Signup_details entity = addItemInTable(item);
                                     Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                     startActivity(i);
                            }
                            else
                            {
                                throw new Exception("Username already in use!!Please do register with new username.");
                               // Toast.makeText(getApplicationContext(),"Username already in use!!",Toast.LENGTH_LONG).show();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                }
                            });
                        }
                        catch (final Exception e)
                        {
                           createAndShowDialogFromTask(e, "Error");
                            //Toast.makeText(getApplicationContext(),"Username already in use!!",Toast.LENGTH_LONG).show();
                        }
                        return null;
                    }
                };
                runAsyncTask(task);
                uname.setText("");
                pass.setText("");
                cpass.setText("");
                contact.setText("");
                emailid.setText("");
    }
    public void alert()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(SignUp.this);
        builder1.setMessage("UserName already in use!!.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
    /**
     * Add an item to the Mobile Service Table
     *
     * @param item
     *            The item to Add
     */
    public Signup_details addItemInTable(Signup_details item) throws ExecutionException, InterruptedException {
        Signup_details entity = mToDoTable.insert(item).get();
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
    


}
