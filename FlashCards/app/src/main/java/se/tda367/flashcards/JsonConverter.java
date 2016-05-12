package se.tda367.flashcards;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;

import se.tda367.flashcards.models.Deck;

/**
 * Created by Razzan on 2016-05-12.
 */
public class JsonConverter {
    JSONObject writer = new JSONObject();
    JSONArray list = new JSONArray();

    public JsonConverter(Deck deck) throws Exception{

        for(int i = 0; i<deck.getSize(); i++){
            list.put(i, deck.getList().get(i));
        }

        writer.put("name", deck.getName());
        writer.put("list", list);
        writer.put("made", deck.getMade());
       /* writer.put("counter", deck.getCounter());
        writer.put("playedSince", deck.getPlayedSince());
        writer.put("nbrOfTimesPlayed", deck.getNbrOfTimesPlayed());*/

    }
    public String getJsonString(){
        return this.writer.toString();
    }
    public String toURL(String url){
        try {
            String encodedUrl = URLEncoder.encode(url, "UTF-8");
            return encodedUrl;

        }
        catch (Exception e)
        {

        }
        return "null";
    }
    public String fromURL(String url){
        try {
            String decodedUrl = URLDecoder.decode(url, "UTF-8");
            return decodedUrl;
        }
        catch(Exception e){

        }
        return "null";
    }
}
