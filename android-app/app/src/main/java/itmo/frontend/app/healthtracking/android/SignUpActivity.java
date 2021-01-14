package itmo.frontend.app.healthtracking.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import okhttp3.Response;
import itmo.frontend.app.healthtracking.rest.controller.UsersBaseController;

public class SignUpActivity extends BaseActivity {
    private String tag = "Sign up activity";
    private UsersBaseController usersController;

    @Override
    protected String getTag() {
        return this.tag;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_signup;
    }

    @Override
    protected int getActivityTitle() {
        return R.string.app_name;
    }

    EditText email, password, confirmedPassword;
    Button submitButton;
    TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        confirmedPassword = findViewById(R.id.input_confirmPassword);
        submitButton = findViewById(R.id.btn_signup);
        loginLink = findViewById(R.id.link_login);

        Context ctx = getApplicationContext();
        usersController = new UsersBaseController(ctx);

        submitButton.setOnClickListener((View v) -> signUp());
        loginLink.setOnClickListener((View v) -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public void signUp() {
        if (!validate()) {
            onSignUpFailed("Fill all the fields");
            return;
        }
        submitButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String emailString = this.email.getText().toString();
        String passwordString = this.password.getText().toString();
        String confirmedPasswordString = confirmedPassword.getText().toString();

        ANRequest request = usersController.getSignUpRequest(emailString, passwordString);
        request.getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response.isSuccessful()) {
                            onSignUpSuccess();
                        } else {
                            onSignUpFailed("Sign up failed");
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        onSignUpFailed("Sign up failed");
                        progressDialog.dismiss();
                    }
                });
    }

    public void onSignUpSuccess() {
        Toast.makeText(getBaseContext(), "succeeded", Toast.LENGTH_LONG).show();
        submitButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignUpFailed(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        submitButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String emailText = this.email.getText().toString();
        String passwordText = this.password.getText().toString();
        String confirmedPasswordText = confirmedPassword.getText().toString();

//        if (emailText.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
        if (emailText.isEmpty()) {
            this.email.setError("username empty");
            valid = false;
        } else {
            this.email.setError(null);
        }

        if (passwordText.isEmpty() || passwordText.length() < 4) {
            this.password.setError("password should be longer than 4 char");
            valid = false;
        } else {
            this.password.setError(null);
        }

        if (confirmedPasswordText.isEmpty() || confirmedPasswordText.length() < 4 || !(confirmedPasswordText.equals(passwordText))) {
            confirmedPassword.setError("password not matched");
            valid = false;
        } else {
            confirmedPassword.setError(null);
        }

        return valid;
    }
}
