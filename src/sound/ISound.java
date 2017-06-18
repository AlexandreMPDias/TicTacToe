package sound;

/**
 * ISound Interface.
 * Interface used for managing all sound files and usage.
 * This interface should contain all the methods used for correctly playing or stop
 * playing any sound.
 */
public interface ISound {

  /**
   * Play a Sound for its full duration.
   *
   * @param sound is the SoundLabels enum that correspond to a static loaded file.
   */
  void play(SoundLabels sound);

  /**
   * Play a Sound for a given duration.
   *
   * @param sound is the SoundLabels enum that correspond to a static loaded file.
   * @param timeMillis is the duration.
   */
  void play(SoundLabels sound, long timeMillis);

  /**
   * Play a Sound in Loop forever.
   *
   * @param sound is the SoundLabels enum that correspond to a static loaded file.
   */
  void playLoop(SoundLabels sound);

  /**
   * Play a Sound in Loop forever. But adjusting the Volume.
   *
   * @param sound is the SoundLabels enum that correspond to a static loaded file.
   */
  void playLoop(SoundLabels sound, double volume);

  /**
   * Stop a Sound in Loop.
   *
   * @param sound is the SoundLabels enum that correspond to a static loaded file.
   */
  void stopLoop(SoundLabels sound);

  /**
   * Get method for MuteProperty.
   *
   * @return the boolean for the MuteProperty.
   */
  boolean getMuteProperty();

  /**
   * Mute/Unmute all Sounds
   *
   * @param muteProperty if property is true, mute all sounds, if not, unmute.
   */
  void setMuteProperty(boolean muteProperty);
}