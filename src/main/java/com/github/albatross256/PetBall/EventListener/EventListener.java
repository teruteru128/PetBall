package com.github.albatross256.PetBall.EventListener;

import com.github.albatross256.PetBall.BallData.BallData;
import com.github.albatross256.PetBall.BallManager;
import com.github.albatross256.PetBall.LoreWriter.factory.LoreWriterFactory;
import com.github.albatross256.PetBall.Main;
import com.github.albatross256.PetBall.WorldManager;
import com.github.teruteru128.logger.Logger;
import com.saicone.rtag.RtagEditor;
import com.saicone.rtag.RtagEntity;
import com.saicone.rtag.RtagItem;
import com.saicone.rtag.stream.TStream;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
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
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.ChestedHorse;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Mule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.HorseInventory;
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

import static java.util.Map.entry;
import static org.bukkit.Material.*;


/**
 * net.minecraft.server -> NMS
*/
public class EventListener implements Listener{

    private static final Set<Material> MATERIALS = Collections.unmodifiableSet(EnumSet.of(CRAFTING_TABLE, CHIPPED_ANVIL, DAMAGED_ANVIL, BEACON, BREWING_STAND, FURNACE_MINECART, HOPPER_MINECART, CAKE, CANDLE_CAKE, CHEST_MINECART, COMMAND_BLOCK, DAYLIGHT_DETECTOR, RESPAWN_ANCHOR, STONECUTTER, CARTOGRAPHY_TABLE, SMITHING_TABLE, LOOM, SHULKER_BOX, RED_SHULKER_BOX, ORANGE_SHULKER_BOX, YELLOW_SHULKER_BOX, LIME_SHULKER_BOX, GREEN_SHULKER_BOX, CYAN_SHULKER_BOX, BLUE_SHULKER_BOX, PURPLE_SHULKER_BOX, MAGENTA_SHULKER_BOX, LIGHT_BLUE_SHULKER_BOX, PINK_SHULKER_BOX, BROWN_SHULKER_BOX, WHITE_SHULKER_BOX, GRAY_SHULKER_BOX, LIGHT_GRAY_SHULKER_BOX, BLACK_SHULKER_BOX, ANVIL));
    private static final Set<Material> TYPES = Collections.unmodifiableSet(EnumSet.of(IRON_DOOR, IRON_TRAPDOOR));
	private static final Map<String, String > OLD_ENTITY_TYPES = Map.ofEntries(entry("MUSHROOM_COW", EntityType.MOOSHROOM.toString()), entry("SNOWMAN", EntityType.SNOW_GOLEM.toString()));
	private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(
			EventListener.class);
	private BallManager ballManager;
	private WorldManager worldManager;
	private Logger logger;
	private Plugin plugin;
	public EventListener(BallManager ballManager, WorldManager worldManager, Main main) {
		plugin = main;
		logger = Logger.getInstance(plugin);
		this.ballManager = ballManager;
		this.worldManager = worldManager;
	}

	/**
	 * ブロック排出イベント処理
	 * @param event BlockDispenseEvent
	 * */
	@EventHandler
	public void onBlockDispense(BlockDispenseEvent event){
		logger.debug("onBlockDispense:Start");
		logger.trace("event.isCancelled():" + event.isCancelled());
		logger.trace("event.getBlock():" + event.getBlock());
		logger.trace("event.getBlock().getType():" + event.getBlock().getType());
		logger.trace("event.isCancelled() || event.getBlock().getType() .equals(DROPPER):" +(event.isCancelled() || event.getBlock().getType() .equals(DROPPER)));
		if(event.isCancelled() || event.getBlock().getType() .equals(DROPPER)) {
			logger.debug("イベントキャンセルか、ドロッパーが実施");
			return;
		}
		if(isEntityBall(event.getItem())){
			logger.debug("isEntityBall=true");
			event.setCancelled(true);
		}
		logger.debug("onBlockDispense:End");
	}

	/**
	 * 捕獲可能なエンティティか
	 * @param type エンティティのタイプ
	 * @return チェック結果
	 * */
	private boolean canCatch(EntityType type) {
		logger.debug("canCatchCall");
		logger.trace("type:" + type);
		return this.ballManager.getAllBallDatas().containsKey(type);
	}

	/**
	 * プレイヤーが右クリックした時のイベント。
	 * PetBallの取り出し処理もここで実施。
	 * @param event PlayerInteractEvent
	 * */
	@EventHandler
	public void onTap(PlayerInteractEvent event) {
		logger.debug("onTap:Start");
		logger.trace("event:" + event);
		logger.trace("event.getAction():" + event.getAction());
		logger.trace("event.getAction().equals(Action.RIGHT_CLICK_AIR):" + event.getAction().equals(Action.RIGHT_CLICK_AIR));

		if(event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			logger.debug("right click AIR");
			logger.trace("this.isEntityBall(event.getItem()):" + this.isEntityBall(event.getItem()));
			if (this.isEntityBall(event.getItem())) {
				event.setCancelled(true);
				logger.trace("isEntityBall:True");
			} else {
				logger.trace("isEntityBall:False");
			}
			return;
		}else if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			logger.debug("is not action RIGHT_CLICK_BLOCK");
			return;
		}

