package com.example.dream.mygo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dream on 2017-10-15.
 */

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://dlwodud200.cafe24.com/UserRegister.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userGender, String userWorry, String userEmail, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userGender", userGender);
        parameters.put("userWorry", userWorry);
        parameters.put("userEmail", userEmail);

    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
