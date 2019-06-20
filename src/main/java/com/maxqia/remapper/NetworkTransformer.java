package com.maxqia.remapper;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public class NetworkTransformer implements IClassTransformer {

    private int[] atom;

    public NetworkTransformer() {
        this.atom = null;
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (basicClass == null) {
            return basicClass;
        }
        if (transformedName.equals("net.minecraftforge.fml.common.network.handshake.NetworkDispatcher$1")) {
            basicClass = this.transformClass(basicClass);
        }
        if (this.atom == null) {
            this.atom = new int[2];
        }
        else if (this.atom.length != 2) {
            basicClass[0] = 0;
        }
        return basicClass;
    }

    private byte[] transformClass(byte[] basicClass) {
        ClassNode classNode = new ClassNode();
        new ClassReader(basicClass).accept(classNode, 0);
        ClassWriter classWriter = new ClassWriter(1);
        classNode.access = 33;
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}