		Location location = event.getClickedBlock().getLocation();
		logger.trace("event.getClickedBlock():" + event.getClickedBlock());
		logger.trace("location:" + location);

		Location newLocation = new Location(
				location.getWorld(),
				location.getX() + event.getBlockFace().getModX() + 0.5,
				location.getY() + event.getBlockFace().getModY(),
				location.getZ() + event.getBlockFace().getModZ() + 0.5
				);
		logger.trace("location.getWorld():" + location.getWorld());
		logger.trace("location.getX():" + location.getX());
		logger.trace("event.getBlockFace().getModX() + 0.5:" + event.getBlockFace().getModX() + 0.5);
		logger.trace("location.getY():" + location.getY());
		logger.trace("event.getBlockFace().getModY():" + event.getBlockFace().getModY());
		logger.trace("location.getZ():" + location.getZ());
		logger.trace("event.getBlockFace().getModZ() + 0.5:" + event.getBlockFace().getModZ() + 0.5);
		logger.trace("newLocation:" + newLocation);

		logger.trace("!event.getPlayer().isSneaking():" + !event.getPlayer().isSneaking());
		logger.trace("isTouchable(event.getClickedBlock()):" + isTouchable(event.getClickedBlock()));
		if(!event.getPlayer().isSneaking() && isTouchable(event.getClickedBlock())) {
			logger.debug("is not Sneaking or is Touchable");
			return;
		}

		ItemStack mainItem = event.getPlayer().getInventory().getItemInMainHand();
		ItemStack offItem = event.getPlayer().getInventory().getItemInOffHand();
		logger.trace("mainItem:" + mainItem);
		logger.trace("offItem:" + offItem);

		ItemStack entityBall = null;
		boolean isEntityBallMain = isEntityBall(mainItem);
		boolean isEntityBallOff = isEntityBall(offItem);
		logger.trace("isEntityBallMain:" + isEntityBallMain);
		logger.trace("isEntityBallOff:" + isEntityBallOff);
		if(isEntityBallMain){
			logger.debug("mainItem is EntityBall");
			event.setCancelled(true);

			logger.trace("isEntityEmptyBall(mainItem):" + isEntityEmptyBall(mainItem));
			if(isEntityEmptyBall(mainItem)) {
				logger.debug("mainItem is EntityEmptyBall");
				return;
			}else{
				logger.debug("entityBall is MainItem");
				entityBall = mainItem;
			}
		}else if(isEntityBallOff) {
			logger.trace("OffItem is EntityBall");
			event.setCancelled(true);

			logger.trace("isEntityEmptyBall(offItem):" + isEntityEmptyBall(offItem));
			logger.trace("mainItem.getType().equals(AIR):" + mainItem.getType().equals(AIR));
			if(isEntityEmptyBall(offItem)) {
				logger.debug("offItem is EntityEmptyBall");
				return;
			}else if(mainItem.getType().equals(AIR)){
				logger.debug("mainItem.getType().equals(AIR) True");
				entityBall = offItem;
			}
		}

		logger.trace("entityBall:" + entityBall);
		if(entityBall == null) {
			logger.debug("entity ball is null");
			return;
		}

		logger.trace("!this.worldManager.isUsableWorld(event.getPlayer().getWorld().getName()):" + !this.worldManager.isUsableWorld(event.getPlayer().getWorld().getName()));
		if(!this.worldManager.isUsableWorld(event.getPlayer().getWorld().getName())) {
			logger.debug("is not Usable World");
			return;
		}

		RtagItem tag = new RtagItem(entityBall);
//		CompoundTag nbtTag =  CraftItemStack.asNMSCopy(entityBall).getTag();
//		logger.trace("nbtTag:" + nbtTag);

		Entity entity = null;
		BallData ballData = null;
		// このへんでとまっている
		var allBallDatas = this.ballManager.getAllBallDatas();
//		var entityBallContentKey = nbtTag.getString(BallData.ENTITYBALL_CONTENT_KEY);
		var entityBallContentKey = tag.get(BallData.ENTITYBALL_CONTENT_KEY);
		logger.trace("this.ballManager:" + this.ballManager);
		logger.trace("allBallDatas:" + allBallDatas);
		logger.trace("entityBallContentKey:" + entityBallContentKey);

		// 1.20.5で内部エンティティデータキーが変更になったので、それを新しいキーに変換する
		logger.trace("OLD_ENTITY_TYPES.containsKey(entityBallContentKey): " + OLD_ENTITY_TYPES.containsKey(entityBallContentKey));
		if (OLD_ENTITY_TYPES.containsKey(entityBallContentKey)) {
			entityBallContentKey = OLD_ENTITY_TYPES.get(entityBallContentKey);
		}

