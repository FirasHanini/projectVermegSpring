package com.example.test.controllerDemo;


public interface api {
     String internal ="/api/internal";


     public interface Internal extends api{
         String person=internal + "/persons";


         public interface Person extends Internal {
             String ADD="/add";
             String FIND="/find/{id}";
             String DELETE="/delete/{id}";
             String UPDATE="/update/{id}";



         }

     }


}

