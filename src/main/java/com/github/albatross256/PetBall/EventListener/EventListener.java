package com.github.albatross256.PetBall.EventListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.block.Container;
import org.bukkit.block.DaylightDetector;
import org.bukkit.block.EnchantingTable;
import org.bukkit.block.EnderChest;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Cake;
import org.bukkit.block.data.type.Comparator;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Gate;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.block.data.type.Sign;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.ChestedHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.albatross256.PetBall.BallManager;
import com.github.albatross256.PetBall.WorldManager;
import com.github.albatross256.PetBall.BallData.BallData;
import com.github.albatross256.PetBall.LoreWriter.factory.LoreWriterFactory;

import net.minecraft.nbt.NBTCompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.level.storage.WorldData;

/**
 * net.minecraft.server -> NMS
*/
public class EventListener implements Listener{

	private BallManager ballManager;
	private WorldManager worldManager;

	public EventListener(BallManager ballManager, WorldManager worldManager) {

		this.ballManager = ballManager;
		this.worldManager = worldManager;
	}

	@EventHandler
	public void onBlockDispense(BlockDispenseEvent event){
		if(event.isCancelled() || event.getBlock().getType() .equals(Material.DROPPER)) return;
		if(isEntityBall(event.getItem())){
			event.setCancelled(true);
		}
	}

	private boolean canCatch(EntityType type) {
		for(String key : this.ballManager.getAllBallDatas().keySet()) {
			BallData ballData = this.ballManager.getAllBallDatas().get(key);
			if(ballData.getEntityType().equals(type)) return true;
		}
		return false;
	}

	@EventHandler
	public void onTap(PlayerInteractEvent event) {
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			if(this.isEntityBall(event.getItem())) {
				event.setCancelled(true);
				return;
			}else{
				return;
			}
		}else if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;

		Location location = event.getClickedBlock().getLocation();
		Location newLocation = new Location(
				location.getWorld(),
				location.getX() + event.getBlockFace().getModX() + 0.5,
				location.getY() + event.getBlockFace().getModY(),
				location.getZ() + event.getBlockFace().getModZ() + 0.5
				);

		if(!event.getPlayer().isSneaking() && isTouchable(event.getClickedBlock())) return;

		ItemStack mainItem = event.getPlayer().getInventory().getItemInMainHand();
		ItemStack offItem = event.getPlayer().getInventory().getItemInOffHand();
		ItemStack entityBall = null;

		if(isEntityBall(mainItem)){
			event.setCancelled(true);
			if(isEntityEmptyBall(mainItem)) {
				return;
			}else{
				entityBall = mainItem;
			}
		}else if(isEntityBall(offItem)) {
			event.setCancelled(true);
			if(isEntityEmptyBall(offItem)) {
				return;
			}else if(mainItem.getType().equals(Material.AIR)){
				entityBall = offItem;
			}
		}

		if(entityBall == null) return;

		if(!this.worldManager.isUsableWorld(event.getPlayer().getWorld().getName())) return;

		// CraftItemStack.asNMSCopy(entityBall).getTag() -> CraftItemStack.asNMSCopy(entityBall).t()
		NBTTagCompound nbttag =  CraftItemStack.asNMSCopy(entityBall).t();


		Entity entity = null;

		BallData ballData = null;
		for(String key : this.ballManager.getAllBallDatas().keySet()) {
			BallData eachBallData = this.ballManager.getAllBallDatas().get(key);

			// getString -> l
			if(eachBallData.getEntityType().toString().equals(nbttag.l(BallData.ENTITYBALL_CONTENT_KEY))) {
				event.setCancelled(true);

				//同時出し入れ対策2
				// worldData -> E -> M
				// net.minecraft.world.level.storage.DerivedLevelData.getTime() -> e()
				Long currentTime = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().M.e();
				// getLong -> i
				if(Math.abs(nbttag.i(BallData.ENTITYBALL_TIMESTAMP_KEY) - currentTime) < 1) return;

				entity = location.getWorld().spawnEntity(newLocation, eachBallData.getEntityType());
				ballData = eachBallData;
				break;
			}
		}

