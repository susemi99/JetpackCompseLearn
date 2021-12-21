package kr.susemi99.xylophone

import android.app.Application
import android.content.pm.ActivityInfo
import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
  private val vm by viewModels<MainViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE // 화면 가로 고정
    super.onCreate(savedInstanceState)

    setContent {
      XylophoneScreen(v)
    }
  }
}

@Composable
fun XylophoneScreen(vm: MainViewModel) {
  val keys = listOf(
    Pair("도", Color.Red),
    Pair("레", Color(0xffff9800)),
    Pair("미", Color(0xffffc107)),
    Pair("파", Color(0xff88c34a)),
    Pair("솔", Color(0xff2196f3)),
    Pair("라", Color(0xff3f51b5)),
    Pair("시", Color(0xff673ab7)),
    Pair("도", Color.Red),
  )

  Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly) {
    keys.forEachIndexed { index, key ->
      val padding = (index + 2) * 8
      Keyboard(modifier = Modifier
        .padding(top = padding.dp, bottom = padding.dp)
        .clickable { vm.playSound(index) }, text = key.first, color = key.second)
    }
  }
}

@Composable
fun Keyboard(modifier: Modifier, text: String, color: Color) {
  Box(
    modifier = modifier
      .width(50.dp)
      .fillMaxHeight()
      .background(color)
  ) {
    Text(text, style = TextStyle(color = Color.White, fontSize = 20.sp), modifier = Modifier.align(alignment = Alignment.Center))
  }
}


/**************************************************************************
 * ViewModel
 **************************************************************************/
class MainViewModel(application: Application) : AndroidViewModel(application) {
  private val soundPool = SoundPool.Builder().setMaxStreams(8).build()

  private val sounds = listOf(
    soundPool.load(application.applicationContext, R.raw.do1, 1),
    soundPool.load(application.applicationContext, R.raw.re, 1),
    soundPool.load(application.applicationContext, R.raw.mi, 1),
    soundPool.load(application.applicationContext, R.raw.fa, 1),
    soundPool.load(application.applicationContext, R.raw.sol, 1),
    soundPool.load(application.applicationContext, R.raw.la, 1),
    soundPool.load(application.applicationContext, R.raw.si, 1),
    soundPool.load(application.applicationContext, R.raw.do2, 1),
  )

  fun playSound(index: Int) {
    soundPool.play(sounds[index], 1f, 1f, 0, 0, 1f)
  }

  override fun onCleared() {
    super.onCleared()
    soundPool.release()
  }
}