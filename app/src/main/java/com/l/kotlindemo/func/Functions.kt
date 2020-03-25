package com.l.kotlindemo.func

import android.view.View
import android.widget.Button
import com.l.kotlindemo.IFace
import com.l.kotlindemo.IFaceImpl
import com.l.kotlindemo.glass.A

class Functions {

    var iFaceImpl: IFaceImpl? = null

    fun testFunc() {

        iFaceImpl = IFaceImpl()
        //非空时调用
        iFaceImpl?.printHello()

        //不管是不是非空都调用
        iFaceImpl!!.printHello()

        //如果空，调用后面
        iFaceImpl?.printHello() ?: Testfor()

        //类型判断
        iFaceImpl is IFace

        //类型强转
        iFaceImpl as IFaceImpl

        //中缀调用
        //FunctionsKt.sameAs("yoy", "yoyo");
        "yoy" sameAs "yoyo"

        //拓展函数
        //FunctionsKt.extend("youo", "yoyo");
        "youo".extend("yoyo")

        //解构    int age = bean.component2();
        var bean = A("yoyo", 29)
        var (_, age) = bean

        //变长参数
        args(1, 2, 3, 4)

        //lambda
        var d: Button = Button(null)
        d.setOnClickListener {
            Testfor()
        }

        //内联函数+高级函数+crossinline
        add(1, 2) { a, b ->
            if (a < 0) {
                return@add 0 //return当前lambda
            }
            return@add a + b
        }

        //内联函数+高级函数+noinline
        var func = del(1, 2) { a, b ->
            if (a < 0) {
                return@del 0 //return当前lambda
//                return 0  将调用函数返回，即返回testFunc
            }
            return@del a - b
        }
        func.invoke(1, 2)
    }

    fun Testfor() {

        //运算符重载 中缀调用
        for (i in 0..10 step 2) {
        }

        for (i in 10 downTo 0) {
        }

//        遍历集合
//        for(string in list){}

    }

    //变长参数
    fun args(vararg arg: Int) {}
}


//中缀调用
infix fun String.sameAs(b: String): Boolean = this.equals(b)

//拓展函数
fun String.extend(b: String): Boolean = this.equals(b)

//inline在编译时，会将代码平铺到调用函数位置
//crossinline修饰参数是高阶函数
inline fun add(
    b: Int, c: Int, crossinline func: (Int, Int) -> Int//不允许外部lambda中断函数执行
) {
    val a = func(b, c)
    print("add result = ${a}")
}

//noinline修饰返回的高阶函数不能return掉调用函数
inline fun del(b: Int, c: Int, noinline l1: (Int, Int) -> Int): (Int, Int) -> Int {

    return l1
}