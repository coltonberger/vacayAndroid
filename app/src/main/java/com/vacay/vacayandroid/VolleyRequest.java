package com.vacay.vacayandroid;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class VolleyRequest {

    public static void makeGetRequest(Context context, String url, final RequestCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onResponse(response, null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onResponse(null, error);
            }
        });

        queue.add(request);
    }

    interface RequestCallback {
        public void onResponse(String response, VolleyError error);
    }
}
