package com.kubik.userappcourse.ui.base.profile_base.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kubik.userappcourse.DependenciesDatabase
import com.kubik.userappcourse.R
import com.kubik.userappcourse.databinding.FragmentProfileBinding
import com.kubik.userappcourse.ui.authentication.models.UserModel
import com.kubik.userappcourse.ui.base.MainActivity
import com.kubik.userappcourse.ui.base.profile_base.edit_profile.EditProfileFragment

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by lazy { ViewModelProvider(this).get(ProfileViewModel::class.java) }
    private var user = UserModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initField()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getUserData(DependenciesDatabase.daoUser)
    }

    private fun initField() {
        viewModel.userData.observeForever {
            user = it
            binding.apply {
                firstName.text = it.first_name
                lastName.text = it.last_name
                textLogin.text = it.login
                loading.visibility = View.GONE
                containerText.visibility = View.VISIBLE
            }
        }
        viewModel.exit.observeForever {
            (activity as MainActivity).goToAuthenticationActivity()
        }
    }

    private fun initView() {
        binding.apply {
            btnExit.setOnClickListener {
                viewModel.exitUser(DependenciesDatabase.daoUser)
            }
            btnEditProfile.setOnClickListener {
                try {
                    val bundle = Bundle()
                    bundle.putParcelable(EditProfileFragment.KEY_RECEPTION_DATA, user)
                    findNavController().navigate(
                        R.id.action_profileFragment_to_editProfileFragment, bundle
                    )
                } catch (e: Exception) {
                    Log.e("MyLog", "ProfileFragment navigate: ${e.message}")
                }
            }
        }
    }
}