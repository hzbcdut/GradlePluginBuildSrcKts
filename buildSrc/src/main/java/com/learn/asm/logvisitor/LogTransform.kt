package com.learn.asm.logvisitor

import com.learn.asm.BaseTransform
import com.learn.asm.transform.FileProcess
import com.learn.asm.transform.convertToFileProcess

class LogTransform : BaseTransform(), FileProcess by LogProcess.convertToFileProcess()