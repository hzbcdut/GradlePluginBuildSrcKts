package com.learn.asm.visitor;

import static com.learn.asm.UtilsKt.pluginLog;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LifecycleMethodVisitor extends MethodVisitor {
    private String className;
    private String methodName;

    public LifecycleMethodVisitor(MethodVisitor methodVisitor, String className, String methodName) {
        super(Opcodes.ASM5, methodVisitor);
        this.className = className;
        this.methodName = methodName;
    }

    //方法执行前插入
    @Override
    public void visitCode() {
        super.visitCode();
        pluginLog("字节码插桩 MethodVisitor visitCode------  mv " + mv);

//        mv.visitLdcInsn("TAG");
//        mv.visitLdcInsn(className + "---->" + methodName);
//        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
//        mv.visitInsn(Opcodes.POP);

//        将这行代码转换成ASM Code
//        Log.i("MainActivity", "onCreate:  insert bytecode ")
        mv.visitLdcInsn("MainActivity");
        mv.visitLdcInsn("onCreate:  insert bytecode ");
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(Opcodes.POP);
        mv.visitEnd();
    }
}