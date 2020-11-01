package fengmini.command.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import fengmini.command.CommandContext;
import fengmini.command.ICommand;
import fengmini.music.GuildMusicManager;
import fengmini.music.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;

public class ShuffleCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getEvent().getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager guildMusicManager = playerManager.getGuildMusicManager(ctx.getEvent().getGuild());
        BlockingQueue<AudioTrack> queue = guildMusicManager.scheduler.getQueue();

        if (queue.isEmpty()) {
            channel.sendMessage("The queue is empty!").queue();
            return;
        }

        ArrayList<AudioTrack> shuffled = new ArrayList<>();
        for (AudioTrack track : queue)
            shuffled.add(track);
        Collections.shuffle(shuffled);
        queue.clear();
        for (AudioTrack track : shuffled)
            guildMusicManager.scheduler.queue(track);

        channel.sendMessage("Queue has been shuffled!").queue();
    }

    @Override
    public String getName() {
        return "shuffle";
    }

    @Override
    public String getHelp() {
        return "Shuffles tracks currently in queue.";
    }
}
