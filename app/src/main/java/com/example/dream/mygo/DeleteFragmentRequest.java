package com.example.dream.mygo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dream on 2017-11-11.
 */

public class DeleteFragmentRequest extends StringRequest {
    final static private String URL ="http://dlwodud200.cafe24.com/UDeleteFragment.php";
    private Map<String, String> parameters;

    public DeleteFragmentRequest(int courseID, String userID, Response.Listener<String> listener){
        super(Method.POST ,URL , listener, null);
        parameters = new HashMap<>();
        parameters.put("courseID", Integer.toString(courseID));
        parameters.put("userID",UserID.getUserID());
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
