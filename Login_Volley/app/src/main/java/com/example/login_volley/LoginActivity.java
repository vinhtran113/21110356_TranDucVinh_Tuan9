package com.example.login_volley;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import static android.text.TextUtils.*;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText name, pass;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        progressBar = findViewById(R.id.progressBar);

        name = findViewById(R.id.etUsername);
        pass = findViewById(R.id.etUserPassword);

        //calling the method userLogin() for login the user
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                userLogin();
            }

        });

        //if user presses on textview not register calling RegisterActivity
        findViewById(R.id.txtRegister).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }

        });
    }

    private void userLogin() {

        // Lấy giá trị
        final String username = name.getText().toString();
        final String password = pass.getText().toString();

        // Xác thực đầu vào
        if (isEmpty(username)) {
            name.setError("Vui lòng nhập tên người dùng");
            name.requestFocus();
            return;
        }

        if (isEmpty(password)) {
            pass.setError("Vui lòng nhập mật khẩu");
            pass.requestFocus();
            return;
        }

        // Nếu mọi thứ đều ổn

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            // Ẩn thanh tiến trình

                            // Chuyển đổi phản hồi sang đối tượng JSON
                            JSONObject obj = new JSONObject(response);

                            // Nếu không có lỗi trong phản hồi
                            if (!obj.getBoolean("error")) {
                                // Hiển thị thông báo
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                // Lấy thông tin người dùng từ phản hồi
                                JSONObject userJson = obj.getJSONObject("user");
                                // Tạo một đối tượng người dùng mới

                                User user = new User(

                                        // Lấy id từ json
                                        userJson.getInt("id"),

                                        // Lấy username từ json
                                        userJson.getString("username"),

                                        // Lấy email từ json
                                        userJson.getString("email"),

                                        // Lấy giới tính từ json
                                        userJson.getString("gender"),

                                        // Lấy ảnh từ json
                                        userJson.getString("images")

                                );

// Lưu trữ người dùng trong shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

// Bắt đầu activity profile
                                finish();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);

                            } else {

                                // Hiển thị thông báo lỗi
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {

                            // In ra lỗi
                            e.printStackTrace();

                        }
                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // Hiển thị thông báo lỗi
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("username", username);

                params.put("password", password);

                return params;

            }

        };

        // Thêm request vào request queue
        VolleySingle.getInstance(this).addToRequestQueue(stringRequest);

    }

}
