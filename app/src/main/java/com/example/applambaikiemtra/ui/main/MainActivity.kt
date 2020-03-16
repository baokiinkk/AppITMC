package com.example.applambaikiemtra.ui.main

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.applambaikiemtra.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.main_navigation)
        val navView: NavigationView = findViewById(R.id.nav_view)

        appBarConfiguration = AppBarConfiguration(setOf(
             R.id.fragment_BoMon), drawer_layout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener{
            when(it.itemId)
            {
                R.id.nav_fanpage->getlink("https://www.facebook.com/it.multimedia.club/")
                R.id.nav_facebook->getlink("https://www.facebook.com/baokiin")
                R.id.nav_web->getlink("https://itmc-ptithcm.github.io/")
                R.id.nav_danhgia->
                {
                    val intent:Intent= Intent(Intent.ACTION_SEND);
                    intent.setType("message/rfc822")
                    intent.putExtra(Intent.EXTRA_EMAIL,"baokiinkk@gmail.com")
                    intent.putExtra(Intent.EXTRA_SUBJECT,"-Phản Hồi Đóng Góp Xây Dựng App:")
                    intent.putExtra(Intent.EXTRA_TEXT,"... ")
                    startActivity(Intent.createChooser(intent,"choose an email client"))
                }
            }
             true
        }

    }

    fun getlink(url:String)
    {
        var intent:Intent=Intent(Intent.ACTION_VIEW)
        intent.data= Uri.parse(url)
        startActivity(intent)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.main_navigation)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main2, menu)
        return true
    }

    override fun onBackPressed() {
        if(!findNavController(R.id.main_navigation).popBackStack())
        {
            finish()
        }
    }
}

