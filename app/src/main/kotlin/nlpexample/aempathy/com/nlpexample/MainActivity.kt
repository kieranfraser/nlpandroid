package nlpexample.aempathy.com.nlpexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.aempathy.NLPAndroid.TopicClassifier
import nlpexample.aempathy.com.nlpexample.R.layout.activity_main

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        val tc = TopicClassifier(applicationContext)
        Log.d("Example",
                tc.classifyTopic("Hey, Richard are you going to the football match tomorrow night?"))
    }
}
