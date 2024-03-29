package com.example.login_volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingle {
    private static VolleySingle instance;
    private RequestQueue requestQueue;
    private static Context context;

    private VolleySingle(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleySingle getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingle(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}
