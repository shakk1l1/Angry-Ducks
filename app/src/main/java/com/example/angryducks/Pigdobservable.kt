package com.example.angryducks

interface Pigdobservable {
    val observers: ArrayList<Pigobserver>
    fun add(observer: Pigobserver) {
        observers.add(observer)
    }
    fun remove(observer: Pigobserver) {
        observers.remove(observer)
    }

     fun hasUpdated() {
        observers.forEach { it.update(); }
    }
}