		if(entity == null) return;


		/* 以下 NBTの解析及び埋め込み*/

		// net.minecraft.nbt.CompoundTag#getByteArray(String) -> getByteArray
		byte[] byteNbt = nbttag.m(BallData.ENTITYBALL_NBT_KEY);

		ByteArrayInputStream bais = new ByteArrayInputStream(byteNbt);
		try {
			NBTTagCompound nbt = NBTCompressedStreamTools.a(bais);
			// ((CraftEntity) entity).getHandle().load(nbt);
			// Entity.setPositionRotation(double, double, double, float, float) -> absMoveTo(double,double,double,float,float) -> a(double,double,double,float,float) ?
			((EntityLiving)((CraftEntity) entity).getHandle()).a(newLocation.getX(), newLocation.getY(), newLocation.getZ(), 0, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}


		/* 以下ボールの生成及びインベントリ転送*/

		ItemStack dItemStack = new ItemStack(entityBall);
		dItemStack.setAmount(1);
		HashMap<Integer, ItemStack> leftItems = event.getPlayer().getInventory().removeItem(dItemStack);
		for(Integer key : leftItems.keySet()) {
			offItem.setAmount(offItem.getAmount() - 1);
		}


		ItemStack addItem = new ItemStack(this.ballManager.getBallData(entity.getType()).getEmptyBallMaterial(), 1);
		addItem = this.getMetaItem(addItem, BallData.ENTITYBALL_CONTENT_KEY, BallData.ENTITYBALL_CONTENT_EMPTY);
		ItemMeta meta = addItem.getItemMeta();
		meta.setDisplayName(this.ballManager.getBallData(entity.getType()).getDisplayName());

		List<String> lore = new ArrayList<String>();
		lore.add("Empty");
		meta.setLore(lore);
		addItem.setItemMeta(meta);

		Player player = event.getPlayer();
		PlayerInventory inventory = player.getInventory();

		if(inventory.getItem(inventory.getHeldItemSlot()) == null ) {
			inventory.setItem(inventory.getHeldItemSlot(), addItem);
		}else {
			HashMap<Integer, ItemStack> notAddedItems = inventory.addItem(addItem);
			if(!notAddedItems.isEmpty()) {
				player.getWorld().dropItem(player.getLocation(), notAddedItems.get(0));
				player.sendMessage(ChatColor.GREEN + "[PetBall] " + ChatColor.RED +":: 空きスロット不足 :: 空のPetBallを地面に捨てました" );
			}
		}
	}

	private ItemStack getMetaItem(ItemStack item, String key, String value) {
		// net.minecraft.nbt.NBTBase
		// net.minecraft.nbt.NBTTagCompound -> net.minecraft.nbt.CompoundTag
		NBTTagCompound nbttag = new NBTTagCompound();
		// setString -> putString -> a
		nbttag.a(key, value);
		net.minecraft.world.item.ItemStack itemCopy = CraftItemStack.asNMSCopy(item);
		// itemCopy.setTag(nbttag) -> c
		itemCopy.c(nbttag);
		ItemStack entityBall = CraftItemStack.asBukkitCopy(itemCopy);
		return entityBall;
	}

	private boolean isEntityBall(ItemStack item) {
		if(item == null) return false;
		ItemMeta itemMeta = item.getItemMeta();
		if(itemMeta == null) return false;

		net.minecraft.world.item.ItemStack handItemCopy = CraftItemStack.asNMSCopy(item);
		// handItemCopy.getTag(); -> t
		NBTTagCompound handItemNbttag = handItemCopy.t();
		if(handItemNbttag != null && handItemNbttag.e(BallData.ENTITYBALL_CONTENT_KEY)) return true;

		return false;
	}

