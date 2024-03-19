package com.github.albatross256.PetBall.EventListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.albatross256.PetBall.BallManager;
import com.github.albatross256.PetBall.WorldManager;
import com.github.albatross256.PetBall.BallData.BallData;
import com.github.albatross256.PetBall.LoreWriter.factory.LoreWriterFactory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.block.Barrel;
import org.bukkit.block.Chest;
import org.bukkit.block.ChiseledBookshelf;
import org.bukkit.block.CommandBlock;
import org.bukkit.block.DaylightDetector;
import org.bukkit.block.Dispenser;
import org.bukkit.block.EnderChest;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.block.Jukebox;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.*;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Comparator;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NBTReadLimiter;


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
		Logger logger = Logger.getLogger("onTap");
		logger.log(Level.INFO,"onTap:start");
		logger.log(Level.INFO,"event.getAction:" + event.getAction().toString());
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			logger.log(Level.INFO,"this.isEntityBall:" + this.isEntityBall(event.getItem()));
			if (this.isEntityBall(event.getItem())) {
				event.setCancelled(true);
				return;
			} else {
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

		logger.log(Level.INFO,"event.getPlayer().isSneaking():" + event.getPlayer().isSneaking());
		logger.log(Level.INFO,"isTouchable(event.getClickedBlock()):" + isTouchable(event.getClickedBlock()));
		if(!event.getPlayer().isSneaking() && isTouchable(event.getClickedBlock())) return;

		ItemStack mainItem = event.getPlayer().getInventory().getItemInMainHand();
		ItemStack offItem = event.getPlayer().getInventory().getItemInOffHand();
		ItemStack entityBall = null;

		logger.log(Level.INFO,"isEntityBall(mainItem):" + isEntityBall(mainItem));
		logger.log(Level.INFO,"isEntityEmptyBall(mainItem):" + isEntityEmptyBall(mainItem));
		logger.log(Level.INFO,"isEntityBall(offItem):" + isEntityBall(offItem));
		logger.log(Level.INFO,"isEntityEmptyBall(offItem):" + isEntityEmptyBall(offItem));
		logger.log(Level.INFO,"mainItem.getType().equals(Material.AIR):" + mainItem.getType().equals(Material.AIR));
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

		logger.log(Level.INFO,"isUsableWorld:" + this.worldManager.isUsableWorld(event.getPlayer().getWorld().getName()));
		if(!this.worldManager.isUsableWorld(event.getPlayer().getWorld().getName())) return;

		// CraftItemStack.asNMSCopy(entityBall).getTag() -> CraftItemStack.asNMSCopy(entityBall).t()
		CompoundTag nbttag =  CraftItemStack.asNMSCopy(entityBall).getTag();


		Entity entity = null;

		BallData ballData = null;
		// このへんでとまっている
		for(String key : this.ballManager.getAllBallDatas().keySet()) {
			BallData eachBallData = this.ballManager.getAllBallDatas().get(key);

			// getString -> l
			if(eachBallData.getEntityType().toString().equals(nbttag.getString(BallData.ENTITYBALL_CONTENT_KEY))) {
				event.setCancelled(true);

				//同時出し入れ対策2
				// worldData -> E -> M
				// net.minecraft.world.level.storage.DerivedLevelData.getTime() -> e()
				Long currentTime = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getTime();
				// getLong -> i
				if(Math.abs(nbttag.getLong(BallData.ENTITYBALL_TIMESTAMP_KEY) - currentTime) < 1) return;

				entity = location.getWorld().spawnEntity(newLocation, eachBallData.getEntityType());
				ballData = eachBallData;
				break;
			}
		}

		logger.log(Level.INFO,"entity:" + entity);
		if(entity == null) return;


		/* 以下 NBTの解析及び埋め込み */
		// net.minecraft.nbt.CompoundTag#getByteArray(String) -> getByteArray
		byte[] byteNbt = nbttag.getByteArray(BallData.ENTITYBALL_NBT_KEY);

		ByteArrayInputStream bais = new ByteArrayInputStream(byteNbt);
		try {
//			CompoundTag nbt = NbtIo.readCompressed(bais);
			CompoundTag nbt = NbtIo.readCompressed(bais, NBTReadLimiter.unlimitedHeap());
			((CraftEntity) entity).getHandle().load(nbt);
			// Entity.setPositionRotation(double, double, double, float, float) -> absMoveTo(double,double,double,float,float) -> a(double,double,double,float,float) ?
			((CraftEntity) entity).getHandle().absMoveTo(newLocation.getX(), newLocation.getY(), newLocation.getZ(), 0, 0);
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

//		logger.log(Level.INFO,"inventory.getItem(inventory.getHeldItemSlot()):" + inventory.getItem(inventory.getHeldItemSlot()));
		if(inventory.getItem(inventory.getHeldItemSlot()) == null ) {
			inventory.setItem(inventory.getHeldItemSlot(), addItem);
		}else {
			HashMap<Integer, ItemStack> notAddedItems = inventory.addItem(addItem);
//			logger.log(Level.INFO,"notAddedItems.isEmpty():" + notAddedItems.isEmpty());
			if(!notAddedItems.isEmpty()) {
				player.getWorld().dropItem(player.getLocation(), notAddedItems.get(0));
				player.sendMessage(ChatColor.GREEN + "[PetBall] " + ChatColor.RED +":: 空きスロット不足 :: 空のPetBallを地面に捨てました" );
			}
		}
//		logger.log(Level.INFO,"onTap Complete");
	}

	private ItemStack getMetaItem(ItemStack item, String key, String value) {
		// net.minecraft.nbt.NBTBase
		// net.minecraft.nbt.NBTTagCompound -> net.minecraft.nbt.CompoundTag
		CompoundTag nbttag = new CompoundTag();
		// setString -> putString -> a
		nbttag.putString(key, value);
		net.minecraft.world.item.ItemStack itemCopy = CraftItemStack.asNMSCopy(item);
		// itemCopy.setTag(nbttag) -> c
		itemCopy.setTag(nbttag);
		ItemStack entityBall = CraftItemStack.asBukkitCopy(itemCopy);
		return entityBall;
	}

	private boolean isEntityBall(ItemStack item) {
		if(item == null) return false;
		ItemMeta itemMeta = item.getItemMeta();
		if(itemMeta == null) return false;

		net.minecraft.world.item.ItemStack handItemCopy = CraftItemStack.asNMSCopy(item);
		// handItemCopy.getTag(); -> t
		CompoundTag handItemNbttag = handItemCopy.getTag();
		if(handItemNbttag != null && handItemNbttag.contains(BallData.ENTITYBALL_CONTENT_KEY)) return true;

		return false;
	}

	private boolean isEntityEmptyBall(ItemStack item) {
		ItemMeta itemMeta = item.getItemMeta();
		if(itemMeta == null) return false;

		net.minecraft.world.item.ItemStack handItemCopy = CraftItemStack.asNMSCopy(item);
		// handItemCopy.getTag(); -> t
		CompoundTag handItemNbttag = handItemCopy.getTag();
		// hasKey -> contains(e) か getBoolean(q) のどちらか -> 十中八九 contains(e), getString -> l
		if(handItemNbttag != null && handItemNbttag.contains(BallData.ENTITYBALL_CONTENT_KEY) && handItemNbttag.getString(BallData.ENTITYBALL_CONTENT_KEY).equals(BallData.ENTITYBALL_CONTENT_EMPTY)) return true;

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
		CompoundTag handItemNbttag = CraftItemStack.asNMSCopy(mainItem).getTag();
		// hasKey -> contains(e), getString -> l
		if(handItemNbttag == null || !handItemNbttag.contains(BallData.ENTITYBALL_CONTENT_KEY) || !handItemNbttag.getString(BallData.ENTITYBALL_CONTENT_KEY).equals(BallData.ENTITYBALL_CONTENT_EMPTY)) return;

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
		CompoundTag tag = new CompoundTag();
		// NBTタグを追加しているのか削除しているのかメソッド名からわからない nmsEntity.saveAsPassenger(tag) -> nmsEntity.d(tag)-> nmsEntity.d(tag)
		nmsEntity.saveAsPassenger(tag);
		event.setCancelled(true);

		ItemStack item = new ItemStack(this.ballManager.getBallData(entity.getType()).getFilledBallMaterial(), 1);
		net.minecraft.world.item.ItemStack itemCopy = CraftItemStack.asNMSCopy(item);
		CompoundTag nbttag = new CompoundTag();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			NbtIo.writeCompressed(tag, baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] byteNbt = baos.toByteArray();

		//空気対策
		// getString -> l
		if(tag.getString("id") == null || tag.getString("id").equals("")) return;

		// setByteArray -> putByteArray -> a, setString -> putString -> a
		nbttag.putByteArray(BallData.ENTITYBALL_NBT_KEY, byteNbt);
		nbttag.putString(BallData.ENTITYBALL_CONTENT_KEY, entity.getType().toString());

		/*
		 * 出し入れ同時対策1
		 */
		Long time = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getTime();
		// setLong -> putLong -> a
		nbttag.putLong(BallData.ENTITYBALL_TIMESTAMP_KEY, time);
		//entity.getPersistentDataContainer().set(new NamespacedKey(this.plugin, BallData.ENTITYBALL_TIMESTAMP_KEY), PersistentDataType.LONG, entity.getWorld().getFullTime());

		// setTag -> c
		itemCopy.setTag(nbttag);
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

		if( block.getType().equals(Material.IRON_DOOR) ||
			block.getType().equals(Material.IRON_TRAPDOOR)){
			// 鉄シリーズは問答無用でfalse
			return false;
		} else if(block.getState() instanceof Sign){
			// 看板は編集可・不可の状態が変化するので、動的に取得する
			return !((Sign)block.getState()).isWaxed();
		} else if(
			// それ以外
			// ベルは触った場所が本体以外だとうまく動作しないが、場所を知る手立てがないため入れてない
			// コンポスターは最大状態以外だとうまく動作しないが、知る手立てがないため入れていない
			block.getState() instanceof Container ||
			block.getState() instanceof EnderChest ||
			block.getState() instanceof EnchantingTable ||
			block.getState() instanceof CommandBlock ||
			block.getState() instanceof DaylightDetector ||
			block.getState() instanceof Jukebox ||
			block.getState() instanceof ChiseledBookshelf ||
			block.getBlockData() instanceof Door ||
			block.getBlockData() instanceof TrapDoor ||
			block.getBlockData() instanceof Bed ||
			block.getBlockData() instanceof Gate ||
			block.getBlockData() instanceof Cake ||
			block.getBlockData() instanceof Switch ||
			block.getBlockData() instanceof Repeater ||
			block.getBlockData() instanceof Dispenser ||
			block.getBlockData() instanceof Comparator ||
			block.getBlockData() instanceof Hopper ||
			block.getBlockData() instanceof NoteBlock ||
			block.getBlockData() instanceof Chest ||
			block.getBlockData() instanceof Grindstone ||
			block.getBlockData() instanceof Furnace ||
			block.getBlockData() instanceof Barrel ||
			block.getBlockData().getMaterial().equals(Material.CRAFTING_TABLE) ||
			block.getBlockData().getMaterial().equals(Material.ANVIL) ||
			block.getBlockData().getMaterial().equals(Material.CHIPPED_ANVIL) ||
			block.getBlockData().getMaterial().equals(Material.DAMAGED_ANVIL) ||
			block.getBlockData().getMaterial().equals(Material.BEACON) ||
			block.getBlockData().getMaterial().equals(Material.BREWING_STAND) ||
			block.getBlockData().getMaterial().equals(Material.FURNACE_MINECART) ||
			block.getBlockData().getMaterial().equals(Material.HOPPER_MINECART) ||
			block.getBlockData().getMaterial().equals(Material.CAKE) ||
			block.getBlockData().getMaterial().equals(Material.CANDLE_CAKE) ||
			block.getBlockData().getMaterial().equals(Material.CHEST_MINECART) ||
			block.getBlockData().getMaterial().equals(Material.COMMAND_BLOCK) ||
			block.getBlockData().getMaterial().equals(Material.DAYLIGHT_DETECTOR) ||
			block.getBlockData().getMaterial().equals(Material.RESPAWN_ANCHOR) ||
			block.getBlockData().getMaterial().equals(Material.STONECUTTER) ||
			block.getBlockData().getMaterial().equals(Material.CARTOGRAPHY_TABLE) ||
			block.getBlockData().getMaterial().equals(Material.SMITHING_TABLE) ||
			block.getBlockData().getMaterial().equals(Material.LOOM) ||
			block.getBlockData().getMaterial().equals(Material.SHULKER_BOX) ||
			block.getBlockData().getMaterial().equals(Material.RED_SHULKER_BOX) ||
			block.getBlockData().getMaterial().equals(Material.ORANGE_SHULKER_BOX) ||
			block.getBlockData().getMaterial().equals(Material.YELLOW_SHULKER_BOX) ||
			block.getBlockData().getMaterial().equals(Material.LIME_SHULKER_BOX) ||
			block.getBlockData().getMaterial().equals(Material.GREEN_SHULKER_BOX) ||
			block.getBlockData().getMaterial().equals(Material.CYAN_SHULKER_BOX) ||
			block.getBlockData().getMaterial().equals(Material.BLUE_SHULKER_BOX) ||
			block.getBlockData().getMaterial().equals(Material.PURPLE_SHULKER_BOX) ||
			block.getBlockData().getMaterial().equals(Material.MAGENTA_SHULKER_BOX) ||
			block.getBlockData().getMaterial().equals(Material.LIGHT_BLUE_SHULKER_BOX) ||
			block.getBlockData().getMaterial().equals(Material.PINK_SHULKER_BOX) ||
			block.getBlockData().getMaterial().equals(Material.BROWN_SHULKER_BOX) ||
			block.getBlockData().getMaterial().equals(Material.WHITE_SHULKER_BOX) ||
			block.getBlockData().getMaterial().equals(Material.GRAY_SHULKER_BOX) ||
			block.getBlockData().getMaterial().equals(Material.LIGHT_GRAY_SHULKER_BOX) ||
			block.getBlockData().getMaterial().equals(Material.BLACK_SHULKER_BOX) ||
			block.getBlockData().getMaterial().equals(Material.ANVIL)
			) {
			return true;
		}
		return false;
	}
}
