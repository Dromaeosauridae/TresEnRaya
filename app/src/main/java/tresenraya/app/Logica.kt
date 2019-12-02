package me.pegunto.si.habra.a3enraya

class Logica(){

    var tablero = Array(3) { Array(3) {""} }

    fun reiniciar(){
        tablero = Array(3) { Array(3) {""} }
    }

    fun casillaLibre(fil: Int, col: Int): Boolean{
        return (tablero[fil][col]=="")
    }

    fun comprobarVictoria(fil: Int, col: Int, ficha: String) : Boolean{
        var victoria = false

        actualizarTablero(fil, col, ficha)
        victoria = comprobarFila(fil, ficha) or comprobarColumna(col, ficha) or comprobarDiagonales(ficha)

        return victoria
    }

    private fun actualizarTablero(fil: Int, col: Int, ficha: String) {
        tablero[fil][col] = ficha
    }

    private fun comprobarFila(fil: Int, ficha : String): Boolean {
        var sumaPuntos = 0

        for (i in 0..2) {
            if (tablero[fil][i] == ficha) {
                sumaPuntos++
            }
        }
        return sumaPuntos == 3
    }

    private fun comprobarColumna(col: Int, ficha : String): Boolean {
        var sumaPuntos = 0

        for (i in 0..2) {
            if (tablero[i][col] == ficha) {
                sumaPuntos++
            }
        }
        return sumaPuntos == 3

    }

    private fun comprobarDiagonales(ficha : String): Boolean {
        var sumaPuntos = 0

        //primera diagonal
        for(i in 0..2){
            if(tablero[i][i] == ficha){
                sumaPuntos++
            }
        }
        //segunda diagonal
        if(sumaPuntos < 3){
            sumaPuntos = 0

            for(i in 0..2){
                if(tablero[i][2-i] == ficha){
                    sumaPuntos++
                }
            }
        }

        return sumaPuntos == 3
    }
}