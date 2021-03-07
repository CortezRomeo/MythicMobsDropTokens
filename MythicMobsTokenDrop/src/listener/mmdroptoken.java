package listener;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import color.color;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import me.realized.tokenmanager.api.TokenManager;
import me.sv.Main;

public class mmdroptoken implements Listener{
	private Main plugin;
	
	public mmdroptoken(Main plugin) {
		this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);			
	}
	
	@EventHandler
	public void DropTokens(MythicMobDeathEvent e) {		      
		
		if(e.getKiller() instanceof Player) {
			final TokenManager tokenManager = (TokenManager) Bukkit.getPluginManager().getPlugin("TokenManager");
			Player p = (Player) e.getKiller();
			
			int max = plugin.getConfig().getInt("drop.tokens.max");
			int min = plugin.getConfig().getInt("drop.tokens.min");				
			Random randomInt = new Random();		
			int randomTokens = min + randomInt.nextInt(max);	
			
			if(Main.booster.containsKey(e.getKiller().getName())) {
				
				randomTokens=randomTokens*Main.booster.get(e.getKiller().getName());
				tokenManager.addTokens(p, randomTokens);
				
				String messageBooster = plugin.getConfig().getString("drop.boosterMessage");
				messageBooster=messageBooster.replace("%tokens%", String.valueOf(randomTokens));
				messageBooster=messageBooster.replace("%booster%", String.valueOf(Main.booster.get(e.getKiller().getName())));
				p.sendMessage(color.add(messageBooster));
				
				return;
			}			       
			
		tokenManager.addTokens(p, randomTokens);		
		p.sendMessage(color.add(plugin.getConfig().getString("drop.message").replace("%tokens%", String.valueOf(randomTokens))));
		
		} else 
			return;
	}	

}
