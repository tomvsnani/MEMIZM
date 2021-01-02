package com.example.memeizm

import android.R.attr.*
import android.annotation.SuppressLint
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.memeizm.databinding.ActivityMainBinding
import com.github.chrisbanes.photoview.PhotoView
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolBar()

        loadFragment(MainFragment(),"home",true)


        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.save -> loadFragment(MyCreationsFragment())
                R.id.home-> {
                    if(supportFragmentManager.findFragmentById(R.id.container) !is  MainFragment)
                    supportFragmentManager.popBackStackImmediate(
                        "home",
                        0
                    )
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

//        supportFragmentManager.addOnBackStackChangedListener {
//            Log.d("counttt",supportFragmentManager.backStackEntryCount.toString()+" "+ supportFragmentManager.fragments.size)
//        }


//        val photoView = binding.image
//        photoView.setImageResource(R.drawable.img)
//
//        val matrix = Matrix()
//        photoView.scaleType = ImageView.ScaleType.MATRIX //required
//
//        photoView.setOnTouchListener { v, event ->
//            matrix.postRotate(
//                180f,
//                photoView.drawable.bounds.width() / 2f,
//                photoView.drawable.bounds.height() / 2f
//            )
//            photoView.imageMatrix = matrix
//
//            return@setOnTouchListener true
//
//        }


    }

    fun setUpFragmentsToolbarProperties(
        toolbarText: String,
        isDrawerLocked: Boolean,
        @SuppressLint("UseCompatLoadingForDrawables") drawerIcon: Drawable = resources.getDrawable(R.drawable.ic_baseline_arrow_back_ios_24,null)
    ) {
        this.apply {
            if (isDrawerLocked)
                binding.drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            else
                binding.drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            setToolBarText(toolbarText)
            actionBarDrawerToggle.isDrawerIndicatorEnabled = false
            actionBarDrawerToggle.setHomeAsUpIndicator(drawerIcon)

        }
    }

    private fun setToolBarText(toolbarText: String) {
        binding.titleEditText.setText(toolbarText)
    }

    fun loadFragment(
        mainFragment: Fragment,
        backStackText: String = "",
        addToBackStack: Boolean = true
    ) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(
                    R.id.container, mainFragment
                )
                if (addToBackStack)
                    addToBackStack(backStackText)
                commit()
            }


    }


    private fun setUpToolBar() {
        setSupportActionBar(binding.maintoolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)

        }
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this, binding.drawerlayout, R.string.openDrawer,
            R.string.closeDrawer
        )
        binding.drawerlayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("clickeddd", "activityclcikedd")
        if (item.itemId == android.R.id.home) {
            if (supportFragmentManager.fragments.isNotEmpty() && supportFragmentManager.fragments[0] is MainFragment) {
                if (binding.drawerlayout.isDrawerOpen(GravityCompat.START))
                    binding.drawerlayout.closeDrawer(GravityCompat.START)
                else
                    binding.drawerlayout.openDrawer(GravityCompat.START)
            } else {
                onBackPressed()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if(supportFragmentManager.findFragmentById(R.id.container) !is MainFragment)
        super.onBackPressed()
        else finish()
    }




}