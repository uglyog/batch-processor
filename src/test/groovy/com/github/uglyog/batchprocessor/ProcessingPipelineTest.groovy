package com.github.uglyog.batchprocessor

import org.junit.Before
import org.junit.Test

class ProcessingPipelineTest {

    def args
    def options = [arguments: { args }]

    @Before
    void setup() {

    }

    @Test
    void 'validate options returns true if there are less than 2 options'() {
        args = ['one']
        assert ProcessingPipeline.optionsInvalid(null)
        assert ProcessingPipeline.optionsInvalid(options)
    }

    @Test
    void 'validate options returns false if there are 2 or more options'() {
        args = ['1', '2']
        assert !ProcessingPipeline.optionsInvalid(options)
        args = ['1', '2', '3']
        assert !ProcessingPipeline.optionsInvalid(options)
    }

}
