package mx.lifehealthsolutions.myhealthjournal.interfaces

import android.widget.SpinnerAdapter

interface DownloadedDataListener {

    fun didFinishDownload(adapter: SpinnerAdapter)

}
