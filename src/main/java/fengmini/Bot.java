package fengmini;

import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Bot {

    public static JDA jda;

    private Bot() throws LoginException {
        WebUtils.setUserAgent("Mozilla/5.0 FengMini#5646 / acquiescence#2256");
        jda = JDABuilder
                .createDefault(Config.get("TOKEN"))
                .addEventListeners(new Listener())
                .setActivity(Activity.listening("music!"))
                .build();
    }

    public static void main(String[] args) throws LoginException {
        new Bot();
    }
}
