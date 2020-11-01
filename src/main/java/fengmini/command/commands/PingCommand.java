package fengmini.command.commands;

import fengmini.command.CommandContext;
import fengmini.command.ICommand;
import net.dv8tion.jda.api.JDA;

public class PingCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();
        jda.getRestPing().queue(
                (ping) -> ctx.getChannel()
                .sendMessageFormat("Channel ping: %sms\nGateway ping: %sms", ping, jda.getGatewayPing()).queue()
        );
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getHelp() {
        return "Shows current ping from bot to Discord servers.";
    }
}
