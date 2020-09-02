package com.r.dbfirestrore.model

import android.os.Parcel
import android.os.Parcelable

data class Course(var id:String, var name:String, var description:String,var trainerName:String, var hoursNum:Int, var coursePrice:Double, var centerName:String,var expirydate:String,var isFav:Boolean){

    constructor():this(" ","","","",0,0.0," ","",false)

    constructor( name:String , description:String,trainerName:String,hoursNum:Int, coursePrice:Double ,centerName:String ,expirydate:String):
            this ("",name,description,trainerName,hoursNum,coursePrice ,centerName,expirydate,false)
}

//    constructor(parcel: Parcel) : this(
//        parcel.readString()!!,
//        parcel.readString()!!,
//        parcel.readInt(),
//        parcel.readDouble(),
//        parcel.readString()!!
//    ) {
//
//    }


//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(id)
//        parcel.writeString(name)
//        parcel.writeString(trainerName)
//        parcel.writeInt(hoursNum)
//        parcel.writeDouble(coursePrice)
//        parcel.writeString(centerName)
//
//
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }



  //  constructor( name:String , average:Double , image:String  ):this ("",name,average ,image)
//
//
//    companion object CREATOR : Parcelable.Creator<Course> {
//        override fun createFromParcel(parcel: Parcel): Course {
//            return Course(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Course?> {
//            return arrayOfNulls(size)
//        }
//    }
//
//}
//
//
//


