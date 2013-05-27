import com.marklogic.client.DatabaseClientFactory

markLogic {
    host = 'localhost'
    port = 8003
    user = 'admin'
    password = 'admin'
    authentication = DatabaseClientFactory.Authentication.DIGEST
}