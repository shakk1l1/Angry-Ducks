package com.example.angryducks

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.AudioAttributes
import android.media.MediaPlayer
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
    val startpositionx = 120f
    val startpositiony = 120f

    //sound

    private val activity = context as FragmentActivity
    private val soundPool: SoundPool
    private val soundMap: SparseIntArray
    private var mediaPlayer = MediaPlayer.create(context, R.raw.themesong)
    private var mediaLost = MediaPlayer.create(context, R.raw.levelfailed)
    private var mediaWin = MediaPlayer.create(context, R.raw.levelwin)
    private var mediaBirdLaunch = MediaPlayer.create(context, R.raw.birdlaunch)
    var mediaPigdead: MediaPlayer = MediaPlayer.create(context, R.raw.pigdead)


    //----------------------------------------------------------------------------------------------
    // Threads

    private lateinit var thread: Thread

    //----------------------------------------------------------------------------------------------
    // object ans classes
    private val bloc1 = ObstacleRectangle(1200.0, 900.0, 0.0, 100.0, 1700.0, 2500, false)
    private val bloc2 = ObstacleRectangle(330.0, 896.0, 0.6, 50.0, 100.0, 2500, false)
    private val bloc3 = ObstacleRectangle(650.0, 600.0, 0.0, 500.0, 30.0, 250, false)
    private val bloc4 = ObstacleRectangle(1275.0, 500.0, 0.0, 30.0, 750.0, 100, false)
    private val bloc5 = ObstacleRectangle(900.0, 600.0, 0.0, 500.0, 30.0, 250, false)
    private val bloc6 = ObstacleRectangle(775.0, 550.0, 0.0, 30.0, 250.0, 250, false)
    private val bloc7 = ObstacleRectangle(1650.0, 600.0, 0.0, 500.0, 30.0, 250, false)
    private val bloc8 = ObstacleRectangle(1900.0, 600.0, 0.0, 500.0, 30.0, 250, false)
    private val bloc9 = ObstacleRectangle(1775.0, 550.0, 0.0, 30.0, 250.0, 100, false)
    private val bloc10 = ObstacleRectangle(685.0, 270.0, 1.1, 35.0, 400.0, 150, false)
    private val bloc11 = ObstacleRectangle(865.0, 270.0, -1.1, 35.0, 400.0, 150, false)
    private val bloc12 = ObstacleRectangle(1685.0, 270.0, 1.1, 35.0, 400.0, 150, false)
    private val bloc13 = ObstacleRectangle(1865.0, 270.0, -1.1, 35.0, 400.0, 150, false)
    private val bloc14 = ObstacleRectangle(775.0, 50.0, 0.0, 100.0, 10.0, 10, false)
    private val bloc15 = ObstacleRectangle(1775.0, 50.0, 0.0, 100.0, 10.0, 10, false)
    private val bloc16 = ObstacleRectangle(1100.0, 375.0, 0.0, 250.0, 30.0, 250, false)
    private val bloc17 = ObstacleRectangle(1450.0, 375.0, 0.0, 250.0, 30.0, 250, false)
    private val bloc18 = ObstacleRectangle(1175.0, 100.0, 1.0, 25.0, 400.0, 150, false)
    private val bloc19 = ObstacleRectangle(1375.0, 100.0, -1.0, 25.0, 400.0, 150, false)
    private val bloc20 = ObstacleRectangle(1275.0, -100.0, 0.0, 100.0, 10.0, 10, false)
    private val bloc21 = ObstacleRectangle(1275.0, 200.0, 0.0, 10.0, 350.0, 100, false)
    private val bloc22 = ObstacleRectangle(1450.0, 800.0, 0.0, 100.0, 10.0, 10, false)
    private val bloc23 = ObstacleRectangle(1100.0, 800.0, 0.0, 100.0, 10.0, 10, false)
    private val bloc24 = ObstacleRectangle(1150.0, 825.0, 0.0, 50.0, 10.0, 10, false)
    private val bloc25 = ObstacleRectangle(1400.0, 825.0, 0.0, 50.0, 10.0, 10, false)
    private val bloc26 = ObstacleRectangle(1425.0, 800.0, 0.0, 10.0, 50.0, 10, false)
    private val bloc27 = ObstacleRectangle(1125.0, 800.0, 0.0, 10.0, 50.0, 10, false)
    private val bloc28 = ObstacleRectangle(1275.0, 825.0, 0.0, 50.0, 175.0, 100, false)
    private val pig1 = Pig(this, 20.0, 1350f, 180f, 0.0, 0.0, 20f, 100, false)
    private val pig2 = Pig(this, 500.0, 1800f, 450f, 0.0, 0.0, 90f, 250, false)
    private val pig3 = Pig(this, 20.0, 1140f, 765f, 0.0, 0.0, 30f, 60, false)
    private val pig4 = Pig(this, 20.0, 1330f, 760f, 0.0, 0.0, 30f, 60, false)
    private val pig5 = Pig(this, 100.0, 710f,490f,0.0, 0.0, 40f, 100, false)

    private val bird1 = Bird(this, Collision.groundheight,30f,5.0)
    private val bird2 = Bird(this, Collision.groundheight,20f,20.0)
    private val bird3 = Bird(this, Collision.groundheight,50f,30.0)
    private val bird4 = Bird(this, Collision.groundheight,30f,50.0)
    private val bird5 = Bird(this, Collision.groundheight,30f,20.0)
    private val bird6 = Bird(this, Collision.groundheight,30f,20.0)
    private val bird7 = Bird(this, Collision.groundheight,30f,20.0)
    private val bird8 = Bird(this, Collision.groundheight,30f,20.0)
    private val bird9 = Bird(this, Collision.groundheight,30f,20.0)
    private val bird10 = Bird(this, Collision.groundheight,90f,1000.0)
    private val ground = Ground(Collision.groundheight, 0f, 0f, this)
    override val observers: ArrayList<Pigobserver> = ArrayList()

