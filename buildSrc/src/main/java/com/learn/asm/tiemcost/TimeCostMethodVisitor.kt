package com.learn.asm.tiemcost

import com.learn.asm.MyPlugin
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.ASM7
import org.objectweb.asm.Type
import org.objectweb.asm.commons.LocalVariablesSorter

class TimeCostMethodVisitor(
    api: Int,
    mv: MethodVisitor,
    access: Int,
    name: String?,
    descriptor: String?,
) : LocalVariablesSorter(ASM7, access, descriptor, mv), Opcodes {

    var startTime: Int = 0
    var endTime: Int = 0
    var constTime: Int = 0
    var thisMethodStack: Int = 0


    override fun visitCode() {
        super.visitCode()
        //val startTime = System.currentTimeMillis();
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
        startTime = newLocal(Type.LONG_TYPE)
        mv.visitVarInsn(Opcodes.LSTORE, startTime)
    }

    override fun visitInsn(opcode: Int) {
        if (opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) {
            //long endTime = System.currentTimeMillis();
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
            endTime = newLocal(Type.LONG_TYPE)
            mv.visitVarInsn(Opcodes.LSTORE, endTime)

            //long constTime = endTime - startTime;
            mv.visitVarInsn(Opcodes.LLOAD, endTime)
            mv.visitVarInsn(Opcodes.LLOAD, startTime)
            mv.visitInsn(Opcodes.LSUB)
            constTime = newLocal(Type.LONG_TYPE)
            mv.visitVarInsn(Opcodes.LSTORE, constTime)

            //判断costTime是否大于threshold
            mv.visitVarInsn(Opcodes.LLOAD, constTime)
            mv.visitLdcInsn(MyPlugin.threshold)
            mv.visitInsn(Opcodes.LCMP)

            //if constTime <= sThreshold,就跳到end标记处，否则继续往下执行
            val end = Label()
            mv.visitJumpInsn(Opcodes.IFLE, end)

            //StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[0]
            mv.visitTypeInsn(Opcodes.NEW, "java/lang/Exception")
            mv.visitInsn(Opcodes.DUP)
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Exception", "<init>", "()V", false)
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Exception", "getStackTrace", "()[Ljava/lang/StackTraceElement;", false)
            mv.visitInsn(Opcodes.ICONST_0)
            mv.visitInsn(Opcodes.AALOAD)
            thisMethodStack = newLocal(Type.getType(StackTraceElement::class.java))
            mv.visitVarInsn(Opcodes.ASTORE, thisMethodStack)

            //Log.e("rain", String.format（"===> %s.%s(%s:%s)方法耗时 %d ms", thisMethodStack.getClassName(), thisMethodStack.getMethodName(),thisMethodStack.getFileName(),thisMethodStack.getLineNumber(),constTime));
            mv.visitLdcInsn("TimeCost")
            mv.visitLdcInsn("===> %s.%s(%s:%s)\u65b9\u6cd5\u8017\u65f6 %d ms")
            mv.visitInsn(Opcodes.ICONST_5)
            mv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object")
            mv.visitInsn(Opcodes.DUP)
            mv.visitInsn(Opcodes.ICONST_0)
            mv.visitVarInsn(Opcodes.ALOAD, thisMethodStack)
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StackTraceElement", "getClassName", "()Ljava/lang/String;", false)
            mv.visitInsn(Opcodes.AASTORE)
            mv.visitInsn(Opcodes.DUP)
            mv.visitInsn(Opcodes.ICONST_1)
            mv.visitVarInsn(Opcodes.ALOAD, thisMethodStack)
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StackTraceElement", "getMethodName", "()Ljava/lang/String;", false)
            mv.visitInsn(Opcodes.AASTORE)
            mv.visitInsn(Opcodes.DUP)
            mv.visitInsn(Opcodes.ICONST_2)
            mv.visitVarInsn(Opcodes.ALOAD, thisMethodStack)
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StackTraceElement", "getFileName", "()Ljava/lang/String;", false)
            mv.visitInsn(Opcodes.AASTORE)
            mv.visitInsn(Opcodes.DUP)
            mv.visitInsn(Opcodes.ICONST_3)
            mv.visitVarInsn(Opcodes.ALOAD, thisMethodStack)
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StackTraceElement", "getLineNumber", "()I", false)
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false)
            mv.visitInsn(Opcodes.AASTORE)
            mv.visitInsn(Opcodes.DUP)
            mv.visitInsn(Opcodes.ICONST_4)
            mv.visitVarInsn(Opcodes.LLOAD, constTime)
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false)
            mv.visitInsn(Opcodes.AASTORE)
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String", "format", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", false)
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false)
            mv.visitInsn(Opcodes.POP)

            //end标记处，即方法的末尾
            mv.visitLabel(end)
        }
        super.visitInsn(opcode)
    }
}