package com.knunu.android.whesiknow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.kakao.auth.Session;
import com.kakao.auth.ISessionCallback;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final int REQUEST_LOGIN = 0;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private SessionCallback callback;
    private BackPressCloseHandler backPressCloseHandler;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private List<User> userObject;
    private User user;
    private String user_token;
    private String name;

    @BindView(R.id.input_email) EditText emailText;
    @BindView(R.id.input_password) EditText passwordText;
    @BindView(R.id.facebook_login_button) LoginButton fbLoginButton;
    @BindView(R.id.kakao_login_button) com.kakao.usermgmt.LoginButton kakaoLoginButton;
    @OnClick(R.id.login_button) void onLogin() {
        login();
    }
    @OnClick(R.id.link_signup) void onSignup() {
        // Start the signup activity
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(Constant.USER, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        backPressCloseHandler = new BackPressCloseHandler(this);

        loginWithFacebook();
        loginWithKakao();
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // kakao callback manager(session)
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);

        // facebook callback manager
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // app callback manager
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                Toast.makeText(LoginActivity.this, "회원 가입 되셨습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            Toast.makeText(getBaseContext(), "로그인 실패", Toast.LENGTH_LONG).show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("로그인 중");
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        Call<List<User>> userObjectCall = RestfulAdapter.getInstance().getUser(email, Constant.APP, password);
        userObjectCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                // if connection succeeded
                List<User> userObject = response.body();

                if (userObject == null) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "계정이 없거나 비밀번호가 틀렸습니다.", Toast.LENGTH_LONG).show();
                    return;
                }

                // json parsing
                editor.putInt("id", userObject.get(0).getId());
                editor.putString("name", userObject.get(0).getName());
                editor.putString("email", userObject.get(0).getEmail());
                editor.putString("login_group", Constant.APP);
                editor.commit();
                onLoginSuccess();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // if connection failed
                t.printStackTrace();
            }
        });
    }

    private void onLoginSuccess() {
        // Start the main activity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("유효한 이메일 주소를 입력해주세요.");
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

        return valid;
    }

    /** facebook sign up, login part **/
    private void loginWithFacebook() {
        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d(TAG, "Current Token : " + currentAccessToken);
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Log.d(TAG, "Current Profile : " + currentProfile);
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        fbLoginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        if (response.getError() != null) {
                            // error handling part
                        } else {
                            try {
                                name = response.getJSONObject().get("name").toString();
                                user_token = null;
                                if (response.getJSONObject().isNull("email")) {
                                    user_token = response.getJSONObject().get("id").toString();
                                } else {
                                    user_token = response.getJSONObject().get("email").toString();
                                }

                                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                                        R.style.AppTheme_Dark_Dialog);
                                progressDialog.setIndeterminate(true);
                                progressDialog.setMessage("로그인 중");
                                progressDialog.show();

                                // TODO: Implement your own authentication logic here.
                                Call<List<User>> getUserObjectCall = RestfulAdapter.getInstance().getUser(user_token, Constant.FACEBOOK, null);
                                getUserObjectCall.enqueue(new Callback<List<User>>() {
                                    @Override
                                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                                        // if connection succeeded
                                        userObject = response.body();

                                        if (userObject == null) {
                                            // TODO: Implement your own signup logic here.
                                            Call<User> postUserObjectCall = RestfulAdapter.getInstance().postUser(user_token, Constant.FACEBOOK, null, name);
                                            postUserObjectCall.enqueue(new Callback<User>() {
                                                @Override
                                                // if facebook user is not signed up yet
                                                public void onResponse(Call<User> call, Response<User> response) {
                                                    // if connection succeeded
                                                    user = response.body();

                                                    // json parsing
                                                    editor.putInt("id", user.getId());
                                                    editor.putString("name", name);
                                                    /** IT WILL BE FIXED LATER **/
                                                    editor.putString("token", user_token);
                                                    editor.putString("login_group", Constant.FACEBOOK);
                                                    editor.commit();
                                                    onLoginSuccess();
                                                    progressDialog.dismiss();
                                                }

                                                @Override
                                                public void onFailure(Call<User> call, Throwable t) {
                                                    // if connection failed
                                                    t.printStackTrace();
                                                    Toast.makeText(LoginActivity.this, "DB 연결에 실패 했습니다.", Toast.LENGTH_SHORT).show();
                                                    onBackPressed();
                                                }
                                            });
                                        } else {
                                            // json parsing
                                            editor.putInt("id", userObject.get(0).getId());
                                            editor.putString("name", name);
                                            /** IT WILL BE FIXED LATER **/
                                            editor.putString("token", user_token);
                                            editor.putString("login_group", Constant.FACEBOOK);
                                            editor.commit();
                                            onLoginSuccess();
                                            progressDialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<User>> call, Throwable t) {
                                        // if connection failed
                                        t.printStackTrace();
                                        Toast.makeText(LoginActivity.this, "사용자 정보를 가지고 오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    /** kakao sign up, login part **/
    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            redirectKakaoSignupActivity();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }

            setContentView(R.layout.activity_login);
        }
    }

    private void loginWithKakao() {
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
    }

    private void redirectKakaoSignupActivity() {
        final Intent intent = new Intent(this, KakaoSignupActivity.class);
        startActivity(intent);
        finish();
    }
}
