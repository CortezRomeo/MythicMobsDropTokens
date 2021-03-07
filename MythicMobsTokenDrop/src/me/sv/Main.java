package me.sv;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import commands.mmtk;
import listener.mmdroptoken;


public class Main extends JavaPlugin implements Listener{
	
	public static HashMap<String, Integer> booster = new HashMap<String, Integer>();
	
	@Override
	public void onEnable() {
		
        if (Bukkit.getPluginManager().getPlugin("TokenManager") == null) {         

        	Bukkit.getServer().getConsoleSender().sendMessage("§c§lKHÔNG THỂ BẬT PLUGIN §3§lMythicMobsDropTokens §c§lVÌ THIẾU PLUGIN §e§lTokenManager");
        	Bukkit.getPluginManager().disablePlugin(this);        	
        	
    		
        } else if (Bukkit.getPluginManager().getPlugin("MythicMobs") == null) {         

        	Bukkit.getServer().getConsoleSender().sendMessage("§c§lKHÔNG THỂ BẬT PLUGIN §3§lMythicMobsDropTokens §c§lVÌ THIẾU PLUGIN §e§lMythicMobs");
        	Bukkit.getPluginManager().disablePlugin(this);        	
                     
    		
        } else {   
     
        Bukkit.getServer().getConsoleSender().sendMessage("§a§lĐÃ BẬT THÀNH CÔNG PLUGIN §3§lMythicMobsDropTokens");
        
        loadConfig();
        loadEvents();
        loadCommands();
        }
    } 		
	
	public void loadConfig() {	
        
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	
	}	
	
	public void loadEvents() {
		
        new mmdroptoken(this);  
        
	}	
	
	public void loadCommands() {
		
        new mmtk(this);  
        
	}		

}
