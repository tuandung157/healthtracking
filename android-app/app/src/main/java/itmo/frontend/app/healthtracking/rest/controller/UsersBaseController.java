package itmo.frontend.app.healthtracking.rest.controller;

import android.content.Context;

import com.androidnetworking.common.ANRequest;

import org.json.JSONException;
import org.json.JSONObject;

// api/users
public class UsersBaseController extends BaseController {

    public UsersBaseController(Context ctx) {
        super(ctx);
    }
    //POST
    public ANRequest getLoginRequest(String username,String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",username);
            jsonObject.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String path = "/users/login";
//        return getGetRequestWithAuth(path)
        return getPostRequestWithAuth(path)
                .addJSONObjectBody(jsonObject)
                .build();
    }


    // POST users
    public ANRequest getSignUpRequest(String username, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",username);
            jsonObject.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getPostRequest("users/add")
                .addJSONObjectBody(jsonObject)
                .build();
    }

}
