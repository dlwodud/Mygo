package com.example.dream.mygo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dream on 2017-10-15.
 */

public class EditRequest extends StringRequest {

    final static private String URL = "http://dlwodud200.cafe24.com/CourseEdit.php";
    private Map<String, String> parameters;

    public EditRequest(String courseID, String userID, String courseTitle, String courseStroy, String courseWorry, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("courseID", courseID);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
