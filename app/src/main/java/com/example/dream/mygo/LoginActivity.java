package com.example.dream.mygo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends Activity {

    private AlertDialog dialog;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView registerButton = (TextView) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
        final EditText idText =(EditText) findViewById(R.id.idText);
        final EditText passwordText =(EditText) findViewById(R.id.passwordText);
        final Button loginButton =(Button) findViewById(R.id.loginButton);
        SharedPreferences pref = getSharedPreferences("idtext",MODE_PRIVATE);
        id=pref.getString("idtext", String.valueOf(false));
        idText.setText(id);


      //  HashMap<String, String> memoDate = mObjFileMgr.load();
     //   if(memoDate != null)
    //    {
     //       idText.setText(memoDate.get("IDText"));
    //    }
        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                final String userID =idText.getText().toString();
                final String userPassword = passwordText.getText().toString();
                final HashMap<String,String> memoDate = new HashMap<String, String>();

                Response.Listener<String>responseLister = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                SharedPreferences pref =getSharedPreferences("idtext",MODE_PRIVATE);
                                SharedPreferences.Editor editor =pref.edit();
                                editor.putString("idtext",userID);
                                editor.commit();
                              //  memoDate.put("IDtext",userID);
                             //   mObjFileMgr.save(memoDate);
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("로그인에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                UserID.setUserID(userID);
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                intent.putExtra("userID",userID);
                                LoginActivity.this.startActivity(intent);
                                finish();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("계정을 다시 확인하세요.")
                                        .setNegativeButton("다시 시도",null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest =new LoginRequest(userID, userPassword,responseLister);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

            }
        });
    }
    @Override
    protected void onStop(){
        super.onStop();
        if(dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }
}
