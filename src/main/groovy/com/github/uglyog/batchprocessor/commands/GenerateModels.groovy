package com.github.uglyog.batchprocessor.commands

import com.google.common.net.UrlEscapers
import groovy.xml.MarkupBuilder

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
                    navigation: generateNavigation(make),
                    thumbnails: generateThumbnails(pipelineContext, make, model, worksForModel)
                ]
                pipelineContext.files.models[make] << filename
            }
        }

        return pipelineContext
    }

    def generateNavigation(make) {
        def writer = new StringWriter()
        def html = new MarkupBuilder(new PrintWriter(writer))
        def escaper = UrlEscapers.urlFragmentEscaper()
        html.a(href: escaper.escape('index.html'), 'index')
        html.a(href: escaper.escape(make + '.html'), make)
        writer.toString()
    }

    def generateThumbnails(def context, def make, def model, def works) {
        def writer = new StringWriter()
        def html = new MarkupBuilder(new PrintWriter(writer))
        html.div('class': 'thumbnails') {
            works.each { work ->
                img(src: work.urls.url.find{ it.@type == 'small' })
            }
        }
        writer.toString()
    }
}
