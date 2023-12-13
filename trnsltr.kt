import java.io.File

lateinit var miniN: File
lateinit var kotlin: File
lateinit var code: List<String>
val lineNames = mutableMapOf<String, Int>()
var i = 0
var variables = mutableMapOf<String, String>()
var funs = mutableListOf<String>()

fun getType(obj: Any): String {
    return when (obj) {
        is String -> {
            if (obj.toIntOrNull() != null) {
                "Int"
            }
            else if (obj.toDoubleOrNull() != null) {
                "Double"
            } else {
                "String"
            }
        }
        else -> obj::class.simpleName ?: "Unknown type"
    }
}

fun exe(curr: List<String>, line: Int) {
    if (curr[1] == "PRINT") {
        print(curr, line, "exe")
    } else if (curr[1] == "EXE") {
        exe(code[lineNames[curr[2]]!!].split(" ").filter { it != "" }, lineNames[curr[2]]!!)
    } else if (curr[1] == "IN") {
        input(curr, "exe")
    } else if (curr[1] == "WHILE") {
        wloop(curr, line, "exe")
    } else if (curr[1] == "BREAK") {
        brek(curr, "exe")
    } else if (curr[1] == "IF") {
        ifstt(curr, line, "exe")
    } else if (curr[1] == "CONTINUE") {
        cont(curr, "exe")
    } else if (curr[1] == "FOR") {
        floop(curr, line, "exe")
    } else if (curr[1] == "LIST") {
        list(curr, line, "exe")
    } else if (curr[1] == "RETURN") {
        rtrn(curr, "exe")
    } else if (curr[1] == "EXEF") {
        exef(curr, line, "exe")
    } else if (curr[1] == "VAR") {
        variable(curr, line, "var", "exe")
    } else if (curr[1] == "VAL") {
        variable(curr, line, "val", "exe")
    }

    else if (curr[2] == "=" || curr[2] == "+=" || curr[2] == "-=" || curr[2] == "*=" || curr[2] == "/=" || curr[2] == "%=") {
        varchange(curr, line, "exe")
    }
}
fun print(curr: List<String>, line: Int, fn: String) {
    if (curr[0] == "~" || fn == "exe") {
        kotlin.appendText("\tprintln(\"" + code[line].split('"').filter { it != "" }.last() + "\")\n")
    }
}
fun variable(curr: List<String>, line: Int, type: String, fn: String) {
    if ((curr[0] == "~" || fn == "exe") && curr.size == 3 && type == "var") {
        println("...")
        kotlin.appendText("\tlateinit var ${curr[2]}: Any\n")
    } else if ((curr[0] == "~" || fn == "exe") && curr[3] == "=") {
        if (curr[4][0] == '"') {
            kotlin.appendText("\t$type ${curr[2]} = \"${code[line].split("\"")[1]}\"\n")
            variables[curr[2]] = getType(code[line].split("\"")[1])
        } else {
            var b = ""
            for (k in 4 until curr.size) {
                b += curr[k]
            }
            kotlin.appendText("\t$type ${curr[2]} = ${b}\n")
            variables[curr[2]] = getType(b)
        }
    }
}
fun varchange(curr: List<String>, line: Int, fn: String) {
    if (curr[0] == "~" || fn == "exe") {
        if (curr[3][0] == '"') {
            kotlin.appendText("\t${curr[1]} ${curr[2]} \"${code[line].split("\"")[1]}\"\n")
        } else {
            var b = ""
            for (k in 3 until curr.size) {
                b += curr[k]
            }
            kotlin.appendText("\t${curr[1]} ${curr[2]} ${b}\n")
        }
    }
}
fun ifstt(curr: List<String>, line: Int, fn: String) {
    if (curr[0] == "~" || fn == "exe") {
        kotlin.appendText("\tif (${code[line].split("[")[1].split("]").first()}) {\n")
        val lines = code[line].split("]").last().filter { it != ' ' }.split('&')
        for (line2 in lines) {
            exe(code[lineNames[line2]!!].split(" ").filter { it != "" }, lineNames[line2]!!)
        }
        kotlin.appendText("\t}\n")
    }
}
fun elifstt(curr: List<String>, line: Int) {
    if (curr[0] == "~" && (code[line-1].split(" ").filter { it != "" }[1] == "ELIF" || code[line-1].split(" ").filter { it != "" }[1] == "IF")) {
        kotlin.appendText("\telse if (${code[line].split("[")[1].split("]").first()}) {\n")
        val lines = code[line].split("]").last().filter { it != ' ' }.split('&')
        for (line2 in lines) {
            exe(code[lineNames[line2]!!].split(" ").filter { it != "" }, lineNames[line2]!!)
        }
        kotlin.appendText("\t}\n")
    }
}
fun elsestt(curr: List<String>, line: Int) {
    if (curr[0] == "~" && (code[line-1].split(" ").filter { it != "" }[1] == "ELIF" || code[line-1].split(" ").filter { it != "" }[1] == "IF")) {
        kotlin.appendText("\telse {\n")
        val lines = code[line].split("ELSE").last().filter { it != ' ' }.split('&')
        for (line2 in lines) {
            exe(code[lineNames[line2]!!].split(" ").filter { it != "" }, lineNames[line2]!!)
        }
        kotlin.appendText("\t}\n")
    }
}
fun input(curr: List<String>, fn: String) {
    if (curr[0] == "~" || fn == "exe") {
        kotlin.appendText("\t${curr[2]} = readln()")
        when (variables[curr[2]]) {
            "Int" -> kotlin.appendText(".toInt()\n")
            "String" -> kotlin.appendText("\n")
            "Double" -> kotlin.appendText(".toDouble()\n")
            else -> kotlin.appendText("\n")
        }
    }
}
fun wloop(curr: List<String>, line: Int, fn: String) {
    if (curr[0] == "~" || fn == "exe") {
        kotlin.appendText("\twhile (${code[line].split("[")[1].split("]").first()}) {\n")
        val lines = code[line].split("]").last().filter { it != ' ' }.split('&')
        for (line2 in lines) {
            exe(code[lineNames[line2]!!].split(" ").filter { it != "" }, lineNames[line2]!!)
        }
        kotlin.appendText("\t}\n")
    }
}
fun brek(curr: List<String>, fn: String) {
    if (curr[0] == "~" || fn == "exe") {
        kotlin.appendText("\tbreak\n")
    }
}
fun cont(curr: List<String>, fn: String) {
    if (curr[0] == "~" || fn == "exe") {
        kotlin.appendText("\tcontinue\n")
    }
}
fun floop(curr: List<String>, line: Int, fn: String) {
    if (curr[0] == "~" || fn == "exe") {
        if (curr[3] == ">") {
            val a = code[line].split("[")[1].split(">")[1].split("]")[0].filter { it != ' ' }
            val b = code[line].split("[")[1].split(">")[0].filter { it != ' ' }
            kotlin.appendText("\tfor ($b in $a) {\n")
            val lines = code[line].split("]").last().filter { it != ' ' }.split('&')
            for (line2 in lines) {
                exe(code[lineNames[line2]!!].split(" ").filter { it != "" }, lineNames[line2]!!)
            }
            kotlin.appendText("\t}\n")
        }
    }
}
fun list(curr: List<String>, line: Int, fn: String) {
    if (curr[0] == "~" || fn == "exe") {
        kotlin.appendText("\tvar ${curr[2]} = mutableListOf(${code[line].split("[").last().filter { it != ']' }})\n")
    }
}
fun func() {
    if (funs.isNotEmpty()) {
        for (func in funs) {
            kotlin.appendText("fun ${func.split(" ").filter { it != "" }[2].split("[").first()}")
            kotlin.appendText("(${func.split("[").last().split("]").first()}): Any {\n")
            val lines = func.split("[").last().split("]").last().filter { it != ' ' }.split("&")
            for (line2 in lines) {
                exe(code[lineNames[line2]!!].split(" ").filter { it != "" }, lineNames[line2]!!)
            }
            kotlin.appendText("}\n")
        }
    }
}
fun rtrn(curr: List<String>, fn: String) {
    if (fn == "exe") {
        kotlin.appendText("\treturn ${curr[2]}\n")
    }
}
fun exef(curr: List<String>, line: Int, fn: String) {
    if (curr[0] == "~" || fn == "exe") {
        kotlin.appendText("\t${code[line].split(" ").filter { it != "" }[2].split("[").first()}")
        kotlin.appendText("(${code[line].split("[").last().split("]").first()})\n")
    }
}
fun main(args: Array<String>) {
    miniN = File("${args[0]}miniN.minni")
    kotlin = File("${args[0]}trnsltd.kt")
    code = miniN.readLines()
    kotlin.writeText("fun main() {\n")
    while (i < code.size) {
        val curr = code[i].split(" ").filter { it != "" }

        //line namer
        if (curr.isEmpty() || curr[0] == "?") {
            i++
            continue
        } else if (curr[0] != "~") {
            lineNames[curr[0]] = i
        }

        if (curr[1] == "PRINT") {
            print(curr, i, "main")
        } else if (curr[1] == "EXE") {
            exe(code[lineNames[curr[2]]!!].split(" ").filter { it != "" }, lineNames[curr[2]]!!)
        } else if (curr[1] == "VAR") {
            variable(curr, i, "var", "main")
        } else if (curr[1] == "VAL") {
            variable(curr, i, "val", "main")
        } else if (curr[1] == "IF") {
            ifstt(curr, i, "main")
        } else if (curr[1] == "ELIF") {
            elifstt(curr, i)
        } else if (curr[1] == "ELSE") {
            elsestt(curr, i)
        } else if (curr[1] == "IN") {
            input(curr, "main")
        } else if (curr[1] == "WHILE") {
            wloop(curr, i, "main")
        } else if (curr[1] == "BREAK") {
            brek(curr, "main")
        } else if (curr[1] == "CONTINUE") {
            cont(curr, "main")
        } else if (curr[1] == "FOR") {
            floop(curr, i, "main")
        } else if (curr[1] == "LIST") {
            list(curr, i, "main")
        } else if (curr[1] == "FUN") {
            funs += code[i]
        } else if (curr[1] == "RETURN") {
            rtrn(curr, "main")
        } else if (curr[1] == "EXEF") {
            exef(curr, i, "main")
        }

        else if (curr[2] == "=" || curr[2] == "+=" || curr[2] == "-=" || curr[2] == "*=" || curr[2] == "/=" || curr[2] == "%=") {
            varchange(curr, i, "main")
        }
        i++
    }
    kotlin.appendText("}\n")
    func()
}