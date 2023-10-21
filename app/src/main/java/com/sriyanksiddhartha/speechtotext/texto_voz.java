package com.sriyanksiddhartha.speechtotext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.MenuItem;
import android.widget.EditText;
public class texto_voz extends AppCompatActivity {
    private TextToSpeech textToSpeech;
    private EditText editText;
    private View buttonSpeak;
    private View btnBorrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.texto_voz);

        // Habilitar el botón de "Atrás" en la barra superior
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Establece el título personalizado para esta actividad
        setTitle("Texto a Voz");
        editText = (EditText) findViewById(R.id.editText);
        buttonSpeak = findViewById(R.id.buttonSpeak);
        btnBorrar = findViewById(R.id.btnLimpiar);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
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
        super.onDestroy();
    }
        // Resto del código de la actividad...

}
