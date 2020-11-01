package fengmini.command.commands.music;

import fengmini.Bot;
import fengmini.command.CommandContext;
import fengmini.command.ICommand;
import fengmini.music.GuildMusicManager;
import fengmini.music.PlayerManager;
import net.dv8tion.jda.api.entities.Activity;

public class StopCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager guildMusicManager = playerManager.getGuildMusicManager(ctx.getEvent().getGuild());

        guildMusicManager.scheduler.getQueue().clear();
        guildMusicManager.player.stopTrack();
        guildMusicManager.player.setPaused(false);

        ctx.getEvent().getChannel().sendMessage("Music player is stopped.").queue();
        Bot.jda.getPresence().setActivity(Activity.listening("music!"));

    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getHelp() {
        return "Stops the music player.";
    }
}
