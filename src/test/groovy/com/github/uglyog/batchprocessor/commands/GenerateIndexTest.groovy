package com.github.uglyog.batchprocessor.commands

import org.junit.Before
import org.junit.Test

public class GenerateIndexTest {

    GenerateIndex command
    def context
    String worksXml = '''
<works>
    <work>
        <id>001</id>
        <urls>
            <url type="small">small.jpg</url>
            <url type="large">large.jpg</url>
        </urls>
    </work>
    <work>
        <id>002</id>
        <urls>
            <url type="small">small2.jpg</url>
        </urls>
    </work>
    <work>
        <id>003</id>
        <urls>
            <url type="large">large3.jpg</url>
            <url type="small">small3.jpg</url>
        </urls>
    </work>
</works>
    '''

    @Before
    void setup() {
        command = new GenerateIndex()
        context = [
            data: [
                original:  new XmlSlurper().parseText(worksXml)
            ],
            results: [:]
        ]
    }

    @Test
    void 'generates thumbnails for the first 10 works'() {
        def result = command.execute(context)
        assert result.results['index.html'].thumbnails ==
'''<div class='thumbnails'>
  <img src='small.jpg' />
  <img src='small2.jpg' />
  <img src='small3.jpg' />
</div>'''
    }

    @Test
    void 'generates a navigation that allows the user to browse to all camera makes'() {
        def result = command.execute(context)
        assert result.results['index.html'].navigation == ''
    }

}
