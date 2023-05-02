package com.example.angryducks

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

interface Pigobserver {
     fun update()
}