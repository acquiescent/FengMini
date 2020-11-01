package fengmini.command.commands.music;

import fengmini.command.CommandContext;
import fengmini.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getEvent().getChannel();
        AudioManager audioManager = ctx.getEvent().getGuild().getAudioManager();

        if (!audioManager.isConnected()) {
            channel.sendMessage("I'm not connected to a voice channel!").queue();
            return;
        }

        VoiceChannel voiceChannel = audioManager.getConnectedChannel();

        if (!voiceChannel.getMembers().contains(ctx.getEvent().getMember())) {
            channel.sendMessage("You have to be in the same voice channel as me to use this command!").queue();
            return;
        }

        audioManager.closeAudioConnection();
        channel.sendMessage("Disconnected from your voice channel!").queue();
    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getHelp() {
        return "Makes the bot leave your channel.";
    }
}
