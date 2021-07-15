package com.example.bankaccountapp.tictactoe

import android.media.AsyncPlayer

class Board {

    companion object {
        const val PLAYER = "O"
        const val COMPUTER = "X"
    }

    val boardplaces = Array(3) { arrayOfNulls<String>(3) }

    val availableCells: List<Cell>
        // função dentro da variável a qual irá definir o valor available cells (por isso o return)
        get() {
            val cells = arrayListOf<Cell>()
            for (i in boardplaces.indices) {
                for (j in boardplaces.indices) {
                    if (boardplaces[i][j].isNullOrEmpty()) {
                        cells.add(Cell(i, j))
                    }
                }
            }
            return cells
        }


    fun placeMove(cell: Cell, player: String) {  //função para alocar um movimento em uma célula específica
        boardplaces[cell.i][cell.j] = player
    }

    fun computerWon(): Boolean {
        if (
            (boardplaces[0][0] == boardplaces[1][1] && boardplaces[1][1] == boardplaces[2][2] && boardplaces[0][0] == COMPUTER) ||
            (boardplaces[0][2] == boardplaces[1][1] && boardplaces[1][1] == boardplaces[2][0] && boardplaces[2][0] == COMPUTER)
        ) {
            return true
        }
        for (i in boardplaces.indices) {
            if (
                (boardplaces[i][0] == boardplaces[i][1] && boardplaces[i][1] == boardplaces[i][2] && boardplaces[i][0] == COMPUTER) ||
                (boardplaces[0][i] == boardplaces[1][i] && boardplaces[0][i] == boardplaces[2][i] && boardplaces[0][i] == COMPUTER)
            ) {
                return true
            }
        }
        return false
    }

    fun playerWon(): Boolean {
        if (
            (boardplaces[0][0] == boardplaces[1][1] && boardplaces[1][1] == boardplaces[2][2] && boardplaces[0][0] == PLAYER) ||
            (boardplaces[0][2] == boardplaces[1][1] && boardplaces[1][1] == boardplaces[2][0] && boardplaces[2][0] == PLAYER)
        ) {
            return true
        }
        for (i in boardplaces.indices) {
            if (
                (boardplaces[i][0] == boardplaces[i][1] && boardplaces[i][1] == boardplaces[i][2] && boardplaces[i][0] == PLAYER) ||
                (boardplaces[0][i] == boardplaces[1][i] && boardplaces[0][i] == boardplaces[2][i] && boardplaces[0][i] == PLAYER)
            ) {
                return true
            }
        }
        return false
    }

    fun gameOver(): Boolean {
        if (computerWon() || playerWon() || availableCells.isEmpty()) {
            return true
        }
        return false
    }

    var computersMove: Cell? = null
    fun minimax(depth: Int, player: String): Int {

        if (computerWon()) return +1
        if (playerWon()) return -1
        if (availableCells.isEmpty()) return 0

        var min = Integer.MAX_VALUE
        var max = Integer.MIN_VALUE

        for (i in availableCells.indices) {
            val cell = availableCells[i]
            if (player == COMPUTER) {
                placeMove(cell, COMPUTER)
                val currentScore = minimax(depth + 1, PLAYER)
                max = Math.max(currentScore, max) //max é uma função da classe math para comparar se o primeiro valor é maior que o segundo, ou seja, se o current score é maior que o max
                // max = if (a>=b){ a } else{ b }

                if (currentScore >= 0) {
                    if (depth == 0) {
                        computersMove = cell
                    }
                }
                if (currentScore == 1) {
                    boardplaces[cell.i][cell.j] = ""
                    break
                }

                if (i == availableCells.size - 1 && max < 0) {
                    if (depth == 0)
                    {
                        computersMove = cell
                    }
                }
            } else if (player == PLAYER) {
                placeMove(cell, PLAYER)
                val currentScore = minimax(depth + 1, COMPUTER)
                min = Math.min(currentScore, min)

                if (min == -1) {
                    boardplaces[cell.i][cell.j] = ""
                    break
                }

            }
            boardplaces[cell.i][cell.j] = ""
        }

        if (player == COMPUTER) {
            return max
        } else {
            return min
        }
//        return if (player== COMPUTER) max else min


    }


}