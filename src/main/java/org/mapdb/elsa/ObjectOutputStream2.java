package org.mapdb.elsa;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;

/**
 * Created by jan on 3/28/16.
 */
final class ObjectOutputStream2 extends ObjectOutputStream {

    private ElsaSerializerPojo serializerPojo;


    protected ObjectOutputStream2(ElsaSerializerPojo serializerPojo, OutputStream out) throws IOException, SecurityException {
        super(out);
        this.serializerPojo = serializerPojo;
    }

    @Override
    protected void writeClassDescriptor(ObjectStreamClass desc) throws IOException {
        int classId = serializerPojo.classToId(desc.getName());
        ElsaUtil.packInt(this, classId);
        if (classId == -1) {
            //unknown class, write its full name
            this.writeUTF(desc.getName());
            //and notify about unknown class

            serializerPojo.notifyMissingClassInfo(desc.forClass());
        }
    }
}
