package com.github.uglyog.batchprocessor.commands

import com.github.uglyog.batchprocessor.PipelineProcessingError

class ValidateTargetDirectory implements Command {

    @Override
    def execute(def pipelineContext) {
        File targetDir = new File(pipelineContext.args[1])

        if (!targetDir.exists()) {
            throw new PipelineProcessingError("Target directory $targetDir does not exist")
        }

        if (!targetDir.isDirectory()) {
            throw new PipelineProcessingError("Target directory $targetDir is not a directory")
        }

        if (!targetDir.canWrite()) {
            throw new PipelineProcessingError("Target directory $targetDir is not writeable")
        }

        pipelineContext.targetDirectory = targetDir

        return pipelineContext
    }
}
