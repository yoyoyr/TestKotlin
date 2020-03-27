package com.l.kotlindemo.collection

import com.l.kotlindemo.LoggerUtils
import kotlinx.coroutines.*

class TestCollection {


    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

//            TestList.testArrayList()
            TestMap.testMap()
        }

    }


}

object TestList {

    //无法修改list
    fun testList() {
        val list = listOf("yoyo", 1, true)

        list.forEach {
            println(it.toString())
        }
//            list.add()

        //不可变list转可变
        list.toMutableList().add("hello")

    }

    fun testArrayList() {

        val list = arrayListOf("yoyo", 1, true)

        list.add(false)

        println(list)
    }

    //可以修改的list
    fun testMulitList() {
        val list = mutableListOf<String>("yoho", "hr", "he")

        list.add("hello")

        println(list)

        //返回最后一个符合条件
        list.last {
            if (it.contains("he")) {
                println(it)
            }
            true
        }

        //当所有条件都满足是true
        println(list.all {
            it.contains("h")
        })

        println(list.map {
            it + "---"
        })

        val list2 = mutableListOf<String>("y1", "y2", "y3")

        //合并
        println(list.zip(list2))
    }
}

object TestMap {

    //不可变map
    fun testMap() {
        val map = mapOf("java" to "hello", "kotlin" to "hi", "go" to "hei")

        //map转list
        val tmp = map.map {
            "${it.key}有${it.value}节课"
            1
        }

        //遍历
        for (en in map.entries){
            println(en.key + "  "+en.value)
        }


    }

    fun testHashMap() {
        val map = hashMapOf<String, String>("java" to "hello", "kotlin" to "hi", "go" to "hei")
    }

//    fun testLinkedHashMap() {
//        val map = LinkedHashMap<String, String>("java" to "hello", "kotlin" to "hi", "go" to "hei")
//    }
}