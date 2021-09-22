package com.softaai.simpleloginapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.softaai.simpleloginapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)

        mViewBinding.helloUsername.text =
            getString(R.string.hello, intent.getStringExtra("username"))

    }
}