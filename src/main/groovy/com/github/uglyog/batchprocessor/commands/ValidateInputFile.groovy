package com.github.uglyog.batchprocessor.commands

import com.github.uglyog.batchprocessor.PipelineProcessingError

class ValidateInputFile implements Command {

    @Override
    def execute(def pipelineContext) {
        File inputFile = new File(pipelineContext.args.first())

        if (!inputFile.exists()) {
            throw new PipelineProcessingError("Input file $inputFile does not exist")
        }

        if (!inputFile.isFile()) {
            throw new PipelineProcessingError("Input file $inputFile is not a file")
        }

        if (!inputFile.canRead()) {
            throw new PipelineProcessingError("Input file $inputFile is not readable")
        }

        pipelineContext.inputFile = inputFile

        return pipelineContext
    }
}
