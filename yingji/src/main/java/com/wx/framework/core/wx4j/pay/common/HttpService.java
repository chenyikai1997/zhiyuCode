package com.wx.framework.core.wx4j.pay.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpService
{
  private static Log logger = LogFactory.getLog(HttpService.class);
  private static CloseableHttpClient httpClient = buildHttpClient();
  private static int socketTimeout = 5000;
  private static int connectTimeout = 5000;
  private static int requestTimeout = 5000;

  public static CloseableHttpClient buildHttpClient()
  {
    KeyStore keyStore;
    try
    {
      keyStore = KeyStore.getInstance("PKCS12");
      FileInputStream instream = new FileInputStream(new File(Configure.getCertLocalPath()));
      try {
        keyStore.load(instream, Configure.getCertPassword().toCharArray());
      } finally {
        instream.close();
      }

      SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, Configure.getCertPassword().toCharArray()).build();

      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

      RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setConnectionRequestTimeout(requestTimeout).setSocketTimeout(socketTimeout).build();

      httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).setSSLSocketFactory(sslsf).build();

      return httpClient;
    } catch (Exception e) {
      throw new RuntimeException("error create httpclient......", e);
    }
  }

  public static String doGet(String requestUrl) throws Exception
  {
    HttpGet httpget = new HttpGet(requestUrl);
    try
    {
      logger.debug("Executing request " + httpget.getRequestLine());

      ResponseHandler responseHandler = new ResponseHandler()
      {
        public String handleResponse(HttpResponse response)
          throws ClientProtocolException, IOException
        {
          int status = response.getStatusLine().getStatusCode();
          if ((status >= 200) && (status < 300)) {
            HttpEntity entity = response.getEntity();
            return ((entity != null) ? EntityUtils.toString(entity) : null);
          }
          throw new ClientProtocolException("Unexpected response status: " + status);
        }

      };
      String str = (String)httpClient.execute(httpget, responseHandler);

      return str; } finally { httpget.releaseConnection();
    }
  }

  public static String doPost(String url, Object object2Xml)
  {
    String result = null;

    HttpPost httpPost = new HttpPost(url);

    String postDataXML = XMLParser.toXML(object2Xml);

    logger.info("API POST DATA:");
    logger.info(postDataXML);

    StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
    httpPost.addHeader("Content-Type", "text/xml");
    httpPost.setEntity(postEntity);

    logger.info("executing request" + httpPost.getRequestLine());
    try
    {
      HttpResponse response = httpClient.execute(httpPost);

      HttpEntity entity = response.getEntity();

      result = EntityUtils.toString(entity, "UTF-8");
    }
    catch (ConnectionPoolTimeoutException e) {
      logger.error("http get throw ConnectionPoolTimeoutException(wait time out)", e);
    }
    catch (ConnectTimeoutException e) {
      logger.error("http get throw ConnectTimeoutException", e);
    }
    catch (SocketTimeoutException e) {
      logger.error("http get throw SocketTimeoutException", e);
    }
    catch (Exception e) {
      logger.error("http get throw Exception", e);
    }
    finally {
      httpPost.releaseConnection();
    }

    return result;
  }
}