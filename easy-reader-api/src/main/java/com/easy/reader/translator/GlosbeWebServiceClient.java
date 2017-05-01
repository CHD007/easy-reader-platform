package com.easy.reader.translator;

import com.easy.reader.persistance.entity.Word;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Web service client for word's translation.
 * @author dchernyshov
 */
@Stateless
public class GlosbeWebServiceClient {
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
    
        JSONObject jsonObject = new JSONObject(getTranslation(wordToTranslate));
        JSONArray jsonArray = jsonObject.getJSONArray("tuc");
        StringBuilder wordTranslations = new StringBuilder();
    
        for (int i = 0; i < jsonArray.length() && i < MAX_TRANSLATIONS; i++) {
            JSONObject jsonTranslation = jsonArray.getJSONObject(i);
            
            if (jsonTranslation.has("phrase")) {
                wordTranslations.append((String) jsonTranslation.getJSONObject("phrase").get("text"));
                wordTranslations.append(", ");
            }
        }
        
        wordTranslations.delete(wordTranslations.length()-2, wordTranslations.length());
        
        word.setWordName(wordToTranslate);
        word.setTranslation(wordTranslations.toString());
        return word;
    }
}
