package eu.kanade.tachiyomi.ui.setting.track

import android.content.Intent
import android.os.Bundle
import android.view.Gravity.CENTER
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import eu.kanade.tachiyomi.data.track.TrackManager
import eu.kanade.tachiyomi.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import uy.kohesive.injekt.injectLazy

class BangumiLoginActivity : AppCompatActivity() {

    private val trackManager: TrackManager by injectLazy()

    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)

        val view = ProgressBar(this)
        setContentView(view, FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, CENTER))

        val code = intent.data?.getQueryParameter("code")
        if (code != null) {
            scope.launch {
                trackManager.bangumi.login(code)
                returnToSettings()
            }
        } else {
            trackManager.bangumi.logout()
            returnToSettings()
        }
    }

    private fun returnToSettings() {
        finish()

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }
}
