package br.com.igSoulPoint;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


public class Main extends JavaPlugin implements Listener{
	
	  public static Main plugin;
	  public Config db = new Config(this, "players.yml");; 
	  public Config config = new Config(this, "config.yml");;
	  
	  public void onLoad(){
		 plugin = this;
	  }
	  
	  public static Main getInstance(){
	    return plugin;
	  }
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		config.saveDefault();
	    Bukkit.getPluginManager().registerEvents(this, this);
	    Bukkit.getPluginManager().registerEvents(new PlayerDieEvent(), this);
	    getCommand("souls").setExecutor(new Command());
	    ConsoleCommandSender c = Bukkit.getConsoleSender();
	    c.sendMessage("");
	    c.sendMessage("�6igSoulPoint �aAtivado");
	    c.sendMessage("");
		try {
			File arquivop = new File("plugins/igSoulPoint/players.yml");
			if(!arquivop.exists()) {
				arquivop.createNewFile();
			}
		} catch (IOException e1) {e1.printStackTrace();} 
	}
	
	@Override
	public void onDisable() {
	    ConsoleCommandSender c = Bukkit.getConsoleSender();
	    c.sendMessage("");
	    c.sendMessage("�6igSoulPoint �cDesativado");
	    c.sendMessage("");
	    HandlerList.unregisterAll();
	}

	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
	    if(!db.contains("" + e.getPlayer().getName())) {
	    	 db.set("" + e.getPlayer().getName(), 10);
	    	 db.saveConfig();
	    }
		startSoulPointCounter(e.getPlayer());
		String MensagemEntrou = config.getString("MensagemEntrou");
		e.getPlayer().sendMessage(MensagemEntrou);
	}
	
	  private void startSoulPointCounter(Player p) {
      new BukkitRunnable(){
        public void run(){
          if (p.hasPermission("igSoulPoint.vip")){
        	  if(getSoulPoint(p) < 15) {
        		 putSoulPoint(p,getSoulPoint(p) + 1);
	            p.sendMessage("�c�l~ �7ja se passou Meia hora que voc� est� se aventurando conosco! �7Tome, pegue este presente para voc�");
        	  }else {
        		  putSoulPoint(p, 15);
        	  }
          }else{
        	  if(getSoulPoint(p) < 10) {
        		 putSoulPoint(p, getSoulPoint(p) + 1);
	            p.sendMessage("�c�l~ �7ja se passou Meia hora que voc� es� se aventurando conosco! �7Tome, pegue este presente para voc�");
        	  }else {
        		  putSoulPoint(p, 10);
        	  }
          }
      }
    }.runTaskTimer(Main.plugin, 36000L, 36000L);
} 
	
	
	public int getSoulPoint(Player p) {
		return db.getInt("" + p.getName());
	}
	
	public void putSoulPoint(Player p, Integer value) {
		db.set("" + p.getName(), value);
		db.saveConfig();
	}
	
	
	
	

}
