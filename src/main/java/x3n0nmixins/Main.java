package x3n0nmixins;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.MOD_ID)
public class Main {
    public static final String MOD_ID = "x3n0nmixins";

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        System.out.println("LOADED X3n0n mixins!");
    }
}
