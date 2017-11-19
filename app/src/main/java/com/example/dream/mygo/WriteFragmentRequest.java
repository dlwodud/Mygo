package com.example.dream.mygo;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dream on 2017-11-11.
 */

public class WriteFragmentRequest extends StringRequest {
    final static private String URL ="http://dlwodud200.cafe24.com/UWriteFragment.php";
    private Map<String, String> parameters;

    public WriteFragmentRequest(String userID, String courseTitle, String courseStroy, String courseWorry, Response.Listener<String> listener){
        super(Method.POST ,URL , listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",UserID.getUserID());
        parameters.put("courseTitle",courseTitle);
        parameters.put("courseStroy",courseStroy);
        parameters.put("courseWorry",courseWorry);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
