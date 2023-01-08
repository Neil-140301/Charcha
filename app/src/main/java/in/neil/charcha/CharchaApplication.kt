package `in`.neil.charcha

import `in`.neil.charcha.data.AppContainer
import `in`.neil.charcha.data.DefaultAppContainer
import android.app.Application

class CharchaApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}