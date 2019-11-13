package net.hulyka.spacexviewer.repository

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment.DIRECTORY_DOWNLOADS
import androidx.core.content.getSystemService
import net.hulyka.spacexviewer.R
import java.util.*
import javax.inject.Inject


class FileDownloadRepositoryImpl @Inject constructor(val context: Context) :
    FileDownloadRepository {


    override fun downloadFiles(urls: List<String>): List<Long?> {
        return urls.map {
            DownloadManager.Request(Uri.parse(it))
                .setTitle(context.getString(R.string.download_image_notification_title))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, "${UUID.randomUUID()}.jpg")
        }.map {
            context.getSystemService<DownloadManager>()?.enqueue(it)
        }
    }

}