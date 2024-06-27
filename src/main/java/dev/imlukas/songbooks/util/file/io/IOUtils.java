package dev.imlukas.songbooks.util.file.io;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class IOUtils {

    public static final int BUFFER_SIZE = 1024;

    private IOUtils() {

    }

    public static void traverseAndLoad(File folder, Consumer<File> consumer) {
        if (folder == null || !folder.exists() || !folder.isDirectory()) {
            return;
        }

        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                traverseAndLoad(file, consumer);
            } else {
                consumer.accept(file);
            }
        }
    }

    public static boolean createFile(File file) {
        if (file == null) {
            return false;
        }

        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                return file.createNewFile();
            } catch (IOException ignored) {
                // Ignored, return false
            }
        }

        return false;
    }

    public static void copyBuiltInResources(JavaPlugin platform,
                                            File jarFile, String... exclusions) { // the jarFile must be fetched through JavaPlugin#getFile, as it's a protected method
        // Copy all resources from the jar to the data folder, except plugin.yml and code
        // Open the jar file as a zip

        File dataFolder = platform.getDataFolder();

        try (JarFile jar = new JarFile(jarFile)) {
            // Get the entries in the jar file
            Enumeration<JarEntry> entries = jar.entries();

            // Iterate over the entries
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();

                //

                // Make sure to not copy classes (including dependencies), we only want resources
                if (name.endsWith(".class")) {
                    continue;
                }

                // skip meta-inf
                if (name.startsWith("META-INF/")) {
                    continue;
                }

                // Skip plugin.yml
                for (String exclusion : exclusions) {
                    if (name.equals(exclusion)) {
                        continue;
                    }
                }

                // If the entry is a directory, and the directory doesn't contain code, create it
                if (name.endsWith("/")) {
                    if (isCodeDirectory(name, jar.entries())) {
                        continue;
                    }

                    new File(dataFolder, name).mkdirs();
                    continue;
                }

                // The entry is a file, so copy it
                try (InputStream in = jar.getInputStream(entry)) {
                    File file = new File(dataFolder, name);

                    if (file.exists()) {
                        continue;
                    }

                    Files.copy(in, file.toPath());
                } catch (IOException ignored) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isCodeDirectory(String name, Enumeration<JarEntry> dir) {
        while (dir.hasMoreElements()) {
            JarEntry entry = dir.nextElement();
            String entryName = entry.getName();

            if (entryName.startsWith(name) && entryName.endsWith(".class")) {
                return true;
            }
        }

        return false;
    }

    public static void copy(InputStream in, File file) throws IOException {
        IOUtils.createFile(file);

        try (OutputStream out = new FileOutputStream(file)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }
}