		for(EntityType key : allBallDatas.keySet()) {
			BallData eachBallData = allBallDatas.get(key);
			logger.trace("key:" + key);
			logger.trace("eachBallData:" + eachBallData);
			logger.trace("eachBallData.getEntityType().toString().equals(entityBallContentKey):" + eachBallData.getEntityType().toString().equals(entityBallContentKey));
			if(eachBallData.getEntityType().toString().equals(entityBallContentKey)) {
				logger.debug("eachBallData entityBallContentKey is Found. key:" + key);
				event.setCancelled(true);

				//同時出し入れ対策2
				long currentTime = System.currentTimeMillis();
//				var entityBallTimestamp = nbtTag.getLong(BallData.ENTITYBALL_TIMESTAMP_KEY);
				var entityBallTimestamp = tag.getOptional(BallData.ENTITYBALL_TIMESTAMP_KEY).asLong();
				var abs = Math.abs(currentTime - entityBallTimestamp);
				logger.trace("currentTime = " + currentTime + ", entityBallTimestamp = " + entityBallTimestamp + ", diff = " + abs);
				// 差が50ミリ秒未満だった場合拒否する
				if (abs < 50) {
					logger.debug("abs < 50");
					return;
				}

				entity = location.getWorld().spawnEntity(newLocation, eachBallData.getEntityType());
				ballData = eachBallData;
				logger.trace("entity:" + entity);
				break;
			}
		}

		if(entity == null) {
			logger.debug("entity is null");
			return;
		}


		/* 以下 NBTの解析及び埋め込み */
//		byte[] byteNbt = nbtTag.getByteArray(BallData.ENTITYBALL_NBT_KEY);
        this.logger.trace("[TRACE] EventListener.EventListener.onTap Parse NBT START");
        RtagEntity entityTag = new RtagEntity(entity);
        this.logger.trace("[TRACE] RtagEntity entityTag <- new RtagEntity(entity)");
        
        RtagItem itemTag = new RtagItem(mainItem);

		// 1.20.5以降のタグ保存用のデータ
		ItemStack equipedHorseArmor = null;
				if(tag.hasTag(BallData.ENTITYBALL_ISC_KEY)){
					 Map tags = tag.get(BallData.ENTITYBALL_ISC_KEY);
					 tags.forEach((k, v) -> {
					 entityTag.set(v, k);
					 });
				} else {
					// 1.20.4以前のNBTデータから馬鎧を取り出す
					var nbtTag = itemTag.get().get(BallData.ENTITYBALL_NBT_KEY);
					boolean isInstanceOfBytes = nbtTag instanceof byte[];

					if (isInstanceOfBytes) {
						byte[] tagCompoundBytes = (byte[]) nbtTag;
						var tagCompound = TStream.COMPOUND.fromBytes(tagCompoundBytes);
						Map<String, Object> tags = TStream.COMPOUND.toMap(tagCompound);
						if (!tags.keySet().isEmpty()) {
							for (String k : tags.keySet()) {
								Object v = tags.get(k);
								this.logger.trace("[TRACE] key=" + k.toString());
								this.logger.trace("[TRACE] value=" + v.toString());

								boolean isArmorItems = k.equals("ArmorItems");
								this.logger.trace("[TRACE] boolean isArmorItems <- k.equals(\"ArmorItems\")");
								this.logger.trace("[TRACE] isArmorItems ? " + isArmorItems);

								if (isArmorItems) {
									List<Map<String, Object>> armorItemList = (List<Map<String, Object>>) v;
									boolean isNotEmptyArmorItemList = !armorItemList.isEmpty();
									this.logger.trace("[TRACE] boolean isNotEmptyArmorItemList <- !armorItemList.isEmpty()");
									this.logger.trace("[TRACE] isNotEmptyArmorItemList ? " + isNotEmptyArmorItemList);

									if (isNotEmptyArmorItemList) {
										// Armorはプレイヤーと一緒で４部位に分かれて格納されているため、格納部分を探しに行く
										for (Map<String, Object> armorMap : armorItemList) {
											boolean isNotEmptyArmorMap = !armorMap.keySet().isEmpty();
											this.logger.trace("[TRACE] boolean isNotEmptyArmorMap <- !armorMap.keySet().isEmpty()");
											this.logger.trace("[TRACE] isNotEmptyArmorMap ? " + isNotEmptyArmorMap);
											if (isNotEmptyArmorMap) {
												Integer count = null;
												Material armorMaterial = null;
												for (String armKey : armorMap.keySet()) {
													boolean isIdCount = armKey.equals("Count");
													this.logger.trace("[TRACE] boolean isIdCount = armKey.equals(\"Count\")");
													this.logger.trace("[TRACE] isIdCount ? " + isIdCount);
													if (isIdCount) {
														count = Integer.valueOf(armorMap.get(armKey).toString());
														this.logger.trace("[TRACE] int count <- Integer.valueOf(armorMap.get(armKey).toString())");
														this.logger.trace("[TRACE] count=" + count);
													}

													boolean isIdKey = armKey.equals("id");
													this.logger.trace("[TRACE] boolean isIdKey = armKey.equals(\"id\")");
													this.logger.trace("[TRACE] isIdKey ? " + isIdKey);
													if (isIdKey) {
														String namespaceKey = armorMap.get(armKey).toString();
														armorMaterial = Material.matchMaterial(namespaceKey);
														this.logger.trace("[TRACE] Material armorMaterial <- Material.matchMaterial(armKey)");
														this.logger.trace("[TRACE] armorMaterial=" + armorMaterial);
													}

													// アーマー名と数量が入ってたら、設定されているものと見て馬鎧を設定する
													if (Objects.nonNull(armorMaterial) && Objects.nonNull(count)) {
														equipedHorseArmor = new ItemStack(armorMaterial, count.intValue());
														this.logger.trace("[TRACE] equipedHorseArmor <- new ItemStack(armorMaterial, count)");
														this.logger.trace("[TRACE] equipedHorseArmor=" + equipedHorseArmor);
													}
												}
											}
										}
									}
								} else {
									// 入っていたタグを召喚したエンティティタグに設定する
									entityTag.set(v, k);
								}
							}
						}
					}
				}
//		try (ByteArrayInputStream bais = new ByteArrayInputStream(byteIsc)) {
//			CompoundTag nbt = NbtIo.readCompressed(bais, NbtAccounter.unlimitedHeap());
//			((CraftEntity) entity).getHandle().load(nbt);
//			((CraftEntity) entity).getHandle().absMoveTo(newLocation.getX(), newLocation.getY(), newLocation.getZ(), 0, 0);
		entityTag.load();
		this.logger.trace("[TRACE] entityTag.load()");

