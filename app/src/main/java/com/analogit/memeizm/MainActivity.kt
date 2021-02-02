package com.analogit.memeizm

import android.R.attr.*
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.analogit.memeizm.Databases.MainEntityDao
import com.analogit.memeizm.Databases.MyDatabase
import com.analogit.memeizm.Fragments.*
import com.analogit.memeizm.Models.MainContentModel
import com.analogit.memeizm.Models.TextPropertiesModelClass
import com.analogit.memeizm.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import kotlin.coroutines.coroutineContext
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var retrofitInterface: RetrofitInterface
    public var editingImageBitmap: Bitmap? = null
    public var modelThatIsBeingEdited: MainContentModel? = null

    var g = Delegates.observable(0, { property, oldValue, newValue ->
        Log.d("propertyyy", property.toString() + "  " + oldValue + "  " + newValue)
    })
    var s by g

    var h by g
    lateinit var textChangeCallback: (TextPropertiesModelClass) -> Unit
    lateinit var imageChangeCallBack: (String) -> Unit
    var differentTemplateShapesList = listOf<Int>(
        R.layout.customizable_shape_1,
        R.layout.customizable_shape_2,
        R.layout.customizable_shape_3,
        R.layout.customizable_shape_4,
        R.layout.customizable_shape_5,
        R.layout.customizable_shape_6,
        R.layout.customzable_shape_7,
        R.layout.customizable_shape_8
    )

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolBar()
        h=10


        loadFragment(MainFragment(), "home", true)

        buildRetrofitInterface()




        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.profile -> loadFragment(MyTemplatesFragment())
                R.id.home -> {
                    if (supportFragmentManager.findFragmentById(R.id.container) !is MainFragment)
                        supportFragmentManager.popBackStackImmediate(
                            "home",
                            0
                        )
                }
                R.id.search -> {
                    loadFragment(SearchFragment())
                }
                R.id.save -> {
                    h++
                    loadFragment(SavedTemplateFragment())


                }
                R.id.add -> {
                    s++
                    loadFragment(DifferentTemplateShapesFragment())
                }


            }
            return@setOnNavigationItemSelectedListener true
        }


    }

    fun makeProgressVisible(){
        binding.progressbarcard.visibility=View.VISIBLE
    }

    fun makeProgressInvisible(){
        binding.progressbarcard.visibility=View.GONE
    }

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(Constants.BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
        }.build()

    }

    private fun buildRetrofitInterface(): RetrofitInterface {
        retrofitInterface = buildRetrofit().create(RetrofitInterface::class.java)
        return retrofitInterface
    }


    fun showToast(str:String){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show()
    }


    fun setUpFragmentsToolbarProperties(
        toolbarText: String,
        isDrawerLocked: Boolean,
        drawerIcon: Drawable = ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_baseline_arrow_back_ios_24, null
        )!!
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
        binding.titleEditText.text = toolbarText
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
            openCloseDrawerLayout()

        }
        return super.onOptionsItemSelected(item)
    }

    private fun openCloseDrawerLayout() {
        if (supportFragmentManager.fragments.isNotEmpty() && binding.drawerlayout.getDrawerLockMode(
                GravityCompat.START
            ) != DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        ) {
            if (binding.drawerlayout.isDrawerOpen(GravityCompat.START))
                binding.drawerlayout.closeDrawer(GravityCompat.START)
            else
                binding.drawerlayout.openDrawer(GravityCompat.START)
        } else {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentById(R.id.container) !is MainFragment)
            super.onBackPressed()
        else finish()

    }


    fun insert(mainContentModel: MainContentModel) {
        CoroutineScope(Dispatchers.IO).launch {
            try {


                var i = mainEntityDao()
                    .insert(mainContentModel)

                if (i != (-1).toLong()) {

                    showToast1("Inserted ")

                } else {


                    if (update(mainContentModel) > 0)

                        showToast1("updated ")

                }
            } catch (e: Exception) {
                showToast1(e.toString())
            }


        }
    }

    private suspend fun showToast1(s: String) {
        Dispatchers.Main.dispatch(coroutineContext) {
            Toast.makeText(this@MainActivity, s, Toast.LENGTH_SHORT).show()
        }
    }

    fun delelte(mainContentModel: MainContentModel) {
        CoroutineScope(Dispatchers.IO).launch {
            mainEntityDao()
                .delete(mainContentModel)
        }
    }

    private fun update(mainContentModel: MainContentModel): Int {
        Log.d("yess", "okk")
        return mainEntityDao()
            .update(mainContentModel)

    }


    fun getTemplatesInProgress(): LiveData<List<MainContentModel>> {
        Log.d("yess", "okk")
        return mainEntityDao()
            .getMyTemplates()

    }

    fun getDownloads(): LiveData<List<MainContentModel>> {
        Log.d("yess", "okk")
        return mainEntityDao()
            .getMyDownloads()
    }

    fun getTotal(): LiveData<List<MainContentModel>> {
        Log.d("yess", "okk")
        return mainEntityDao()
            .getTotal()
    }


    fun getSavedItems(): LiveData<List<MainContentModel>> {
        Log.d("yess", "okk")
        return mainEntityDao()
            .getMySavedItems()


    }

    fun getFavItems(): LiveData<List<MainContentModel>> {
        Log.d("yess", "okk")
        return mainEntityDao()
            .getMyFavItems()


    }


    private fun mainEntityDao(): MainEntityDao {

        return MyDatabase.getInstance(this).dao
    }


    override fun onStart() {
//      databasee=  MyDatabase.getInstance(applicationContext)
        super.onStart()
        binding.progressbarcard.setOnTouchListener { v, event ->
            return@setOnTouchListener true
        }
    }

    fun View.saveBitmapToMemory() {
        var bitmap = convertToBitmapWithBg()
        try {


            var outputStream = File.createTempFile(
                "okkk",
                "yess" + ".png",

                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            ).let {

                FileOutputStream(it)
            }

            if (bitmap?.compress(
                    Bitmap.CompressFormat.PNG,
                    100,
                    outputStream
                )!!
            ) Toast.makeText(context, "Successfully Saved the Image to storage", Toast.LENGTH_LONG)
                .show()
            else
                Toast.makeText(
                    context,
                    "Sorry could not save the image",
                    Toast.LENGTH_SHORT
                )
                    .show()

        } catch (e: java.lang.Exception) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun View.convertToBitmapWithBg(): Bitmap? {

        var bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bitmap)
        if (background != null && background is BitmapDrawable)
            canvas.drawBitmap(
                (background as BitmapDrawable).bitmap,
                0f,
                0f,
                null
            )
        else if (background != null && background is ColorDrawable)
            canvas.drawColor((background as ColorDrawable).color)
        else
            setBackgroundColor(Color.WHITE)
        draw(canvas)
        return bitmap
    }


}