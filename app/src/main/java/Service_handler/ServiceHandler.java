package Service_handler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class ServiceHandler {

    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    public ServiceHandler() {

    }

    /**
     * Making service call
     *
     * @url - url to make request
     * @method - http request method
     */
    public String makeServiceCall(String url, int method) {
        return this.makeServiceCall(url, method, null);
    }

    /**
     * Making service call
     *
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     */
    public String makeServiceCall(String url, int method,
                                  List<NameValuePair> params) {
        try {
            // http client
            // HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
            SSLSocketFactory sf = null;
            SSLContext sslContext = null;
            StringWriter writer;
            try {
                sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, null, null);
            } catch (NoSuchAlgorithmException e) {
                //<YourErrorHandling>
            } catch (KeyManagementException e) {
                //<YourErrorHandling>
            }

//            try{
//                sf = new SSLSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//
//            } catch(Exception e) {
            //<YourErrorHandling>

            //  }
            //   Scheme scheme = new Scheme("https",443,sf);

            DefaultHttpClient httpClient = new DefaultHttpClient();
            // httpClient.getConnectionManager().getSchemeRegistry().register(scheme);
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }

                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {
                // appending params to url
                if (params != null) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);

                httpResponse = httpClient.execute(httpGet);

            }
            httpEntity = httpResponse.getEntity();
            try {
                response = EntityUtils.toString(httpEntity);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }

    public String makeServiceCall_withHeader(String url, int method,
                                             List<NameValuePair> params, String accestoken) {
        try {
            // http client
            //  HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("Token", accestoken);
                httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
                httpPost.addHeader("Connection", "Keep-Alive");
                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }

                httpResponse = httpClient.execute(httpPost);


            } else if (method == GET) {
                // appending params to url
                if (params != null) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);
                httpGet.setHeader("Content-type", "application/json");
                //  httpGet.setHeader("Accept", "application/json");
                httpGet.addHeader("Token", accestoken);
                // httpGet.addHeader("X-DEVICE", device_id);
                httpResponse = httpClient.execute(httpGet);

            }
            httpEntity = httpResponse.getEntity();
            int intresponse = httpResponse.getStatusLine().getStatusCode();

            //httpResponse.g
            // httpEntity.
            response = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }
}