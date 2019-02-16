package com.sample.todo.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.sample.todo.R
import com.sample.todo.databinding.HostActivityBinding
import androidx.lifecycle.observe
import com.sample.todo.util.extension.setupMainNavGraph
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject

class HostActivity : DaggerAppCompatActivity(), HasTopNavigation {
    override fun getNavControllerIdLiveData(): LiveData<Int> {
        return hostViewModel.currentNavControllerId
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val hostViewModel: HostViewModel by viewModels { viewModelFactory }
    private lateinit var binding: HostActivityBinding
    private lateinit var currentController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<HostActivityBinding>(this, R.layout.host_activity)
            .apply {
                viewModel = hostViewModel
                setLifecycleOwner(this@HostActivity)
            }
        setupMainNavGraph(R.navigation.tasks_graph, mapOf(
            R.id.tasks_nav_controller to R.id.tasksFragment,
            R.id.search_nav_controller to R.id.searchFragment,
            R.id.stat_nav_controller to R.id.statisticsFragment,
            R.id.setting_nav_controller to R.id.settingFragment
        ))
        hostViewModel.currentNavControllerId.observe(this) {
            currentController = findNavController(it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        Timber.d("onSupportNavigateUp w/ currentController.graph.id=${currentController.graph.id}")
        return currentController.navigateUp()
    }

    override fun onBackPressed() {
        val poped = currentController.popBackStack()
        Timber.d("onBackPressed poped=$poped")
        if (!poped) finish()
    }

    override fun supportNavigateUpTo(upIntent: Intent) {
        Timber.d("supportNavigateUpTo")
        currentController.navigateUp()
    }

    override fun onTopNavigationSelected(navControllerId: Int) {
        hostViewModel.onTopNavigationSelected(navControllerId)
    }

    override fun getCurrentNavControllerId(): Int {
        return hostViewModel.currentNavControllerId.value
            ?: throw RuntimeException("How can this happen?")
    }
}

interface HasTopNavigation {
    fun onTopNavigationSelected(navControllerId: Int)
    fun getCurrentNavControllerId(): Int
    fun getNavControllerIdLiveData(): LiveData<Int>
}
