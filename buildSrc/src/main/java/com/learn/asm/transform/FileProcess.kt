package com.learn.asm.transform

import java.io.File

interface FileProcess {
    fun processJar(outputJarFile: File) {}
    fun processDirectory(outputDirFile: File) {}
    fun onTransformed() {}
}