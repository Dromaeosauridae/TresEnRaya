package tresenraya.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.marcador.*
import me.pegunto.si.habra.a3enraya.ListenerCasilla
import me.pegunto.si.habra.a3enraya.Logica

class Juego : AppCompatActivity(), ListenerCasilla {

    val DIM: Int = 3
    val SIZE: Int = DIM - 1
    var casillas = arrayListOf<ArrayList<Button>>()
    var listener: ListenerCasilla = this
    var turnoJugador1 : Boolean = true
    var logica = Logica()
    var puntosJugador1 = 0
    var puntosJugador2 = 0
    var ronda = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.juego)

        setupTablero()
        actualizarMarcador()
    }

    private fun setupTablero() {

        // Inicializar array de botones y asignar su listener
        for (fil in 0..SIZE) {

            var fila = arrayListOf<Button>()

            for (col in 0..SIZE) {
                val idString = "casilla_$fil$col"
                val id = resources.getIdentifier(idString, "id", packageName)

                val butt = findViewById<Button>(id)
                butt.setOnClickListener(View.OnClickListener {
                    listener.onCasillaPulsada(butt, fil, col)
                })

                fila.add(col, butt)
            }

            casillas.add(fil, fila)
        }

        // Asignar listener al botón de reset
        findViewById<Button>(R.id.reset).setOnClickListener {
            reiniciarJuego()
        }

        // Definir la lógica
        logica = Logica()
    }

    override fun onCasillaPulsada(butt: Button, fil: Int, col: Int) {
        if(logica.casillaLibre(fil, col)){
            pintarCasilla(butt)
            comprobarVictoria(fil, col)
            cambiarJugador()
            ronda++
        }
        else{
            Toast.makeText(this, "Esa casilla ya está ocupada!", Toast.LENGTH_SHORT).show()
        }
    }

    fun pintarCasilla(b : Button){
        b.text = getFicha()
    }

    fun comprobarVictoria(fil : Int, col : Int){
        if(logica.comprobarVictoria(fil, col, getFicha())){
            var ganador = ""

            if(turnoJugador1){
                ganador = "1"
                puntosJugador1++
            }
            else{
                ganador = "2"
                puntosJugador2++
            }

            Toast.makeText(this, "VICTORIA PARA EL JUGADOR $ganador", Toast.LENGTH_SHORT).show()
            actualizarMarcador()
            reiniciarPartida()

        } else if (ronda == DIM*DIM){
            Toast.makeText(this, "EMPATE", Toast.LENGTH_SHORT).show()
            reiniciarPartida()
        }
    }

    fun reiniciarJuego(){
        puntosJugador1 = 0
        puntosJugador2 = 0

        actualizarMarcador()
        reiniciarPartida()
    }

    fun reiniciarPartida(){
        // Reiniciar contador de jugadas
        ronda = 0

        // Borrar tablero almacenado
        logica.reiniciar()

        // Blanquear el tablero
        for (i in 0..SIZE) { for (j in 0..SIZE) {
            casillas[i][j].text = ""
        }}
    }

    fun actualizarMarcador(){
        marcador_1.text = puntosJugador1.toString()
        marcador_2.text = puntosJugador2.toString()
    }

    fun cambiarJugador(){
        turnoJugador1 = !turnoJugador1
    }

    fun getFicha() : String{
        return when(turnoJugador1){ true -> "o" false -> "x"}
    }

    // Guardar las variables para cuando se rote el movil
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean("turnoJugador1", turnoJugador1)
        outState.putInt("puntosJugador1", puntosJugador1)
        outState.putInt("puntosJugador2", puntosJugador2)
        outState.putInt("rounda", ronda)
        outState.putSerializable("tablero", logica.tablero)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        turnoJugador1 = savedInstanceState.getBoolean("turnoJugador1")
        puntosJugador1 = savedInstanceState.getInt("puntosJugador1")
        puntosJugador2 = savedInstanceState.getInt("puntosJugador2")
        ronda = savedInstanceState.getInt("ronda")
        logica.tablero = savedInstanceState.getSerializable("tablero") as Array<Array<String>>
    }
}

