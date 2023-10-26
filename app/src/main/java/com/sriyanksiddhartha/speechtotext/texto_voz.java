package com.sriyanksiddhartha.speechtotext;

import android.content.IntentFilter;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import java.util.Locale;
import android.view.View;
import android.view.MenuItem;
import android.widget.EditText;

public class texto_voz extends AppCompatActivity {
    private TextToSpeech textToSpeech;
    private EditText editText;
    private View buttonSpeak;
    private View btnBorrar;
    private VolumeButtonReceiver volumeButtonReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.texto_voz);


        // Registra el receptor de eventos para las teclas de volumen
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        volumeButtonReceiver = new VolumeButtonReceiver();
        IntentFilter filter = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(volumeButtonReceiver, filter);

        // Habilitar el botón de "Atrás" en la barra superior
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Establece el título personalizado para esta actividad
        setTitle("Modo Texto a Voz");
        editText = (EditText) findViewById(R.id.editText);
        buttonSpeak = findViewById(R.id.buttonSpeak);
        btnBorrar = findViewById(R.id.btnLimpiar);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    String mensaje = "Modo Texto a Voz";
                    textToSpeech.speak(mensaje, TextToSpeech.QUEUE_FLUSH, null, null);
                    // El motor TTS está listo para usar.
                } else {
                    // El motor TTS no está disponible, maneja el error aquí.
                }
            }
        });

        buttonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if (!text.isEmpty()) {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(""); // Borra el contenido del EditText
            }
        });
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
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        if (volumeButtonReceiver != null) {
            unregisterReceiver(volumeButtonReceiver);
        }
        super.onDestroy();
    }


}
