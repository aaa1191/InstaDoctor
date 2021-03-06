package com.softgates.instadoctor.twillo

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.softgates.instadoctor.twillo.util.BasicChatClient
import com.twilio.chat.ErrorInfo

class TwilioApplication : Application() {
    lateinit var basicClient: BasicChatClient
        private set

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        instance = this
        basicClient = BasicChatClient(applicationContext)
    }

    @JvmOverloads fun showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
      //  debug { text }
        Log.e("TwilioApplication",text)

        Handler(Looper.getMainLooper()).post {
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0)
            toast.show()
        }
    }

    fun showError(error: ErrorInfo) {
        showError("Something went wrong", error)
    }

    fun showError(message: String, error: ErrorInfo) {
        showToast(formatted(message, error), Toast.LENGTH_LONG)
        logErrorInfo(message, error)
    }

    fun logErrorInfo(message: String, error: ErrorInfo) {
        error { formatted(message, error) }
    }

    private fun formatted(message: String, error: ErrorInfo): String {
        return String.format("%s. %s", message, error.toString())
    }

    companion object {
        lateinit var instance: TwilioApplication
            private set
    }
}
