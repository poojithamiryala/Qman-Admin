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

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.poojithamiryala.admin_qma.Azure.mClient;
import static com.example.poojithamiryala.admin_qma.Azure.mToDoTable;
import static com.example.poojithamiryala.admin_qma.R.id.pass;

public class AddContact extends AppCompatActivity {
    EditText uname;
    EditText pwd;
    EditText contact;
    EditText email;
    EditText cpwd;
    Button sub;
    String password;
    String cpassword;
    String uname1;
    int size1;
    List<Signup_details> result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        uname=(EditText)findViewById(R.id.un);
        pwd=(EditText)findViewById(pass);
        cpwd=(EditText)findViewById(R.id.cpass);
        contact=(EditText)findViewById(R.id.contact);
        email=(EditText)findViewById(R.id.email);
        sub=(Button)findViewById(R.id.btn_signup);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                password = pwd.getText().toString();
                cpassword=cpwd.getText().toString();
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
        final String user1=uname.getText().toString();
        final String pwd1=pwd.getText().toString();
        final String cpwd1=cpwd.getText().toString();
        final String email1=email.getText().toString();
        final String contact1=contact.getText().toString();

        // Insert the new item
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    result = mToDoTable.where().field("username").eq(user1).execute().get();
                    size1=result.size();
                    if(size1==0)
                    {
                        Intent i=new Intent(getApplicationContext(),AddOrganization.class);
                        Bundle b=new Bundle();
                        b.putString("uname",user1);
                        b.putString("pwd",pwd1);
                        b.putString("email",email1);
                        b.putString("contact",contact1);
                        i.putExtras(b);
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
        pwd.setText("");
        cpwd.setText("");
        contact.setText("");
        email.setText("");
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
