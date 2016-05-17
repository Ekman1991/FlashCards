package se.tda367.flashcards;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;

import se.tda367.flashcards.models.Deck;

/**
 * Created by Razzan on 2016-05-12.
 */
public class JsonConverter extends AppCompatActivity {
    JSONObject writer = new JSONObject();

    public JsonConverter(){

    }

    public JsonConverter(Deck deck) throws Exception{

        for(int i = 0; i<deck.getSize(); i++){
            writer.put(i+"question", deck.getList().get(i).getQuestion());
            writer.put(i+"answer", deck.getList().get(i).getAnswer());
        }

        writer.put("name", deck.getName());
        writer.put("made", deck.getMade());


    }
    public String getJsonString(){
        return this.writer.toString();
    }
    public JSONObject getWriter(){
        return this.writer;
    }

    //converts Jsonobject to URL where you can send info to other users
    public String toURL(String url){
        try {
            String encodedUrl = URLEncoder.encode(url, "UTF-8");
            return encodedUrl;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"No valid url found", Toast.LENGTH_SHORT).show();
        }
        return "null";
    }
    //includes the app specific uri scheme that the app listens to
    public String toUri(String url){
        return "flashcards://"+url;
    }

    //converts URL containing info to Json string
    public String fromURL(String url){
        try {
            String decodedUrl = URLDecoder.decode(url, "UTF-8");
            return decodedUrl;
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"No url found", Toast.LENGTH_SHORT).show();
        }
        return "null";
    }
    //converts Json string to JSONOBject
    public JSONObject toJson(String url){
        try {
            return new JSONObject(url);
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"No valid JsonUrl found", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
