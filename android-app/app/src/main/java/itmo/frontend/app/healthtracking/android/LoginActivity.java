package itmo.frontend.app.healthtracking.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import itmo.frontend.app.healthtracking.rest.controller.UsersBaseController;
import itmo.frontend.app.healthtracking.util.PropertiesManager;

public class LoginActivity extends BaseActivity {
    private final String tag = "Main activity";
    private UsersBaseController usersController;
    private PropertiesManager propertiesManager;

    @Override
    protected String getTag() {
        return this.tag;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected int getActivityTitle() {
        return R.string.app_name;
    }

    Button submitButton;
    EditText email, password;
    TextView signUpLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        submitButton = findViewById(R.id.btn_login);
        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        signUpLink = findViewById(R.id.link_signup);

        submitButton.setOnClickListener((View v) -> login());
        signUpLink.setOnClickListener((View v) -> {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        Context ctx = getApplicationContext();
        usersController = new UsersBaseController(ctx);
        propertiesManager = new PropertiesManager(ctx);

        if (propertiesManager.hasValidToken()) {
            propertiesManager.removeToken();
        }
    }

    public void login() {

//        set valid
        if (!validate()) {
            onLoginFailed();
            return;
        }
        submitButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();

        propertiesManager.saveToken(emailText, passwordText, System.currentTimeMillis() + PropertiesManager.DefaultExpiration);
        System.out.println("check run");
//        usersController.getLoginRequest(emailText)
//                .getAsOkHttpResponse(new OkHttpResponseListener() {
//            @Override
//            public void onResponse(Response response) {
//                System.out.println(" check response user " +response);
//                if (!response.isSuccessful()) {
//                    onLoginFailed();
//                }
//                else {
//                    onLoginSuccess();
//                }
//                progressDialog.dismiss();
//            }
//            @Override
//            public void onError(ANError error) {
//                onLoginFailed();
//                progressDialog.dismiss();
//            }
//        });
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("usersName",emailText);
//            jsonObject.put("password",passwordText);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        usersController.getLoginRequest(emailText,passwordText)
//        AndroidNetworking.post("http://10.0.2.2:8080/users/login")
//                .addJSONObjectBody(jsonObject)
//                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("show response" + response.toString());
                        onLoginSuccess();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println(" if failed");
                        onLoginFailed();
                        progressDialog.dismiss();
                    }
                });
    }

    public void onLoginSuccess() {
        submitButton.setEnabled(true);

        if (propertiesManager.hasValidToken()) {
            Toast.makeText(getBaseContext(), "Login succeed", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    public void onLoginFailed() {
        propertiesManager.removeToken();
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        submitButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();

        /*if (emailText.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }*/

        if (passwordText.isEmpty() || passwordText.length() < 4 || passwordText.length() > 10) {
            password.setError("Password should be between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }
}