		// 旧式馬鎧の設定
		if (Objects.nonNull(equipedHorseArmor) && entity instanceof Horse horse) {
			HorseInventory horseInv = horse.getInventory();
			this.logger.trace("[TRACE] HorseInventory horseInv <- horse.getInventory()");
			this.logger.trace("[TRACE] horseInv=" + horseInv);
			boolean isEmptyInventory = horseInv.isEmpty();
			this.logger.trace("boolean isEmptyInventory <- horseInv.isEmpty() ? ");
			this.logger.trace("[TRACE] isEmptyInventory ? " + isEmptyInventory);
			horseInv.setArmor(equipedHorseArmor);
		}
		// タグ情報にエンティティの位置情報があるため、ここで今ボールを使った座標に移動させる
			entity.teleport(newLocation);
			this.logger.trace("[TRACE]EventListener.EventListener.onTap Parse NBT END");
//			logger.trace("nbt:" + nbt);
			logger.debug("bais OK");
//		} catch (IOException e) {
//			logger.debug("bais Error");
//			logger.getLogger().throwing("com.github.albatross256.PetBall.EventListener.EventListener", "onTap", e);
//		}

		/* 以下ボールの生成及びインベントリ転送*/
		ItemStack dItemStack = new ItemStack(entityBall);
		dItemStack.setAmount(1);
		HashMap<Integer, ItemStack> leftItems = event.getPlayer().getInventory().removeItem(dItemStack);
		logger.trace("leftItems" + leftItems);
		for(Integer key : leftItems.keySet()) {
			offItem.setAmount(offItem.getAmount() - 1);
		}

		ItemStack addItem = new ItemStack(this.ballManager.getBallData(entity.getType()).getEmptyBallMaterial(), 1);
		logger.trace("addItem:" + addItem);
		addItem = this.getMetaItem(addItem, BallData.ENTITYBALL_CONTENT_KEY, BallData.ENTITYBALL_CONTENT_EMPTY);
		ItemMeta meta = addItem.getItemMeta();
		meta.setDisplayName(this.ballManager.getBallData(entity.getType()).getDisplayName());
		logger.trace("afterAddItem:" + addItem);
		logger.trace("meta:" + meta);
		logger.trace("this.ballManager.getBallData(entity.getType()).getDisplayName()" + this.ballManager.getBallData(entity.getType()).getDisplayName());

		// 空のボール作成
		List<String> lore = new ArrayList<String>();
		lore.add("Empty");
		meta.setLore(lore);
		addItem.setItemMeta(meta);

