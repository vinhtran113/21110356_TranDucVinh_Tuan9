package com.example.login_volley;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPrefManager {


    private static final String SHARED_PREF_NAME = "volleyregisterlogin";

    private static final String KEY_USERNAME = "keyusername";

    private static final String KEY_EMAIL = "keyemail";

    private static final String KEY_GENDER = "keygender";

    private static final String KEY_ID = "keyid";

    private static final String KEY_IMAGES = "keyimages";

    private static SharedPrefManager mInstance;

    private static Context ctx;

//khởi tạo constructor

    private SharedPrefManager(Context context) {

        ctx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {

        if (mInstance == null) {

            mInstance = new SharedPrefManager(context);

        }

        return mInstance;

    }

    //phương thức này sẽ lưu trữ dữ liệu người dùng trong shared preferences
    public void userLogin(User user) {

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

//lưu trữ id người dùng
        editor.putInt(KEY_ID, user.getId());

//lưu trữ tên người dùng
        editor.putString(KEY_USERNAME, user.getName());

//lưu trữ email
        editor.putString(KEY_EMAIL, user.getEmail());

//lưu trữ giới tính
        editor.putString(KEY_GENDER, user.getGender());

//lưu trữ hình ảnh
        editor.putString(KEY_IMAGES, user.getImages());

//lưu trữ các thay đổi
        editor.apply();

    }

    //phương thức này sẽ kiểm tra xem người dùng đã đăng nhập hay chưa
    public boolean isLoggedIn() {

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

//trả về true nếu tên người dùng không null
        return sharedPreferences.getString(KEY_USERNAME, null) !=null;

    }

    //phương thức này sẽ lấy thông tin người dùng đã đăng nhập
    public User getUser() {

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

//trả về một User object với các thông tin được lưu trữ
        return new User(

                sharedPreferences.getInt(KEY_ID, -1),

                sharedPreferences.getString(KEY_USERNAME, null),

                sharedPreferences.getString(KEY_EMAIL, null),

                sharedPreferences.getString(KEY_GENDER, null),

                sharedPreferences.getString(KEY_IMAGES, null)

        );

    }
    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, LoginActivity.class));

    }

}
