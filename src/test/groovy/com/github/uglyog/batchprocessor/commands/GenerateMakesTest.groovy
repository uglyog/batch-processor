package com.github.uglyog.batchprocessor.commands

import org.junit.Before
import org.junit.Test

public class GenerateMakesTest {

    GenerateMakes command
    def context

    @Before
    void setup() {
        command = new GenerateMakes()
        context = [
            data: [
                makes: [
                    'model1': [:],
                    'model2': [:]
                ]
            ],
            files: [:],
            results: [:]
        ]
    }

    @Test
    void 'adds a file to the list of makes'() {
        def result = command.execute(context)
        assert result.files.makes == ['model1.html', 'model2.html']
    }

}
