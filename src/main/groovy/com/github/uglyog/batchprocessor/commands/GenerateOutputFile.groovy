package com.github.uglyog.batchprocessor.commands

class GenerateOutputFile implements Command {
    @Override
    def execute(def context) {

        def outputFile  = new File(context.targetDirectory, context.inputFile.name.replace('.xml', '.html'))
        def template = getClass().getResourceAsStream('/output-template.html').text

        template = template.replaceAll('\\{\\{ TITLE GOES HERE \\}\\}', context.results.title)
            .replaceAll('\\{\\{ NAVIGATION GOES HERE \\}\\}', context.results.navigation)
            .replaceAll('\\{\\{ THUMBNAIL IMAGES GO HERE \\}\\}', context.results.thumbnails)

        outputFile.write(template)

        return context
    }
}
