package nlpexample.aempathy.com.nlpexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.aempathy.NLPAndroid.TopicClassifier
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import nlpexample.aempathy.com.nlpexample.R.layout.activity_main

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        val tc = TopicClassifier(applicationContext)
        var result = "No result yet"

        GlobalScope.launch {
            val first = async { tc.classifyTopic("This is my smartphone and it is great.") }
            result = first.await()
        }
        Toast.makeText(applicationContext,
            result,
            Toast.LENGTH_LONG).show()

    }
}
