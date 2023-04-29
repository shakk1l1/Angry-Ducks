package com.example.angryducks

interface Objectobservable {
    val observers: ArrayList<Objectobserver>;
    fun add(observer: Objectobserver) {
        observers.add(observer);
    }
    fun remove(observer: Objectobserver) {
        observers.remove(observer);
    }
    fun hasUpdated() {
        observers.forEach { it.update(); }
    }
}
//observer avec birds et pigs