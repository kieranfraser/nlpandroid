package nlpexample.aempathy.com.nlpexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.aempathy.NLPAndroid.NLP
import com.aempathy.NLPAndroid.models.Entity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nlpexample.aempathy.com.nlpexample.R.layout.activity_main

class MainActivity : AppCompatActivity() {

    private val TAG = "Example: "+MainActivity::class.java.simpleName

    private lateinit var nlp:NLP
    var result = "No result yet"
    var nerResult = mutableListOf<Entity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)


        /* Initialise the Topic Classifier with context and
        *  boolean indicating use of local-only
        * (i.e. if text is not to be sent via internet to server)
        */

        nlp = NLP(getApplicationContext(), false)

        /*
        * Using kotlin Coroutine, infer topic and update
        */
        runBlocking {
            launch (Dispatchers.Default) {
                val gResult = async { nlp.classifyTopic("The recipe for lasagne is?") }
                result = gResult.await()
            }
        }

        /* Toast illustrating result */
        Toast.makeText(applicationContext,
            result,
            Toast.LENGTH_LONG).show()

        /*
        * Using kotlin Coroutine, infer topic and update
        */
        runBlocking {
            launch (Dispatchers.Default) {
                val gResult = async { nlp.namedEntityRecognition("Hey Kieran, the recipe for lasagne is?") }
                nerResult = gResult.await()
            }
        }

        for(e in nerResult){
            Toast.makeText(applicationContext,
                e.entity+":"+e.type,
                Toast.LENGTH_LONG).show()
        }
    }
}
