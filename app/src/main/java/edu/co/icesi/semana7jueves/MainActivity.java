package edu.co.icesi.semana7jueves;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private String urlstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        new Thread(
                ()->{
                    //Asincrono
                    try {
                        String urlstr = "https://api.deezer.com/search?q=eminem";
                        URL url = new URL(urlstr);
                        HttpsURLConnection conexion = (HttpsURLConnection) url.openConnection();

                        InputStream is = conexion.getInputStream();

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();

                        byte[] buffer = new byte[1024];
                        int bytesLeidos;
                        while ((bytesLeidos = is.read(buffer)) != -1) {
                            baos.write(buffer, 0, bytesLeidos);
                        }
                        is.close();
                        baos.close();

                        String recibido = new String(baos.toByteArray());
                        Log.e(">>>",recibido);

                        Gson gson = new Gson();
                        Deezer deezer = gson.fromJson(recibido, Deezer.class);

                        for(Song song : deezer.data){
                            Log.e(">>>",song.title);
                        }


                        runOnUiThread(
                                ()->{
                                    Toast.makeText(this, "Descarga completa!", Toast.LENGTH_SHORT).show();
                                }
                        );


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();





    }
}