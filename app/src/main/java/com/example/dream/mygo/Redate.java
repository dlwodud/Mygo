package com.example.dream.mygo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dream on 2017-11-19.
 */


public class Redate extends Activity {
    private RedateListAdapter adapter;
    private ListView redateListView;
    private List<RedateNotice> redateList;
    private int courseID;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redate);

        courseID = getIntent().getIntExtra("courseID", -1);
        if (courseID == -1) {
            finish();
        }

        final TextView titleText = (TextView) findViewById(R.id.titleText);
        final String title = getIntent().getStringExtra("title");
        titleText.setText(title.toCharArray(), 0, title.length());

        redateListView = (ListView) findViewById(R.id.redateText);
        redateList = new ArrayList<RedateNotice>();
        adapter = new RedateListAdapter(this.getApplicationContext(), redateList, this, courseID, title);
        redateListView.setAdapter(adapter);

        final EditText Retitle = (EditText) findViewById(R.id.retitleText);

        final Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  notice.setVisibility(View.GONE);
                finish();
            }
        });

        Button okButton = (Button) findViewById(R.id.okButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = Retitle.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Redate.this);
                                dialog = builder.setMessage("덧글 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        Intent registerIntent = new Intent(Redate.this, Redate.class);
                                        registerIntent.putExtra("courseID",courseID);
                                        registerIntent.putExtra("title", title);
                                        startActivity(registerIntent);
                                        finish();
                                    }
                                });
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(com.example.dream.mygo.Redate.this);
                                dialog = builder.setMessage("덧글 등록에 실패했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                RedateFragmentRequest redateFragmentRequest = new RedateFragmentRequest(courseID, UserID.getUserID(), text, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Redate.this);
                queue.add(redateFragmentRequest);
            }
        });

        new BackgroundTask().execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuffer stringBuilder = new StringBuffer();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            target = "http://dlwodud200.cafe24.com/RedateList.php?courseID=" + courseID;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }


        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            try {
                redateList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int redateID; //댓글 번호
                String userID; // 작성자
                String redateText;  // 내용
                String date; // 날짜
                int count = 0;

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    redateID = object.getInt("redateID");
                    userID = object.getString("userID");
                    redateText = object.getString("redateText");
                    date = object.getString("date");
                    RedateNotice courseNotice = new RedateNotice(redateID, userID, redateText, date);
                    redateList.add(courseNotice);
                    count++;
                }
                if (count == 0) {
                    AlertDialog dialog;
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Redate.this);
                    dialog = builder1.setMessage("조회된 댓글이 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

