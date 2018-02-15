package com.epam.autograder.runner.util;

import com.epam.autograder.runner.entity.SandboxStatus;
import com.github.dockerjava.api.command.InspectContainerResponse;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;

/**
 * DockerStatusConverter
 */
public class DockerStatusConverter {
    /**
     * @param state ContainerState
     * @return SandboxStatus by container status
     */
    public static SandboxStatus getSandboxStatus(InspectContainerResponse.ContainerState state) {
        if (Objects.nonNull(state)) {
            String dockerStatus = StringUtils.trim(state.getStatus());
            if (Objects.equals(dockerStatus, "running")) {
                return SandboxStatus.RUNNING;
            }
            if (Objects.equals(dockerStatus, "exited")) {
                Integer exitCode = state.getExitCode();
                if (Objects.nonNull(exitCode)) {
                    return SandboxStatus.COMPLETE;
                } else {
                    return SandboxStatus.FAILED;
                }
            }
        }
        return SandboxStatus.FAILED;
    }
}
