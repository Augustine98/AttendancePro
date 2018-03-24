package com.example.augustine.collegeplus;

/**
 * Created by Augustine on 3/10/2018.
 */

public class SubjectInfo {

    int _id;
    String _subjectname;

     int _attended=0, _bunked=0;
     double _percent=0.0;

    SubjectInfo(){

        _attended=0; _bunked=0;
        _percent=0.0;
    }




   public void attendInitiated()
   {
       _attended++;
       _percent= Double.valueOf(_attended/(_attended+_bunked));
   }

   public void bunkInitiated(){
       _bunked++;
       _percent= Double.valueOf(_bunked/(_attended+_bunked));
   }
}
