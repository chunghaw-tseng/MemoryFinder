package com.example.memoryfinder.views

import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.example.memoryfinder.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class OfflineMemoryViewerTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun initTest() {
        val instrumentationRegistry = InstrumentationRegistry.getInstrumentation()

        // TODO Need to do more tests
        if(isConnected(instrumentationRegistry.targetContext)) {
            clickAirplaneMode(
                instrumentationRegistry,
                instrumentationRegistry.targetContext
            )
            returnToApp(
                instrumentationRegistry,
                instrumentationRegistry.targetContext
            )
        }
    }


   @Test
   fun checkNoInternetConnection(){
       onView(withId(R.id.text_error)).check(matches(isDisplayed()))
   }


    // TODO Test not yet finished
    fun returnToApp(instrumentation: Instrumentation, targetContext: Context){
        val device = UiDevice.getInstance(instrumentation)
        device.pressHome()
        device.pressRecentApps()
        val appName = targetContext.getString(targetContext.applicationInfo.labelRes)
        device.findObject(UiSelector().text(appName)).click()
    }



    fun clickAirplaneMode(instrumentation: Instrumentation, targetContext: Context) {
        UiDevice.getInstance(instrumentation).run {
            targetContext.packageManager.getLaunchIntentForPackage("com.android.settings")?.let { intent ->
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                targetContext.startActivity(intent)
                findObject(UiSelector().textContains("Network")).clickAndWaitForNewWindow()
                findObject(UiSelector().textContains("Airplane")).click()
            }
            targetContext.sendBroadcast(Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        }
    }


    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetwork
        val actNw =
            connectivityManager.getNetworkCapabilities(activeNetworkInfo) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}