package com.dispassionproject.lunchtime;

import com.jcabi.manifests.Manifests;
import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;
import java.lang.management.ManagementFactory;

public class LunchtimeBanner implements Banner {

    private static final String BANNER = "\n" +
            "██╗     ██╗   ██╗███╗   ██╗ ██████╗██╗  ██╗████████╗██╗███╗   ███╗███████╗██╗\n" +
            "██║     ██║   ██║████╗  ██║██╔════╝██║  ██║╚══██╔══╝██║████╗ ████║██╔════╝██║\n" +
            "██║     ██║   ██║██╔██╗ ██║██║     ███████║   ██║   ██║██╔████╔██║█████╗  ██║\n" +
            "██║     ██║   ██║██║╚██╗██║██║     ██╔══██║   ██║   ██║██║╚██╔╝██║██╔══╝  ╚═╝\n" +
            "███████╗╚██████╔╝██║ ╚████║╚██████╗██║  ██║   ██║   ██║██║ ╚═╝ ██║███████╗██╗\n" +
            "╚══════╝ ╚═════╝ ╚═╝  ╚═══╝ ╚═════╝╚═╝  ╚═╝   ╚═╝   ╚═╝╚═╝     ╚═╝╚══════╝╚═╝\n" +
            "                            %s, v%s, commit %s                               \n" +
            "                            build %s (%s)                                    \n";

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.printf(BANNER,
                getManifestValue("Project-Name"),
                getManifestValue("Version"),
                getManifestValue("Commit"),
                getManifestValue("Build"),
                getManifestValue("Build-Date")
        );
        out.append("\nArguments: ");
        ManagementFactory.getRuntimeMXBean().getInputArguments().forEach(s -> out.append(s).append(" "));
        out.append("\n\n");
    }

    private static String getManifestValue(final String name) {
        try {
            return Manifests.read(name);
        } catch (Exception e) {
            return "undefined";
        }
    }

}
