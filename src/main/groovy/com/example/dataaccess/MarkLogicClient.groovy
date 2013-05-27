package com.example.dataaccess

import com.marklogic.client.DatabaseClient
import com.marklogic.client.document.JSONDocumentManager
import com.marklogic.client.document.XMLDocumentManager
import com.marklogic.client.io.DOMHandle
import com.marklogic.client.io.StringHandle
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.w3c.dom.Document

class MarkLogicClient {
    private static Log LOG = LogFactory.getLog(MarkLogicClient)

    private final DatabaseClient client
    private final XMLDocumentManager xmlDocumentManager
    private final JSONDocumentManager jsonDocumentManager

    MarkLogicClient(DatabaseClient client) {
        this.client = client
        xmlDocumentManager = client.newXMLDocumentManager()
        jsonDocumentManager = client.newJSONDocumentManager()
    }

    Document getXmlDocument(String uri) {
        if (LOG.debugEnabled) {
            LOG.debug("Getting XML document with URI $uri")
        }

        DOMHandle handle = new DOMHandle()
        xmlDocumentManager.read(uri, handle)
        handle.get()
    }

    String getJsonString(String uri) {
        if (LOG.debugEnabled) {
            LOG.debug("Getting JSON document with URI $uri")
        }

        final StringHandle handle = new StringHandle()
        jsonDocumentManager.read(uri, handle)
        handle.get()
    }
}
