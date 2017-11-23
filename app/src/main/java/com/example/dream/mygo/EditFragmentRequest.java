package com.example.dream.mygo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dream on 2017-11-11.
 */

public class EditFragmentRequest extends StringRequest {
    final static private String URL = "http://dlwodud200.cafe24.com/UEditFragment.php";
    private Map<String, String> parameters;

    public EditFragmentRequest(String userID, String courseTitle, String courseStroy, String targetGender, String targetAge, String courseWorry, int timeToDelete, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", UserID.getUserID());
        parameters.put("courseTitle", courseTitle);
        parameters.put("courseStroy", courseStroy);
        parameters.put("targetGender", targetGender);
        parameters.put("targetAge", targetAge);
        parameters.put("courseWorry", courseWorry);
        parameters.put("timeToDelete", ""+timeToDelete+":00:00");

    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

}