package com.kubik.userappcourse.ui.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kubik.userappcourse.DependenciesDatabase
import com.kubik.userappcourse.R
import com.kubik.userappcourse.databinding.ActivityAuntificationBinding
import com.kubik.userappcourse.ui.base.MainActivity

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuntificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityAuntificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        DependenciesDatabase.init(applicationContext)
    }

    fun goToMainAct() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
