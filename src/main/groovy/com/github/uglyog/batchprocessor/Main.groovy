package com.github.uglyog.batchprocessor

/**
 * Main script to parse parameters and setup pipeline
 */

import org.fusesource.jansi.Ansi
import org.fusesource.jansi.AnsiConsole

AnsiConsole.out().println(Ansi.ansi().bold().a('Batch Processor').boldOff().a(' Version 0.0.1\n'))

def cli = new CliBuilder(usage:'batchprocessor [options] inputfile targetdirectory')
cli.help('print this message')
def options = cli.parse(args)

if (options.arguments().empty || options.help || ProcessingPipeline.optionsInvalid(options)) {
    cli.usage()
} else {
    try {
        new ProcessingPipeline(options).executePipeline()
    } catch (e) {
        AnsiConsole.out().println(Ansi.ansi().fg(Ansi.Color.RED).a('Failed to process input file - ')
            .a(e.message).reset().a('\n\n'))
        e.printStackTrace()
    }
}
