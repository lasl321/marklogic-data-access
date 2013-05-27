package com.example.dataaccess

import org.junit.Before
import org.junit.Test

class MarkLogicClientIntegrationTest {
    MarkLogicClient markLogicClient

    @Before
    void before() {
        final Class<?> configClass = this.class.classLoader.loadClass('Config')
        final ConfigObject config = new ConfigSlurper().parse(configClass)

        final MarkLogicClientFactory factory = new MarkLogicClientFactory().with {
            host = config.markLogic.host
            port = config.markLogic.port
            user = config.markLogic.user
            password = config.markLogic.password
            authentication = config.markLogic.authentication

            it
        }

        markLogicClient = factory.createClient()
    }

    @Test
    void shouldGetXmlDocument() {
        markLogicClient.getXmlDocument('/xml/one.xml')
    }

    @Test
    void shouldGetJsonDocument() {
        markLogicClient.getJsonString('/json/two.json')
    }
}