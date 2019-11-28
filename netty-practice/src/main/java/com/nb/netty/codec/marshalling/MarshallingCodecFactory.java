package com.nb.netty.codec.marshalling;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

public class MarshallingCodecFactory {
    /**
     * 创建 Marshalling 解码器
     * @return
     */
    public static MarshallingDecoder buildMarshallingDecoder() {
        final MarshallerFactory serial = Marshalling.getProvidedMarshallerFactory("serial"); // serial == null 跑不通
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        DefaultUnmarshallerProvider provider = new DefaultUnmarshallerProvider(serial, configuration);
        return new MarshallingDecoder(provider, 1024);
    }

    /**
     * 创建 Marshalling 编码器
     * @return
     */
    public static MarshallingEncoder buildMarshallingEncoder() {
        final MarshallerFactory serial = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(serial, configuration);
        return new MarshallingEncoder(provider);
    }
}
