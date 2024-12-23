package com.example.myapplication

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.InputActivityBinding

class InputActivity : AppCompatActivity() {

    private lateinit var binding: InputActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = InputActivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.blood_types,
            android.R.layout.simple_list_item_1
        )

        binding.birthdateLayer.setOnClickListener {
            val listener = OnDateSetListener{ _, year, month, dayOfMonth ->
                binding.birthDateTextView.text = "$year-${month.inc()}-$dayOfMonth"
            }
            DatePickerDialog(
                this,
                listener,
                1995,
                3,
                19
            ).show()
        }

        binding.warningCheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.warningEditText.isVisible = isChecked
        }

        binding.warningEditText.isVisible = binding.warningCheckBox.isChecked

        binding.saveButton.setOnClickListener {
            saveData()
            finish()
        }

    }

    private fun saveData() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE).edit()) {
            putString(NAME, binding.nameEditTextView.text.toString())
            putString(BLOOD_TYPE, getBloodType())
            putString(EMERGENCY, binding.emergencyContactEditText.text.toString())
            putString(BIRTHDATE, binding.birthDateTextView.text.toString())
            putString(WARNING, getWarning())
            apply()
        }

        Toast.makeText(this, "Saving is compelete", Toast.LENGTH_SHORT).show()
    }

    private fun getBloodType(): String {
        val bloodAlphabet = binding.bloodTypeSpinner.selectedItem.toString()
        val bloodSign = if (binding.bloodTypePlus.isChecked) "+" else "-"
        return "$bloodSign$bloodAlphabet"
    }

    private fun getWarning(): String {
        return if (binding.warningCheckBox.isChecked) binding.warningEditText.text.toString() else ""
    }
}