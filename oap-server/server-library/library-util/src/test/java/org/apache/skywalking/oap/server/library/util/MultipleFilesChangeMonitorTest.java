/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.oap.server.library.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.awaitility.Duration;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.awaitility.Awaitility.await;

public class MultipleFilesChangeMonitorTest {
    private static final String FILE_NAME = "FileChangeMonitorTest.tmp";

    @Test
    public void test() throws IOException {
        StringBuilder content = new StringBuilder();
        MultipleFilesChangeMonitor monitor = new MultipleFilesChangeMonitor(
            1, readableContents -> {
                Assert.assertEquals(2, readableContents.size());
                Assert.assertNull(readableContents.get(1));
            content.delete(0, content.length());
            content.append(new String(readableContents.get(0), 0, readableContents.get(0).length,
                                      StandardCharsets.UTF_8
            ));
        }, FILE_NAME, "XXXX_NOT_EXIST.SW");

        monitor.start();

        final AtomicReference<File> file = new AtomicReference<>(new File(FILE_NAME));
        final AtomicReference<BufferedOutputStream> bos =
            new AtomicReference<>(new BufferedOutputStream(new FileOutputStream(file.get())));
        bos.get().write("test context".getBytes(StandardCharsets.UTF_8));
        bos.get().flush();
        bos.get().close();

        AtomicBoolean notified = new AtomicBoolean();
        AtomicBoolean notified2 = new AtomicBoolean();
        await().atMost(Duration.TWO_MINUTES)
               .pollInterval(Duration.TWO_SECONDS)
               .untilAsserted(() -> {
                   if ("test context".equals(content.toString())) {
                       file.set(new File(FILE_NAME));
                       bos.set(new BufferedOutputStream(new FileOutputStream(file.get())));
                       bos.get().write("test context again".getBytes(StandardCharsets.UTF_8));
                       bos.get().flush();
                       bos.get().close();
                       notified.set(true);
                   } else if ("test context again".equals(content.toString())) {
                       notified2.set(true);
                   }
                   Thread.sleep(500);
                   Assert.assertTrue(notified.get());
                   Assert.assertTrue(notified2.get());
               });

    }

    @BeforeClass
    @AfterClass
    public static void cleanup() {
        File file = new File(FILE_NAME);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }
}
