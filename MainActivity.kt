package com.jarvis.voice

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.*
import java.util.Locale

class MainActivity : Activity(), TextToSpeech.OnInitListener {
    private lateinit var status: TextView
    private lateinit var transcript: TextView
    private lateinit var listenButton: Button
    private var recognizer: SpeechRecognizer? = null
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 48, 32, 32)
        }

        val title = TextView(this).apply {
            text = "Jarvis Voice Assistant"
            textSize = 28f
        }
        status = TextView(this).apply {
            text = "Ready — press the button and speak"
            textSize = 18f
        }
        transcript = TextView(this).apply {
            text = "You: —"
            textSize = 20f
            setPadding(0, 24, 0, 24)
        }
        listenButton = Button(this).apply { text = "🎙️ Speak" }

        layout.addView(title)
        layout.addView(status)
        layout.addView(transcript)
        layout.addView(listenButton)
        setContentView(layout)

        tts = TextToSpeech(this, this)
        listenButton.setOnClickListener { startListening() }

        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), 10)
        }
    }

    override fun onInit(result: Int) {
        if (result == TextToSpeech.SUCCESS) {
            tts.language = Locale("ur", "PK")
        }
    }

    private fun startListening() {
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            speak("Is phone par voice recognition available nahi hai.")
            return
        }

        recognizer?.destroy()
        recognizer = SpeechRecognizer.createSpeechRecognizer(this)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ur-PK")
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false)
        }

        status.text = "Sun raha hoon..."
        recognizer!!.setRecognitionListener(object : RecognitionListener {
            override fun onResults(results: Bundle) {
                val text = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    ?.firstOrNull().orEmpty()
                transcript.text = "You: $text"
                handleCommand(text.lowercase(Locale.ROOT))
            }
            override fun onError(error: Int) {
                status.text = "Dobara try karo."
            }
            override fun onReadyForSpeech(p0: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(p0: Float) {}
            override fun onBufferReceived(p0: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onPartialResults(p0: Bundle?) {}
            override fun onEvent(p0: Int, p1: Bundle?) {}
        })
        recognizer!!.startListening(intent)
    }

    private fun handleCommand(command: String) {
        when {
            command.contains("youtube") || command.contains("یوٹیوب") -> {
                speak("Theek hai, YouTube khol raha hoon.")
                openUrl("https://www.youtube.com")
            }
            command.contains("instagram") || command.contains("انسٹاگرام") -> {
                speak("Theek hai, Instagram khol raha hoon.")
                openUrl("https://www.instagram.com")
            }
            command.contains("chrome") || command.contains("browser") -> {
                speak("Theek hai, browser khol raha hoon.")
                openUrl("https://www.google.com")
            }
            command.contains("band") || command.contains("stop") ||
                    command.contains("بس") || command.contains("بند") -> {
                speak("Theek hai, main ruk gaya.")
            }
            command.contains("hello") || command.contains("salam") ||
                    command.contains("السلام") -> {
                speak("Wa alaikum assalam! Bolo, kya karna hai?")
            }
            else -> {
                speak("Maine suna: $command. Abhi is command ka action version 1 mein available nahi hai.")
            }
        }
        status.text = "Ready"
    }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "jarvis")
    }

    override fun onDestroy() {
        recognizer?.destroy()
        tts.shutdown()
        super.onDestroy()
    }
}
