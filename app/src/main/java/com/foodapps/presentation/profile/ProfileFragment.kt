package com.foodapps.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.foodapps.databinding.FragmentProfileBinding
import com.foodapps.presentation.auth.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        observeEditMode()
        observeProfileData()
    }

    private fun observeProfileData() {
        Glide.with(binding.root).load("https://github.com/Nurulaisyah1/Food_Asset/blob/main/img_discount.jpg").into(binding.ivProfile)
        val user = FirebaseAuth.getInstance().currentUser
       if(user != null){
           binding.emailEditText.setText(user.email)
           binding.nameEditText.setText(user.displayName)
           binding.usernameEditText.setText(user.uid)
       }

    }

    private fun setClickListener() {
        binding.btnEdit.setOnClickListener {
            viewModel.changeEditMode()
        }
        binding.btnLogout.setOnClickListener {
            logout() // Panggil fungsi logout saat tombol logout ditekan
        }
    }

    private fun observeEditMode() {
        viewModel.isEditMode.observe(viewLifecycleOwner) {
            binding.emailEditText.isEnabled = it
            binding.nameEditText.isEnabled = it
            binding.usernameEditText.isEnabled = it
        }
    }

    private fun logout() {
        // Implementasi proses logout di sini
        // Misalnya, hapus token autentikasi atau sesi pengguna
        // Kemudian, arahkan pengguna kembali ke halaman login
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        requireActivity().finish()
    }
}
