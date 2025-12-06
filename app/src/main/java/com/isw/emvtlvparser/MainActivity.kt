package com.isw.emvtlvparser

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.isw.emvtlvparser.adapter.TLVAdapter
import com.isw.emvtlvparser.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TLVAdapter
    private lateinit var hexInput: EditText
    private lateinit var parseButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        hexInput = binding.hexInput
        parseButton = binding.parseButton
        recyclerView = binding.tlvRecycler

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TLVAdapter(emptyList())
        recyclerView.adapter = adapter


        parseButton.setOnClickListener {
            val hex = hexInput.text.toString()
            if (hex.isBlank()) {
                hexInput.error = "Hex input cannot be empty"
                return@setOnClickListener
            }
            try {
                val nodes = TLVParser.parse(hex)
                adapter.updateData(nodes)
            } catch (e: Exception) {
                hexInput.setBackgroundColor(resources.getColor(R.color.pink))
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}