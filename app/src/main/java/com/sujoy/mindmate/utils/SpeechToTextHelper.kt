package com.sujoy.mindmate.utils

import android.content.Context
import com.google.api.gax.rpc.ClientStream
import com.google.api.gax.rpc.ResponseObserver
import com.google.api.gax.rpc.StreamController
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.speech.v1.RecognitionConfig
import com.google.cloud.speech.v1.SpeechClient
import com.google.cloud.speech.v1.SpeechSettings
import com.google.cloud.speech.v1.StreamingRecognitionConfig
import com.google.cloud.speech.v1.StreamingRecognizeRequest
import com.google.cloud.speech.v1.StreamingRecognizeResponse
import com.sujoy.mindmate.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SpeechToTextHelper(private val context: Context) {

    private var speechClient: SpeechClient? = null
    private var clientStream: ClientStream<StreamingRecognizeRequest>? = null
    private var isListening = false
    private var committedText = ""

    private val _spokenText = MutableStateFlow("")
    val spokenText: StateFlow<String> = _spokenText

    init {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val credentials =
                    GoogleCredentials.fromStream(context.resources.openRawResource(R.raw.cloud_speech_credentials))
                val speechSettings = SpeechSettings.newBuilder()
                    .setCredentialsProvider { credentials }
                    .build()

                speechClient = SpeechClient.create(speechSettings)
            } catch (e: Exception) {
                // Handle exceptions
            }
        }
    }

    fun startListening() {
        if (isListening) return
        isListening = true
        clientStream = speechClient?.streamingRecognizeCallable()
            ?.splitCall(object : ResponseObserver<StreamingRecognizeResponse> {
                override fun onStart(controller: StreamController?) {}

                override fun onResponse(response: StreamingRecognizeResponse?) {
                    val streamingResult = response?.resultsList?.firstOrNull()
                    if (streamingResult != null) {
                        val alternativesList = streamingResult.alternativesList?.firstOrNull()
                        alternativesList?.let {
                            if (streamingResult.isFinal) {
                                committedText =
                                    if (committedText.isNotEmpty()) "$committedText ${it.transcript}" else it.transcript
                                _spokenText.value = committedText
                            } else {
                                _spokenText.value =
                                    if (committedText.isNotEmpty()) "$committedText ${it.transcript}" else it.transcript
                            }
                        }
                    }
                }

                override fun onError(t: Throwable?) {}

                override fun onComplete() {}
            })

        val recognitionConfig = RecognitionConfig.newBuilder()
            .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
            .setSampleRateHertz(16000)
            .setLanguageCode("en-US")
            .build()

        val streamingConfig = StreamingRecognitionConfig.newBuilder()
            .setConfig(recognitionConfig)
            .setInterimResults(true)
            .build()

        val request = StreamingRecognizeRequest.newBuilder()
            .setStreamingConfig(streamingConfig)
            .build()

        clientStream?.send(request)

        CoroutineScope(Dispatchers.IO).launch {
            AudioEmitter().emit(context).collect { audio ->
                val audioRequest = StreamingRecognizeRequest.newBuilder()
                    .setAudioContent(audio)
                    .build()
                clientStream?.send(audioRequest)
            }
        }
    }

    fun stopListening() {
        if (!isListening) return
        isListening = false
        clientStream?.closeSend()
    }

    fun destroy() {
        stopListening()
        speechClient?.shutdownNow()
        speechClient = null
    }
}