package logisticspipes.network.abstractpackets;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import logisticspipes.network.NetworkConstants;
import lombok.Getter;

public abstract class ModernPacket {

	protected String channel;

	public abstract void readData(DataInputStream data) throws IOException;

	public abstract void processPacket(EntityPlayer player);

	public abstract void writeData(DataOutputStream data) throws IOException;

	public Packet getPacket() {

		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(bytes);
		try {
			data.writeByte(getId());
			writeData(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = channel;
		packet.data = bytes.toByteArray();
		packet.length = packet.data.length;
		return packet;
	}

	@Getter
	private final int id;

	public ModernPacket(int id) {
		this.channel = NetworkConstants.LOGISTICS_PIPES_CHANNEL_NAME;
		this.id = id;
	}

	public abstract ModernPacket template();

}