		Player player = event.getPlayer();
		PlayerInventory inventory = player.getInventory();
		logger.trace("player:" + player);
		logger.trace("inventory:" + inventory);
		logger.trace("inventory.getItem(inventory.getHeldItemSlot()) == null :" + (inventory.getItem(inventory.getHeldItemSlot()) == null ));
		if(inventory.getItem(inventory.getHeldItemSlot()) == null ) {
			logger.debug("inventory is Empty");
			inventory.setItem(inventory.getHeldItemSlot(), addItem);
		}else {
			logger.debug("inventory is not Empty");
			HashMap<Integer, ItemStack> notAddedItems = inventory.addItem(addItem);
			if(!notAddedItems.isEmpty()) {
				logger.debug("notAddedItems is Empty");
				player.getWorld().dropItem(player.getLocation(), notAddedItems.get(0));
				player.sendMessage(ChatColor.GREEN + "[PetBall] " + ChatColor.RED +":: 空きスロット不足 :: 空のPetBallを地面に捨てました" );
			}
		}
		logger.debug("onTap:End");
	}

	/**
	 * アイテムの作成処理
	 * @param item Item情報
	 * @param key NBTタグのキー
	 * @param value NBTタグの値
	 *
	 * @return 作成したアイテム情報
	 * */
	private ItemStack getMetaItem(ItemStack item, String key, String value) {
		logger.debug("getMetaItem:Start");

//		CompoundTag nbttag = new CompoundTag();
//		logger.trace("nbttag:" + nbttag);
		ItemStack entityBall = item.clone();
		RtagEditor tag = new RtagItem(entityBall);

		logger.trace("tag:" + tag);
//		nbttag.putString(key, value);
//		net.minecraft.world.item.ItemStack itemCopy = CraftItemStack.asNMSCopy(item);
//		itemCopy.setTag(nbttag);
//		ItemStack entityBall = CraftItemStack.asBukkitCopy(itemCopy);
		tag.set(value, key);
		tag.load();
//		logger.trace("nbttag:" + nbttag);
//		logger.trace("itemCopy:" + itemCopy);
		logger.trace("tag:" + tag);
		logger.trace("entityBall:" + entityBall);

		logger.debug("getMetaItem:End");
		return entityBall;
	}

	/**
	 * PetBallかの判定処理
	 * @param item Item情報
	 *
	 * @return チェック結果
	 * */
	private boolean isEntityBall(ItemStack item) {
		logger.debug("isEntityBall:Start");
		if(item == null) {
			logger.debug("item is NULL");
			return false;
		}
		ItemMeta itemMeta = item.getItemMeta();
		if(itemMeta == null) {
			logger.debug("itemMeta is NULL");
			return false;
		}

//		net.minecraft.world.item.ItemStack handItemCopy = CraftItemStack.asNMSCopy(item);
//		CompoundTag handItemNbtTag = handItemCopy.getTag();
		ItemStack handItemCopy = item.clone();
		RtagItem tag = new RtagItem(handItemCopy);
//		RtagItem tag = new RtagItem(item);
//		logger.trace("handItemNbtTag != null:" + (handItemNbtTag != null));
//		if(handItemNbtTag != null && handItemNbtTag.contains(BallData.ENTITYBALL_CONTENT_KEY)) {
		if(tag.hasTag(BallData.ENTITYBALL_CONTENT_KEY)) {
			logger.debug("is Entity Ball");
			return true;
		}
//		}

		logger.debug("itemMeta is not Exist");
		return false;
	}

	/**
	 * 空のPetBallかの判定処理
	 * @param item Item情報
	 *
	 * @return チェック結果
	 * */
	private boolean isEntityEmptyBall(ItemStack item) {
		logger.debug("isEntityEmptyBall:Start");
		ItemMeta itemMeta = item.getItemMeta();
		if(itemMeta == null) {
			logger.debug("itemMeta is NULL");
			return false;
		}

//		net.minecraft.world.item.ItemStack handItemCopy = CraftItemStack.asNMSCopy(item);
//		CompoundTag handItemNbtTag = handItemCopy.getTag();
//		logger.trace("itemMeta:" + itemMeta);
//		logger.trace("handItemNbtTag:" + handItemNbtTag);
//		logger.trace("handItemNbtTag != null :" + (handItemNbtTag != null ));
//		logger.trace("handItemNbtTag.contains(BallData.ENTITYBALL_CONTENT_KEY):" + handItemNbtTag.contains(BallData.ENTITYBALL_CONTENT_KEY));
//		logger.trace("handItemNbtTag.getString(BallData.ENTITYBALL_CONTENT_KEY).equals(BallData.ENTITYBALL_CONTENT_EMPTY):" + handItemNbtTag.getString(BallData.ENTITYBALL_CONTENT_KEY).equals(BallData.ENTITYBALL_CONTENT_EMPTY));
		ItemStack handItemCopy = item.clone();
		RtagItem tag = new RtagItem(handItemCopy);
//		if(handItemNbtTag != null && handItemNbtTag.contains(BallData.ENTITYBALL_CONTENT_KEY) && handItemNbtTag.getString(BallData.ENTITYBALL_CONTENT_KEY).equals(BallData.ENTITYBALL_CONTENT_EMPTY)) {
		if(tag.hasTag(BallData.ENTITYBALL_CONTENT_KEY) && tag.get(BallData.ENTITYBALL_CONTENT_KEY).toString().equals(BallData.ENTITYBALL_CONTENT_EMPTY)) {
			logger.debug("item is EntityEmptyBall");
			return true;
		}
//		}

		logger.debug("item is not empty ball.");
		return false;
	}

	/**
	 * プレイヤーがエンティティに対して右クリックした時のイベント。
	 * PetBall収納処理を実施。
	 * @param event PlayerInteractEntityEvent
	 * */
	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
		logger.debug("onPlayerInteractEntityEvent:Start");
		ItemStack mainItem = event.getPlayer().getInventory().getItemInMainHand();
		ItemStack offItem = event.getPlayer().getInventory().getItemInOffHand();

		//子供生成対策
		var allBallDatas = this.ballManager.getAllBallDatas();
		logger.trace("mainItem:" + mainItem);
		logger.trace("offItem:" + offItem);
		logger.trace("allBallDatas:" + allBallDatas);
		for(EntityType key : allBallDatas.keySet()) {
			BallData ballData = allBallDatas.get(key);

			logger.trace("key:" + key);
			logger.trace("ballData:" + ballData);
			logger.trace("event.getRightClicked().getType().equals(ballData.getFilledBallEntityType()):" + event.getRightClicked().getType().equals(ballData.getFilledBallEntityType()));
			logger.trace("event.getRightClicked().getType().equals(ballData.getEmptyBallEntityType()):" + event.getRightClicked().getType().equals(ballData.getEmptyBallEntityType()));
			if(event.getRightClicked().getType().equals(ballData.getFilledBallEntityType())) {
				logger.debug("getFilledBallEntityType is found. key:" + key);
				logger.trace("isEntityBall(mainItem):" + isEntityBall(mainItem));
				logger.trace("!isEntityEmptyBall(mainItem) :" + !isEntityEmptyBall(mainItem) );
				logger.trace("mainItem.getType().equals(ballData.getFilledBallMaterial()):" + mainItem.getType().equals(ballData.getFilledBallMaterial()));
				logger.trace("isEntityBall(offItem):" + isEntityBall(offItem));
				logger.trace("!isEntityEmptyBall(offItem):" + !isEntityEmptyBall(offItem));
				logger.trace("offItem.getType().equals(ballData.getFilledBallMaterial()):" + offItem.getType().equals(ballData.getFilledBallMaterial()));
				if(isEntityBall(mainItem) && !isEntityEmptyBall(mainItem) && mainItem.getType().equals(ballData.getFilledBallMaterial())) {
					logger.debug("mainItem is not EmptyBall");
					event.setCancelled(true);
					return;
				}else if(isEntityBall(offItem) && !isEntityEmptyBall(offItem) && offItem.getType().equals(ballData.getFilledBallMaterial())){
					logger.debug("offItem is not EmptyBall");
					event.setCancelled(true);
					return;
				}
			}else if(event.getRightClicked().getType().equals(ballData.getEmptyBallEntityType())) {
				logger.debug("getFilledBallEntityType is not found.");
				logger.trace("isEntityEmptyBall(mainItem):" + isEntityEmptyBall(mainItem));
				logger.trace("mainItem.getType().equals(ballData.getEmptyBallMaterial()):" + mainItem.getType().equals(ballData.getEmptyBallMaterial()));
				logger.trace("isEntityEmptyBall(offItem) :" + offItem.getType().equals(ballData.getEmptyBallMaterial()));
				if(isEntityEmptyBall(mainItem) && mainItem.getType().equals(ballData.getEmptyBallMaterial())) {
					logger.debug("mainItem is EntityEmptyBall");
					event.setCancelled(true);
					return;
				}else if(isEntityEmptyBall(offItem) && offItem.getType().equals(ballData.getEmptyBallMaterial())){
					logger.debug("offItem is EntityEmptyBall");
					event.setCancelled(true);
				}
			}
		}
		ItemStack entityEmptyBall = null;
		if(!isEntityBall(mainItem) && !isEntityBall(offItem)) {
			logger.debug("not entity ball");
			return;
		}
		if(!this.canCatch(event.getRightClicked().getType())) {
			logger.debug("can not catch RightClicked");
			return;
		}

		boolean isMainHand = true;
		logger.trace("this.isEntityEmptyBall(mainItem):" + this.isEntityEmptyBall(mainItem));
		logger.trace("this.isEntityEmptyBall(offItem):" + this.isEntityEmptyBall(offItem));
		logger.trace("mainItem.getType().equals(AIR):" + mainItem.getType().equals(AIR));
		if(this.isEntityEmptyBall(mainItem)) {
			logger.debug("mainItem is Empty Ball");
			entityEmptyBall = mainItem;
			isMainHand = true;
		}else if(this.isEntityEmptyBall(offItem) && mainItem.getType().equals(AIR)) {
			logger.debug("offItem is Empty Ball");
			entityEmptyBall = offItem;
			isMainHand = false;
		}

		logger.trace("!this.worldManager.isUsableWorld(event.getPlayer().getWorld().getName()):" + !this.worldManager.isUsableWorld(event.getPlayer().getWorld().getName()));
		if(!this.worldManager.isUsableWorld(event.getPlayer().getWorld().getName())) {
			logger.debug("not usage world");
			return;
		}

		RtagItem handTag = new RtagItem(mainItem);
