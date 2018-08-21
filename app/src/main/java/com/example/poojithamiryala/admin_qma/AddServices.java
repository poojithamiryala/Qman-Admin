package com.example.poojithamiryala.admin_qma;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.poojithamiryala.admin_qma.Azure.mToDoTable;
import static com.example.poojithamiryala.admin_qma.Main_1.SHAREDPREFFILE;
import static com.example.poojithamiryala.admin_qma.Main_1.USERIDPREF;

public class AddServices extends AppCompatActivity {
    Button plus1;
    ListView lv;
    Button b1;
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();
    Signup_details item;
    int start;
    Organization_details item1;
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services);
        plus1=(Button)findViewById(R.id.plus);
        b1=(Button)findViewById(R.id.button);
        lv=(ListView)findViewById(R.id.listView);
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        lv.setAdapter(adapter);
        final Bundle b=getIntent().getExtras();
        if(b.getString("uname")!=null)
        {
            start=1;
        }
        else
            start=2;
        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if(start==1)
                {
                    Intent i=new Intent(getApplicationContext(),inputDialog.class);
                }
                else
                {
                    String sname=b.getString("service");
                    String sdesc=b.getString("servicedesc");
                    listItems.add(sname+"("+sdesc+")");
                    adapter.notifyDataSetChanged();
                }*/
               onCreateDialog();
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddServices.this);
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("Are you sure want to delete?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //String pos=key.get(i).toString();
                        listItems.remove(i);
                        adapter.notifyDataSetChanged();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.show();
                return true;
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });
    }
    public void onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddServices.this);
        // Get the layout inflater
        View v=getLayoutInflater().inflate(R.layout.activity_input_dialog,null);
        builder.setView(v);
        final AlertDialog dialog=builder.create();
        dialog.show();
        final EditText serv=(EditText)v.findViewById(R.id.service);
        final EditText serv_desc=(EditText)v.findViewById(R.id.servicedesc);
        Button b=(Button)v.findViewById(R.id.submit);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItems.add(serv.getText().toString()+"("+serv_desc.getText().toString()+")");
                adapter.notifyDataSetChanged();
                dialog.cancel();
            }
        });

    }
    public void addItem(View v)
    {
        final Bundle b=getIntent().getExtras();
        item = new Signup_details();
        item.setUsername(b.getString("uname"));
        item.setContactnumber(b.getString("contact"));
        item.setEmail(b.getString("email"));
        item.setPassword(b.getString("pwd"));
        item1=new Organization_details();
        item1.setCategory(b.getString("category"));
        item1.setCity(b.getString("city"));
        item1.setBranches(b.getString("branch"));
        item1.setUsername(b.getString("uname"));
        item1.setName(b.getString("name"));
        item1.setServices(android.text.TextUtils.join(",",listItems));
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Signup_details entity = addItemInTable(item);
                    Organization_details entity1 = addItemInTable1(item1);
                    for(String i: listItems)
                    {
                        final AdminQ item3=new AdminQ();
                        item3.setUsername(b.getString("uname"));
                        item3.setOrgname(b.getString("name"));
                        item3.setServicename(i);
                        item3.setAdminQ(0);
                        item3.setTime(120000);
                        addItemInTable2(item3);
                    }
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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
    public Signup_details addItemInTable(Signup_details item) throws ExecutionException, InterruptedException {
        Signup_details entity = mToDoTable.insert(item).get();
        return entity;
    }
    public Organization_details addItemInTable1(Organization_details item) throws ExecutionException, InterruptedException {
        Organization_details entity = Azure.mOrgan.insert(item).get();
        return entity;
    }
    public AdminQ addItemInTable2(AdminQ item) throws ExecutionException, InterruptedException
    {
        AdminQ entity = Azure.madmin.insert(item).get();
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
