package com.example.slambook

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.slambook.databinding.ActivitySlambookBinding

class SlambookActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySlambookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize binding
        binding = ActivitySlambookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup edge-to-edge UI
        setupEdgeToEdge()

        // Initialize navigation components
        setupNavigation()

       handleIncomingIntent()

       passDataToFragment()
    }

    private fun handleIncomingIntent() {
        val fullName = intent.getStringExtra("fullName")
        val nickName = intent.getStringExtra("nickName")
        val gender = intent.getStringExtra("gender")
        val birthday = intent.getStringExtra("birthday")
        val email = intent.getStringExtra("email")
        val contactNumber = intent.getStringExtra("contactNumber")
        val address = intent.getStringExtra("address")

        // Log or use the data as needed
        Log.d("MainActivity", "Received fullName: $fullName")
    }


    private fun setupEdgeToEdge() {
        // Apply window insets for edge-to-edge design
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupNavigation() {
        // Get the NavController from the NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val navController = navHostFragment?.navController

        if (navController != null) {
            // Set up BottomNavigationView with NavController
            binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
                // Log to confirm menu item click
                Log.d("Navigation", "Menu item clicked: ${menuItem.itemId}")

                // Ensure we are navigating to the correct fragment
                when (menuItem.itemId) {
                    R.id.profileFragment -> {
                        navController.navigate(R.id.profileFragment)
                        true
                    }
                    R.id.listFragment -> {
                        navController.navigate(R.id.listFragment)
                        true
                    }
                    R.id.addFragment -> {
                        navController.navigate(R.id.addFragment)
                        true
                    }
                    else -> false
                }
            }

            // Add a listener to log the current destination
            navController.addOnDestinationChangedListener { _, destination, _ ->
                Log.d("Navigation", "Current destination: ${destination.label}")
                Toast.makeText(this, "Current destination: ${destination.label}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "NavHostFragment not found. Check your layout XML.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val navController = navHostFragment?.navController

        if (navController != null && navController.popBackStack()) {
            // Handle back press by popping the back stack
        } else {
            // If no fragments to pop, either exit or move the task to the background
            super.onBackPressed() // Default back press behavior
            // Or use moveTaskToBack(true) if you want to minimize the app instead of exiting
            // moveTaskToBack(true)
        }
    }


    private fun passDataToFragment() {
        val fullName = intent.getStringExtra("fullName")
        val nickName = intent.getStringExtra("nickName")
        val gender = intent.getStringExtra("gender")
        val birthday = intent.getStringExtra("birthday")
        val email = intent.getStringExtra("email")
        val contactNumber = intent.getStringExtra("contactNumber")
        val address = intent.getStringExtra("address")

        // Check if necessary data is missing
        if (fullName.isNullOrEmpty() || email.isNullOrEmpty()) {
            Log.e("SlambookActivity", "Error: Missing necessary data for fragment")
            return
        }

        // Create a Bundle to pass data
        val bundle = Bundle().apply {
            putString("fullName", fullName)
            putString("nickName", nickName)
            putString("gender", gender)
            putString("birthday", birthday)
            putString("email", email)
            putString("contactNumber", contactNumber)
            putString("address", address)
        }

        // Check if fragment is attached to the activity before transaction
        if (!isFinishing) {
            val mainProfileFragment = ProfileFragment().apply {
                arguments = bundle
            }

            // Use NavController for navigation if you are using Navigation Components
            try {
                // Get the NavController if you're using it
                val navController = findNavController(R.id.nav_host_fragment)

                // Navigate to the ProfileFragment
                navController.navigate(R.id.profileFragment, bundle)

            } catch (e: Exception) {
                // Fallback if NavController is not set up
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, mainProfileFragment)
                    .addToBackStack(null) // Add to backstack so user can go back
                    .commit()
            }
        }
    }

}
