package com.github.uglyog.batchprocessor.commands

class GenerateMakes implements Command {
    @Override
    def execute(def pipelineContext) {
        pipelineContext.files['makes'] = []
        pipelineContext.data.makes.each { make, works ->
            def filename = make + '.html'
            pipelineContext.results[filename] = [
                title: make,
                navigation: generateNavigation(pipelineContext, make, works),
                thumbnails: generateThumbnails(pipelineContext, make, works)
            ]
            pipelineContext.files.makes << filename
        }

        return pipelineContext
    }

    def generateNavigation(Object context, Object make, Object works) {
        ''
    }

    def generateThumbnails(Object context, Object make, Object works) {
        ''
    }
}
