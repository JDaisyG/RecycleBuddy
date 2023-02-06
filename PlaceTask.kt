package com.guanorg.RecycleBuddy.search

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Boolean
import java.net.HttpURLConnection
import java.net.URL

// Asyn Task, which simulates command by running Google place search using HttpURLConnect lib and 
// URL: https://maps.googleapis.com/maps/api/place/textsearch/json?query=recycle%20in%20zip%20code%2001532&key=MY_API_KEY

class PlaceTask ( context: Context, type: Int) : AsyncTask<String?, Int?, String?>(){
    var context: Context =context
    val type : Int = type
    
    //var delegate: AsyncResponse? = null

    
    // String s - JSon format data - returns byt doInBackground(), and in turn it's froM downloadUrl(string:String)
    
    override fun onPostExecute(s: String?) {
        
        //super.onPostExecute(s);
        PlaceParseTask(context, type).execute(s)
        Log.d(
            "Guan: ",
            "onPostExecute %%%%%%%%%%%%%%%%%%%%%%%%%% 1"
        )

        //delegate?.onProcessFinish(s, id);
        Log.d(
            "Guan: ",
            "onPostExecute %%%%%%%%%%%%%%%%%%%%%%%%%% 2"
        )

    }

    override fun doInBackground(vararg params: String?): String? {
        var data: String? = null
        try {
            data = params[0]?.let { downloadUrl (it) 
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return data
    }

    
    // string is url: builds the connection using url
    // build return data on search result, based on url
    // data in JSon format
    
    @Throws(IOException::class)
    private fun downloadUrl(string: String): String? {
        val url = URL(string)
        val connection = url.openConnection() as HttpURLConnection
        
        //connection.setRequestProperty("X-Android-Package","com.guanorg.RecycleBuddy")
        //connection.setRequestProperty("X-Android-Cert","E3:C2:D9:F2:CE:1F:4C:D6:0A:AE:9A:FF:F2:D0:91:E0:CC:4C:96:EF")
        
        connection.connect()
        val stream = connection.inputStream
        val reader = BufferedReader(InputStreamReader(stream))
        val builder = StringBuilder()
        var line: String? = ""
        while (reader.readLine().also { line = it } != null) {
            builder.append(line)
        }
        val data = builder.toString()
        reader.close()
        return data
    }

}
