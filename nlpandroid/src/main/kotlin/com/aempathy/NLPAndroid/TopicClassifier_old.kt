/*
package com.aempathy.NLPAndroid

import android.content.Context
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer
import org.deeplearning4j.models.word2vec.Word2Vec
import org.tensorflow.lite.Interpreter
import java.util.*
import java.io.*


class TopicClassifier(mContext: Context){

    private val TAG = "NLPAndroid: "+TopicClassifier::class.java.simpleName

    private val VECTOR_DIM = 100
    private val LABELS = arrayOf("Adult", "Arts & Entertainment", "Autos & Vehicles",
        "Beauty & Fitness", "Books & Literature", "Business & Industrial",
        "Computers & Electronics", "Finance", "Food & Drink", "Games",
        "Health", "Hobbies & Leisure", "Home & Garden",
        "Internet & Telecom", "Jobs & Education", "Law & Government",
        "News", "Online Communities", "People & Society", "Pets & Animals",
        "Real Estate", "Reference", "Science", "Sensitive Subjects",
        "Shopping", "Sports", "Travel")

    private val context = mContext
    private var word2Vec: Word2Vec?=null
    private var tflite: Interpreter?=null

    init {
        GlobalScope.launch {
            setupWord2Vec()
            setupTflite()
        }

    }

    suspend private fun setupWord2Vec(){
        val id = context.resources.getIdentifier("news_word_vector", "raw", context.packageName)
        val file = File(context.getFilesDir().toString()+ File.separator+"news_word_vector.txt")
        GlobalScope.async {

            try {
                val inputStream = context.resources.openRawResource(id)
                val fileOutputStream = FileOutputStream(file)

                // prepare the file for reading
                val chapterReader = InputStreamReader(inputStream)
                val buffreader = BufferedReader(chapterReader)

                var line: String?

                // read every line of the file into the line-variable, on line at the time
                do {
                    line = buffreader.readLine()
                    // do something with the line
                    Log.d("Example ", line)
                } while (line != null)

                val buf = ByteArray(1024)
                var len = inputStream.read(buf)
                while(len > 0) {
                    fileOutputStream.write(buf,0,len);
                    len = inputStream.read(buf)
                }

                fileOutputStream.close();
                inputStream.close();
            } catch (e1: IOException) {}
        }.await()
        //word2Vec = WordVectorSerializer.readWord2VecModel(file)
    }

    suspend private fun setupTflite(){
        val id = context.resources.getIdentifier("topic_model", "raw", context.packageName)
        val file = File(context.getFilesDir().toString()+ File.separator+"topic_model.tflite")
        GlobalScope.async {

            try {
                val inputStream = context.resources.openRawResource(id)
                val fileOutputStream = FileOutputStream(file)

                val buf = ByteArray(1024)
                var len = inputStream.read(buf)
                while(len > 0) {
                    fileOutputStream.write(buf,0,len);
                    len = inputStream.read(buf)
                }

                fileOutputStream.close();
                inputStream.close();
            } catch (e1: IOException) {}
        }.await()
        tflite = Interpreter(file)
    }

    fun classifyTopic(text: String): String {

        val inp = arrayOf(sentenceToVector(text.toLowerCase()))
        // Check if input == [0.0.. etc], classification, 'unknown'
        val outputSize = FloatArray(27)

        val out = arrayOf(outputSize)

        tflite?.run(inp,out);
        Log.d(TAG, "Classification value: "+LABELS[maxIndex(out[0])])
        return LABELS[maxIndex(out[0])]
    }

    private fun maxIndex(probabilities: FloatArray): Int {
        val max = probabilities.max()
        return probabilities.indexOf(max?:0f)
    }

    private fun sentenceToVector(sentence: String): FloatArray {
        var sentenceVec = DoubleArray(VECTOR_DIM)

        val wordVectors = arrayListOf<DoubleArray>()
        val st = StringTokenizer(sentence)
        while (st.hasMoreTokens()) {
            val token = st.nextToken()
            val wordVector = word2Vec?.getWordVector(token)
            wordVectors.add(wordVector?: DoubleArray(VECTOR_DIM))
        }
        if (!wordVectors.isEmpty()) {
            sentenceVec = averageVectors(wordVectors)
        }
        Log.d(TAG, Arrays.toString(sentenceVec))
        return doubleArrayToFloat(sentenceVec)
    }

    private fun doubleArrayToFloat(doubleArray: DoubleArray?): FloatArray{
        val array = FloatArray(VECTOR_DIM)
        var i = 0
        for(value in doubleArray?: doubleArrayOf()){
            array[i] = value.toFloat()
            i++
        }
        return array
    }

    private fun averageVectors(vectors: ArrayList<DoubleArray>): DoubleArray {
        val n = vectors.size
        val s = VECTOR_DIM

        val V = DoubleArray(s)
        for (i in 0 until n)
            for (j in 0 until s)
                V[j] = V[j] + vectors[i][j]
        //V.putScalar(j, V.getDouble(j) + vectors.get(i).getDouble(j));

        var rms = 0.0
        for (i in 0 until s)
            rms = rms + (V[i] * V[i])
        //rms = rms + V.getDouble(i) * V.getDouble(i);
        rms = Math.sqrt(rms / s)
        if (rms != 0.0) {
            for (i in 0 until s)
                V[i] = V[i] / rms
            // V.putScalar(i, (V.getDouble(i) / rms) );
        }
        return V
    }
}*/
