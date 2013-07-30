package com.zimerick.zmrklibrary.zmrkdatasync;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Observer;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Log;



public class ZMRKDataRequest {
	
	private static ZMRKDataRequest sharedInstance = null;
	private ZMRKDataEncryption zmrkDataEncryption;
	private ZMRKDataCompression zmrkDataCompression;
	private ZMRKDataSyncManager delegate;
	private ZMRKDataRequestQueue queue;
	
	private String webServiceURL;
	private boolean isNetworkActivityEnabled = true;
	private static final String LOGTAG = "ZMRKDataRequest";
	
	public ZMRKDataRequest() {
		// TODO Auto-generated constructor stub
	}
	
	public static ZMRKDataRequest getSharedInstance ()
	{
		if (sharedInstance == null)
		{
			sharedInstance = new ZMRKDataRequest();
			sharedInstance.zmrkDataEncryption = new ZMRKDataEncryption();
			sharedInstance.zmrkDataCompression = new ZMRKDataCompression();
			sharedInstance.queue = new ZMRKDataRequestQueue();
		}
		return sharedInstance;
	}
	
	
	public void setDataCompression (ZMRKDataCompression _compression) {
		if (_compression != null) {
			this.zmrkDataCompression = _compression;			
		}
		else {
			this.zmrkDataCompression = new ZMRKDataCompression();
		}
	}
	
	public void setDataEncryption (ZMRKDataEncryption _encryption)
	{
		if (_encryption != null) {
			this.zmrkDataEncryption = _encryption;
		}
		else {
			this.zmrkDataEncryption = new ZMRKDataEncryption();
		}
	}
	
	public void setDelegate (ZMRKDataSyncManager _delegate)
	{
		this.delegate = _delegate;
	}


	public String getWebServiceURL() {
		return webServiceURL;
	}

	public void setWebServiceURL(String webServiceURL) {
		this.webServiceURL = webServiceURL;
	}

	public ZMRKDataRequestQueue getQueue() {
		return queue;
	}

	public void setQueue(ZMRKDataRequestQueue queue) {
		this.queue = queue;
	}

	public void requestData (String serviceName, JSONObject data, ZMRKDataRequestQueue.QueuePriority priority, ZMRKDataRequestDelegate delegate)
	{
//		ZMRKLog.d(LOGTAG, "Request data: " + data.toString());
		
		//Only process one at a time
		if (queue.size() > 0)
			return;
		
		if (serviceName == null)
			return;
		
		ZMRKDataRequestQueueObject queueObject = new ZMRKDataRequestQueueObject();
		queueObject.setDelegate(delegate);
		queueObject.setServiceName(serviceName);		
		queueObject.setData(data);
		queue.push(queueObject, priority);
		
		if (queue.size() == 1)
		{
			this.processNextItemInQueue();
		}
	}
	
	public void processNextItemInQueue ()
	{
		if (this.isNetworkActivityEnabled)
		{
			ZMRKDataRequestQueueObject queueObject =  queue.peek();
			if (queueObject != null)
				processQueueObject(queueObject);
		}
	}
	
	public void enableNetworkActivity ()
	{
		this.isNetworkActivityEnabled = true;
		this.processNextItemInQueue();
	}
	
	public void disableNetworkActivity ()
	{
		this.isNetworkActivityEnabled = false;
	}
	
	public void addObserverForQueue (Observer observer)
	{
		queue.addObserver(observer);
	}
	
	protected void processResponseFromAsyncTask (String response)
	{		
		ZMRKDataRequestQueueObject requestObject = queue.peek();;
		JSONObject jsonResponse;
		try {
			
			jsonResponse = new JSONObject(response);
		} catch (JSONException e) {
			ZMRKLog.e(LOGTAG, e.getMessage() + ": " + response);
			this.delegate.handleDataRequestFailed(requestObject, "Unable to parse JSON: " + response);
			this.cancelAllDataRequests();
			return;
		}
		queue.pop();
		this.delegate.handleDataRequestSuccessful(requestObject, jsonResponse);
		
		//this.processNextItemInQueue();
	}
	
	protected void processErrorFromAsyncTask (String error)
	{
		ZMRKDataRequestQueueObject requestObject = queue.pop();
		delegate.handleDataRequestFailed(requestObject, error);
		this.cancelAllDataRequests();		
	}
	
