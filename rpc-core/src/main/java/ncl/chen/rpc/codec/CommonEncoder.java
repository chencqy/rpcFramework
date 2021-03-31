package ncl.chen.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import ncl.chen.rpc.entity.RpcRequest;
import ncl.chen.rpc.enumeration.PackageType;
import ncl.chen.rpc.serializer.CommonSerializer;

/**
 * Encoder
 * The protocol
 * +---------------+---------------+-----------------+-------------+
 * |  Magic Number |  Package Type | Serializer Type | Data Length |
 * |    4 bytes    |    4 bytes    |     4 bytes     |   4 bytes   |
 * +---------------+---------------+-----------------+-------------+
 * |                          Data Bytes                           |
 * |                   Length: ${Data Length}                      |
 * +---------------------------------------------------------------+
 * @author: Qiuyu
 */
public class CommonEncoder extends MessageToByteEncoder {

    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    private final CommonSerializer serializer;

    public CommonEncoder(CommonSerializer serializer) {
        this.serializer = serializer;
    }


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.writeInt(MAGIC_NUMBER);
        // Request or Response(Package Type)
        if (msg instanceof RpcRequest) {
            out.writeInt(PackageType.REQUEST_PACK.getCode());
        } else {
            out.writeInt(PackageType.RESPONSE_PACK.getCode());
        }
        // Serializer Type
        out.writeInt(serializer.getCode());
        byte[] bytes = serializer.serialize(msg);
        // Data length
        out.writeInt(bytes.length);
        // Data
        out.writeBytes(bytes);

    }
}
