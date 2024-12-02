package com.example.slambook
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.slambook.databinding.FragmentProfileBinding

class ProfileFragment: Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    // This method is used to inflate the view and initialize the binding.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment and initialize the binding
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve data from the arguments
        val fullName = arguments?.getString("fullName")
        val nickName = arguments?.getString("nickName")
        val gender = arguments?.getString("gender")
        val birthday = arguments?.getString("birthday")
        val email = arguments?.getString("email")
        val contactNumber = arguments?.getString("contactNumber")
        val address = arguments?.getString("address")

        // Set the data to the views using binding
        binding.fullNameText.text = "Full Name: $fullName"
        binding.nickNameText.text = "Nickname: $nickName"
        binding.genderText.text = "Gender: $gender"
        binding.birthdayText.text = "Birthday: $birthday"
        binding.emailText.text = "Email: $email"
        binding.contactNumberText.text = "Contact Number: $contactNumber"
        binding.addressText.text = "Address: $address"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clean up the binding reference
        _binding = null
    }

}