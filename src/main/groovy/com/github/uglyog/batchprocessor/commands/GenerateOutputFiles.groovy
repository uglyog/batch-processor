package com.github.uglyog.batchprocessor.commands

import org.fusesource.jansi.Ansi
import org.fusesource.jansi.AnsiConsole

class GenerateOutputFiles implements Command {
    @Override
    def execute(def context) {

        def template = getClass().getResourceAsStream('/output-template.html').text

        context.results.each { filename, data ->
            AnsiConsole.out().println(Ansi.ansi().a('    --> Generating ').bold()
                .a(filename).boldOff())
            def outputFile  = new File(context.targetDirectory, filename)
            def fileContents = template.replaceAll('\\{\\{ TITLE GOES HERE \\}\\}', data.title)
                .replaceAll('\\{\\{ NAVIGATION GOES HERE \\}\\}', data.navigation)
                .replaceAll('\\{\\{ THUMBNAIL IMAGES GO HERE \\}\\}', data.thumbnails)

            outputFile.write(fileContents)
        }

        return context
    }
}
