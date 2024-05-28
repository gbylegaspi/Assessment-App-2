package com.example.todolistapp

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager

class PreferencesActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var radioGroupTheme: RadioGroup
    private lateinit var switchNotifications: Switch
    private lateinit var spinnerLanguage: Spinner
    private lateinit var spinnerDefaultView: Spinner
    private lateinit var spinnerUnits: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        radioGroupTheme = findViewById(R.id.radioGroupTheme)
        switchNotifications = findViewById(R.id.switchNotifications)
        spinnerLanguage = findViewById(R.id.spinnerLanguage)
        spinnerDefaultView = findViewById(R.id.spinnerDefaultView)
        spinnerUnits = findViewById(R.id.spinnerUnits)
        val buttonSave: Button = findViewById(R.id.buttonSave)

        loadPreferences()

        buttonSave.setOnClickListener { savePreferences() }
    }

    private fun loadPreferences() {
        val theme = sharedPreferences.getString("theme", "Light")
        val notifications = sharedPreferences.getBoolean("notifications", true)
        val language = sharedPreferences.getString("language", "English")
        val defaultView = sharedPreferences.getString("default_view", "Main View")
        val units = sharedPreferences.getString("units", "Metric")

        if (theme == "Light") {
            radioGroupTheme.check(R.id.radioLight)
        } else {
            radioGroupTheme.check(R.id.radioDark)
        }

        switchNotifications.isChecked = notifications

        val languageAdapter = ArrayAdapter.createFromResource(this,
            R.array.languages, android.R.layout.simple_spinner_item)
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLanguage.adapter = languageAdapter
        spinnerLanguage.setSelection(languageAdapter.getPosition(language))

        val viewAdapter = ArrayAdapter.createFromResource(this,
            R.array.views, android.R.layout.simple_spinner_item)
        viewAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDefaultView.adapter = viewAdapter
        spinnerDefaultView.setSelection(viewAdapter.getPosition(defaultView))

        val unitsAdapter = ArrayAdapter.createFromResource(this,
            R.array.units, android.R.layout.simple_spinner_item)
        unitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerUnits.adapter = unitsAdapter
        spinnerUnits.setSelection(unitsAdapter.getPosition(units))
    }

    private fun savePreferences() {
        val editor = sharedPreferences.edit()

        val theme = if (radioGroupTheme.checkedRadioButtonId == R.id.radioLight) "Light" else "Dark"
        val notifications = switchNotifications.isChecked
        val language = spinnerLanguage.selectedItem.toString()
        val defaultView = spinnerDefaultView.selectedItem.toString()
        val units = spinnerUnits.selectedItem.toString()

        editor.putString("theme", theme)
        editor.putBoolean("notifications", notifications)
        editor.putString("language", language)
        editor.putString("default_view", defaultView)
        editor.putString("units", units)

        editor.apply()

        Toast.makeText(this, "Preferences Saved", Toast.LENGTH_SHORT).show()
        finish()

        if (theme != sharedPreferences.getString("theme", "Light")) {
            recreate()
        }
    }
}
