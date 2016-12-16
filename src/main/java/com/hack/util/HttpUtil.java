package com.hack.util;

import com.hack.conf.PropertyUtil;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.LineParser;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.aspectj.weaver.SignatureUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.codecs.DefaultHttpRequestWriterFactory;
import org.apache.http.impl.nio.codecs.DefaultHttpResponseParser;
import org.apache.http.impl.nio.codecs.DefaultHttpResponseParserFactory;
import org.apache.http.impl.nio.conn.ManagedNHttpClientConnectionFactory;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.NHttpMessageParser;
import org.apache.http.nio.NHttpMessageParserFactory;
import org.apache.http.nio.NHttpMessageWriterFactory;
import org.apache.http.nio.conn.ManagedNHttpClientConnection;
import org.apache.http.nio.conn.NHttpConnectionFactory;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.SessionInputBuffer;
import org.apache.http.nio.util.HeapByteBufferAllocator;

public class HttpUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    public static final int TIMEOUT_100_MS = 100;// 100毫秒
    public static final int TIMEOUT_200_MS = 200;// 200毫秒
    public static final int TIMEOUT_500_MS = 500;// 推荐配置 ==> 500毫秒
    public static final int TIMEOUT_1_SEC = 1000;// 1秒
    public static final int TIMEOUT_2_SEC = 2000;// 2秒
    public static String rongYunAppKey=  PropertyUtil.getProperty("rongYunAppKey");
    public static String rongYunAppKeySecret=  PropertyUtil.getProperty("rongYunAppKeySecret");
    public static String nonce = PropertyUtil.getProperty("nonce");

    private static PoolingHttpClientConnectionManager connManager = null;
    private static CloseableHttpClient httpclientPool = null;
    private static CloseableHttpClient httpclientRetryPool = null;

    private static PoolingNHttpClientConnectionManager connAsyncManager = null;
    private static CloseableHttpAsyncClient httpclientAsyncPool = null;
    private static CloseableHttpAsyncClient httpclientAsyncRetryPool = null;

    static {
        try {
            SSLContext sslContext = SSLContexts.custom().useTLS().build();
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs,
                                               String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs,
                                               String authType) {
                }
            }}, null);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https",
                            new SSLConnectionSocketFactory(sslContext)).build();

            connManager = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry);
            httpclientPool = HttpClients.custom()
                    .setConnectionManager(connManager).build();
            httpclientRetryPool = HttpClients.custom()
                    .setConnectionManager(connManager).setRetryHandler(getRetryHandler(3)).build();
            // Create socket configuration
            SocketConfig socketConfig = SocketConfig.custom()
                    .setTcpNoDelay(true).build();
            connManager.setDefaultSocketConfig(socketConfig);
            // Create message constraints
            MessageConstraints messageConstraints = MessageConstraints.custom()
                    .setMaxHeaderCount(200).setMaxLineLength(2000).build();
            // Create connection configuration
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                    .setMalformedInputAction(CodingErrorAction.IGNORE)
                    .setUnmappableInputAction(CodingErrorAction.IGNORE)
                    .setCharset(Consts.UTF_8)
                    .setMessageConstraints(messageConstraints).build();
            connManager.setDefaultConnectionConfig(connectionConfig);
            connManager.setMaxTotal(200);
            connManager.setDefaultMaxPerRoute(20);
        } catch (KeyManagementException e) {
            log.error("KeyManagementException", e);
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException", e);
        }

        // 异步 httpclient
        // https://hc.apache.org/httpcomponents-asyncclient-4.0.x/httpasyncclient/examples/org/apache/http/examples/nio/client/AsyncClientConfiguration.java

        try {
            NHttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {

                @Override
                public NHttpMessageParser<HttpResponse> create(
                        final SessionInputBuffer buffer,
                        final MessageConstraints constraints) {
                    LineParser lineParser = new BasicLineParser() {

                        @Override
                        public Header parseHeader(final CharArrayBuffer buffer) {
                            try {
                                return super.parseHeader(buffer);
                            } catch (ParseException ex) {
                                return new BasicHeader(buffer.toString(), null);
                            }
                        }

                    };
                    return new DefaultHttpResponseParser(buffer, lineParser,
                            DefaultHttpResponseFactory.INSTANCE, constraints);
                }

            };
            NHttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();
            NHttpConnectionFactory<ManagedNHttpClientConnection> connFactory = new ManagedNHttpClientConnectionFactory(
                    requestWriterFactory, responseParserFactory,
                    HeapByteBufferAllocator.INSTANCE);
            SSLContext sslcontext = SSLContexts.createSystemDefault();
            X509HostnameVerifier hostnameVerifier = new BrowserCompatHostnameVerifier();
            Registry<SchemeIOSessionStrategy> sessionStrategyRegistry = RegistryBuilder
                    .<SchemeIOSessionStrategy>create()
                    .register("http", NoopIOSessionStrategy.INSTANCE)
                    .register(
                            "https",
                            new SSLIOSessionStrategy(sslcontext,
                                    hostnameVerifier)).build();
            DnsResolver dnsResolver = new SystemDefaultDnsResolver() {
                @Override
                public InetAddress[] resolve(final String host)
                        throws UnknownHostException {
                    if (host.equalsIgnoreCase("myhost")) {
                        return new InetAddress[]{InetAddress
                                .getByAddress(new byte[]{127, 0, 0, 1})};
                    } else {
                        return super.resolve(host);
                    }
                }

            };

            IOReactorConfig ioReactorConfig = IOReactorConfig
                    .custom()
                    .setIoThreadCount(
                            Runtime.getRuntime().availableProcessors())
                    .setConnectTimeout(30000).setSoTimeout(30000).build();

            ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(
                    ioReactorConfig);

            connAsyncManager = new PoolingNHttpClientConnectionManager(
                    ioReactor, connFactory, sessionStrategyRegistry,
                    dnsResolver);

            MessageConstraints messageConstraints = MessageConstraints.custom()
                    .setMaxHeaderCount(200).setMaxLineLength(2000).build();
            // Create connection configuration
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                    .setMalformedInputAction(CodingErrorAction.IGNORE)
                    .setUnmappableInputAction(CodingErrorAction.IGNORE)
                    .setCharset(Consts.UTF_8)
                    .setMessageConstraints(messageConstraints).build();
            // Configure the connection manager to use connection configuration
            connAsyncManager.setDefaultConnectionConfig(connectionConfig);
            connAsyncManager.setConnectionConfig(new HttpHost("somehost", 80),
                    ConnectionConfig.DEFAULT);

            connAsyncManager.setMaxTotal(200);
            connAsyncManager.setDefaultMaxPerRoute(20);
            connAsyncManager.setMaxPerRoute(new HttpRoute(new HttpHost("somehost",
                    80)), 20);

            // Use custom cookie store if necessary.
            CookieStore cookieStore = new BasicCookieStore();
            // Use custom credentials provider if necessary.
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            // Create global request configuration
            RequestConfig defaultRequestConfig = RequestConfig
                    .custom()
                    .setCookieSpec(CookieSpecs.BEST_MATCH)
                    .setExpectContinueEnabled(true)
                    .setStaleConnectionCheckEnabled(true)
                    .setTargetPreferredAuthSchemes(
                            Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(
                            Arrays.asList(AuthSchemes.BASIC)).build();

            // Create an HttpClient with the given custom dependencies and
            // configuration.
            httpclientAsyncPool = HttpAsyncClients.custom()
                    .setConnectionManager(connAsyncManager)
                    .setDefaultCookieStore(cookieStore)
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .setDefaultRequestConfig(defaultRequestConfig).build();
        } catch (Exception e) {
            log.error("CloseableHttpAsyncClient error:" + e);
        }
    }

    @Deprecated
    public static String post(String url, String params, String cookieStr)
            throws ParseException, IOException {
        HttpPost httpPost = new HttpPost(url);
        log.info("requestUrl:" + url);
        if (StringUtils.hasText(params)) {
            List<NameValuePair> kvs = new ArrayList<>();
            for (String entry : params.split("\\&")) {
                String[] paraArr = entry.split("\\=");
                if (paraArr.length != 2) {
                    log.warn("paraArr:" + Arrays.toString(paraArr));
                    continue;
                }
                kvs.add(new BasicNameValuePair(paraArr[0], paraArr[1]));
            }
            httpPost.addHeader("Content-Type",
                    "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.setEntity(new UrlEncodedFormEntity(kvs, HTTP.UTF_8));
        }
        httpPost.setHeader("Cookie", cookieStr);
        try {
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient
                         .execute(httpPost)) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            log.error("post Fail", e);
            return null;
        }
    }

    @Deprecated
    public static String get(String url, String params, String cookieStr)
            throws ParseException, IOException {
        String requestUrl;
        if (StringUtils.hasText(params)) {
            requestUrl = url + "?" + params;
        } else {
            requestUrl = url;
        }

        HttpGet httpGet = new HttpGet(requestUrl);
        httpGet.setHeader("Cookie", cookieStr);
        log.info("requestUrl:" + requestUrl);
        try {
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient
                         .execute(httpGet)) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            log.error("get Fail", e);
            return null;
        }
    }

    @Deprecated
    public static String get(String url, String params) throws ParseException,
            IOException {
        String requestUrl;
        if (StringUtils.hasText(params)) {
            requestUrl = url + "?" + params;
        } else {
            requestUrl = url;
        }

        HttpGet httpGet = new HttpGet(requestUrl);
        try {
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient
                         .execute(httpGet)) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            log.error("get Fail", e);
            return null;
        }
    }

    @Deprecated
    public static String post(String url, Map<String, String> params)
            throws ParseException, IOException {
        String requestUrl;
        requestUrl = url;
        HttpPost httpPost = new HttpPost(requestUrl);

        if (params != null) {
            List<NameValuePair> kvs;
            kvs = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                kvs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.addHeader("Content-Type",
                    "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.setEntity(new UrlEncodedFormEntity(kvs,
                    StandardCharsets.UTF_8));
        }
        try {
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient
                         .execute(httpPost)) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            log.error("post Fail", e);
            return null;
        }
    }

    @Deprecated
    public static String postStringResult(String surl, String charset,
                                          String[][] params) {
        List<NameValuePair> paramList = new ArrayList<>();
        if (params != null && params.length > 0) {
            for (String[] param : params) {
                if (param.length == 2) {
                    paramList.add(new BasicNameValuePair(param[0], param[1]));
                }

            }
        }

        HttpPost post = new HttpPost(surl);
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectionRequestTimeout(3000).setConnectTimeout(3000)
                .setSocketTimeout(3000);
        post.setConfig(builder.build());
        try {
            if (paramList.size() > 0) {
                UrlEncodedFormEntity uefe = new UrlEncodedFormEntity(paramList,
                        charset);
                post.setEntity(uefe);
            }
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(post)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    return EntityUtils.toString(response.getEntity());
                }
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage() + " execute util:" + surl + " failed", e);
            return null;
        }
    }

    @Deprecated
    public static String postStringResult(String surl, String content,
                                          String charset, int socketTimeout) {

        HttpPost post = new HttpPost(surl);
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectionRequestTimeout(30000).setConnectTimeout(30000)
                .setSocketTimeout(socketTimeout);
        post.setConfig(builder.build());
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();

            if (StringUtils.hasText(content)) {
                StringEntity se = new StringEntity(content, ContentType.create(
                        "text/xml", StandardCharsets.UTF_8));
                post.setEntity(se);
            }

            CloseableHttpResponse response = httpclient.execute(post);

            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity());
            }
            return null;
        } catch (Exception e) {
            log.error(e.getMessage() + " execute util:" + surl + " failed", e);
            return null;
        }
    }


    /**
     * get方式调用接口 default 连接请求超时=3s , 连接超时=3s , 读超时=3s
     *
     * @param url
     * @param params
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String getStringResult(String url, String params)
            throws ParseException, IOException {
        String requestUrl;
        if (StringUtils.hasText(params)) {
            requestUrl = url + "?" + params;
        } else {
            requestUrl = url;
        }

        HttpGet get = new HttpGet(requestUrl);
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectionRequestTimeout(3000).setConnectTimeout(3000)
                .setSocketTimeout(3000);
        get.setConfig(builder.build());
        try {
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(get)) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            log.error("getStringResult Fail", e);
            return "";
        }
    }

    /**
     * get方式调用接口
     *
     * @param url
     * @param params
     * @param connReqTimeout (ms)
     * @param connTimeout    (ms)
     * @param socketTimeout  (ms)
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String getStringResult(String url, String params,
                                         int connReqTimeout, int connTimeout, int socketTimeout)
            throws ParseException, IOException {
        String requestUrl;
        if (StringUtils.hasText(params)) {
            requestUrl = url + "?" + params;
        } else {
            requestUrl = url;
        }
        HttpGet get = new HttpGet(requestUrl);
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectionRequestTimeout(connReqTimeout)
                .setConnectTimeout(connTimeout).setSocketTimeout(socketTimeout);
        get.setConfig(builder.build());
        try {
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(get)) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            log.error("getStringResult Fail", e);
            return "";
        }
    }

    /**
     * post方式调用接口 default 连接请求超时=3s , 连接超时=3s , 读超时=3s
     *
     * @param surl
     * @param params
     * @param charset
     * @param ip
     * @return
     */
    public static String postStringResult(String surl,
                                          List<NameValuePair> params, String charset, String ip) {
        HttpPost post = new HttpPost(surl);
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectionRequestTimeout(3000).setConnectTimeout(3000)
                .setSocketTimeout(3000);
        post.setConfig(builder.build());
        try {
            post.addHeader("mt_client_ip", ip);
            if (params != null && params.size() > 0) {
                UrlEncodedFormEntity uefe = new UrlEncodedFormEntity(params,
                        charset);
                post.setEntity(uefe);
            }
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(post)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    return EntityUtils.toString(response.getEntity());
                }
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage() + " execute util:" + surl + " failed", e);
            return null;
        }
    }

    /**
     * post方式调用接口
     *
     * @param surl
     * @param params
     * @param charset
     * @param connReqTimeout (ms)
     * @param connTimeout    (ms)
     * @param socketTimeout  (ms)
     * @return
     */
    public static String postStringResult(String surl,
                                          List<NameValuePair> params, String charset, int connReqTimeout,
                                          int connTimeout, int socketTimeout) {
        log.info("post in " + surl);
        HttpPost post = new HttpPost(surl);
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectionRequestTimeout(connReqTimeout)
                .setConnectTimeout(connTimeout).setSocketTimeout(socketTimeout);
        post.setConfig(builder.build());
        try {
            if (params != null && params.size() > 0) {
                UrlEncodedFormEntity uefe = new UrlEncodedFormEntity(params,
                        charset);
                post.setEntity(uefe);
            }
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(post)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    log.info("response status: 200");
                    return EntityUtils.toString(response.getEntity());
                }
                log.info("response status: {}", response.getStatusLine().getStatusCode());
                log.info("res :{}", EntityUtils.toString(response.getEntity()));
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage() + " execute util:" + surl + " failed", e);
            return null;
        }
    }

    /**
     * 得到返回的二进制数组.只在200的时候返回.其他时候返回null
     *
     * @param surl
     * @return
     * @author chenchun
     * @created 2012-9-14
     */
    public static byte[] getByteResult(String surl) {
        HttpGet get = new HttpGet(surl);
        try (CloseableHttpClient hc = HttpClients.createDefault();
             CloseableHttpResponse response = hc.execute(get)) {

            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toByteArray(response.getEntity());
            }
            return null;
        } catch (Exception e) {
            log.error(e.getMessage() + " execute util:" + surl + " failed", e);
            return null;
        }
    }

    /**
     * get 异步请求接口
     *
     * @param url
     * @param params
     * @param connection_timeout
     * @param so_timeout
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static Future<HttpResponse> asyncGet(final String url,
                                                Map<String, String> params, int connection_timeout, int so_timeout) throws ParseException, IOException {
        String requestUrl;
        if (params != null && params.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
            requestUrl = url + "?" + sb.toString();
        } else {
            requestUrl = url;
        }

        HttpGet httpGet = new HttpGet(requestUrl);

        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectionRequestTimeout(connection_timeout)
                .setConnectTimeout(connection_timeout)
                .setSocketTimeout(so_timeout);
        httpGet.setConfig(builder.build());

        httpclientAsyncPool.start();
        Future<HttpResponse> future = httpclientAsyncPool.execute(httpGet,
                new FutureCallback<HttpResponse>() {

                    @Override
                    public void failed(Exception ex) {
                        try {
                            log.info(url + "    failed......");
                            httpclientAsyncPool.close();
                        } catch (IOException e) {
                            log.error("httpClient.close():" + e);
                        }
                    }

                    @Override
                    public void completed(HttpResponse result) {
                        try {
                            log.info(url + "    completed......");
                            httpclientAsyncPool.close();
                        } catch (IOException e) {
                            log.error("httpClient.close():" + e);
                        }
                    }

                    @Override
                    public void cancelled() {
                        try {
                            log.info(url + "    cancelled......");
                            httpclientAsyncPool.close();
                        } catch (IOException e) {
                            log.error("httpClient.close():" + e);
                        }
                    }
                });

        return future;
    }

    /**
     * 异步调用，返回 future
     *
     * @param url
     * @param params
     * @param connection_timeout
     * @param so_timeout
     * @param retryTimes
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static Future<HttpResponse> asyncPost(String url,
                                                 Map<String, String> params, int connection_timeout, int so_timeout,
                                                 int retryTimes) throws ParseException, IOException {

        HttpPost httpPost = new HttpPost(url);
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectionRequestTimeout(connection_timeout)
                .setConnectTimeout(connection_timeout)
                .setSocketTimeout(so_timeout);

        httpPost.setConfig(builder.build());

        if (params != null && params.size() > 0) {
            List<NameValuePair> kvs = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                kvs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.addHeader("Content-Type",
                    "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.setEntity(new UrlEncodedFormEntity(kvs, HTTP.UTF_8));
        }

        httpclientAsyncPool.start();
        Future<HttpResponse> future = httpclientAsyncPool.execute(httpPost,
                new FutureCallback<HttpResponse>() {
                    @Override
                    public void failed(Exception ex) {
                        try {
                            httpclientAsyncPool.close();
                        } catch (IOException e) {
                            log.error("httpClient.close():" + e);
                        }
                    }

                    @Override
                    public void completed(HttpResponse result) {
                        try {
                            httpclientAsyncPool.close();
                        } catch (IOException e) {
                            log.error("httpClient.close():" + e);
                        }
                    }

                    @Override
                    public void cancelled() {
                        try {
                            httpclientAsyncPool.close();
                        } catch (IOException e) {
                            log.error("httpClient.close():" + e);
                        }
                    }
                });

        return future;
    }

    /**
     * @param url
     * @param params
     * @param connection_timeout
     * @param so_timeout
     * @param retryTimes         重试次数
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String postRetry(String url, Map<String, String> params,
                                   int connection_timeout, int so_timeout, int retryTimes)
            throws ParseException, IOException {

        HttpPost httpPost = new HttpPost(url);
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectionRequestTimeout(connection_timeout)
                .setConnectTimeout(connection_timeout)
                .setSocketTimeout(so_timeout);

        httpPost.setConfig(builder.build());

        if (params != null && params.size() > 0) {
            List<NameValuePair> kvs = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                kvs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.addHeader("Content-Type",
                    "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.setEntity(new UrlEncodedFormEntity(kvs, HTTP.UTF_8));
        }

        HttpResponse response = httpclientRetryPool.execute(httpPost);
        return EntityUtils.toString(response.getEntity());
    }

    /**
     * @param url
     * @param params
     * @param connection_timeout
     * @param so_timeout
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String postNoRetry(String url, Map<String, String> params,
                                     int connection_timeout, int so_timeout) throws ParseException,
            IOException {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectionRequestTimeout(connection_timeout)
                .setConnectTimeout(connection_timeout)
                .setSocketTimeout(so_timeout);

        httpPost.setConfig(builder.build());

        if (params != null && params.size() > 0) {
            List<NameValuePair> kvs = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                kvs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.addHeader("Content-Type",
                    "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.addHeader("App-Key",rongYunAppKey);
            httpPost.addHeader("Nonce",nonce);
            String timestamp = DateTime.now().getMillis()/1000+"";
            httpPost.addHeader("Timestamp",timestamp);
            String signature = SHAUtil.getSha1(rongYunAppKeySecret+nonce+timestamp);
            httpPost.addHeader("Signature",signature );
            httpPost.setEntity(new UrlEncodedFormEntity(kvs, HTTP.UTF_8));
        }

        HttpResponse response = httpclientPool.execute(httpPost);
        return EntityUtils.toString(response.getEntity());
    }


    /*
     * get方式调用接口
     *
     * @param url
     *
     * @param params
     *
     * @param connReqTimeout (ms)
     *
     * @param connTimeout (ms)
     *
     * @param socketTimeout (ms)
     *
     * @return
     *
     * @throws ParseException
     *
     * @throws IOException
     */
    public static String getRetry(String url, Map<String, String> params,
                                  int connectionTimeout, int socketTimeout, int retryTimes)
            throws ParseException, IOException {

        HttpGet get = new HttpGet(url);
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionTimeout)
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(socketTimeout);
        get.setConfig(builder.build());
        try {
            CloseableHttpResponse response = httpclientRetryPool.execute(get);
            return EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            log.error("getStringResult Fail", e);
            return "";
        }
    }

    /*
     * get方式调用接口
     *
     * @param url
     *
     * @param params
     *
     * @param connReqTimeout (ms)
     *
     * @param connTimeout (ms)
     *
     * @param socketTimeout (ms)
     *
     * @return
     *
     * @throws ParseException
     *
     * @throws IOException
     */
    public static String getNoRetry(String url, Map<String, String> params,
                                    int connectionTimeout, int socketTimeout) throws ParseException,
            IOException {

        HttpGet get = new HttpGet(url);
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionTimeout)
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(socketTimeout);
        get.setConfig(builder.build());
        try {
            CloseableHttpResponse response = httpclientPool.execute(get);
            return EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            log.error("getStringResult Fail", e);
            return "";
        }
    }

    public static HttpRequestRetryHandler getRetryHandler(final int retryTimes) {
        DefaultHttpRequestRetryHandler myRetryHandler = new DefaultHttpRequestRetryHandler() {

            @Override
            public boolean retryRequest(IOException exception,
                                        int executionCount, HttpContext context) {
                if (executionCount >= retryTimes) {
                    // Do not retry if over max retry count
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    // Timeout
                    return false;
                }
                if (exception instanceof UnknownHostException) {
                    // Unknown host
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    // Connection refused
                    return false;
                }
                if (exception instanceof SSLException) {
                    // SSL handshake exception
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext
                        .adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // Retry if the request is considered idempotent
                    return true;
                }
                return false;
            }
        };

        return myRetryHandler;
    }

    public static void main(String[] args) {
        try {
            String url = "http://open-in.test.meituan.com" + "/user/login";

            String r;

            r = HttpUtil
                    .postStringResult(url, Arrays
                                    .asList((NameValuePair) new BasicNameValuePair(
                                                    "email", "zhangweihua@meituan.com"),
                                            new BasicNameValuePair("password",
                                                    "weihua19870925"),
                                            new BasicNameValuePair("ip", ""),
                                            new BasicNameValuePair("uuid", ""),
                                            new BasicNameValuePair("captcha", "")),
                            "UTF-8", "");
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
