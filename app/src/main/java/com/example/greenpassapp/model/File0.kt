package com.example.greenpassapp.model

import android.content.Context
import java.io.File

/**
 * a random class for file input and output?
 * i just copied it from my lab
 * hopefully it works in java
 * @see com.example.greenpassapp.model.File
 */
object File0 {
    fun getFile(filename: String, context: Context): File {
        return File(context.filesDir, filename)
    }
    fun createFile(filename: String, context: Context): Boolean {
        return getFile(filename, context).createNewFile()
    }
    fun readFile(filename: String, context: Context): String {
        return context.openFileInput(filename).bufferedReader().useLines {
            it.fold("") { text, add ->
                "$text\n$add"
            }
        }
    }
    fun writeFile(filename: String, context: Context, data: String) {
        return context.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(data.toByteArray())
            it.close()
        }
    }
    fun appendToFile(filename: String, context: Context, data: String) {
        return context.openFileOutput(filename, Context.MODE_APPEND or Context.MODE_PRIVATE).use {
            it.write(data.toByteArray())
            it.close()
        }
    }
    fun appendLineToFile(filename: String, context: Context, data: String) {
        return appendToFile(filename, context, data + "\n")
    }
    fun overwriteLine(filename: String, context: Context, newLine: String, find: String, delimiter: String) {
        val string = readFile(filename, context)
        var ans = ""
        var found = false
        string.split("\n").forEach { s -> if (s != "") if (s.split(delimiter)[0] == find) {
            ans += if (newLine == "") "" else newLine + "\n"
            found = true
        } else ans += "$s\n" }
        return if (!found) appendLineToFile(filename, context, newLine) else writeFile(filename, context, ans)
    }
    fun deleteLine(filename: String, context: Context, find: String, delimiter: String) {
        return overwriteLine(filename, context, "", find, delimiter)
    }
}