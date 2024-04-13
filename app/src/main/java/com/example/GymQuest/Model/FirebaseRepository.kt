package com.example.GymQuest.Model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRepository {

    private val db = FirebaseFirestore.getInstance()
    private val playerCollection = db.collection("users")
    private val questCollection = db.collection("quests")

    fun addPlayer( name: String, strength: Int, dexterity: Int, stamina: Int, health: Int) {
        val playerDocRef = playerCollection.document(name)

        val playerData = hashMapOf(
            "name" to name,
            "strength" to stamina,
            "dexterity" to dexterity,
            "stamina" to stamina,
            "health" to health

        )

        playerDocRef.set(playerData)
            .addOnSuccessListener {
                println("Player document created/updated in Firestore for user: $name")
            }
            .addOnFailureListener { e ->
                println("Error creating/updating player document in Firestore: $e")
            }
    }

    fun addQuest (questName: String, questDesc: String) {
        val questDocRef = questCollection.document(questName)

        val questData = hashMapOf(
            "questName" to questName,
            "questDesc" to questDesc
        )

        questDocRef.set(questData)
            .addOnSuccessListener {
                println("Quest document created/updated in Firestore for quest: $questName")
            }
            .addOnFailureListener { e ->
                println("Error creating/updating quest document in Firestore: $e")
            }

    }
}