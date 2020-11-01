package fengmini;

import fengmini.command.*;
import fengmini.command.commands.*;
import fengmini.command.commands.music.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {

    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new PingCommand());
        addCommand(new HelpCommand(this));

        addCommand(new JoinCommand());
        addCommand(new LeaveCommand());
        addCommand(new NowPlayingCommand());
        addCommand(new PlayCommand());
        addCommand(new QueueCommand());
        addCommand(new SkipCommand());
        addCommand(new StopCommand());
        addCommand(new ShuffleCommand());
    }

    private void addCommand(ICommand cmd) {
        boolean nameFound = this.commands.stream().anyMatch(
                (it) -> it.getName().equalsIgnoreCase(cmd.getName())
        );

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present!");
        }

        commands.add(cmd);
    }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.commands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }

        return null;
    }

    void handle(GuildMessageReceivedEvent event) {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(Config.get("prefix")), "")
                .split("\\s+");

        String invoke = split[0].toLowerCase();
        if (invoke.isEmpty())
            return;

        ICommand cmd = this.getCommand(invoke);

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);

            cmd.handle(ctx);
        }
    }

    public List<ICommand> getCommands() {
        return commands;
    }

}
