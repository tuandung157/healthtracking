package itmo.frontend.app.healthtracking.rest.controller;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import itmo.frontend.app.healthtracking.android.R;
import itmo.frontend.app.healthtracking.util.PropertiesManager;

public class BaseController {

    protected Context context;
    protected PropertiesManager propertiesManager;

    public BaseController(Context ctx) {
        context = ctx;
        propertiesManager = new PropertiesManager(ctx);
    }


    protected ANRequest.PostRequestBuilder getPostRequest(String resourcePath) {
        String requestPath = context.getString(R.string.api_root_url) +
                resourcePath;
        return AndroidNetworking.post(requestPath)
                .setContentType("application/json");
    }

    protected ANRequest.GetRequestBuilder getGetRequest(String resourcePath) {
        String requestPath = context.getString(R.string.api_root_url) +
                resourcePath;
        return AndroidNetworking.get(requestPath);

    }

    protected ANRequest.PostRequestBuilder getPostRequestWithAuth(String resourcePath) {
        String token = propertiesManager.getToken();

        return getPostRequest(resourcePath)
                .addHeaders("Authorization", "Basic " + token);

    }

    protected ANRequest.GetRequestBuilder getGetRequestWithAuth(String resourcePath) {
        String token = propertiesManager.getToken();

        return getGetRequest(resourcePath)
                .addHeaders("Authorization", "Basic " + token);

    }

}