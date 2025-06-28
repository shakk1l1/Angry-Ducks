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
import kotlin.math.atan
import kotlin.math.hypot


class LevelView @JvmOverloads constructor
    (context: Context,
     attributes: AttributeSet? = null,
     defStyleAttr: Int = 0
): SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback, Runnable, Pigdobservable {

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

    private var showArrow = false
    private var startX = 0f
    private var startY = 0f
    private var endX = 0f
    private var endY = 0f


    //sound

    private val activity = context as FragmentActivity
    private val soundPool: SoundPool
    private val soundMap: SparseIntArray
    private var mediaPlayer = MediaPlayer.create(context, R.raw.themesong)
    private var mediaSecret = MediaPlayer.create(context, R.raw.secret_theme)
    private var mediaLost = MediaPlayer.create(context, R.raw.levelfailed)
    private var mediaWin = MediaPlayer.create(context, R.raw.levelwin)
    private var mediaBirdLaunch = MediaPlayer.create(context, R.raw.birdlaunch)
    var mediaPigdead: MediaPlayer = MediaPlayer.create(context, R.raw.pigdead)
    private var secretachieved = false
    private var nbrachieved = 0


    //----------------------------------------------------------------------------------------------
    // Threads

    private lateinit var thread: Thread

    //----------------------------------------------------------------------------------------------
    // object ans classes
    private var birds: List<Bird> = emptyList()
    private var pigs: List<Pig> = emptyList()
    private var blocs: List<ObstacleRectangle> = emptyList()

    fun setBirds(birds: List<Bird>) {
        this.birds = birds
        invalidate() // pour redessiner la vue avec les nouveaux oiseaux
    }

    fun setPigs(pigs: List<Pig>) {
        this.pigs = pigs
        invalidate()
    }

    fun setBlocks(blocks: List<ObstacleRectangle>) {
        this.blocs = blocks
        invalidate()
    }

    private val ground = Ground(groundheight, 0f, 0f, this)   //Initialisation du sol

    override val observers: ArrayList<Pigobserver> = ArrayList()    //Initialisation de l'observeur des cochons

//----------------------------------------------------------------------------------------------
//var

    private var birdavailable = 0
    private var birdsshot = 0
    var pigleft = 5
        set(value){
            field = value
            hasUpdated()

        }
    //private val pigs = arrayOf(pig1, pig2, pig3, pig4,pig5)             //Initialisation des listes
    //private val birds = arrayOf(bird1, bird2, bird3, bird4, bird5, bird6, bird7,bird8,bird9,bird10)
    //
    private val objets: List<Objet>
        get() = birds + pigs  // ajoute aussi blocks si besoin
    // private val objets = arrayOf(bird1, bird2, bird3, bird4, bird5, bird6, bird7,bird8,bird9,bird10,pig1, pig2, pig3, pig4,pig5)
    //private val blocs = arrayOf(bloc0,bloc1,bloc2,bloc3,bloc4,bloc5,bloc6,bloc7,bloc8,bloc9,bloc10,bloc11,bloc12,bloc13,bloc14,bloc15,bloc16,bloc17,bloc18,bloc19,bloc20,bloc21,bloc22,bloc23,bloc24,bloc25,bloc26,bloc27,bloc28)
    private var gameOver = false
    private var totalElapsedTime = 0.0
    private var waittime = 0.0
    private var fixwaitime = 0.0   //cmb de temp avant prochain oiseau
    private var maxwaittime = 10.0   //cmb de temp avant fin du jeu

    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // Functions
    //----------------------------------------------------------------------------------------------


    fun computeLaunchVector(startX: Float, startY: Float, endX: Float, endY: Float): Pair<Float, Float> {
        val dx = startX - endX
        val dy = startY - endY
        val distance = hypot(dx.toDouble(), dy.toDouble())

        val L = atan(distance / 1000) * 1000

        val factor = ((L / 2.5) / distance).toFloat()

        return Pair(dx * factor, dy * factor)
    }
    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    fun startArrow(x: Float, y: Float) {
        startX = x
        startY = y
        endX = x
        endY = y
        showArrow = true
    }

    fun updateArrow(x: Float, y: Float) {
        endX = x
        endY = y
    }

    fun hideArrow() {
        showArrow = false
    }

    init {                          //Initialisation du niveau
        skycolor.color = Color.parseColor("#add8e6")
        textPaint.textSize = screenWidth / 10
        textPaint.color = Color.BLACK
        birdavailable = birds.size
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

    fun pause() {               //Entrées:None, Sorties:None
        drawing = false         //Met le niveau en pause

        if(secretachieved)
        {mediaSecret.pause()}
        else{
            mediaPlayer.pause()
        }
        thread.join()
    }

    fun resume() {              //Entrées:None, Sorties:None
        drawing = true          //Reprend le niveau
        thread = Thread(this)
        thread.start()
        if(secretachieved)
        {mediaSecret.start()}
        else{
            mediaPlayer.start()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {      //Entrées:width, height, old width , old width, Sorties:None
        super.onSizeChanged(w, h, oldw, oldh)                               //Adapte le niveau à la taille de l'écran
        val screenHeight1 = screenHeight
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()
        ground.screenHeight = screenHeight
        ground.screenWidth = screenWidth
        ground.setRect()
        textPaint.textSize = w / 25f
        textPaint.isAntiAlias = true
        for (pig in pigs){
            pig.set_posy(screenHeight-screenHeight1)
            //pig.setxp(screenWidth-screenWidth1)
        }
        for (bloc in blocs){
            bloc.set_posy(screenHeight-screenHeight1)
            //bloc.setxp(screenWidth-screenWidth1)
        }
    }

    private fun draw() {                        //Entrées:None, Sorties:None
        if (holder.surface.isValid) {           //Appelle tous les objets pour dessinner leur canevas et l'afficher
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
            if (showArrow) {
                val paint = Paint().apply {
                    color = Color.BLACK
                    strokeWidth = 8f
                    style = Paint.Style.STROKE
                    isAntiAlias = true
                }
                val (vx, vy) = computeLaunchVector(startX, startY, endX, endY)

                canvas.drawLine(
                    startpositionx,
                    screenHeight - groundheight - startpositiony,
                    startpositionx + vx,
                    screenHeight - groundheight - startpositiony + vy,
                    paint
                )
            }
            holder.unlockCanvasAndPost(canvas)
        }
        // Dessiner la flèche si nécessaire

    }

    override fun run() {                                        //Entrées:None, Sorties:None
        var previousFrameTime = System.currentTimeMillis()      //Fait tourner le niveau en appelant les fonctions draw et updatePositions
        while (drawing) {
            val currentTime = System.currentTimeMillis()
            val elapsedTimeMS: Double = (currentTime - previousFrameTime).toDouble()
            totalElapsedTime += elapsedTimeMS / 1000.0
            updatePositions(elapsedTimeMS)
            draw()
            previousFrameTime = currentTime
        }
    }

    private fun updatePositions(elapsedTimeMS: Double) {        //Entrées:temps écoulé, Sorties:None
        val interval = elapsedTimeMS / 1000.0                   //Appelle la méthode statique birdcollisioner et vérifie que le jeu n'est pas fini
        birdcollisioner(birds.toTypedArray(), pigs.toTypedArray(), objets.toTypedArray(), interval, blocs.toTypedArray())
        val bloc0 = blocs[0]
        if(nbrachieved==0 && bloc0.killed  ){
            nbrachieved=1
            secretachieved = true
            mediaPlayer.pause()
            mediaSecret.setVolume(0.2f,0.2f)
            mediaSecret.seekTo(0)
            mediaSecret.start()
        }

        waittime -= interval
        if (pigleft == 0){          //Vérifie si il y a encore des cochons à tuer
            gameOver = true
            drawing = false
            mediaWin.seekTo(0)
            mediaWin.start()
            if(secretachieved)
                {mediaSecret.pause()}
            else{
                mediaPlayer.pause()
            }

            showGameOverDialog(R.string.win)

        }

        else if(birdavailable == 0 && waittime <= -maxwaittime){    //Vérifie qu'il reste des oiseaux disponibles
            gameOver = true
            drawing = false
            showGameOverDialog(R.string.lost)
            if(secretachieved)
            {mediaSecret.pause()}
            else{
                mediaPlayer.pause()
            }
            mediaLost.seekTo(0)
            mediaLost.start()
        }

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int,       //Ces trois fonctions s'occuppent de la gestion de la surface visuelle
                                width: Int, height: Int) {}

    override fun surfaceCreated(holder: SurfaceHolder) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    private fun showGameOverDialog(messageId: Int) {                    //Affichage du game over
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



    private fun newGame() { //Entrées:None, Sorties:None
        birdavailable = 10              //Réinitialisation du niveau lorsque le bouton new game est pressé
        birdsshot = 0
        totalElapsedTime = 0.0
        secretachieved = false
        nbrachieved = 0
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
        mediaSecret.seekTo(100000)
        mediaPlayer.seekTo(0)
        mediaPlayer.start()
    }

    fun shootbird(diffx: Double, diffy: Double){       //Entrées:Différences de position lors du glissement du doigt sur l'écran, Sorties:None
        if (birdavailable > 0){                        //S'occuppe de déclencher le lancer des oiseaux après avoir vérifié si celui-ci était possible
            val bird = birds[birdavailable-1]
            if (waittime <= 0.0) {
                bird.launch(diffx*1.05, diffy*1.05)
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
    fun newgamebutton(){        //Entrées:None, Sorties:None
        newGame()               //Appelle la fonction newGame
    }
}