package gg.clouke.alpha.command.impl;

import gg.clouke.alpha.provider.log.Log;
import gg.clouke.alpha.util.atomic.FinalNumber;
import gg.clouke.alpha.util.atomic.Reference;
import gg.clouke.alpha.util.chat.CC;
import gg.clouke.alpha.util.command.BaseCommand;
import gg.clouke.alpha.util.command.Command;
import gg.clouke.alpha.util.command.CommandArgs;
import gg.clouke.alpha.util.command.Completer;
import gg.clouke.alpha.util.task.Asynchronous;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Lucanius
 * @since May 31, 2022.
 * Alpha - All Rights Reserved.
 */
public class LogsCommand extends BaseCommand {

    @Command(name = "alpha.logs", permission = "alpha.command.logs")
    public void onDispatch(CommandArgs cmd) {
        Asynchronous.run(() -> {
            Player player = cmd.getPlayer();
            String[] args = cmd.getArgs();

            if (args.length != 1) {
                player.sendMessage(CC.translate("&cUsage: /alpha logs <user>"));
                return;
            }

            Player target = plugin.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(CC.translate("&cPlayer not found!"));
                return;
            }

            Set<Log> logs = plugin.getLogProvider().fetch(target.getUniqueId());
            if (logs.isEmpty()) {
                player.sendMessage(CC.translate("&cNo logs found!"));
                return;
            }

            Reference<StringBuilder> builder = new Reference<>();
            if (logs.size() >= 15) {
                builder.set(new StringBuilder());
                logs.forEach(log -> builder.get().append(log.append())); // for paste
            }

            player.sendMessage(CC.translate(CC.LINE));
            player.sendMessage(CC.translate("&e" + target.getName() + "'s Logs:"));

            FinalNumber number = new FinalNumber();
            for (Log log : logs) {
                if (number.getAndIncrease() >= 15) {
                    player.sendMessage(CC.translate("&eUploaded logs to: &7https://pastie.io/" + plugin.getLogProvider().toPaste(builder.toString())));
                    break;
                }

                player.sendMessage(CC.translate(log.string()));
            }

            player.sendMessage(CC.translate(CC.LINE));
        });
    }

    @Completer(name = "alpha.logs")
    public List<String> onCompleter(CommandArgs cmd) {
        return plugin.getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }
}
