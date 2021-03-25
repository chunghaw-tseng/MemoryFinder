package com.example.memoryfinder.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.memoryfinder.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if(savedInstanceState == null){
//            supportFragmentManager.commit {
//                setReorderingAllowed(true)
//                add<MainFragment>(R.id.fragment_container_view)
//            }
//        }
        setContentView(R.layout.activity_main)
    }
}