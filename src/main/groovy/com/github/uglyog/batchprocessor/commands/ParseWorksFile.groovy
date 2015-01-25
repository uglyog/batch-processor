package com.github.uglyog.batchprocessor.commands

import org.apache.commons.lang3.StringUtils

class ParseWorksFile implements Command {

    @Override
    def execute(def pipelineContext) {

        def slurper = new XmlSlurper()
        pipelineContext.data.original = slurper.parse(pipelineContext.inputFile)
        pipelineContext.data.makes = pipelineContext.data.original.children().groupBy { work ->
            def make = work.exif.make.toString()
            if (StringUtils.isBlank(make)) {
                'Unknown'
            } else {
                make
            }
        }
        pipelineContext.data.makes.each { make, worksForThatMake ->
            pipelineContext.data.makes[make] = worksForThatMake.groupBy { work ->
                def model = work.exif.model.toString()
                if (StringUtils.isBlank(model)) {
                    'Unknown'
                } else {
                    model
                }
            }
        }

        return pipelineContext
    }
}
