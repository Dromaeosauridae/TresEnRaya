package me.pegunto.si.habra.a3enraya

import android.widget.Button

interface ListenerCasilla{
    fun onCasillaPulsada(butt : Button, fil : Int, col : Int)
}