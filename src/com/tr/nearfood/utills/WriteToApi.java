package com.tr.nearfood.utills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

public class WriteToApi {

	public class makeHttpPostConnection extends
			AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String jsonData = httpPostConnection("ENTER YOUR URL HERE");
			return null;
		}

	}

	public String httpPostConnection(String url) {

		// Building post parameters
		// key and value pair
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
		/*
		 * nameValuePair.add(new BasicNameValuePair("customerid", Integer
		 * .toString(_customerId))); nameValuePair.add(new
		 * BasicNameValuePair("device_token", _deviceToken));
		 * nameValuePair.add(new BasicNameValuePair("mode", "info"));
		 * Log.d("NameVlaue Pair", nameValuePair.toString());
		 */
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			HttpResponse httpResponse = client.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream content = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					content));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.d("HTTP POST CONNECTION", builder.toString());
		return builder.toString();

	}
}
