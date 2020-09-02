package com.r.firestoreproject.ui.fragment

import android.content.Intent
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
import com.r.firestoreproject.ui.activity.AddCourseActivity
import kotlinx.android.synthetic.main.activity_center_course.*
import kotlinx.android.synthetic.main.fragment_course.*
import kotlinx.android.synthetic.main.fragment_course.avi
import kotlinx.android.synthetic.main.fragment_course.view.*

class CourseFragment : Fragment() {

    var db: FirebaseFirestore? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_course, container, false)
        root.fab.setOnClickListener {
            val i= Intent(requireActivity(),AddCourseActivity::class.java)

            startActivity(i)
        }
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Firebase.firestore
        viewAllCourses()

    }
    private fun viewAllCourses(): ArrayList<Course> {
        startAnim()

        val data = ArrayList<Course>()
        db!!.collection(AppConstants.COLLECTION_COURSE)
            .get()
            .addOnSuccessListener { result ->
                stopAnim()

                for (document in result) {
                 Toast.makeText(requireContext(),"sucess get",Toast.LENGTH_LONG).show()

                    Log.d("Reem", "${document.id} => ${document.data}")
                    val course = document.toObject(Course::class.java)
                    course.id = document.id
                    data.add(course)
                    rv_course.layoutManager= LinearLayoutManager(requireActivity())
                    val adapter=CourseAdapter(requireActivity(),data, db!!)
                    rv_course.adapter=adapter

                }
            }
            .addOnFailureListener { exception ->
                stopAnim()

                Toast.makeText(requireContext(),exception.message.toString(),Toast.LENGTH_LONG).show()

                Log.w("reem", exception.message.toString())

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

