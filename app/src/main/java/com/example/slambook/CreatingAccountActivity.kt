package com.example.slambook

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.slambook.databinding.ActivityCreatingAccountBinding
import com.google.android.material.snackbar.Snackbar

class CreatingAccountActivity: AppCompatActivity() {

    lateinit var binding: ActivityCreatingAccountBinding
    private val userDataList = mutableListOf<UserData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreatingAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Populate the year spinner with dynamically generated years
        val yearsArray = generateYears()
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, yearsArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.birthdayYear.adapter = adapter


        binding.createButton.setOnClickListener {
         saveUserData()
        //    startActivity(Intent(this,SlambookActivity::class.java))
          //  finish()
        }
    }

    // Function to generate the years dynamically
    private fun generateYears(): Array<String> {
        val currentYear = 2024
        val startYear = 1900
        val yearsList = mutableListOf<String>()

        for (year in currentYear downTo startYear) {
            yearsList.add(year.toString())
        }

        return yearsList.toTypedArray()
    }

    private fun saveUserData() {
        // Retrieve input values
        val fullName = binding.fullName.text.toString()
        val nickName = binding.nickName.text.toString()

        // Gender selection logic
        val gender = when {
            binding.genderMale.isChecked -> "Male"
            binding.genderFemale.isChecked -> "Female"
            else -> "Unspecified" // In case no gender is selected
        }
        val email = binding.emailInput.text.toString()
        val contactNumber = binding.passwordInput.text.toString()
        val address = binding.confirmPasswordInput.text.toString()
        val birthMonth = binding.birthdayMonth.selectedItem.toString()
        val birthDay = binding.birthdayDay.selectedItem.toString()
        val birthYear = binding.birthdayYear.selectedItem.toString()

        // Validate inputs
        if (fullName.isEmpty() || email.isEmpty() || contactNumber.isEmpty() || address.isEmpty()) {
            Snackbar.make(binding.root, "Please fill in all required fields.", Snackbar.LENGTH_SHORT).show()
            return
        }

        // Log the user data
        Log.d("CreateActivity", "Creating user: $fullName, $email")

        // Save the data in memory
        val newUser = UserData(fullName, nickName, gender, "$birthMonth $birthDay, $birthYear", email, contactNumber, address)
        userDataList.add(newUser)

        // Show success message
        Toast.makeText(this, "User created successfully!", Toast.LENGTH_SHORT).show()

        // Pass the data to MainActivity
        val intent = Intent(this, SlambookActivity::class.java).apply {
            putExtra("fullName", fullName)
            putExtra("nickName", nickName)
            putExtra("gender", gender)
            putExtra("birthday", "$birthMonth $birthDay, $birthYear")
            putExtra("email", email)
            putExtra("contactNumber", contactNumber)
            putExtra("address", address)
        }

        // Log the intent action
        Log.d("CreateActivity", "Starting MainActivity")

        // Start MainActivity
        startActivity(intent)

        // Clear inputs
        clearInputs()

        // Finish CreateActivity
        finish()
    }

    private fun clearInputs() {
        binding.fullName.text.clear()
        binding.nickName.text.clear()
        binding.emailInput.text.clear()
        binding.passwordInput.text.clear()
        binding.confirmPasswordInput.text.clear()
        binding.genderMale.isChecked = false
        binding.genderFemale.isChecked = false
        binding.birthdayMonth.setSelection(0)
        binding.birthdayDay.setSelection(0)
        binding.birthdayYear.setSelection(0)
    }

}