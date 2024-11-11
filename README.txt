For running this application I completed the following steps:

Install gradle-8.10.2
Install jdk-17
Install Android Studio Ladybug -
	Ensure the Gradle and Kotlin plugins are installed, by going to Settings < Plugins < Installed
	Ensure they are active, they have a blue checkmark next to them.

Environment Variables:
Make sure your JAVA_HOME points to jdk-17
Make sure C:\Gradle\gradle-8.10.2 is on your Path 

Running the App:
Open the project in Android Studio, if you import it import it as an android app. 
The first thing you should do is sync the project with the gradle files. There is a button for this in Android Studio, its near the settings button, it looks like an elephant with an arrow pointing down.
Then, make the module by clicking the hammer icon on that same line of icons near settings.
Now make sure your run configuration is an Android App,  named app, with the module "vcentials-android.app.main"
Use the default APK

Hit run, and it should pull up the Medium Phone API and start the app. 