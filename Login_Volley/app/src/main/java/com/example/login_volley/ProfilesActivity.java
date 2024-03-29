package com.example.login_volley;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

public class ProfilesActivity extends AppCompatActivity implements View.OnClickListener {
    TextView id, userName, userEmail, gender;
    Button btnLogout;
    ImageView imageViewpprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            id = findViewById (R.id.textViewId);
            userName = findViewById(R.id.textViewUsername);
            userEmail = findViewById(R.id.textViewEmail);
            gender = findViewById (R.id.textViewGender);
            btnLogout = findViewById (R.id.buttonLogout);
            imageViewpprofile = findViewById (R.id.imageViewProfile);
            User user = SharedPrefManager.getInstance(this).getUser();
            id.setText(String.valueOf(user.getId()));
            userEmail.setText(user.getEmail());
            gender.setText(user.getGender());
            userName.setText(user.getName());
            Glide.with(getApplicationContext())
                    .load(user.getImages()).into(imageViewpprofile);
            btnLogout.setOnClickListener(this);
        } else {
            Intent intent = new Intent(ProfilesActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void onClick(View view) {
        if(view.equals(btnLogout)){
            SharedPrefManager.getInstance (getApplicationContext()).logout();
        }
    }
}
