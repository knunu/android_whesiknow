package com.knunu.android.whesiknow;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;

import com.kakao.util.helper.SharedPreferencesCache;
import com.kakao.util.helper.log.Logger;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KakaoSignupActivity extends Activity {
    /**
     * Main으로 넘길지 가입 페이지를 그릴지 판단하기 위해 me를 호출한다.
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private List<User> userObject;
    private User user;
    private String user_token;
    private String name;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(Constant.USER, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        requestMe();
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void requestMe() { //유저의 정보를 받아오는 함수
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "Failed to get user info. msg=" + errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {} // 카카오톡 회원이 아닐 시 showSignup(); 호출해야함

            @Override
            public void onSuccess(UserProfile userProfile) {  //성공 시 userProfile 형태로 반환
                user_token = Long.toString(userProfile.getId());
                name = userProfile.getNickname();

                // TODO: Implement your own authentication logic here.
                Call<List<User>> getUserObjectCall = RestfulAdapter.getInstance().getUser(user_token, Constant.KAKAO, null);
                // enqueue를 호출하게 되면서 이 부분은 Asynchronous하게 작동된다!
                getUserObjectCall.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        // if connection succeeded
                        userObject = response.body();

                        if (userObject == null) {
                            // TODO: Implement your own signup logic here.
                            // RESTful API중 user를 입력하기 위해 post method를 사용하는 부분, retrofit2를 이용해서 구현했기 때문에 학습 필요
                            Call<User> postUserObjectCall = RestfulAdapter.getInstance().postUser(user_token, Constant.KAKAO, null, name);
                            postUserObjectCall.enqueue(new Callback<User>() {
                                @Override
                                // if kakao user is not signed up yet, do sign up.
                                public void onResponse(Call<User> call, Response<User> response) {
                                    // if connection succeeded
                                    user = response.body();

                                    // json parsing
                                    editor.putInt("id", user.getId());
                                    editor.putString("name", name);
                                    /** IT WILL BE FIXED LATER **/
                                    editor.putString("token", user_token);
                                    editor.putString("login_group", Constant.KAKAO);
                                    editor.commit();
                                    redirectMainActivity(); // (DB 삽입) DB 에서 id값 추출 및 로그인 성공시 MainActivity로 redirect
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    // if connection failed
                                    t.printStackTrace();
                                    Toast.makeText(KakaoSignupActivity.this, "DB 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                            });
                        } else {
                            // json parsing
                            editor.putInt("id", userObject.get(0).getId());
                            editor.putString("name", name);
                            /** IT WILL BE FIXED LATER **/
                            editor.putString("token", user_token);
                            editor.putString("login_group", Constant.KAKAO);
                            editor.commit();
                            redirectMainActivity(); // (DB 삽입) DB 에서 id값 추출 및 로그인 성공시 MainActivity로 redirect
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        // if connection failed
                        t.printStackTrace();
                        Toast.makeText(KakaoSignupActivity.this, "사용자 정보를 가지고 오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void redirectMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    protected void redirectLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}