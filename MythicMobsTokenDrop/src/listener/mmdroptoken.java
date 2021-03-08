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
			String mobname = e.getMobType().getInternalName();
			
			int max = plugin.getConfig().getInt("drop.tokens.default.max");
			int min = plugin.getConfig().getInt("drop.tokens.default.min");
			Random randomInt = new Random();
			
			for(String getstring : plugin.getConfig().getConfigurationSection("drop.tokens.").getKeys(false)) {				
				if(mobname.equalsIgnoreCase(getstring)) {
					max = plugin.getConfig().getInt("drop.tokens."+e.getMobType().getInternalName()+".max");
					min = plugin.getConfig().getInt("drop.tokens."+e.getMobType().getInternalName()+".min");
					break;					
				}					
			}
			max=max-min;			
			int random = min + randomInt.nextInt(max);				
			
			if(Main.booster.containsKey(e.getKiller().getName())) {
				tokenManager.addTokens(p, random*Main.booster.get(e.getKiller().getName()));
				
				String messageBooster = plugin.getConfig().getString("drop.boosterMessage");
				messageBooster=messageBooster.replace("%tokens%", String.valueOf(random*Main.booster.get(e.getKiller().getName())));
				messageBooster=messageBooster.replace("%booster%", String.valueOf(Main.booster.get(e.getKiller().getName())));
				p.sendMessage(color.add(messageBooster));
			} else {
				tokenManager.addTokens(p, random);
				p.sendMessage(color.add(plugin.getConfig().getString("drop.message").replace(
						"%tokens%", String.valueOf(random))));	
			}				
		} else 
			return;
	}	

}
