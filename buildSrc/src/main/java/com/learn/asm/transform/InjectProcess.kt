package com.learn.asm.transform

import com.learn.asm.visitor.InjectClassVisitor
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

object InjectProcess : BytesProcess {

    override fun read(bytes: ByteArray): ByteArray {
        val classReader = ClassReader(bytes)
        val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
        val injectClassVisitor = InjectClassVisitor(classWriter)
        classReader.accept(injectClassVisitor, 0)

        return classWriter.toByteArray()
    }
}