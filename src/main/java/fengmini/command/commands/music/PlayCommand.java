package fengmini.command.commands.music;

import com.sedmelluq.discord.lavaplayer.player.*;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import fengmini.Config;
import fengmini.command.*;
import fengmini.music.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;

import java.net.*;


public class PlayCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();

        if (ctx.getArgs().isEmpty()) {
            channel.sendMessage("I need a youtube URL!").queue();
            return;
        }

        String input = String.join("", ctx.getArgs().get(0));
        if (!isURL(input)) {
            channel.sendMessage("Invalid URL!").queue();
            return;
        }

        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);

        PlayerManager manager = PlayerManager.getInstance();
        manager.loadAndPlay(ctx.getEvent().getChannel(), input);
        manager.getGuildMusicManager(ctx.getGuild()).player.setVolume(10);
    }

    public boolean isURL(String input) {
        try {
            new URL(input);
            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getHelp() {
        return "Plays a song\n" +
                "Usage: `" + Config.get("prefix") + getName() + " <youtube url>`";
    }
}
