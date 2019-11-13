package net.hulyka.spacexviewer.repository

interface FileDownloadRepository {

    fun downloadFiles(urls: List<String>): List<Long?>

}
