package net.mehvahdjukaar.supplementaries.client;

import com.google.common.base.Suppliers;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.mehvahdjukaar.supplementaries.Supplementaries;
import net.mehvahdjukaar.supplementaries.common.block.tiles.BookPileBlockTile;
import net.mehvahdjukaar.supplementaries.reg.ModRegistry;
import net.mehvahdjukaar.supplementaries.reg.ModTextures;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.level.block.entity.BannerPattern;

import javax.annotation.Nullable;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Supplier;

import static net.mehvahdjukaar.supplementaries.reg.ClientRegistry.BANNER_SHEET;
import static net.minecraft.client.renderer.Sheets.SHULKER_SHEET;
import static net.minecraft.client.renderer.texture.TextureAtlas.LOCATION_BLOCKS;

public class ModMaterials {

    //materials
    public static final Material CLOCK_HAND = new Material(TextureAtlas.LOCATION_BLOCKS, ModTextures.CLOCK_HAND_TEXTURE);
    public static final Material BLACKBOARD_OUTLINE = new Material(LOCATION_BLOCKS, ModTextures.BLACKBOARD_GRID);
    public static final Material BELLOWS_MATERIAL = new Material(LOCATION_BLOCKS, ModTextures.BELLOWS_TEXTURE);
    public static final Material BUBBLE_BLOCK_MATERIAL = new Material(LOCATION_BLOCKS, ModTextures.BUBBLE_BLOCK_TEXTURE);
    public static final Material BOOK_ENCHANTED_MATERIAL = new Material(SHULKER_SHEET, ModTextures.BOOK_ENCHANTED_TEXTURES);
    public static final Material BOOK_TOME_MATERIAL = new Material(SHULKER_SHEET, ModTextures.BOOK_TOME_TEXTURES);
    public static final Material BOOK_WRITTEN_MATERIAL = new Material(SHULKER_SHEET, ModTextures.BOOK_WRITTEN_TEXTURES);
    public static final Material BOOK_AND_QUILL_MATERIAL = new Material(SHULKER_SHEET, ModTextures.BOOK_AND_QUILL_TEXTURES);
    public static final Material BOOK_TATTERED_MATERIAL = new Material(SHULKER_SHEET, ModTextures.BOOK_ANTIQUE_TEXTURES);

    public static final Supplier<Map<BookPileBlockTile.BookColor, Material>> BOOK_MATERIALS = Suppliers.memoize(() -> {
        var map = new IdentityHashMap<BookPileBlockTile.BookColor, Material>();
        for (var e : ModTextures.BOOK_TEXTURES.entrySet()) {
            map.put(e.getKey(), new Material(SHULKER_SHEET, e.getValue()));
        }
        return map;
    });

    public static final Supplier<Map<WoodType, Material>> SIGN_POSTS_MATERIALS = Suppliers.memoize(() -> {
        var map = new IdentityHashMap<WoodType, Material>();
        ModRegistry.SIGN_POST_ITEMS.forEach((wood, item) -> map
                .put(wood, new Material(LOCATION_BLOCKS, Supplementaries.res("entity/sign_posts/" +
                        Utils.getID(item).getPath()))));
        return map;
    });

    public static final Supplier<Map<BannerPattern, Material>> FLAG_MATERIALS = Suppliers.memoize(() -> {
        var map = new IdentityHashMap<BannerPattern, Material>();
        for (var v : ModTextures.FLAG_TEXTURES.entrySet()) {
            map.put(v.getKey(), new Material(BANNER_SHEET, v.getValue()));
        }
        return map;
    });

    @Nullable
    public static Material getFlagMaterialForPatternItem(BannerPatternItem item) {
        var p = ITEM_TO_PATTERNS.get(item);
        if (p == null) {
            for (var j : Registry.BANNER_PATTERN.getTag(item.getBannerPattern()).get()) {
                ITEM_TO_PATTERNS.put(item, j.value());
                return FLAG_MATERIALS.get().get(j.value());
            }
            return null;
        } else return FLAG_MATERIALS.get().get(p);
    }

    private static final Map<BannerPatternItem, BannerPattern> ITEM_TO_PATTERNS = new IdentityHashMap<>();


}
