package com.gauravbajaj.reelly

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Custom [Application] class for the Reelly application.
 *
 * This class is annotated with `@HiltAndroidApp` to enable Hilt dependency injection
 * throughout the application.
 */
@HiltAndroidApp
class MyApplication : Application()
