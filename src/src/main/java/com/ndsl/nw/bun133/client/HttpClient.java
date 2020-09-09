package com.ndsl.nw.bun133.client;

import com.ndsl.nw.bun133.json.Json;
import com.ndsl.nw.bun133.json.JsonException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpClient {
    public static final Duration default_timeout = Duration.ofSeconds(10);
    public static final java.net.http.HttpClient.Version default_version = java.net.http.HttpClient.Version.HTTP_2;
    public static final java.net.http.HttpClient.Redirect default_redirect = java.net.http.HttpClient.Redirect.NORMAL;

    public String url;
    public java.net.http.HttpClient client;
    public HttpClient(String url){
        client=genClient(url,default_version,default_timeout,default_redirect);
        this.url=url;
    }

    public HttpClient(String url,java.net.http.HttpClient.Version version){
        client=genClient(url,version,default_timeout,default_redirect);
        this.url=url;
    }

    public HttpClient(String url,Duration timeout){
        client=genClient(url,default_version,timeout,default_redirect);
        this.url=url;
    }

    public HttpClient(String url, java.net.http.HttpClient.Redirect redirect){
        client=genClient(url,default_version,default_timeout,redirect);
        this.url=url;
    }

    private java.net.http.HttpClient genClient(String url, java.net.http.HttpClient.Version httpVersion, Duration timeout, java.net.http.HttpClient.Redirect redirect){
        return java.net.http.HttpClient.newBuilder()
                .version(httpVersion)
                .connectTimeout(timeout)
                .followRedirects(redirect)
                .build();
    }

    /*
        Base
     */

    public HttpResponse<String> sendReq(HttpRequest req) throws IOException, InterruptedException {
        return client.send(req,HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<byte[]> sendByteReq(HttpRequest req) throws IOException, InterruptedException {
        return client.send(req,HttpResponse.BodyHandlers.ofByteArray());
    }

    public Json sendReqAndGetJson(HttpRequest req,Class MapClass) throws JsonException, IOException, InterruptedException {
        return Json.buildThrowError(sendReq(req).body(),MapClass);
    }

    public Request Request=new Request();
    /**
     * Request
     */
    public class Request{
        public final String UserAgent = "NDSL-Agent";
        public final Duration default_timeout=Duration.ofSeconds(10);
        /*
         Get
        */
        public GET GET=new GET();
        public class GET {
            public HttpRequest genGetReq(String url, String header, Duration timeout) throws URISyntaxException {
                return HttpRequest.newBuilder(new URI(url))
                        .GET()
                        .setHeader("User-Agent", header)
                        .timeout(timeout)
                        .build();
            }

            public HttpRequest genGetReq(String url) throws URISyntaxException {
                return genGetReq(url, UserAgent, default_timeout);
            }

            public HttpRequest genGetReq() throws URISyntaxException {
                return genGetReq(HttpClient.this.url);
            }
        }
        public POST POST=new POST();
        public class POST{
            public HttpRequest genPostReq(String url, HttpRequest.BodyPublisher publisher,String header,Duration timeout) throws URISyntaxException{
                return HttpRequest.newBuilder(new URI(url))
                        .POST(publisher)
                        .timeout(timeout)
                        .setHeader("User-Agent",header)
                        .build();
            }

            public HttpRequest genPostReq(String url, HttpRequest.BodyPublisher publisher) throws URISyntaxException {
                return genPostReq(url,publisher,UserAgent, default_timeout);
            }

            public HttpRequest genPostReq(HttpRequest.BodyPublisher bodyPublisher) throws URISyntaxException {
                return genPostReq(HttpClient.this.url,bodyPublisher);
            }
        }
        public DEL DEL=new DEL();
        public class DEL{
            public HttpRequest genDelReq(String url,String header,Duration timeout) throws URISyntaxException{
                return HttpRequest.newBuilder(new URI(url))
                        .DELETE()
                        .timeout(timeout)
                        .setHeader("User-Agent",header)
                        .build();
            }

            public HttpRequest genDelReq(String url) throws URISyntaxException {
                return genDelReq(url,UserAgent, default_timeout);
            }

            public HttpRequest genDelReq() throws URISyntaxException {
                return genDelReq(HttpClient.this.url);
            }
        }
        public COPY COPY=new COPY();
        public class COPY{
            public HttpRequest genCopyReq(String url,String header,Duration timeout) throws URISyntaxException{
                return HttpRequest.newBuilder(new URI(url))
                        .copy()
                        .timeout(timeout)
                        .setHeader("User-Agent",header)
                        .build();
            }

            public HttpRequest genCopyReq(String url) throws URISyntaxException {
                return genCopyReq(url,UserAgent, default_timeout);
            }

            public HttpRequest genCopyReq() throws URISyntaxException {
                return genCopyReq(HttpClient.this.url);
            }
        }
        public PUT PUT=new PUT();
        public class PUT{
            public HttpRequest genPutReq(String url,HttpRequest.BodyPublisher bodyPublisher,String header,Duration timeout) throws URISyntaxException{
                return HttpRequest.newBuilder(new URI(url))
                        .PUT(bodyPublisher)
                        .timeout(timeout)
                        .setHeader("User-Agent",header)
                        .build();
            }

            public HttpRequest genPutReq(String url,HttpRequest.BodyPublisher bodyPublisher) throws URISyntaxException {
                return genPutReq(url,bodyPublisher ,UserAgent, default_timeout);
            }

            public HttpRequest genCopyReq(HttpRequest.BodyPublisher bodyPublisher) throws URISyntaxException {
                return genPutReq(HttpClient.this.url,bodyPublisher);
            }
        }
    }
}
