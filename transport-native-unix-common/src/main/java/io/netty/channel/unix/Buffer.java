/*
 * Copyright 2018 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.channel.unix;

import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.UnstableApi;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@UnstableApi
public final class Buffer {

    private Buffer() { }

    /**
     * Free the direct {@link ByteBuffer}.
     */
    public static void free(ByteBuffer buffer) {
        PlatformDependent.freeDirectBuffer(buffer);
    }

    /**
     * Returns a new {@link ByteBuffer} which has the same {@link ByteOrder} as the native order of the machine.
     */
    public static ByteBuffer allocateDirectWithNativeOrder(int capacity) {
        return ByteBuffer.allocateDirect(capacity).order(
                PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
    }

    /**
     * Returns the memory address of the given direct {@link ByteBuffer}.
     */
    public static MemoryAddress memoryAddress(ByteBuffer buffer) {
        assert buffer.isDirect();
        if (PlatformDependent.hasUnsafe()) {
            return PlatformDependent.directBufferAddress(buffer);
        }
        return memoryAddress0(buffer);
    }

    /**
     * Returns the size of a pointer.
     */
    public static int addressSize() {
        if (PlatformDependent.hasUnsafe()) {
            return PlatformDependent.addressSize();
        }
        return addressSize0();
    }

    private static native int addressSize0();
    private static native MemoryAddress memoryAddress0(ByteBuffer buffer);
}
