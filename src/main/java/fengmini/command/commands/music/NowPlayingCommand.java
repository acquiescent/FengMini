package fengmini.command.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import fengmini.command.CommandContext;
import fengmini.command.ICommand;
import fengmini.music.GuildMusicManager;
import fengmini.music.PlayerManager;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.TimeUnit;

public class NowPlayingCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getEvent().getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager guildMusicManager = playerManager.getGuildMusicManager(ctx.getEvent().getGuild());
        AudioPlayer player = guildMusicManager.player;

        if (player.getPlayingTrack() == null) {
            channel.sendMessage("I'm not playing any tracks!").queue();
            return;
        }

        AudioTrackInfo info = player.getPlayingTrack().getInfo();

        channel.sendMessage(
                EmbedUtils.embedMessage(
                        String.format(
                                "**Playing:**\t\t[%s](%s)\n\n%s %s - %s\n",
                                info.title,
                                info.uri,
                                player.isPaused() ? "\u23F8" : "▶",
                                formatTime(player.getPlayingTrack().getPosition()),
                                formatTime(player.getPlayingTrack().getDuration())
                        )
                ).build()
        ).queue();
    }

    private String formatTime(long ms) {
        final long hours = ms / TimeUnit.HOURS.toMillis(1);
        final long minutes = ms / TimeUnit.MINUTES.toMillis(1);
        final long seconds = ms % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public String getName() {
        return "nowplaying";
    }

    @Override
    public String getHelp() {
        return "Lists the details of currently playing track.";
    }
}