	protected void processQueueObject (ZMRKDataRequestQueueObject queueObject)
	{
		String webService = queueObject.getServiceName();
		String serviceURL = webServiceURL + webService + ".php";
		JSONObject jsonData = queueObject.getData();
		String data = jsonData.toString();
		
		ZMRKDataRequestAsyncTask task = new ZMRKDataRequestAsyncTask(serviceURL, data);		
		task.execute();
		
		//TODO Use dataEncryption/ Data compression
	}
	
	@SuppressWarnings("unused")
	private void showNetworkActivityIndicator ()
	{
		
	}
	
	@SuppressWarnings("unused")
	private void hideNetworkActivityIndicator ()
	{
		
	}
	
	public void cancelDataRequestQueueObject (ZMRKDataRequestQueueObject queueObject)
	{
		delegate.handleDataRequestCancelled(queueObject);
	}
	
	public void cancelAllDataRequests ()
	{
		while (queue.size() != 0)
		{
			ZMRKDataRequestQueueObject queueObject = queue.peek();
			this.cancelDataRequestQueueObject(queueObject);
			queue.pop();
		}
	}
	
	
	
	
	//AsyncTask
    public class ZMRKDataRequestAsyncTask extends AsyncTask<String, Void, String> {
        private String serviceURL;
        private String data;

        public ZMRKDataRequestAsyncTask(String _serviceURL, String _data) {
            this.serviceURL = _serviceURL;
            this.data = _data;
        }

        @Override
        // Actual download method, run in the task thread
        protected String doInBackground(String... params) {        	
        	try {
        		ZMRKLog.d(LOGTAG, "Send request data: " + data);
				String response = sendRequestToServer ();
//        		String response = sendRequestToServer1 ();
				return response;
			} catch (Exception e) {				
				e.printStackTrace();
				return null;
			} 
        }

        @Override
        // Once the image is downloaded, associates it to the imageView
        protected void onPostExecute(String string) {
            if (isCancelled()) {
                processErrorFromAsyncTask("Sync cancelled");
                return;
            }

            if (string != null)  {
            	processResponseFromAsyncTask(string);
            }
            else {
            	processErrorFromAsyncTask("Sync failed");
            }            
        }
        
        public String sendRequestToServer () throws Exception {
        	HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(serviceURL);
            StringEntity se =  new StringEntity (data);
            //sets the post request as the resulting string
            httpPost.setEntity(se);
            //sets a request header so the page receving the request
            //will know what to do with it
            //httpPost.setHeader("Accept", "application/json");
            //httpPost.setHeader("Content-type", "application/json");            
            HttpResponse response = httpClient.execute(httpPost);        
            return getResponseBody(response);        	
        }
        
//        public String sendRequestToServer1 () throws Exception {
//        	HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
//
//            DefaultHttpClient client = new DefaultHttpClient();
//
//            SchemeRegistry registry = new SchemeRegistry();
//            SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
//            socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
//            registry.register(new Scheme("https", socketFactory, 443));
//            SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
//            DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());
//
//            // Set verifier      
//            HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
//
//            HttpPost httpPost = new HttpPost(serviceURL);
//            StringEntity se =  new StringEntity (data);
//            //sets the post request as the resulting string
//            httpPost.setEntity(se);
//            //sets a request header so the page receving the request
//            //will know what to do with it
//            httpPost.setHeader("Accept", "application/json");
//            httpPost.setHeader("Content-type", "application/json");            
//            HttpResponse response = httpClient.execute(httpPost);        
//            return getResponseBody(response);
//        }
//        
        
        
        
//        private void trustEveryone() {
//        	try {
//        		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
//            			public boolean verify(String hostname, SSLSession session) {
//            				return true;
//            			}});
//        		SSLContext context = SSLContext.getInstance("TLS");
//        		context.init(null, new X509TrustManager[]{new X509TrustManager(){
//        			public void checkClientTrusted(X509Certificate[] chain,
//        					String authType) throws CertificateException {}
//        			public void checkServerTrusted(X509Certificate[] chain,
//        					String authType) throws CertificateException {}
//        			public X509Certificate[] getAcceptedIssuers() {
//        				return new X509Certificate[0];
//        			}}}, new SecureRandom());
//        		HttpsURLConnection.setDefaultSSLSocketFactory(
//        				context.getSocketFactory());
//        	} catch (Exception e) { // should never happen
//        		e.printStackTrace();
//        	}
//        }
        
        public String sendRequestToServer1 () throws Exception {
        	HttpClient httpClient = new DefaultHttpClient();
        	httpClient = this.sslClient(httpClient);
        	
            HttpPost httpPost = new HttpPost(serviceURL);
            StringEntity se =  new StringEntity (data);
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);        
            return getResponseBody(response);       
        }        

        
        
