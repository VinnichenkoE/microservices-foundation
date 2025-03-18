package com.epam.service;

import com.epam.dto.ResourceMetaDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SongIntegrationServiceImpl implements SongIntegrationService {
    @Value("${song.service.url}")
    private String songServiceUrl;

    private final WebClient webClient;

    @Autowired
    public SongIntegrationServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public void saveResourceMetadata(ResourceMetaDataDto resourceMetaData) {
        String url = UriComponentsBuilder
                .fromUriString(songServiceUrl)
                .path("/songs")
                .build()
                .toString();

        webClient
                .post()
                .uri(url)
                .bodyValue(resourceMetaData)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

    }

    @Override
    public void deleteResourceMetadata(String ids) {
        String url = UriComponentsBuilder
                .fromUriString(songServiceUrl)
                .path("/songs")
                .queryParam("id", ids)
                .build()
                .toString();

        webClient
                .delete()
                .uri(url)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
