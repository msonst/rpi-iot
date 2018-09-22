package de.sonsts.rpi.iot.communication.common.messaage.payload;

import java.util.HashMap;

public class MappingPayloadDescriptor<K, V> implements PayloadDescriptor
{
    private HashMap<K, V> signalMapping;

    public MappingPayloadDescriptor()
    {
    }

    public MappingPayloadDescriptor(HashMap<K, V> signalMapping)
    {
        this.signalMapping = signalMapping;
    }


    public HashMap<K, V> getSignalMapping()
    {
        return signalMapping;
    }

    public void setSignalMapping(HashMap<K, V> signalMapping)
    {
        this.signalMapping = signalMapping;
    }
}
