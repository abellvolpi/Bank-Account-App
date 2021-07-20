package com.example.bankaccountapp.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.bankaccountapp.R
import com.example.bankaccountapp.databinding.FragmentTicTacToeBinding
import com.example.bankaccountapp.tictactoe.Board
import com.example.bankaccountapp.tictactoe.Cell
import com.example.bankaccountapp.utils.AccountManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.FieldPosition
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random


class TicTacToeFragment : Fragment(R.layout.fragment_tic_tac_toe), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main + Job()

    private val boardCells = Array(3) { arrayOfNulls<ImageButton>(3) }

    private lateinit var binding: FragmentTicTacToeBinding

    private val args: TicTacToeFragmentArgs by navArgs()

    private var snackbar: Snackbar? = null

    var board = Board()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTicTacToeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch(Dispatchers.Default) {
            AccountManager.accountGenerator()

        }

        args.conta.balance -= 100000
        AccountManager.escreverCsv()

        with(binding) {

            loadBoard()

            toolbarTictactoe.setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }
    }


    override fun onPause() {
        super.onPause()
        snackbar?.let {
            it.dismiss()
        }
    }


    private fun mapBoardToUi() {
        for (i in board.boardplaces.indices) {
            for (j in board.boardplaces.indices) {
                when (board.boardplaces[i][j]) {
                    Board.PLAYER -> {
                        boardCells[i][j]?.setImageResource(R.drawable.ic_circle)
                        boardCells[i][j]?.isEnabled = false
                    }
                    Board.COMPUTER -> {
                        boardCells[i][j]?.setImageResource(R.drawable.ic_x)
                        boardCells[i][j]?.isEnabled = false
                    }
                    //servirá para limpar o board após o restart
                    else -> {
                        boardCells[i][j]?.setImageResource(0)
                        boardCells[i][j]?.isEnabled = true
                    }
                }
            }
        }
    }

    inner class CellClickListener(private val i: Int, private val j: Int) :
        View.OnClickListener { //classe para identificar o movimento do jogador e generalizar o comando onClick
        override fun onClick(v: View?) {

            if (!board.gameOver()) {

                val cell = Cell(i, j)

                board.placeMove(cell, Board.PLAYER)

                board.minimax(0, Board.COMPUTER) //atribui um valor a célula do computers move

                board.computersMove?.let {
                    board.placeMove(it, Board.COMPUTER)
                }

                mapBoardToUi() //repassa o jogo da velha interno para a interface
            }
            when {
                board.computerWon() -> {

                    view?.let { showSnack(it) }
                    binding.whoWon.text = "Computer Won"

                }
                board.playerWon() -> binding.whoWon.text = "This will never happen"

                board.gameOver() -> {
                    view?.let { showSnack(it) }
                    binding.whoWon.text = "Game Tied"
                    args.conta.balance += 100000
                    AccountManager.escreverCsv()
                }
            }
        }
    }


    private fun linkButtons() {
        with(binding) {
            boardCells[0][0] = btn1
            boardCells[0][1] = btn2
            boardCells[0][2] = btn3
            boardCells[1][0] = btn4
            boardCells[1][1] = btn5
            boardCells[1][2] = btn6
            boardCells[2][0] = btn7
            boardCells[2][1] = btn8
            boardCells[2][2] = btn9
        }
    }

    private fun loadBoard() {
        linkButtons()
        for (i in boardCells.indices) {
            for (j in boardCells.indices) {
                boardCells[i][j]?.setOnClickListener(CellClickListener(i, j))
            }
        }
    }

    fun showSnack(view: View) {
        snackbar = Snackbar.make(view, "Fim de Jogo", Snackbar.LENGTH_INDEFINITE).setAction("Restart") {
            args.conta.balance -= 100000
            AccountManager.escreverCsv()
            board = Board()
            binding.whoWon.text = ""
            mapBoardToUi()
        }
        snackbar?.show()
    }

}
