package com.dantanini.ajhomework.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dantanini.ajhomework.MyConstants
import com.dantanini.ajhomework.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences(MyConstants.NAME_GIVE_ME_HIGH_SALARY_PLZ, Context.MODE_PRIVATE)
        val msg = sharedPreferences.getString(MyConstants.KEY_GIVE_ME_HIGH_SALARY_PLZ, null)
        if (msg == null) {
            val editor = sharedPreferences.edit()
            editor.putString(MyConstants.KEY_GIVE_ME_HIGH_SALARY_PLZ, getString(R.string.give_me_high_salary))
            editor.apply()
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setMessage(msg)
                .setPositiveButton(R.string.ok_we_will_give_very_high_salary_for_you, null)
                .setCancelable(false)
                .show()
        }
    }
}
