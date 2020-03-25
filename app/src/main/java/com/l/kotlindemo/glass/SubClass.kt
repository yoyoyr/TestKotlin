package com.l.kotlindemo.glass

class SubClass(var sa:String) : Glass(sa) {

    override var c: String
        get() = super.c
        set(value) {}


    override fun a() {
        super.a()
        C().innerA()
    }



}