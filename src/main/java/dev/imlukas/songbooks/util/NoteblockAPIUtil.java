package dev.imlukas.songbooks.util;

import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.TimeUtils;

public final class NoteblockAPIUtil {

    private NoteblockAPIUtil() {
    }

    public String getSongTimeStamp(SongPlayer songPlayer) {
        return TimeUtils.getActualTime("hh:mm:ss", songPlayer);
    }

    public double getSongPercentage(SongPlayer songPlayer) {
        int noteLength = songPlayer.getTick();
        int songLength = songPlayer.getSong().getLength();
        return (double) noteLength / songLength;
    }

    public int getSeconds(SongPlayer player) {
        int ticks = player.getTick();
        float speed = player.getSong().getSpeed();

        return (int) (ticks / speed);
    }


}
