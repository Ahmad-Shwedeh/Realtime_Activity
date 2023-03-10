package com.example.realtime_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.realtime_activity.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var count = 0
        val database = Firebase.database
        val myRef = database.getReference()
        binding.save.setOnClickListener{
            val name = binding.PersonName.text.toString()
            val id = binding.PersonID.text.toString()
            val age = binding.PersonAge.text.toString()
            val person = hashMapOf(
                "name" to name,
                "id" to id,
                "age" to age,
            )
            myRef.child("Person").child("$count").setValue(person)
            count++
            Toast.makeText(applicationContext,"success",Toast.LENGTH_SHORT).show()
        }

        // Read from the database
        binding.get.setOnClickListener{
            myRef.addValueEventListener(object: ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = snapshot.getValue()
                    //Log.e("********", "onDataChange: "+value.toString() )
                    binding.textView.text = value.toString()
                    Toast.makeText(applicationContext,"success",Toast.LENGTH_SHORT).show()

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext,"fail",Toast.LENGTH_SHORT).show()

                }


            })
        }
    }

}