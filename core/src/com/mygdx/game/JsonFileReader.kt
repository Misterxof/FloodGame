package com.mygdx.game

import com.mygdx.game.entity.Tile
import com.mygdx.game.factory.EntityFactory
import java.io.File
import kotlin.reflect.KClass

class JsonFileReader {
    companion object {
        private val bracketsStack = ArrayDeque<Char>()
        private var currentClass: Pair<KClass<*>?, Int> = (null to 0)
        private var value = StringBuilder()
        private var classContent = ArrayList<String>()
        private var isObject = false
        private var isArray = false
        private var factory: EntityFactory? = null
        fun readFile(fileName: String): ArrayList<JsonSerialization> {
            val result = ArrayList<JsonSerialization>()

            val file = File(fileName).useLines {
                it.forEach { str ->
                    if (!isObject)
                        parseRow(str)
                    else {
                        if (currentClass.second == 0) {
                            if (factory == null)
                                factory = EntityFactory.createFactory(currentClass.first!!)

                            factory?.let { fac ->
                                result.add(fac.create(classContent))
                                classContent.clear()
                                currentClass = currentClass.copy(second = 6)
                            }
                            isObject = false
                        } else {
                            classContent.add(str)
                            currentClass = currentClass.copy(second = currentClass.second - 1)
                        }
                    }
                }
            }

            clear()

            return result
        }

        fun parseRow(row: String) {
            if (row.length <= 2)
                parseCharacter(row)
            else {
                for (i in row.indices) {
                    if (row[i] == '"')
                        parseCharacter("\"")
                    else if (bracketsStack.last() == '"') {
                        value.append(row[i])
                    }
                }
            }
        }

        fun parseCharacter(row: String) {
            when {
                row[0] == '{' -> {
                    if (bracketsStack.isNotEmpty()) {
                        isObject = true
                    }
                    bracketsStack.addLast('{')
                }

                row[0] == '}' -> {
                    bracketsStack.addLast('}')
                }

                row[0] == '[' -> {
                    isArray = true
                    bracketsStack.addLast('[')
                }

                row[0] == ']' -> {
                    isArray = false
                    bracketsStack.addLast(']')
                }

                row[0] == '"' -> {
                    if (bracketsStack.last() != '"')
                        bracketsStack.addLast('"')
                    else {
                        checkForType()
                        bracketsStack.removeLast()
                    }
                }

                else -> throw Exception("Unknown character ${row[0]}")
            }
        }

        fun checkForType() {
            when (value.toString()) {
                "Tile" -> {
                    currentClass = (Tile::class to 6)
                    value.clear()
                }
            }
        }

        fun clear() {
            bracketsStack.clear()
            currentClass = (null to 0)
            value.clear()
            classContent.clear()
            isObject = false
            isArray = false
            factory = null
        }
    }
}

enum class Chararcters {
    OBJECT_START, OBJECT_END, ARRAY_START, ARRAY_END
}

fun main() {
    val arr = JsonFileReader.readFile("F:/floodTest/default.json")
    arr.forEach {
        println(it)
    }
}