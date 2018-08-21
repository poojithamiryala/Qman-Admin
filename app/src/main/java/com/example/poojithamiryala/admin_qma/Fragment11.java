package com.example.poojithamiryala.admin_qma;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.example.poojithamiryala.admin_qma.Azure.madmin;
import static com.example.poojithamiryala.admin_qma.Azure.mbook;

public class Fragment11 extends AppCompatActivity {
    Button b1;
    Button b2;
    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    List<BookUser> result;
    AdminQ admin;
    int num = 0;
    String service1;
    String orgname1;
    TextView time;
    long start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment11);

        t1=(TextView)findViewById(R.id.name);
        t2=(TextView)findViewById(R.id.service);
        t3=(TextView)findViewById(R.id.date);
        t4=(TextView)findViewById(R.id.left);
        b1=(Button)findViewById(R.id.next);
        b2=(Button) findViewById(R.id.updateq);
        time=(TextView)findViewById(R.id.textView);
        if (getIntent().getExtras() != null)
        {
            Bundle b1=getIntent().getExtras();
            service1=b1.getString("service");
            t1.setText(b1.getString("name"));
            t2.setText(b1.getString("service"));
        }
        getData1();
        getData();
        start = java.util.Calendar.getInstance().getTime().getTime();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start = updateTime(start);
                updateItem(view);
               //getData();
            }
        });

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        t3.setText(dateFormat.format(date));
        b2.setText(Integer.toString(num));
    }
    public long updateTime(long start) {
        long stop = java.util.Calendar.getInstance().getTime().getTime();
        final long dur = stop - start;
        start = java.util.Calendar.getInstance().getTime().getTime();

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    admin.setTime((admin.getTime() + dur) / (2));
                    final AdminQ updated =  UpdateItemInTable(admin);
                } catch (final Exception e) {
                    //createAndShowDialogFromTask(e, "Error");
                }
                return null;
            }
        };
        runAsyncTask(task);
        return start;
    }
    public void updateItem(View v) {
        final String orgname = t1.getText().toString();
        final String service_name = t2.getText().toString();
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final List<BookUser> result1 =
                            mbook.where()
                                    .field("orgname").eq(orgname).
                                    and()
                                    .field("service").eq(service_name)
                                    .orderBy("timestamp", QueryOrder.Ascending)
                                    .execute().get();
                    final List<AdminQ> result =
                            madmin.where().field("username").eq(Main_1.username1).
                                    and().field("service").eq(service_name).
                                    and().field("orgname").eq(orgname).execute().get();
                    admin.setAdminQ(admin.getAdminQ()+1);
                    final AdminQ updated =  UpdateItemInTable(admin);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            t4.setText(String.valueOf(result1.size()-updated.getAdminQ()));
                            time.setText("Time-"+(convertDate(String.valueOf(Calendar.getInstance().getTime().getTime()+(admin.getTime()*(result1.size()-updated.getAdminQ()))),"hh:mm:ss")));
                            b2.setText(""+updated.getAdminQ());
                        }
                    });
                } catch (final Exception e) {
                    //createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };
        runAsyncTask(task);
    }

    public AdminQ UpdateItemInTable(AdminQ item) throws ExecutionException, InterruptedException {
        return Azure.madmin.update(item).get();
    }

    public void getData1() {
        final int[] number = new int[1];
        number[0] = 9;
        final String orgname = t1.getText().toString();
        final String name = t1.getText().toString();
        final String service_name = t2.getText().toString();
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final List<AdminQ> result =
                            madmin.where().field("username").eq(Main_1.username1).
                            and().field("service").eq(service_name).
                            and().field("orgname").eq(name).execute().get();
                    final List<BookUser> result1 =
                            mbook.where()
                                    .field("orgname").eq(orgname).
                                    and()
                                    .field("service").eq(service_name)
                                    .orderBy("timestamp", QueryOrder.Ascending)
                                    .execute().get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            admin = result.get(0);
                            b2.setText(""+admin.getAdminQ());
                            time.setText("Time-"+(convertDate(String.valueOf(Calendar.getInstance().getTime().getTime()+(admin.getTime()*(result1.size()-admin.getAdminQ()))),"hh:mm:ss")));
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
    public static String convertDate(String dateInMilliseconds,String dateFormat) {
        return android.text.format.DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }
    public void getData() {
        final String orgname = t1.getText().toString();
        final String service_name = t2.getText().toString();
        final String name = t1.getText().toString();
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final List<AdminQ> result1 =
                            madmin.where().field("username").eq(Main_1.username1).
                                    and().field("service").eq(service_name).
                                    and().field("orgname").eq(name).execute().get();
                    result =
                            mbook.where()
                                    .field("orgname").eq(orgname).
                                    and()
                                    .field("service").eq(service_name)
                                    .orderBy("timestamp", QueryOrder.Ascending)
                                    .execute().get();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            t4.setText(""+(result.size()-result1.get(0).getAdminQ()));
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

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if(exception.getCause() != null){
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

    private void createAndShowDialogFromTask(final Exception exception, String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createAndShowDialog(exception, "Error");
            }
        });
    }

    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}