//----------------------------------------------------------------------------------------------
//var

    private var birdavailable = 0
    private var birdsshot = 0
    var pigleft = 5
        set(value){
            field = value
            hasUpdated()

        }
    private val pigs = arrayOf(pig1, pig2, pig3, pig4,pig5)
    private val birds = arrayOf(bird1, bird2, bird3, bird4, bird5, bird6, bird7,bird8,bird9,bird10)
    private val objets = arrayOf(bird1, bird2, bird3, bird4, bird5, bird6, bird7,bird8,bird9,bird10,pig1, pig2, pig3, pig4,pig5)
    private val blocs = arrayOf(bloc1,bloc2,bloc3,bloc4,bloc5,bloc6,bloc7,bloc8,bloc9,bloc10,bloc11,bloc12,bloc13,bloc14,bloc15,bloc16,bloc17,bloc18,bloc19,bloc20,bloc21,bloc22,bloc23,bloc24,bloc25,bloc26,bloc27,bloc28)
    private var gameOver = false
    private var totalElapsedTime = 0.0
    private var waittime = 0.0
    private var fixwaitime = 0.0   //cmb de temp avant prochain oiseau
    private var maxwaittime = 10.0   //cmb de temp avant fin du jeu

    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // Functions
    //----------------------------------------------------------------------------------------------

    init {
        skycolor.color = Color.parseColor("#add8e6")
        textPaint.textSize = screenWidth / 10
        textPaint.color = Color.BLACK
        birdavailable = 7
        this.pigleft = pigs.size
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
        mediaPlayer.isLooping = true
        mediaPlayer.setVolume(0.2f,0.2f)
        mediaPlayer.start()
    }

    fun pause() {
        drawing = false
        thread.join()
        mediaPlayer.pause()
    }

    fun resume() {
        drawing = true
        thread = Thread(this)
        thread.start()
        mediaPlayer.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val screenWidth1=screenWidth
        val screenHeight1 = screenHeight
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()
        ground.screenHeight = screenHeight
        ground.screenWidth = screenWidth
        ground.setRect()
        textPaint.textSize = w / 25f
        textPaint.isAntiAlias = true
        for (pig in pigs){
            pig.onsizechanged(screenHeight-screenHeight1)
            //pig.setxp(screenWidth-screenWidth1)
        }
        for (bloc in blocs){
            bloc.onsizechanged(screenHeight-screenHeight1)
            //bloc.setxp(screenWidth-screenWidth1)
        }
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

            canvas.drawCircle(startpositionx,screenHeight - groundheight - startpositiony,7f,textPaint)

            for(bloc in blocs){
                bloc.draw(canvas)
            }


            for (bird in birds) {
                if (bird.statuslaunched)
                    bird.draw(canvas)
            }

            ground.draw(canvas)

            for(pig in pigs) {
                pig.draw(canvas)
            }
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

        birdcollisioner(birds, pigs, objets, interval, blocs)

        waittime -= interval
        if (pigleft == 0){
            gameOver = true
            drawing = false
            mediaWin.seekTo(0)
            mediaWin.start()
            mediaPlayer.pause()
            showGameOverDialog(R.string.win)

        }

        else if(birdavailable == 0 && waittime <= -maxwaittime){
            gameOver = true
            drawing = false
            showGameOverDialog(R.string.lost)
            mediaPlayer.pause()
            mediaLost.seekTo(0)
            mediaLost.start()
        }

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int,
                                width: Int, height: Int) {}

    override fun surfaceCreated(holder: SurfaceHolder) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    private fun showGameOverDialog(messageId: Int) {
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



    private fun newGame() {
        birdavailable = 7
        birdsshot = 0
        totalElapsedTime = 0.0
        drawing = true
        for(bird in birds){bird.reset()}
        for(pig in pigs) {pig.reset()}
        for(bloc in blocs) {bloc.reset()}
        this.pigleft = pigs.size
        if (gameOver) {
            gameOver = false
            thread = Thread(this)
            thread.start()
        }
        mediaWin.seekTo(10000)
        mediaLost.seekTo(10000)
        mediaPlayer.seekTo(0)
        mediaPlayer.start()
    }

    fun shootbird(diffx: Double, diffy: Double){       // shoot bird
        if (birdavailable > 0){
            val bird = birds[birdavailable-1]
            if (waittime <= 0.0) {
                bird.launch(diffx, diffy)
                mediaBirdLaunch.seekTo(0)
                mediaBirdLaunch.start()
                birdavailable --
                birdsshot ++
                waittime = fixwaitime

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
    fun newgamebutton(){
        newGame()
    }
}
