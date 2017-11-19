package com.example.dream.mygo;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

import static android.app.PendingIntent.getActivity;

/**
 * Created by Dream on 2017-04-16.
 */

public class CourseListAdapter extends BaseAdapter{

    private Context context;
    private List<CourseNotice> coursesList;

    private Fragment parent;
    private ListView courseListView;
    private ListView _listview;
    private String[] items = { "삭제", "수정", "댓글",};



    public CourseListAdapter(Context context, List<CourseNotice> coursesList, Fragment parent) {
        this.context = context;
        this.coursesList = coursesList;
        this.parent =parent;
    }


    @Override
    public int getCount() {
        return coursesList.size();
    }

    @Override
    public Object getItem(int i) {

        return coursesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, final View View, final ViewGroup viewGroup) {
        View v =View.inflate(context,R.layout.coursenotice,null);
        TextView courseID = (TextView) v.findViewById(R.id.courseID);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseStroy = (TextView) v.findViewById(R.id.courseStroy);
        TextView courseDate1 = (TextView) v.findViewById(R.id.courseDate1);



        courseTitle.setText(coursesList.get(i).getCourseTitle());
        courseStroy.setText(coursesList.get(i).getCourseStroy());
        courseDate1.setText(coursesList.get(i).getCourseDate1());
        courseID.setText("NO."+coursesList.get(i).getCourseID());

        v.setTag(coursesList.get(i).getCourseID());

        Button editButton =(Button)v.findViewById(R.id.editButton);
        if(UserID.getUserID().equals(coursesList.get(i).getUserID())) {
            editButton.setVisibility(android.view.View.VISIBLE);
        }
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View view){
                Intent intent = new Intent(MainActivity.mActivity, Change.class);
                intent.putExtra("courseID", coursesList.get(i).getCourseID());
                intent.putExtra("title", coursesList.get(i).getCourseTitle());
                intent.putExtra("stroy", coursesList.get(i).getCourseStroy());
                intent.putExtra("worry", coursesList.get(i).getCourseWorry());
                MainActivity.mActivity.startActivity(intent);
            }
        });

        Button deleteButton =(Button)v.findViewById(R.id.deleteButton);
        if(UserID.getUserID().equals(coursesList.get(i).getUserID())) {
            deleteButton.setVisibility(android.view.View.VISIBLE);
        }

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View view){
                final Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.mActivity);
                            if (success) {
                                builder.setTitle("삭제 확인 대화 상자")
                                        .setMessage("Gom민 삭제에 성공했습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                                            // 취소 버튼 클릭시 설정
                                            public void onClick(DialogInterface dialog, int whichButton){
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.show();

                                FragmentManager fragmentManager = MainActivity.mActivity.getFragmentManager();
                                FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                                fragmentTransaction.detach(parent).attach(parent);
                                fragmentTransaction.commit();
                            } else {
                                builder.setMessage("Gom민 삭제에 실패했습니다.")
                                        .setPositiveButton("확인", null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.mActivity);
                builder.setTitle("삭제 확인 대화 상자")
                        .setMessage("해당 Gom민을 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener(){
                            // 확인 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton){
                                DeleteFragmentRequest deleteFragmentRequest = new DeleteFragmentRequest(coursesList.get(i).getCourseID(), UserID.getUserID(), responseListener);
                                RequestQueue queue = Volley.newRequestQueue(MainActivity.mActivity);
                                queue.add(deleteFragmentRequest);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                            // 취소 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton){
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
