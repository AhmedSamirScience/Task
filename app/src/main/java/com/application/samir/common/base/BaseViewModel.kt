package com.application.samir.common.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    abstract fun stop()
    abstract fun start()
}