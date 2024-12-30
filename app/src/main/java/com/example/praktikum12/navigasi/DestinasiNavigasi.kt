package com.example.praktikum12.navigasi

interface  DestinasiNavigasi{
    val route: String
    val titleRes: String
}

object DestinasiHome: DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home Mhs"
}

object DestinasiDetail : DestinasiNavigasi {
    override val route: String = "detail/{nim}"
    override val titleRes: String = "Detail Mhs"

    fun createRoute(nim: String): String {
        return "detail/$nim"
    }
}

object DestinasiEntry : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Entry Mhs"
}

object DestinasiUpdate : DestinasiNavigasi {
    override val route: String = "update/{nim}"
    override val titleRes: String = "Update Mhs"
}