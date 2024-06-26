package com.example.GymQuest.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.GymQuest.Adapter.QuestAdapter
import com.example.GymQuest.Model.FirebaseRepository
import com.example.GymQuest.Model.Player
import com.example.GymQuest.Model.Quest
import com.example.GymQuest.R

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var questAdapter: QuestAdapter
    private lateinit var firebaseRepository: FirebaseRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        firebaseRepository = FirebaseRepository()

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Pass FirebaseRepository instance to QuestAdapter
        questAdapter = QuestAdapter(emptyList(), firebaseRepository)
        recyclerView.adapter = questAdapter

        val wizard = findViewById<ImageView>(R.id.wizardImage)
        wizard.setOnClickListener {
            val intent = Intent(this, WizardActivity::class.java)
            startActivity(intent)
        }

        val gnome = findViewById<ImageView>(R.id.chefImage)

        gnome.setOnClickListener {
            val intent = Intent(this, DietActivity::class.java)
            startActivity(intent)
        }

        fetchQuests()

        fetchPlayerStats("Parker Hinrichs")
    }

    private fun fetchPlayerStats(playerName: String) {
        firebaseRepository.getPlayer(playerName,
            onSuccess = { players ->
                runOnUiThread {
                    if (players.isNotEmpty()) {
                        val player = players[0] // Assuming player name is unique
                        populatePlayerStats(player)
                    }
                }
            },
            onFailure = { exception ->
                // Handle failure
            }
        )
    }

    private fun populatePlayerStats(player: Player) {
        // Populate EditText fields with player stats
        findViewById<TextView>(R.id.editHealth).setText("  HP: ${player.health}")
        findViewById<TextView>(R.id.editStrength).setText("  Pow: ${player.strength}")
        findViewById<TextView>(R.id.editDex).setText("  Dex: ${player.dexterity}")
        findViewById<TextView>(R.id.editStamina).setText("  STA: ${player.stamina}")
    }

    private fun fetchQuests() {
        firebaseRepository.getAllQuests(
            onSuccess = { quests ->
                runOnUiThread {
                    // Update QuestAdapter's data
                    questAdapter.updateQuests(quests)
                }
            },
            onFailure = { exception ->
                // Handle failure
            }
        )
    }
}