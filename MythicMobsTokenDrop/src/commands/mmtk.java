package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import color.color;
import me.sv.Main;

public class mmtk implements CommandExecutor{
	private Main plugin;
	
	public mmtk(Main plugin) {
		this.plugin = plugin;
        plugin.getCommand("mmtk").setExecutor(this);		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(sender instanceof Player) {
			if(!((Player)sender).hasPermission("mmtk.use")) {
				sender.sendMessage("Bạn không có quyền!");
				return false;
			}
		}
		if(args.length==1) {
			if(args[0].equalsIgnoreCase("reload")) {
				plugin.reloadConfig();
				sender.sendMessage("Reload thành công!");
			}
			return false;			
		}
		if(args.length<3) {
			sender.sendMessage("========================================================");
			sender.sendMessage("/mmtk booster <player> <booster> <time>");
			sender.sendMessage("/mmtk reload");
			sender.sendMessage("Lưu ý:");
			sender.sendMessage("<booster> có thể là 2 hoặc 3 hoặc 4 (x2 ; x3 ; x4) token");
			sender.sendMessage("<time> là thời gian, tính bằng giây (1 tiếng = 3600 giây)");
			sender.sendMessage("========================================================");
			return false;
		}
		if(args.length<=4) {
			if(args[0].equalsIgnoreCase("booster")) {
				if(Bukkit.getPlayer(args[1])!=null) {
					
					Player target = Bukkit.getPlayer(args[1]);
					
					if(Main.booster.containsKey(target.getName())) {
						sender.sendMessage(target.getName()+" đang có booster x"+Main.booster.get(target.getName())+" token nên không thể"
								+ " set thêm booster nữa");						
						return false;
					}
					
					try {						
						int booster = Integer.parseInt(args[2]);
						int time = Integer.parseInt(args[3]);
										
						Main.booster.put(target.getName(), booster);
						
						String messageActive = plugin.getConfig().getString("booster.activeBoosterMessage");
						messageActive=messageActive.replace("%booster%", String.valueOf(booster));
						messageActive=messageActive.replace("%time%", String.valueOf(time));
						
						target.sendMessage(
						color.add(messageActive)
						);						
						sender.sendMessage(color.add("Đã kích hoạt x"+booster+" token trong vòng "+time+"giây cho "
						+target.getName()+" thành công!"));
						
						new BukkitRunnable() {
							
							@Override
							public void run() {
								
								if(target.isOnline()==true) {
									target.sendMessage(
											color.add(plugin.getConfig().getString("booster.expiredBoosterMessage").replace("%booster%",
													String.valueOf(Main.booster.get(target.getName())))));
								}
								
								Main.booster.remove(target.getName());
								
								
							}
						}.runTaskLaterAsynchronously(plugin, 20*time);
						
					} catch (Exception e) {						
						sender.sendMessage("<booster> hoặc <time> phải là con số!");						
					}
				} else
				sender.sendMessage("Người chơi không tồn tại!");
								
			}
		}
		
		return true;
	}
	
}
