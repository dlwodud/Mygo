package com.example.dream.mygo;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dream on 2017-11-14.
 */

public class PassRequest extends StringRequest {
    final static private String URL = "http://dlwodud200.cafe24.com/Passdate.php";
    private Map<String, String> parameters;

    public PassRequest(String coursePwd, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("coursePwd", coursePwd);

    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
