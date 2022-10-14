import java.lang.Integer.min

fun main() {
    class Player(val name: String) {

    }

    var rows: Char = '0'
    var columns: Char = '0'
    val boardBorders: MutableList<String> = mutableListOf()
    val boardValues: MutableList<MutableList<String>> = mutableListOf()
    var FirstPlayer = Player("First")
    var SecondPlayer = Player("Second")

    fun checkWin(column: Int, index: Int): Boolean  {
        var flag = false
        if (index >= 3) {
            val n1 = boardValues[column - 1][index]
            val n2 = boardValues[column - 1][index-1]
            val n3 = boardValues[column - 1][index-2]
            val n4 = boardValues[column - 1][index-3]
            if (n1 == n2 && n3 == n4 && n1 == n3) {
                flag = true
            }
        } else if (flag == false) {
            for (i in 0..columns.toString().toInt() - 4) {
                val n1 = boardValues[i][index]
                val n2 = boardValues[i + 1][index]
                val n3 = boardValues[i + 2][index]
                val n4 = boardValues[i + 3][index]
                if (n1 == n2 && n3 == n4 && n1 == n3 && n1 != " ") {
                    flag = true
                    break
                }
            }
        }

        if (flag == false) {
            val minim = min(column - 1, index)
            val startColumn = column - 1 - minim
            val startRow = index - minim
            val minim2 = min(columns.toString().toInt() - 1 - startColumn, rows.toString().toInt() - 1 - startRow)
            val startColumn2 = column - 1 - min(column - 1, rows.toString().toInt() - 1 - index)
            val startRow2 = index + min(column - 1, rows.toString().toInt() - 1 - index)
            val minim3 = min(columns.toString().toInt() - 1 - startColumn2, startRow2)
            if (minim2 < 3) {
                flag = false
            } else {
                for (i in 0..minim2 - 3) {
                    val n1 = boardValues[startColumn + i][startRow + i]
                    val n2 = boardValues[startColumn + i + 1][startRow + i + 1]
                    val n3 = boardValues[startColumn + i + 2][startRow + i + 2]
                    val n4 = boardValues[startColumn + i + 3][startRow + i + 3]
                    if (n1 == n2 && n3 == n4 && n1 == n3 && n1 != " ") {
                        return true
                    }
                }
            }
            if (minim3 >= 3) {
                for (i in 0..minim3 - 3) {
                    val n1 = boardValues[startColumn2 + i][startRow2 - i]
                    val n2 = boardValues[startColumn2 + i + 1][startRow2 - i - 1]
                    val n3 = boardValues[startColumn2 + i + 2][startRow2 - i - 2]
                    val n4 = boardValues[startColumn2 + i + 3][startRow2 - i - 3]
                    if (n1 == n2 && n3 == n4 && n1 == n3 && n1 != " ") {
                        return true
                    }
                }
            }
        }

        return flag
    }

    fun createBoard(grows: Int, gcolumns: Int) {
        var numberColumn = ""
        for (i in 1..gcolumns + 1) {
            if (i == gcolumns + 1) {
                numberColumn += " "
            } else {
                numberColumn += "$i"
            }
        }
        var borderString = ""
        boardBorders.add(numberColumn)

        for (i in 0..grows) {
            borderString = ""
            for (j in 0.. gcolumns) {
                if (i == grows) {
                    if (j == 0) {
                        borderString += "╚"
                    } else if (j == gcolumns) {
                        borderString += "╝"
                    } else {
                        borderString += "╩"
                    }
                    if (j < gcolumns) {
                        borderString += "═"
                    }
                } else {
                    borderString += "║"
                }
            }
            boardBorders.add(borderString)
        }

        for (i in 0..gcolumns-1) {
            var boardStringValues: MutableList<String> = mutableListOf()
            for (j in 0.. grows-1) {
                boardStringValues.add(" ")
            }
            boardValues.add(boardStringValues)
        }

    }

    fun printBoard(inputRows:Int, inputColumns: Int) {
        for (i in 0..inputRows) {
            for (j in 0..inputColumns) {
                if (i == 0) {
                    print(" ${boardBorders[i][j]}")
                } else {
                    print(boardBorders[i][j])
                    if (j <= inputColumns - 1) {
                        print(boardValues[j][inputRows - i])
                    }
                }
            }
            println()
        }
        println(boardBorders[inputRows+1])
    }

    fun intro() {
        println("Connect Four")
        println("First player's name:")
        FirstPlayer = Player(readln())
        println("Second player's name:")
        SecondPlayer = Player(readln())
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        val DimRegexp = Regex("\\s*\\d+\\s*[X|x]\\s*\\d+\\s*")
        var flag = false
        while (flag != true) {
            val Dim = readln().toString()
            if (Dim == "") {
                rows = '6'
                columns = '7'
                flag = true
                println("${FirstPlayer.name} VS ${SecondPlayer.name}")
                println("$rows X $columns board")
            } else {
                if (DimRegexp.matches(Dim) != true) {
                    println("Invalid input")
                    println("Set the board dimensions (Rows x Columns)")
                    println("Press Enter for default (6 x 7)")
                } else {
                    val NewDim = Dim.replace("\\s".toRegex(), "").toMutableList()
                    rows = NewDim[0]
                    columns = NewDim[2]
                    if (rows.code in 53..57 == true) {
                        if (columns.code in 53..57 == true) {
                            flag = true
                            println("${FirstPlayer.name} VS ${SecondPlayer.name}")
                            println("$rows X $columns board")
                        } else {
                            println("Board columns should be from 5 to 9")
                            println("Set the board dimensions (Rows x Columns)")
                            println("Press Enter for default (6 x 7)")
                        }
                    } else {
                        println("Board rows should be from 5 to 9")
                        println("Set the board dimensions (Rows x Columns)")
                        println("Press Enter for default (6 x 7)")
                    }
                }
            }

        }
    }

    fun game(){
        var flag = false
        var turn = 0
        val turnRegex = Regex("\\d+")
        var player: String
        var mark = ""
        while (flag != true){
            player = if (turn % 2 == 0) FirstPlayer.name else SecondPlayer.name
            mark = if (turn % 2 == 0) "o" else "*"
            println("$player's turn:")
            val valueTurn = readln()
            if (valueTurn == "end") {
                println("Game over!")
                flag = true
            } else {
                if (turnRegex.matches(valueTurn)) {
                    val valueTurnInt = valueTurn.toInt()
                    if (valueTurnInt in 1..columns.toString().toInt()) {
                        if (boardValues[valueTurnInt - 1].indexOf(" ") != -1) {
                            val index = boardValues[valueTurnInt - 1].indexOf(" ")
                            boardValues[valueTurnInt - 1][index] = mark
                            printBoard(rows.toString().toInt(), columns.toString().toInt())
                            if (checkWin(valueTurnInt,index) == true) {
                                println("Player $player won")
                                println("Game over!")
                                flag = true
                            } else if (true) {
                                var drawFlag = 0
                                for (i in 0..columns.toString().toInt() - 1) {
                                    if (boardValues[i].indexOf(" ") == -1) {
                                        drawFlag++
                                    }
                                }
                                if(drawFlag == columns.toString().toInt()) {
                                    println("It is a draw")
                                    println("Game Over!")
                                    flag = true
                                }
                            }
                            turn++
                        } else {
                            println("Column $valueTurn is full")
                        }
                    } else {
                        println("The column number is out of range (1 - $columns)")
                    }
                } else {
                    println("Incorrect column number")
                }
            }

        }
    }

    intro()
    createBoard(rows.toString().toInt(), columns.toString().toInt())
    printBoard(rows.toString().toInt(), columns.toString().toInt())
    game()
}