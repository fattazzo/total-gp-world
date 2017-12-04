package com.gmail.fattazzo.formula1world.activity.dbupdate

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.activeandroid.ActiveAndroid
import com.activeandroid.util.ReflectionUtils
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.service.DataService
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager
import org.androidannotations.annotations.*
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


/**
 * @author fattazzo
 *         <p/>
 *         date: 28/11/17
 */
@EActivity(R.layout.activity_dbupdate)
open class DBUpdateActivity : Activity() {

    @Extra
    @JvmField
    var versionToUpdate: Int = 0

    @Extra
    lateinit internal var currentVersione: String

    @Extra
    lateinit internal var onlineMinAppVersion: String

    @Bean
    lateinit internal var preferenceManager: ApplicationPreferenceManager

    @Bean
    lateinit internal var dataService: DataService

    @ViewById
    lateinit internal var downloadProgressBar: ProgressBar

    @ViewById
    lateinit internal var downloadProgressTV: TextView

    @ViewById
    lateinit internal var dbUpdateDescriptionTV: TextView

    @ViewById
    lateinit internal var minVersionTV: TextView

    @ViewById
    lateinit internal var cancelButton: Button

    private var downloadTask: DownloadTask? = null

    private var canClose: Boolean = true

    @AfterViews
    fun initViews() {
        dbUpdateDescriptionTV.text = Html.fromHtml(getString(R.string.dbupdate_message_text))

        minVersionTV.visibility = if (convertVersionToInt(currentVersione) < convertVersionToInt(onlineMinAppVersion)) View.VISIBLE else View.GONE
        var text = getString(R.string.dbupdate_versionecode_text)
        text = text.toString().replace("{0}", currentVersione).replace("{1}", onlineMinAppVersion)
        minVersionTV.text = Html.fromHtml(text)

        updateDBButtonClicked()
    }

    @Click
    fun updateDBButtonClicked() {
        if (convertVersionToInt(currentVersione) > convertVersionToInt(onlineMinAppVersion)) {
            downloadTask = DownloadTask(this)
            downloadTask!!.execute(DB_URL)
        }
    }

    private fun convertVersionToInt(version: String): Int {
        val stringSplit = version.split(".")
        return stringSplit[0].toInt() * 1000 + stringSplit[1].toInt()
    }

    @Click
    fun cancelButtonClicked() {
        if (downloadTask != null) {
            downloadTask!!.cancel(true)
        }
    }

    override fun onBackPressed() {
        if (canClose) {
            super.onBackPressed()
        }
    }

    private inner class DownloadTask(private val context: Context) : AsyncTask<String, Int, String?>() {

        var operation = "Download"

        override fun doInBackground(vararg sUrl: String): String? {
            var input: InputStream? = null
            var output: OutputStream? = null
            var connection: HttpURLConnection? = null
            try {
                val url = URL(sUrl[0])
                connection = url.openConnection() as HttpURLConnection
                connection.connect()

                if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    return ("Server returned HTTP " + connection.responseCode + " " + connection.responseMessage)
                }

                val fileLength = connection.contentLength

                input = connection.inputStream

                output = FileOutputStream(DOWNLOAD_PATH + DOWNLOAD_FILE_NAME)

                val data = ByteArray(4096)
                var total: Long = 0

                while (true) {
                    val count = input!!.read(data)
                    if (count <= 0)
                        break

                    if (isCancelled) {
                        input.close()
                        canClose = true
                        return null
                    }
                    total += count.toLong()

                    if (fileLength > 0)
                        publishProgress((total * 100 / fileLength).toInt())
                    output.write(data, 0, count)
                }

                ActiveAndroid.dispose()

                val dbName = ReflectionUtils.getMetaData<String>(applicationContext, "AA_DB_NAME")
                deleteDatabase(dbName)

                File(DOWNLOAD_PATH + DOWNLOAD_FILE_NAME).renameTo(File(DOWNLOAD_PATH + dbName))
            } catch (e: Exception) {
                return e.toString()
            } finally {
                try {
                    if (output != null)
                        output.close()
                    if (input != null)
                        input.close()
                } catch (ignored: IOException) {
                }

                if (connection != null)
                    connection.disconnect()

                dataService.clearCache()
                ActiveAndroid.initialize(this@DBUpdateActivity)
            }
            return null
        }

        override fun onPreExecute() {
            super.onPreExecute()
            canClose = false

            cancelButton.isEnabled = true

            downloadProgressTV.text = ""

            downloadProgressBar.max = 100
            downloadProgressBar.progress = 0
        }

        override fun onProgressUpdate(vararg progress: Int?) {
            super.onProgressUpdate(*progress)

            downloadProgressBar.isIndeterminate = false
            downloadProgressBar.max = 100
            downloadProgressBar.progress = progress[0]!!.or(0)

            downloadProgressTV.text = operation + ": " + downloadProgressBar.progress.toString() + "% / 100%"
        }

        override fun onPostExecute(result: String?) {
            canClose = true
            cancelButton.isEnabled = false
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show()
            else
                Toast.makeText(context, "DB aggiornato alla versione " + versionToUpdate, Toast.LENGTH_SHORT).show()
            preferenceManager.prefs.sharedPreferences.edit().putInt("lastVersionDBFilesImported", versionToUpdate).apply()

            this@DBUpdateActivity.finish()
        }
    }

    companion object {
        val DB_URL = " https://raw.githubusercontent.com/fattazzo/total-gp-world/master/db/f1db"

        val DOWNLOAD_FILE_NAME = "f1dbnew"
        val DOWNLOAD_PATH = ActiveAndroid.getDatabase().path.removeSuffix("f1db")
    }
}