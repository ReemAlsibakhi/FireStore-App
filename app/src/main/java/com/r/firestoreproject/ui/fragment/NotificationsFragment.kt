package com.r.firestoreproject.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.r.dbfirestrore.adapter.CourseAdapter
import com.r.dbfirestrore.model.AppConstants
import com.r.dbfirestrore.model.Course
import com.r.firestoreproject.R
import kotlinx.android.synthetic.main.fragment_course.avi
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() {
    var db: FirebaseFirestore? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Firebase.firestore
       viewAllSaved()

    }

    private fun viewAllSaved() :ArrayList<Course>{
        startAnim()

        val data = ArrayList<Course>()
        db!!.collection(AppConstants.COLLECTION_COURSE)
            .whereEqualTo("isFav",true)
            .get()
            .addOnSuccessListener { result ->
                stopAnim()
                for (document in result) {
                    Toast.makeText(requireContext(),"sucess get saved", Toast.LENGTH_LONG).show()
                    Log.d("saved", "${document.id} => ${document.data}")
                    val course = document.toObject(Course::class.java)
                    course.id = document.id
                    data.add(course)
                    rv_saved.layoutManager= LinearLayoutManager(requireActivity())
                    val adapter= CourseAdapter(requireActivity(),data, db!!)
                    rv_saved.adapter=adapter

                }
            }
            .addOnFailureListener { exception ->
                stopAnim()

                Toast.makeText(requireContext(),"failed get", Toast.LENGTH_LONG).show()

                Log.w("saved", "Error getting documents.", exception)
            }
        return data
    }

    fun startAnim() {
        avi.show()
        // or avi.smoothToShow();
    }
    fun stopAnim() {
        avi.hide()
        // or avi.smoothToHide();
    }

}