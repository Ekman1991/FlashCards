package se.tda367.flashcards.services;


import org.json.JSONObject;

public interface IJsonService {
    public String getJsonString();
    public JSONObject getWriter();
    public String toURL(String json);
    public String fromURL(String url);
    public JSONObject toJson(String json);
}
