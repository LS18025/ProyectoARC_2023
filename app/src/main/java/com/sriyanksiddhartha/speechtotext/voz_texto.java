package com.sriyanksiddhartha.speechtotext;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;
import android.view.MenuItem;
public class voz_texto extends AppCompatActivity {
    private TextView txvResult;
    private Vibrator vibrator;
    private VolumeButtonReceiver volumeButtonReceiver;
    private TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voz_texto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Modo transcripción");

        // Registra el receptor de eventos para las teclas de volumen
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        volumeButtonReceiver = new VolumeButtonReceiver();
        IntentFilter filter = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(volumeButtonReceiver, filter);

        txvResult = (TextView) findViewById(R.id.txvResult);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE); // Inicializa el objeto Vibrator

        // Inicializa el motor TTS para el mensaje de bienvenida
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    // El motor TTS está listo para usar.
                    String mensaje = "Modo Transcripción";
                    textToSpeech.speak(mensaje, TextToSpeech.QUEUE_FLUSH, null, null);
                } else {
                    // El motor TTS no está disponible, maneja el error aquí.
                }
            }
        });

    }

    public void getSpeechInput(View view) {
        vibrator.vibrate(300); // Inicia la vibración durante 1 segundo
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txvResult.setText(result.get(0));
                }
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Esto cerrará la actividad y volverá a la actividad anterior
            return true;
        }
        return super.onOptionsItemSelected(item);
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
