package com.tr.nearfood.utills;



import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tr.nearfood.utills.SampleApplication;

public class HttpRequestUsingVolley {
	
	// These tags will be used to cancel the requests
		private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
		String TAG="Making http request";
		private ProgressDialog pDialog;
		 JSONArray jsonArray= new JSONArray();
	public HttpRequestUsingVolley(Context context) {
		pDialog = new ProgressDialog(context);
		pDialog.setMessage("Loading...");
		pDialog.setCancelable(false);

	}
	private void showProgressDialog() {
		if (!pDialog.isShowing())
			pDialog.show();
	}

	private void hideProgressDialog() {
		if (pDialog.isShowing())
			pDialog.hide();
	}

	/**
	 * Making json object request
	 * */
	private void makeJsonObjReq(String URL,	final Map<String, String> params) {
		showProgressDialog();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				URL, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, response.toString());
						hideProgressDialog();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						hideProgressDialog();
					}
				}) {

			/**
			 * Passing some request headers
			 * */
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("api", AppConstants.API);
				return headers;
			}

			@Override
			protected Map<String, String> getParams() {
				

				return params;
			}

		};

		// Adding request to request queue
		SampleApplication.getInstance().addToRequestQueue(jsonObjReq,
				tag_json_obj);

		// Cancelling request
		// ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);		
	}

	/**
	 * Making json array request
	 * */
	private JSONArray makeJsonArryReq(String URL,final Map<String, String> params) {
		showProgressDialog();
		
		JsonArrayRequest req = new JsonArrayRequest(URL,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, response.toString());
						jsonArray=response;
						hideProgressDialog();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						hideProgressDialog();
					}
				}){

			/**
			 * Passing some request headers
			 * */
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("api", AppConstants.API);
				return headers;
			}

			@Override
			protected Map<String, String> getParams() {
				return params;
			}

		};
		

		// Adding request to request queue
		SampleApplication.getInstance().addToRequestQueue(req,
				tag_json_arry);

		// Cancelling request
		// ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
		return jsonArray;
	}

}
