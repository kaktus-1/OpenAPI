package pl.nightdev701.key;

/*

Lukas - 16:10
26.08.2023
https://github.com/NightDev701

© SunLightScorpion 2020 - 2023

*/

import pl.nightdev701.base.BaseKey;

import java.util.UUID;

/**
 * uuid key
 */
public class UniqueValueKey<T extends UUID> implements BaseKey {

    T key;

    /**
     * create key
     */
    private UniqueValueKey(T key) {
        this.key = key;
    }

    /**
     * read value
     */
    public static UniqueValueKey<UUID> getKey(UUID key) {
        return new UniqueValueKey<>(key);
    }

    @Override
    public Object baseValue() {
        return key;
    }

    @Override
    public void save(String path) {
    }

    @Override
    public Object getData(String path) {
        return null;
    }

}
