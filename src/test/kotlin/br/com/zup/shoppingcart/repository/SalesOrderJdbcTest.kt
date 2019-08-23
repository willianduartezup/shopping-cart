package br.com.zup.shoppingcart.repository

import br.com.zup.shoppingcart.domain.SalesOrder
import br.com.zup.shoppingcart.ServletTestConfig.Companion.id
import br.com.zup.shoppingcart.ServletTestConfig.Companion.LOG
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import kotlin.test.assertEquals

class SalesOrderJdbcTest {

    companion object {

        private val salesOrder = SalesOrder(id, "123456",-1, card_id = "123321")


        @BeforeClass
        @JvmStatic
        fun inserSalesOrder() {
            LOG.info("Insert Sales Order Test")

            val jdbc = ConnectionFactory()
            try {
                val factory = DAOFactory()
                val salesOrderDao: SalesOrderDAO =
                    factory.getInstanceOf(SalesOrderDAO::class.java, jdbc.getConnection()) as SalesOrderDAO

                salesOrderDao.add(salesOrder)
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                jdbc.closeConnection()
            }
        }

        @AfterClass
        @JvmStatic
        fun deleteSalesOrder() {

            LOG.info("Delete Sales Order Test")

            val jdbc = ConnectionFactory()

            try {
                val factory = DAOFactory()
                val salesOrderDao: SalesOrderDAO =
                    factory.getInstanceOf(SalesOrderDAO::class.java, jdbc.getConnection()) as SalesOrderDAO

                salesOrderDao.remove(id)

            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                jdbc.closeConnection()
            }
        }

    }

    @Test
    fun `validate insert Sales Order`() {
        LOG.info("Validate Insert Sales Order")

        val jdbc = ConnectionFactory()
        try {
            val factory = DAOFactory()
            val salesOrderDao: SalesOrderDAO =
                factory.getInstanceOf(SalesOrderDAO::class.java, jdbc.getConnection()) as SalesOrderDAO

            val result = salesOrderDao.add(salesOrder)
            assertEquals(result.id, salesOrder.id)

        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        } finally {
            jdbc.closeConnection()
        }
    }


    @Test
    fun `update Sales Order`() {

        LOG.info("Update Sales Order")

        val jdbc = ConnectionFactory()
        try {
            val factory = DAOFactory()
            val salesOrderDao: SalesOrderDAO =
                factory.getInstanceOf(SalesOrderDAO::class.java, jdbc.getConnection()) as SalesOrderDAO

            val salesOrder = SalesOrder(id, "123",-1, card_id = "321")

            salesOrderDao.edit(salesOrder)

        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()

        } finally {
            jdbc.closeConnection()
        }
    }



}