package id.ac.polbeng.gentaindianaluthfi.mediaplayerexample

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.ac.polbeng.gentaindianaluthfi.mediaplayerexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mediaPlayer: MediaPlayer? = null
    private var isPaused = false
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            if (isPaused) {
                // Melanjutkan pemutaran dari posisi pause
                mediaPlayer?.seekTo(position)
                mediaPlayer?.start()
                isPaused = false
            } else {
                if (mediaPlayer == null) {
                    // Inisialisasi MediaPlayer saat pertama kali diputar
                    mediaPlayer = MediaPlayer.create(applicationContext, R.raw.sound)
                    mediaPlayer?.setOnCompletionListener { stopPlayer() }
                }
                mediaPlayer?.start()
            }
        }

        binding.btnPause.setOnClickListener {
            mediaPlayer?.let { player ->
                if (player.isPlaying) {
                    position = player.currentPosition
                    player.pause()
                    isPaused = true
                }
            }
        }

        binding.btnStop.setOnClickListener {
            stopPlayer()
        }
    }

    private fun stopPlayer() {
        mediaPlayer?.let { player ->
            isPaused = false
            position = 0
            player.release()
            mediaPlayer = null
            Toast.makeText(this, "Media Player Released!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStop() {
        super.onStop()
        stopPlayer()
    }
}