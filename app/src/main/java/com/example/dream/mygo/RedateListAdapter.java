package com.example.dream.mygo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Dream on 2017-04-16.
 */

public class RedateListAdapter extends BaseAdapter {

    private Context context;
    private int courseID;
    private String title;
    private List<RedateNotice> redateList;

    private Activity parent;


    public RedateListAdapter(Context context, List<RedateNotice> redateList, Activity parent, int courseID, String title) {
        this.context = context;
        this.redateList = redateList;
        this.parent = parent;
        this.courseID = courseID;
        this.title = title;
    }


    @Override
    public int getCount() {
        return redateList.size();
    }

    @Override
    public Object getItem(int i) {

        return redateList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, final View View, final ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.redatenotice, null);
        TextView redateID = (TextView) v.findViewById(R.id.redateID);
        TextView redateText = (TextView) v.findViewById(R.id.redateText);
        TextView redateDate = (TextView) v.findViewById(R.id.redateDate);


        redateText.setText(redateList.get(i).getRedateText());
        redateDate.setText(redateList.get(i).getDate());
        redateID.setText("NO." + redateList.get(i).getRedateID());

        v.setTag(redateList.get(i).getRedateID());

        Button editButton = (Button) v.findViewById(R.id.editButton);
        if (UserID.getUserID().equals(redateList.get(i).getUserID())) {
            editButton.setVisibility(android.view.View.VISIBLE);
        }
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(parent, ChangeRedate.class);
                intent.putExtra("redateID", redateList.get(i).getRedateID());
                intent.putExtra("redateText", redateList.get(i).getRedateText());
                intent.putExtra("courseID", courseID);
                intent.putExtra("title", title);
                parent.startActivity(intent);
                parent.finish();
            }
        });

        Button deleteButton = (Button) v.findViewById(R.id.deleteButton);
        if (UserID.getUserID().equals(redateList.get(i).getUserID())) {
            deleteButton.setVisibility(android.view.View.VISIBLE);
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            AlertDialog.Builder builder = new AlertDialog.Builder(parent);
                            if (success) {
                                builder.setTitle("삭제 확인 대화 상자")
                                        .setMessage("댓글 삭제에 성공했습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            // 취소 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.show();

                                // Refresh main activity upon close of dialog box
                                Intent refresh = new Intent(parent, Redate.class);
                                refresh.putExtra("courseID", courseID);
                                refresh.putExtra("title", title);
                                parent.startActivity(refresh);
                                parent.finish(); //

                            } else {
                                builder.setMessage("댓글 삭제에 실패했습니다.")
                                        .setPositiveButton("확인", null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(parent);
                builder.setTitle("삭제 확인 대화 상자")
                        .setMessage("해당 댓글을 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            // 확인 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DeleteRedateFragmentRequest deleteFragmentRequest = new DeleteRedateFragmentRequest(redateList.get(i).getRedateID(), UserID.getUserID(), responseListener);
                                RequestQueue queue = Volley.newRequestQueue(parent);
                                queue.add(deleteFragmentRequest);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            // 취소 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return v;

    }


}
