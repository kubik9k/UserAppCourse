package com.kubik.userappcourse.ui.authentication.sign_out

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kubik.userappcourse.R
import com.kubik.userappcourse.data.user_data.UserRepositoryImpl
import com.kubik.userappcourse.databinding.FragmentSignOutBinding
import com.kubik.userappcourse.ui.authentication.models.UserModel
import com.kubik.userappcourse.ui.checkNotEmptyEditText
import com.kubik.userappcourse.ui.showToast

class SignOutFragment : Fragment() {

    private lateinit var binding: FragmentSignOutBinding
    private val viewModel by lazy { ViewModelProvider(this).get(SignOutViewModel::class.java) }
    private val userRepository by lazy { UserRepositoryImpl() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSignOutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initField()
    }

    private fun initField() {
        val context = binding.root.context
        viewModel.isSuccessfulSignUp.observeForever {
            if (it) {
                showToast(context, getString(R.string.toast_user_successful_register))
                goToBackFragment()
            } else {
                showToast(context, getString(R.string.toast_user_is_not_registered))
            }
        }
        viewModel.exitsLogin.observeForever {
            if (it != 0) {
                showToast(context, getString(R.string.toast_login_already_exists))
            }
        }
    }

    private fun initView() {
        binding.apply {
            btnBack.setOnClickListener {
                goToBackFragment()
            }
            btnSignUp.setOnClickListener {
                onClickBtnSignOut()
            }
        }
    }

    private fun onClickBtnSignOut() {
        checkNotEmptyAllEditText {
            binding.apply {
                val user = UserModel(
                    login = editTextEnterLogin.text.toString(),
                    password = editTextEnterPassword.text.toString(),
                    first_name = editTextEnterFirstName.text.toString(),
                    last_name = editTextEnterLastName.text.toString()
                )
                viewModel.registerUser(user, userRepository)
            }
        }
    }

    private fun checkNotEmptyAllEditText(successful: () -> Unit) {
        binding.apply {
            if (checkNotEmptyEditText(editTextEnterFirstName)) {
                if (checkNotEmptyEditText(editTextEnterLastName)) {
                    if (checkNotEmptyEditText(editTextEnterLogin)) {
                        if (checkNotEmptyEditText(editTextEnterPassword)) {
                            successful()
                        } else showToast(root.context, getString(R.string.please_enter_password))
                    } else showToast(root.context, getString(R.string.please_enter_login))
                } else showToast(root.context, getString(R.string.please_enter_last_name))
            } else showToast(root.context, getString(R.string.please_enter_first_name))
        }
    }

    private fun goToBackFragment() {
        if (!findNavController().popBackStack())
            findNavController().navigate(R.id.action_signOutFragment_to_signInFragment)
    }

}