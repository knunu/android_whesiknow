package com.knunu.android.whesiknow;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @BindView(R.id.input_name) EditText nameText;
    @BindView(R.id.input_email) EditText emailText;
    @BindView(R.id.input_password) EditText passwordText;
    @BindView(R.id.input_confirm_password) EditText confirmPasswordText;
    @BindView(R.id.btn_signup) Button signupButton;
    @BindView(R.id.link_login) TextView loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signUp() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignUpFailed();
            return;
        }

        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("계정 생성중...");
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String name = nameText.getText().toString();

        // TODO: Implement your own signup logic here.
        Call<User> userObejctCall = RestfulAdapter.getInstance().postUser(email, Constant.APP, password, name);
        userObejctCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // if connection succeeded
                User user = response.body();
                Log.d(TAG, user.toString());
                onSignUpSuccess();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // if connection failed
                t.printStackTrace();
            }
        });
    }


    public void onSignUpSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignUpFailed() {
        Toast.makeText(getBaseContext(), "회원가입 실패", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String confirm_password = confirmPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("최소한 3글자는 되어야 합니다.");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("유효한 이메일을 입력해주세요.");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("4~10자 사이의 비밀번호를 입력해주세요.");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (!password.equals(confirm_password)) {
            confirmPasswordText.setError("확인용 비밀번호가 일치하지 않습니다.");
            valid = false;
        } else {
            confirmPasswordText.setError(null);
        }

        return valid;
    }
}