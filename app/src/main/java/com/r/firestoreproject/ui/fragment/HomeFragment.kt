package com.r.firestoreproject.ui.fragment

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.r.dbfirestrore.model.AppConstants
import com.r.firestoreproject.R
import com.r.firestoreproject.ui.activity.CenterCourseActivity
import com.r.firestoreproject.ui.model.MarkerData

class HomeFragment : Fragment() , OnMapReadyCallback  ,GoogleMap.OnMarkerClickListener{
    var db: FirebaseFirestore? = null
    private lateinit var mMap: GoogleMap
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
       // getAllCenters()

//        Dexter.withContext(requireContext())
//            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//            .withListener(object : PermissionListener {
//                override fun onPermissionGranted(response: PermissionGrantedResponse) {
//                    var manager =
//                        activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                        checkGPSEnable()
//                    } else {
//                        getAllCenters()
//                    }
//                }
//
//                override fun onPermissionDenied(response: PermissionDeniedResponse) {
//                    activity!!.finish()
//                }
//
//                override fun onPermissionRationaleShouldBeShown(
//                    permission: PermissionRequest?,
//                    token: PermissionToken?
//                ) {
//                    token!!.continuePermissionRequest()
//                }
//            }).check()
    }
    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!
       mMap.setOnMarkerClickListener(this)
        setUpMap()
        getAllCenters()
    }
    private fun getAllCenters() {

        var GSG = LatLng(31.5129811, 34.4453482)
        var GGateway = LatLng(31.5138529, 34.4274498)
        var VisionPlus = LatLng(31.5177906, 34.4465647)
        var ComputerLand = LatLng(31.5207793,34.4530177)

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(GSG, 12.0f))

        val  markersArray =  ArrayList<MarkerData>();
        markersArray.add(MarkerData(31.5129811,34.4453482,"Gaza Sky Geeks","Founded in 2011" +
                " in partnership with Google and the international NGO Mercy Corps",R.drawable.common_google_signin_btn_icon_dark))

        markersArray.add(MarkerData(31.5177906, 34.4465647,"Vision Plus","Provide formal training and certified international curricula" +
                "  for information technology from companies Microsoft - Cisco -5 Adobe ",R.drawable.common_google_signin_btn_icon_dark))
        markersArray.add(MarkerData(31.5138529, 34.4274498,"GGGateway","GGGateway is a social enterprise that has a hybrid business model aiming to build ICT graduates capacity to employ them in outsourcing projects",R.drawable.common_google_signin_btn_icon_dark))
        markersArray.add(MarkerData(31.5207793, 34.430177,"Computer Land","Is a company specialized in IT training. Whether you are a person or a company",R.drawable.common_google_signin_btn_icon_dark))

        for( i in 0 until  markersArray.size) {
            createMarker(markersArray[i].latitude, markersArray[i].Longitude, markersArray[i].title,
                markersArray[i].snippet, markersArray[i].iconResID);
        }
        val polygon1 = mMap.addPolygon(
            PolygonOptions()
                .clickable(true)
                .fillColor(Color.GREEN)
                .strokeColor(Color.YELLOW)
                .strokeWidth(5.0f)
                .add(
                    GSG,
                    GGateway,
                    VisionPlus,
                    ComputerLand
                )
        )
        polygon1.tag = "alpha"
        mMap.setOnPolygonClickListener { polygon ->
            if (polygon.tag!! == "alpha") {
                Log.e("hzm", "Polygon 1 Clicked")
            }
        }
    }

    private fun setUpMap() {
        // mMap.isMyLocationEnabled = true
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true

    }
    private fun createMarker(latitude: Double, longitude: Double, title: String, snippet: String, iconResID: Int): Marker? {

            return mMap.addMarker(MarkerOptions()
                .position(LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet))


//                .icon(BitmapDescriptorFactory.fromResource(iconResID)));
        }

    private fun checkGPSEnable() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id
                ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
               requireActivity().finish()
            })
        val alert = dialogBuilder.create()
        alert.show()
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
      when {
          p0!!.title == "Gaza Sky Geeks" ->{
              val i=Intent(requireContext(),CenterCourseActivity::class.java)
              i.putExtra(AppConstants.GSG,AppConstants.GSG)
              requireActivity().startActivity(i)
          Toast.makeText(requireContext(),"gsg",Toast.LENGTH_LONG).show()
          }
          p0.title == "Vision Plus" ->{
              val i=Intent(requireContext(),CenterCourseActivity::class.java)
              i.putExtra(AppConstants.VISIONPLUS,AppConstants.VISIONPLUS)
              requireActivity().startActivity(i)
          Toast.makeText(requireContext(),"vision",Toast.LENGTH_LONG).show()
          }
          p0.title == "GGGateway" ->{
              val i=Intent(requireContext(),CenterCourseActivity::class.java)
              i.putExtra(AppConstants.GGATAWAY,AppConstants.GGATAWAY)
              requireActivity().startActivity(i)
          Toast.makeText(requireContext(),"GGGateway",Toast.LENGTH_LONG).show()
          }
          p0.title == "Computer Land" ->{
              val i=Intent(requireContext(),CenterCourseActivity::class.java)
              i.putExtra(AppConstants.COMPUTERLAND,AppConstants.COMPUTERLAND)
              requireActivity().startActivity(i)
          Toast.makeText(requireContext(),"Computer Land",Toast.LENGTH_LONG).show()
          }


      }
        return true
    }


}

//private fun viewAllData() {
//    //     Toast.makeText(requireContext(),"sucess get center", Toast.LENGTH_LONG).show()
//
//    val docRef = db!!.collection("center").document("00iKIgoMH6Acv8f2v9JT")
//    docRef.get()
//        .addOnSuccessListener { document ->
//            if (document != null) {
//                Toast.makeText(
//                    requireContext(),
//                    "sucess get center +${document.data}",
//                    Toast.LENGTH_LONG
//                ).show()
//
//                Log.d("TAG", "DocumentSnapshot data: ${document.data}")
//            } else {
//                Log.d("TAG", "No such document")
//            }
//        }
//        .addOnFailureListener { exception ->
//            Log.d("TAG", "get failed with ", exception)
//        }
//}
