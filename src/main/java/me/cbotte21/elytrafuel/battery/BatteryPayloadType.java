package me.cbotte21.elytrafuel.battery;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class BatteryPayloadType implements PersistentDataType<Integer, Integer> {
    @Override
    public @NotNull Class<Integer> getPrimitiveType() {
        return Integer.class;
    }

    @Override
    public @NotNull Class<Integer> getComplexType() {
        return Integer.class;
    }

    @Override
    public @NotNull Integer toPrimitive(@NotNull Integer complex, @NotNull PersistentDataAdapterContext context) {
        return complex;
    }

    @Override
    public @NotNull Integer fromPrimitive(@NotNull Integer primitive, @NotNull PersistentDataAdapterContext context) {
        return primitive;
    }
}
