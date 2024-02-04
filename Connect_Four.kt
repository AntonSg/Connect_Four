import java.lang.Integer.min



class Board(var grows: Int, var gcolumns: Int) {
    var boardBorders: MutableList<String> = mutableListOf()
    var boardValues: MutableList<MutableList<String>> = mutableListOf()
    
    fun createBoard() {
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

    fun printBoard() {
        for (i in 0..grows) {
            for (j in 0..gcolumns) {
                if (i == 0) {
                    print(" ${boardBorders[i][j]}")
                } else {
                    print(boardBorders[i][j])
                    if (j <= gcolumns - 1) {
                        print(boardValues[j][grows - i])
                    }
                }
            }
            println()
        }
        println(boardBorders[grows+1])
    }

}




class Player(val name: String) {
    var score: Int = 0

}

class Game (FPlayer: Player, SPlayer: Player, NumberOfGames: Int, rows: Int, columns: Int) {
    val FirstPlayer = FPlayer
    val SecondPlayer = SPlayer
    val NGames = NumberOfGames
    val rows = rows
    val columns = columns

    fun checkWin(column: Int, index: Int, board: Board): Boolean  {
        var flag = false
        if (index >= 3) {
            val n1 = board.boardValues[column - 1][index]
            val n2 = board.boardValues[column - 1][index-1]
            val n3 = board.boardValues[column - 1][index-2]
            val n4 = board.boardValues[column - 1][index-3]
            if (n1 == n2 && n3 == n4 && n1 == n3) {
                flag = true
            }
        } else if (flag == false) {
            for (i in 0..columns.toString().toInt() - 4) {
                val n1 = board.boardValues[i][index]
                val n2 = board.boardValues[i + 1][index]
                val n3 = board.boardValues[i + 2][index]
                val n4 = board.boardValues[i + 3][index]
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
                    val n1 = board.boardValues[startColumn + i][startRow + i]
                    val n2 = board.boardValues[startColumn + i + 1][startRow + i + 1]
                    val n3 = board.boardValues[startColumn + i + 2][startRow + i + 2]
                    val n4 = board.boardValues[startColumn + i + 3][startRow + i + 3]
                    if (n1 == n2 && n3 == n4 && n1 == n3 && n1 != " ") {
                        return true
                    }
                }
            }
            if (minim3 >= 3) {
                for (i in 0..minim3 - 3) {
                    val n1 = board.boardValues[startColumn2 + i][startRow2 - i]
                    val n2 = board.boardValues[startColumn2 + i + 1][startRow2 - i - 1]
                    val n3 = board.boardValues[startColumn2 + i + 2][startRow2 - i - 2]
                    val n4 = board.boardValues[startColumn2 + i + 3][startRow2 - i - 3]
                    if (n1 == n2 && n3 == n4 && n1 == n3 && n1 != " ") {
                        return true
                    }
                }
            }
        }

        return flag
    }
    
    fun game(board: Board){
        var flag = false
        var turn = 0
        val turnRegex = Regex("\\d+")
        var player: Player
        var mark = ""
        while (flag != true){
            player = if (turn % 2 == 0) FirstPlayer else SecondPlayer
            mark = if (turn % 2 == 0) "o" else "*"
            println("${player.name}'s turn:")
            val valueTurn = readln()
            if (valueTurn == "end") {
                println("Game over!")
                flag = true
            } else {
                if (turnRegex.matches(valueTurn)) {
                    val valueTurnInt = valueTurn.toInt()
                    if (valueTurnInt in 1..columns.toString().toInt()) {
                        if (board.boardValues[valueTurnInt - 1].indexOf(" ") != -1) {
                            val index = board.boardValues[valueTurnInt - 1].indexOf(" ")
                            board.boardValues[valueTurnInt - 1][index] = mark
                            board.printBoard()
                            if (checkWin(valueTurnInt,index,board) == true) {
                                println("Player ${player.name} won")
                                player.score += 2

                                flag = true
                            } else if (true) {
                                var drawFlag = 0
                                for (i in 0..columns.toString().toInt() - 1) {
                                    if (board.boardValues[i].indexOf(" ") == -1) {
                                        drawFlag++
                                    }
                                }
                                if (drawFlag == columns.toString().toInt()) {
                                    println("It is a draw")
                                    FirstPlayer.score += 1
                                    SecondPlayer.score += 1
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
    
    fun startGames() {
        var board = Board(rows,columns)
        if (NGames == 1) {
            println("Single game")
            board.createBoard()
            board.printBoard()
            game(board)
        } else {
            for (i in 1..NGames) {
                println("Total $NGames Games")
                println("Game #$i")
                board.createBoard()
                board.printBoard()
                game(board)
                println("$FirstPlayer.name: $FirstPlayer.score $SecondPlayer.name: $SecondPlayer.score")
            }
        }
        println("Game over!")
    }
}

fun main() {

// Create a game class with constructor
//    var NGames: Int
//    var rows: Char = '0'
//    var columns: Char = '0'
//    val boardBorders: MutableList<String> = mutableListOf()
//    val boardValues: MutableList<MutableList<String>> = mutableListOf()
//    var FirstPlayer = Player("First")
//    var SecondPlayer = Player("Second")


   



    fun intro(): Game {
        var rows = '6'
        var columns = '7'
        println("Connect Four")
        println("First player's name:")
        var FirstPlayer = Player(readln())
        println("Second player's name:")
        var SecondPlayer = Player(readln())
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        val DimRegexp = Regex("\\s*\\d+\\s*[X|x]\\s*\\d+\\s*")
        var flag = false
        while (flag != true) {
            val Dim = readln().toString()
            if (Dim == "") {
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
                    var rows = NewDim[0]
                    var columns = NewDim[2]
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
        var flag2 = false
        var nGames: Int = 1
        val nGamesRexExp = Regex("[1-9]+")
        while (flag2 != true) {
            println("""Do you want to play single or multiple games?
            |For a single game, input 1 or press Enter
            |Input a number of games:
        """.trimMargin())
            val nGamesS: String = readln().toString()
            if (nGamesS.matches(nGamesRexExp)) {
                nGames = nGamesS.toInt()
                flag2 = true
            } else {
                if (nGamesS == "") {
                    nGames = 1
                    flag2 = true
                } else {
                    println("Invalid input")
                }
            }
        }
        return Game(FirstPlayer, SecondPlayer, nGames, rows.toString().toInt(), columns.toString().toInt())
    }
    

    val game1 = intro()
    game1.startGames()
}
