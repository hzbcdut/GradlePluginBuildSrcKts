package com.learn.asm.tiemcost

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.commons.Method

class TimeCostMethodVisitor2(
    api: Int,
    mv: MethodVisitor,
    access: Int,
    name: String?,
    descriptor: String?,
) : AdviceAdapter(api, mv, access, name, descriptor), Opcodes {

    var startTime: Int = 0
    override fun onMethodEnter() {
        super.onMethodEnter()
//        long startTime = System.currentTimeMillis();
        // asm中提供了操作字节码指令的方法 和字节码的名字一模一样，比如invokeStatic
        invokeStatic(Type.getType("Ljava/lang/System;"), Method("currentTimeMillis", "()J"))
        // 创建本地变量返回本地变量的索引
        startTime = newLocal(Type.LONG_TYPE)
        // 用一个本地变量接收上一步执行的结果
        storeLocal(startTime)
    }

    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)
        //        long endTime = System.currentTimeMillis();
        invokeStatic(Type.getType("Ljava/lang/System;"), Method("currentTimeMillis", "()J"))
        val endTime = newLocal(Type.LONG_TYPE)
        storeLocal(endTime)

        /**
         * System.out.println("execute:"+(endTime-startTime)+"ms.");
         */


        getStatic(Type.getType("Ljava/lang/System;"), "out", Type.getType("Ljava/io/PrintStream;"))
        // new一个实例
        newInstance(Type.getType("Ljava/lang/StringBuilder;"))
        dup()
        // 执行没有任何参数的构造方法
        invokeConstructor(Type.getType("Ljava/lang/StringBuilder;"), Method("<init>", "()V"))
        // 注入一个字符串
        visitLdcInsn("execute:")

        invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"))

        // 3表示之前保存的开始时间，  先加载开始时间，再加载结束时间，再运行一个减法
//        LLOAD 3
//        LLOAD 1
//        LSUB
        loadLocal(endTime)
        loadLocal(startTime)
        math(SUB, Type.LONG_TYPE)

        invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), Method("append", "(J)Ljava/lang/StringBuilder;"))
        visitLdcInsn("ms.")
        invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"))
        invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), Method("toString", "()Ljava/lang/String;"))
        invokeVirtual(Type.getType("Ljava/io/PrintStream;"), Method("println", "(Ljava/lang/String;)V"))

    }

}