//		CompoundTag handItemNbttag = CraftItemStack.asNMSCopy(mainItem).getTag();
//		logger.trace("handItemNbttag:" + handItemNbttag);
//		logger.trace("!handItemNbttag.contains(BallData.ENTITYBALL_CONTENT_KEY):" + !handItemNbttag.contains(BallData.ENTITYBALL_CONTENT_KEY));
//		logger.trace("!handItemNbttag.getString(BallData.ENTITYBALL_CONTENT_KEY).equals(BallData.ENTITYBALL_CONTENT_EMPTY):" + !handItemNbttag.getString(BallData.ENTITYBALL_CONTENT_KEY).equals(BallData.ENTITYBALL_CONTENT_EMPTY));
		if(!handTag.hasTag(BallData.ENTITYBALL_CONTENT_KEY) || !handTag.get(BallData.ENTITYBALL_CONTENT_KEY).toString().equals(BallData.ENTITYBALL_CONTENT_EMPTY)) {
//		if(handItemNbttag == null || !handItemNbttag.contains(BallData.ENTITYBALL_CONTENT_KEY) ||
//				!handItemNbttag.getString(BallData.ENTITYBALL_CONTENT_KEY).equals(BallData.ENTITYBALL_CONTENT_EMPTY)) {
			logger.debug("not entity ball");
			return;
//		}
		}

		Entity entity = event.getRightClicked();
		logger.trace("entity:" + entity);
		logger.trace("!this.canCatch(entity.getType()):" + !this.canCatch(entity.getType()));
		if(!this.canCatch(entity.getType())) {
			logger.debug("can not catch");
			return;
		}

		/*
		 * 馬 チェストアイテム ばらまき
		 */

		if(entity instanceof ChestedHorse horse) {
			logger.debug("entity is ChestedHorse");

			Location entityLocation  = entity.getLocation();
			Location dropItemLocation = new Location(entity.getWorld(), entityLocation.getX()+0.5, entityLocation.getY()+0.5, entityLocation.getZ()+0.5);
			boolean hasSaddle = false;
			AbstractHorse abHorse = null;
			logger.trace("entity:" + entity);
			logger.trace("entityLocation:" + entityLocation);
			logger.trace("dropItemLocation:" + dropItemLocation);
			logger.trace("entity.getWorld():" + entity.getWorld());
			logger.trace("entityLocation.getX()+0.5:" + entityLocation.getX()+0.5);
			logger.trace("entityLocation.getY()+0.5:" + entityLocation.getY()+0.5);
			logger.trace("entityLocation.getZ()+0.5:" + entityLocation.getZ()+0.5);

			// ロバかラバの場合、外れないように先頭にある鞍をあらかじめ除外しておく
			if(entity instanceof Donkey|| entity instanceof Mule) {
				logger.trace("entity is Donkey or Mule.");
				abHorse = (AbstractHorse)entity;

				// サドルがついてる場合は、フラグを立てて一度外す
				logger.trace("hasSaddle: " + Objects.nonNull(abHorse.getInventory().getSaddle()));
				if(Objects.nonNull(abHorse.getInventory().getSaddle())){
					logger.trace("this entity has Saddle.");
					hasSaddle = true;
					abHorse.getInventory().setSaddle(null);
				}
			}
			for(ItemStack storageItem : horse.getInventory().getStorageContents()) {
				if(storageItem != null) {
					logger.debug("StrageItem is not null.Item drop!");
					entity.getWorld().dropItem(dropItemLocation, storageItem);
				}
			}
			logger.debug("HorseInventory Clear.");
			horse.getInventory().clear();

			// サドルがある場合は、馬につけなおす
			logger.trace("hasSaddle: " + hasSaddle);
			if(hasSaddle){
				logger.trace("setSaddle.");
				abHorse.getInventory().setSaddle(new ItemStack(SADDLE));
			}
		}

