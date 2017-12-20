## WordCheck


### I. Preconditions:

There are no peripheral and OS (considering widely used operating systems - Linux, Mac, and Windows) dependencies for our project though it requires at least Java 1.8. One hardware constraint is that to load the pre-trained model we have included in our repository, you need at least 8GB of RAM. For the Android application, minimum Operating system - Android version 4.0.


### II. Supporting Files:

**Traning Models**:

ModelTraining, and ModelTrainingSpanish are to construct and store the model. Tests are embedded in Test.java class (they can be called from Main.java for testing) and execution steps are explained in the next section.


**AndroidProject**:

Simply install the LanguageChecker.apk on your android device and run it. There are two main options for the Android UI.

Tap to Open Mic - This is the speech to text part. The Google speech option would open and upon speaking, the language checker would run on the after speaking.

Check Now dialog box: Here you can enter any text to be checked and by clicking the check now button, the checker would run.


**Speech API**:

This project does not include any non-standard libraries but requires Google’s Speech API running in Google Cloud for GUI to accept speech input with user’s credentials (we have included our credentials - JSON file - in our project for testing purposes)

To create an instance of google speech API in google cloud, the instructions can be found [here](https://cloud.google.com/speech/docs/getting-started) and the path to the JSON file after creating a speech API instance has to be included in the directory “``` speech_to_text ```” under the name “``` google_speech_api.json ```”.


### III. Execution:

The project consists of 5 main java projects called ModelTraining, ModelTrainingSpanish, AndroidProject, WordCheckUI, WordCheckCLUI.


**ModelTraining**:

This part trains the model based on the text files in dataset/TrainingFiles/. Moreover, it trains a spell checker based on dataset/words.txt. It stores the trained model and the spell checker in dataset/NGramData/. Then, a user should take these stored files and use them in AndroidProject, WordCheckUI, WordCheckCLUI as described in the corresponding section.

This part can be easily compiled and ran using Main.java if opened in IntelliJ IDEA project. The model would be trained on the text files put in dataset/TrainingFiles/ (Currently there is just one text there, the user can add text files here to train 4gram model on a different dataset.)

It also has a Test.java class to test the classes in this part. Test results of data structures are printed when the user instantiates an object of this class through Main.java.


**ModelTrainingSpanish**:

This part is similar to ModelTraining part, the only difference is the language to be trained on. The same steps should be used in this part as described above.


**AndroidProject**:

If you wish to add your own Ngram model, you can simply open the project, copy the two Ngram files (wordDict.ser & prevWord4.ser) into the Asset folder and create a new apk file after that.
To run the application from Android Studio, make sure you have connected the phone to the PC via USB, and simply click run. You can also view the log in the logview console below to see how the data flows within the application.

In order to run the application without using Android Studio:
Simply install the LanguageChecker.apk on your android device and run it, wait for one minute as the model gets loaded. There are two main options for the Android UI

Tap to Open Mic - This is the speech to text part. The Google speech option would open and upon speaking, the language checker would run on the after speaking.

Check Now dialog box: Here you can enter any text to be checked and by clicking the check now button, the checker would run.


**WordCheckUI**:

The pre-trained models (object files) have to be included in the “``` WordCheckUI/dataset/NgramData/ ```” before running GUI. Appropriate models have to be included for English and Spanish language checker. GUI can be run by opening WordCheckUI as a project in IntelliJ IDE and build(and run) the project by using “``` src ```” folder as the source folder. For the speech input to work, make sure that “``` speech_to_text ```” folder is one folder level above WordCheckUI (which is the case in this BitBucket repository) and it contains “``` google_speech_api.json ```” and has the following files:

target/classes/com/example/speech/QuickstartSample.class
target/classes/com/example/speech/Recognize.class
target/classes/com/example/speech/Recognize$1ResponseApiStreamingObserver.class
target/speech-google-cloud-samples-1.0.0-jar-with-dependencies.jar

*One quick tip *: The speak button is configured in a way that, it records audio as long as you keep the button pressed. So press, speak and release to provide a speech input.


**WordCheckCLUI**:

The pre-trained models (object files) have to be included in the “```  WordCheckUI/dataset/NgramData/ ```” before running GUI. Appropriate models have to be included for English and Spanish language checker. After moving into the directory “``` CLUInGUI/WordCheckerCLUI/src/ ```”, command line program can be compiled using the command:
```
<javac CLUI.java>
```
And run using the command:
```
<java CLUI>
```
Once the pre-trained model is loaded, to input the text for language checking, just type the following:

“```-line```” and press enter. Now the program is ready to take input strings to check. Include the string that needs to be checked and press enter to get the wrong parts highlighted.

To input a text file, type the following:

“```-file```” and press enter. Now the program is ready to take “``` file name ```” as input. Include the file name along with the path relative to the current working directory and press enter. The entire file is taken as input to the language checker and wrong parts of the sentence are highlighted.
You can type “```:quit```” to exit the program and “```-help```” to see the options available.

The project also has pretrained models stored in ```TrainedModels``` folder. PC and Android differ in size of the data structure because it takes too much time to load the project in Android.
