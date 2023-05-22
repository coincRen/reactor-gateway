/*
 * Copyright 2023 zoukang, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zk.rgw.common.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUtil {

    private ZipUtil() {
    }

    public static void unzip(String zipFileName, String targetDirName) throws IOException {
        Path targetExtractPath = Paths.get(targetDirName);
        if (Files.notExists(targetExtractPath)) {
            Files.createDirectories(targetExtractPath);
        }
        try (ZipFile zipFile = new ZipFile(zipFileName)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            ZipEntry zipEntry;
            Path curZipEntryExtractPath;
            while (entries.hasMoreElements()) {
                zipEntry = entries.nextElement();
                curZipEntryExtractPath = targetExtractPath.resolve(zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    Files.createDirectories(curZipEntryExtractPath);
                } else {
                    Files.copy(zipFile.getInputStream(zipEntry), curZipEntryExtractPath);
                }
            }
        }
    }

}
