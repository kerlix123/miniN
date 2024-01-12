import java.io.File
import kotlin.system.exitProcess

lateinit var miniN: File
lateinit var kotlin: File
lateinit var code: List<String>
val lineNames = mutableMapOf<String, Int>()
var i = 0
var funs = mutableListOf<String>()
var sttstack = mutableListOf<String>()

fun exe(curr: List<String>, line: Int) {
    if (curr[1] == "PRINT" || curr[1] == "print") {
        print(curr, line, "exe")
    } else if (curr[1] == "EXE" || curr[1] == "exe") {
        exe(code[lineNames[curr[2]]!!].split(" ").filter { it != "" }, lineNames[curr[2]]!!)
    } else if (curr[1] == "IN" || curr[1] == "in") {
        input(curr, "exe")
    } else if (curr[1] == "WHILE" || curr[1] == "while") {
        wloop(curr, line, "exe")
    } else if (curr[1] == "BREAK" || curr[1] == "break") {
        brek(curr, "exe")
    } else if (curr[1] == "IF" || curr[1] == "if") {
        ifstt(curr, line, "exe")
    } else if (curr[1] == "CONTINUE" || curr[1] == "continue") {
        cont(curr, "exe")
    } else if (curr[1] == "FOR" || curr[1] == "for") {
        floop(curr, line, "exe")
    } else if (curr[1] == "RETURN" || curr[1] == "return") {
        rtrn(curr, "exe")
    } else if (curr[1] == "EXEF" || curr[1] == "exef") {
        exef(curr, line, "exe")
    } else if (curr[1] == "VAR" || curr[1] == "var") {
        variable(curr, line, "var", "exe")
    } else if (curr[1] == "VAL" || curr[1] == "val") {
        variable(curr, line, "val", "exe")
    }

    else if (curr[2] == "=" || curr[2] == "+=" || curr[2] == "-=" || curr[2] == "*=" || curr[2] == "/=" || curr[2] == "%=") {
        varchange(curr, line, "exe")
    }
}
fun print(curr: List<String>, line: Int, fn: String) {
    if (curr[0] == "~" || fn == "exe") {
        if (curr[2].first() == '"') {
            kotlin.appendText("\tprintln(\"" + code[line].split('"').last { it != "" } + "\")\n")
        } else {
            kotlin.appendText("\tprintln(${curr[2]})\n")
        }
    }
}
fun variable(curr: List<String>, line: Int, type: String, fn: String) {
    if ((curr[0] == "~" || fn == "exe") && curr.size == 3 && type == "var") {
        kotlin.appendText("\tlateinit var ${curr[2]}: Any\n")
    } else if ((curr[0] == "~" || fn == "exe") && curr[3] == "=") {
        if (curr[4].first() == '"' && curr.last().last() == '"') {
            kotlin.appendText("\t$type ${curr[2]} = \"${code[line].split("\"")[1]}\"\n")
        } else if (curr[4].first() == '[' && curr.last().last() == ']') {
            kotlin.appendText("\t$type ${curr[2]} = mutableListOf(${code[line].split("[").last().filter { it != ']' }})\n")
        } else {
            var b = ""
            for (k in 4..<curr.size) {
                b += curr[k]
            }
            kotlin.appendText("\t$type ${curr[2]} = ${b}\n")
        }
    }
}
fun varchange(curr: List<String>, line: Int, fn: String) {
    if (curr[0] == "~" || fn == "exe") {
        if (curr[3][0] == '"' && curr.last().last() == '"') {
            kotlin.appendText("\t${curr[1]} ${curr[2]} \"${code[line].split("\"")[1]}\"\n")
        } else if (curr[3][0] == '[' && curr.last().last() == ']') {
            kotlin.appendText("\t${curr[2]} ${curr[2]} mutableListOf(${code[line].split("[").last().filter { it != ']' }})\n")
        } else {
            var b = ""
            for (k in 3..<curr.size) {
                b += curr[k]
            }
            kotlin.appendText("\t${curr[1]} ${curr[2]} ${b}\n")
        }
    }
}
fun ifstt(curr: List<String>, line: Int, fn: String) {
    if (curr[0] == "~" || fn == "exe") {
        kotlin.appendText("\tif (${code[line].subSequence(code[line].indexOf('[')+1, code[line].lastIndexOf(']'))}) {\n")
        val lines = code[line].split("]").last().filter { it != ' ' }.split('&')
        for (line2 in lines) {
            exe(code[lineNames[line2]!!].split(" ").filter { it != "" }, lineNames[line2]!!)
        }
        kotlin.appendText("\t}\n")
        sttstack += "if"
    }
}
fun elifstt(curr: List<String>, line: Int) {
    if (curr[0] == "~" && sttstack.last() == "elif" || sttstack.last() == "if") {
        kotlin.appendText("\telse if (${code[line].subSequence(code[line].indexOf('[')+1, code[line].lastIndexOf(']'))}) {\n")
        val lines = code[line].split("]").last().filter { it != ' ' }.split('&')
        for (line2 in lines) {
            exe(code[lineNames[line2]!!].split(" ").filter { it != "" }, lineNames[line2]!!)
        }
        kotlin.appendText("\t}\n")
        sttstack += "elif"
    }
}
fun elsestt(curr: List<String>, line: Int) {
    if (curr[0] == "~" && sttstack.last() == "elif" || sttstack.last() == "if") {
        kotlin.appendText("\telse {\n")
        val lines = code[line].split("ELSE").last().filter { it != ' ' }.split('&')
        for (line2 in lines) {
            exe(code[lineNames[line2]!!].split(" ").filter { it != "" }, lineNames[line2]!!)
        }
        kotlin.appendText("\t}\n")
        sttstack += "else"
    }
}
fun input(curr: List<String>, fn: String) {
    if (curr[0] == "~" || fn == "exe") {
        kotlin.appendText("\t${curr[3]} = readln()")
        when (curr[2]) {
            "INT" -> kotlin.appendText(".toInt()\n")
            "STRING" -> kotlin.appendText("\n")
            "DOUBLE" -> kotlin.appendText(".toDouble()\n")
            "CHAR" -> kotlin.appendText(".toChar()\n")
            "BOOL" -> kotlin.appendText(".toBoolean()\n")
            else -> kotlin.appendText("\n")
        }
    }
}
fun wloop(curr: List<String>, line: Int, fn: String) {
    if (curr[0] == "~" || fn == "exe") {
        kotlin.appendText("\twhile (${code[line].subSequence(code[line].indexOf('[')+1, code[line].lastIndexOf(']'))}) {\n")
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
fun file (curr: List<String>, line: Int, fn: String) {
    if (curr[0] == "~" || fn == "exe") {
        if (curr[4].first() == '"' && curr[4].last() == '"') {
            kotlin.appendText("\tvar ${curr[2]} = File(${curr[4]})\n")
        }
    }
}
fun main(args: Array<String>) {
    val type: String
    miniN = File("${args[0]}miniN.minni")
    kotlin = File("${args[0]}trnsltd.kt")
    code = miniN.readLines()

    kotlin.writeText("")

    //LBF || LBAF
    if (code.first().split(" ").first { it != "" } == "#") {
        type = when (code.first().split(" ").filter { it != "" }[1]) {
            "LBF" -> "LBF"
            "LBAF" -> "LBAF"
            else -> exitProcess(0)
        }
    } else {
        exitProcess(0)
    }

    //LBAF
    if (type == "LBAF")  {
        for (k in code.indices) {
            val curr = code[k].split(" ").filter { it != "" }
            if (curr.isNotEmpty() && (curr[0] != "~" && curr[0] != "?" && curr[0] != "#")) {
                lineNames[curr[0]] = k
            }
        }
    }

    if (code.contains("> FILE") || code.contains("> file")) {
        kotlin.appendText("import java.io.File\n")
    }

    kotlin.appendText("fun main() {\n")
    while (i < code.size) {
        val curr = code[i].split(" ").filter { it != "" }

        //line namer && LBF
        if (curr.isEmpty() || curr[0] == "?" || curr[0] == "#") {
            i++
            continue
        } else if (curr[0] != "~" && type == "LBF") {
            lineNames[curr[0]] = i
        }
        if (curr[1] == "PRINT" || curr[1] == "print") {
            print(curr, i, "main")
        } else if (curr[1] == "EXE" || curr[1] == "exe") {
            exe(code[lineNames[curr[2]]!!].split(" ").filter { it != "" }, lineNames[curr[2]]!!)
        } else if (curr[1] == "VAR" || curr[1] == "var") {
            variable(curr, i, "var", "main")
        } else if (curr[1] == "VAL" || curr[1] == "val") {
            variable(curr, i, "val", "main")
        } else if (curr[1] == "IF" || curr[1] == "if") {
            ifstt(curr, i, "main")
        } else if (curr[1] == "ELIF" || curr[1] == "elif") {
            elifstt(curr, i)
        } else if (curr[1] == "ELSE" || curr[1] == "else") {
            elsestt(curr, i)
        } else if (curr[1] == "IN" || curr[1] == "in") {
            input(curr, "main")
        } else if (curr[1] == "WHILE" || curr[1] == "while") {
            wloop(curr, i, "main")
        } else if (curr[1] == "BREAK" || curr[1] == "break") {
            brek(curr, "main")
        } else if (curr[1] == "CONTINUE" || curr[1] == "continue") {
            cont(curr, "main")
        } else if (curr[1] == "FOR" || curr[1] == "for") {
            floop(curr, i, "main")
        } else if (curr[1] == "FUN" || curr[1] == "fun") {
            funs += code[i]
        } else if (curr[1] == "RETURN" || curr[1] == "return") {
            rtrn(curr, "main")
        } else if (curr[1] == "EXEF" || curr[1] == "exef") {
            exef(curr, i, "main")
        } else if (curr[1] == "FILE" || curr[1] == "file") {
            file(curr, i, "main")
        }

        else if (curr[2] == "=" || curr[2] == "+=" || curr[2] == "-=" || curr[2] == "*=" || curr[2] == "/=" || curr[2] == "%=") {
            varchange(curr, i, "main")
        }
        i++
    }
    kotlin.appendText("}\n")
    func()
}