package com.example.myfeelings_flores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {


    var jsonFile: JSONFile? = null
    var veryHappy = 0.0F
    var happy = 0.0F
    var neutral = 0.0F
    var sad = 0.0f
    var verysad = 0.0F
    var data: Boolean = false
    var lista = ArrayList<Emociones>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val verySadButton = findViewById<ImageButton>(R.id.verySadButton)
        val sadButton = findViewById<ImageButton>(R.id.sadButton)
        val neutralButton = findViewById<ImageButton>(R.id.neutralButton)
        val happyButton = findViewById<ImageButton>(R.id.happyButton)
        val veryHappyButton = findViewById<ImageButton>(R.id.veryHappyButton)
        val graphVerySad= findViewById<View>(R.id.graphVerySad)
        val guardarButton = findViewById<Button>(R.id.guardarButton)
        val graphVeryHappy = findViewById<View>(R.id.graphVeryHappy)
        val graphHappy = findViewById<View>(R.id.graphHappy)
        val graphNeutral = findViewById<View>(R.id.graphNeutral)
        val graphSad = findViewById<View>(R.id.graphSad)


        jsonFile=JSONFile()

        fetchingData()


        if(!data){
            graphVeryHappy.background = CustomBarDrawable(this, Emociones("Muy feliz", 0.0F, R.color.mustard, veryHappy))
            graphHappy.background = CustomBarDrawable(this, Emociones("Feliz", 0.0F, R.color.orange, happy))
            graphNeutral.background = CustomBarDrawable(this, Emociones("Neutral", 0.0F, R.color.greenie, neutral))
            graphSad.background = CustomBarDrawable(this, Emociones("Triste", 0.0F, R.color.blue, sad))
            graphVerySad.background = CustomBarDrawable(this, Emociones("Muy feliz", 0.0F, R.color.deepBlue, verysad))


        }else{
            Log.d("HAYDATOOOS","aca")
            actualizarGrafica()
            iconoMayoria()
        }


        guardarButton.setOnClickListener {
            guardar()
        }

        veryHappyButton.setOnClickListener {
            veryHappy++
            iconoMayoria()
            actualizarGrafica()
        }
        happyButton.setOnClickListener {
            happy++
            iconoMayoria()
            actualizarGrafica()
        }
        neutralButton.setOnClickListener {
            neutral++
            iconoMayoria()
            actualizarGrafica()
        }
        verySadButton.setOnClickListener {
            verysad++
            iconoMayoria()
            actualizarGrafica()
        }
        sadButton.setOnClickListener {
            sad++
            iconoMayoria()
            actualizarGrafica()
        }

    }

    fun fetchingData() {

        var json: String = jsonFile?.getData(this) ?: ""

        if (json != "") {

            this.data = true
            var jsonArray: JSONArray = JSONArray(json)

            this.lista = parseJson(jsonArray)


            for (i in lista) {
                when (i.nombre) {
                    "Muy feliz" -> veryHappy = i.total
                    "Feliz" -> happy = i.total
                    "Neutral" -> neutral = i.total
                    "Triste" -> sad = i.total
                    "Muy Triste" -> verysad = i.total
                }
            }


        } else {
            this.data = false
        }




    }

    fun iconoMayoria() {

        val icon = findViewById<ImageView>(R.id.icon)


        if (happy > veryHappy && happy > neutral && happy > sad && happy > verysad) {
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_happy))
        }
        if (veryHappy > happy && veryHappy > neutral && veryHappy > sad && veryHappy > verysad) {
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_veryhappy))
        }

        if (neutral > happy && neutral > veryHappy && neutral > sad && neutral > verysad) {
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_neutral))
        }

        if (sad > happy && sad > neutral && sad > veryHappy && sad > verysad) {
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_sad))
        }
        if (verysad > happy && verysad > neutral && verysad > sad && verysad > veryHappy) {
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_verysad))
        }




    }

    fun actualizarGrafica() {

        val graphVerySad= findViewById<View>(R.id.graphVerySad)
        val graphVeryHappy = findViewById<View>(R.id.graphVeryHappy)
        val graphHappy = findViewById<View>(R.id.graphHappy)
        val graphNeutral = findViewById<View>(R.id.graphNeutral)
        val graphSad = findViewById<View>(R.id.graphSad)
        val graph = findViewById<ConstraintLayout>(R.id.graph)


        val total = veryHappy + happy + neutral + verysad + sad

        var pVH: Float = (veryHappy * 100 / total).toFloat()
        var pH: Float = (happy * 100 / total).toFloat()
        var pN: Float = (neutral * 100 / total).toFloat()
        var pS: Float = (sad * 100 / total).toFloat()
        var pVS: Float = (verysad * 100 / total).toFloat()

        Log.d("porcentajes", "very happy" + pVH)
        Log.d("porcentajes", " happy" + pH)
        Log.d("porcentajes", "neutral" + pN)
        Log.d("porcentajes", "sad" + pS)
        Log.d("porcentajes", "very sad" + pVS)


        lista.clear()



        lista.add(Emociones("Muy feliz", pVH, R.color.mustard, veryHappy))
        lista.add(Emociones("Feliz", pH, R.color.orange, happy))
        lista.add(Emociones("Neutral", pN, R.color.greenie, neutral))
        lista.add(Emociones("Triste", pS, R.color.blue, sad))
        lista.add(Emociones("Muy Triste", pVS, R.color.deepBlue, verysad))

        val fondo = CustomCircleDrawable(this, lista)

        graphVeryHappy.background = CustomBarDrawable(this, Emociones("Muy feliz", pVH, R.color.mustard, veryHappy))
        graphHappy.background = CustomBarDrawable(this, Emociones("Feliz", pH, R.color.orange, happy))
        graphNeutral.background = CustomBarDrawable(this, Emociones("Neutral", pN, R.color.greenie, neutral))
        graphSad.background = CustomBarDrawable(this, Emociones("Triste", pS, R.color.blue, sad))
        graphVerySad.background = CustomBarDrawable(this, Emociones("Muy Triste", pVS, R.color.deepBlue, verysad))

        graph.background=fondo

    }

    fun parseJson(jsonArray: JSONArray): ArrayList<Emociones> {


        var lista = ArrayList<Emociones>()


        for (i in 0 .. jsonArray.length()) {
            try{
                val nombre = jsonArray.getJSONObject(i).getString("nombre")
                val porcentaje = jsonArray.getJSONObject(i).getDouble("porcentaje").toFloat()
                val color = jsonArray.getJSONObject(i).getInt("color")
                val total = jsonArray.getJSONObject(i).getDouble("total").toFloat()
                var emocion = Emociones(nombre, porcentaje, color, total)
                Log.d("estos", emocion.toString())
                lista.add(emocion)
            }catch (e: JSONException){
                e.printStackTrace()
            }


        }

        return lista

    }

    fun guardar() {

        var jsonArray: JSONArray = JSONArray()


        var o: Int = 0


        for (i in lista) {
            Log.d("objetos", i.toString())
            var j: JSONObject = JSONObject()
            j.put("nombre", i.nombre)
            j.put("porcentaje", i.porcentaje)
            j.put("color", i.color)
            j.put("total", i.total)
            jsonArray.put(o, j)
            o++

        }

        jsonFile?.saveData(this, jsonArray.toString())

        Toast.makeText(this, "Datos Guardados", Toast.LENGTH_SHORT).show()


    }


}