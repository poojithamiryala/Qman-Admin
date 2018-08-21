package com.example.poojithamiryala.admin_qma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class inputDialog extends AppCompatActivity {
    EditText serv;
    EditText serv_desc;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_dialog);
        serv=(EditText)findViewById(R.id.service);
        serv_desc=(EditText)findViewById(R.id.servicedesc);
        b=(Button)findViewById(R.id.submit);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),AddServices.class);
                Bundle b=new Bundle();
                b.putString("service",serv.getText().toString());
                b.putString("servicedesc",serv_desc.getText().toString());
                i.putExtras(b);
                startActivity(i);
            }
        });
    }
}
