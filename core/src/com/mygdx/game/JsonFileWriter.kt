package com.mygdx.game

import com.mygdx.game.JsonFileWriter.Companion.getType
import com.mygdx.game.entity.Tile
import java.io.File
import java.io.PrintWriter

class JsonFileWriter {
    companion object {
        fun write(fileName: String, objectToWrite: JsonSerialization) {
            val file = createFile(fileName)

            file.printWriter().use { out ->
                out.println(addJSONStart())
                writeObject(out, objectToWrite)
                out.println(addJSONEnd())
            }
        }

        fun <T> write(fileName: String, arrayToWrite: ArrayList<T>, arrayName: String) {
            val file = createFile(fileName)

            file.printWriter().use { out ->
                out.println(addJSONStart())
                out.println(addArrayStart(arrayName))
               // writeObject(out, objectToWrite)
                out.println(addArrayEnd())
                out.println(addJSONEnd())
            }
        }

        private fun createFile(fileName: String) : File {
            val path = "F:/floodTest"
            val fullName = "$path/$fileName"

            File(path).mkdirs()
            File(fullName).createNewFile()

            return File(fullName)
        }

        fun addArrayStart(arrayName: String) : String {
            return "\"${arrayName}\" : ["
        }

        fun addArrayEnd() : String {
            return "]"
        }

        fun addObjectStart(objectToWrite: Any) : String {
            return "\"${objectToWrite.javaClass.simpleName}\" : {"
        }

        fun addJSONStart() : String {
            return "{"
        }
        fun addJSONEnd() : String {
            return "}"
        }

        fun writeObject(out: PrintWriter, objectToWrite: JsonSerialization) {
            out.println(addObjectStart(objectToWrite))
            objectToWrite.write(out)
            out.println(addJSONEnd())
        }

        fun addString(fieldName: String, value: String) : String {
            return "\"$fieldName\" : \"$value\","
        }

        fun addBoolean(fieldName: String, value: Boolean) : String {
            return "\"$fieldName\" : $value,"
        }

        fun addNumber(fieldName: String, value: Number) : String {
            return "\"$fieldName\" : $value,"
        }

        inline fun <reified T> ArrayList<T>.getType(): String = T::class.java.simpleName

        inline fun <reified T : Any>T.logTag(): String = T::class.java.simpleName

//        fun tre(ty: ArrayList<out Any>) {
//            println(ty::class.java.toGenericString())
////            ty.getType()
//        }
    }
}

fun main() {
    val tile = Tile(0f,0f,10f,10f,TileOccupationType.FREE,1)
    val ar = ArrayList<Tile>()
    JsonFileWriter.write("ter2.json", ar, "Tiles")
   // JsonFileWriter.tre(ar)

    ar.getType()
}