package com.pfe.hypermax.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

@Configuration
public class ProxyConfig {

    @Value("${proxy.host:}")
    private String proxyHost;

    @Value("${proxy.port:8080}")
    private int proxyPort;

    @Bean
    public WebClient.Builder webClientBuilder() {
        WebClient.Builder builder = WebClient.builder();

        if (!proxyHost.isEmpty()) {
            HttpClient httpClient = HttpClient.create()
                    .proxy(proxy -> proxy
                            .type(ProxyProvider.Proxy.HTTP)
                            .host(proxyHost)
                            .port(proxyPort)
                    );
            builder.clientConnector(new ReactorClientHttpConnector(httpClient));
        }

        return builder;
    }
}
