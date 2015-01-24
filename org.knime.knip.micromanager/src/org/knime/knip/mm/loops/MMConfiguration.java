package org.knime.knip.mm.loops;

import java.io.File;

import org.knime.knip.mm.MMGateway;
import org.scijava.command.Command;
import org.scijava.plugin.Menu;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(menu = {@Menu(label = "Micro-Manager"),
		@Menu(label = "Micro-Manager Configuration")},
	description = "Global Micro-Manager configuration",
	headless = true,
	type = Command.class)
public class MMConfiguration implements Command {

	public MMConfiguration() {
		microManagerDirectory = MMGateway.discoverMicroManager();
	}

	@Parameter(description = "The top-level directory of the Micro-Manager installation", style = "directory")
	private File microManagerDirectory;

	@Parameter(description = "The system configuration, as written by Micro-Manager")
	private File systemConfiguration;

	@Override
	public void run() {
		final MMGateway mm = MMGateway.createInstance(microManagerDirectory);
		if (systemConfiguration != null && systemConfiguration.isFile()) {
			mm.loadSystemConfiguration(systemConfiguration.getAbsolutePath());
		}
		else {
			mm.loadDevice("Camera", "DemoCamera", "DCam");
		}
		mm.initializeAllDevices();
	}
}
