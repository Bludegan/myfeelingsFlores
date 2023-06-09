package com.example.myfeelings_flores

import android.content.Context
import android.util.Log
import java.io.IOException

class JSONFile {
    val MY_FEELINGS = "data.json"

    constructor() {

    }

    fun saveData(context: Context, json: String) {
        try {
            context.openFileOutput(MY_FEELINGS, Context.MODE_PRIVATE).use {
                it.write(json.toByteArray())
                Log.d("ENTROOO",json
                )
            }
        } catch (e: IOException) {
            Log.e("Guardar", "Error in writing" + e.localizedMessage)
        }
    }

    fun getData(context: Context):String{
        return try{
            context.openFileInput(MY_FEELINGS).bufferedReader().readLine()
        } catch (e: IOException){
            Log.e("Obtener", "Error in fetching data" + e.localizedMessage)
            ""
        }
    }
}