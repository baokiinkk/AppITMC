package com.ptithcm.applambaikiemtra.ui.main

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ptithcm.applambaikiemtra.R
import com.google.android.material.navigation.NavigationView
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var appUpdateManager: AppUpdateManager
    private val MY_REQUEST_CODE = 11
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //in-app update
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(this)
        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                // For a flexible update, use AppUpdateType.FLEXIBLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                    AppUpdateType.IMMEDIATE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    MY_REQUEST_CODE)

            }
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // tạo database sql

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
                R.id.nav_fanpage_DeThiPtit->getlink("https://www.facebook.com/dekiemtraptit/")
                R.id.nav_facebook->getlink("https://www.facebook.com/baokiin")
                R.id.nav_web->getlink("https://itmc-ptithcm.github.io/")
                R.id.nav_danhgia->getlink("https://play.google.com/store/apps/details?id=com.ptithcm.applambaikiemtra")
                R.id.nav_capnhat->getlink("https://play.google.com/store/apps/details?id=com.baokiin.uisptit")
                R.id.nav_share->{
                    val intent:Intent= Intent(Intent.ACTION_SEND);
                    val to="baokiinkk@gmail.com"
                    intent.data=Uri.parse("mailto:")
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.ptithcm.applambaikiemtra")
                    startActivity(Intent.createChooser(intent,"choose an mess client"))
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                //Log.d("tncnhan","Update flow failed! Result code: $resultCode")
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }

        }
    }
    override fun onResume() {
        super.onResume()

        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    // If an in-app update is already running, resume the update.
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        MY_REQUEST_CODE
                    )
                }
            }
    }
}

