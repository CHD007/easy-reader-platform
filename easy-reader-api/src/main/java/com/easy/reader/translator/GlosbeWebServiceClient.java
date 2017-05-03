package com.easy.reader.translator;

import com.easy.reader.persistance.entity.Word;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Web service client for word's translation.
 * @author dchernyshov
 */
@Stateless
public class GlosbeWebServiceClient {
    private static final Logger LOGGER = Logger.getLogger(GlosbeWebServiceClient.class);
    private static final int MAX_TRANSLATIONS = 5;
    private static final String BASE_URL = "https://glosbe.com/gapi/translate";
    
    private String getTranslation(String word) {
        Client client = ClientBuilder.newClient();
        WebTarget baseTarget = client.target(BASE_URL);
        return baseTarget.queryParam("from", "eng").
                queryParam("dest", "rus").
                queryParam("format", "json").
                queryParam("phrase", word).
                request(MediaType.APPLICATION_JSON).
                get(String.class);
    }
    
    /**
     * Find word translation.
     * @param wordToTranslate word for translation
     * @return {@see Word} object with translation
     */
    public Word getWordWithTranslation(String wordToTranslate) {
        Word word = new Word();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode tuc = mapper.readTree(getTranslation(wordToTranslate)).path("tuc");
            String translations = StreamSupport.stream(tuc.spliterator(), false)
                    .map(e -> e.path("phrase").path("text").asText())
                    .filter(translation -> !"".equals(translation))
                    .limit(MAX_TRANSLATIONS)
                    .collect(Collectors.joining(", "));
            word.setTranslation(translations);
        } catch (IOException e) {
            LOGGER.error("Error while parsing word's translation", e);
        }

        return word;
    }
}
