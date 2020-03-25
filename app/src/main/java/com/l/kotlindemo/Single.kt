package com.l.kotlindemo



class Single private constructor() {

    private object Holder {
        val holder = Single()
    }

    companion object {
        fun get(): Single {
            return Holder.holder
        }
    }
}