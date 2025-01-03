package top.bearcabbage.annoyingeffects.utils;

import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NoSavePlayerData<T> {

    public final T initialValue;
    protected final Map<UUID, T> map = new HashMap<>();

    public NoSavePlayerData(T initialValue){
        this.initialValue = initialValue;
    }

    private void check(UUID player){
        if(!map.containsKey(player)) map.put(player, initialValue);
    }

    public T get(UUID player){
        check(player);
        return map.get(player);
    }

    public T get(PlayerEntity player){return this.get(player.getUuid());}

    public void set(UUID player, T value){
        check(player);
        map.put(player, value);
    }

    public void set(PlayerEntity player, T value){this.set(player.getUuid(), value);}

    public void clear(){
        map.clear();
    }

    public void cleanTrash(){
        for(UUID player: map.keySet()){
            if(map.get(player) == initialValue){
                map.remove(player);
            }
        }
    }

}