//		net.minecraft.world.entity.Entity nmsEntity = ((CraftEntity) entity).getHandle();
//		CompoundTag tag = new CompoundTag();
//		nmsEntity.saveAsPassenger(tag);
		RtagEntity entityTag = new RtagEntity(entity);
		entityTag.update();
		event.setCancelled(true);
//		logger.trace("nmsEntity:" + nmsEntity);
//		logger.trace("tag:" + tag);

		ItemStack item = new ItemStack(this.ballManager.getBallData(entity.getType()).getFilledBallMaterial(), 1);
//		net.minecraft.world.item.ItemStack itemCopy = CraftItemStack.asNMSCopy(item);
//		CompoundTag nbttag = new CompoundTag();
		ItemStack itemCopy = item.clone();
		RtagItem itemTag = new RtagItem(itemCopy);
		itemTag.update();
		logger.trace("item:" + item);
//		logger.trace("itemCopy:" + itemCopy);
//		logger.trace("nbttag:" + nbttag);

//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		try {
////			NbtIo.writeCompressed(tag, baos);
//			logger.debug("Write NBT");
//		} catch (IOException e) {
//			logger.getLogger().throwing("EventListener", "onPlayerInteractEntityEvent", e);
//		}

//		byte[] byteNbt = baos.toByteArray();


		//空気対策
		Entity ent = entityTag.getEntity();
		logger.trace("ent: " + ent);
