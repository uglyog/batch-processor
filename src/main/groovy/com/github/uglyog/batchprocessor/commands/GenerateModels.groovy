package com.github.uglyog.batchprocessor.commands

class GenerateModels implements Command {
    @Override
    def execute(def pipelineContext) {
        pipelineContext.files['models'] = [:]
        pipelineContext.data.makes.each { make, works ->
            pipelineContext.files.models[make] = []
            works.each { model, worksForModel ->
                def filename = make + '-' + model + '.html'
                pipelineContext.results[filename] = [
                    title: make + ' - ' + model,
                    navigation: generateNavigation(pipelineContext, make, model, works),
                    thumbnails: generateThumbnails(pipelineContext, make, model, works)
                ]
                pipelineContext.files.models[make] << filename
            }
        }

        return pipelineContext
    }

    def generateNavigation(def context, def make, def model, def works) {
        ''
    }

    def generateThumbnails(def context, def make, def model, def works) {
        ''
    }
}
