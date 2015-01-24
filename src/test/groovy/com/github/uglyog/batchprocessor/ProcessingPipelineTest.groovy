package com.github.uglyog.batchprocessor

import com.github.uglyog.batchprocessor.commands.Command
import org.junit.Before
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.spy
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

class ProcessingPipelineTest {

    def args
    def options = [arguments: { args }]
    ProcessingPipeline pipeline

    @Before
    void setup() {
        pipeline = spy(new ProcessingPipeline(options))
    }

    @Test
    void 'validate options returns true if there are less than 2 options'() {
        args = ['one']
        assert ProcessingPipeline.optionsInvalid(null)
        assert ProcessingPipeline.optionsInvalid(options)
    }

    @Test
    void 'validate options returns false if there are 2 options'() {
        args = ['1', '2']
        assert !ProcessingPipeline.optionsInvalid(options)
    }

    @Test
    void 'validate options returns false if there are more than 3'() {
        args = ['1', '2', '3']
        assert !ProcessingPipeline.optionsInvalid(options)
    }

    @Test
    void 'execute pipeline sets up a processing pipeline and executes each one passing the result along'() {
        args = ['a', 'b']
        def command1 = mock(Command)
        def command2 = mock(Command)
        pipeline.pipeline = [command1, command2]

        def secondArgs = 'pass this to second command'
        when(command1.execute(args)).thenReturn(secondArgs)

        pipeline.executePipeline()

        verify(command1).execute(args)
        verify(command2).execute(secondArgs)
    }

}
