package com.myapp.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by cgh.
 */
@Configuration
public class ElasticSearchConfig {
    @Value("${elasticsearch.host}")
    private String esHost;

    @Value("${elasticsearch.port}")
    private int esPort;

    @Value("${elasticsearch.cluster.name}")
    private String esName;

    @Bean
    public TransportClient esClient() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", this.esName)
//                .put("cluster.name", "elasticsearch")
                .put("client.transport.sniff", true)
                .build();

        InetSocketTransportAddress master = new InetSocketTransportAddress(
            InetAddress.getByName(esHost), esPort
        );

        InetSocketTransportAddress slave1 = new InetSocketTransportAddress(
                InetAddress.getByName(esHost), 9301
        );

        InetSocketTransportAddress slave2 = new InetSocketTransportAddress(
                InetAddress.getByName(esHost), 9302
        );

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(master)
                .addTransportAddress(slave1)
                .addTransportAddress(slave2);

        return client;
    }
}
