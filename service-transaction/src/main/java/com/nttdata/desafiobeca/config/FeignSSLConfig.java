package com.nttdata.desafiobeca.infra.config;

import feign.Client;
import org.springframework.context.annotation.Bean;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class FeignSSLConfig {

    @Bean
    public Client feignClient() {
        return new Client.Default(getUnsafeSSLSocketFactory(), (hostname, session) -> true);
    }

    private javax.net.ssl.SSLSocketFactory getUnsafeSSLSocketFactory() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() { return null; }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            return sc.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}