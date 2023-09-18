package com.mygdx.game

import java.io.File
import java.io.PrintWriter

class JsonFileWriter {
    companion object {
        fun write(fileName: String, objectToWrite: JsonSerialization) {
            val file = createFile(fileName)

            file.printWriter().use { out ->
                out.println(addJSONStart())
                writeObject(out, objectToWrite, false)
                out.println(addJSONEnd())
            }
        }

        // * json trailing comma
        fun <T> write(fileName: String, arrayToWrite: ArrayList<T>, arrayType: String): Boolean {
            val file = createFile(fileName)

            file.printWriter().use { out ->
                out.println(addJSONStart())
                out.println(addArrayStart(arrayType))

                when {
                    arrayToWrite.isNullOrEmpty() -> {
                        out.println(addArrayEnd())
                        out.println(addJSONEnd())
                        return true
                    }

                    arrayToWrite[0] is String -> {
                        arrayToWrite.forEach {
                            out.println(addStringToArray(it as String))
                        }
                    }

                    arrayToWrite[0] is Boolean -> {
                        arrayToWrite.forEach {
                            out.println(addBooleanToArray(it as Boolean))
                        }
                    }

                    arrayToWrite[0] is Number -> {
                        arrayToWrite.forEach {
                            out.println(addNumberToArray(it as Number))
                        }
                    }

                    else -> {
                        arrayToWrite.forEach {
                            writeObject(out, it as JsonSerialization, true)
                        }
                    }
                }

                out.println(addArrayEnd())
                out.println(addJSONEnd())

                return true
            }
        }

        fun writeObject(out: PrintWriter, objectToWrite: JsonSerialization, isArray: Boolean) {
            if (isArray) out.println(addJSONStart())
            else out.println(addObjectStart(objectToWrite))

            objectToWrite.write(out)

            if (isArray) out.println("${addJSONEnd()},")
            else out.println(addJSONEnd())
        }

        private fun createFile(fileName: String): File {
            val path = "F:/floodTest"
            val fullName = "$path/$fileName"

            File(path).mkdirs()
            File(fullName).createNewFile()

            return File(fullName)
        }

        fun addArrayStart(arrayName: String): String {
            return "\"${arrayName}\" : ["
        }

        fun addArrayEnd(): String {
            return "]"
        }

        fun addObjectStart(objectToWrite: Any): String {
            return "\"${objectToWrite.javaClass.simpleName}\" : {"
        }

        fun addJSONStart(): String {
            return "{"
        }

        fun addJSONEnd(): String {
            return "}"
        }

        fun addString(fieldName: String, value: String): String {
            return "\"$fieldName\" : \"$value\","
        }

        fun addBoolean(fieldName: String, value: Boolean): String {
            return "\"$fieldName\" : $value,"
        }

        fun addNumber(fieldName: String, value: Number): String {
            return "\"$fieldName\" : $value,"
        }

        fun addStringToArray(value: String): String {
            return "\"$value\","
        }

        fun addBooleanToArray(value: Boolean): String {
            return "$value,"
        }

        fun addNumberToArray(value: Number): String {
            return "$value,"
        }

        inline fun <reified T> ArrayList<T>.getType(): String = T::class.java.simpleName

        inline fun <reified T : Any> T.logTag(): String = T::class.java.simpleName

//        fun tre(ty: ArrayList<out Any>) {
//            println(ty::class.java.toGenericString())
////            ty.getType()
//        }
    }
}