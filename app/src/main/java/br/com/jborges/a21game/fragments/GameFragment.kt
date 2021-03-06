package br.com.jborges.a21game.fragments

//jborges

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import br.com.jborges.a21game.EstorouPontuacaoActivity

import br.com.jborges.a21game.R
import br.com.jborges.a21game.dao.CartaDAO
import br.com.jborges.a21game.model.Carta
import kotlinx.android.synthetic.main.fragment_game.*
import java.util.*

class GameFragment : Fragment() {

    val cartaDAO = CartaDAO()
    val gerador = Random()
    var cartas: MutableList<Carta> = ArrayList()

    lateinit var btRecomecar: Button
    lateinit var tvPontuacao: TextView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btProximaCarta.setOnClickListener({
            realizarJogada()
        })
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_game, container, false)

        btRecomecar = view.findViewById(R.id.btRecomecar)
        tvPontuacao = view.findViewById(R.id.tvPontuacao)

        btRecomecar.setOnClickListener({
            iniciarPartida()
        })

        iniciarPartida()

        return view
    }

    fun iniciarPartida() {
        tvPontuacao.setText("0")
        cartas = cartaDAO.getBaralho().toMutableList()
    }

    fun realizarJogada() {

        val posicaoCartaSelecionada = gerador.nextInt(cartas.size)
        val cartaSelecionada = cartas.get(posicaoCartaSelecionada)

        val pontuacaoAtualizada = tvPontuacao.text.toString().toInt() + cartaSelecionada.pontucao
        tvPontuacao.text = pontuacaoAtualizada.toString()
        if (pontuacaoAtualizada > 21) {
            val intent = Intent(context, EstorouPontuacaoActivity::class.java)
            intent.putExtra("PONTUACAO", tvPontuacao.text.toString())
            startActivity(intent)
            iniciarPartida()
        } else {
            cartas.removeAt(posicaoCartaSelecionada)
            ivCarta.setImageDrawable(ContextCompat.getDrawable(context, cartaSelecionada.resourceId))
        }
    }
}
