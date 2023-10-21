package com.sriyanksiddhartha.speechtotext;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.IntentFilter;
import android.media.AudioManager;

public class MainActivity extends AppCompatActivity {

    private VolumeButtonReceiver volumeButtonReceiver;

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

