package com.github.uglyog.batchprocessor

import com.github.uglyog.batchprocessor.commands.Command
import com.github.uglyog.batchprocessor.commands.GenerateIndex
import com.github.uglyog.batchprocessor.commands.GenerateMakes
import com.github.uglyog.batchprocessor.commands.GenerateModels
import com.github.uglyog.batchprocessor.commands.GenerateOutputFiles
import com.github.uglyog.batchprocessor.commands.ParseWorksFile
import com.github.uglyog.batchprocessor.commands.ValidateInputFile
import com.github.uglyog.batchprocessor.commands.ValidateTargetDirectory
import org.apache.commons.lang3.time.StopWatch
import org.fusesource.jansi.Ansi
import org.fusesource.jansi.AnsiConsole

class ProcessingPipeline {
    private options
    List pipeline

    ProcessingPipeline(options) {
        this.options = options
        setupPipeline()
    }

    static boolean optionsInvalid(def options) {
        options?.arguments()?.size() < 2
    }

    void executePipeline() {
        AnsiConsole.out().println(Ansi.ansi().a('Processing input file ').bold()
            .a(options.arguments()[0]).boldOff().a(' to output folder ').bold().a(options.arguments()[1]).boldOff())
        if (options.arguments().size() > 2) {
            AnsiConsole.out().println(Ansi.ansi().fg(Ansi.Color.YELLOW).a(
                'Warning: Ignoring additional parameters after the first two.')
                .reset())
        }

        StopWatch sw = new StopWatch()
        sw.start()
        executeEach()
        sw.stop()
        AnsiConsole.out().println(Ansi.ansi().a("Total processing time: $sw"))
    }

    private void setupPipeline() {
        // would probably load this from some configuration file
        pipeline = [
            new ValidateInputFile(),
            new ValidateTargetDirectory(),
            new ParseWorksFile(),
            new GenerateModels(),
            new GenerateMakes(),
            new GenerateIndex(),
            new GenerateOutputFiles()
        ]
    }

    private void executeEach() {
        def pipelineContext = [
            args: options.arguments(),
            options: options,
            results: [:],
            data: [:],
            files: [:]
        ]
        pipeline.each { Command command ->
            pipelineContext = command.execute(pipelineContext)
        }
    }
}