        private HttpClient sslClient(HttpClient client) {
    	    try {
    	        X509TrustManager tm = new X509TrustManager() {
    	            public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
    	            }
     
    	            public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
    	            }
     
    	            public X509Certificate[] getAcceptedIssuers() {
    	                return null;
    	            }
    	        };
    	        SSLContext ctx = SSLContext.getInstance("TLS");
    	        ctx.init(null, new TrustManager[]{tm}, null);
    	        SSLSocketFactory ssf = new MySSLSocketFactory(ctx);
    	        ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
    	        ClientConnectionManager ccm = client.getConnectionManager();
    	        SchemeRegistry sr = ccm.getSchemeRegistry();
    	        sr.register(new Scheme("https", ssf, 443));
    	        return new DefaultHttpClient(ccm, client.getParams());
    	    } catch (Exception ex) {
    	        return null;
    	    }
    	}        
        
        
        public String getResponseBody(HttpResponse response) {
        	String response_text = null;
        	HttpEntity entity = null;
        	try {
        		entity = response.getEntity();
        		response_text = _getResponseBody(entity);
        	} 
        	catch (ParseException e) {
        		e.printStackTrace();
        	} 
        	catch (IOException e) {
        		if (entity != null) {
        			try {
        				entity.consumeContent();
        			} 
        			catch (IOException e1) {
        			}
        		}
        	}
        	ZMRKLog.d(LOGTAG, "Receive response: " + response_text);
        	return response_text;
        }
        

        public String _getResponseBody(final HttpEntity entity) throws IOException, ParseException {
        	if (entity == null) { 
        		throw new IllegalArgumentException("HTTP entity may not be null");
        	}

        	InputStream instream = entity.getContent();
        	if (instream == null) { 
        		return ""; 
        	}

        	if (entity.getContentLength() > Integer.MAX_VALUE) { 
        		throw new IllegalArgumentException("HTTP entity too large to be buffered in memory"); 
        	}

        	String charset = getContentCharSet(entity);
        	if (charset == null) {
        		charset = HTTP.DEFAULT_CONTENT_CHARSET;
        	}

        	Reader reader = new InputStreamReader(instream, charset);
        	StringBuilder buffer = new StringBuilder();

        	try {
        		char[] tmp = new char[1024];
        		int l;
        		while ((l = reader.read(tmp)) != -1) {
        			buffer.append(tmp, 0, l);
        		}
        	} finally {
        		reader.close();
        	}
        	return buffer.toString();
        }

        public String getContentCharSet(final HttpEntity entity) throws ParseException {
        	if (entity == null) { 
        		throw new IllegalArgumentException("HTTP entity may not be null"); 
        	}

        	String charset = null;
        	if (entity.getContentType() != null) {
        		HeaderElement values[] = entity.getContentType().getElements();
        		if (values.length > 0) {
        			NameValuePair param = values[0].getParameterByName("charset");
        			if (param != null) {
        				charset = param.getValue();
        			}
        		}
        	}
        	return charset;
        }
        
        public HttpClient wrapClient(HttpClient base) {
            try {
                SSLContext ctx = SSLContext.getInstance("TLS");
                X509TrustManager tm = new X509TrustManager() {
     
                    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                    }
     
                    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                    }
     
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                };
                X509HostnameVerifier verifier = new X509HostnameVerifier() {

					public boolean verify(String host, SSLSession session) {
						// TODO Auto-generated method stub
						return true;
					}

					public void verify(String host, SSLSocket ssl)
							throws IOException {
						// TODO Auto-generated method stub
						
					}

					public void verify(String host, X509Certificate cert)
							throws SSLException {
						// TODO Auto-generated method stub
						
					}

					public void verify(String host, String[] cns,
							String[] subjectAlts) throws SSLException {
						// TODO Auto-generated method stub
						
					}
     
                };
                ctx.init(null, new TrustManager[]{tm}, null);
                SSLSocketFactory ssf = SSLSocketFactory.getSocketFactory();
                ssf.setHostnameVerifier(verifier);
                ClientConnectionManager ccm = base.getConnectionManager();
                SchemeRegistry sr = ccm.getSchemeRegistry();
                sr.register(new Scheme("https", ssf, 443));
                return new DefaultHttpClient(ccm, base.getParams());
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }        
    }	
	
    
    
         
}
