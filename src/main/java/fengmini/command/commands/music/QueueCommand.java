package fengmini.command.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import fengmini.command.CommandContext;
import fengmini.command.ICommand;
import fengmini.music.GuildMusicManager;
import fengmini.music.PlayerManager;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.*;
import java.util.concurrent.BlockingQueue;

public class QueueCommand implements ICommand {
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

        int trackCount = Math.min(queue.size(), 10);
        List<AudioTrack> tracks = new ArrayList<>(queue);
        EmbedBuilder builder = EmbedUtils.defaultEmbed()
                .setTitle("Current Queue (Total: " + queue.size() + ")");

        for (int loop = 0; loop < trackCount; loop++) {
            AudioTrack track = tracks.get(loop);
            AudioTrackInfo info = track.getInfo();
            builder.appendDescription(String.format("%s - %s\n", info.title, info.author));
        }

        channel.sendMessage(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "queue";
    }

    @Override
    public String getHelp() {
        return "Shows the current queue for the music player.";
    }
}
