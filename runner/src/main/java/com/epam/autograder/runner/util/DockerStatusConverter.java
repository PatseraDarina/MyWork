package com.epam.autograder.runner.util;

import com.epam.autograder.runner.entity.SandboxStatus;
import com.github.dockerjava.api.command.InspectContainerResponse;
import org.apache.commons.lang.StringUtils;

public class DockerStatusConverter {
    public static SandboxStatus getSandboxStatus(InspectContainerResponse.ContainerState state) {
        String dockerStatus = StringUtils.trim(state.getStatus());
        if (dockerStatus.equals("running")) {
            return SandboxStatus.RUNNING;
        }
        if (dockerStatus.equals("exited")) {
            int exitCode = state.getExitCode();
            if (exitCode == 0) {
                return SandboxStatus.COMPLETE;
            } else {
                return SandboxStatus.FAILED;
            }
        }
        return SandboxStatus.FAILED;

    }
}
