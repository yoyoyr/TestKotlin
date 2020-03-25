package com.l.kotlindemo

class SubClass(val sup: IFace) : IFace by sup {

    companion object {
        var a: String = ""
        fun get() {

        }
    }


    fun add(){

    }

    var a:Int = 0
    val b:Int = 0

}