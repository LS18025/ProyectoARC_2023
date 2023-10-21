package com.sriyanksiddhartha.speechtotext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
public class VolumeButtonReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if ("android.media.VOLUME_CHANGED_ACTION".equals(action)) {
            int currentVolume = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
            int previousVolume = intent.getIntExtra("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", 0);

            if (currentVolume > previousVolume) {
                // Botón de subir volumen presionado
                Intent newIntent = new Intent(context, texto_voz.class);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
            } else if (currentVolume < previousVolume) {
                // Botón de bajar volumen presionado
                Intent newIntent = new Intent(context, voz_texto.class);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
            }
        }
    }
}
