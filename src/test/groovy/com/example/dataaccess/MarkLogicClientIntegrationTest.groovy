package com.example.dataaccess

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.junit.Before
import org.junit.Test
import org.w3c.dom.Document

import javax.xml.xpath.XPath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathExpression
import javax.xml.xpath.XPathFactory

class MarkLogicClientIntegrationTest {
    private static Log LOG = LogFactory.getLog(MarkLogicClientIntegrationTest)

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
        final Document checklist = markLogicClient.getXmlDocument('/checklist/f2284957-8809-4de7-9424-ee1f0cc4852c.xml')
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression xPathExpression = xpath.compile("//scopedIntervention/id");

        org.w3c.dom.NodeList scopedInterventionIdNodes = xPathExpression.evaluate(checklist, XPathConstants.NODESET) as org.w3c.dom.NodeList
        List<String> scopedInterventionIds = scopedInterventionIdNodes.collect {
            final String scopedInterventionId = it.textContent
            if (LOG.debugEnabled) {
                LOG.debug("Found scoped intervention ID: ${scopedInterventionId}")
            }
            scopedInterventionId
        }

        assert scopedInterventionIds.size()
    }
}