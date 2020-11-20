package qbo.com.appkea2googlemapsactivity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener {

    private lateinit var mMap: GoogleMap
    var lstLatLong = ArrayList<LatLng>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //Agregar el evento para hacer clic en el mapa.
        mMap.setOnMapClickListener(this)
        //Agregar el evento para arrastrar el marcador del mapa.
        mMap.setOnMarkerDragListener(this)

        // Add a marker in Sydney and move the camera
        val ubicacion = LatLng(-12.089150, -77.036641)
        mMap.addMarker(MarkerOptions()
                .position(ubicacion)
                .title("Ubicación")
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location))
                .snippet("Lima-Perú"))
        mMap.isTrafficEnabled = true
        //mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 16.0F))
    }

    override fun onMapClick(p0: LatLng?) {
        mMap.addMarker(
                MarkerOptions().position(p0!!)
                        .title("Nuevo Marcador")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location))
        )
        lstLatLong.add(p0!!)
        val nuevaLinea = PolylineOptions()
        nuevaLinea.color(Color.RED)
        nuevaLinea.width(6F)
        nuevaLinea.addAll(lstLatLong)
        mMap.addPolyline(nuevaLinea)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p0!!, 36.0F))
    }

    override fun onMarkerDragStart(p0: Marker?) {
        p0!!.hideInfoWindow()
    }

    override fun onMarkerDrag(p0: Marker?) {

    }

    override fun onMarkerDragEnd(p0: Marker?) {
        var posicion = p0!!.position
        p0!!.snippet = "${posicion.latitude} : ${posicion.longitude}"
        p0!!.showInfoWindow()
    }
}