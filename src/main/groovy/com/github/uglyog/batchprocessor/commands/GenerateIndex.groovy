package com.github.uglyog.batchprocessor.commands

import groovy.xml.MarkupBuilder

class GenerateIndex implements Command {
    @Override
    def execute(def pipelineContext) {

        pipelineContext.results['index.html'] = [
            title: 'index',
            navigation: generateNavigation(pipelineContext),
            thumbnails: generateThumbnails(pipelineContext)
        ]

        return pipelineContext
    }

    def generateThumbnails(def context) {
        def writer = new StringWriter()
        def html = new MarkupBuilder(new PrintWriter(writer))
        html.div('class': 'thumbnails') {
            context.data.original.children().take(10).each { work ->
                img(src: work.urls.url.find{ it.@type == 'small' })
            }
        }
        writer.toString()
    }

    def generateNavigation(def context) {
        ''
    }
}