//		logger.trace("tag.getString(\"id\") == null:" + (tag.getString("id") == null));
//		logger.trace("tag.getString(\"id\").equals(\"\"):" + tag.getString("id").equals(""));
//		if(tag.getString("id") == null || tag.getString("id").equals("")) {
		if(Objects.isNull(ent) || ent.getType().equals("")){
			logger.debug("tag is empty");
			return;
		}

		itemTag.set(entityTag.get(), BallData.ENTITYBALL_ISC_KEY);
		itemTag.set(entity.getType().toString(), BallData.ENTITYBALL_CONTENT_KEY);
//		nbttag.putByteArray(BallData.ENTITYBALL_NBT_KEY, byteNbt);
//		nbttag.putString(BallData.ENTITYBALL_CONTENT_KEY, entity.getType().toString());

		/*
		 * 出し入れ同時対策1
		 */
		long time = System.currentTimeMillis();
		itemTag.set(time, BallData.ENTITYBALL_TIMESTAMP_KEY);
//		nbttag.putLong(BallData.ENTITYBALL_TIMESTAMP_KEY, time);
		entity.getPersistentDataContainer().set(new NamespacedKey(this.plugin, BallData.ENTITYBALL_TIMESTAMP_KEY), PersistentDataType.LONG, time);

//		itemCopy.setTag(nbttag);
//		ItemStack entityBall = CraftItemStack.asBukkitCopy(itemCopy);
		itemTag.load();
		ItemStack entityBall = itemCopy;
		logger.trace("entityBall:" + entityBall);

		ItemMeta itemMeta2 = entityBall.getItemMeta();
		itemMeta2.setDisplayName(entity.getCustomName() == null ? this.ballManager.getBallData(entity.getType()).getDisplayName() : entity.getCustomName());
		logger.trace("itemMeta2:" + itemMeta2);

		List<String> lore = new LoreWriterFactory().newLoreWriter(entity.getType()).generateLore(entity);
		logger.trace("lore:" + lore);

		itemMeta2.setLore(lore);
		entityBall.setItemMeta(itemMeta2);

		Player player = event.getPlayer();
		logger.trace("entityEmptyBall.getAmount() == 1:" + (entityEmptyBall.getAmount() == 1));
		if(entityEmptyBall.getAmount() == 1) {
			logger.debug("entityEmptyBall count is 1");
			logger.trace("isMainHand:" + isMainHand);
			if(isMainHand) {
				logger.debug("isMainHand:True");
				player.getInventory().setItem(player.getInventory().getHeldItemSlot(), entityBall);
			}else {
				logger.debug("isMainHand:False");
				player.getInventory().remove(entityEmptyBall);
				player.getInventory().addItem(entityBall);
			}
		}else {
			logger.debug("entityEmptyBall count is not 1");
			entityEmptyBall.setAmount(entityEmptyBall.getAmount() - 1);
			 Map<Integer, ItemStack> left = player.getInventory().addItem(entityBall);
			if(!left.isEmpty()) {
				player.getWorld().dropItem(player.getLocation(), left.get(0));
				player.sendMessage(ChatColor.GREEN + "[PetBall] " + ChatColor.RED +":: 空きスロット不足:: 捕獲済みPetBallを地面に捨てました" );
			}
		}

		entity.remove();
		logger.debug("onPlayerInteractEntityEvent:End");
	}

	/**
	 * PetBallを動かしていいブロックかの判定。
	 * 主に右クリックしたときにインベントリや、動作があるブロックかをここでチェックする。
	 * @param block 対象のブロック
	 * @return チェック結果
	 * */
	private boolean isTouchable(Block block) {

		logger.trace("isTouchable:Start");
		var type = block.getType();
		logger.trace("type:" + type);
		logger.trace("TYPES.contains(type):" + TYPES.contains(type));
		if(TYPES.contains(type)){
			logger.debug("type is IRON series");
			// 鉄シリーズは問答無用でfalse
			return false;
		} else {
			var state = block.getState();
			logger.trace("state:" + state);
			if(state instanceof Sign sign){
				// 看板は編集可・不可の状態が変化するので、動的に取得する
				logger.debug("type is Sign series");
				logger.trace("!sign.isWaxed():" + !sign.isWaxed());
				return !sign.isWaxed();
			} else {
				var blockData = block.getBlockData();
				var material = blockData.getMaterial();
				logger.trace("stateClass:" + state.getClass());
				logger.trace("checkMaterial.blockData:"+ blockData + ".material:" + material + "isTouchable:End");
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
