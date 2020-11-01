package fengmini.command.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import fengmini.Bot;
import fengmini.command.CommandContext;
import fengmini.command.ICommand;
import fengmini.music.GuildMusicManager;
import fengmini.music.PlayerManager;
import fengmini.music.TrackScheduler;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;

public class SkipCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getEvent().getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager guildMusicManager = playerManager.getGuildMusicManager(ctx.getEvent().getGuild());
        TrackScheduler scheduler = guildMusicManager.scheduler;
        AudioPlayer player = guildMusicManager.player;

        if (player.getPlayingTrack() == null) {
            channel.sendMessage("The player isn't playing anything!").queue();
            return;
        }

        if (scheduler.getQueue().isEmpty()) {
            channel.sendMessage("No more tracks left in queue!").queue();
            player.stopTrack();
            Bot.jda.getPresence().setActivity(Activity.listening("music!"));
        } else {
            scheduler.nextTrack();
            channel.sendMessage("Skipped the track!").queue();
        }
    }

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getHelp() {
        return "Skips the current song.";
    }
}
