package com.r.dbfirestrore.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.r.dbfirestrore.model.AppConstants
import com.r.dbfirestrore.model.Course
import com.r.firestoreproject.R
import com.r.firestoreproject.ui.activity.AddCourseActivity
import com.r.firestoreproject.ui.activity.LoginActivity
import kotlinx.android.synthetic.main.course_item.view.*

class CourseAdapter(var activity: Activity, var data: ArrayList<Course>, val db: FirebaseFirestore) :
    RecyclerView.Adapter<CourseAdapter.MyViewHolder>() {
  //  var  db = Firebase.firestore


    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        //content
        var name = item.tv_name
        var hours = item.tv_hoursNum
        var price = item.tv_price
        var center = item.tv_center
        //title
        var name2 = item.tv2_name
        var description=item.tv_description
        var trainer=item.tv_trainer
        var price2 = item.tv2_price
        var date_expiry = item.tv_expiryDate
        var hour2 = item.tv2_hours
        var btn_login = item.btn_login

       var btn_pop=item.btn_popup
        var fc=item.folding_cell
        var cardview = item.cardview

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(activity).inflate(R.layout.course_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       //  holder.id.text=data[position].id.toString()
        holder.name.text = data[position].name
        holder.hours.text=data[position].hoursNum.toString()+"h"
        holder.price.text=data[position].coursePrice.toString()
        holder.center.text=data[position].centerName
        //title

        holder.name2.text=data[position].name
        holder.description.text=data[position].description
        holder.trainer.text=data[position].trainerName
        holder.price2.text=data[position].coursePrice.toString()
        holder.date_expiry.text=data[position].expirydate
        holder.hour2.text=data[position].hoursNum.toString()

     holder.btn_login.setOnClickListener {
       val i= Intent(activity,LoginActivity::class.java)
         activity.startActivity(i)
     }

        // attach click listener to folding cell
        holder.fc.setOnClickListener {
            holder.fc.toggle(false)
        }

         holder.btn_pop.setOnClickListener {
          val popup = PopupMenu(activity, holder.btn_pop)
          popup.menuInflater.inflate(R.menu.main_menu, popup.menu)
          popup.setOnMenuItemClickListener {
              when (it.itemId) {
                  R.id.save -> {
                      save(data[position].id)
                  }
                  R.id.delete -> {
                      deleteCourseById(data[position].id,position)
                  }
                  R.id.update -> {
                     val i=Intent(activity,AddCourseActivity::class.java)
                      i.putExtra(AppConstants.UPDATE,AppConstants.UPDATE)
                      i.putExtra(AppConstants.ID_COURSE,data[position].id)
                      i.putExtra(AppConstants.COURSE_NAME,data[position].name)
                      i.putExtra(AppConstants.DESCRIPTION,data[position].description)
                      i.putExtra(AppConstants.TRAINER_NAME,data[position].trainerName)
                      i.putExtra(AppConstants.HOURS,data[position].hoursNum.toString())
                      i.putExtra(AppConstants.COURSE_PRICE,data[position].coursePrice.toString())
                      i.putExtra(AppConstants.CENTER_NAME,data[position].centerName)
                      i.putExtra(AppConstants.EXPIRY_DATE,data[position].expirydate)
                      activity.startActivity(i)
                  }
              }
              true
          }
          popup.show()
      }


     //   Picasso.get().load(data[position].img).into(holder.image);
      
//        holder.cardview.setOnClickListener {
//            val i = Intent(activity, CourseAdapter::class.java)
//            i.putExtra("name", data[position].name)
//            i.putExtra("avg", data[position].avg.toString())
//
//            activity.startActivity(i)
//        }
//        holder.cardview.setOnLongClickListener {
//            val alertDialog = AlertDialog.Builder(activity)
//            alertDialog.setTitle("Delete Course")
//            alertDialog.setMessage("Are you sure ?")
//            alertDialog.setCancelable(false)
//            alertDialog.setPositiveButton("Yes") { _, _ ->
//                deleteCourseById(data[position].id!!, position)
//                data.removeAt(position)
//                notifyItemRemoved(position)
//                notifyItemRangeRemoved(position, data.size)
//
//                Toast.makeText(activity, "Yes", Toast.LENGTH_SHORT).show()
//            }
//            alertDialog.setNegativeButton("No") { _, _ ->
//                Toast.makeText(activity, "No", Toast.LENGTH_SHORT).show()
//            }
//
//            alertDialog.create().show()
//            true
//        }
//        holder.btn_edit.setOnClickListener {
//            val i = Intent(activity, UpdateActivity::class.java)
//            i.putExtra("id", data[position].id)
//            i.putExtra("name", data[position].name)
//            i.putExtra("average", data[position].avg.toString())
//            activity.startActivity(i)
//        }
    }

    private fun save(id: String) {
        val course = HashMap<String, Any>()
        course["isFav"] = true
        db.collection(AppConstants.COLLECTION_COURSE)
            .document(id)
            .update(course)
            .addOnSuccessListener {
                Toast.makeText(activity, "Saved Success", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(activity, "Error saving document", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteCourseById(id: String, position: Int) {
        db.collection(AppConstants.COLLECTION_COURSE).document(id)
           .delete().addOnSuccessListener {
             data.removeAt(position)
                notifyItemRemoved(position)
               notifyItemRangeRemoved(position, data.size)
           }.addOnFailureListener {
               Log.e("Delete", "Error deleteing document")
          }
    }


}
