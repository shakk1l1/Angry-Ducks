package com.example.angryducks

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.util.SparseIntArray
import androidx.fragment.app.FragmentActivity


class LevelView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback, Runnable {
    lateinit var canvas: Canvas

    // visual

    val textPaint = Paint()
    var screenWidth = 0f
    var screenHeight = 0f
    var drawing = false
    var SkyColor = Paint()

    // JSP
    lateinit var thread: Thread
    val slingshot = Slingshot()

    // object ans classes
    val bloc = Obstacle(5f, 100f, 200f, 0f, 10f, this)
    val pig = Pig(1.0, 25f, 500f, 500f, 0.0, 0.0, 0.0, 0.0)
    val bird1 = Bird(this, pig, bloc) // peut-etre pas
    val bird2 = Bird(this, pig, bloc)
    val bird3 = Bird(this, pig, bloc)

    //var
    var birdavailable = 0
    var pigleft = 0
    val birds = arrayOf(bird1, bird2, bird3)
    var gameOver = false
    var totalElapsedTime = 0.0


    val activity = context as FragmentActivity
    val soundPool: SoundPool
    val soundMap: SparseIntArray
    //val gestureDetector = GestureDetector(this)

    init {
        SkyColor.color = Color.BLUE
        textPaint.textSize = screenWidth / 20
        textPaint.color = Color.BLACK
        birdavailable = 3
        pigleft = 1

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(audioAttributes)
            .build()

        soundMap = SparseIntArray(3)
        //soundMap.put(0, soundPool.load(context, R.raw.target_hit, 1))
        //soundMap.put(1, soundPool.load(context, R.raw.canon_fire, 1))
        //soundMap.put(2, soundPool.load(context, R.raw.blocker_hit, 1))
    }

    fun playObstacleSound() {
        soundPool.play(soundMap.get(2), 1f, 1f, 1, 0, 1f)
    }

    fun playCibleSound() {
        soundPool.play(soundMap.get(0), 1f, 1f, 1, 0, 1f)
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

    }

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(
                0f, 0f, canvas.width.toFloat(),
                canvas.height.toFloat(), SkyColor
            )
            /*
            val formatted = String.format("%.2f", timeLeft)
            canvas.drawText(
                "Il reste $formatted secondes. ",
                30f, 50f, textPaint
            )
            */
            //canon.draw(canvas)
            for (bird in birds) {
                if (bird.status_launched)//à changer
                    bird.draw(canvas)
            }

            bloc.draw(canvas)
            pig.draw(canvas)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    /*
    fun updatePositions(elapsedTimeMS: Double) {
        val interval = elapsedTimeMS / 1000.0
        bloc.update(interval)
        pig.update()//(interval)
        bird.update(interval)
    }

     */

    override fun run() {
        var previousFrameTime = System.currentTimeMillis()
        while (drawing) {
            val currentTime = System.currentTimeMillis()
            var elapsedTimeMS: Double = (currentTime - previousFrameTime).toDouble()
            totalElapsedTime += elapsedTimeMS / 1000.0
            updatePositions(elapsedTimeMS) //ça buguait chez mwa à cause de ça
            draw()
            previousFrameTime = currentTime
        }
    }

    private fun updatePositions(elapsedTimeMS: Double) {
        val interval = elapsedTimeMS / 1000.0
        for (bird in birds){
            if (bird.status_launched){
                bird.update(interval)
            }
        }

    }

    fun gameOver() {
        drawing = false
        /*
        showGameOverDialog(R.string.win)

         */
        gameOver = true
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int,
                                width: Int, height: Int) {}

    override fun surfaceCreated(holder: SurfaceHolder) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {}
/*
    fun showGameOverDialog(messageId: Int) {
        class GameResult: DialogFragment() {
            @SuppressLint("StringFormatInvalid")
            override fun onCreateDialog(bundle: Bundle?): Dialog {
                val builder = AlertDialog.Builder(getActivity())
                builder.setTitle(resources.getString(messageId))
                builder.setMessage(
                    resources.getString(
                        R.string.results_format, shotsFired, totalElapsedTime
                    )
                )
                builder.setPositiveButton(R.string.reset_game,
                    DialogInterface.OnClickListener { _, _->newGame()}
                )
                return builder.create()
            }

            fun show(ft: Any, s: String) {

            }

            fun setCancelable(b: Boolean) {

            }
        }

        activity.runOnUiThread(
            Runnable {
                val ft = activity.supportFragmentManager.beginTransaction()
                val prev =
                    activity.supportFragmentManager.findFragmentByTag("dialog")
                if (prev != null) {
                    ft.remove(prev)
                }
                ft.addToBackStack(null)
                val gameResult = GameResult()
                gameResult.setCancelable(false)
                gameResult.show(ft,"dialog")
            }
        )
    }


 */
    fun newGame() {
        /*
        cible.resetCible()
        obstacle.resetObstacle()
        timeLeft = 60.0
        balle.resetCanonBall()
        shotsFired = 0

         */
        totalElapsedTime = 0.0
        drawing = true
        if (gameOver) {
            gameOver = false
            thread = Thread(this)
            thread.start()
        }
    }

    fun shootbird(diffx: Double, diffy: Double){
        var bird = birds[3-birdavailable]
        /*
        slingshot.align(diffx, diffy)

         */
        bird.launch(diffx, diffy)
    }
}

