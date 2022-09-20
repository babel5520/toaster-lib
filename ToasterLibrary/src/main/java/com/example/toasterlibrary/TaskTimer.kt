package com.example.toasterlibrary

object TaskTimer {
    private var lastMillis = System.currentTimeMillis()
    private var task = 0
    fun log(){
        task++
        val currentTimeMillis = System.currentTimeMillis()
        println("time lapse  ${getClassNameMethodNameAndLineNumber()}  $task : ${currentTimeMillis - lastMillis}")
        lastMillis = currentTimeMillis

    }
    private fun getLineNumber(): Int {
        return Thread.currentThread().stackTrace[5].lineNumber
    }
    /**
     * Get the current class name. Note, this will only work as called from this
     * class as it has to go a predetermined number of steps up the stack trace.
     * In this case 5.
     *
     * @author kvarela
     * @return String - Current line number.
     */
    private fun getClassName(): String? {
        val fileName = Thread.currentThread().stackTrace[5].fileName
        // kvarela: Removing ".java" and returning class name
        return fileName.substring(0, fileName.length - 5)
    }
    private fun getMethodName(): String {
        return Thread.currentThread().stackTrace[5].methodName
    }
    /**
     * Returns the class name, method name, and line number from the currently
     * executing log call in the form .()-
     *
     * @author kvarela
     * @return String - String representing class name, method name, and line
     * number.
     */
    private fun getClassNameMethodNameAndLineNumber(): String? {
        return "[" + getClassName() + "." + getMethodName() + "()-" + getLineNumber() + "]: "
    }
}