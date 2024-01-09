package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.example.todoapp.databinding.ActivityCreateCardBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class create_card : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCardBinding
    private lateinit var database: myDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "To_Do"
        ).build()

        binding.saveButton.setOnClickListener {
            if (binding.createTitle.text.toString().trim { it <= ' ' }.isNotEmpty()
                && binding.createPriority.text.toString().trim { it <= ' ' }.isNotEmpty()
            ) {
                val title = binding.createTitle.text.toString()
                val priority = binding.createPriority.text.toString()

                // Assuming DataObject.setData() is a function you have created
                DataObject.setData(title, priority)

                // Launch a coroutine to insert the task into the database
                GlobalScope.launch {
                    database.dao().insertTask(Entity(0, title, priority))
                }

                // Launch another coroutine to log the tasks (for testing purposes)
                GlobalScope.launch {
                    Log.i("Kshipra", database.dao().getTasks().toString())
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}