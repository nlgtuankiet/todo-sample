package com.sample.todo.dynamic.seeddatabase.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.sample.todo.dynamic.seeddatabase.R
import com.sample.todo.dynamic.seeddatabase.databinding.SeedDatabaseActivityBinding
import javax.inject.Inject

class SeedDatabaseActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: SeedDatabaseViewModel.Factory
    private lateinit var binding: SeedDatabaseActivityBinding
    private val seedDatabaseViewModel: SeedDatabaseViewModel by lazy {
        ViewModelProviders
            .of(this@SeedDatabaseActivity, viewModelFactory)
            .get(SeedDatabaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        SeedDatabaseActivityComponent(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.seed_database_activity)
        binding.apply {
            viewModel = seedDatabaseViewModel
        }
    }
}
