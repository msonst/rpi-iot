package de.sonsts.rpi.iot.communication.common.messaage.cannel;

public class IotTopic
{
    public static final IotTopic REPLY = new IotTopic("iot/reply");
    public static final IotTopic REQUEST = new IotTopic("iot/request");
    public static final IotTopic CMD = new IotTopic("iot/cmd");
    public static final IotTopic LIVE = new IotTopic("iot.live");

    private String mTopic;

    private IotTopic(String topic)
    {
        mTopic = topic;
    }

    public String getTopic()
    {
        return mTopic;
    }

    public IotTopic combine(String combine)
    {
        return new IotTopic(mTopic + combine);
    }

    @Override
    public String toString()
    {
        return "IotTopic [mTopic=" + mTopic + "]";
    }
}
