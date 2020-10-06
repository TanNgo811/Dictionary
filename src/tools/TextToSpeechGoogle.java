package tools;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.net.URL;
import java.net.HttpURLConnection;
import javazoom.jl.player.Player;
import javazoom.jl.decoder.JavaLayerException;

public class TextToSpeechGoogle {

    public InputStream getAudio(String word, String targetLang) throws IOException {
        if (word == null || word.equals("")) {
            return null;
        }

        String url = "https://translate.google.com/translate_tts?ie=UTF-8&tl=" + targetLang + "&client=tw-ob&q=" + URLEncoder.encode(word, "UTF-8");
        URL object = new URL(url);
        HttpURLConnection URLConnect = (HttpURLConnection) object.openConnection();
        URLConnect.setRequestProperty("User-Agent", "Mozilla/5.0");

        InputStream audioSrc = URLConnect.getInputStream();

        System.out.println(audioSrc.toString());

        return new BufferedInputStream(audioSrc);
    }

    public void play(InputStream sound) throws JavaLayerException {
        (new Player(sound)).play();
    }
}
