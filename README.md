# Android’s Room in Kotlin with MVVM Architecture 

This example uses MVVM with Room using Kotlin. Let's dive into the steps of doing it.

# Gradle
Add dependencies (you can also add other modules that you need):

dependencies

{
  
  // - - Room Persistence Library
  
    def room_version = "2.2.5"
    implementation "androidx.room:room-runtime:$room_version"
    kapt "androidx.room:room-compiler:$room_version"
 
    implementation "androidx.room:room-ktx:$room_version"
    
     // Lifecycle components
     
    def life_versions = "1.1.1"
    implementation "android.arch.lifecycle:extensions:$life_versions"
    annotationProcessor "android.arch.lifecycle:compiler:$life_versions"
   
   }
   
   
   # Steps to Use This App:
   
   1.Enter UserName and Password and enter Register Button for first time using.
   
   2.Once you Register ,user will navigate to UserList Screen.
   
   3.Next time when you come to this App, just use registed username and password and click login .
   
   
   # NOTE: Use Your Google API Key to interect with Map.
