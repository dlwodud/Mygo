package com.example.dream.mygo;
import android.app.AlertDialog;
import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Course.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Course#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyHom extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyHom() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Course.
     */
    // TODO: Rename and change types and number of parameters
    public static MyHom newInstance(String param1, String param2) {
        MyHom fragment = new MyHom();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ArrayAdapter WorryAdapter;
    private Spinner WorrySpinner;
    private String courseWorry = "";
    private ListView courseListView;
    private CourseListAdapter adapter;
    private List<CourseNotice> courseList;


    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);
        WorrySpinner = (Spinner) getView().findViewById(R.id.WorrySpinner);
        WorryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.WorryR, android.R.layout.simple_spinner_dropdown_item);
        WorrySpinner.setAdapter(WorryAdapter);
        WorrySpinner.setSelection(0);

        courseListView = (ListView) getView().findViewById(R.id.courseListView);
        courseList = new ArrayList<CourseNotice>();
        adapter = new CourseListAdapter(MainActivity.mActivity.getApplicationContext(), courseList, this);

        courseListView.setAdapter(adapter);

        Button searchButton = (Button) getView().findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask().execute();
            }
        });

        new BackgroundTask().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_hom, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
            try {
                if (WorrySpinner.getSelectedItemPosition() == 0)
                    courseWorry = "*";
                else
                    courseWorry = WorrySpinner.getSelectedItem().toString();
                target = "http://dlwodud200.cafe24.com/UHom.php?userID="+URLEncoder.encode(UserID.getUserID(), "UTF-8")+"&courseWorry=" + URLEncoder.encode(courseWorry, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

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
                courseList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int courseID; //게시글 번호
                String userID; // 게시자
                String courseTitle;  // 제목
                String courseStroy;  // 내용
                String courseDate1; // 날짜
                int count = 0;

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseID = object.getInt("courseID");
                    userID = object.getString("userID");
                    courseTitle = object.getString("courseTitle");
                    courseStroy = object.getString("courseStroy");
                    courseDate1 = object.getString("courseDate1");
                    CourseNotice courseNotice = new CourseNotice(courseID, userID,  courseTitle, courseStroy, courseWorry, courseDate1);
                    courseList.add(courseNotice);
                    count++;
                }
                if (count == 0) {
                    AlertDialog dialog;
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MyHom.this.getActivity());
                    dialog = builder1.setMessage("조회된 게시글이 없습니다.")
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
