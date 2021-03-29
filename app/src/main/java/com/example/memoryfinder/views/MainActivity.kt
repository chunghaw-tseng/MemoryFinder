package com.example.memoryfinder.views

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.memoryfinder.R


class MainActivity : AppCompatActivity() {

    private val TAG : String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        handleIntent(intent)
    }


//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.search_menu, menu)
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        (menu.findItem(R.id.search).actionView as SearchView).apply {
//            setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        }
//
//        return true
//    }
//
//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//        handleIntent(intent)
//    }

//    private fun handleIntent(intent: Intent){
//
//        if (Intent.ACTION_SEARCH == intent.action) {
//            val query = intent.getStringExtra(SearchManager.QUERY)
//            //use the query to search your data somehow
//            Log.d(TAG, "Searched $query")
//            // Obtained search query
//            val bundle = Bundle()
//            bundle.putString("search", query)
//            val fragobj = MemoryViewer()
//            fragobj.setArguments(bundle)
//        }
//    }


}