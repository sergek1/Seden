package com.tin.ui.main.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast

import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.tin.databinding.LastLayoutBinding
import com.tin.ui.main.`interface`.Post_request
import kotlinx.coroutines.*
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates


class Last() : Fragment() {

    private  var id_post : ArrayList<String> = ArrayList()
    private lateinit var index_post : String
    private var index_number = 1
    private var index_true = 1

    private lateinit var binding: LastLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LastLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parsePost("random")

        binding.buttonNext.setOnClickListener {
            if (index_number == 1) {
//            Toast.makeText(context, "Next", Toast.LENGTH_SHORT).show()
                parsePost("random")
//                context?.let { it1 -> hasNetwork(it1) }
            } else{
                if (index_number != 0  && index_true + 1  < id_post.size ){
                    index_true ++
                    index_number --
                    index_post =  id_post[index_true]
                    parsePost_old(index_post)
                }else{
                    index_number = 0
                }

            }

        }
        binding.buttonBack.setOnClickListener{
            if (id_post.size > 1 && index_number  < id_post.size) {
                index_number ++
                index_true = id_post.size - index_number
                index_post = id_post[index_true]
                parsePost_old(index_post)
            }


//            Toast.makeText(context, "Back", Toast.LENGTH_SHORT).show()
        }
    }


    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }


    fun checkConnectivity(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        if(activeNetwork?.isConnected!=null){
            return activeNetwork.isConnected
        }
        else{

            return false
        }
    }
    private fun parsePost(idd : String) {
        val cacheSize = (1000 *1024 *1024).toLong()
        val myCache = Cache(context?.cacheDir, cacheSize)

        val okHttpClient = OkHttpClient.Builder()
                .cache(myCache)
                .addInterceptor { chain ->
                    var request = chain.request()
                    request = if (context?.let { checkConnectivity(it) }!!)
                        request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                    else
                        request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                    chain.proceed(request)
                }
                .build()

        if (context?.let { checkConnectivity(it) } == false){
            Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show()
        }

        val request_post = "$idd?json=true"

        // Create Retrofit
        val retrofit = Retrofit.Builder()
                .baseUrl("https://developerslife.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        // Create Service
        val service = retrofit.create(Post_request::class.java)

        CoroutineScope(Dispatchers.IO).launch {

            // Do the GET request and get response
            val response = service.getcompany(request_post)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val description_1 = response.body()?.description ?: "No information"
                    binding.titleText.text = description_1

                    val previewURL_1 = response.body()?.previewURL ?: "No information"
                    if (previewURL_1 != "") {
                        Picasso.get().load(previewURL_1).into(binding.imageURL)

                    }else{

                    }
                    val id_1 = response.body()?.id ?: "No information"
                    id_post.add(id_1)
                    index_post = id_post[id_post.size - 1 ]
                    Toast.makeText(context, "${id_post.size}, $index_post", Toast.LENGTH_SHORT).show()
                    val gifURL_1 = response.body()?.gifURL ?: "No information"

                        Glide.with(requireContext())
                                .asGif()
                                .load(gifURL_1)
                                .into(binding.imageURL)

//                    linearLayout.addView(imageView)
                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }
    private fun parsePost_old(idd : String) {

        val cacheSize = (100 *1024 *1024).toLong()
        val myCache = Cache(context?.cacheDir, cacheSize)

        val okHttpClient = OkHttpClient.Builder()
                .cache(myCache)
                .addInterceptor { chain ->
                    var request = chain.request()
                    request = if (context?.let { checkConnectivity(it) }!!)
                        request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                    else
                        request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                    chain.proceed(request)
                }
                .build()
        val request_post = "$idd?json=true"

        // Create Retrofit
        val retrofit = Retrofit.Builder()
                .baseUrl("https://developerslife.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        // Create Service
        val service = retrofit.create(Post_request::class.java)

        CoroutineScope(Dispatchers.IO).launch {

            // Do the GET request and get response
            val response = service.getcompany(request_post)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val description_1 = response.body()?.description ?: "No information"
                    binding.titleText.text = description_1

                    val previewURL_1 = response.body()?.previewURL ?: "No information"
                    if (previewURL_1 != "") {
                        Picasso.get().load(previewURL_1).into(binding.imageURL)
                    }else{
                    }
//                    Toast.makeText(context, "${id_post.size}, $index_post", Toast.LENGTH_SHORT).show()
                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }
}

