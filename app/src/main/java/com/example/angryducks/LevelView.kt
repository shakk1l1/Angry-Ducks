package com.example.angryducks

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.SparseIntArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.example.angryducks.Collision.Companion.birdcollisioner
import com.example.angryducks.Collision.Companion.groundheight


class LevelView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback, Runnable, Pigdobservable {
    private lateinit var canvas: Canvas
    //----------------------------------------------------------------------------------------------
    // Variables init
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // visual

    private val textPaint = Paint()
    var screenWidth = 2000f
    var screenHeight = 1000f
    private var drawing = false
    private var skycolor = Paint()

    //----------------------------------------------------------------------------------------------
    // Threads

    private lateinit var thread: Thread

    //----------------------------------------------------------------------------------------------
    // object ans classes

    private val bloc = ObstacleRectangle(1000.0, 400.0, 0.0, 1.0, 100.0, 300.0)
    private val pig1 = Pig(this, 20.0, 25f, 450f, 550f, 0.0, 100.0, 0.0f, 0f, 20f, 888800, false)
    private val pig2 = Pig(this, 500.0, 90f, 850f, 550f, 0.0, 100.0, 0.0f, 0f, 90f, 100, false)
    private val pig3 = Pig(this, 20.0, 25f, 1050f, 550f, 0.0, 100.0, 0.0f, 0f, 20f, 100, false)
    private val pig4 = Pig(this, 20.0, 25f, 1250f, 550f, 0.0, 100.0, 0.0f, 0f, 20f, 100, false)

    private val bird1 = Bird(this, groundheight,20f,5555.0)
    private val bird2 = Bird(this, groundheight,20f,1.0)
    private val bird3 = Bird(this, groundheight,20f,0.0000000056)
    private val bird4 = Bird(this, groundheight,20f,5416464.0)
    private val bird5 = Bird(this, groundheight,50f,666.0)
    private val ground = Ground(groundheight, 0f, 0f, 0f, this)
    override val observers: ArrayList<Pigobserver> = ArrayList()

    //----------------------------------------------------------------------------------------------
    //var

    private var birdavailable = 0
    private var birdsshot = 0
    var pigleft = 4
        set(value){
            field = value
            hasUpdated()

        }
    private val pigs = arrayOf(pig1, pig2, pig3, pig4)
    private val birds = arrayOf(bird1, bird2, bird3, bird4, bird5)
    private var gameOver = false
    private var totalElapsedTime = 0.0
    private var waittime = 0.0
    private var fixwaitime = 0.0   //cmb de temp avant prochain oiseau
    private var maxwaittime = 100.0   //cmb de temp avant fin du jeu
    //var TempsFinDernieroiseau = 0L

    //----------------------------------------------------------------------------------------------
    //sound

    private val activity = context as FragmentActivity
    private val soundPool: SoundPool
    private val soundMap: SparseIntArray


    //----------------------------------------------------------------------------------------------
    //Fonctions
    //----------------------------------------------------------------------------------------------

    init {
        skycolor.color = Color.parseColor("#add8e6")
        textPaint.textSize = screenWidth / 10
        textPaint.color = Color.BLACK
        birdavailable = 5
        this.pigleft = 4
        waittime = 0.0
        for (pig in pigs){
            this.add(pig)
        }


        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(audioAttributes)
            .build()

        soundMap = SparseIntArray(3)
    }

    fun pause() {
        drawing = false
        thread.join()
    }

    fun resume() {
        drawing = true
        thread = Thread(this)
        thread.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) { //en fonction de l'écran
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()
        ground.screenHeight = screenHeight
        ground.screenWidth = screenWidth
        ground.setRect()
        textPaint.textSize = w / 25f
        textPaint.isAntiAlias = true
    }

    private fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(
                0f, 0f, canvas.width.toFloat(),
                canvas.height.toFloat(), skycolor
            )

            canvas.drawText(
                "Il reste $birdavailable oiseaux et $pigleft cochons",
                50f, 70f, textPaint
            )

            bloc.draw(canvas)
            for(segment in bloc.obstaacles){
                segment.draw(canvas)
            }


            for (bird in birds) {
                if (bird.statuslaunched)
                    bird.draw(canvas)
            }

            ground.draw(canvas)

            for(pig in pigs) {
                pig.draw(canvas)
            }
            bloc.draw(canvas)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    override fun run() {
        var previousFrameTime = System.currentTimeMillis()
        while (drawing) {
            val currentTime = System.currentTimeMillis()
            val elapsedTimeMS: Double = (currentTime - previousFrameTime).toDouble()
            totalElapsedTime += elapsedTimeMS / 1000.0
            updatePositions(elapsedTimeMS)
            draw()
            previousFrameTime = currentTime
        }
    }

    private fun updatePositions(elapsedTimeMS: Double) {
        val interval = elapsedTimeMS / 1000.0

        birdcollisioner(birds, pigs, interval, bloc.obstaacles)

        waittime -= interval
        //var temps = System.currentTimeMillis() - TempsFinDernieroiseau
        //println(temps)
        if (pigleft == 0){
            gameOver = true
            drawing = false
            showGameOverDialog(R.string.win)
        }

        else if(birdavailable == 0 && waittime <= -maxwaittime /*&& temps>10000L*/){
            gameOver = true
            drawing = false
            showGameOverDialog(R.string.lost)
        }

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int,
                                width: Int, height: Int) {}

    override fun surfaceCreated(holder: SurfaceHolder) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    private fun showGameOverDialog(messageId: Int) {        //message de fin de jeu
        class GameResult: DialogFragment() {
            @SuppressLint("StringFormatInvalid")
            override fun onCreateDialog(bundle: Bundle?): Dialog {
                val builder = AlertDialog.Builder(activity)
                builder.setTitle(resources.getString(messageId))
                builder.setMessage(
                    resources.getString(
                        R.string.results_format, birdsshot, totalElapsedTime
                    )
                )
                builder.setPositiveButton(R.string.reset_game
                ) { _, _ -> newGame() }
                return builder.create()
            }
        }

        activity.runOnUiThread {
            val ft = activity.supportFragmentManager.beginTransaction()
            val prev =
                activity.supportFragmentManager.findFragmentByTag("dialog")
            if (prev != null) {
                ft.remove(prev)
            }
            ft.addToBackStack(null)
            val gameResult = GameResult()
            gameResult.isCancelable = false
            gameResult.show(ft, "dialog")
        }
    }



    private fun newGame() {         //new game reset
        birdavailable = 5
        birdsshot = 0
        totalElapsedTime = 0.0
        drawing = true
        for(bird in birds){bird.reset()}
        for(pig in pigs) {pig.reset()}
        this.pigleft = 4
        if (gameOver) {
            gameOver = false
            thread = Thread(this)
            thread.start()
        }
        //pig.reset()
    }

    fun shootbird(diffx: Double, diffy: Double){       // shoot bird
        if (birdavailable > 0){
            val bird = birds[birdavailable-1]
            /*
            slingshot.align(diffx, diffy)

             */
            if (waittime <= 0.0) {
                bird.launch(diffx, diffy)
                birdavailable --
                birdsshot ++
                waittime = fixwaitime
                //TempsFinDernieroiseau = System.currentTimeMillis()

            }
            else{
                val formatted = String.format("%.2f", waittime)

                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "wait $formatted second", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else{
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, "no birds left", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun cassepaslesc(){//pour le bouton New Game prcq fun Newgame est private
        newGame()
    }
}
