package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_memer.*


class memer : AppCompatActivity() {
    var imgurl:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memer)
        loadmeme()
    }
    private fun loadmeme(){
        progressbar.visibility=View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url="https://meme-api.herokuapp.com/gimme"
// Request a string response from the provided URL.
        val jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->
                imgurl=response.getString( "url")
     Glide.with(this).load(imgurl).listener(object:RequestListener<Drawable>{
         override fun onLoadFailed(
             e: GlideException?,
             model: Any?,
             target: Target<Drawable>?,
             isFirstResource: Boolean
         ): Boolean {
             progressbar.visibility=View.GONE
             return false
         }

         override fun onResourceReady(
             resource: Drawable?,
             model: Any?,
             target: Target<Drawable>?,
             dataSource: DataSource?,
             isFirstResource: Boolean
         ): Boolean {
             progressbar.visibility=View.GONE
             return false
         }
     }
     )
         .into(memeimageview)
            },
            Response.ErrorListener {
                Toast.makeText(this, "SOME ERROR OCCURED!", Toast.LENGTH_LONG).show()
            })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)



    }


    fun sharememe(view: View) {
      val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey ,checkout this meme $imgurl")
       val chooser=Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)
    }
    fun nextmeme(Listenerview: View) {

        loadmeme()
    }
}