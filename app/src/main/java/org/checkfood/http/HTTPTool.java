package org.checkfood.http;

import com.loopj.android.http.*;
/**
 * Created by alexander on 11.01.2016.
 */
public class HTTPTool {
    public HTTPTool(String barcode){

    }
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final String BASE_URL = "http://checkfood.org/en/request/barcode/";

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
