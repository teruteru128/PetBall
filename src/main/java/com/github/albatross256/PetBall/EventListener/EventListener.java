package com.github.albatross256.PetBall.EventListener;

import com.github.albatross256.PetBall.BallData.BallData;
import com.github.albatross256.PetBall.BallManager;
import com.github.albatross256.PetBall.LoreWriter.factory.LoreWriterFactory;
import com.github.albatross256.PetBall.Main;
import com.github.albatross256.PetBall.WorldManager;
import com.github.albatross256.PetBall.logging.Logger;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.ChiseledBookshelf;
import org.bukkit.block.CommandBlock;
import org.bukkit.block.Container;
import org.bukkit.block.DaylightDetector;
import org.bukkit.block.Dispenser;
import org.bukkit.block.EnchantingTable;
import org.bukkit.block.EnderChest;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.block.Jukebox;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Cake;
import org.bukkit.block.data.type.Comparator;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Gate;
import org.bukkit.block.data.type.Grindstone;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
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
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.bukkit.Material.*;


/**
 * net.minecraft.server -> NMS
*/
public class EventListener implements Listener{

    private static final Set<Material> MATERIALS = Collections.unmodifiableSet(EnumSet.of(CRAFTING_TABLE, CHIPPED_ANVIL, DAMAGED_ANVIL, BEACON, BREWING_STAND, FURNACE_MINECART, HOPPER_MINECART, CAKE, CANDLE_CAKE, CHEST_MINECART, COMMAND_BLOCK, DAYLIGHT_DETECTOR, RESPAWN_ANCHOR, STONECUTTER, CARTOGRAPHY_TABLE, SMITHING_TABLE, LOOM, SHULKER_BOX, RED_SHULKER_BOX, ORANGE_SHULKER_BOX, YELLOW_SHULKER_BOX, LIME_SHULKER_BOX, GREEN_SHULKER_BOX, CYAN_SHULKER_BOX, BLUE_SHULKER_BOX, PURPLE_SHULKER_BOX, MAGENTA_SHULKER_BOX, LIGHT_BLUE_SHULKER_BOX, PINK_SHULKER_BOX, BROWN_SHULKER_BOX, WHITE_SHULKER_BOX, GRAY_SHULKER_BOX, LIGHT_GRAY_SHULKER_BOX, BLACK_SHULKER_BOX, ANVIL));
    private static final Set<Material> TYPES = Collections.unmodifiableSet(EnumSet.of(IRON_DOOR, IRON_TRAPDOOR));
    private BallManager ballManager;
	private WorldManager worldManager;
	private Logger logger;
	private Plugin plugin;
	public EventListener(BallManager ballManager, WorldManager worldManager, Main main) {
		plugin = main;
		var config = main.getConfig();
		var logLevel = config.getString("log-level");
		boolean logLevelIsNull = false;
		if (logLevel == null) {
			logLevel = "info";
			logLevelIsNull = true;
		}
		logger = new Logger(java.util.logging.Logger.getLogger(getClass().getCanonicalName()), logLevel);
		if (logLevelIsNull) {
			logger.warn("log-levelに値がセットされていないためINFOに設定されました");
		}
		this.ballManager = ballManager;
		this.worldManager = worldManager;
	}

	@EventHandler
	public void onBlockDispense(BlockDispenseEvent event){
		if(event.isCancelled() || event.getBlock().getType() .equals(DROPPER)) return;
		if(isEntityBall(event.getItem())){
			event.setCancelled(true);
		}
	}

	private boolean canCatch(EntityType type) {
		return this.ballManager.getAllBallDatas().containsKey(type);
	}

