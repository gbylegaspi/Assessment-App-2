package com.example.todolistapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private val taskList = mutableListOf<Task>()
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val theme = sharedPreferences.getString("theme", "Light")
        applyTheme(theme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskAdapter = TaskAdapter(taskList)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter

        val editTextTask: EditText = findViewById(R.id.editTextTask)
        val buttonAdd: Button = findViewById(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            val taskName = editTextTask.text.toString()
            if (taskName.isNotEmpty()) {
                val task = Task(taskName)
                taskList.add(task)
                taskAdapter.notifyItemInserted(taskList.size - 1)
                editTextTask.text.clear()
            }
        }

        val buttonPreferences: Button = findViewById(R.id.buttonPreferences)
        buttonPreferences.setOnClickListener {
            val intent = Intent(this, PreferencesActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadPreferences()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_instructions -> {
                showInstructionsDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showInstructionsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Instructions")
            .setMessage(
                "Welcome to the To-Do List app!\n\n" +
                        "Here's how you can use this app effectively:\n\n" +
                        "1. **Adding a Task**: To add a new task, type your task into the text field at the top of the screen and press the 'Add' button. Your task will appear in the list below.\n\n" +
                        "2. **Marking a Task as Completed**: To mark a task as completed, tap on the checkbox next to the task. This will mark the task as done, and you can uncheck it if you need to mark it as incomplete again.\n\n" +
                        "3. **Deleting a Task**: To delete a task, you can long-press on the task in the list, and a delete option will appear. Confirm the deletion to remove the task from your list.\n\n" +
                        "4. **Viewing Preferences**: You can customize your app experience by pressing the 'Preferences' button. Here you can change the app theme, enable or disable notifications, choose your preferred language, set your default view, and select your measurement units.\n\n" +
                        "5. **Saving Preferences**: Any changes made in the preferences screen will be saved automatically when you press the 'Save' button. These settings will be applied immediately and remembered the next time you open the app.\n\n" +
                        "6. **Theme Selection**: Choose between Light and Dark themes from the preferences screen to suit your viewing comfort. The theme change will be applied immediately.\n\n" +
                        "7. **Notifications**: Enable or disable notifications to receive reminders about your tasks. This can be customized in the preferences screen.\n\n" +
                        "8. **Language Preference**: Select your preferred language from the available options in the preferences screen.\n\n" +
                        "9. **Default View**: Set which view the app should open on startup, either the main view or the favorites view, as per your preference.\n\n" +
                        "10. **Measurement Units**: Choose between metric and imperial units for any measurement-related tasks.\n\n" +
                        "We hope you find this app helpful and easy to use. If you have any questions or need further assistance, please refer to the help section or contact our support team.\n\n" +
                        "Thank you for using the To-Do List app!"
            )
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun loadPreferences() {
        val theme = sharedPreferences.getString("theme", "Light")
        applyTheme(theme)
    }

    private fun applyTheme(theme: String?) {
        when (theme) {
            "Light" -> setTheme(R.style.AppTheme_Light)
            "Dark" -> setTheme(R.style.AppTheme_Dark)
        }
    }
}
