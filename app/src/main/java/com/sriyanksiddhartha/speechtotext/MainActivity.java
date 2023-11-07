package com.sriyanksiddhartha.speechtotext;


import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.IntentFilter;
import android.media.AudioManager;

public class MainActivity extends AppCompatActivity {

    private VolumeButtonReceiver volumeButtonReceiver;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Registra el receptor de eventos para las teclas de volumen
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        volumeButtonReceiver = new VolumeButtonReceiver();
        IntentFilter filter = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(volumeButtonReceiver, filter);

        View btn= findViewById(R.id.btnVoz);
        View btn1= findViewById(R.id.btnTexto);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, voz_texto.class);
                startActivity(intent);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, texto_voz.class);
                startActivity(intent);
            }
        });
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    String mensaje = "Bienvenido, utilice los botones de volumen para seleccionar la función necesaria";
                    textToSpeech.speak(mensaje, TextToSpeech.QUEUE_FLUSH, null, null);
                    // El motor TTS está listo para usar.
                } else {
                    // El motor TTS no está disponible, maneja el error aquí.
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Registra el receptor de eventos para las teclas de volumen al entrar en la actividad principal
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        if (volumeButtonReceiver != null) {
            volumeButtonReceiver.setMainActivityActive(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Desregistra el receptor de eventos al salir de la actividad principal
        if (volumeButtonReceiver != null) {
            volumeButtonReceiver.setMainActivityActive(false);
        }
    }

    @Override
    protected void onDestroy() {
        // Asegúrate de desregistrar el receptor de eventos al destruir la actividad
        if (volumeButtonReceiver != null) {
            unregisterReceiver(volumeButtonReceiver);
        }
        super.onDestroy();
    }
}

