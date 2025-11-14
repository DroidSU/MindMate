package com.sujoy.mindmate.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.core.app.ActivityCompat
import com.google.protobuf.ByteString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AudioEmitter {

    @SuppressLint("MissingPermission")
    fun emit(context: Context): Flow<ByteString> = flow {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return@flow
        }

        val audioRecord = AudioRecord.Builder()
            .setAudioSource(MediaRecorder.AudioSource.MIC)
            .setAudioFormat(
                AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(16000)
                    .setChannelMask(AudioFormat.CHANNEL_IN_MONO)
                    .build()
            )
            .setBufferSizeInBytes(3200)
            .build()

        audioRecord.startRecording()

        val buffer = ByteArray(3200)
        while (true) {
            val read = audioRecord.read(buffer, 0, buffer.size)
            if (read > 0) {
                emit(ByteString.copyFrom(buffer, 0, read))
            }
        }
    }
}