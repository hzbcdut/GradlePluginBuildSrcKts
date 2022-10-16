package com.example.learnasm;


public class A {

    public void hello() {
        String method = Thread.currentThread() .getStackTrace()[2].getMethodName();
        System.out.println("当前执行方法的方法名 " + method);
        // 这个函数里面什么都没写，字节码插桩插入语句
        // 先不用asm生成字节码指令，直接在代码中写看字节码指令是什么
//        long startTime = System.currentTimeMillis();
//        long endTime = System.currentTimeMillis();
//        System.out.println("execute:"+(endTime-startTime)+"ms.");
    }
}