	private boolean isEntityEmptyBall(ItemStack item) {
		ItemMeta itemMeta = item.getItemMeta();
		if(itemMeta == null) return false;

		net.minecraft.world.item.ItemStack handItemCopy = CraftItemStack.asNMSCopy(item);
		// handItemCopy.getTag(); -> t
		NBTTagCompound handItemNbttag = handItemCopy.t();
		// hasKey -> contains(e) か getBoolean(q) のどちらか -> 十中八九 contains(e), getString -> l
		if(handItemNbttag != null && handItemNbttag.e(BallData.ENTITYBALL_CONTENT_KEY) && handItemNbttag.l(BallData.ENTITYBALL_CONTENT_KEY).equals(BallData.ENTITYBALL_CONTENT_EMPTY)) return true;

		return false;
	}

	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
		ItemStack mainItem = event.getPlayer().getInventory().getItemInMainHand();
		ItemStack offItem = event.getPlayer().getInventory().getItemInOffHand();

		//子供生成対策
		for(String key : this.ballManager.getAllBallDatas().keySet()) {
			BallData ballData = this.ballManager.getAllBallDatas().get(key);

			if(event.getRightClicked().getType().equals(ballData.getFilledBallEntityType())) {
				if(isEntityBall(mainItem) && !isEntityEmptyBall(mainItem) && mainItem.getType().equals(ballData.getFilledBallMaterial())) {
					event.setCancelled(true);
					return;
				}else if(isEntityBall(offItem) && !isEntityEmptyBall(offItem) && offItem.getType().equals(ballData.getFilledBallMaterial())){
					event.setCancelled(true);
					return;
				}
			}else if(event.getRightClicked().getType().equals(ballData.getEmptyBallEntityType())) {
				if(isEntityEmptyBall(mainItem) && mainItem.getType().equals(ballData.getEmptyBallMaterial())) {
					event.setCancelled(true);
					return;
				}else if(isEntityEmptyBall(offItem) && offItem.getType().equals(ballData.getEmptyBallMaterial())){
					event.setCancelled(true);
				}
			}
		}

		ItemStack entityEmptyBall = null;
		if(!isEntityBall(mainItem) && !isEntityBall(offItem)) return;
		if(!this.canCatch(event.getRightClicked().getType())) return;

		boolean isMainHand = true;
		if(this.isEntityEmptyBall(mainItem)) {
			entityEmptyBall = mainItem;
			isMainHand = true;
		}else if(this.isEntityEmptyBall(offItem) && mainItem.getType().equals(Material.AIR)) {
			entityEmptyBall = offItem;
			isMainHand = false;
		}

		if(!this.worldManager.isUsableWorld(event.getPlayer().getWorld().getName())) return;

		// getTag -> t
		NBTTagCompound handItemNbttag = CraftItemStack.asNMSCopy(mainItem).t();
		// hasKey -> contains(e), getString -> l
		if(handItemNbttag == null || !handItemNbttag.e(BallData.ENTITYBALL_CONTENT_KEY) || !handItemNbttag.l(BallData.ENTITYBALL_CONTENT_KEY).equals(BallData.ENTITYBALL_CONTENT_EMPTY)) return;

		Entity entity = event.getRightClicked();

		if(!this.canCatch(entity.getType())) {
			return;
		}


		/*
		 * 馬 チェストアイテム ばらまき
		 */

		if(entity instanceof ChestedHorse) {
			Location entityLocation  = entity.getLocation();
			Location dropItemLocation = new Location(entity.getWorld(), entityLocation.getX()+0.5, entityLocation.getY()+0.5, entityLocation.getZ()+0.5);
			for(ItemStack strageItem : ((ChestedHorse)entity).getInventory().getStorageContents()) {
				if(strageItem != null) {
					entity.getWorld().dropItem(dropItemLocation, strageItem);
				}
			}
			((AbstractHorse)entity).getInventory().clear();
		}

		net.minecraft.world.entity.Entity nmsEntity = ((CraftEntity) entity).getHandle();
		NBTTagCompound tag = new NBTTagCompound();
		// NBTタグを追加しているのか削除しているのかメソッド名からわからない nmsEntity.saveAsPassenger(tag) -> nmsEntity.d(tag)-> nmsEntity.d(tag)
		nmsEntity.d(tag);
		event.setCancelled(true);

