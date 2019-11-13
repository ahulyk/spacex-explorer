package net.hulyka.spacexviewer.ui

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.hulyka.spacexviewer.R
import net.hulyka.spacexviewer.setupWithNavController

class MainActivity : DaggerAppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Wait for restore state...
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar() //...and init after state is restored
    }

    private fun setupBottomNavigationBar() {

        val navGraphIds = listOf(R.navigation.home, R.navigation.chart)

        //TODO https://issuetracker.google.com/issues/80029773
        val controller = bottomNavView.setupWithNavController(
            navGraphIds, supportFragmentManager, R.id.nav_host_container, intent
        )

        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }


}
