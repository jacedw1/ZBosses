package me.zelevon.zbosses.utils;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityTypes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//NOT my class, simply using resources from the internet for entity registry

@SuppressWarnings("unused")
public class NMSUtils {

    public static final int WEAPON_SLOT = 0;
    public static final int HELMET_SLOT = 4;
    public static final int CHEST_SLOT = 3;
    public static final int LEG_SLOT = 2;
    public static final int BOOT_SLOT = 1;
    public static void registerEntity(String name, int id, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass){
        try {

            List<Map<?, ?>> dataMap = new ArrayList<>();
            for (Field f : EntityTypes.class.getDeclaredFields()){
                if (f.getType().getSimpleName().equals(Map.class.getSimpleName())){
                    f.setAccessible(true);
                    dataMap.add((Map<?, ?>) f.get(null));
                }
            }

            if (dataMap.get(2).containsKey(id)){
                dataMap.get(0).remove(name);
                dataMap.get(2).remove(id);
            }

            Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
            method.setAccessible(true);
            method.invoke(null, customClass, name, id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