		ItemStack item = new ItemStack(this.ballManager.getBallData(entity.getType()).getFilledBallMaterial(), 1);
		net.minecraft.world.item.ItemStack itemCopy = CraftItemStack.asNMSCopy(item);
		NBTTagCompound nbttag = new NBTTagCompound();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			NBTCompressedStreamTools.a(tag, baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] byteNbt = baos.toByteArray();

		//空気対策
		// getString -> l
		if(tag.l("id") == null || tag.l("id").equals("")) return;

		// setByteArray -> putByteArray -> a, setString -> putString -> a
		nbttag.a(BallData.ENTITYBALL_NBT_KEY, byteNbt);
		nbttag.a(BallData.ENTITYBALL_CONTENT_KEY, entity.getType().toString());

		/*
		 * 出し入れ同時対策1
		 */
		Long time = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().M.e();
		// setLong -> putLong -> a
		nbttag.a(BallData.ENTITYBALL_TIMESTAMP_KEY, time);
		//entity.getPersistentDataContainer().set(new NamespacedKey(this.plugin, BallData.ENTITYBALL_TIMESTAMP_KEY), PersistentDataType.LONG, entity.getWorld().getFullTime());

		// setTag -> c
		itemCopy.c(nbttag);
		ItemStack entityBall = CraftItemStack.asBukkitCopy(itemCopy);

		ItemMeta itemMeta2 = entityBall.getItemMeta();
		itemMeta2.setDisplayName(entity.getCustomName() == null ? this.ballManager.getBallData(entity.getType()).getDisplayName() : entity.getCustomName());

		List<String> lore = new LoreWriterFactory().newLoreWriter(entity.getType()).generateLore(entity);

		itemMeta2.setLore(lore);
		entityBall.setItemMeta(itemMeta2);

		Player player = event.getPlayer();
		if(entityEmptyBall.getAmount() == 1) {
			if(isMainHand) {
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), entityBall);
			}else {
				player.getInventory().remove(entityEmptyBall);
				player.getInventory().addItem(entityBall);
			}
		}else {
			entityEmptyBall.setAmount(entityEmptyBall.getAmount() - 1);
			 Map<Integer, ItemStack> left = player.getInventory().addItem(entityBall);
			if(!left.isEmpty()) {
				player.getWorld().dropItem(player.getLocation(), left.get(0));
				player.sendMessage(ChatColor.GREEN + "[PetBall] " + ChatColor.RED +":: 空きスロット不足:: 捕獲済みPetBallを地面に捨てました" );
			}
		}

		entity.remove();
	}

	private boolean isTouchable(Block block) {
		if(	block.getState() instanceof Container ||
			block.getState() instanceof EnderChest ||
			block.getState() instanceof EnchantingTable ||
			block.getState() instanceof CommandBlock ||
			block.getState() instanceof DaylightDetector ||
			block.getBlockData() instanceof Door ||
			block.getBlockData() instanceof TrapDoor ||
			block.getBlockData() instanceof Bed ||
			block.getBlockData() instanceof Gate ||
			block.getBlockData() instanceof Cake ||
			block.getBlockData() instanceof Sign ||
			block.getBlockData() instanceof WallSign ||
			block.getBlockData() instanceof Switch ||
			block.getBlockData() instanceof Repeater ||
			block.getBlockData() instanceof Comparator ||
			block.getBlockData().getMaterial().equals(Material.CRAFTING_TABLE) ||
			block.getBlockData().getMaterial().equals(Material.ANVIL) ||
			block.getBlockData().getMaterial().equals(Material.CHIPPED_ANVIL) ||
			block.getBlockData().getMaterial().equals(Material.DAMAGED_ANVIL) ||
			block.getBlockData().getMaterial().equals(Material.ANVIL)
			) {
			return true;
		}
		return false;
	}
}
