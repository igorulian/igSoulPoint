package br.com.igSoulPoint;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender s, org.bukkit.command.Command c, String arg2, String[] args) {
		Player p = (Player) s;
		if(c.getName().equalsIgnoreCase("Souls")) { 
			if(args.length == 0) {
				p.sendMessage("�7You have �c" + Main.getInstance().getSoulPoint(p) + " �7Souls");
			}else if(args.length == 1) {
			 String player2name  = args[0].toString();
			 if(Main.getInstance().db.contains("" + player2name)) {
				 Player player2 = Bukkit.getPlayer(player2name);
				 if(player2.isOnline()) {
						 p.sendMessage("�c" + player2.getName() + " �7has" + " �c" + Main.getInstance().getSoulPoint(p) + " �7Souls");
				 }else {
					 p.sendMessage("�cThis player is not online");
					 return false;
				 } 
			 }else {
				 p.sendMessage("�cThis player doesn't exists");
				 return false;
			 }
			}
		}
		return false;
	}

}
