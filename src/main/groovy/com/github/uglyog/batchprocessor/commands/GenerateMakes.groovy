package com.github.uglyog.batchprocessor.commands

import com.google.common.net.UrlEscapers
import groovy.xml.MarkupBuilder

class GenerateMakes implements Command {
    @Override
    def execute(def pipelineContext) {
        pipelineContext.files['makes'] = []
        pipelineContext.data.makes.each { make, works ->
            def filename = make + '.html'
            pipelineContext.results[filename] = [
                title: make,
                navigation: generateNavigation(pipelineContext, make),
                thumbnails: generateThumbnails(works)
            ]
            pipelineContext.files.makes << filename
        }

        return pipelineContext
    }

    def generateNavigation(context, make) {
        def writer = new StringWriter()
        def html = new MarkupBuilder(new PrintWriter(writer))
        def escaper = UrlEscapers.urlFragmentEscaper()
        html.a(href: escaper.escape('index.html'), 'index')
        context.files.models[make].each { file ->
            html.a(href: escaper.escape(file), file.replaceAll('\\.html', ''))
        }
        writer.toString()
    }

    def generateThumbnails(works) {
        def writer = new StringWriter()
        def html = new MarkupBuilder(new PrintWriter(writer))
        html.div('class': 'thumbnails') {
            works.collect{ it.value }.flatten().take(10).each { work ->
                img(src: work.urls.url.find{ it.@type == 'small' })
            }
        }
        writer.toString()
    }
}
