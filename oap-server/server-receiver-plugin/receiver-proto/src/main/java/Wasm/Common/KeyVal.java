/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
// automatically generated by the FlatBuffers compiler, do not modify

package Wasm.Common;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class KeyVal extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_1_12_0(); }
  public static KeyVal getRootAsKeyVal(ByteBuffer _bb) { return getRootAsKeyVal(_bb, new KeyVal()); }
  public static KeyVal getRootAsKeyVal(ByteBuffer _bb, KeyVal obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public KeyVal __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String key() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer keyAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer keyInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public String value() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer valueAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer valueInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }

  public static int createKeyVal(FlatBufferBuilder builder,
      int keyOffset,
      int valueOffset) {
    builder.startTable(2);
    KeyVal.addValue(builder, valueOffset);
    KeyVal.addKey(builder, keyOffset);
    return KeyVal.endKeyVal(builder);
  }

  public static void startKeyVal(FlatBufferBuilder builder) { builder.startTable(2); }
  public static void addKey(FlatBufferBuilder builder, int keyOffset) { builder.addOffset(0, keyOffset, 0); }
  public static void addValue(FlatBufferBuilder builder, int valueOffset) { builder.addOffset(1, valueOffset, 0); }
  public static int endKeyVal(FlatBufferBuilder builder) {
    int o = builder.endTable();
    builder.required(o, 4);  // key
    return o;
  }

  @Override
  protected int keysCompare(Integer o1, Integer o2, ByteBuffer _bb) { return compareStrings(__offset(4, o1, _bb), __offset(4, o2, _bb), _bb); }

  public static KeyVal __lookup_by_key(KeyVal obj, int vectorLocation, String key, ByteBuffer bb) {
    byte[] byteKey = key.getBytes(java.nio.charset.StandardCharsets.UTF_8);
    int span = bb.getInt(vectorLocation - 4);
    int start = 0;
    while (span != 0) {
      int middle = span / 2;
      int tableOffset = __indirect(vectorLocation + 4 * (start + middle), bb);
      int comp = compareStrings(__offset(4, bb.capacity() - tableOffset, bb), byteKey, bb);
      if (comp > 0) {
        span = middle;
      } else if (comp < 0) {
        middle++;
        start += middle;
        span -= middle;
      } else {
        return (obj == null ? new KeyVal() : obj).__assign(tableOffset, bb);
      }
    }
    return null;
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public KeyVal get(int j) { return get(new KeyVal(), j); }
    public KeyVal get(KeyVal obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
    public KeyVal getByKey(String key) {  return __lookup_by_key(null, __vector(), key, bb); }
    public KeyVal getByKey(KeyVal obj, String key) {  return __lookup_by_key(obj, __vector(), key, bb); }
  }
}

