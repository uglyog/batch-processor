package com.github.uglyog.batchprocessor

class ProcessingPipeline {
    private options

    ProcessingPipeline(options) {
        this.options = options
    }

    static boolean optionsInvalid(def options) {
        options?.arguments()?.size() < 2
    }

    void executePipeline() {

    }
}
