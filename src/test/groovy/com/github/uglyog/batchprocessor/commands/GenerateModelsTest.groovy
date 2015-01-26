package com.github.uglyog.batchprocessor.commands

import org.junit.Before
import org.junit.Test

public class GenerateModelsTest {

    GenerateModels command
    def context
    String model1Xml =
        '''<work>
        <id>001</id>
        <urls>
            <url type="small">small.jpg</url>
            <url type="large">large.jpg</url>
        </urls>
        <exif>
            <make>MAKE1</make>
        </exif>
        </work>
        '''
    String model2Xml =
        '''<work>
        <id>002</id>
        <urls>
            <url type="large">large2.jpg</url>
            <url type="small">small2.jpg</url>
        </urls>
        <exif>
            <make>MAKE2</make>
        </exif>
        </work>
        '''
    String model3Xml =
        '''<work>
        <id>003</id>
        <urls>
            <url type="small">small3.jpg</url>
            <url type="large">large3.jpg</url>
        </urls>
        <exif>
            <make>MAKE2</make>
        </exif>
        </work>
        '''

    @Before
    void setup() {
        command = new GenerateModels()
        def slurper = new XmlSlurper()
        context = [
            data: [
                makes: [
                    make1: [
                        model1: slurper.parseText(model1Xml)
                    ],
                    make2: [
                        model2: [slurper.parseText(model2Xml), slurper.parseText(model3Xml)]
                    ]
                ]
            ],
            files: [:],
            results: [:]
        ]
    }

    @Test
    void 'adds a file to the list of models'() {
        def result = command.execute(context)
        assert result.files.models == [
            make1:['make1-model1.html'],
            make2:['make2-model2.html']
        ]
    }

    @Test
    void 'generates thumbnails for the all works for that make and model'() {
        def result = command.execute(context)
        assert result.results['make1-model1.html'].thumbnails ==
            '''<div class='thumbnails'>
  <img src='small.jpg' />
</div>'''
        assert result.results['make2-model2.html'].thumbnails ==
            '''<div class='thumbnails'>
  <img src='small2.jpg' />
  <img src='small3.jpg' />
</div>'''
    }

    @Test
    void 'generates a navigation that allows the user to browse to to the index page and the make for that model'() {
        def result = command.execute(context)
        assert result.results['make1-model1.html'].navigation ==
            '''<a href='index.html'>index</a>
<a href='make1.html'>make1</a>'''
        assert result.results['make2-model2.html'].navigation ==
            '''<a href='index.html'>index</a>
<a href='make2.html'>make2</a>'''
    }

}
