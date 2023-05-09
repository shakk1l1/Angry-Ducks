package com.example.angryducks

interface Pigdobservable {
    val observers: ArrayList<Pigobserver>
    fun add(observer: Pigobserver) {        //ajoutte un élément à l'observeur
        observers.add(observer)
    }
    fun remove(observer: Pigobserver) {     //retire un élément à l'observeur
        observers.remove(observer)
    }

     fun hasUpdated() {
        observers.forEach { it.update(); }
    }
}