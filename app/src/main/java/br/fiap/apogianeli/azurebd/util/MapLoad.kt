package br.fiap.apogianeli.azurebd.util

import android.location.Geocoder
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import java.io.IOException


class MapLoad : SupportMapFragment(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        val posicao = getAddress("Westfield Sydney Shopping Centre, 6010 Pitt St & Market Streets, Sydney NSW 2000")

        if (posicao != null) {
            val update = CameraUpdateFactory.newLatLngZoom(posicao, 17f)
            googleMap.addMarker(MarkerOptions().position(posicao).title("Westfield Sydney Shopping"))

            googleMap.moveCamera(update)
        }

    }

    private fun getAddress(endereco: String): LatLng? {
        try {
            val geocoder = Geocoder(context)
            val resultados = geocoder.getFromLocationName(endereco, 1)
            if (!resultados.isEmpty()) {
                val posicao = LatLng(resultados[0].latitude, resultados[0].longitude)
                return LatLng(resultados[0].latitude, resultados[0].longitude)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}