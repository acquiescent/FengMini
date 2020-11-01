package fengmini.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import fengmini.Bot;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason.FINISHED;
import static com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason.STOPPED;

public class TrackScheduler extends AudioEventAdapter {

    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingDeque<>();
    }

    public void queue(AudioTrack track) {
        if (!player.startTrack(track, true))
            queue.offer(track);
    }

    public BlockingQueue<AudioTrack> getQueue() {
        return queue;
    }

    public void nextTrack() {
        AudioTrack nextTrack = queue.poll();
        player.startTrack(nextTrack, false);
        Bot.jda.getPresence().setPresence(Activity.playing(nextTrack.getInfo().title), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason == FINISHED)
            if (queue.isEmpty())
                Bot.jda.getPresence().setActivity(Activity.listening("music!"));
            else
                nextTrack();
        else if (endReason == STOPPED)
            Bot.jda.getPresence().setActivity(Activity.listening("music!"));

    }

}
