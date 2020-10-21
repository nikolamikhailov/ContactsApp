package ru.l4gunner4l.contactsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, HolderFragment.newInstance())
            .commit()
    }

    override fun onBackPressed() {}
}