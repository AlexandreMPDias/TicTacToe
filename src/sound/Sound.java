package sound;

import static java.lang.Thread.sleep;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Sound Class. It implements {link ISound} interface. This class statically loads all music files,
 * and through the method, can play them. The class is very simple. Not being able to play the same
 * sound more than once at the same time, but being able to play multiple sounds at once.
 * All sounds can be mute and unmute, but no sound can be mute/unmute alone.
 */
public class Sound implements ISound {

  private static Map<SoundLabels, MediaPlayer> allSounds;

  static {
    JFXPanel sound = new JFXPanel();
    allSounds = new HashMap<>();
    allSounds.put(SoundLabels.BG, load("SilverArrows_2_15.mp3"));
    allSounds.put(SoundLabels.Cross, load("cross_3segs.mp3"));
    allSounds.put(SoundLabels.Circle, load("circle.wav"));
    allSounds.put(SoundLabels.Button, load("teleport.wav"));
    allSounds.put(SoundLabels.PopUp, load("boot.wav"));
    allSounds.put(SoundLabels.Front, load("payon.mp3"));
    allSounds.put(SoundLabels.Epic, load("epic.mp3"));
  }

  private boolean inLoop;

  public Sound() {
    inLoop = false;
  }

  private static MediaPlayer load(String path) {
    try {
      Media hit = new Media(new File("data\\sound\\" + path).toURI().toString());
      return new MediaPlayer(hit);
    } catch (Exception e) {
      e.printStackTrace();

    }
    throw new IllegalArgumentException("Error creating MediaPlayer. [" + path + "]");
  }

  @Override
  public void play(SoundLabels sound) {
    if (!allSounds.containsKey(sound)) {
      throw new IllegalArgumentException("SoundLabel not found in map.");
    }
    allSounds.get(sound).stop();
    allSounds.get(sound).play();
  }

  @Override
  public void play(SoundLabels sound, long durationMillis) {
    if (!allSounds.containsKey(sound)) {
      throw new IllegalArgumentException("SoundLabel not found in map.");
    }
    if (durationMillis < 0) {
      throw new IllegalArgumentException("Sound's duration can't be negative.");
    }
    if (durationMillis == 0) {
      allSounds.get(sound).stop();
      allSounds.get(sound).play();
    }
    Runnable runnable = () -> {
      allSounds.get(sound).stop();
      allSounds.get(sound).play();
      try {
        sleep(durationMillis);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      allSounds.get(sound).stop();
    };
    Thread thread = new Thread(runnable);
    thread.start();
  }

  @Override
  public void playLoop(SoundLabels sound) {
    playLoop(sound, 0.08);
  }

  @Override
  public void stopLoop(SoundLabels sound) {
    inLoop = false;
    allSounds.get(sound).stop();
  }

  @Override
  public void playLoop(SoundLabels sound, double volume) {
    if (!allSounds.containsKey(sound)) {
      throw new IllegalArgumentException("SoundLabel not found in map.");
    }
    inLoop = false;
    MediaPlayer media = allSounds.get(sound);
    Runnable runnable = () -> {
      media.stop();
      inLoop = true;
      media.setVolume(volume);
      while (inLoop) {
        media.play();
        try {
          Thread.sleep((long) media.getTotalDuration().toMillis());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        media.stop();
      }
    };
    Thread thread = new Thread(runnable);
    thread.start();
  }

  @Override
  public boolean getMuteProperty() {
    for (MediaPlayer media : allSounds.values()) {
      return media.isMute();
    }
    throw new IllegalArgumentException("Not media initialized.");
  }

  @Override
  public void setMuteProperty(boolean muteProperty) {
    try {
      for (MediaPlayer media : allSounds.values()) {
        media.setMute(muteProperty);
      }
    } catch (Exception e) {
      //Nothing
    }
  }
}
