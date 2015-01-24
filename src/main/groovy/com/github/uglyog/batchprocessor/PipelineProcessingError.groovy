package com.github.uglyog.batchprocessor

class PipelineProcessingError extends RuntimeException {

    PipelineProcessingError() {
    }

    PipelineProcessingError(String message) {
        super(message)
    }

    PipelineProcessingError(String message, Throwable cause) {
        super(message, cause)
    }

    PipelineProcessingError(Throwable cause) {
        super(cause)
    }

}
