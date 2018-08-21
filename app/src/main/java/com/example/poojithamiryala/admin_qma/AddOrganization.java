package com.example.poojithamiryala.admin_qma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddOrganization extends AppCompatActivity {
    Button b1;
    Spinner s1;
    EditText e1;
    EditText e2;
    EditText e3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_organization2);
        b1=(Button)findViewById(R.id.submit);
        s1=(Spinner)findViewById(R.id.category);
        e1=(EditText)findViewById(R.id.nameorg);
        e2=(EditText)findViewById(R.id.city);
        e3=(EditText)findViewById(R.id.branch);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b=getIntent().getExtras();
                b.putString("category",s1.getSelectedItem().toString());
                b.putString("name",e1.getText().toString());
                b.putString("city",e2.getText().toString());
                b.putString("branch",e3.getText().toString());
                Intent i=new Intent(getApplicationContext(),AddServices.class);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }
}