	@EventHandler
	public void onTap(PlayerInteractEvent event) {
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
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
			}else if(mainItem.getType().equals(AIR)){
				entityBall = offItem;
			}
		}

		if(entityBall == null) return;

		if(!this.worldManager.isUsableWorld(event.getPlayer().getWorld().getName())) return;

		CompoundTag nbtTag =  CraftItemStack.asNMSCopy(entityBall).getTag();


		Entity entity = null;

		BallData ballData = null;
		// このへんでとまっている
		var allBallDatas = this.ballManager.getAllBallDatas();
		for(EntityType key : allBallDatas.keySet()) {
			BallData eachBallData = allBallDatas.get(key);

			if(eachBallData.getEntityType().toString().equals(nbtTag.getString(BallData.ENTITYBALL_CONTENT_KEY))) {
				event.setCancelled(true);

				//同時出し入れ対策2
				Long currentTime = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getTime();
				if(Math.abs(nbtTag.getLong(BallData.ENTITYBALL_TIMESTAMP_KEY) - currentTime) < 1) return;

				entity = location.getWorld().spawnEntity(newLocation, eachBallData.getEntityType());
				ballData = eachBallData;
				break;
			}
		}

		if(entity == null) return;


		/* 以下 NBTの解析及び埋め込み */
		byte[] byteNbt = nbtTag.getByteArray(BallData.ENTITYBALL_NBT_KEY);

		try (ByteArrayInputStream bais = new ByteArrayInputStream(byteNbt)) {
			CompoundTag nbt = NbtIo.readCompressed(bais, NbtAccounter.unlimitedHeap());
			((CraftEntity) entity).getHandle().load(nbt);
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
		CompoundTag nbttag = new CompoundTag();
		nbttag.putString(key, value);
		net.minecraft.world.item.ItemStack itemCopy = CraftItemStack.asNMSCopy(item);
		itemCopy.setTag(nbttag);
		ItemStack entityBall = CraftItemStack.asBukkitCopy(itemCopy);
		return entityBall;
	}

	private boolean isEntityBall(ItemStack item) {
		if(item == null) return false;
		ItemMeta itemMeta = item.getItemMeta();
		if(itemMeta == null) return false;

		net.minecraft.world.item.ItemStack handItemCopy = CraftItemStack.asNMSCopy(item);
		CompoundTag handItemNbtTag = handItemCopy.getTag();
		if(handItemNbtTag != null && handItemNbtTag.contains(BallData.ENTITYBALL_CONTENT_KEY)) return true;

		return false;
	}

	private boolean isEntityEmptyBall(ItemStack item) {
		ItemMeta itemMeta = item.getItemMeta();
		if(itemMeta == null) return false;

		net.minecraft.world.item.ItemStack handItemCopy = CraftItemStack.asNMSCopy(item);
		CompoundTag handItemNbtTag = handItemCopy.getTag();
		if(handItemNbtTag != null && handItemNbtTag.contains(BallData.ENTITYBALL_CONTENT_KEY) && handItemNbtTag.getString(BallData.ENTITYBALL_CONTENT_KEY).equals(BallData.ENTITYBALL_CONTENT_EMPTY)) return true;

		return false;
	}

	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
		ItemStack mainItem = event.getPlayer().getInventory().getItemInMainHand();
		ItemStack offItem = event.getPlayer().getInventory().getItemInOffHand();

		//子供生成対策
		var allBallDatas = this.ballManager.getAllBallDatas();
		for(EntityType key : allBallDatas.keySet()) {
			BallData ballData = allBallDatas.get(key);

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
		logger.trace("子供生成対策終わり");
		ItemStack entityEmptyBall = null;
		if(!isEntityBall(mainItem) && !isEntityBall(offItem)) {
			logger.trace("not entity ball");
			return;
		}
		if(!this.canCatch(event.getRightClicked().getType())) {
			logger.trace("can not catch RightClicked");
			return;
		}

		boolean isMainHand = true;
		if(this.isEntityEmptyBall(mainItem)) {
			entityEmptyBall = mainItem;
			isMainHand = true;
		}else if(this.isEntityEmptyBall(offItem) && mainItem.getType().equals(AIR)) {
			entityEmptyBall = offItem;
			isMainHand = false;
		}

		if(!this.worldManager.isUsableWorld(event.getPlayer().getWorld().getName())) {
			logger.trace("usage world");
			return;
		}

		CompoundTag handItemNbttag = CraftItemStack.asNMSCopy(mainItem).getTag();
		if(handItemNbttag == null || !handItemNbttag.contains(BallData.ENTITYBALL_CONTENT_KEY) ||
				!handItemNbttag.getString(BallData.ENTITYBALL_CONTENT_KEY).equals(BallData.ENTITYBALL_CONTENT_EMPTY)) {
			logger.trace("not entity ball");
			return;
		}

		Entity entity = event.getRightClicked();

		if(!this.canCatch(entity.getType())) {
			logger.trace("can not catch");
			return;
		}


		/*
		 * 馬 チェストアイテム ばらまき
		 */

		if(entity instanceof ChestedHorse horse) {
			Location entityLocation  = entity.getLocation();
			Location dropItemLocation = new Location(entity.getWorld(), entityLocation.getX()+0.5, entityLocation.getY()+0.5, entityLocation.getZ()+0.5);
			for(ItemStack strageItem : horse.getInventory().getStorageContents()) {
				if(strageItem != null) {
					entity.getWorld().dropItem(dropItemLocation, strageItem);
				}
			}
			horse.getInventory().clear();
		}

		net.minecraft.world.entity.Entity nmsEntity = ((CraftEntity) entity).getHandle();
		CompoundTag tag = new CompoundTag();
		nmsEntity.saveAsPassenger(tag);
		event.setCancelled(true);

		ItemStack item = new ItemStack(this.ballManager.getBallData(entity.getType()).getFilledBallMaterial(), 1);
		net.minecraft.world.item.ItemStack itemCopy = CraftItemStack.asNMSCopy(item);
		CompoundTag nbttag = new CompoundTag();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			NbtIo.writeCompressed(tag, baos);
		} catch (IOException e) {
			logger.getLogger().throwing("EventListener", "onPlayerInteractEntityEvent", e);
		}
		byte[] byteNbt = baos.toByteArray();

		//空気対策
		if(tag.getString("id") == null || tag.getString("id").equals("")) {
			logger.trace("tag is empty");
			return;
		}

		nbttag.putByteArray(BallData.ENTITYBALL_NBT_KEY, byteNbt);
		nbttag.putString(BallData.ENTITYBALL_CONTENT_KEY, entity.getType().toString());

		/*
		 * 出し入れ同時対策1
		 */
		long time = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getTime();
		nbttag.putLong(BallData.ENTITYBALL_TIMESTAMP_KEY, time);
		entity.getPersistentDataContainer().set(new NamespacedKey(this.plugin, BallData.ENTITYBALL_TIMESTAMP_KEY), PersistentDataType.LONG, entity.getWorld().getFullTime());

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
		logger.trace("done");
	}

	private boolean isTouchable(Block block) {

		var type = block.getType();
		if(TYPES.contains(type)){
			// 鉄シリーズは問答無用でfalse
			return false;
		} else {
			var state = block.getState();
			if(state instanceof Sign sign){
				// 看板は編集可・不可の状態が変化するので、動的に取得する
				return !sign.isWaxed();
			} else {
				var blockData = block.getBlockData();
				var material = blockData.getMaterial();
                // それ以外
                // ベルは触った場所が本体以外だとうまく動作しないが、場所を知る手立てがないため入れてない
                // コンポスターは最大状態以外だとうまく動作しないが、知る手立てがないため入れていない
                return state instanceof Container ||
                        state instanceof EnderChest ||
                        state instanceof EnchantingTable ||
                        state instanceof CommandBlock ||
                        state instanceof DaylightDetector ||
                        state instanceof Jukebox ||
                        state instanceof ChiseledBookshelf ||
                        blockData instanceof Door ||
                        blockData instanceof TrapDoor ||
                        blockData instanceof Bed ||
                        blockData instanceof Gate ||
                        blockData instanceof Cake ||
                        blockData instanceof Switch ||
                        blockData instanceof Repeater ||
                        blockData instanceof Dispenser ||
                        blockData instanceof Comparator ||
                        blockData instanceof Hopper ||
                        blockData instanceof NoteBlock ||
                        blockData instanceof Chest ||
                        blockData instanceof Grindstone ||
                        blockData instanceof Furnace ||
                        blockData instanceof Barrel ||
                        MATERIALS.contains(material);
			}
		}
    }
}
