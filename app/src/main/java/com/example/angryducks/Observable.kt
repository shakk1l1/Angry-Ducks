package com.example.angryducks

interface Observable {
    val observers: ArrayList<Observer>;
    fun add(observer: Observer) {
        observers.add(observer);
    }
    fun remove(observer: Observer) {
        observers.remove(observer);
    }
    fun hasUpdated() {
        observers.forEach { it.update(); }
    }
}
//observer avec birds et pigs